package Glava11;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskBTest {

    @Test
    void testGraphInitialization() {
        TaskB graph = new TaskB(5);
        assertEquals(5, graph.getVertexCount(), "Количество вершин в графе должно быть 5");

        for (int i = 0; i < 5; i++) {
            assertTrue(graph.getAdjacentVertices(i).isEmpty(), "Список смежных вершин должен быть пустым для новой вершины");
        }
    }

    @Test
    void testAddEdge() {
        TaskB graph = new TaskB(4);
        graph.addEdge(0, 1);

        assertTrue(graph.hasEdge(0, 1), "Ребро между вершинами 0 и 1 должно существовать");
        assertTrue(graph.hasEdge(1, 0), "Ребро между вершинами 1 и 0 должно существовать (неориентированный граф)");
        assertEquals(Set.of(1), graph.getAdjacentVertices(0), "Вершина 0 должна быть смежна с вершиной 1");
        assertEquals(Set.of(0), graph.getAdjacentVertices(1), "Вершина 1 должна быть смежна с вершиной 0");
    }

    @Test
    void testRemoveEdge() {
        TaskB graph = new TaskB(3);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);

        assertFalse(graph.hasEdge(0, 1), "Ребро между вершинами 0 и 1 не должно существовать после удаления");
        assertFalse(graph.hasEdge(1, 0), "Ребро между вершинами 1 и 0 не должно существовать после удаления");
        assertTrue(graph.getAdjacentVertices(0).isEmpty(), "Список смежных вершин для вершины 0 должен быть пустым");
        assertTrue(graph.getAdjacentVertices(1).isEmpty(), "Список смежных вершин для вершины 1 должен быть пустым");
    }

    @Test
    void testHasEdge() {
        TaskB graph = new TaskB(2);
        graph.addEdge(0, 1);

        assertTrue(graph.hasEdge(0, 1), "Ребро между вершинами 0 и 1 должно существовать");
        assertFalse(graph.hasEdge(0, 0), "Вершина 0 не должна быть соединена сама с собой");
    }

    @Test
    void testInvalidVertex() {
        TaskB graph = new TaskB(3);

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(0, 3), "Ожидается исключение для недопустимой вершины");
        assertThrows(IllegalArgumentException.class, () -> graph.getAdjacentVertices(3), "Ожидается исключение для недопустимой вершины");
        assertThrows(IllegalArgumentException.class, () -> graph.hasEdge(0, -1), "Ожидается исключение для отрицательной вершины");
    }

    @Test
    void testEmptyGraph() {
        assertThrows(IllegalArgumentException.class, () -> new TaskB(0), "Граф должен содержать хотя бы одну вершину");
    }

    @Test
    void testMultipleEdges() {
        TaskB graph = new TaskB(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        assertEquals(Set.of(1, 2), graph.getAdjacentVertices(0), "Вершина 0 должна быть смежна с вершинами 1 и 2");
        assertEquals(Set.of(0), graph.getAdjacentVertices(1), "Вершина 1 должна быть смежна только с вершиной 0");
        assertEquals(Set.of(0), graph.getAdjacentVertices(2), "Вершина 2 должна быть смежна только с вершиной 0");
    }
}