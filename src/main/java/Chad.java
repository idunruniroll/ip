import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Chad {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Save save = new Save(Paths.get("data/chad.txt"));
        ArrayList<Task> taskList;

        try {
            taskList = save.load();
        } catch (ChadException e) {
            taskList = new ArrayList<>();
            System.out.println("___________________________________");
            System.out.println("Warning: could not load saved data. Starting fresh.");
            System.out.println("___________________________________");
        }
        int taskCounter = taskList.size();

        //intro
        String logo = """
               _               _ 
              | |             | |
           ___| |__   __ _  __| |
          / __| '_ \\ / _` |/ _` |
         | (__| | | | (_| | (_| |
          \\___|_| |_|\\__,_|\\__,_|
        """;
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
                    System.out.println("\t" + (i + 1) + ". " + taskList.get(i));
                }
                System.out.println("\t___________________________________");
                continue;
            }

            // mark task as done tracker
            if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;
                    taskList.get(index).markAsDone();
                    save.save(taskList); 

                    System.out.println("\t___________________________________");
                    System.out.println("\tNice! I've marked this task as done:");
                    System.out.println("\t  " + taskList.get(index));
                    System.out.println("\t___________________________________");

                } catch (ChadException e) {
                    printError("OOPS!!! Invalid task number for mark.");
                }
                continue;
            }

            // unmark task as not done tracker
            if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    taskList.get(index).markAsNotDone();
                    save.save(taskList); 

                    System.out.println("\t___________________________________");
                    System.out.println("\tOK, I've marked this task as not done yet:");
                    System.out.println("\t  " + taskList.get(index));
                    System.out.println("\t___________________________________");

                } catch (ChadException e) {
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
                Task t = new Todo(desc);
                taskList.add(t);
                taskCounter++;
                try {
                    save.save(taskList);
                } catch (ChadException e) {
                    printError("OOPS!!! Failed to save tasks.");
                }

                System.out.println("\t___________________________________");
                System.out.println("\tGot it. I've added this task:");
                System.out.println("\t  " + taskList.get(taskCounter - 1));
                System.out.println("\tNow you have " + taskCounter + " tasks in the list.");
                System.out.println("\t___________________________________");
                continue;
            }

            // deadline
            if (input.startsWith("deadline")) {
                try {
                    String[] parts = input.substring(9).split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new IllegalArgumentException();
                    }

                    Task t = new Deadline(parts[0].trim(), Date.inputDate(parts[1].trim()));
                    taskList.add(t);
                    taskCounter++;
                    save.save(taskList);

                    System.out.println("\t___________________________________");
                    System.out.println("\tGot it. I've added this task:");
                    System.out.println("\t  " + taskList.get(taskCounter - 1));
                    System.out.println("\tNow you have " + taskCounter + " tasks in the list.");
                    System.out.println("\t___________________________________");

                } catch (ChadException e) {
                    printError("OOPS!!! Deadline format: deadline <desc> /by <time>");
                }
                continue;
            }

            // event
            if (input.startsWith("event")) {
                try {
                    String[] parts = input.substring(5).trim().split(" /from ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty()) {
                        throw new IllegalArgumentException();
                    }

                    String[] times = parts[1].split(" /to ", 2);
                    if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty()) {
                        throw new IllegalArgumentException();
                    }


                    Task t = new Event(parts[0].trim(), Date.inputDate(times[0].trim()), Date.inputDate(times[1].trim()));
                    taskList.add(t);
                    taskCounter++;
                    save.save(taskList);

                    System.out.println("\t___________________________________");
                    System.out.println("\tGot it. I've added this task:");
                    System.out.println("\t  " + taskList.get(taskCounter - 1));
                    System.out.println("\tNow you have " + taskCounter + " tasks in the list.");
                    System.out.println("\t___________________________________");

                } catch (ChadException e) {
                    printError("OOPS!!! Event format: event <desc> /from <start> /to <end>");
                }
                continue;
            }

            // delete task
            if (input.startsWith("delete ")) {
                try {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (index < 0 || index >= taskCounter) {
                        throw new IllegalArgumentException();
                    }

                    Task removed = taskList.remove(index);
                    taskCounter--;
                    save.save(taskList);

                    System.out.println("\t___________________________________");
                    System.out.println("\tNoted. I've removed this task:");
                    System.out.println("\t  " + removed);
                    System.out.println("\tNow you have " + taskCounter + " tasks in the list.");
                    System.out.println("\t___________________________________");

                } catch (ChadException e) {
                    printError("Invalid task number for delete.");
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

