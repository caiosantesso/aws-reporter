package dev.caiosantesso.aws.reporter.report;

import dev.caiosantesso.aws.reporter.api.LambdasEndpoint;
import dev.caiosantesso.aws.reporter.api.LambdasEndpoint.Lambda;
import dev.caiosantesso.aws.reporter.file.Csv;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.logging.Logger;

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
        var csvPath = Csv.save(csvRows, dir);
        printFooter(start, csvPath);
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

    private void printFooter(LocalDateTime start, String csvPath) {
        logger.info(() -> "Saved at %s".formatted(csvPath));
        logger.info(() -> "Time elapsed (ms) %s".formatted(Duration
                .between(start, LocalDateTime.now())
                .toMillis()));
    }
}
