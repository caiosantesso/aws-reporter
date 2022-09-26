package dev.caiosantesso.aws.reporter.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class Csv {
    private Csv() {}

    public static String save(Iterable<String> rows, String dir) {
        var report = Path.of("reports/%s/%s.csv".formatted(dir, LocalDateTime.now()));
        var parent = report.getParent();
        try {
            Files.createDirectories(parent);
            Files.write(report, rows, CREATE_NEW);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return report.toAbsolutePath().toString();
    }
}
