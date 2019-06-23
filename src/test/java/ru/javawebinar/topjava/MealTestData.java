package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
   public static final int START_SEQ = 100002;
    public static final List<Meal> USER_MEALS = Arrays.asList(
            new Meal(START_SEQ, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(START_SEQ + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(START_SEQ + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(START_SEQ + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(START_SEQ + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(START_SEQ + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final List<Meal> ADMIN_MEALS = Arrays.asList(
            new Meal(START_SEQ + 6, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510),
            new Meal(START_SEQ + 7, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500)
    );
    public static final List<Meal> ALL_MEALS = Stream.concat(USER_MEALS.stream(), ADMIN_MEALS.stream()).collect(Collectors.toList());



        public static void assertMatch(Meal actual, Meal expected) {
      //  assertThat(actual).isEqualToIgnoringGivenFields(expected, "id");
            assertThat(actual).isEqualToComparingFieldByField(expected);

    }



        public static void assertMatch(List<Meal> actual, List<Meal> expected) {
            assertThat(actual).isEqualTo(expected);
    }


}
