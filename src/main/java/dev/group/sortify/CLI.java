package dev.group.sortify;

import dev.group.sortify.collection.StudentList;
import dev.group.sortify.comparator.AgeComparator;
import dev.group.sortify.comparator.GradePointAverageComparator;
import dev.group.sortify.comparator.GroupNumberComparator;
import dev.group.sortify.comparator.RecordBookNumberComparator;
import dev.group.sortify.generator.EmptyStudentStrategy;
import dev.group.sortify.generator.RandomStudentStrategy;
import dev.group.sortify.model.Student;
import dev.group.sortify.service.FileService;
import dev.group.sortify.service.StudentFactoryService;
import dev.group.sortify.service.StudentSearchService;
import dev.group.sortify.strategy.EvenOnlyInsertionSortStrategy;
import dev.group.sortify.strategy.InsertionSortStrategy;
import dev.group.sortify.strategy.SortingStrategy;
import dev.group.sortify.utils.ConsoleActions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class CLI {
    private final Map<String, Consumer<String>> commands = new HashMap<>();
    private final Scanner scanner;
    private final StudentFactoryService factory;
    private final FileService fileService;
    private StudentList students;
    private SortingStrategy strategy;

    public CLI(Scanner scanner) {
        commands.put("print", this::handlePrint);
        commands.put("size", this::handleSize);
        commands.put("read file", this::handleReadFile);
        commands.put("write file", this::handleWriteFile);
        commands.put("create empty", this::handleCreateEmpty);
        commands.put("create random", this::handleCreateRandom);
        commands.put("add", this::handleAdd);
        commands.put("remove", this::handleRemove);
        commands.put("set strategy", this::handleSetStrategy);
        commands.put("get strategy", this::handleGetStrategy);
        commands.put("sort", this::handleSort);
        commands.put("search", this::handleSearch);
        commands.put("search parallel", this::handleSearchParallel);
        commands.put("help", this::handleHelp);
        this.scanner = scanner;
        this.factory = new StudentFactoryService();
        this.fileService = new FileService();
    }

    public static void handleWelcome() {
        System.out.println("Sortify CLI");
        System.out.println("Для вывода всех команд на экран воспользуйтесь командой 'help'");
        System.out.println("Для выхода введите 'exit'");
    }

    public Map<String, Consumer<String>> getCommands() {
        return commands;
    }

    private void handlePrint(String command) {
        if (students == null) {
            ConsoleActions.printInfo("Коллекция не создана");
            return;
        } else if (students.size() == 0) {
            ConsoleActions.printInfo("Коллекция еще не имеет элементов");
        }
        AtomicInteger index = new AtomicInteger(1);

        students.stream().forEach(student -> System.out.println("[" + (index.getAndIncrement() - 1) + "] " + student));
    }

    private void handleSize(String command) {
        if (students == null) {
            ConsoleActions.printInfo("Коллекция не создана");
            return;
        }
        ConsoleActions.printInfo("Размер коллекции " + students.size());
    }

    private void handleReadFile(String command) {
        ConsoleActions.printAction("Введите путь к файлу для чтения (по умолчанию, data/students.txt): ");
        String fileName = scanner.nextLine().trim();

        if (fileName.isEmpty()) {
            fileName = "data/students.txt";
        }

        StudentList loadedData = fileService.loadFromFile(fileName);
        if (loadedData == null) {
            return;
        } else if (loadedData.size() > 0) {
            this.students = loadedData;
            ConsoleActions.printInfo(String.format("Успешно загружено студентов: %d", students.size()));
        } else {
            this.students = loadedData;
            ConsoleActions.printInfo("Файл не имеет данных для заполнения коллекции");
        }

    }

    private void handleWriteFile(String command) {

        if (students == null || students.size() == 0) {
            ConsoleActions.printError("Коллекция не создана или не имеет элементов");
            return;
        }

        ConsoleActions.printAction("Введите путь к файлу для записи (по умолчанию, data/students.txt): ");
        String fileName = scanner.nextLine().trim();

        if (fileName.isEmpty()) {
            fileName = "data/students.txt";
        }

        fileService.saveToFile(students, fileName);
    }

    private void handleCreateEmpty(String command) {
        factory.setStrategy(new EmptyStudentStrategy());
        this.students = factory.createStudents();
        ConsoleActions.printInfo("Создана пустая коллекция размером " + students.size());
    }

    private void handleCreateRandom(String command) {
        int count = 0;

        while (true) {
            try {
                ConsoleActions.printAction("Введите размер коллекции: ");
                String line = scanner.nextLine().trim();
                count = Integer.parseInt(line);
                if (count > 0) break;
                ConsoleActions.printInfo("Число должно быть > 0");
            } catch (NumberFormatException e) {
                ConsoleActions.printError("Введите целое число");
            }

        }

        factory.setStrategy(new RandomStudentStrategy(count));
        this.students = factory.createStudents();

        ConsoleActions.printInfo("Создана случайная коллекция размером " + students.size());
    }

    private void handleAdd(String command) {
        if (students == null) {
            ConsoleActions.printInfo("Коллекция не создана");
            return;
        }

        String fullName;
        while (true) {
            ConsoleActions.printAction("Введите данные (Фамилия Имя Отчество): ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                ConsoleActions.printInfo("ФИО не может быть пустым");
                continue;
            }
            if (!line.matches("^[A-Za-zА-Яа-яЁё]+\\s[A-Za-zА-Яа-яЁё]+\\s[A-Za-zА-Яа-яЁё]+$")) {
                ConsoleActions.printInfo("Допускается вводить только буквы и пробелы");
                continue;
            }
            fullName = line;
            break;
        }

        int age = 0;
        while (true) {
            ConsoleActions.printAction("Введите возраст: ");
            String line = scanner.nextLine().trim();
            try {
                age = Integer.parseInt(line);
                if (age >= 18 && age <= 80) break;
                ConsoleActions.printInfo("Возраст должен быть от 18 до 80");
                continue;
            } catch (NumberFormatException e) {
                ConsoleActions.printInfo("Введено не число");
            }
        }

        int groupNumber;
        while (true) {
            ConsoleActions.printAction("Введите номер группы: ");
            String line = scanner.nextLine().trim();
            try {
                groupNumber = Integer.parseInt(line);
                if (groupNumber < 1000 && groupNumber > 0) break;
                ConsoleActions.printInfo("Номер группы может быть от 1 до 999");
                continue;
            } catch (NumberFormatException e) {
                ConsoleActions.printInfo("Введено не число");
            }
        }

        double gradePointAverage;
        while (true) {
            ConsoleActions.printAction("Введите средний балл: ");
            String line = scanner.nextLine().trim();
            try {
                gradePointAverage = Double.parseDouble(line);
                if (gradePointAverage > 2.5) break;
                ConsoleActions.printInfo("Средний балл может быть от 2.5 до 5.0");
                continue;
            } catch (NumberFormatException e) {
                ConsoleActions.printInfo("Введено не вещественное число");
            }
        }

        String recordBookNumber;
        while (true) {
            ConsoleActions.printAction("Введите номер зачетной книжки (A...Z + GROUP + YEAR): ");
            recordBookNumber = scanner.nextLine().trim();

            if (recordBookNumber.matches("^[A-Z]\\d{3}(201[7-9]|202[0-6])$")) break;

            ConsoleActions.printInfo("Введен недопустимый формат, пример ввода D1292026");
        }

        Student student = new Student.Builder()
                .fullName(fullName)
                .age(age)
                .gradePointAverage(gradePointAverage)
                .groupNumber(groupNumber)
                .recordBookNumber(recordBookNumber)
                .build();

        students.add(student);
        ConsoleActions.printInfo("Добавлен элемент: " + student.toString());
    }

    private void handleRemove(String command) {
        if (students == null) {
            ConsoleActions.printInfo("Коллекция не создана");
            return;
        } else if (students.size() == 0) {
            ConsoleActions.printInfo("Коллекция еще не имеет элементов");
            return;
        }

        int index = 0;
        while (true) {
            ConsoleActions.printAction("Введите индекс удаляемого элемента: ");
            String line = scanner.nextLine().trim();
            try {
                index = Integer.parseInt(line);
                if (index >= 0 && index < students.size()) {
                    Student removed = students.remove(index);
                    ConsoleActions.printInfo("Удален элемент с индексом " + index + "\n" + "Элемент: " + removed.toString());
                    break;
                }
                ConsoleActions.printInfo("Индекс должен быть от 0 до " + (students.size() - 1));
                continue;
            } catch (NumberFormatException e) {
                ConsoleActions.printError("Недопустимый индекс элемента");
            }
        }

    }

    private void handleGetStrategy(String command) {
        if (strategy == null) {
            ConsoleActions.printInfo("Стратегия сортировки не установлена");
            return;
        }
        ConsoleActions.printInfo("Установлена стратегия: " + strategy.getStrategyName());
    }

    private void handleSetStrategy(String command) {
        int strategyNumber = 0;
        while (true) {
            ConsoleActions.printAction("1: Стандартная сортировка\n");
            ConsoleActions.printAction("2: Четная сортировка (по возрасту)\n");
            ConsoleActions.printAction("Введите стратегию сортировки: ");
            String line = scanner.nextLine().trim();
            try {
                strategyNumber = Integer.parseInt(line);
                if (strategyNumber == 1 || strategyNumber == 2) break;
                ConsoleActions.printInfo("Выберите допустимое значение");
                continue;
            } catch (NumberFormatException e) {
                ConsoleActions.printInfo("Введено не число");
                continue;
            }
        }

        SortingStrategy selectedStrategy = switch (strategyNumber) {
            case 1 -> {
                int fieldNumber = 0;
                while (true) {
                    ConsoleActions.printAction("1: Сортировка по группе\n");
                    ConsoleActions.printAction("2: Сортировка по среднему баллу\n");
                    ConsoleActions.printAction("3: Сортировка по зачетной книжке\n");
                    ConsoleActions.printAction("Введите поле для сортировки: ");
                    String line = scanner.nextLine().trim();
                    try {
                        fieldNumber = Integer.parseInt(line);
                        if (fieldNumber == 1 || fieldNumber == 2 || fieldNumber == 3) break;
                        ConsoleActions.printInfo("Выберите допустимое значение");
                    } catch (NumberFormatException e) {
                        ConsoleActions.printInfo("Введено не число");
                    }
                }
                ConsoleActions.printInfo("Стратегия сортировки установлена");
                yield switch (fieldNumber) {
                    case 1 -> new InsertionSortStrategy(new GroupNumberComparator());
                    case 2 -> new InsertionSortStrategy(new GradePointAverageComparator());
                    case 3 -> new InsertionSortStrategy(new RecordBookNumberComparator());
                    default -> null;
                };
            }

            case 2 -> {
                ConsoleActions.printInfo("Стратегия сортировки установлена");
                yield new EvenOnlyInsertionSortStrategy(new AgeComparator());
            }

            default -> null;
        };

        strategy = selectedStrategy;

    }

    private void handleSort(String command) {
        if (students == null) {
            ConsoleActions.printInfo("Коллекция не создана");
            return;
        } else if (students.size() == 0) {
            ConsoleActions.printInfo("Коллекция еще не имеет элементов");
            return;
        }

        if (strategy == null) {
            ConsoleActions.printInfo("Не выбрана стратегия сортировки");
            return;
        }

        students = new StudentList(strategy.sort(students.toArray()));
        ConsoleActions.printInfo("Произведена сортировка коллекции");
    }

    private void handleSearch(String command) {
        if (students == null) {
            ConsoleActions.printInfo("Коллекция не создана");
            return;
        } else if (students.size() == 0) {
            ConsoleActions.printInfo("Коллекция еще не имеет элементов");
            return;
        }

        int targetAge = 0;
        while (true) {
            ConsoleActions.printAction("Введите возраст поиска: ");
            String line = scanner.nextLine().trim();
            try {
                targetAge = Integer.parseInt(line);
                if (targetAge >= 18 && targetAge < 80) break;
                ConsoleActions.printInfo("Возраст должен быть от 18 до 80");
                continue;
            } catch (NumberFormatException e) {
                ConsoleActions.printInfo("Введено не число");
            }
        }

        long startTime = System.nanoTime();
        long count = StudentSearchService.searchAge(students, targetAge);
        long endTime = System.nanoTime();

        long durationNano = endTime - startTime;
        double durationMilli = durationNano / 1000000.0;

        if (count == -1) {
            ConsoleActions.printInfo("Не найдено элементов");
        } else {
            ConsoleActions.printInfo("Количество найденных элементов " + count + ", найдено за время " + durationMilli + " мс");
        }

    }

    private void handleSearchParallel(String command) {
        if (students == null) {
            ConsoleActions.printInfo("Коллекция не создана");
            return;
        } else if (students.size() == 0) {
            ConsoleActions.printInfo("Коллекция еще не имеет элементов");
            return;
        }

        int targetAge = 0;
        while (true) {
            ConsoleActions.printAction("Введите возраст поиска: ");
            String line = scanner.nextLine().trim();
            try {
                targetAge = Integer.parseInt(line);
                if (targetAge >= 18 && targetAge < 80) break;
                ConsoleActions.printInfo("Возраст должен быть от 18 до 80");
                continue;
            } catch (NumberFormatException e) {
                ConsoleActions.printInfo("Введено не число");
            }
        }

        long startTime = System.nanoTime();
        long count = StudentSearchService.searchParallelAge(students, targetAge);
        long endTime = System.nanoTime();

        long durationNano = endTime - startTime;
        double durationMilli = durationNano / 1000000.0;

        if (count == -1) {
            ConsoleActions.printInfo("Не найдено элементов");
        } else {
            ConsoleActions.printInfo("Количество найденных элементов " + count + ", найдено за время " + durationMilli + " мс");
        }

    }

    private void handleHelp(String command) {
        System.out.println("Команды:\n" +
                "   print           - вывести коллекцию в консоль;\n" +
                "   size            - вывести текущий размер коллекции;\n" +
                "   read file       - загрузить данные из файла;\n" +
                "   write file      - сохранить данные в файл;\n" +
                "   create empty    - создать пустую коллецию;\n" +
                "   create random   - создать случайную коллекцию;\n" +
                "   add             - добавить элемент в коллекцию;\n" +
                "   remove          - удалить элемент из коллекции;\n" +
                "   set strategy    - установить стратегию сортировки коллекции;\n" +
                "   get strategy    - текущая стратегия сортировки коллекции;\n" +
                "   sort            - запустить сортировку коллекции;\n" +
                "   search          - поиск количества элементов по возрасту;\n" +
                "   search parallel - поиск количества элементов по возрасту (многопоточный режим);\n" +
                "   help            - список команд;\n" +
                "   clear           - очистить консоль;\n" +
                "   exit            - выход;"
        );
    }

}
