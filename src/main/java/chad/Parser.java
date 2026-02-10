package chad;

import java.util.ArrayList;

/**
 * Parses and executes user commands by coordinating TaskList, Ui, and Save.
 *
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class Parser {

    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_FIND = "find";

    /**
     * Handles a single line of user input by identifying the command and
     * delegating execution to the corresponding command handler.
     *
     * @param input    Raw user input string.
     * @param taskList The task list containing all tasks.
     * @param ui       UI component responsible for output.
     * @param save     Storage component responsible for persistence.
     * @throws ChadException If a command fails due to invalid input or state.
     */
    public void handle(String input, TaskList taskList, Ui ui, Save save) throws ChadException {
        String trimmed = input.trim();
        String[] parts = trimmed.split("\\s+", 2);

        String commandWord = parts[0];
        String args = parts.length == 2 ? parts[1].trim() : "";

        switch (commandWord) {
            case CMD_BYE:
                ui.exit();
                System.exit(0);
                break;

            case CMD_LIST:
                handleList(taskList, ui);
                break;

            case CMD_MARK:
                handleMark(args, taskList, ui, save);
                break;

            case CMD_UNMARK:
                handleUnmark(args, taskList, ui, save);
                break;

            case CMD_TODO:
                handleTodo(args, taskList, ui, save);
                break;

            case CMD_DEADLINE:
                handleDeadline(args, taskList, ui, save);
                break;

            case CMD_EVENT:
                handleEvent(args, taskList, ui, save);
                break;

            case CMD_DELETE:
                handleDelete(args, taskList, ui, save);
                break;

            case CMD_FIND:
                handleFind(args, taskList, ui);
                break;

            default:
                ui.printError("OOPS!!! I'm sorry, but I don't know what that means :-(");
                break;
        }
    }

    /**
     * Displays all tasks currently stored in the task list.
     *
     * @param taskList The task list to display.
     * @param ui       UI component for formatting output.
     * @throws ChadException If task retrieval fails unexpectedly.
     */
    private void handleList(TaskList taskList, Ui ui) throws ChadException {
        ui.printLine();

        if (taskList.size() == 0) {
            System.out.println("\tYour list is empty.");
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + taskList.get(i));
            }
        }

        ui.printLine();
    }

    /**
     * Marks a task as completed.
     *
     * @param args     Argument string containing the task index.
     * @param taskList The task list containing the task.
     * @param ui       UI component for output.
     * @param save     Storage component for persisting changes.
     * @throws ChadException If the task index is invalid.
     */
    private void handleMark(String args, TaskList taskList, Ui ui, Save save) throws ChadException {
        int index = parseTaskIndex(
                args,
                "OOPS!!! Mark format: mark <task number>",
                "OOPS!!! Mark format: mark <task number>");

        assert index >= 0 : "mark index should be non-negative";

        try {
            taskList.get(index).markAsDone();
            save.save(taskList.getTasks());

            ui.printLine();
            System.out.println("\tNice! I've marked this task as done:");
            System.out.println("\t  " + taskList.get(index));
            ui.printLine();
        } catch (ChadException e) {
            throw new ChadException("OOPS!!! Invalid task number for mark.");
        }
    }

    /**
     * Marks a task as not completed.
     *
     * @param args     Argument string containing the task index.
     * @param taskList The task list containing the task.
     * @param ui       UI component for output.
     * @param save     Storage component for persisting changes.
     * @throws ChadException If the task index is invalid.
     */
    private void handleUnmark(String args, TaskList taskList, Ui ui, Save save) throws ChadException {
        int index = parseTaskIndex(
                args,
                "OOPS!!! Unmark format: unmark <task number>",
                "OOPS!!! Unmark format: unmark <task number>");

        assert index >= 0 : "unmark index should be non-negative";

        try {
            taskList.get(index).markAsNotDone();
            save.save(taskList.getTasks());

            ui.printLine();
            System.out.println("\tOK, I've marked this task as not done yet:");
            System.out.println("\t  " + taskList.get(index));
            ui.printLine();
        } catch (ChadException e) {
            throw new ChadException("OOPS!!! Invalid task number for unmark.");
        }
    }

    /**
     * Adds a todo task to the task list.
     *
     * @param args     Description of the todo task.
     * @param taskList The task list to add the task to.
     * @param ui       UI component for output.
     * @param save     Storage component for persisting changes.
     */
    private void handleTodo(String args, TaskList taskList, Ui ui, Save save) {
        if (args.isEmpty()) {
            ui.printError("OOPS!!! The description of a todo cannot be empty.");
            return;
        }

        Task task = new Todo(args);
        taskList.add(task);

        try {
            save.save(taskList.getTasks());
        } catch (ChadException e) {
            ui.printError("OOPS!!! Failed to save tasks.");
        }

        ui.printLine();
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
        ui.printLine();
    }

    /**
     * Adds a deadline task to the task list.
     *
     * @param args     Argument string containing description and deadline.
     * @param taskList The task list to add the task to.
     * @param ui       UI component for output.
     * @param save     Storage component for persisting changes.
     */
    private void handleDeadline(String args, TaskList taskList, Ui ui, Save save) {
        try {
            if (args.isEmpty()) {
                throw new ChadException("OOPS!!! Deadline format: deadline <desc> /by <time>");
            }

            String[] parts = args.split("\\s+/by\\s+", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new ChadException("OOPS!!! Deadline format: deadline <desc> /by <time>");
            }

            Task t = new Deadline(parts[0].trim(), Date.inputDate(parts[1].trim()));
            taskList.add(t);
            save.save(taskList.getTasks());

            ui.printLine();
            System.out.println("\tGot it. I've added this task:");
            System.out.println("\t  " + t);
            System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
            ui.printLine();
        } catch (ChadException e) {
            ui.printError(e.getMessage());
        }
    }

    /**
     * Adds an event task to the task list.
     *
     * @param args     Argument string containing description, start, and end time.
     * @param taskList The task list to add the task to.
     * @param ui       UI component for output.
     * @param save     Storage component for persisting changes.
     */
    private void handleEvent(String args, TaskList taskList, Ui ui, Save save) {
        try {
            String[] parts = args.split(" /from ", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty()) {
                throw new ChadException("OOPS!!! Event format: event <desc> /from <start> /to <end>");
            }

            String[] times = parts[1].split(" /to ", 2);
            if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty()) {
                throw new ChadException("OOPS!!! Event format: event <desc> /from <start> /to <end>");
            }

            Task t = new Event(parts[0].trim(),
                    Date.inputDate(times[0].trim()),
                    Date.inputDate(times[1].trim()));
            taskList.add(t);
            save.save(taskList.getTasks());

            ui.printLine();
            System.out.println("\tGot it. I've added this task:");
            System.out.println("\t  " + t);
            System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
            ui.printLine();
        } catch (ChadException e) {
            ui.printError("OOPS!!! Event format: event <desc> /from <start> /to <end>");
        }
    }

    /**
     * Deletes a task from the task list.
     *
     * @param args     Argument string containing the task index.
     * @param taskList The task list containing the task.
     * @param ui       UI component for output.
     * @param save     Storage component for persisting changes.
     */
    private void handleDelete(String args, TaskList taskList, Ui ui, Save save) {
        if (args.isEmpty()) {
            ui.printError("OOPS!!! Please specify which task to delete. Delete format: delete <task number>");
            return;
        }

        try {
            int index = parseTaskIndex(
                    args,
                    "OOPS!!! Delete format: delete <task number>",
                    "OOPS!!! Delete format: delete <task number>");

            Task removed = taskList.remove(index);
            save.save(taskList.getTasks());

            ui.printLine();
            System.out.println("\tNoted. I've removed this task:");
            System.out.println("\t  " + removed);
            System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
            ui.printLine();
        } catch (ChadException e) {
            ui.printError(e.getMessage());
        }
    }

    /**
     * Finds and displays tasks that contain the given keyword.
     *
     * @param args     Keyword to search for.
     * @param taskList The task list to search within.
     * @param ui       UI component for output.
     */
    private void handleFind(String args, TaskList taskList, Ui ui) {
        if (args.isEmpty()) {
            ui.printError("OOPS!!! Find format: find <keyword>");
            return;
        }

        ArrayList<Task> matches = taskList.find(args);

        ui.printLine();
        if (matches.size() == 0) {
            System.out.println("\tNo matching tasks found.");
        } else {
            System.out.println("\tHere are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + matches.get(i));
            }
        }
        ui.printLine();
    }

    /**
     * Parses a task index from a raw argument string.
     *
     * @param raw            Raw argument containing the index.
     * @param emptyErrorMsg  Error message for missing index.
     * @param numberErrorMsg Error message for invalid index format.
     * @return Zero-based task index.
     * @throws ChadException If the index is missing or not a valid number.
     */
    private int parseTaskIndex(String raw, String emptyErrorMsg, String numberErrorMsg) throws ChadException {
        if (raw == null || raw.trim().isEmpty()) {
            throw new ChadException(emptyErrorMsg);
        }

        try {
            return Integer.parseInt(raw.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new ChadException(numberErrorMsg);
        }
    }
}
