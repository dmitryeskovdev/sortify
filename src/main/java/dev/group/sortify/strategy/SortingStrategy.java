package dev.group.sortify.strategy;

import dev.group.sortify.model.Student;
import dev.group.sortify.comparator.StudentComparator;

public interface SortingStrategy {
    void sort(Student[] array, StudentComparator comparator);
}