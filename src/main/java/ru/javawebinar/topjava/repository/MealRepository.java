package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal , int UserId);

    // false if not found
    boolean delete(int id , int UserId);

    // null if not found
    Meal get(int id, int UserId);

    Collection<Meal> getAll( int UserId);

    Collection<Meal> filterByDateTime(LocalDateTime beginDataTime, LocalDateTime endDataTime,   int UserId);

    Collection<Meal> filterByDateTime(LocalDate beginData, LocalDate endData, LocalTime beginTime,  LocalTime endTime,  int UserId);
}
