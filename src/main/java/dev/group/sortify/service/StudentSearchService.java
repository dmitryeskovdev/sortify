package dev.group.sortify.service;

import dev.group.sortify.collection.StudentList;

public class StudentSearchService {

    public static long searchAge(StudentList students, int targetAge) {

        final int finalAge = targetAge;

        long count = students.stream()
                .filter(student -> student.getAge() == finalAge)
                .count();

        if (count == 0) {
            return -1;
        } else {
            return count;
        }
    }

    public static long searchParallelAge(StudentList students, int targetAge) {

        final int finalAge = targetAge;

        long count = students.parallelStream()
                .filter(student -> student.getAge() == finalAge)
                .count();

        if (count == 0) {
            return -1;
        } else {
            return count;
        }
    }
}
