package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;


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

    public List<Meal> getAll() {
        int authUserId = SecurityUtil.authUserId();
        log.info("getAll, authUserId - {}",authUserId);
        return service.getAll(authUserId);
    }

    public Meal get(int id) {
        int authUserId = SecurityUtil.authUserId();
        log.info("get id {}, authUserId {}", id,authUserId);
        return service.get(id,authUserId);
    }

    public Meal create(Meal meal) {
        int authUserId = SecurityUtil.authUserId();
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal,authUserId);
    }

    public void delete(int id) {
        int authUserId = SecurityUtil.authUserId();
        log.info("delete id {}, authUserId {}", id);
        service.delete(id,authUserId);
    }

    public void update(Meal meal, int id) {
        int authUserId = SecurityUtil.authUserId();
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal,authUserId);
    }

}