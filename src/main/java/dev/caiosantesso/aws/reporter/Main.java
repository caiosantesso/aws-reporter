package dev.caiosantesso.aws.reporter;

import dev.caiosantesso.aws.reporter.report.LambdasReporter;

public class Main {
    public static void main(String[] args) {
        var lambdasReporter = new LambdasReporter();
        lambdasReporter.saveLatestToCsv();
//        lambdasReporter.saveVersionsToCsv();
    }
}
