package chad;

/**
 * Represents a todo task that has only a description and no associated
 * date/time.
 * 
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class Todo extends Task {
    /**
     * Creates a {@code Todo} task with the given description.
     *
     * @param description Description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of this todo task for display to the user.
     *
     * @return A formatted string representing this todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the string representation of this todo task for saving to disk.
     *
     * @return A formatted string used for persistence.
     */
    @Override
    public String toSaveString() {
        return "T | " + (getIsDone() ? "1" : "0") + " | " + getDescription();
    }
}
