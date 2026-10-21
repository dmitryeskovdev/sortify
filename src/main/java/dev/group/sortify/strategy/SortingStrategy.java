package dev.group.sortify.strategy;

import dev.group.sortify.model.Student;

public interface SortingStrategy {
    Student[] sort(Student[] array);

    String getStrategyName();
}