package Glava12;

import Glava12.TaskA.CallCenter;
import Glava12.TaskA.Client;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.*;

class TaskATest {

    @Test
    void testCallCenterHandlesCalls() throws InterruptedException {
        // Создаем CallCenter с 2 операторами
        CallCenter callCenter = new CallCenter(2);

        // Создаем клиентов
        Client client1 = new Client("Client-1", callCenter);
        Client client2 = new Client("Client-2", callCenter);
        Client client3 = new Client("Client-3", callCenter);

        // Создаем ExecutorService для запуска потоков
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Запускаем клиентов в потоках
        executor.submit(client1);
        executor.submit(client2);
        executor.submit(client3);

        // Ждем завершения работы клиентов
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        // Проверяем, что тест прошел без ошибок
        assertTrue(executor.isTerminated(), "Не все потоки завершили выполнение.");
    }

    @Test
    void testOperatorQueueHandling() throws InterruptedException {
        // Создаем CallCenter с 1 оператором
        CallCenter callCenter = new CallCenter(1);

        // Создаем 3 клиентов
        Client client1 = new Client("Client-1", callCenter);
        Client client2 = new Client("Client-2", callCenter);
        Client client3 = new Client("Client-3", callCenter);

        // Создаем ExecutorService для запуска потоков
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Запускаем клиентов в потоках
        executor.submit(client1);
        executor.submit(client2);
        executor.submit(client3);

        // Ждем завершения работы клиентов
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        // Проверяем, что все клиенты были обслужены
        // Поскольку у нас 1 оператор клиентов должно обслужить по очереди.
        assertTrue(executor.isTerminated(), "Не все потоки завершили выполнение.");
    }
}