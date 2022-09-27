package dev.caiosantesso.aws.reporter;


import dev.caiosantesso.aws.reporter.cli.AwsReporterCommand;
import picocli.CommandLine;

public class Main {

    public static void main(String... args) {
        int exitCode = new CommandLine(new AwsReporterCommand()).execute(args);
        System.exit(exitCode);
    }
}
