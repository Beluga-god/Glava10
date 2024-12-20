package Glava14.TaskB;

import java.io.*;
import java.net.*;

public class SimpleServer {
    private static final int PORT = 9090;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }

    static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String encryptedMessage = reader.readLine();
                if (encryptedMessage == null) {
                    System.out.println("Клиент закрыл соединение");
                    return;
                }
                System.out.println("Получено от прокси: " + encryptedMessage);

                String decryptedMessage = decryptMessage(encryptedMessage);
                System.out.println("Расшифрованное сообщение: " + decryptedMessage);

                String response = "Сервер получил ваше сообщение: " + decryptedMessage;
                System.out.println("Отправка ответа клиенту: " + response);
                writer.println(response);
            } catch (IOException e) {
                System.err.println("Ошибка при взаимодействии с клиентом: " + e.getMessage());
            } finally {
                System.out.println("Завершена обработка запроса клиента");
            }
        }
    }

    public static String decryptMessage(String encryptedMessage) {
        int shift = 3; // Должен совпадать со сдвигом в прокси-сервере
        StringBuilder decryptedMessage = new StringBuilder();

        for (char c : encryptedMessage.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base - shift + 26) % 26 + base);
            }
            decryptedMessage.append(c);
        }

        return decryptedMessage.toString();
    }
}
