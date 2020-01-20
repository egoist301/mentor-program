package com.epam.esm.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateFormatter {
    private DateFormatter() {
    }

    public static LocalDate convertStringToDate(String date) {
        return LocalDate.parse(date,
                DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
