package Glava11;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskA {

    // Метод для сортировки строк стихотворения по длине
    public static List<String> sortPoem(List<String> poemLines)
    {
        if (poemLines == null)
        {
            return new ArrayList<>();  // Возвращаем пустой список, если входной список null
        }

        // Сортировка с использованием Stream API
        return poemLines.stream()
                .sorted(Comparator.comparingInt(String::length))  // Сортировка по длине
                .collect(Collectors.toList());  // Собираем отсортированные строки в новый список
    }
}
