package org.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateFormatUtils {

    private DateFormat formatter;
    private Date dateFrom;
    private Date dateTo;

    public DateFormatUtils(DateFormat formatter, String period) {
        this.formatter = formatter;
        try {
            String[] arr = period.split(" - ");
            this.dateFrom = formatter.parse(arr[0]);
            this.dateTo = formatter.parse(arr[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public Date parseDate(String s) throws ParseException {
        return formatter.parse(s);
    }
}
