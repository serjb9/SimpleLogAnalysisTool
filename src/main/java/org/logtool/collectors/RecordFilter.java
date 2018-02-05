package org.logtool.collectors;


import org.matcher.LineMatcher;
import org.slf4j.Logger;
import org.utils.Utils;

import java.io.BufferedReader;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RecordFilter extends BaseRecordHandler {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(RecordFilter.class);

    public RecordFilter() {
        logRecordMatcher = new LineMatcher(String.format("%s(\\t+|\\s+)-(\\t+|\\s+)%s(\\t+|\\s+)-(\\t+|\\s+)%s",
                new LineMatcher(Utils.getDatePatternRegExp()).getStringPattern(),
                new LineMatcher(Utils.getUserName()).getStringPattern(),
                new LineMatcher(Utils.getCustomMsg()).getStringPattern()));
        LOG.info("Seeking with pattern: " + logRecordMatcher.getStringPattern() + "\n");
    }

    public List<Date> filterRecords(BufferedReader reader) throws ParseException {
        return reader
                .lines()
                .map(i -> {
                    Date date;
                    Matcher m = Pattern.compile(logRecordMatcher.getStringPattern()).matcher(i);
                    if (m.find()) {
                        try {
                            date = dateUtils.parseDate(m.group(1));
                            if (date.after(dateUtils.getDateFrom()) && date.before(dateUtils.getDateTo())) {
                                return date;
                            } else return null;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return null;
                        }
                    } else return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}