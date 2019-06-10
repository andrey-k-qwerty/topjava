package ru.javawebinar.topjava.model;

import java.util.List;

public interface MealDAO {
    List<Meal> getAllMeal();
    void add(Meal meal);
    void delete(Meal meal);
    void update(Meal meal);
    Meal  getByID(int id);
}
