package chad;

import java.nio.file.Paths;

/**
 * Entry point of the Chad chatbot application.
 * Sets up the UI, parser, storage, and task list, then runs the main input
 * loop.
 * 
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class Chad {

    /**
     * Starts the chatbot.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();
        Save save = new Save(Paths.get("data/chad.txt"));
        TaskList taskList;

        try {
            taskList = new TaskList(save.load());
        } catch (ChadException e) {
            taskList = new TaskList();
            ui.printFileLoadingError();
        }

        // intro
        ui.intro();

        // main loop
        while (true) {
            String input = ui.input();
            try {
                parser.handle(input, taskList, ui, save);
            } catch (ChadException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    public String getGreeting() {
        return "Hello! I'm Chad.\nWhat can I do for you?";
    }

    public String getResponse(String input) {
        if (input.trim().equalsIgnoreCase("bye")) {
            return "Bye. See you next time!";
        }
        return "Chad heard: " + input;
    }

}
