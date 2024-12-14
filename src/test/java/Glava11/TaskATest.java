package Glava11;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskATest {

    //    Тест на сортировку по длине
    @Test
    void testSortPoemLinesByLength_withValidLines() {
        List<String> input = Arrays.asList(
                "Мороз и солнце; день чудесный!",
                "Еще ты дремлешь, друг прелестный -",
                "Пора, красавица, проснись:",
                "Открой сомкнуты негой взоры",
                "Навстречу северной Авроры,",
                "Звездою севера явись!"
        );
        List<String> expected = Arrays.asList(
                "Звездою севера явись!",
                "Пора, красавица, проснись:",
                "Навстречу северной Авроры,",
                "Открой сомкнуты негой взоры",
                "Мороз и солнце; день чудесный!",
                "Еще ты дремлешь, друг прелестный -"

        );

        List<String> result = TaskA.sortPoem(input);
        assertEquals(expected, result, "Сортировка строк по длине выполнена неверно");
    }

    //    Тест на пустой входной список
    @Test
    void testSortPoemLinesByLength_withEmptyList() {
        List<String> input = Collections.emptyList();
        List<String> result = TaskA.sortPoem(input);

        assertTrue(result.isEmpty(), "Результат должен быть пустым для пустого входного списка");
    }

    //    Тест на null
    @Test
    void testSortPoemLinesByLength_withNullInput() {
        List<String> result = TaskA.sortPoem(null);

        assertTrue(result.isEmpty(), "Результат должен быть пустым для null входного значения");
    }

    //    Тест на равную длину строк
    @Test
    void testSortPoemLinesByLength_withEqualLengthLines() {
        List<String> input = Arrays.asList("Строка", "Другая", "Курьер");
        List<String> expected = Arrays.asList("Строка", "Другая", "Курьер");

        List<String> result = TaskA.sortPoem(input);
        assertEquals(expected, result, "Порядок строк одинаковой длины должен сохраняться");
    }

    //    Тест на список из одной строки
    @Test
    void testSortPoemLinesByLength_withSingleLine() {
        List<String> input = Collections.singletonList("Только одна строка");
        List<String> result = TaskA.sortPoem(input);

        assertEquals(input, result, "Список из одной строки должен остаться неизменным");
    }
}