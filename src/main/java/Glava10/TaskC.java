package Glava10;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TaskC {

    // Метод для обработки содержимого файла
    public static void processFile(File inputFile, File outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile)))
        {

            String line;
            while ((line = reader.readLine()) != null)
            {
                // Обрабатываем строку
                String processedLine = processLine(line);
                writer.write(processedLine);
                writer.newLine();
            }
        }
    }

    private static String processLine(String line) {
        String[] words = line.split("\\b"); // \b сохраняет разделители (пробелы, символы)
        return Arrays.stream(words)
                .map(word -> word.length() > 2 ? word.toUpperCase() : word)
                .collect(Collectors.joining(""));
    }
}