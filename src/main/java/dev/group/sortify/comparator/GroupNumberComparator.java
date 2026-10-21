package dev.group.sortify.comparator;

import dev.group.sortify.model.Student;

public class GroupNumberComparator implements StudentComparator {
    private final String comparatorName = "GroupNumber";

    @Override
    public String getComparatorName() {
        return comparatorName;
    }

    @Override
    public int compare(Student studentFirst, Student studentSecond) {
        return Integer.compare(studentFirst.getGroupNumber(), studentSecond.getGroupNumber());
    }
}
