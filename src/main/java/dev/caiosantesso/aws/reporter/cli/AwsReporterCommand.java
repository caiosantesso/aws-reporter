package dev.caiosantesso.aws.reporter.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

@Command(name = "awsreporter",
         subcommands = {LambdasSubCommand.class, CommandLine.HelpCommand.class},
         description = "List AWS resources",
         mixinStandardHelpOptions = true)

public class AwsReporterCommand {
    @Mixin
    private HelpUsage helpUsage;

    public AwsReporterCommand() {}
}
