package chad;

/**
 * Represents a simple todo task without any date/time constraints.
 * 
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class Todo extends Task {
    /**
     * Creates a todo task.
     *
     * @param description Task description.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toSaveString() {
        return "T | " + (getIsDone() ? "1" : "0") + " | " + getDescription();
    }
}
