package Glava11;


import java.util.*;

public class TaskB {
    private final int vertices;
    private final Map<Integer, Set<Integer>> adjacencyList;

    // Конструктор графа
    public TaskB(int vertices)
    {
        if (vertices <= 0) {
            throw new IllegalArgumentException("Количество вершин должно быть положительным");
        }
        this.vertices = vertices;
        this.adjacencyList = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.put(i, new HashSet<>());
        }
    }

    // Добавить ребро между двумя вершинами
    public void addEdge(int v1, int v2)
    {
        validateVertex(v1);
        validateVertex(v2);

        adjacencyList.get(v1).add(v2);
        adjacencyList.get(v2).add(v1); // так как граф неориентированный
    }

    // Удалить ребро между двумя вершинами
    public void removeEdge(int v1, int v2)
    {
        validateVertex(v1);
        validateVertex(v2);

        adjacencyList.get(v1).remove(v2);
        adjacencyList.get(v2).remove(v1);
    }

    // Получить список смежных вершин для заданной вершины
    public Set<Integer> getAdjacentVertices(int vertex)
    {
        validateVertex(vertex);
        return Collections.unmodifiableSet(adjacencyList.get(vertex));
    }

    // Проверить, существует ли ребро между двумя вершинами
    public boolean hasEdge(int v1, int v2)
    {
        validateVertex(v1);
        validateVertex(v2);

        return adjacencyList.get(v1).contains(v2);
    }

    // Получить количество вершин в графе
    public int getVertexCount() {
        return vertices;
    }

    // Проверить корректность вершины
    private void validateVertex(int vertex)
    {
        if (vertex < 0 || vertex >= vertices) {
            throw new IllegalArgumentException("Вершина " + vertex + " вне допустимого диапазона 0-" + (vertices - 1));
        }
    }
}