package chad;

import java.util.Scanner;

/**
 * Handles user interaction such as reading commands and printing messages.
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class Ui {
    private final Scanner sc = new Scanner(System.in);

    /**
     * Prints the welcome message and ASCII logo shown at the start of the program.
     */
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

    /**
     * Prints a horizontal separator line used to format the console output.
     */
    public void printLine() {
        System.out.println("\t___________________________________");
    }

    /**
     * Prints an error message surrounded by separator lines.
     *
     * @param msg The error message to display.
     */
    public void printError(String msg) {
        printLine();
        System.out.println("\t" + msg);
        printLine();
    }

    /**
     * Prints a warning message indicating that saved data could not be loaded.
     */
    public void printFileLoadingError() {
        printLine();
        System.out.println("\tWarning: could not load saved data. Starting fresh.");
        printLine();
    }

    /**
     * Prints the goodbye message shown when the program exits.
     */
    public void exit() {
        printLine();
        System.out.println("\tBye. Hope to see you again soon!");
        printLine();
    }
}
