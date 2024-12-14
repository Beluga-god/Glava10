package org.example.TaskB;

import java.io.*;
import java.util.List;


public class SaladConnector {

    public static void saveSalad(Salad salad, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(salad);
            System.out.println("Салат сохранен в файл: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении салата: " + e.getMessage());
        }
    }

    public static Salad loadSalad(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Salad) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке салата: " + e.getMessage());
            return null;
        }
    }
}

