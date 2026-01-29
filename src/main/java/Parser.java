public class Parser {

    public void handle(String input, TaskList tasks, Ui ui, Save save) throws ChadException {
        if (input.equals("bye")) {
            ui.exit();
            System.exit(0);
        }

        // everything else is still handled in Chad.java
        throw new ChadException("NOT_HANDLED");
    }
}
