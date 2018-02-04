package org.logtool;


import org.logtool.collectors.RecordAggregator;

import org.logtool.collectors.RecordFilter;
import org.omg.SendingContext.RunTime;
import org.output.CSVFileIO;
import org.output.ConsoleTableIO;
import org.slf4j.Logger;
import org.utils.LogFileUtils;
import org.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleLogAnalyser {

    protected static final Logger LOG = org.slf4j.LoggerFactory.getLogger(SimpleLogAnalyser.class);

    private LogFileUtils logFileUtils;
    private RecordFilter filter;
    private RecordAggregator aggregator;
    private CSVFileIO csvFileIO;
    private ConsoleTableIO consoleTableIO;
    private List<Path> pathList;

    private String by = Utils.getGroupParams().toLowerCase().trim();

    private Long countByUsername = null;
    private Map<String, Long> dateResults = new HashMap<>();

    public SimpleLogAnalyser() {
        this.filter = new RecordFilter();
        this.aggregator = new RecordAggregator();
        this.logFileUtils = new LogFileUtils();
        this.csvFileIO = new CSVFileIO();
        this.consoleTableIO = new ConsoleTableIO();
    }

    public void analyzeFolder() {
        try {
            process();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during directory processing.");
        }
    }

    private void process() throws IOException, ParseException {

        if (by.contains("date")) {
            for (Path path : getPathList()) {
                List<Date> filteredRecords = getFilteredRecords(path);
                Map<String, Long> aggregatedRecords = getAggregator().aggregateRecordsByDate(by, filteredRecords);
                if (aggregatedRecords.keySet().size() != 0 && dateResults.keySet().size() == 0) {
                    dateResults.putAll(aggregatedRecords);
                } else {
                    dateResults.keySet().forEach(i -> dateResults.merge(i, dateResults.get(i), Long::sum));
                }
            }
            dateResults = sort(dateResults);
            getConsoleTableIO().printByGroup(dateResults);
            getCSVFileIO().writeResultsToFile(dateResults);


        } else if (by.contains("username")) {
            for (Path p : getPathList()) {
                List<Date> filteredRecords = getFilteredRecords(p);
                if (countByUsername != null) {
                    countByUsername += getAggregator().aggregateRecordsByUsername(filteredRecords);
                } else {
                    countByUsername = getAggregator().aggregateRecordsByUsername(filteredRecords);
                }
            }
            getConsoleTableIO().printByUsername(countByUsername);
            getCSVFileIO().writeResultsToFile(countByUsername);
        } else {
            throw new RuntimeException("No valid group parameters were given.");
        }
    }

    private List<Date> getFilteredRecords(Path path) throws IOException, ParseException {
        LOG.info(" - - - Scanning file: " + path);
        FileReader fileReader = new FileReader(path.toFile());
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(fileReader);
            return getFilter().filterRecords(reader);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private List<Path> getPathList() throws IOException {
        if (pathList == null) {
            pathList = logFileUtils.findFiles(Utils.getDirectoryPath(),
                    Utils.getLogFilePattern());
            return pathList;
        }
        return pathList;
    }

    private RecordFilter getFilter() {
        return filter;
    }

    private CSVFileIO getCSVFileIO() {
        return csvFileIO;
    }

    public RecordAggregator getAggregator() {
        return aggregator;
    }

    public ConsoleTableIO getConsoleTableIO() {
        return consoleTableIO;
    }

    private Map<String, Long> sort(Map<String, Long> map) {
        return map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k, v) -> k, LinkedHashMap::new));
    }
}

