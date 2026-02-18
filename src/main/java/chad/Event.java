package chad;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents an event task that occurs during a specific time range.
 * 
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

    /**
     * Checks equality based on the description and time range of the event. Two
     * event
     * tasks are considered equal if they have the same description and time range,
     * regardless
     * of their completion status. This allows for identifying duplicate tasks based
     * on their content
     * and scheduled time.
     * 
     * @param o The object to compare with this event task.
     * @return true if the given object is an event task with the same description
     *         and time
     *         range, false otherwise.
     * 
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        Event other = (Event) o;
        return Objects.equals(this.getDescription(), other.getDescription())
                && Objects.equals(this.from, other.from)
                && Objects.equals(this.to, other.to);
    }

    /**
     * Generates a hash code based on the description and time range of the event.
     * This is consistent with the equals method, which considers two event tasks
     * equal
     * if they have the same description and time range.
     * 
     * @return A hash code value for this event task.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), from, to);
    }
}
