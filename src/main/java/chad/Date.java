package chad;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Date {
    // set input and output format
    private static final DateTimeFormatter INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT = DateTimeFormatter.ofPattern("MMM d yyyy");

    public static LocalDate inputDate(String dateString) throws ChadException {
        try {
            return LocalDate.parse(dateString.trim(), INPUT);
        } catch (DateTimeParseException e) {
            throw new ChadException("Invalid date format. Please use yyyy-MM-dd (E.g., 2019-10-15)");
        }
    }

    public static String outputDate(LocalDate date) {
        return date.format(OUTPUT);
    }
}
