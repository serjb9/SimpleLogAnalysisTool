package org;

import org.logtool.SimpleLogAnalyser;
import org.slf4j.Logger;

public class Main {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SimpleLogAnalyser analyzer = new SimpleLogAnalyser();
        analyzer.analyzeFolder();
        LOG.info(" - - - Done.\n");
    }
}
