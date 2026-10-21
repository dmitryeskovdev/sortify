package dev.group.sortify.comparator;

import dev.group.sortify.model.Student;

public class RecordBookNumberComparator implements StudentComparator {
    private final String comparatorName = "RecordBookNumber";

    @Override
    public String getComparatorName() {
        return comparatorName;
    }

    @Override
    public int compare(Student studentFirst, Student studentSecond) {
        return studentFirst.getRecordBookNumber().compareTo(studentSecond.getRecordBookNumber());
    }
}
