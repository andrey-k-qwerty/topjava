package ru.javawebinar.topjava.model;

import java.util.List;

public class MealDAOImpl implements MealDAO {
    private DataSource dataSource = DataSource.getInstance();
    @Override
    public List<Meal> getAllMeal() {
        return dataSource.getUsers();
    }
}
