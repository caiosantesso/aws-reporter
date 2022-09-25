package dev.caiosantesso.aws.reporter.report;

import dev.caiosantesso.aws.reporter.api.LambdasEndpoint;
import dev.caiosantesso.aws.reporter.api.LambdasEndpoint.Lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class LambdasReporter {

    private final Logger logger = Logger.getLogger(LambdasReporter.class.getName());

    public void saveLatestToCsv() {
        logger.info(() -> "Fetching all of the latest lambdas");
        saveToCsv(LambdasReporter::fetchLatest, "lambdas");
    }

    public void saveVersionsToCsv() {
        logger.info(() -> "Fetching all lambda versions");
        saveToCsv(LambdasReporter::fetchVersions, "lambda_versions");
    }

    private void saveToCsv(Supplier<Collection<Lambda>> lambdaSupplier, String dir) {
        var start = LocalDateTime.now();
        var lambdas = lambdaSupplier.get();
        var csvRows = toCsvRows(lambdas);
        var csv = saveToCsv(csvRows, dir);
        printFooter(start, csv);
    }


    private static Collection<Lambda> fetchLatest() {
        try (LambdasEndpoint endpoint = new LambdasEndpoint()) {
            return endpoint.lambdas();
        }
    }

    private static Collection<Lambda> fetchVersions() {
        try (LambdasEndpoint endpoint = new LambdasEndpoint()) {
            return endpoint.lambdaVersions();
        }
    }

    private Iterable<String> toCsvRows(Collection<Lambda> lambdas) {
        return lambdas
                .stream()
                .map(Lambda::toCsvRow)
                .toList();
    }

    private static Path saveToCsv(Iterable<String> rows, String dir) {
        var report = Path.of("reports/%s/%s.csv".formatted(dir, LocalDateTime.now()));
        var parent = report.getParent();
        try {
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            Files.write(report, rows, CREATE_NEW);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return report;
    }


    private void printFooter(LocalDateTime start, Path csv) {
        logger.info(() -> "Saved at %s".formatted(csv.toAbsolutePath()));
        logger.info(() -> "Time elapsed (ms) %s".formatted(Duration
                .between(start, LocalDateTime.now())
                .toMillis()));
    }
}
