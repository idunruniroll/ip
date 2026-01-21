import java.util.Scanner;

public class Chad {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] taskList = new String[100];
        int taskCounter = 0;

        //intro
        String logo = "       _               _ \n"
                + "      | |             | |\n"
                + "   ___| |__   __ _  __| |\n"
                + "  / __| '_ \\ / _` |/ _` |\n"
                + " | (__| | | | (_| | (_| |\n"
                + "  \\___|_| |_|\\__,_|\\__,_|\n";
        System.out.println("___________________________________");
        System.out.println("Hello! I'm Chad");
        System.out.println("What can I do for you?");
        System.out.println(logo);
        System.out.println("___________________________________");

        // main loop
        while (true) {
            String input = sc.nextLine().toLowerCase().trim();   
            if (input.equals("bye")) {
                System.out.println("\t___________________________________");
                System.out.println("\tBye. Hope to see you again soon!");
                System.out.println("\t___________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("\t___________________________________");
                for (int i = 0; i < taskCounter; i++) {
                    System.out.println("\t" + (i + 1) + ". " + taskList[i]);
                }
                System.out.println("\t___________________________________");
                continue;
            }

            taskList[taskCounter] = input;
            taskCounter++;
            System.out.println("\t___________________________________");
            System.out.println("\tadded: " + input);
            System.out.println("\t___________________________________");
        }
        sc.close();
    }
}

