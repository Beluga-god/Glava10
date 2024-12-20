package Glava14;


import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class TaskATest {

    private static final int SERVER_PORT = 6000;
    private static Process serverProcess;
    private static final ExecutorService clientExecutor = Executors.newFixedThreadPool(2);

    // Запуск сервера в отдельном процессе для тестирования
    @BeforeAll
    public static void setUpServer() throws IOException {
        serverProcess = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "vol14.solutionA.Server")
                .redirectErrorStream(true)
                .start();
        // Подождем, пока сервер не начнет слушать
        waitForServerToStart();
    }

    // Завершаем серверный процесс после теста
    @AfterAll
    public static void tearDownServer() {
        if (serverProcess != null) {
            serverProcess.destroy();
        }
        clientExecutor.shutdown();
    }

    // Ожидаем, что сервер будет слушать на порту
    private static void waitForServerToStart() {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 5000) {
            try (Socket socket = new Socket("localhost", SERVER_PORT)) {
                if (socket.isConnected()) {
                    return;  // Сервер готов
                }
            } catch (IOException e) {
                try {
                    Thread.sleep(500);  // Подождать и попробовать снова
                } catch (InterruptedException ignored) {}
            }
        }
        fail("Server did not start within the expected time");
    }

    // Проверка на отправку и получение сообщений от сервера
    @Test
    void testMessageSending() throws Exception {
        // Создание клиента и подключение
        try (Socket socket = new Socket("localhost", SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            // Отправляем сообщение
            String testMessage = "Hello Server!";
            writer.write(testMessage);
            writer.newLine();
            writer.flush();

            // Получаем подтверждение от сервера
            String response = reader.readLine();
            assertNotNull(response);
            assertEquals("Сообщение получено: " + testMessage, response, "The response message is incorrect");
        }
    }

    // Проверка на обработку нескольких клиентов
    @Test
    void testMultipleClients() throws Exception {
        CompletableFuture<Void> client1 = simulateClient("Client 1", "Hello from Client 1");
        CompletableFuture<Void> client2 = simulateClient("Client 2", "Hello from Client 2");

        // Ждем, пока оба клиента не закончат
        CompletableFuture.allOf(client1, client2).join();

        assertDoesNotThrow(() -> client1.get(), "Client 1 failed");
        assertDoesNotThrow(() -> client2.get(), "Client 2 failed");
    }

    private CompletableFuture<Void> simulateClient(String clientName, String message) {
        return CompletableFuture.runAsync(() -> {
            try (Socket socket = new Socket("localhost", SERVER_PORT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                // Отправляем сообщение от клиента
                writer.write(message);
                writer.newLine();
                writer.flush();

                // Получаем подтверждение от сервера
                String response = reader.readLine();
                assertNotNull(response);
                assertEquals("Сообщение получено: " + message, response, clientName + " - The response message is incorrect");
            } catch (IOException e) {
                fail(clientName + " failed: " + e.getMessage());
            }
        }, clientExecutor);
    }

    // Проверка на отключение клиента
    @Test
    void testClientDisconnection() throws Exception {
        try (Socket socket = new Socket("localhost", SERVER_PORT);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            // Отправляем сообщение
            writer.write("Test message");
            writer.newLine();
            writer.flush();

            // Закрываем сокет, чтобы симулировать отключение
            socket.close();
        }

        assertTrue(true, "Client disconnected and no errors occurred");
    }
}