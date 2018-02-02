package org.matcher;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineMatcher {

    private Pattern p;
    private Matcher m;

    public LineMatcher(String stringPattern) {
        if (!stringPattern.isEmpty()) {
            p = Pattern.compile(stringPattern);
        } else {
            p = Pattern.compile("(.*)");
        }
    }

    public boolean find(String string) {
        Matcher m = p.matcher(string);
        return m.find();
    }

    public String getStringPattern() {
        return p.toString();
    }
}

