package dev.group.sortify.collection;

import dev.group.sortify.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StudentList {
    private final List<Student> students;

    public StudentList() {
        this.students = new ArrayList<>();
    }

    public void add(Student student) {
        if (student != null) {
            students.add(student);
        }
    }

    public Student get(int index) {
        return students.get(index);
    }

    public int size() {
        return students.size();
    }

    public Stream<Student> stream() {
        return students.stream();
    }
}