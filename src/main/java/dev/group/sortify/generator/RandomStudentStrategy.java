package dev.group.sortify.generator;

import dev.group.sortify.collection.StudentList;
import dev.group.sortify.model.Student;

import java.util.Random;

public class RandomStudentStrategy implements StudentGenerationStrategy {
    private final int count;
    private final Random random = new Random();

    private static final String[] MALE_NAMES = {
            "Иван", "Пётр", "Сергей", "Дмитрий", "Андрей", "Александр", "Максим", "Кирилл", "Владимир", "Михаил"
    };

    private static final String[] MALE_PATRONYMICS = {
            "Иванович", "Петрович", "Сергеевич", "Дмитриевич", "Андреевич", "Александрович",
            "Максимович", "Кириллович", "Владимирович", "Михайлович"
    };

    private static final String[] MALE_SURNAMES = {
            "Иванов", "Петров", "Сидоров", "Кузнецов", "Смирнов", "Васильев", "Попов", "Соколов", "Михайлов", "Новиков"
    };

    private static final String[] FEMALE_NAMES = {
            "Анна", "Мария", "Елена", "Ольга", "Ирина", "Виктория", "Наталья", "Татьяна", "Юлия", "Светлана"
    };

    private static final String[] FEMALE_PATRONYMICS = {
            "Ивановна", "Петровна", "Сергеевна", "Дмитриевна", "Андреевна", "Александровна",
            "Максимовна", "Кирилловна", "Владимировна", "Михайловна"
    };

    private static final String[] FEMALE_SURNAMES = {
            "Иванова", "Петрова", "Сидорова", "Кузнецова", "Смирнова", "Васильева", "Попова", "Соколова", "Михайлова", "Новикова"
    };

    public RandomStudentStrategy(int count) {
        this.count = count;
    }

    @Override
    public StudentList generate() {
        StudentList students = new StudentList();

        for (int i = 0; i < count; i++) {
            boolean isMale = random.nextBoolean();

            String name = isMale ? MALE_NAMES[random.nextInt(MALE_NAMES.length)] : FEMALE_NAMES[random.nextInt(FEMALE_NAMES.length)];
            String surname = isMale ? MALE_SURNAMES[random.nextInt(MALE_SURNAMES.length)] : FEMALE_SURNAMES[random.nextInt(FEMALE_SURNAMES.length)];
            String patronymic = isMale ? MALE_PATRONYMICS[random.nextInt(MALE_PATRONYMICS.length)] : FEMALE_PATRONYMICS[random.nextInt(FEMALE_PATRONYMICS.length)];

            String fullName = surname + " " + name + " " + patronymic;
            int age = random.nextInt(13) + 18;;
            int groupNumber = random.nextInt(900) + 100;
            double gradePointAverage = 2.5 + random.nextDouble() * 2.5;;

            char prefix = (char) (random.nextInt(26) + 'A');
            int year = random.nextInt(10) + 2017;
            String recordBookNumber = prefix + String.valueOf(groupNumber) + year;

            Student student = new Student.Builder()
                    .fullName(fullName)
                    .age(age)
                    .groupNumber(groupNumber)
                    .gradePointAverage(gradePointAverage)
                    .recordBookNumber(recordBookNumber)
                    .build();

            students.add(student);
        }

        return students;
    }
}

