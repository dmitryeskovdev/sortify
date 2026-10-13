package dev.group.sortify.service;

import dev.group.sortify.model.Student;

import java.util.Comparator;
import java.util.List;

public class Sort {
    private final List<Student> students;
    public Sort(List<Student> students) {
        if(students == null){
            throw new IllegalArgumentException("Список студентов не может быть null");
        }
        this.students = students;
    }
    public void sort(){
        students.sort(Comparator.comparing(Student::getFio)
                .thenComparingInt(Student::getNumGroup)
                .thenComparing(Comparator.comparingDouble(Student::getAverageScore).reversed()));
    }
    public List<Student> getStudents() {
        return students;
    }
}
