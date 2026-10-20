package dev.group.sortify.service;

import dev.group.sortify.collection.StudentList;
import dev.group.sortify.generator.StudentGenerationStrategy;

public class StudentFactoryService {
    private StudentGenerationStrategy strategy;

    public void setStrategy(StudentGenerationStrategy strategy) {
        this.strategy = strategy;
    }

    public StudentList createStudents() {
        if (strategy == null) {
            throw new IllegalStateException("Стратегия создания коллекции не установлена");
        }
        return strategy.generate();
    }

}
