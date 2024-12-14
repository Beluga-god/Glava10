package Glava11;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> poem = new ArrayList<>();
        poem.add("Буря мглою небо кроет,");
        poem.add("Вихри снежные крутя;");
        poem.add("То, как зверь, она завоет,");
        poem.add("То заплачет, как дитя,");
        poem.add("То по кровле обветшалой");
        poem.add("Вдруг соломой зашумит,");
        poem.add("То, как путник запоздалый,");
        poem.add("К нам в окошко застучит.");


        System.out.println("Оригинальный стих:");
        poem.forEach(System.out::println);

        List<String> sortedPoem = TaskA.sortPoem(poem);

        System.out.println("\nОтсортированный по длине строк:");
        sortedPoem.forEach(System.out::println);

        System.out.println("\n\n");
        // TaskB

        TaskB graph = new TaskB(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);

        System.out.println("Количество вершин в графе: " + graph.getVertexCount());
        System.out.println("Смежные вершины 0: " + graph.getAdjacentVertices(0));
        System.out.println("Смежные вершины 1: " + graph.getAdjacentVertices(1));
        System.out.println("Есть ли ребро между 0 и 1: " + graph.hasEdge(0, 1));
        System.out.println("Есть ли ребро между 2 и 3: " + graph.hasEdge(2, 3));


        graph.removeEdge(0, 1);
        System.out.println("Смежные вершины 0 после удаления ребра (0, 1): " + graph.getAdjacentVertices(0));
    }
}