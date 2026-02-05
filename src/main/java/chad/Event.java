package chad;

import java.time.LocalDate;

/**
 * Represents an event task that occurs during a specific time range.
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;

    /**
     * Creates an event task.
     *
     * @param description Task description.
     * @param from        Start date/time (LocalDate).
     * @param to          End date/time (LocalDate).
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the display string for an event task.
     *
     * @return Display string including event time range.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + Date.outputDate(from) + " to: " + Date.outputDate(to) + ")";
    }

    /**
     * Returns the save string for an event task.
     *
     * @return Save format string.
     */
    @Override
    public String toSaveString() {
        return "E | " + (getIsDone() ? "1" : "0") + " | " + getDescription() + " | " + from + " | " + to;
    }
}
