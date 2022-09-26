package dev.caiosantesso.aws.reporter;


import dev.caiosantesso.aws.reporter.report.LambdasReporter;

import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        var lambdasReporter = new LambdasReporter();
        lambdasReporter.saveLatestToCsv();
//        lambdasReporter.saveVersionsToCsv();
    }
}
