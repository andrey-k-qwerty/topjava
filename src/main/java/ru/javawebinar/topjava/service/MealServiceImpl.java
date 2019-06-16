package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int UserId) {
        return repository.save(meal, UserId);
    }

    @Override
    public void delete(int id, int UserId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, UserId), id);
    }

    @Override
    public Meal get(int id, int UserId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, UserId), id);
    }

    @Override
    public void update(Meal meal, int UserId) {
        checkNotFoundWithId(repository.save(meal, UserId), meal.getId());
    }

    @Override
    public List<Meal> getAll(int UserId) {
        return (List<Meal>) repository.getAll(UserId);
    }
}