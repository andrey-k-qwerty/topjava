package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DataSource {
    private static final AtomicInteger AUTO_ID = new AtomicInteger(1);
    private static DataSource ourInstance = new DataSource();
    private static List<Meal> meals = new CopyOnWriteArrayList<>();

    static {
        meals.addAll(Arrays.asList(
                new Meal(AUTO_ID.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(AUTO_ID.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(AUTO_ID.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(AUTO_ID.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(AUTO_ID.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(AUTO_ID.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
                )
        );

    }

    public static DataSource getInstance() {
        return ourInstance;
    }

    private DataSource() {
    }

    public List<Meal> getAllMeals() {
        return meals;
    }

    public void addMeal(Meal meal) {
        if (meal.getId() == 0)
            meal.setId(AUTO_ID.getAndIncrement());
        meals.add(meal);
    }

    public void deleteMeal(Meal meal) {
        meals.removeIf(ml -> ml.getId() == meal.getId());
    }

    public void updateMeal(Meal meal) {

        meals.parallelStream().filter(m -> m.getId() == meal.getId()).forEach(m -> {
            m.setDateTime(meal.getDateTime());
            m.setDescription(meal.getDescription());
            m.setCalories(meal.getCalories());
        });

    }

    public Meal getMealById(int id) {
        return meals.parallelStream().filter(m -> m.getId() == id).findFirst().get();
    }

}
