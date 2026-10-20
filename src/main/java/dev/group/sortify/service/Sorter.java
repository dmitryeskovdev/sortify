package dev.group.sortify.service;

import dev.group.sortify.model.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorter  {
    private final List<Student> students;
    public Sorter (List<Student> students){
        if (students == null) {
            throw new IllegalArgumentException("Список студентов не может быть null");
        }
        this.students = students;
    }
    public void sort(Comparator<Student> strategy){
        students.sort(strategy);
    }
    public void sortEvenGroupsKeepOddPositions() {
        List<Student> evenStudents = new ArrayList<>();
        for(Student s : students){
            if(s.getNumGroup() % 2 == 0){
                evenStudents.add(s);
            }
        }
        evenStudents.sort(Comparator.comparingInt(Student::getNumGroup));
        int evenIndex = 0;
        for(int i =0; i<students.size(); i++){
            Student current = students.get(i);
            if (current.getNumGroup() % 2 == 0) {
                students.set(i, evenStudents.get(evenIndex));
                evenIndex++;
            }
        }
    }
    public List<Student> getStudents() {
        return students;
    }
}
