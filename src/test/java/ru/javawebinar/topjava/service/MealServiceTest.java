package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void getAll() {

        List<Meal> all = service.getAll(USER_ID);
        assertMatch(USER_MEALS,all);

         all = service.getAll(ADMIN_ID);
        assertMatch(ADMIN_MEALS,all);
    }
    @Test
    public void get() {
        Meal meal = service.get(START_SEQ, USER_ID);
        assertMatch(USER_MEALS.get(0),meal);
    }
    @Test
    public void delete() {
        service.delete(START_SEQ, USER_ID);
        assertMatch(service.getAll(USER_ID), USER_MEALS.subList(1,(USER_MEALS.size())));
    }

    @Test
    public void getBetweenDates() {
        LocalDate startDate = LocalDate.of(2015, Month.MAY, 30);
        LocalDate endDate = LocalDate.of(2015, Month.MAY, 30);
        List<Meal> betweenDateTimes = service.getBetweenDates(startDate, endDate, USER_ID);
        List<Meal> mealFilterList = USER_MEALS.stream().filter(meal -> Util.isBetween(meal.getDate(), startDate, endDate)).collect(Collectors.toList());
        assertMatch(betweenDateTimes,mealFilterList);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2015, Month.MAY, 30, 14, 0);
        List<Meal> betweenDateTimes = service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID);
        List<Meal> mealFilterList = USER_MEALS.stream().filter(meal -> Util.isBetween(meal.getDateTime(), startDateTime, endDateTime)).collect(Collectors.toList());
        assertMatch(betweenDateTimes,mealFilterList);
    }
    @Test
    public void update() {

        int id = ADMIN_MEALS.size() - 1;
        Meal update = new Meal(ADMIN_MEALS.get(id));
        update.setDescription("Перекусон");
        update.setCalories(3000);
        service.update(update,ADMIN_ID);
        assertMatch(service.get(ADMIN_MEALS.get(id).getId(),ADMIN_ID), update);
    }

    @Test
    public void create() {

        Meal meal = new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 23, 0), "Админ перекус", 1000);
        Meal mealReturn = service.create(meal, ADMIN_ID);
        assertThat(mealReturn.getId()).isEqualTo(START_SEQ + 8);
        assertThat(mealReturn.getDescription()).isEqualTo("Админ перекус");
        assertThat(mealReturn.getCalories()).isEqualTo(1000);

    }

}