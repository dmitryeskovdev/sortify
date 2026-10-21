package dev.group.sortify.strategy;

import dev.group.sortify.comparator.StudentComparator;
import dev.group.sortify.model.Student;

public class EvenOnlyInsertionSortStrategy implements SortingStrategy {
    private final StudentComparator comparator;
    private final String strategyName;

    public EvenOnlyInsertionSortStrategy(StudentComparator comparator) {
        this.strategyName = "EvenOnlyInsertionSortStrategy " + "by " + comparator.getComparatorName();
        this.comparator = comparator;
    }

    @Override
    public String getStrategyName() {
        return strategyName;
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

        int evenIterator = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i].getAge() % 2 == 0) {
                students[i] = sortedEvenStudents[evenIterator++];
            }
        }

        return students;
    }

}
