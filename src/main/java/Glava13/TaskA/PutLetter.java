package Glava13.TaskA;

import java.sql.*;

public class PutLetter {

    public void putLetter(int senderId, String subject, String body, int[] receiverIds) throws SQLException {
        String query = "INSERT INTO letters (sender_id, receiver_id, subject, body, sent_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                for (int receiverId : receiverIds)
                {
                    stmt.setInt(1, senderId);
                    stmt.setInt(2, receiverId);
                    stmt.setString(3, subject);
                    stmt.setString(4, body);
                    stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                    stmt.addBatch(); // Добавляем письмо в пакет
                }
                stmt.executeBatch(); // Выполняем все вставки за раз
            }
        }
    }
}