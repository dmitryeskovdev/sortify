package dev.group.sortify.strategy;

import dev.group.sortify.model.Student;

import java.util.List;

public interface StudentGenerationStrategy {
    List<Student> generate();
}
