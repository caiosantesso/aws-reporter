package dev.caiosantesso.aws.reporter.cli;

import dev.caiosantesso.aws.reporter.report.LambdasReporter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

@Command(name = "lambdas", description = "List lambdas")
public class LambdasSubCommand {
    @Mixin
    private HelpUsage helpUsage;

    @Command(name = "latest", description = "List only latest AWS lambda functions")
    public void latest(@Option(names = "--with-header", description = "Inserts header row into CSV")
                       boolean insertHeader) {
        LambdasReporter reporter = new LambdasReporter(insertHeader);
        reporter.saveLatestToCsv();
    }

    @Command(name = "versions", description = "List all AWS lambda function versions")
    public void versions(@Option(names = "--with-header", description = "Inserts header row into CSV")
                         boolean insertHeader) {
        LambdasReporter reporter = new LambdasReporter(insertHeader);
        reporter.saveVersionsToCsv();
    }
}
