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
            String input = sc.nextLine().trim();   
            if (input.equals("bye")) {
                System.out.println("\t___________________________________");
                System.out.println("\tBye. Hope to see you again soon!");
                System.out.println("\t___________________________________");
                break;
            }
            
            // list tasks
            if (input.equals("list")) {
                System.out.println("\t___________________________________");
                for (int i = 0; i < taskCounter; i++) {
                    System.out.println("\t" + (i + 1) + ". " + taskList[i]);
                }
                System.out.println("\t___________________________________");
                continue;
            }

            // mark task as done tracker
            if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1; // user uses 1-based indexing
                taskList[index].markAsDone();

                System.out.println("\t___________________________________");
                System.out.println("\tNice! I've marked this task as done:");
                System.out.println("\t  " + taskList[index]);
                System.out.println("\t___________________________________");
                continue;
            }

            // unmark task as not done tracker
            if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                taskList[index].markAsNotDone();

                System.out.println("\t___________________________________");
                System.out.println("\tOK, I've marked this task as not done yet:");
                System.out.println("\t  " + taskList[index]);
                System.out.println("\t___________________________________");
                continue;
            }

            // todo
            if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                taskList[taskCounter++] = new Todo(desc);

                System.out.println("\t___________________________________");
                System.out.println("\tGot it. I've added this task:");
                System.out.println("\t  " + taskList[taskCounter - 1]);
                System.out.println("\tNow you have " + taskCounter + " tasks in the list.");
                System.out.println("\t___________________________________");
                continue;
            }

            // deadline
            if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ", 2);
                String desc = parts[0].trim();
                String doneBy = parts[1].trim();

                taskList[taskCounter++] = new Deadline(desc, doneBy);

                System.out.println("\t___________________________________");
                System.out.println("\tGot it. I've added this task:");
                System.out.println("\t  " + taskList[taskCounter - 1]);
                System.out.println("\tNow you have " + taskCounter + " tasks in the list.");
                System.out.println("\t___________________________________");
                continue;
            }

            // event
            if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from ", 2);
                String desc = parts[0].trim();

                String[] times = parts[1].split(" /to ", 2);
                String from = times[0].trim();
                String to = times[1].trim();

                taskList[taskCounter++] = new Event(desc, from, to);

                System.out.println("\t___________________________________");
                System.out.println("\tGot it. I've added this task:");
                System.out.println("\t  " + taskList[taskCounter - 1]);
                System.out.println("\tNow you have " + taskCounter + " tasks in the list.");
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

