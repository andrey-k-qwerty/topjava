package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {
    Meal create(Meal meal, int UserId);

    void delete(int id, int UserId) throws NotFoundException;

    Meal get(int id, int UserId) throws NotFoundException;

    void update(Meal meal, int UserId);

    List<Meal> getAll(int UserId);
}