package dev.group.sortify;

import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Map;

public class CLI {
    private final Map<String, Consumer<String>> commands = new HashMap<>();

    public CLI() {
        commands.put("print", this::handlePrint);
        commands.put("add", this::handleAdd);
        commands.put("write", this::handleWrite);
        commands.put("sort", this::handleSort);
        commands.put("help", this::handleHelp);
    }

    public Map<String, Consumer<String>> getCommands() {
        return commands;
    }

    private void handlePrint(String command) {
        System.out.println("Вызов " + command);
    }

    private void handleAdd(String command) {
        System.out.println("Вызов " + command);
    }

    private void handleWrite(String command) {
        System.out.println("Вызов " + command);
    }

    private void handleSort(String command) {
        System.out.println("Вызов " + command);
    }

    private void handleHelp(String command) {
        System.out.println("Команды:\n" +
                "   print       - выводит список студентов;\n" +
                "   add         - добавить список студентов;\n" +
                "   write       - сохранение списка студентов;\n" +
                "   sort        - сортировка списка студентов;\n" +
                "   help        - выводит список команд;\n" +
                "   exit        - выход;"
        );
    }

    public static void handleWelcome() {
        System.out.println("Sortify CLI");
        System.out.println("Для вывода всех команд на экран воспользуйтесь командой 'help'");
        System.out.println("Для выхода введите 'exit'");
    }

}

class ConsoleColors {
    public static final String RESET = "\033[0m";
    public static final String CYAN_BRIGHT = "\033[0;96m";
}
