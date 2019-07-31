package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    public static final String REST_URL = "/rest/meals";

    @GetMapping
    public List<MealTo> getAll() {
        log.info("REST getAll");
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        log.info("REST get id {}", id);
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("REST delete id {}", id);
        super.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        log.info("REST create {}", meal);
        // checkNew(meal);
        //  Meal created = service.create(meal);
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        log.info("REST update meal - {}, id  -{}", meal, id);
        super.update(meal, id);
    }

    // Так, если нужно принимать параметры типа LocalDateTime то их два
    //  если  LocalDate/LocalTime то четыре

    @GetMapping("/filter/2")
    public List<MealTo> getBetween (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
                                   ) {
        log.info("REST getBetween startDate {}, endDate {}", startDate, endDate);
        return super.getBetween(startDate.toLocalDate(), startDate.toLocalTime(), endDate.toLocalDate(),endDate.toLocalTime());
    }


    @GetMapping("/filter/4")
    public List<MealTo> getBetween(@RequestParam String startDate,
                                   @RequestParam String endDate,
                                   @RequestParam String startTime,
                                   @RequestParam String endTime) {
        log.info("REST getBetween startDate {}, endDate {}, startTime {}, endTime {}", startDate, endDate, startTime, endTime);
        LocalDate startDateLocal = parseLocalDate(startDate);
        LocalDate endDateLocal = parseLocalDate(endDate);
        LocalTime startTimeLocal = parseLocalTime(startTime);
        LocalTime endTimeLocal = parseLocalTime(endTime);

        return super.getBetween(startDateLocal, startTimeLocal, endDateLocal, endTimeLocal);
    }
     /*
   @GetMapping("/filter")
    public List<MealTo> getBetween (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        log.info("REST getBetween startDate {}, endDate {}, startTime {}, endTime {}", startDate,endDate, startTime,endTime);
        return super.getBetween(startDate,startTime,endDate,endTime);
    }*/


}