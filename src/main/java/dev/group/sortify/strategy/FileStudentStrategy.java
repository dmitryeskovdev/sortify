package dev.group.sortify.strategy;

import dev.group.sortify.model.Student;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStudentStrategy implements StudentGenerationStrategy{
    private final Scanner scanner;
    public FileStudentStrategy(Scanner scanner) {
        this.scanner = scanner;
    }
    @Override
    public List<Student> generate() {
        System.out.print("Введите название файла (без расширения .txt): > ");


        String nameFile;
        if (!scanner.hasNextLine()) {
            return new ArrayList<>();
        }
        nameFile = scanner.nextLine().trim();

        if (nameFile.isEmpty()) {
            System.out.println("Имя файла не введено.");
            return new ArrayList<>();
        }

        String filePath = "data/" + nameFile + ".txt";
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Файл не найден: " + filePath);
            return new ArrayList<>();
        }

        List<Student> result = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.startsWith("Студент:")) {
                    String fio = line.split(":", 2)[1].trim();


                    if (!fileScanner.hasNextLine()) break;
                    int numGroup = Integer.parseInt(fileScanner.nextLine().split(":", 2)[1].trim());

                    if (!fileScanner.hasNextLine()) break;
                    double averageScore = Double.parseDouble(fileScanner.nextLine().split(":", 2)[1].trim());

                    if (!fileScanner.hasNextLine()) break;
                    int studentIdNumber = Integer.parseInt(fileScanner.nextLine().split(":", 2)[1].trim());

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

