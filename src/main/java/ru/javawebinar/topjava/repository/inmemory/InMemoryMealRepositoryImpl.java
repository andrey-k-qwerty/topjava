package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        // Для пользователя Админ
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        // Для пользователя User
        save(new Meal(LocalDateTime.of(2017, Month.AUGUST, 30, 10, 0), "Завтрак", 500), 2);
        save(new Meal(LocalDateTime.of(2017, Month.AUGUST, 30, 13, 0), "Обед", 1000), 2);
        save(new Meal(LocalDateTime.of(2017, Month.AUGUST, 30, 20, 0), "Ужин", 500), 2);
    }

    @Override
    public Meal save(Meal meal, int UserId) {

        // add new meal
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(UserId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // update meal, get old data
        Meal meal1 = get(meal.getId(), UserId);
        if (meal1 == null) return null;

        meal.setUserId(meal1.getUserId());

        // treat case: update, but absent in storage
        log.info("save {}, UserId {} ", meal, UserId);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int UserId) {
        log.info("delete id - {}, UserId - {} ", id, UserId);
        return get(id, UserId) != null && (repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int UserId) {
        log.info("get id - {}, UserId - {} ", id, UserId);
        Meal meal = repository.get(id);
        if (meal == null) {
            return null;
        }

        return meal.getUserId() == UserId ? meal : null;

    }

    @Override
    public Collection<Meal> getAll(int UserId) {
        log.info("getAll, UserId {}", UserId);
        return repository.values().stream()
                // что делать с userId null?
                .filter(meal -> meal.getUserId() != null && meal.getUserId() == UserId)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

