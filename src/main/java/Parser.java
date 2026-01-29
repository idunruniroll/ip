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

        // everything else is still handled in Chad.java
        throw new ChadException("NOT_HANDLED");
    }
}
