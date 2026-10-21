package dev.group.sortify.service;

import dev.group.sortify.collection.StudentList;
import dev.group.sortify.model.Student;
import dev.group.sortify.model.StudentValidator;
import dev.group.sortify.utils.ConsoleActions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileService {
    private static final Path PATH = Paths.get("data", "students.txt");

    private static final Pattern NAME_PATTERN = Pattern.compile("ФИО студента:\\s*(.*)");
    private static final Pattern AGE_PATTERN = Pattern.compile("Возраст:\\s*(.*)");
    private static final Pattern GROUP_PATTERN = Pattern.compile("Номер группы:\\s*(.*)");
    private static final Pattern GRADE_PATTERN = Pattern.compile("Средний балл:\\s*(.*)");
    private static final Pattern BOOK_PATTERN = Pattern.compile("Номер зачётной книжки:\\s*(.*)");

    public void saveToFile(StudentList students, String fileName) {
        if (students == null || students.size() == 0) {
            ConsoleActions.printInfo("Коллекция пуста, нет данных для записи");
            return;
        }

        Path targetPath = Path.of(fileName);
        List<String> linesToWrite = new ArrayList<>();

        Stream.of(students.toArray())
                .forEach(student -> {
                    linesToWrite.add("ФИО студента: " + student.getFullName());
                    linesToWrite.add("Возраст: " + student.getAge());
                    linesToWrite.add("Номер группы: " + student.getGroupNumber());
                    linesToWrite.add(String.format("Средний балл: %.1f", student.getGradePointAverage()));
                    linesToWrite.add("Номер зачётной книжки: " + student.getRecordBookNumber());
                    linesToWrite.add("");
                });

        try {
            if (targetPath.getParent() != null) {
                Files.createDirectories(targetPath.getParent());
            }

            Files.write(targetPath, linesToWrite, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            ConsoleActions.printInfo("Данные успешно дозаписаны в файл: " + targetPath);
            ConsoleActions.printInfo(String.format("Успешно записано студентов: %d.%n", students.size()));
        } catch (IOException e) {
            ConsoleActions.printError("Не удалось записать данные в файл: " + e.getMessage());
        }
    }

    public StudentList loadFromFile(String fileName) {
        Path targetPath = Path.of(fileName);

        if (!Files.exists(targetPath)) {
            ConsoleActions.printError("Файл не найден");
            return null;
        }

        try {
            String content = Files.readString(targetPath, StandardCharsets.UTF_8);

            return Stream.of(content.split("\\r?\\n\\r?\\n"))
                    .map(String::trim)
                    .filter(block -> !block.isEmpty())
                    .map(this::parseStudentFromBlock)
                    .filter(student -> student != null)
                    .collect(StudentList.toStudentList());

        } catch (IOException e) {
            ConsoleActions.printError("Не удалось прочитать файл: " + e.getMessage());
            return new StudentList();
        }
    }

    private Student parseStudentFromBlock(String block) {
        String name = extractValue(block, NAME_PATTERN);
        String ageStr = extractValue(block, AGE_PATTERN);
        String groupStr = extractValue(block, GROUP_PATTERN);
        String gradeStr = extractValue(block, GRADE_PATTERN);
        String book = extractValue(block, BOOK_PATTERN);

        if (name == null || ageStr == null || groupStr == null || gradeStr == null || book == null) {
            return null;
        }

        try {
            int age = Integer.parseInt(ageStr);
            int groupNumber = Integer.parseInt(groupStr);
            double gradePointAverage = Double.parseDouble(gradeStr);

            if (StudentValidator.validateFullName(name) &&
                    StudentValidator.validateAge(age) &&
                    StudentValidator.validateGroup(groupNumber) &&
                    StudentValidator.validateGrade(gradePointAverage) &&
                    StudentValidator.validateRecordBook(book)) {

                return new Student.Builder()
                        .fullName(name)
                        .age(age)
                        .groupNumber(groupNumber)
                        .gradePointAverage(gradePointAverage)
                        .recordBookNumber(book)
                        .build();
            }
        } catch (NumberFormatException ignored) {

        }
        return null;
    }

    private String extractValue(String block, Pattern pattern) {
        Matcher matcher = pattern.matcher(block);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}
