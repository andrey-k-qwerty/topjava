package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal , int UserId);

    // false if not found
    boolean delete(int id , int UserId);

    // null if not found
    Meal get(int id, int UserId);

    Collection<Meal> getAll( int UserId);
}
