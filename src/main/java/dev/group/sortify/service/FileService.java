package dev.group.sortify.service;

import dev.group.sortify.collection.StudentList;
import dev.group.sortify.model.Student;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    private static final String DELIMITER = ";";

    public void save(StudentList students, String fileName) throws IOException {
        if (students == null) {
            throw new IllegalArgumentException("Коллекция не может быть null");
        }
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Имя файла не может быть пустым");
        }

        Path path = Paths.get(fileName);

        List<String> lines = students.stream()
                .map(this::toLine)
                .collect(Collectors.toList());

        Files.write(
                path,
                lines,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public StudentList load(String fileName) throws IOException {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Имя файла не может быть пустым");
        }

        Path path = Paths.get(fileName);
        StudentList result = new StudentList();

        if (!Files.exists(path)) {
            return result;
        }

        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.filter(line -> !line.isBlank())
                    .map(this::parseLine)
                    .forEach(result::add);
        }

        return result;
    }

    private String toLine(Student s) {
        return s.getFio() + DELIMITER
                + s.getNumGroup() + DELIMITER
                + s.getAverageScore();
    }

    private Student parseLine(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректная строка: " + line);
        }
        return new Student.Builder()
                .fio(parts[0].trim())
                .numGroup(Integer.parseInt(parts[1].trim()))
                .averageScore(Double.parseDouble(parts[2].trim()))
                .build();
    }
}