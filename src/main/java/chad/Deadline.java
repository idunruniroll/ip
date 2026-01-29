package chad;

import java.time.LocalDate;

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
}
