package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudMeal;
    @Autowired
    private CrudUserRepository crudUser;

    @Override
    public Meal save(Meal meal, int userId) {
        User user = crudUser.findById(userId).orElse(null);
        if ((!meal.isNew() && get(meal.getId(), userId) == null) || user == null) {
            return null;
        }
        meal.setUser(user);
        return crudMeal.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMeal.deleteByIdAndUser_Id(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudMeal.findByIdAndUser_Id(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {

        return crudMeal.findAllByUser_IdOrderByDateTimeDesc((List.of(userId)));
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudMeal.getAllByDateTimeBetweenAndUser_IdOrderByDateTimeDesc(startDate, endDate, userId);
    }
}
