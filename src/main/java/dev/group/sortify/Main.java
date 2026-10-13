package dev.group.sortify;

import dev.group.sortify.model.Student;
import dev.group.sortify.service.AddInFile;
import dev.group.sortify.service.Sort;
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

        Sort sort = new Sort(students);
        AddInFile addF = new AddInFile(students);


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
        commands.put("add", input -> {
            add.setStrategy(new FileStudentStrategy("data/list_std.txt"));
            add.addStudents();
        });
        commands.put("write",input -> {addF.writeFile();
            System.out.println("Студенты записаны в файл test");});
        commands.put("print", input ->{
            System.out.println(students);
        });
        commands.put("sort",input ->{
            sort.sort();
        });
        commands.put("help", input ->{
            System.out.println("Команды:\n print - выводит список студентов;\n add - добавляет в список данные из файла;" +
                    "\n write - записывает данные в файл;\n sort - сортирует список по полю ФИО;\n add_con - ввод данных в консоли\n add_random - рандомное пополнение студентов");
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

