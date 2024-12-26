package Glava14.TaskA;

import java.io.*;
import java.net.Socket;

public class Client1 {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Адрес сервера
        int serverPort = 6000; // Порт сервера

        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {

            System.out.println("Соединение с сервером установлено. Ожидание сообщений...");

            // Чтение сообщений от сервера
            String message;
            while ((message = reader.readLine()) != null)
            {
                System.out.println("Сообщение от сервера: " + message);
            }

        } catch (IOException e)
        {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
