package org.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogFileUtils {

    public List<Path> findFiles(Path path, String pattern) throws IOException {
        Pattern p = Pattern.compile(pattern);

        List<Path> filesList = Files.walk(path)
                .filter(entry -> {
                    if (!Files.isDirectory(entry)) {
                        Matcher m = p.matcher(entry.getFileName().toString());
                        return m.find();
                    } else return false;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (filesList.isEmpty()) {
            throw new FileNotFoundException("No logtool file(s) matching pattern was/were found.");
        } else return filesList;
    }
}
