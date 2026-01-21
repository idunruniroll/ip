import java.util.Scanner;

public class Chad {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] taskList = new Task[100];
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
            }
            
            if (input.equals("list")) {
                System.out.println("\t___________________________________");
                for (int i = 0; i < taskCounter; i++) {
                    System.out.println("\t" + (i + 1) + ". " + taskList[i]);
                }
                System.out.println("\t___________________________________");
                continue;
            }

            if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1; // user uses 1-based indexing
                taskList[index].markAsDone();

                System.out.println("\t___________________________________");
                System.out.println("\tNice! I've marked this task as done:");
                System.out.println("\t  " + taskList[index]);
                System.out.println("\t___________________________________");
                continue;
            }

            if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                taskList[index].markAsNotDone();

                System.out.println("\t___________________________________");
                System.out.println("\tOK, I've marked this task as not done yet:");
                System.out.println("\t  " + taskList[index]);
                System.out.println("\t___________________________________");
                continue;
            }

            // add task tracker
            taskList[taskCounter] = new Task(input);
            taskCounter++;
            System.out.println("\t___________________________________");
            System.out.println("\tadded: " + input);
            System.out.println("\t___________________________________");
        }
        sc.close();
    }
}

