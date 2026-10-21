package dev.group.sortify.comparator;

import dev.group.sortify.model.Student;

public class AgeComparator implements StudentComparator {
    private final String comparatorName = "Age";

    @Override
    public String getComparatorName() {
        return comparatorName;
    }

    @Override
    public int compare(Student studentFirst, Student studentSecond) {
        return Integer.compare(studentFirst.getAge(), studentSecond.getAge());
    }
}
