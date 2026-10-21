package dev.group.sortify.strategy;

import dev.group.sortify.comparator.AgeComparator;
import dev.group.sortify.model.Student;
import dev.group.sortify.comparator.StudentComparator;

public class EvenOnlyInsertionSortStrategy implements SortingStrategy {
    private final StudentComparator comparator;

    public EvenOnlyInsertionSortStrategy(StudentComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public Student[] sort(Student[] students) {
        if (students == null || students.length < 2) return students;

        int evenCount = 0;
        for (Student s : students) {
            if (s.getAge() % 2 == 0) {
                evenCount++;
            }
        }

        if (evenCount < 2) return students;

        Student[] evenStudents = new Student[evenCount];
        int index = 0;
        for (Student s : students) {
            if (s.getAge() % 2 == 0) {
                evenStudents[index++] = s;
            }
        }

        SortingStrategy strategy = new InsertionSortStrategy(comparator);
        Student[] sortedEvenStudents = strategy.sort(evenStudents);

        for (Student s : sortedEvenStudents) {
            System.out.println(s);
        }

        int evenIterator = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i].getAge() % 2 == 0) {
                students[i] = sortedEvenStudents[evenIterator++];
            }
        }

        return students;
    }

}
