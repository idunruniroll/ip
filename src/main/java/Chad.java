import java.util.Scanner;

public class Chad {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String logo = "       _               _ \n"
                + "      | |             | |\n"
                + "   ___| |__   __ _  __| |\n"
                + "  / __| '_ \\ / _` |/ _` |\n"
                + " | (__| | | | (_| | (_| |\n"
                + "  \\___|_| |_|\\__,_|\\__,_|\n";
        System.out.println("___________________________________");
        System.out.println("Hello! I'm Chad");
        System.out.println("What can I do for you?");
        System.out.println("___________________________________");
        System.out.println(logo);

        while (true) {
            String input = sc.nextLine().toLowerCase().trim();   
            if (input.equals("bye")) {
                System.out.println("\t___________________________________");
                System.out.println("\tBye. Hope to see you again soon!");
                System.out.println("\t___________________________________");
                break;
            }
            System.out.println("\t___________________________________");
            System.out.println("\t"+input);
            System.out.println("\t___________________________________");
        }
        sc.close();
    }
}

