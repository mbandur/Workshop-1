package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class TaskManager {
    final static String[] LIST_OF_FUNCTIONS = {"add", "remove", "list", "exit"};
    final static String FILENAME = "tasks.csv";
    static String[][] listOfTasks;
    static Scanner scanLine = new Scanner(System.in);

    public static void main(String[] args) {
        listOfTasks = loadTasks();

        while (true) {
            Arrays.sort(listOfTasks, Comparator.comparing(date -> LocalDate.parse(date[1])));
            mainOptions();
            switch (scanLine.nextLine()) {
                case "add":
                    listOfTasks = addTask(listOfTasks);
                    break;
                case "remove":
                    listOfTasks = removeTask(listOfTasks);
                    break;
                case "list":
                    listTask(listOfTasks);
                    break;
                case "exit":
                    saveTask(listOfTasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
        }
    }
    private static String[][] loadTasks() {
        Path path = Paths.get(FILENAME);
        File file = new File(FILENAME);
        int numberLinesInfile = 0;
        String[][] data;

        try {
            numberLinesInfile = (int) Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        data = new String[numberLinesInfile][3];

        try (Scanner scanFile = new Scanner(file)) {
            for (int actualLoopValue = 0; actualLoopValue < numberLinesInfile; actualLoopValue++) {
                data[actualLoopValue] = scanFile.nextLine().split("\\| *");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return data;
    }

    private static void mainOptions() {
        System.out.println(ConsoleColors.BLUE + "\nPlease select an option:" + ConsoleColors.RESET);
        for (String function: LIST_OF_FUNCTIONS) {
            System.out.println(function);
        }
    }
    private static void listTask(String[][] list) {
        for (int actualLoopValue = 0; actualLoopValue < list.length; actualLoopValue++) {
            if (dateValidation(list[actualLoopValue][1]) == -1) {
                System.out.print(pl.coderslab.ConsoleColors.RED);
            } else if (dateValidation(list[actualLoopValue][1]) == 0) {
                System.out.print(pl.coderslab.ConsoleColors.YELLOW);
            } else {
                System.out.print(pl.coderslab.ConsoleColors.GREEN);
            }
            System.out.printf("%d : %s\t%s\t%s\n", actualLoopValue, list[actualLoopValue][0], list[actualLoopValue][1], list[actualLoopValue][2]);
            System.out.print(pl.coderslab.ConsoleColors.RESET);
        }
    }
    private static String[][] addTask(String[][] list) {
        String[] newLine = new String[3];

        System.out.println("Please add task description");
        newLine[0] = scanLine.nextLine();

        System.out.println("Please add task due date");
        while (true) {
            String date = scanLine.nextLine();
            int dateValidationStatus =  dateValidation(date);
            if ((dateValidationStatus == 0) || (dateValidationStatus == 1) ) {
                newLine[1] = date;
                break;
            } else {
                System.out.println("Wrong input. Please add current or future date");
            }
        }

        System.out.println("Is your task is important: true/false");
        while (true) {
            String importance = scanLine.nextLine();
            if ((importance.equals("true")) || (importance.equals("false"))) {
                newLine[2] = importance;
                break;
            }
            System.out.println("Wrong input. Please input \"true\" or \"false\"");
        }
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = newLine;
        return list;
    }
    private static String[][] removeTask(String[][] list) {
        String stringIndexToRemove;
        int intIndexToRemove;

        System.out.println("Please select number to remove.");
        while (true) {
            stringIndexToRemove = scanLine.nextLine();
            if (NumberUtils.isParsable(stringIndexToRemove)) {
                intIndexToRemove = Integer.parseInt(stringIndexToRemove);
                if (intIndexToRemove >= 0 ) {
                    try {
                        String[][] newList = ArrayUtils.remove(list, intIndexToRemove);
                        System.out.println("Are you sure to remove? Enter \"yes\"");
                        String decision = scanLine.nextLine();
                        if (decision.equals("yes")) {
                            list = newList;
                            System.out.println("Value was successfully deleted.");
                            break;
                        }
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Incorrect argument passed. Please give number greater or equal 0");
                        continue;
                    }
                }
            }
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
        }
        return list;
    }
    private static void saveTask(String[][] list) {
        try (FileWriter writer = new FileWriter(FILENAME, false)) {
            for (String[] task: list) {
                writer.append(String.join("|", task) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static int dateValidation(String str) {
        LocalDate todayDate = LocalDate.now();
        try {
            LocalDate date = LocalDate.parse(str);
            if (date.isBefore(todayDate)) {
                return -1;
            } else if (date.isEqual(todayDate)) {
                return 0;
            } else {
                return 1;
            }
        } catch(DateTimeParseException e) {
            return -99;
        }
    }
}