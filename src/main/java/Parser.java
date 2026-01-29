public class Parser {

    public void handle(String input, TaskList taskList, Ui ui, Save save) throws ChadException {
        if (input.equals("bye")) {
            ui.exit();
            System.exit(0);
        }

        if (input.equals("list")) {
            ui.printLine();

            if (taskList.size() == 0) {
                System.out.println("\tYour list is empty.");
            } else {
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println("\t" + (i + 1) + ". " + taskList.get(i));
                }
            }

            ui.printLine();
            return;
        }

        if (input.startsWith("mark ")) {
            try {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                taskList.get(index).markAsDone();
                save.save(taskList.getTasks()); 
                ui.printLine();
                System.out.println("\tNice! I've marked this task as done:");
                System.out.println("\t  " + taskList.get(index));
                ui.printLine();
            } catch (ChadException e) {
                ui.printError("OOPS!!! Invalid task number for mark.");
            }
        }

        if (input.startsWith("unmark ")) {
            try {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                taskList.get(index).markAsNotDone();
                save.save(taskList.getTasks()); 

                ui.printLine();
                System.out.println("\tOK, I've marked this task as not done yet:");
                System.out.println("\t  " + taskList.get(index));
                ui.printLine();

            } catch (ChadException e) {
                ui.printError("OOPS!!! Invalid task number for unmark.");
            }
        }

        if (input.startsWith("todo")) {
            if (input.length() <= 4) {
                ui.printError("OOPS!!! The description of a todo cannot be empty.");
            }

            String desc = input.substring(5).trim();
            Task task = new Todo(desc);
            taskList.add(task);
            try {
                save.save(taskList.getTasks());
            } catch (ChadException e) {
                ui.printError("OOPS!!! Failed to save tasks.");
            }

            ui.printLine();
            System.out.println("\tGot it. I've added this task:");
            System.out.println("\t  " + task);
            System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
            ui.printLine();
        }

        if (input.startsWith("deadline")) {
            try {
                String[] parts = input.substring(9).split(" /by ", 2);
                if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    throw new ChadException("OOPS!!! Deadline format: deadline <desc> /by <time>");
                }

                Task t = new Deadline(parts[0].trim(), Date.inputDate(parts[1].trim()));
                taskList.add(t);

                save.save(taskList.getTasks());

                ui.printLine();
                System.out.println("\tGot it. I've added this task:");
                System.out.println("\t  " + taskList.get(taskList.size() - 1));
                System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
                ui.printLine();

            } catch (ChadException e) {
                ui.printError("OOPS!!! Deadline format: deadline <desc> /by <time>");
            }
        }

        if (input.startsWith("event")) {
            try {
                String[] parts = input.substring(5).trim().split(" /from ", 2);
                if (parts.length < 2 || parts[0].trim().isEmpty()) {
                    throw new ChadException("OOPS!!! Event format: event <desc> /from <start> /to <end>");
                }

                String[] times = parts[1].split(" /to ", 2);
                if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty()) {
                    throw new ChadException("OOPS!!! Event format: event <desc> /from <start> /to <end>");
                }


                Task t = new Event(parts[0].trim(), Date.inputDate(times[0].trim()), Date.inputDate(times[1].trim()));
                taskList.add(t);
                save.save(taskList.getTasks());

                ui.printLine();
                System.out.println("\tGot it. I've added this task:");
                System.out.println("\t  " + taskList.get(taskList.size() - 1));
                System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
                ui.printLine();

            } catch (ChadException e) {
                ui.printError("OOPS!!! Event format: event <desc> /from <start> /to <end>");
            }
        }

        // everything else is still handled in Chad.java
        throw new ChadException("NOT_HANDLED");
    }
}
