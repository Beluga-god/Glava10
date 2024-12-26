package Glava14.TaskA;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final int SERVER_PORT = 6000; // Порт сервера
    private static final String CLIENTS_FILE = "clients.txt"; // Файл для хранения списка клиентов
    private static final List<ClientHandler> connectedClients = new CopyOnWriteArrayList<>(); // Список клиентов
    private static int clientIdCounter = 0; // Счётчик для генерации уникальных ID
    public static volatile boolean isRunning = true;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Сервер запущен на порту " + SERVER_PORT);
            loadClientsFromFile();

            // Поток для отправки сообщений клиентам
            Thread messageSenderThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                // Ожидание подключения клиентов
                while (isRunning) {
                    if (connectedClients.isEmpty()) {
                        System.out.println("Нет подключенных клиентов");
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            System.out.println("Поток отправки сообщений прерван. Завершение работы...");
                            return;
                        }
                        continue;
                    }

                    System.out.println("Подключенные клиенты:");
                    for (int i = 0; i < connectedClients.size(); i++) {
                        System.out.println("ID " + i + ": " + connectedClients.get(i).getClientInfo());
                    }

                    // Отправка сообщения после нажатия Enter
                    System.out.println("Нажмите Enter, чтобы отправить сообщение...");
                    String inputEnter = scanner.nextLine();
                    if (inputEnter.isEmpty() && isRunning) {
                        try {
                            System.out.print("Введите ID клиентов для отправки сообщения (через запятую): ");
                            String input = scanner.nextLine();
                            List<ClientHandler> selectedClients = parseClientSelection(input);

                            System.out.print("Введите сообщение для отправки: ");
                            String message = scanner.nextLine();
                            if (message.trim().isEmpty()) {
                                System.out.println("Сообщение не может быть пустым");
                                continue;
                            }
                            sendMessageClients(message, selectedClients);
                        } catch (Exception e) {
                            System.out.println("Ошибка при вводе: " + e.getMessage());
                        }
                    }
                }
                System.out.println("Поток отправки сообщений остановлен");
            });
            messageSenderThread.setDaemon(true);
            messageSenderThread.start();

            // Ожидание подключения клиентов
            waitClient(serverSocket);
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    private static void loadClientsFromFile() {
        File file = new File(CLIENTS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Считывание ID и информации о клиенте из файла
                    String[] parts = line.split(":");
                    if (parts.length == 3) {
                        String clientId = parts[0].trim();
                        String clientInfo = parts[1] + ":" + parts[2];
                        System.out.println("Загружен клиент: ID " + clientId + ", " + clientInfo);
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка при загрузке данных из файла: " + e.getMessage());
            }
        }
    }

    private static void waitClient(ServerSocket serverSocket) throws IOException {
        while (isRunning) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, clientIdCounter++);
            connectedClients.add(clientHandler);
            saveClientsToFile(); // После подключения клиента сохраняем список
            new Thread(clientHandler).start();
            System.out.println("Новый клиент подключен: ID " + clientHandler.getClientId() + " " + clientHandler.getClientInfo());
        }
    }

    private static List<ClientHandler> parseClientSelection(String input) {
        List<ClientHandler> selectedClients = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Не указаны ID клиентов");
            return selectedClients;
        }
        try {
            String[] indices = input.split(",");
            for (String index : indices) {
                int clientIndex = Integer.parseInt(index.trim());
                if (clientIndex >= 0 && clientIndex < connectedClients.size()) {
                    selectedClients.add(connectedClients.get(clientIndex));
                } else {
                    System.out.println("Неверный ID клиента: " + clientIndex);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ввода. Введите ID клиентов через запятую");
        }
        return selectedClients;
    }

    private static void sendMessageClients(String message, List<ClientHandler> selectedClients) {
        if (selectedClients.isEmpty()) {
            System.out.println("Клиенты не выбраны. Сообщение не отправлено");
            return;
        }
        for (ClientHandler clientHandler : selectedClients) {
            boolean success = clientHandler.sendMessage(message);
            String status = success ? " отправлено успешно" : " не удалось отправить";
            System.out.println("Сообщение клиенту " + clientHandler.getClientInfo() + status);
        }
    }

    private static void saveClientsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTS_FILE))) {
            for (ClientHandler clientHandler : connectedClients) {
                writer.write(clientHandler.getClientId() + ": " + clientHandler.getClientInfo());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении клиентов в файл: " + e.getMessage());
        }
    }

    // Остановка всех потоков
    public static void shutdown() {
        isRunning = false;
        connectedClients.forEach(client -> {
            try {
                client.socket.close();
            } catch (IOException ignored) {}
        });
        connectedClients.clear();
        System.out.println("Сервер остановлен");
    }

    // Вспомогательный класс для работы с клиентами
    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private final BufferedWriter writer;
        private final int clientId;

        ClientHandler(Socket socket, int clientId) throws IOException {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientId = clientId;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Сообщение от " + getClientInfo() + ": " + message);

                    // Отправляем подтверждение клиенту
                    writer.write("Сообщение получено: " + message);
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException e) {
                System.err.println("Клиент отключился: " + getClientInfo());
            } finally {
                try {
                    socket.close();
                    connectedClients.remove(this);
                    saveClientsToFile(); // После отключения клиента обновляем файл
                    System.out.println("Клиент удален: " + getClientInfo());
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии соединения с клиентом: " + e.getMessage());
                }
            }
        }

        public boolean sendMessage(String message) {
            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
                return true;
            } catch (IOException e) {
                System.err.println("Не удалось отправить сообщение клиенту " + getClientInfo() + ": " + e.getMessage());
                return false;
            }
        }

        public String getClientInfo() {
            return socket.getInetAddress() + ":" + socket.getPort();
        }

        public int getClientId() {
            return clientId;
        }
    }
}
