package org.output;


import org.apache.commons.lang3.StringUtils;
import org.utils.Utils;

import java.util.Map;

public class ConsoleTableIO {

    private void printHeader(String by) {
        System.out.format("\n");
        System.out.format("| %-25s| %-25s|\n", by, "Count of records");
    }

    public void printByUsername(Long count) {
        String userName = StringUtils.isEmpty(Utils.getUserName()) ? "match" : Utils.getUserName();
        printHeader(Utils.getGroupParams());
        System.out.format("| %-25s| %-25s|\n", userName, count);
    }

    public void printByGroup(Map<String, Long> map) {
        printHeader(Utils.getGroupParams());
        map.forEach((key, value) -> System.out.format("| %-25s| %-25d|\n", key, value));
    }
}
