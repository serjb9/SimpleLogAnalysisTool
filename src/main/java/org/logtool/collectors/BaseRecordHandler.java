package org.logtool.collectors;

import org.matcher.LineMatcher;
import org.slf4j.Logger;
import org.utils.DateFormatUtils;
import org.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class BaseRecordHandler {

    protected static final Logger LOG = org.slf4j.LoggerFactory.getLogger(BaseRecordHandler.class);

    protected LineMatcher logRecordMatcher;

    protected DateFormatUtils dateUtils = new DateFormatUtils(new SimpleDateFormat(Utils.getDatePattern(), Locale.ENGLISH),
            Utils.getPeriod());

    protected SimpleDateFormat dateFormatter = new SimpleDateFormat(Utils.getDatePattern(), Locale.ENGLISH);
    protected SimpleDateFormat dayFormatter = new SimpleDateFormat("YYYY/MM/dd", Locale.ENGLISH);
    protected SimpleDateFormat hourFormatter = new SimpleDateFormat("YYYY/MM/dd - HH:00", Locale.ENGLISH);
    protected SimpleDateFormat monthFormatter = new SimpleDateFormat("YYYY/MM", Locale.ENGLISH);

}
