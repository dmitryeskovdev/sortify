package dev.group.sortify.model;

public class Student {
    private final String fio;
    private final int numGroup;
    private final double averageScore;
    private final int studentIdNumber;
    public Student(Builder builder)
    {
        this.fio = builder.fio;
        this.numGroup = builder.numGroup;
        this.averageScore = builder.averageScore;
        this.studentIdNumber = builder.studentIdNumber;
    }
    public String getFio(){
        return fio;
    }
    public int getNumGroup(){
        return numGroup;
    }
    public double getAverageScore(){
        return averageScore;
    }
    public int getStudentIdNumber(){
        return studentIdNumber;
    }
    @Override
    public String toString(){
        return "ФИО студента: " +fio+"\nНомер группы: " + numGroup + "\nСредний балл: " + averageScore + "\nНомер зачётной книжки: " + studentIdNumber+"\n";
    }
    public static class Builder{
        private String fio;
        private int numGroup;
        private double averageScore;
        private int studentIdNumber;
        public Builder fio(String fio){
            if(fio == null || fio.isBlank()){
                throw new IllegalArgumentException("Данные ФИО должны быть введены");
            }
            this.fio = fio;
            return this;
        }
        public Builder numGroup(int numGroup){
            if(numGroup <=0){
                throw new IllegalArgumentException("Номер группы не введён или введён некорректно");
            }
            this.numGroup = numGroup;
            return this;
        }
        public Builder averageScore(double averageScore){
            if(averageScore <= 0){
                throw new IllegalArgumentException("Средний балл не введён или введён некорректно");
            }
            this.averageScore = averageScore;
            return this;
        }
        public Builder studentIdNumber(int studentIdNumber){
            if (studentIdNumber <= 0 ){
                throw new IllegalArgumentException("Номер зачётной книжки не введён или введён некорректно");
            }
            this.studentIdNumber = studentIdNumber;
            return this;
        }

        public Student build(){
            if (fio.chars().anyMatch(Character::isDigit)){
                throw new IllegalArgumentException("В ФИО присутствуют цифры или недопустимые символы");
            }
            return  new Student(this);
        }
    }
}

