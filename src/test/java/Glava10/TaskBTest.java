package Glava10;

import Glava10.TaskB.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskBTest {

    private Salad salad;
    private final String filename = "Salat.ser";

    @BeforeEach
    public void setUp() {
        salad = new Salad();
        salad.addIngredient(new Carrot(100));
        salad.addIngredient(new Tomat(150));
        salad.addIngredient(new Cucumber(220));
    }

    @Test
    public void testAddIngredients() {
        assertEquals(3, salad.getIngredients().size(), "Количество ингредиентов должно быть 3");
    }

    @Test
    public void testTotalCalories() {
        double expectedCalories = 103.0;  // 20 * 150 / 15 * 220/ 40 * 100/
        assertEquals(expectedCalories, salad.getTotalCalories(), 0.1, "Общая калорийность салата вычислена неправильно");
    }



    @Test
    public void testSortIngredientsByWeight() {
        salad.sortIngredientsByWeight();
        assertTrue(salad.getIngredients().get(0).getWeight() <= salad.getIngredients().get(1).getWeight(),
                "Ингредиенты не отсортированы по весу");
    }

    @Test
    public void testFindVegetablesByCaloriesRange() {
        List<Vegetable> veggies = salad.findVegetablesByCaloriesRange(10, 60);
        assertEquals(3, veggies.size(), "Найдено неправильное количество овощей в заданном диапазоне калорийности");
    }

    @Test
    public void testSaveAndLoadSalad() {
        SaladConnector.saveSalad(salad, filename);

        // Убедимся что файл существует после сохранения
        File file = new File(filename);
        assertTrue(file.exists(), "Файл не существует после сохранения");

        Salad loadedSalad = SaladConnector.loadSalad(filename);
        assertNotNull(loadedSalad, "Загруженный салат не должен быть null");

        // Проверим что загруженный салат имеет одинаковое количество ингредиентов
        assertEquals(salad.getIngredients().size(), loadedSalad.getIngredients().size(),
                "Количество ингредиентов в загруженном салате должно быть равно количеству в сохраненном");

        // Очистим файл после теста
        file.delete();
    }

}
