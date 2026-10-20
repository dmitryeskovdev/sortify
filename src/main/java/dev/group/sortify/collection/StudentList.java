package dev.group.sortify.collection;

import dev.group.sortify.model.Student;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StudentList implements Iterable<Student> {
    private Student[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 30;

    public StudentList() {
        this.elements = new Student[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void add(Student student) {
        if (size == elements.length) {
            grow();
        }
        int index = size++;
        elements[index] = student;
    }

    public Student get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Недопустимый индекс " + index + " для массива размером " + size);
        }
        return elements[index];
    }

    public int size() {
        return size;
    }

    private void grow() {
        int newCapacity = elements.length * 2;
        Student[] newElements = new Student[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    public Student[] toArray() {
        Student[] result = new Student[this.size];
        System.arraycopy(this.elements, 0, result, 0, this.size);
        return result;
    }

    @Override
    public Iterator<Student> iterator() { return new StudentIterator(this); }

    private static class StudentIterator implements Iterator<Student> {
        private final StudentList list;
        private int cursor = 0;

        public StudentIterator(StudentList list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return cursor < list.size();
        }

        @Override
        public Student next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int index = cursor++;
            return list.get(index);
        }
    }
}
