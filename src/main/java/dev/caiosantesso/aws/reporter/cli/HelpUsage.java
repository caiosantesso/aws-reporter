package dev.caiosantesso.aws.reporter.cli;

import picocli.CommandLine.Command;

@Command(version = {
        "@|fg(magenta) awsreporter 2022.09.alpha|@",
        "Picocli " + picocli.CommandLine.VERSION,
        "JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})",
        "OS: ${os.name} ${os.version} ${os.arch}"},
         optionListHeading = "%nOptions:%n",
         commandListHeading = "%nCommands:%n")
public class HelpUsage {
}
