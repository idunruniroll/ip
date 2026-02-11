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
    private static final String CMD_NOTE = "note";

    /**
     * Handles a single line of user input by identifying the command and
     * delegating execution to the corresponding command handler.
     *
     * @param input       Raw user input string.
     * @param taskList    The task list containing all tasks.
     * @param ui          UI component responsible for output.
     * @param save        Storage component responsible for persistence.
     * @param noteList    The list containing all notes.
     * @param noteStorage Storage component responsible for note persistence.
     * @throws ChadException If a command fails due to invalid input or state.
     */
    public void handle(String input, TaskList taskList, Ui ui, Save save, NoteList noteList, NoteStorage noteStorage)
            throws ChadException {
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

            case CMD_NOTE:
                handleNote(args, noteList, ui, noteStorage);
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
     * Handles note commands (add/list/delete/find).
     *
     * @param args        Note subcommand and arguments.
     * @param noteList    The list containing all notes.
     * @param ui          UI component for output.
     * @param noteStorage Storage component responsible for note persistence.
     * @throws ChadException If note command format or note index is invalid.
     */
    private void handleNote(String args, NoteList noteList, Ui ui, NoteStorage noteStorage) throws ChadException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ChadException("OOPS!!! Note format: note <add|list|delete|find> ...");
        }

        String[] parts = trimmedArgs.split("\\s+", 2);
        String subCommand = parts[0];
        String subArgs = parts.length == 2 ? parts[1].trim() : "";

        switch (subCommand) {
            case "add":
                if (subArgs.isEmpty()) {
                    throw new ChadException("OOPS!!! Note add format: note add <text>");
                }
                Note note = new Note(subArgs);
                noteList.add(note);
                noteStorage.save(noteList.getNotes());

                ui.printLine();
                System.out.println("\tGot it. I've added this note:");
                System.out.println("\t  " + note);
                ui.printLine();
                break;

            case "list":
                ui.printLine();
                if (noteList.size() == 0) {
                    System.out.println("\tYou have no notes.");
                } else {
                    for (int i = 0; i < noteList.size(); i++) {
                        System.out.println("\t" + (i + 1) + ". " + noteList.get(i));
                    }
                }
                ui.printLine();
                break;

            case "delete":
                int deleteIndex = parseNoteIndex(subArgs);
                Note removed = noteList.remove(deleteIndex);
                noteStorage.save(noteList.getNotes());

                ui.printLine();
                System.out.println("\tNoted. I've removed this note:");
                System.out.println("\t  " + removed);
                ui.printLine();
                break;

            case "find":
                if (subArgs.isEmpty()) {
                    throw new ChadException("OOPS!!! Note find format: note find <keyword>");
                }
                ArrayList<Note> matches = noteList.find(subArgs);

                ui.printLine();
                if (matches.isEmpty()) {
                    System.out.println("\tNo matching notes found.");
                } else {
                    System.out.println("\tHere are the matching notes:");
                    for (int i = 0; i < matches.size(); i++) {
                        System.out.println("\t" + (i + 1) + ". " + matches.get(i));
                    }
                }
                ui.printLine();
                break;

            default:
                throw new ChadException("OOPS!!! Unknown note command.");
        }
    }

    /**
     * Parses a one-based note index string into a zero-based index.
     *
     * @param raw Raw note index.
     * @return Zero-based note index.
     * @throws ChadException If missing or not a valid positive integer.
     */
    private int parseNoteIndex(String raw) throws ChadException {
        if (raw == null || raw.trim().isEmpty()) {
            throw new ChadException("OOPS!!! Note delete format: note delete <note number>");
        }

        try {
            int index = Integer.parseInt(raw.trim()) - 1;
            assert index >= 0 : "note index should be non-negative";
            return index;
        } catch (NumberFormatException e) {
            throw new ChadException("OOPS!!! Note delete format: note delete <note number>");
        }
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
