package dev.group.sortify.generator;

import dev.group.sortify.collection.StudentList;

public class RandomStudentStrategy implements StudentGenerationStrategy {
    @Override
    public StudentList generate() {
        return new StudentList();
    }
}

