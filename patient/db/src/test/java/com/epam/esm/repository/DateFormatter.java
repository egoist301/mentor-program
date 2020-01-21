package com.epam.esm.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

final class DateFormatter {
    private DateFormatter() {
    }

    static LocalDate convertStringToDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
