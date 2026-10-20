package dev.group.sortify.utils;

public class ConsoleActions {
    public static void printError(String error) {
        System.out.println(ConsoleColors.RED_BRIGHT + "ОШИБКА: " + error + ConsoleColors.RESET);
    }

    public static void printInfo(String info) {
        System.out.println(ConsoleColors.YELLOW_BRIGHT + "ИНФО: " + info + ConsoleColors.RESET);
    }

    public static void printAction(String action) {
        System.out.print(ConsoleColors.CYAN_BRIGHT + action + ConsoleColors.RESET);
    }

    public static void clearConsole() {
        for (int i = 0; i < 64; i++) {
            System.out.println();
        }
    }
}
