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
        while (sc.hasNextLine()) {
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
                try {
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;
                    taskList[index].markAsDone();

                    System.out.println("\t___________________________________");
                    System.out.println("\tNice! I've marked this task as done:");
                    System.out.println("\t  " + taskList[index]);
                    System.out.println("\t___________________________________");

                } catch (Exception e) {
                    printError("OOPS!!! Invalid task number for mark.");
                }
                continue;
            }

            // unmark task as not done tracker
            if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    taskList[index].markAsNotDone();

                    System.out.println("\t___________________________________");
                    System.out.println("\tOK, I've marked this task as not done yet:");
                    System.out.println("\t  " + taskList[index]);
                    System.out.println("\t___________________________________");

                } catch (Exception e) {
                    printError("OOPS!!! Invalid task number for unmark.");
                }
                continue;
            }
            // todo
            if (input.startsWith("todo")) {
                if (input.length() <= 4) {
                    printError("OOPS!!! The description of a todo cannot be empty.");
                    continue;
                }

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
            if (input.startsWith("deadline")) {
                try {
                    String[] parts = input.substring(9).split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty()) {
                        throw new IllegalArgumentException();
                    }

                    taskList[taskCounter++] = new Deadline(parts[0].trim(), parts[1].trim());
                    System.out.println("\t___________________________________");
                    System.out.println("\tGot it. I've added this task:");
                    System.out.println("\t  " + taskList[taskCounter - 1]);
                    System.out.println("\tNow you have " + taskCounter + " tasks in the list.");
                    System.out.println("\t___________________________________");

                } catch (Exception e) {
                    printError("OOPS!!! Deadline format: deadline <desc> /by <time>");
                }
                continue;
            }

            // event
            if (input.startsWith("event")) {
                try {
                    String[] parts = input.substring(6).split(" /from ", 2);
                    String[] times = parts[1].split(" /to ", 2);

                    taskList[taskCounter++] = new Event(parts[0].trim(), times[0].trim(), times[1].trim());

                    System.out.println("\t___________________________________");
                    System.out.println("\tGot it. I've added this task:");
                    System.out.println("\t  " + taskList[taskCounter - 1]);
                    System.out.println("\tNow you have " + taskCounter + " tasks in the list.");
                    System.out.println("\t___________________________________");

                } catch (Exception e) {
                    printError("OOPS!!! Event format: event <desc> /from <start> /to <end>");
                }
                continue;
            }
            
            printError("OOPS!!! I'm sorry, but I don't know what that means :-(");

            // Remove as tasks are added based on TODO/Event/Deadline.
            // // add task tracker
            // taskList[taskCounter] = new Task(input);
            // taskCounter++;
            // System.out.println("\t___________________________________");
            // System.out.println("\tadded: " + input);
            // System.out.println("\t___________________________________");
        }
        sc.close();
    }

    // error message printer
    private static void printError(String message) {
        System.out.println("\t___________________________________");
        System.out.println("\t" + message);
        System.out.println("\t___________________________________");
    }

}

