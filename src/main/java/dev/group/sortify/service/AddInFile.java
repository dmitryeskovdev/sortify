package dev.group.sortify.service;

import dev.group.sortify.model.Student;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class AddInFile {
    private List<Student> students;
    private Path path = Paths.get("data/test.txt");

    public AddInFile(List<Student> students) {
        this.students = students;
    }
    public void writeFile(){
        List<String> st = students.stream().map(Student::toString).collect(Collectors.toList());
        try {
            Files.write(path,st, StandardCharsets.UTF_8);
        }
        catch (IOException e){
            System.out.println("Ошибка записи: " + e.getMessage());
        }
    }
}