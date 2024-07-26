package Starlink.starlink_access.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String convertLongToStringDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return dateTime.toLocalDate().format(FORMATTER); // Only format the date part
    }

    public static Long convertStringDateToLong(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr, FORMATTER);
            return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (DateTimeException e) {
            // Handle the exception based on your requirement
            e.printStackTrace();
            return null;
        }
    }
}
