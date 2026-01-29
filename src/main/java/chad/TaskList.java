package chad;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) throws ChadException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChadException("OOPS!!! Invalid task number.");
        }
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) throws ChadException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChadException("OOPS!!! Invalid task number.");
        }
        return tasks.remove(index);
    }

    //TOREMOVE
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
