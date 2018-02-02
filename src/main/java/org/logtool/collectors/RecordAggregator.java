package org.logtool.collectors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecordAggregator extends BaseRecordHandler {

    public Long aggregateRecordsByUsername(List<Date> list) throws ParseException {
        return list.stream().count();
    }

    public Map<String, Long> aggregateRecordsByDate(String by, List<Date> list) throws ParseException {

        String[] byP = by.split(",");

        if (byP[0].contains("date") && (byP[1].contains("hour") || byP[1].contains("day") || byP[1].contains("month"))) {
            switch (byP[1].trim()) {
                case "day":
                    return collectInFormat(dayFormatter, list);
                case "hour":
                    return collectInFormat(hourFormatter, list);
                case "month":
                    return collectInFormat(monthFormatter, list);
                default:
                    return collectInFormat(dateFormatter, list);
            }
        } else {
            throw new RuntimeException("Missed configuration for aggregation. Please use pattern: \"date, <day/hour/month>\"");
        }
    }

    private Map<String, Long> collectInFormat(SimpleDateFormat formatter, List<Date> list) {
        return list.stream().map(formatter::format).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
