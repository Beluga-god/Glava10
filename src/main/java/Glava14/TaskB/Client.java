package Glava14.TaskB;

import java.io.*;
import java.net.*;

// Запускать в последнюю очередь
public class Client {
    private static final String PROXY_HOST = "localhost";  // Адрес прокси-сервера
    private static final int PROXY_PORT = 8080;            // Порт прокси-сервера

    public static void main(String[] args) {
        try (Socket socket = new Socket(PROXY_HOST, PROXY_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Подключено к прокси-серверу");

            // Отправляем сообщение прокси-серверу
            System.out.print("Введите сообщение для отправки на прокси: ");
            String message = userInput.readLine();
            out.println(message);

            // Получаем ответ от прокси-сервера
            String response = in.readLine();
            System.out.println("Ответ от сервера (через прокси): " + response);

        } catch (IOException e) {
            System.err.println("Ошибка при взаимодействии с клиентом и сервером: " + e.getMessage());
        }
    }
}
