package dev.group.sortify.service;

import dev.group.sortify.model.Student;
import dev.group.sortify.strategy.StudentGenerationStrategy;

import java.util.ArrayList;
import java.util.List;

public class StudentAdd {
    private final List<Student> students = new ArrayList<>();
    private StudentGenerationStrategy strategy;

    public StudentAdd(StudentGenerationStrategy strategy){
        this.strategy = strategy;
    }

    public void setStrategy(StudentGenerationStrategy strategy){
        this.strategy = strategy;
    }
    public void addStudents(){
        if (strategy == null){
            throw new IllegalStateException("Стратегия не установлена!");
        }

        List<Student> newStudents = strategy.generate();
        students.addAll(newStudents);
        System.out.println("Добавлено " + newStudents.size() + " студентов.");
    }

    public List<Student> getStudents(){
        return students;
    }
}
