package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepository implements MealRepository {
    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
    //    meal.setUser(ref);

        if (meal.isNew()) {
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            Meal meal1 = get(meal.getId(), userId);
            if (meal1 != null  && meal.getUser() == null)
                meal.setUser(ref);
            return (meal1 != null  ) ? em.merge(meal) : null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {

        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user", em.getReference(User.class, userId))
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
//       User ref = em.getReference(User.class, userId);
//        User user = em.find(User.class, userId);
        Meal meal = em.find(Meal.class, id);
        if (meal != null && meal.getUser().getId() == userId)
//            meal.setUser(user);
        return meal;
        else
            return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        User ref = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("user_id", ref)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User ref = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.BEETWEN_DATE, Meal.class)
                .setParameter("user_id", ref)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}