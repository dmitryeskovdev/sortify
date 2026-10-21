package dev.group.sortify;

import dev.group.sortify.utils.ConsoleActions;
import dev.group.sortify.utils.ConsoleColors;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String STOP_WORD = "exit";
    private static final String CLEAR_WORD = "clear";

    public static void main(String[] args) {

        CLI.handleWelcome();
        CLI cli = new CLI(scanner);
        Map<String, Consumer<String>> commands = cli.getCommands();

        while (true) {
            System.out.print(ConsoleColors.CYAN_BRIGHT + "sortify$ " + ConsoleColors.RESET);
            String line = scanner.nextLine().trim().toLowerCase();

            if (line.equalsIgnoreCase(STOP_WORD)) {
                ConsoleActions.printInfo("Программа завершена.");
                break;
            }

            if (line.isEmpty()) {
                continue;
            }

            if (line.equals(CLEAR_WORD)) {
                ConsoleActions.clearConsole();
                continue;
            }

            Consumer<String> action = commands.get(line);

            if (action != null) {
                action.accept(line);
            } else {
                ConsoleActions.printError("Неизвестная команда: " + line + ". Попробуйте снова.");
            }
        }

        scanner.close();
    }

}
