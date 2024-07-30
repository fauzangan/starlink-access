package Starlink.starlink_access.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.Instant;

public class DateFormatter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String convertLongToStringDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return dateTime.toLocalDate().format(DATE_FORMATTER); // Only format the date part
    }

    public static Long convertStringDateToLong(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            // Handle the exception based on your requirement
            e.printStackTrace();
            return null;
        }
    }

    // Fungsi baru untuk mengonversi string dengan waktu menjadi long
    public static Long convertStringDateTimeToLong(String dateTimeStr) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
            return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            // Handle the exception based on your requirement
            e.printStackTrace();
            return null;
        }
    }
}
