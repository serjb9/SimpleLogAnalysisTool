package org.output;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.utils.Utils;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class CSVFileIO {

    protected static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CSVFileIO.class);

    private Path outputFile;
    private Writer writer;
    private CSVWriter csvWriter;

    private final Path FILE_PATH = Utils.getOutputDir();
    private final String FILE_NAME = "analyzedRecords.csv";

    public CSVFileIO() {
        this.writer = getWriter();
    }

    public synchronized void writeResultsToFile(Map<String, Long> resultsMap) {
        String[] headerRecord = {Utils.getGroupParams().split(",")[1], "Count of records"};
        getCsvWriter(getWriter()).writeNext(headerRecord);
        resultsMap.forEach((key, value) -> {
            String[] line = {(key), (String.valueOf(value))};
            write(line);
        });
        closeFile();
        LOG.info(" - - - Results were written to the file: " + outputFile.toAbsolutePath());
    }

    public synchronized void writeResultsToFile(Long count) {
        String[] headerRecord = {"Username", "Count of records"};
        String[] line = {Utils.getUserName(), String.valueOf(count)};
        write(headerRecord);
        write(line);
        closeFile();
        LOG.info(" - - - Results were written to the file: " + outputFile.toAbsolutePath());
    }

    private Writer getWriter() {
        if (writer == null) {
            try {
                outputFile = Paths.get(FILE_PATH.toString(), FILE_NAME);
                return writer = Files.newBufferedWriter(outputFile);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot get access to output file.");
            }
        }
        return writer;
    }

    private CSVWriter getCsvWriter(Writer writer) {
        if (csvWriter == null) {
            this.csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            return csvWriter;
        }
        return csvWriter;
    }

    private void write(String[] line) {
        getCsvWriter(getWriter()).writeNext(line);
    }

    private void closeFile() {
        try {
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
