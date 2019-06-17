package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
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
       // return MealsUtil.getWithExcess(repository.getAll(UserId),MealsUtil.DEFAULT_CALORIES_PER_DAY) ;
        return (List<Meal>) repository.getAll(UserId);

    }

    @Override
    public List<Meal> filterByDateTime(LocalDate beginData, LocalDate endData, LocalTime beginTime, LocalTime endTime, int UserId) {
//        return MealsUtil.getWithExcess(repository.filterByDateTime(beginData,endData,beginTime,endTime,UserId),MealsUtil.DEFAULT_CALORIES_PER_DAY) ;
        return (List<Meal>) repository.filterByDateTime(beginData,endData,beginTime,endTime,UserId);
    }

    @Override
    public List<Meal> filterByDateTime(LocalDateTime beginDataTime, LocalDateTime endDataTime, int UserId) {
  //      return MealsUtil.getWithExcess(repository.filterByDateTime(beginDataTime,endDataTime,UserId),MealsUtil.DEFAULT_CALORIES_PER_DAY) ;
        return (List<Meal>) repository.filterByDateTime(beginDataTime,endDataTime,UserId);

    }
}