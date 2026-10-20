package dev.group.sortify.strategy;

import dev.group.sortify.model.Student;
import dev.group.sortify.comparator.StudentComparator;

public class EvenOnlyInsertionSortStrategy implements SortingStrategy {

    @Override
    public void sort(Student[] students, StudentComparator comparator) {
        if (students == null || students.length < 2) return;

        for (int i = 0; i < students.length; i++) {
            if (students[i].getGroupNumber() % 2 != 0) {
                continue;
            }

            Student key = students[i];

            int j = findPreviousEvenIndex(students, i - 1);

            while (j >= 0 && comparator.compare(students[j], key) > 0) {
                int nextEvenIdx = findNextEvenIndex(students, j + 1);
                students[nextEvenIdx] = students[j];
                j = findPreviousEvenIndex(students, j - 1);
            }

            int targetIdx = findNextEvenIndex(students, j + 1);
            students[targetIdx] = key;
        }
    }

    private int findPreviousEvenIndex(Student[] students, int startIdx) {
        for (int i = startIdx; i >= 0; i--) {
            if (students[i].getGroupNumber() % 2 == 0) {
                return i;
            }
        }
        return -1;
    }

    private int findNextEvenIndex(Student[] students, int startIdx) {
        for (int i = startIdx; i < students.length; i++) {
            if (students[i].getGroupNumber() % 2 == 0) {
                return i;
            }
        }
        return startIdx;
    }
}
