package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class TaskManager {
    final static String[] LIST_OF_FUNCTIONS = {"add", "remove", "list", "exit"};
    final static String FILENAME = "tasks.csv";
    static String[][] listOfTasks;
    static Scanner scanLine = new Scanner(System.in);

    public static void main(String[] args) {
        listOfTasks = loadTasks();

        while (true) {
            mainOptions();
            switch (scanLine.nextLine()) {
                case "add":
                    break;
                case "remove":
                    break;
                case "list":
                    break;
                case "exit":
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
                data[actualLoopValue] = scanFile.nextLine().split(",");
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
}