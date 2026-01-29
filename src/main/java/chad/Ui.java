package chad;

import java.util.Scanner;

/**
 * Handles user interaction such as reading commands and printing messages.
 * 
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class Ui {
    private final Scanner sc = new Scanner(System.in);

    public void intro() {
        String logo = """
                       _               _
                      | |             | |
                   ___| |__   __ _  __| |
                  / __| '_ \\ / _` |/ _` |
                 | (__| | | | (_| | (_| |
                  \\___|_| |_|\\__,_|\\__,_|
                """;

        printLine();
        System.out.println("Hello! I'm Chad");
        System.out.println("What can I do for you?");
        System.out.println(logo);
        printLine();
    }

    /**
     * Reads the next command from the user.
     *
     * @return trimmed command string entered by the user.
     */
    public String input() {
        if (sc.hasNextLine()) {
            return sc.nextLine().trim();
        } else {
            return null;
        }
    }

    public void printLine() {
        System.out.println("\t___________________________________");
    }

    public void printError(String msg) {
        printLine();
        System.out.println("\t" + msg);
        printLine();
    }

    public void printFileLoadingError() {
        printLine();
        System.out.println("\tWarning: could not load saved data. Starting fresh.");
        printLine();
    }

    public void exit() {
        printLine();
        System.out.println("\tBye. Hope to see you again soon!");
        printLine();
    }
}
