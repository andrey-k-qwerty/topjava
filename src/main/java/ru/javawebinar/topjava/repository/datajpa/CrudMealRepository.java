package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal findByIdAndUser_Id(int id, int userId);

    @Transactional
    Meal save(Meal meal);

    List<Meal> findAllByUser_IdOrderByDateTimeDesc(Iterable<Integer> iterable);

    @Transactional
    int deleteByIdAndUser_Id(int id, int userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> findByDateTimeGetBetweenAndUser_idOrderByDateTimeDesc(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);
}
