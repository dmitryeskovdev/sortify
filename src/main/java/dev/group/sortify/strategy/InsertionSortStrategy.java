package dev.group.sortify.strategy;

import dev.group.sortify.model.Student;
import dev.group.sortify.comparator.StudentComparator;

public class InsertionSortStrategy implements SortingStrategy {

    @Override
    public void sort(Student[] students, StudentComparator comparator) {
        if (students == null || students.length < 2) return;

        for (int i = 1; i < students.length; i++) {
            Student key = students[i];
            int j = i - 1;

            while (j >= 0 && comparator.compare(students[j], key) > 0) {
                students[j + 1] = students[j];
                j--;
            }

            students[j + 1] = key;
        }
    }
}

