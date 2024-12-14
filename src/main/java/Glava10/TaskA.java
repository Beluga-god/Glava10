package Glava10;

import java.util.ArrayList;
import java.util.List;

public class TaskA {

    public static List<String> findMatchingWords(List<String> lines)
        {
        List<String> matchingWords = new ArrayList<>();
        // Объединяем все строки в один текст
        StringBuilder fullText = new StringBuilder();
        for (String line : lines) {
            fullText.append(line).append(" ");
        }

        // Разбиваем текст на слова
        String[] words = fullText.toString().trim().split("\\s+");

        // Ищем совпадения
        for (int i = 0; i < words.length - 1; i++)
        {
            String word1 = words[i];
            String word2 = words[i + 1];

            // Проверяем совпадение последней буквы и первой буквы
            if (word1.charAt(word1.length() - 1) == word2.charAt(0))
            {
                matchingWords.add(word1);
                matchingWords.add(word2);
            }
        }

        return matchingWords;
    }
}
