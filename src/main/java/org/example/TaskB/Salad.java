package org.example.TaskB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Salad implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Vegetable> ingredients;

    public Salad() {
        this.ingredients = new ArrayList<>();
    }

    public void addIngredient(Vegetable vegetable) {
        ingredients.add(vegetable);
    }

    public double getTotalCalories() {
        return ingredients.stream().mapToDouble(Vegetable::getCalories).sum();
    }

    public void sortIngredientsByWeight() {
        ingredients.sort(Comparator.comparingDouble(Vegetable::getWeight));
    }

    public List<Vegetable> findVegetablesByCaloriesRange(double minCalories, double maxCalories) {
        return ingredients.stream()
                .filter(veg -> veg.getCalories() >= minCalories && veg.getCalories() <= maxCalories)
                .collect(Collectors.toList());
    }

    public List<Vegetable> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "Ингредиенты салата: " + ingredients;
    }
}