package dev.group.sortify.model;

public class Student {
    private final String fullName;
    private final int age;
    private final int groupNumber;
    private final double gradePointAverage;
    private final String recordBookNumber;

    private Student(Builder builder) {
        this.fullName = builder.fullName;
        this.age = builder.age;
        this.groupNumber = builder.groupNumber;
        this.gradePointAverage = builder.gradePointAverage;
        this.recordBookNumber = builder.recordBookNumber;
    }

    public int getAge() { return age; }
    public int getGroupNumber() { return groupNumber; }
    public double getGradePointAverage() { return gradePointAverage; }
    public String getRecordBookNumber() { return recordBookNumber; }
    public String getFullName() { return fullName; }

    @Override
    public String toString() {
        return String.format("Студент [Группа: %d, Средний балл: %.1f, Номер книжки: |%s|, Возраст: %d]",
                groupNumber,
                gradePointAverage,
                recordBookNumber,
                age);
    }

    public static class Builder {
        private String fullName;
        private int age;
        private int groupNumber;
        private double gradePointAverage;
        private String recordBookNumber;

        public Builder age(int age) {
            this.age = age;
            return this;
        }
        public Builder groupNumber(int groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder gradePointAverage(double gradePointAverage) {
            this.gradePointAverage = gradePointAverage;
            return this;
        }

        public Builder recordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
