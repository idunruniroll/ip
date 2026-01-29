public class Parser {

    public void handle(String input, TaskList taskList, Ui ui, Save save) throws ChadException {
        if (input.equals("bye")) {
            ui.exit();
            System.exit(0);
        }

         else if (input.equals("list")) {
            ui.printLine();

            if (taskList.size() == 0) {
                System.out.println("\tYour list is empty.");
            } else {
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println("\t" + (i + 1) + ". " + taskList.get(i));
                }
            }

            ui.printLine();
        }

        else if (input.equals("mark") || input.startsWith("mark ")) {
            String numberCheck = input.substring(4).trim();

            if (numberCheck.isEmpty()) {
                ui.printError("OOPS!!! Mark format: mark <task number>");
                return;
            }

            try {
                int index = Integer.parseInt(numberCheck) - 1;
                taskList.get(index).markAsDone();
                save.save(taskList.getTasks());

                ui.printLine();
                System.out.println("\tNice! I've marked this task as done:");
                System.out.println("\t  " + taskList.get(index));
                ui.printLine();

            } catch (NumberFormatException e) {
                ui.printError("OOPS!!! Mark format: mark <task number>");
            } catch (ChadException e) {
                ui.printError("OOPS!!! Invalid task number for mark.");
            }
        }

        else if (input.equals("unmark") || input.startsWith("unmark ")) {
            String numberCheck = input.substring(6).trim();

            if (numberCheck.isEmpty()) {
                ui.printError("OOPS!!! Unmark format: unmark <task number>");
                return;
            }

            try {
                int index = Integer.parseInt(numberCheck) - 1;
                taskList.get(index).markAsNotDone();
                save.save(taskList.getTasks());

                ui.printLine();
                System.out.println("\tOK, I've marked this task as not done yet:");
                System.out.println("\t  " + taskList.get(index));
                ui.printLine();

            } catch (NumberFormatException e) {
                ui.printError("OOPS!!! Unmark format: unmark <task number>");
            } catch (ChadException e) {
                ui.printError("OOPS!!! Invalid task number for unmark.");
            }
        }


        else if (input.startsWith("todo")) {
            if (input.length() <= 4 || input.substring(4).trim().isEmpty()) {
                ui.printError("OOPS!!! The description of a todo cannot be empty.");
                return;
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

        else if (input.equals("deadline") || input.startsWith("deadline ")) {
            try {
                String numberCheck = input.substring(8).trim();
                if (numberCheck.isEmpty()) {
                    throw new ChadException("OOPS!!! Deadline format: deadline <desc> /by <time>");
                }

                String[] parts = numberCheck.split("\\s+/by\\s+", 2);
                if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    throw new ChadException("OOPS!!! Deadline format: deadline <desc> /by <time>");
                }

                Task t = new Deadline(parts[0].trim(), Date.inputDate(parts[1].trim()));
                taskList.add(t);
                save.save(taskList.getTasks());

                ui.printLine();
                System.out.println("\tGot it. I've added this task:");
                System.out.println("\t  " + t);
                System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
                ui.printLine();

            } catch (ChadException e) {
                ui.printError(e.getMessage());
            }
        }


        else if (input.startsWith("event")) {
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

        else if (input.startsWith("delete")) {
            String numberCheck = input.substring(6).trim();

            if (numberCheck.isEmpty()) {
                ui.printError("OOPS!!! Please specify which task to delete. Delete format: delete <task number>");
                return;
            }

            try {
                int index = Integer.parseInt(numberCheck) - 1;
                Task removed = taskList.remove(index);
                save.save(taskList.getTasks());

                ui.printLine();
                System.out.println("\tNoted. I've removed this task:");
                System.out.println("\t  " + removed);
                System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
                ui.printLine();

            } catch (NumberFormatException e) {
                ui.printError("OOPS!!! Delete format: delete <task number>");
            } catch (ChadException e) {
                ui.printError(e.getMessage());
            }
        }

        else {
            ui.printError("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }     
    }
}
