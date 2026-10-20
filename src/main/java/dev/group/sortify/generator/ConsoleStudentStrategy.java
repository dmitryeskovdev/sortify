package dev.group.sortify.generator;

import dev.group.sortify.collection.StudentList;

public class ConsoleStudentStrategy implements StudentGenerationStrategy {
    @Override
    public StudentList generate() {
        return new StudentList();
    }
}

