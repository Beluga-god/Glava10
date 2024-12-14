package Glava10;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskATest {

    @Test
    void testFindMatchingWords() {
        // Тестовые данные
        List<String> lines = Arrays.asList(
                "apple",
                "elephant",
                "keyboard race egg"
        );

        // Ожидаемый результат
        List<String> expected = Arrays.asList("apple", "elephant", "race", "egg");

        // Вызов метода
        TaskA WordMatcher = null;
        List<String> result = WordMatcher.findMatchingWords(lines);

        // Проверка результата
        assertEquals(expected, result, "Список найденных слов не совпадает с ожидаемым");
    }

    @Test
    void testFindMatchingWords_NoMatches() {
        // Тестовые данные без совпадений
        List<String> lines = Arrays.asList(
                "cat",
                "dog",
                "mouse"
        );

        // Ожидаемый результат
        List<String> expected = List.of();

        // Вызов метода
        TaskA WordMatcher = null;
        List<String> result = WordMatcher.findMatchingWords(lines);

        // Проверка результата
        assertEquals(expected, result, "Метод должен возвращать пустой список при отсутствии совпадений");
    }

    @Test
    void testFindMatchingWords_EmptyInput() {
        // Пустой ввод
        List<String> lines = List.of();

        // Ожидаемый результат
        List<String> expected = List.of();

        // Вызов метода
        TaskA WordMatcher = null;
        List<String> result = WordMatcher.findMatchingWords(lines);

        // Проверка результата
        assertEquals(expected, result, "Метод должен возвращать пустой список для пустого ввода");
    }

    @Test
    void testFindMatchingWords_SingleLineMatch() {
        // Тестовые данные: одна строка с совпадением
        List<String> lines = List.of("race egg");

        // Ожидаемый результат
        List<String> expected = List.of("race", "egg");

        // Вызов метода
        TaskA WordMatcher = null;
        List<String> result = WordMatcher.findMatchingWords(lines);

        // Проверка результата
        assertEquals(expected, result, "Метод должен корректно обрабатывать одну строку с совпадением");
    }

    @Test
    void testFindMatchingWords_IgnoreCase() {
        // Тестовые данные с разным регистром
        List<String> lines = List.of("Apple", "elephant");

        // Ожидаемый результат
        List<String> expected = List.of("Apple", "elephant");

        // Вызов метода
        TaskA WordMatcher = null;
        List<String> result = WordMatcher.findMatchingWords(lines);

        // Проверка результата
        assertEquals(expected, result, "Метод должен учитывать регистр букв");
    }
}