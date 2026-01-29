package chad;

import java.nio.file.Paths;

public class Chad {

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

        //intro
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
}

