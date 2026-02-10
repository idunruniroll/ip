package chad;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Stores and manages the list of tasks in the application.
 * Provides operations such as add, remove, retrieve, and validate indexes.
 * 
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list initialized with existing tasks.
     *
     * @param tasks Existing tasks loaded from storage.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index zero-based index.
     * @return task at the given index.
     */
    public Task get(int index) throws ChadException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChadException("OOPS!!! Invalid task number.");
        }
        assert index >= 0 : "Index must be non-negative";
        assert index < tasks.size() : "Index must be within bounds";
        return tasks.get(index);
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        assert task != null : "Task to add should not be null";
        tasks.add(task);
        assert tasks.contains(task) : "Task should be in list after add";
    }

    /**
     * Removes and returns the task at the specified index after validation.
     *
     * @param index zero-based index.
     * @return removed task.
     * @throws ChadException If index is invalid.
     */
    public Task remove(int index) throws ChadException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChadException("OOPS!!! Invalid task number.");
        }
        return tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the list of tasks that contain the given keyword.
     *
     * @param keyword Keyword to search for.
     * @return List of matching tasks.
     */
    public ArrayList<Task> find(String keyword) {
        String k = keyword.toLowerCase();
        return tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(k))
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
