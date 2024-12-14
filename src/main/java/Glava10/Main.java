package Glava10;

import Glava10.TaskA.*;
import Glava10.TaskC.*;
import Glava10.TaskB.*;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Чтение текста из файла input.txt
            List<String> lines = Files.readAllLines(Paths.get("input.txt"));

            // Нахождение подходящих слов
            TaskA WordMatcher = null;
            List<String> matchingWords = WordMatcher.findMatchingWords(lines);

            // Форматирование результата
            StringBuilder output = new StringBuilder();
            if (!matchingWords.isEmpty()) {
                for (int i = 0; i < matchingWords.size(); i += 2) {
                    output.append(matchingWords.get(i))
                            .append(" -> ")
                            .append(matchingWords.get(i + 1))
                            .append(System.lineSeparator());
                }
            } else {
                output.append("Подходящие слова не найдены").append(System.lineSeparator());
            }

            // Запись результата в output.txt
            Files.write(Paths.get("output.txt"), output.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            // Вывод результата на экран
            System.out.println("Результат записан в файл output.txt:");
            System.out.println(output);
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлами: " + e.getMessage());
        }

        //TaskC
        File inputFile = new File("input.java");

        // Создаем новую директорию
        File outputDir = new File("processed_files");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        // Файл для сохранения результата
        File outputFile = new File(outputDir, "output.java");

        // Обрабатываем файл
        try {
            TaskC LowerToUpper = null;
            LowerToUpper.processFile(inputFile, outputFile);
            System.out.println("Обработанный файл сохранен в: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Ошибка обработки файла: " + e.getMessage());
        }
    }
}