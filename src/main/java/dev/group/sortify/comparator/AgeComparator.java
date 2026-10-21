package dev.group.sortify.comparator;

import dev.group.sortify.model.Student;

public class AgeComparator implements StudentComparator {
    @Override
    public int compare(Student studentFirst, Student studentSecond) {
        return Integer.compare(studentFirst.getAge(), studentSecond.getAge());
    }
}
