package org.utils;


import org.omg.SendingContext.RunTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class Utils {

    private static Properties props;

    static {
        initProps();
    }

    private static void initProps() {
        props = new Properties();
        try {
            if (System.getProperty("config.path") == null) {
                throw new RuntimeException("Configuration file was not specified. Please use \"config.path\" property to specify the path.");
            }
            File fileProp = new File(System.getProperty("config.path"));
            props.load(new FileInputStream(fileProp));
            if (getUserName().isEmpty() && getPeriod() == null && getCustomMsg().isEmpty()) {
                throw new RuntimeException("At least one parameter should be specified: Username/Period/Custom message.");
            }
        } catch (IOException e) {
            e.getCause().getLocalizedMessage();
        }
    }

    public static Properties getProps() {
        if (props != null)
            return props;
        else throw new RuntimeException("Properties are empty.");
    }

    public static String getLogFilePattern() {
        return props.getProperty("logFilePattern.regexp");
    }

    public static Path getDirectoryPath() {
        return Paths.get(props.getProperty("directory"));
    }

    public static String getDatePattern() {
        return props.getProperty("record.date.format");
    }

    public static String getDatePatternRegExp() {
        return props.getProperty("record.date.pattern.regexp");
    }

    public static String getUserName() {
        return props.getProperty("username");
    }

    public static String getCustomMsg() {
        return props.getProperty("custom.message");
    }

    public static String getGroupParams() {
        String by = props.getProperty("group.by");
        if (by.isEmpty()) {
            throw new RuntimeException("Group parameters are empty.");
        } else return by;
    }

    public static Path getOutputDir() {
        String dir = props.getProperty("output.directory");
        if (dir.isEmpty()) {
            return getDirectoryPath();
        } else {
            return Paths.get(dir);
        }
    }

    public static String getPeriod() {
        return props.getProperty("period");
    }
}
