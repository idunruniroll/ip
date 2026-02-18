package chad;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a task that must be completed by a specific date/time.
 * 
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class Deadline extends Task {
    private final LocalDate doneBy;

    /**
     * Creates a deadline task.
     *
     * @param description Task description.
     * @param doneBy      Deadline date/time (your type:
     *                    String/LocalDate/LocalDateTime).
     */
    public Deadline(String description, LocalDate doneBy) {
        super(description);
        this.doneBy = doneBy;
    }

    /**
     * Returns the display string for a deadline task.
     *
     * @return Display string including deadline information.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + Date.outputDate(doneBy) + ")";
    }

    /**
     * Returns the saved string for a deadline task.
     *
     * @return Save format string.
     */
    @Override
    public String toSaveString() {
        return "D | " + (getIsDone() ? "1" : "0") + " | " + getDescription() + " | " + doneBy;
    }

    /**
     * Checks equality based on the description and deadline date of the task. Two
     * deadline tasks are considered equal if they have the same description and
     * deadline,
     * regardless of their completion status. This allows for identifying duplicate
     * tasks based on their content and deadline.
     * 
     * @param o The object to compare with this deadline task.
     * @return true if the given object is a deadline task with the same description
     *         and deadline date, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deadline)) {
            return false;
        }
        Deadline other = (Deadline) o;
        return Objects.equals(this.getDescription(), other.getDescription())
                && Objects.equals(this.doneBy, other.doneBy);
    }

    /**
     * Generates a hash code based on the description and deadline date of the task.
     * This is consistent with the equals method, which considers two deadline tasks
     * equal
     * if they have the same description and deadline date.
     * 
     * @return A hash code value for this deadline task.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), doneBy);
    }
}
