package dev.group.sortify.comparator;

import dev.group.sortify.model.Student;

public class GradePointAverageComparator implements StudentComparator {
    private final String comparatorName = "GradePointAverage";

    @Override
    public String getComparatorName() {
        return comparatorName;
    }

    @Override
    public int compare(Student studentFirst, Student studentSecond) {
        return Double.compare(studentFirst.getGradePointAverage(), studentSecond.getGradePointAverage());
    }
}
