package dev.group.sortify.strategy;

import dev.group.sortify.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStudentStrategy implements StudentGenerationStrategy{
    final private int count;
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
    // Женские
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
public RandomStudentStrategy(int count){
     this.count = count;
    }

    @Override
    public List<Student> generate() {
        List<Student> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            boolean isMale = random.nextBoolean();
            String name = isMale ? MALE_NAMES[random.nextInt(MALE_NAMES.length)] : FEMALE_NAMES[random.nextInt(FEMALE_NAMES.length)];
            String surname = isMale ? MALE_SURNAMES[random.nextInt(MALE_SURNAMES.length)] : FEMALE_SURNAMES[random.nextInt(FEMALE_SURNAMES.length)];
            String patronymic = isMale ? MALE_PATRONYMICS[random.nextInt(MALE_PATRONYMICS.length)] : FEMALE_PATRONYMICS[random.nextInt(FEMALE_PATRONYMICS.length)];

            String fio = surname + " " + name + " " + patronymic;
            int numGroup = random.nextInt(119 - 101 + 1) + 101;
            double averageScore = Math.round((random.nextDouble() * 5.0) * 10.0) / 10.0;
            int studentIdNumber = random.nextInt(500) + 1000;

            result.add(new Student.Builder()
                    .fio(fio)
                    .numGroup(numGroup)
                    .averageScore(averageScore)
                    .studentIdNumber(studentIdNumber)
                    .build());
        }
        return result;
    }
}
