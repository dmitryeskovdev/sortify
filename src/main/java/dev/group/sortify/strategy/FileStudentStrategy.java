package dev.group.sortify.strategy;

import dev.group.sortify.model.Student;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStudentStrategy implements StudentGenerationStrategy{
    private final String filePath;

    public FileStudentStrategy(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public List<Student> generate() {
        List<Student> result = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Файл не найден: " + filePath);
            return result; // Возвращаем пустой список, не кидаем исключение
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.startsWith("Студент:")) {
                    String fio = line.split(":", 2)[1].trim();

                    // Проверка наличия следующих строк внутри цикла
                    if (!scanner.hasNextLine()) break;
                    int numGroup = Integer.parseInt(scanner.nextLine().split(":", 2)[1].trim());

                    if (!scanner.hasNextLine()) break;
                    double averageScore = Double.parseDouble(scanner.nextLine().split(":", 2)[1].trim());

                    if (!scanner.hasNextLine()) break;
                    int studentIdNumber = Integer.parseInt(scanner.nextLine().split(":", 2)[1].trim());

                    result.add(new Student.Builder()
                            .fio(fio)
                            .numGroup(numGroup)
                            .averageScore(averageScore)
                            .studentIdNumber(studentIdNumber)
                            .build());
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}

