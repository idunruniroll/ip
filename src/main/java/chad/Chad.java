package chad;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main chatbot class that coordinates parsing, storage, and UI.
 *
 * Captures CLI output printed to System.out and converts it into a formatted
 * String for GUI display.
 *
 * Loads and manages persistent task and note data.
 *
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class Chad {

    private final Ui ui;
    private final Parser parser;
    private final Save save;
    private final TaskList tasks;
    private final NoteList notes;
    private final NoteStorage noteStorage;

    private String commandType;

    /**
     * Constructs a Chad instance and loads saved tasks and notes from disk.
     *
     * Falls back to empty lists if loading fails.
     */
    public Chad() {
        ui = new Ui();
        parser = new Parser();
        save = new Save(Paths.get("data/chad.txt"));
        this.noteStorage = new NoteStorage(Path.of("data", "notes.txt"));

        NoteList loadedNotes;
        try {
            loadedNotes = noteStorage.load();
        } catch (ChadException e) {
            loadedNotes = new NoteList();
            ui.printError("OOPS!!! Failed to load notes.");
        }
        this.notes = loadedNotes;

        noteStorage = new NoteStorage(Path.of("data", "notes.txt"));
        NoteList loadedNotes;
        try {
            loadedNotes = noteStorage.load();
        } catch (ChadException e) {
            loadedNotes = new NoteList();
            ui.printError("OOPS!!! Failed to load notes.");
        }
        notes = loadedNotes;

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(save.load());
        } catch (ChadException e) {
            loadedTasks = new TaskList();
            ui.printFileLoadingError();
        }
        tasks = loadedTasks;

        assert ui != null : "Ui should be initialised";
        assert parser != null : "Parser should be initialised";
        assert save != null : "Save should be initialised";
        assert tasks != null : "TaskList should be initialised";
        assert notes != null : "NoteList should be initialised";
        assert noteStorage != null : "NoteStorage should be initialised";
    }

    /**
     * Returns the greeting message displayed when the chatbot starts.
     *
     * @return Greeting message.
     */
    public String getGreeting() {
        return "Hello! I'm Chad.\nWhat can I do for you?";
    }

    /**
     * Generates the chatbot's response to a user input.
     *
     * Captures CLI output and returns it as a formatted String.
     *
     * @param input Raw user input.
     * @return Response message for GUI display.
     */
    public String getResponse(String input) {
        // Assumption: caller should pass a String (GUI might, but we still guard)
        // Keep your existing if-check for user-facing message.
        if (input == null) {
            commandType = "Empty";
            return "Error: input was null";
        }

        // After this point, input is guaranteed non-null
        assert parser != null : "Parser should be initialised before getResponse()";
        assert tasks != null : "TaskList should be initialised before getResponse()";
        assert ui != null : "Ui should be initialised before getResponse()";
        assert save != null : "Save should be initialised before getResponse()";

        String trimmed = input.trim();

        // Assumption: trimming a non-null string gives non-null
        assert trimmed != null : "Trimmed input should not be null";

        setCommandTypeManually(trimmed);

        // Assumption: we expect commandType to be set by setCommandTypeManually
        assert commandType != null : "commandType should be set after setCommandTypeManually()";

        // GUI must NOT let Parser call System.exit(0)
        if (trimmed.equalsIgnoreCase("bye")) {
            commandType = "Bye";
            return "Bye. Hope to see you again soon!";
        }

        PrintStream originalOut = System.out;
        assert originalOut != null : "System.out should not be null";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream capture = new PrintStream(baos);

        try {
            System.setOut(capture);
            parser.handle(trimmed, tasks, ui, save, notes, noteStorage);
        } catch (ChadException e) {
            return "Error: " + e.getMessage();
        } finally {
            System.setOut(originalOut);
        }

        String out = baos.toString();
        assert out != null : "Captured output string should not be null";

        return cleanOutput(out);
    }

    /**
     * Returns the most recently inferred command type.
     *
     * @return Command type.
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Infers and sets the command type based on the first word of input.
     *
     * @param trimmed Trimmed user input.
     */
    private void setCommandTypeManually(String trimmed) {
        if (trimmed.isEmpty()) {
            commandType = "Empty";
            return;
        }

        String firstWord = trimmed.split("\\s+")[0].toLowerCase();
        switch (firstWord) {
            case "bye":
                commandType = "Bye";
                break;
            case "list":
                commandType = "List";
                break;
            case "mark":
                commandType = "Mark";
                break;
            case "unmark":
                commandType = "Unmark";
                break;
            case "todo":
                commandType = "Todo";
                break;
            case "deadline":
                commandType = "Deadline";
                break;
            case "event":
                commandType = "Event";
                break;
            case "delete":
                commandType = "Delete";
                break;
            case "find":
                commandType = "Find";
                break;
            case "note":
                commandType = "Note";
                break;
            default:
                commandType = "Unknown";
                break;
        }
    }

    /**
     * Cleans captured CLI output for GUI display.
     *
     * Removes separator lines and leading tabs.
     *
     * @param raw Raw CLI output.
     * @return Cleaned output.
     */
    private String cleanOutput(String raw) {
        String[] lines = raw.replace("\r", "").split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            String t = line.trim();
            if (t.matches("_+")) {
                continue;
            }
            sb.append(line.replaceFirst("^\\t+", "")).append("\n");
        }
        return sb.toString().trim();
    }
}
