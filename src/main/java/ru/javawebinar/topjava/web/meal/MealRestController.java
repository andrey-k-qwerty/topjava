package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        int authUserId = SecurityUtil.authUserId();
        log.info("getAll, authUserId - {}", authUserId);
        return MealsUtil.getWithExcess(service.getAll(authUserId),SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        int authUserId = SecurityUtil.authUserId();
        log.info("get id {}, authUserId {}", id, authUserId);
        return service.get(id, authUserId);
    }

    public Meal create(Meal meal) {
        int authUserId = SecurityUtil.authUserId();
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId);
    }

    public void delete(int id) {
        int authUserId = SecurityUtil.authUserId();
        log.info("delete id {}, authUserId {}", id);
        service.delete(id, authUserId);
    }

    public void update(Meal meal, int id) {
        int authUserId = SecurityUtil.authUserId();
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId);
    }

    // фильтрция по датам
    // фильтрция по дни-время
    List<MealTo> filterByDateTime(LocalDate beginData, LocalDate endData, LocalTime beginTime, LocalTime endTime) {
        int authUserId = SecurityUtil.authUserId();
        log.info("filterByDateTime  beginData {}, endData {}, beginTime {}, endTime {} ", beginData, endData, beginTime, endTime);
        List<Meal> meals = service.filterByDateTime(beginData, endData, LocalTime.MIN, LocalTime.MAX, authUserId);

        return  MealsUtil.getFilteredWithExcess(meals,SecurityUtil.authUserCaloriesPerDay(),beginTime,endTime);
    }

    // фильтрция по дням
    List<MealTo> filterByDate(LocalDate beginData, LocalDate endData) {
        int authUserId = SecurityUtil.authUserId();
        log.info("filterByDate  beginData {}, endData {} ", beginData, endData);
        return MealsUtil.getWithExcess(service.filterByDateTime(beginData, endData, LocalTime.MIN, LocalTime.MAX, authUserId),SecurityUtil.authUserCaloriesPerDay());
    }



    public  List<MealTo>   checkAndGetByDataTime(String beginData, String endData, String beginTime, String endTime ) {
        LocalDate lbd = DateTimeUtil.toDateOrDateMin(beginData);
        LocalDate led = DateTimeUtil.toDateOrDateMax(endData);
        LocalTime lbt = DateTimeUtil.toTimeOrTimeMin(beginTime);
        LocalTime let = DateTimeUtil.toTimeOrTimeMax(endTime);
      return   filterByDateTime(lbd,led,lbt,let);
    }
}