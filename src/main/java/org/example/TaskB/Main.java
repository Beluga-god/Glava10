package org.example.TaskB;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Salad salad = new Salad();

        // Добавление овощей в салат
        salad.addIngredient(new Carrot(100));
        salad.addIngredient(new Tomat(150));
        salad.addIngredient(new Cucumber(200));

        // Подсчет калорийности
        System.out.println("Общая калорийность салата: " + salad.getTotalCalories() + " ккал");

        // Сортировка по весу
        salad.sortIngredientsByWeight();
        System.out.println("Сортированные ингредиенты по весу: " + salad);

        // Поиск овощей по диапазону калорийности
        double minCalories = 10;
        double maxCalories = 50;
        List<Vegetable> veggiesInRange = salad.findVegetablesByCaloriesRange(minCalories, maxCalories);
        System.out.println("Овощи в диапазоне " + minCalories + " - " + maxCalories + " ккал: " + veggiesInRange);

        // Сохранение салата в файл
        String filename = "Salat.ser";
        SaladConnector.saveSalad(salad, filename);

        // Загрузка салата из файла
        Salad loadedSalad = SaladConnector.loadSalad(filename);
        System.out.println("Загруженный салат из файла:");
        System.out.println(loadedSalad);

    }
}
