package chad;

/**
 * Represents a task with a description and completion status.
 * Subclasses provide additional fields such as deadlines or time ranges.
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a task with the given description by user. Newly created tasks are
     * not done.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Returns the formatted string.
     *
     * @return done status and description.
     */
    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }

    /**
     * Returns the string representation used for saving to disk.
     *
     * @return Save string in the app's storage format.
     */
    public abstract String toSaveString();

}
