package Starlink.starlink_access.utils;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String convertLongToStringDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return dateTime.format(FORMATTER);
    }

    public static Long convertStringDateToLong(String dateStr) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, FORMATTER);
            return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (DateTimeException e) {
            // Handle the exception based on your requirement
            e.printStackTrace();
            return null;
        }
    }
}
