package ru.javawebinar.topjava.model;

import java.util.List;

public class MealDAOImpl implements MealDAO {
    private DataSource dataSource = DataSource.getInstance();
    @Override
    public List<Meal> getAllMeal() {
        return dataSource.getAllMeals();
    }

    @Override
    public void add(Meal meal) {
        dataSource.addMeal(meal);
    }

    @Override
    public void delete(Meal meal) {
        dataSource.deleteMeal(meal);
    }

    @Override
    public void update(Meal meal) {
        dataSource.updateMeal(meal);
    }

    @Override
    public Meal getByID(int id) {
        return dataSource.getMealById(id);
    }
}
