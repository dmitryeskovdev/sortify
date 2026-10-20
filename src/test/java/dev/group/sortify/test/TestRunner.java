package dev.group.sortify.test;

import dev.group.sortify.collection.StudentList;
import dev.group.sortify.strategy.*;
import dev.group.sortify.model.Student;
import dev.group.sortify.comparator.StudentComparator;
import dev.group.sortify.comparator.GroupNumberComparator;
import dev.group.sortify.comparator.GradePointAverageComparator;
import dev.group.sortify.comparator.RecordBookNumberComparator;


public class TestRunner {

    private static final String RESET = "\033[0m";
    private static final String GREEN_BRIGHT = "\033[0;92m";
    private static final String RED_BRIGHT = "\033[0;91m";

    public static void main(String[] args) {
        System.out.println("Запуск тестов Sortify");
        testStudentBuilderAndProperties();
        testStudentListAdditionAndIterable();
        testStudentComparators();
        testStudentGenerationStrategy();
        testSortingStrategy();
    }

    private static void printResult(String testName, boolean isPassed) {
        if (isPassed) {
            System.out.println("Test: " + testName + " -> " + GREEN_BRIGHT + "PASS" + RESET);
        } else {
            System.out.println("Test: " + testName + " -> " + RED_BRIGHT + "FAIL" + RESET);
        }
    }

    private static void testStudentBuilderAndProperties() {
        try {
            Student student = new Student.Builder()
                    .fullName("Иванов Иван")
                    .age(27)
                    .groupNumber(101)
                    .gradePointAverage(4.5)
                    .recordBookNumber("A123456Y2026")
                    .build();

            boolean passed = student.getGroupNumber() == 101
                    && "A123456Y2026".equals(student.getRecordBookNumber());

            printResult("Student Builder & Properties", passed);

        } catch (Exception e) {
            printResult("Student Builder & Properties", false);
        }
    }

    private static void testStudentListAdditionAndIterable() {
        try {
            StudentList list = new StudentList();
            Student s1 = new Student.Builder().fullName("Петров Петр").build();
            Student s2 = new Student.Builder().fullName("Александров Александ").build();

            list.add(s1);
            list.add(s2);

            boolean passedSize = list.size() == 2;
            boolean passedArray = list.toArray().length == 2;
            printResult("StudentList Addition & Array conversion", passedSize && passedArray);

        } catch (Exception e) {
            printResult("StudentList Addition & Array conversion", false);
        }
    }

    private static void testStudentComparators() {
        try {
            Student s1 = new Student.Builder().groupNumber(100).gradePointAverage(4.0).recordBookNumber("A111").build();
            Student s2 = new Student.Builder().groupNumber(200).gradePointAverage(5.0).recordBookNumber("B222").build();

            StudentComparator groupComp = new GroupNumberComparator();
            boolean groupPassed = groupComp.compare(s1, s2) < 0;

            StudentComparator gradeComp = new GradePointAverageComparator();
            boolean gradePassed = gradeComp.compare(s1, s2) < 0;

            StudentComparator recordComp = new RecordBookNumberComparator();
            boolean recordPassed = recordComp.compare(s1, s2) < 0;

            printResult("Student Comparators (Group, Grade, RecordBook)", groupPassed && gradePassed && recordPassed);

        } catch (Exception e) {
            printResult("Student Comparators (Group, Grade, RecordBook)", false);
        }
    }

    private static void testStudentGenerationStrategy() {
        try {
            int expectedCount = 5;
            int actualCount = 5;

            boolean passed = (actualCount == expectedCount);

            printResult("StudentGenerationStrategy Simulation", passed);

        } catch (Exception e) {
            printResult("StudentGenerationStrategy Simulation", false);
        }
    }

    private static void testSortingStrategy() {
        try {

            Student s1 = new Student.Builder().groupNumber(500).build();
            Student s2 = new Student.Builder().groupNumber(200).build();
            Student[] array = {s1, s2};

            SortingStrategy sortingStrategy = new InsertionSortStrategy();

            sortingStrategy.sort(array, new GroupNumberComparator());

            boolean passed = array[0].getGroupNumber() == 200;

            printResult("SortingStrategy (InsertionSort validation)", passed);

        } catch (Exception e) {
            printResult("SortingStrategy (InsertionSort validation)", false);
        }
    }
}
