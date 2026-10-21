package dev.group.sortify.model;

public class StudentValidator {
    public static boolean validateFullName(String fullName) {
        if (fullName == null) return false;
        return fullName.strip().matches("^[А-ЯЁ][а-яё]+\\s[А-ЯЁ][а-яё]+\\s[А-ЯЁ][а-яё]+$");
    }

    public static boolean validateAge(int age) {
        return age >= 18 && age <= 80;
    }

    public static boolean validateGroup(int groupNumber) {
        return groupNumber >= 1 && groupNumber <= 999;
    }

    public static boolean validateGrade(double gradePointAverage) {
        return gradePointAverage >= 2.5 && gradePointAverage <= 5.0;
    }

    public static boolean validateRecordBook(String recordBookNumber) {
        if (recordBookNumber == null) return false;
        String pattern = "^[A-Z]\\d{3}(201[7-9]|202[0-6])$";
        return recordBookNumber.strip().matches(pattern);
    }
}
