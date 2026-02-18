package chad;

import java.util.Objects;

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

    /**
     * Checks equality based on the description of the todo task. Two todo tasks are
     * considered equal if they have the same description, regardless of their
     * completion
     * status. This allows for identifying duplicate tasks based on their content.
     * 
     * @param o The object to compare with this todo task.
     * @return true if the given object is a todo task with the same description,
     *         false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Todo)) {
            return false;
        }
        Todo other = (Todo) o;
        return Objects.equals(this.getDescription(), other.getDescription());
    }

    /**
     * Generates a hash code based on the description of the todo task. This is
     * consistent with
     * the equals method, which considers two todo tasks equal if they have the same
     * description.
     * 
     * @return A hash code value for this todo task.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
    }
}
