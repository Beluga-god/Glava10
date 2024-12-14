package Glava12;

import Glava12.TaskA.CallCenter;
import Glava12.TaskA.Client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int numberOfOperators = 3; // Количество операторов
        int numberOfClients = 10; // Количество клиентов

        CallCenter callCenter = new CallCenter(numberOfOperators);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfClients);

        for (int i = 1; i <= numberOfClients; i++)
        {
            String clientName = "Клиент " + i;
            executorService.submit(new Client(clientName, callCenter));
        }

        executorService.shutdown();
    }
}
