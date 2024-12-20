package Glava14;

import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;
import Glava14.TaskB.*;

import static org.junit.jupiter.api.Assertions.*;

public class TaskBTest {

    private ProxyServer.ClientHandler proxyHandler;

    @Test
    @DisplayName("Test Caesar cipher encryption in ProxyServer")
    void testModifyMessage() {
        proxyHandler = new ProxyServer.ClientHandler(null); // Мок объекта не требуется для проверки метода

        String original = "hello";
        String encrypted = proxyHandler.modifyMessage(original);

        assertEquals("khoor", encrypted, "Encryption with Caesar cipher failed");
    }

    @Test
    @DisplayName("Test Caesar cipher decryption in SimpleServer")
    void testDecryptMessage() {
        String encrypted = "khoor";
        String decrypted = SimpleServer.decryptMessage(encrypted);

        assertEquals("hello", decrypted, "Decryption with Caesar cipher failed");
    }

    @Test
    @DisplayName("Test client-proxy-server interaction")
    void testClientProxyServerInteraction() throws IOException {
        // Создаем тестовый сервер
        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            // В отдельном потоке запускаем обработку запроса сервера
            new Thread(() -> {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String encryptedMessage = reader.readLine();
                    assertNotNull(encryptedMessage, "Encrypted message from proxy is null");
                    assertEquals("khoor", encryptedMessage, "Proxy did not forward the correct encrypted message");

                    String decryptedMessage = SimpleServer.decryptMessage(encryptedMessage);
                    assertEquals("hello", decryptedMessage, "Decrypted message is incorrect");

                    writer.println("Server received your message: " + decryptedMessage);

                } catch (IOException e) {
                    fail("Server failed to handle request: " + e.getMessage());
                }
            }).start();

            // Создаем тестовый прокси
            try (ServerSocket proxySocket = new ServerSocket(8080)) {
                new Thread(() -> {
                    try (Socket clientSocket = proxySocket.accept();
                         BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                         PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                         Socket serverConnection = new Socket("localhost", 9090);
                         BufferedReader serverReader = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
                         PrintWriter serverWriter = new PrintWriter(serverConnection.getOutputStream(), true)) {

                        String clientMessage = clientReader.readLine();
                        assertNotNull(clientMessage, "Client message is null");
                        assertEquals("hello", clientMessage, "Client did not send the correct message");

                        String modifiedMessage = new ProxyServer.ClientHandler(null).modifyMessage(clientMessage);
                        serverWriter.println(modifiedMessage);

                        String serverResponse = serverReader.readLine();
                        assertNotNull(serverResponse, "Server response is null");
                        assertEquals("Server received your message: hello", serverResponse, "Server response is incorrect");

                        clientWriter.println(serverResponse);

                    } catch (IOException e) {
                        fail("Proxy failed to handle request: " + e.getMessage());
                    }
                }).start();

                // Создаем тестовый клиент
                try (Socket clientSocket = new Socket("localhost", 8080);
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    out.println("hello");
                    String response = in.readLine();
                    assertNotNull(response, "Client did not receive a response");
                    assertEquals("Server received your message: hello", response, "Client received incorrect response");

                }
            }
        }
    }
}