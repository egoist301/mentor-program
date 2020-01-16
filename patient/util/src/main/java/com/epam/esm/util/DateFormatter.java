package com.epam.esm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatter {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd");

    private DateFormatter() {
    }

    public static Date convertStringToDate(String date) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(date);
    }

    public static String convertDateToString(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }
}
