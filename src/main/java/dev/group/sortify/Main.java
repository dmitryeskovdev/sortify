package dev.group.sortify;

import dev.group.sortify.model.Student;
import dev.group.sortify.service.AddInFile;
import dev.group.sortify.service.Sorter;
import dev.group.sortify.service.StudentAdd;
import dev.group.sortify.strategy.ConsoleStudentStrategy;
import dev.group.sortify.strategy.FileStudentStrategy;
import dev.group.sortify.strategy.RandomStudentStrategy;

import java.util.*;
import java.util.function.Consumer;

public class Main
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        String stopWord = "Exit";
        StudentAdd add = new StudentAdd(null);

        List<Student> students = add.getStudents();
        AddInFile addF = new AddInFile(students);
        Sorter sorter = new Sorter(students);

        Map<String,Consumer<String>> commands = new HashMap<>();
        commands.put("add_random", input ->{
            System.out.println("Сколько студентов сгенерировать? > ");
            int count = 0;
            while (true){
                try {
                    String line = scanner.nextLine();
                    count = Integer.parseInt(line);
                    if (count > 0) break;
                    System.out.println("Число должно быть > 0");
                }
                catch (NumberFormatException e){
                    System.out.println("Введите число");
                }

            }
            add.setStrategy(new RandomStudentStrategy(count));
            add.addStudents();
        });



        commands.put("add_con", input -> {
            add.setStrategy(new ConsoleStudentStrategy(scanner));
            add.addStudents();
        });

        // Команда: Из файла
        commands.put("add_f", input -> {
            add.setStrategy(new FileStudentStrategy(scanner));
            add.addStudents();
        });
        commands.put("write",input -> {addF.writeFile();
            System.out.println("Студенты записаны в файл test");});
        commands.put("print", input ->{
            System.out.println(students);
        });
        commands.put("sort_fio", input -> {
            sorter.sort(Comparator.comparing(Student::getFio, String.CASE_INSENSITIVE_ORDER));
            System.out.println("Отсортировано по ФИО.");
        });


        commands.put("sort_group", input -> {
            sorter.sort(Comparator.comparingInt(Student::getNumGroup));
            System.out.println("Отсортировано по номеру группы.");
        });


        commands.put("sort_score", input -> {
            sorter.sort(Comparator.comparingDouble(Student::getAverageScore).reversed());
            System.out.println("Отсортировано по среднему баллу.");
        });
        commands.put("sort_id", input -> {
            sorter.sort(Comparator.comparingDouble(Student::getStudentIdNumber).reversed());
            System.out.println("Отсортировано по среднему баллу.");
        });
        commands.put("sort_even", input -> {
            sorter.sortEvenGroupsKeepOddPositions();
            System.out.println("Отсортированы только чётные группы (нечётные на местах).");
        });
        commands.put("help", input -> {
            System.out.println(
                    "Команды:\n" +
                            "  print       - выводит список студентов;\n" +
                            "  add_f         - добавляет из файла;\n" +
                            "  add_con     - ввод с консоли;\n" +
                            "  add_random  - рандомные студенты;\n" +
                            "  write       - записать в файл;\n" +
                            "  sort_fio    - сортировка по ФИО;\n" +
                            "  sort_group  - сортировка по группе;\n" +
                            "  sort_score  - сортировка по баллу;\n" +
                            "  sort_all    - сортировка по номеру зачётной книжки;\n" +
                            "  sort_even    - сортировка чётные нечётные;\n" +
                            "  Exit        - выход."
            );
        });


        System.out.println("Для вывода всех команд на экран воспользуйтесь командой help");
        while (true){
            System.out.print("> ");
            String line = scanner.nextLine();
            if(line.equalsIgnoreCase(stopWord)){
                System.out.println("Программа завершена.");
                break;
            }
            Consumer <String> action = commands.get(line);
            if (action != null){
                action.accept(line);
            }else{
                System.out.println("Неизвестная команда: " + line + ". Попробуйте снова.");
            }
        }
    }
}


