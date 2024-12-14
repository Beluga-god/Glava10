package Glava10.TaskB;

import java.io.Serializable;
import java.util.Objects;

public abstract class Vegetable implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private double weight;
    private double caloriesPer100g;

    public Vegetable(String name, double weight, double caloriesPer100g)
    {
        this.name = name;
        this.weight = weight;
        this.caloriesPer100g = caloriesPer100g;
    }

    public double getCalories()
    {
        return (caloriesPer100g / 100) * weight;
    }

    public double getWeight()
    {
        return weight;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vegetable vegetable = (Vegetable) o;
        return Double.compare(vegetable.weight, weight) == 0 &&
                Double.compare(vegetable.caloriesPer100g, caloriesPer100g) == 0 &&
                Objects.equals(name, vegetable.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, weight, caloriesPer100g);
    }

    @Override
    public String toString()
    {
        return name + " (" + weight + "г, " + getCalories() + " ккал)";
    }
}