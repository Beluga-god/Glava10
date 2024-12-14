package Glava10;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TaskCTest {

    private File inputFile;
    private File outputFile;

    @BeforeEach
    void setUp() throws IOException {
        // Создание временных файлов для входного и выходного содержимого
        inputFile = File.createTempFile("input", ".txt");
        outputFile = File.createTempFile("output", ".txt");
    }

    @AfterEach
    void tearDown() {
        // Удаление временных файлов после выполнения теста
        inputFile.delete();
        outputFile.delete();
    }

    private void writeToFile(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }

    private String readFromFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        // Убираем последний перевод строки
        if (!content.isEmpty()) {
            content.setLength(content.length() - System.lineSeparator().length());
        }
        return content.toString();
    }

    @Test
    public void testProcessFile_StandardCase() throws IOException {
        // Создаем временные файлы
        File inputFile = File.createTempFile("input", ".txt");
        File outputFile = File.createTempFile("output", ".txt");

        // Заполняем входной файл
        String inputContent = """
                public class Example {
                    private int number;
                    public void method() {
                        System.out.println("hello world");
                    }
                }""";

        // Ожидаемое содержимое
        String expected = """
                PUBLIC CLASS EXAMPLE {
                    PRIVATE INT NUMBER;
                    PUBLIC VOID METHOD() {
                        SYSTEM.OUT.PRINTLN("HELLO WORLD");
                    }
                }
                """;

        // Запись содержимого во входной файл
        writeToFile(inputFile, inputContent);

        // Обрабатываем файл
        TaskC LowerToUpper = null;
        LowerToUpper.processFile(inputFile, outputFile);

        // Читаем выходной файл
        String actual;
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            actual = reader.lines().collect(Collectors.joining("\n")); // Нормализация перевода строк
        }

        // Сравниваем строки
        assertEquals(expected.trim(), actual.trim());

        // Удаляем временные файлы
        inputFile.deleteOnExit();
        outputFile.deleteOnExit();
    }

    @Test
    void testProcessFile_EmptyFile() throws IOException {
        // Пустой входной файл
        writeToFile(inputFile, "");

        // Вызов тестируемого метода
        TaskC LowerToUpper = null;
        LowerToUpper.processFile(inputFile, outputFile);

        // Проверка результата
        String actualContent = readFromFile(outputFile);
        assertTrue(actualContent.isEmpty(), "Выходной файл должен быть пустым для пустого входного файла");
    }

    @Test
    void testProcessFile_ShortWords() throws IOException {
        // Входной файл с короткими словами
        String inputContent = "a bc de fg";
        String expectedContent = "a bc de fg"; // Никакие слова не преобразуются

        writeToFile(inputFile, inputContent);

        // Вызов тестируемого метода
        TaskC LowerToUpper = null;
        LowerToUpper.processFile(inputFile, outputFile);

        // Проверка результата
        String actualContent = readFromFile(outputFile);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void testProcessFile_WithPunctuation() throws IOException {
        // Входной файл с пунктуацией
        String inputContent = "Hello, world! This is a test.";
        String expectedContent = "HELLO, WORLD! THIS is a TEST.";

        writeToFile(inputFile, inputContent);

        // Вызов тестируемого метода
        TaskC LowerToUpper = null;
        LowerToUpper.processFile(inputFile, outputFile);

        // Проверка результата
        String actualContent = readFromFile(outputFile);
        assertEquals(expectedContent, actualContent);
    }
}