package chad;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;

public class Chad {

    private final Ui ui;
    private final Parser parser;
    private final Save save;
    private final TaskList tasks;

    private String commandType;

    public Chad() {
        ui = new Ui();
        parser = new Parser();
        save = new Save(Paths.get("data/chad.txt"));

        TaskList loaded;
        try {
            loaded = new TaskList(save.load());
        } catch (ChadException e) {
            loaded = new TaskList();
            // keep this printed for CLI; GUI will just ignore it
            ui.printFileLoadingError();
        }
        tasks = loaded;
    }

    public String getGreeting() {
        return "Hello! I'm Chad.\nWhat can I do for you?";
    }

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
            parser.handle(trimmed, tasks, ui, save);
        } catch (ChadException e) {
            return "Error: " + e.getMessage();
        } finally {
            System.setOut(originalOut);
        }

        String out = baos.toString();
        assert out != null : "Captured output string should not be null";

        return cleanOutput(out);
    }

    public String getCommandType() {
        return commandType;
    }

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
            default:
                commandType = "Unknown";
                break;
        }
    }

    // Removes the separator lines + leading tabs so GUI looks nicer
    private String cleanOutput(String raw) {
        String[] lines = raw.replace("\r", "").split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            String t = line.trim();
            if (t.matches("_+")) { // skip Ui.printLine()
                continue;
            }
            // remove leading tabs used in CLI formatting
            sb.append(line.replaceFirst("^\\t+", "")).append("\n");
        }
        return sb.toString().trim();
    }
}
