import java.nio.file.Paths;
// import java.util.ArrayList;
// import java.util.Scanner;

public class Chad {

    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();
        // Scanner sc = new Scanner(System.in);
        Save save = new Save(Paths.get("data/chad.txt"));
        TaskList taskList;

        try {
            taskList = new TaskList(save.load());
        } catch (ChadException e) {
            taskList = new TaskList();
            ui.printFileLoadingError();
            // More OOP TOREMOVE
            // System.out.println("___________________________________");
            // System.out.println("Warning: could not load saved data. Starting fresh.");
            // System.out.println("___________________________________");
        }

        //intro
        ui.intro();
        // More OOP TOREMOVE
        // String logo = """
        //        _               _ 
        //       | |             | |
        //    ___| |__   __ _  __| |
        //   / __| '_ \\ / _` |/ _` |
        //  | (__| | | | (_| | (_| |
        //   \\___|_| |_|\\__,_|\\__,_|
        // """;
        // System.out.println("___________________________________");
        // System.out.println("Hello! I'm Chad");
        // System.out.println("What can I do for you?");
        // System.out.println(logo);
        // System.out.println("___________________________________");

        // main loop
        while (true) {
            String input = ui.input();
            try {
                parser.handle(input, taskList, ui, save);
            } catch (ChadException e) {
                if (!e.getMessage().equals("NOT_HANDLED")) {
                    ui.printError(e.getMessage());
                    continue;
                }
            }            
            
    }

    
    // More OOP
    // error message printer TOREMOVE
    // private static void printError(String message) {
    //     System.out.println("\t___________________________________");
    //     System.out.println("\t" + message);
    //     System.out.println("\t___________________________________");
    // }

    }
}

