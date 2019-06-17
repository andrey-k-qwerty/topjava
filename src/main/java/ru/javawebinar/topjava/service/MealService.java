package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    Meal create(Meal meal, int UserId);

    void delete(int id, int UserId) throws NotFoundException;

    Meal get(int id, int UserId) throws NotFoundException;

    void update(Meal meal, int UserId);

    List<MealTo> getAll(int UserId);

    List<MealTo> filterByDateTime(LocalDate beginData, LocalDate endData, LocalTime beginTime, LocalTime endTime, int UserId);

    List<MealTo> filterByDateTime(LocalDateTime beginDataTime, LocalDateTime endDataTime, int UserId);
}