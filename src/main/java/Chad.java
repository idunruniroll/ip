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
            // Parser MoreOOP TOREMOVE
            // if (input.equals("bye")) {
            //     ui.exit();
            //     break;
            //     // More OOP TOREMOVE
            //     // System.out.println("\t___________________________________");
            //     // System.out.println("\tBye. Hope to see you again soon!");
            //     // System.out.println("\t___________________________________");
            //     // break;
            // }
            
            // Parser MoreOOP TOREMOVE
            // list tasks
            // if (input.equals("list")) {
            //     try {
            //         ui.printLine();
            //         if (taskList.size() == 0) {
            //             System.out.println("\tYour list is empty.");
            //         } else {
            //             for (int i = 0; i < taskList.size(); i++) {
            //                 System.out.println("\t" + (i + 1) + ". " + taskList.get(i));
            //             }
            //         }
            //         ui.printLine();
            //     } catch (ChadException e) {
            //         ui.printError("OOPS!!! Error retrieving tasks.");
            //     }
            //     continue;
            // }

            // Parser MoreOOP TOREMOVE
            // mark task as done tracker
            // if (input.startsWith("mark ")) {
            //     try {
            //         int index = Integer.parseInt(input.substring(5).trim()) - 1;
            //         taskList.get(index).markAsDone();
            //         save.save(taskList.getTasks()); 

            //         ui.printLine();
            //         System.out.println("\tNice! I've marked this task as done:");
            //         System.out.println("\t  " + taskList.get(index));
            //         ui.printLine();

            //     } catch (ChadException e) {
            //         ui.printError("OOPS!!! Invalid task number for mark.");
            //     }
            //     continue;
            // }


             // Parser MoreOOP TOREMOVE
            // unmark task as not done tracker
            // if (input.startsWith("unmark ")) {
            //     try {
            //         int index = Integer.parseInt(input.substring(7).trim()) - 1;
            //         taskList.get(index).markAsNotDone();
            //         save.save(taskList.getTasks()); 

            //         ui.printLine();
            //         System.out.println("\tOK, I've marked this task as not done yet:");
            //         System.out.println("\t  " + taskList.get(index));
            //         ui.printLine();

            //     } catch (ChadException e) {
            //         ui.printError("OOPS!!! Invalid task number for unmark.");
            //     }
            //     continue;
            // }

            // Parser MoreOOP TOREMOVE
            // todo
            // if (input.startsWith("todo")) {
            //     if (input.length() <= 4) {
            //         ui.printError("OOPS!!! The description of a todo cannot be empty.");
            //         continue;
            //     }

            //     String desc = input.substring(5).trim();
            //     Task task = new Todo(desc);
            //     taskList.add(task);
            //     try {
            //         save.save(taskList.getTasks());
            //     } catch (ChadException e) {
            //         ui.printError("OOPS!!! Failed to save tasks.");
            //     }

            //     ui.printLine();
            //     System.out.println("\tGot it. I've added this task:");
            //     System.out.println("\t  " + task);
            //     System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
            //     ui.printLine();
            //     continue;
            // }

            // deadline
            // if (input.startsWith("deadline")) {
            //     try {
            //         String[] parts = input.substring(9).split(" /by ", 2);
            //         if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            //             throw new ChadException("OOPS!!! Deadline format: deadline <desc> /by <time>");
            //         }

            //         Task t = new Deadline(parts[0].trim(), Date.inputDate(parts[1].trim()));
            //         taskList.add(t);

            //         save.save(taskList.getTasks());

            //         ui.printLine();
            //         System.out.println("\tGot it. I've added this task:");
            //         System.out.println("\t  " + taskList.get(taskList.size() - 1));
            //         System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
            //         ui.printLine();

            //     } catch (ChadException e) {
            //         ui.printError("OOPS!!! Deadline format: deadline <desc> /by <time>");
            //     }
            //     continue;
            // }

            // event
            // if (input.startsWith("event")) {
            //     try {
            //         String[] parts = input.substring(5).trim().split(" /from ", 2);
            //         if (parts.length < 2 || parts[0].trim().isEmpty()) {
            //             throw new ChadException("OOPS!!! Event format: event <desc> /from <start> /to <end>");
            //         }

            //         String[] times = parts[1].split(" /to ", 2);
            //         if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty()) {
            //             throw new ChadException("OOPS!!! Event format: event <desc> /from <start> /to <end>");
            //         }


            //         Task t = new Event(parts[0].trim(), Date.inputDate(times[0].trim()), Date.inputDate(times[1].trim()));
            //         taskList.add(t);
            //         save.save(taskList.getTasks());

            //         ui.printLine();
            //         System.out.println("\tGot it. I've added this task:");
            //         System.out.println("\t  " + taskList.get(taskList.size() - 1));
            //         System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
            //         ui.printLine();

            //     } catch (ChadException e) {
            //         ui.printError("OOPS!!! Event format: event <desc> /from <start> /to <end>");
            //     }
            //     continue;
            // }

            // delete task
            // if (input.startsWith("delete ")) {
            //     try {
            //         int index = Integer.parseInt(input.substring(7).trim()) - 1;
            //         if (index < 0 || index >= taskList.size()) {
            //             throw new ChadException("Invalid task number for delete.");
            //         }

            //         Task removed = taskList.remove(index);
            //         save.save(taskList.getTasks());

            //         ui.printLine();
            //         System.out.println("\tNoted. I've removed this task:");
            //         System.out.println("\t  " + removed);
            //         System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
            //         ui.printLine();

            //     } catch (ChadException e) {
            //         ui.printError("Invalid task number for delete.");
            //     }
            //     continue;
            // }

            // Remove comment when PARSER is completed
            // ui.printError("OOPS!!! I'm sorry, but I don't know what that means :-(");

            // Remove as tasks are added based on TODO/Event/Deadline.
            // // add task tracker
            // taskList[taskCounter] = new Task(input);
            // taskCounter++;
            // System.out.println("\t___________________________________");
            // System.out.println("\tadded: " + input);
            // System.out.println("\t___________________________________");
        }
        // sc.close();
    }

    
    // More OOP
    // error message printer TOREMOVE
    // private static void printError(String message) {
    //     System.out.println("\t___________________________________");
    //     System.out.println("\t" + message);
    //     System.out.println("\t___________________________________");
    // }

}

