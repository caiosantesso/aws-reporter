package dev.caiosantesso.aws.reporter.report;

import dev.caiosantesso.aws.reporter.api.LambdasEndpoint;
import dev.caiosantesso.aws.reporter.api.LambdasEndpoint.Lambda;
import dev.caiosantesso.aws.reporter.file.Csv;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class LambdasReporter {

    private static final Logger logger = Logger.getLogger(LambdasReporter.class.getName());
    private final boolean insertHeader;

    public LambdasReporter(boolean insertHeader) {this.insertHeader = insertHeader;}

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

    private List<String> toCsvRows(Collection<Lambda> lambdas) {
        var stream = lambdas
                .stream()
                .map(Lambda::toCsvRow);

        if (insertHeader) stream = Stream.concat(Stream.of("name,runtime,version,codeSize"), stream);

        return stream.toList();
    }

    private static void printFooter(LocalDateTime start, String csvPath) {
        logger.info(() -> "Saved at %s".formatted(csvPath));
        logger.info(() -> "Time elapsed (ms) %s".formatted(Duration
                .between(start, LocalDateTime.now())
                .toMillis()));
    }
}
