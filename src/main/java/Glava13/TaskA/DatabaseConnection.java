package Glava13.TaskA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/Gl13";
    private static final String USER = "postgres";
    private static final String PASSWORD = "pass";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException
    {
        if (connection == null || connection.isClosed())
        {
            synchronized (DatabaseConnection.class)
            {
                if (connection == null || connection.isClosed())
                {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                }
            }
        }
        return connection;
    }
}