package Glava13.TaskA;

import java.sql.*;
import java.util.*;

public class GetLetter {

    public List<Person> getWithLength() throws SQLException {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT p.*, MIN(CHAR_LENGTH(l.body)) AS min_letter_length " +
                "FROM persons p " +
                "JOIN letters l ON p.id = l.sender_id " +
                "GROUP BY p.id ORDER BY min_letter_length ASC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery())
        {

            while (rs.next())
            {
                persons.add(new Person(rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date")));
            }
        }
        return persons;
    }

    public List<Person> getWithSubject(String subject) throws SQLException {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT DISTINCT p.* " +
                "FROM persons p " +
                "JOIN letters l ON p.id = l.receiver_id " +
                "WHERE l.subject = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, subject);
            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next()) {
                    persons.add(new Person(rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getDate("birth_date")));
                }
            }
        }
        return persons;
    }

    public List<Person> getWithoutSubject(String subject) throws SQLException {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT DISTINCT p.* " +
                "FROM persons p " +
                "WHERE NOT EXISTS (SELECT 1 FROM letters l WHERE l.receiver_id = p.id AND l.subject = ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, subject);
            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                    persons.add(new Person(rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getDate("birth_date")));
                }
            }
        }
        return persons;
    }
}