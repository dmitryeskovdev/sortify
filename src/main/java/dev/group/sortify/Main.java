package dev.group.sortify;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.Map;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String STOP_WORD = "exit";

    public static void main(String[] args) {

        CLI.handleWelcome();
        CLI cli = new CLI();
        Map<String, Consumer<String>> commands = cli.getCommands();

        while (true) {
            System.out.print(ConsoleColors.CYAN_BRIGHT + "sortify$ " + ConsoleColors.RESET);
            String line = scanner.nextLine().trim().toLowerCase();

            if (line.equalsIgnoreCase(STOP_WORD)) {
                System.out.println("Программа завершена.");
                break;
            }

            if (line.isEmpty()) {
                continue;
            }

            Consumer<String> action = commands.get(line);

            if (action != null) {
                action.accept(line);
            } else {
                System.out.println("Неизвестная команда: " + line + ". Попробуйте снова.");
            }
        }

        scanner.close();
    }

}
