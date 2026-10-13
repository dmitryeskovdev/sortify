package dev.group.sortify.strategy;

import dev.group.sortify.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleStudentStrategy implements StudentGenerationStrategy {
    private final Scanner scanner;

    public ConsoleStudentStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<Student> generate() {
        List<Student> result = new ArrayList<>();

        // Вся твоя логика ввода переносится сюда
        String fio;
        while (true) {
            System.out.print("Введите ФИО > ");
            if (!scanner.hasNextLine()) return result;
            fio = scanner.nextLine().trim();
            if (fio.isEmpty()) {
                System.out.println("ФИО не может быть пустым.");
                continue;
            }
            if (!fio.matches("^[А-Яа-яЁё\\s]+$")) {
                System.out.println("Только буквы и пробелы.");
                continue;
            }
            break;
        }

        int numGroup;
        while (true) {
            System.out.print("Номер группы > ");
            if (!scanner.hasNextLine()) return result;
            try {
                numGroup = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Это не число.");
            }
        }

        double averageScore;
        while (true) {
            System.out.print("Средний балл > ");
            if (!scanner.hasNextLine()) return result;
            try {
                averageScore = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Это не число.");
            }
        }

        int studentIdNumber;
        while (true) {
            System.out.print("Номер зачётной книжки > ");
            if (!scanner.hasNextLine()) return result;
            try {
                studentIdNumber = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Это не число.");
            }
        }

        result.add(new Student.Builder()
                .fio(fio)
                .numGroup(numGroup)
                .averageScore(averageScore)
                .studentIdNumber(studentIdNumber)
                .build());

        return result;
    }
}
