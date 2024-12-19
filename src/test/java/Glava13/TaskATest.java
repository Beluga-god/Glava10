package Glava13;

import Glava13.TaskA.GetLetter;
import Glava13.TaskA.Person;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskATest {

    @Test
    void testGetUsersWithMinLetterLength() throws SQLException {
        GetLetter getLetter = new GetLetter();
        List<Person> users = getLetter.getWithLength();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    void testGetUsersWithReceivedSubject() throws SQLException {
        GetLetter getLetter = new GetLetter();
        List<Person> users = getLetter.getWithSubject("Java");
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    void testGetUsersWithoutReceivedSubject() throws SQLException {
        GetLetter getLetter = new GetLetter();
        List<Person> users = getLetter.getWithoutSubject("Java");
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }
}