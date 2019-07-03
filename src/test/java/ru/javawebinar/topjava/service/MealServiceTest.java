package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

// Rule - http://blog.qatools.ru/junit/junit-rules-tutorial#expectedexcptn
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger log = getLogger(MealServiceTest.class);
    private static Map<String, Long> mapLog = new HashMap<>();

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    long startTime;
    @Rule
    public TestWatcher watcher = new TestWatcher() {

        @Override
        protected void starting(Description description) {
            startTime = System.currentTimeMillis();

            //     System.out.print("Starting... ");
            String methodName = description.getMethodName();
            //     System.out.println(methodName);
            log.debug("Starting... {}", methodName);

        }

        @Override
        protected void finished(Description description) {
            //    System.out.print("Finished...");

            String methodName = description.getMethodName();
            //      System.out.print(methodName);
            //       System.out.print(", Time - ");
            long l = System.currentTimeMillis() - startTime;
            //     System.out.println(l + " ms");
            log.debug("Finished... {}, Time - {} ms", methodName, l);
            mapLog.put(methodName, l);
        }


    };
    @Autowired
    private MealService service;

    @AfterClass
    public static void afterClass() {
        // mapLog.forEach((s, aLong) -> System.out.printf("Procedure %s, work time - %d ms\n", s, aLong));
        mapLog.forEach((s, aLong) -> log.info("Procedure {}, work time - {} ms", s, aLong));

    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotOwn() throws Exception {
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test()//(expected = NotFoundException.class)
    public void getNotFound() {
        //test type
        thrown.expect(NotFoundException.class);
        service.get(1, USER_ID);
    }

    @Test//(expected = NotFoundException.class)
    public void getNotOwn() {
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test//(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}