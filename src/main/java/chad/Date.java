package chad;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing and formatting date/time inputs used by tasks.
 * 
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */

public class Date {
    // set input and output format
    private static final DateTimeFormatter INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT = DateTimeFormatter.ofPattern("MMM d yyyy");

    /**
     * Parses a user-provided date/time string into the app's internal
     * representation.
     *
     * @param input Date/time string provided by the user.
     * @return Parsed date/time representation (e.g., LocalDate/LocalDateTime or
     *         formatted string).
     * @throws ChadException If the input format is invalid.
     */
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
