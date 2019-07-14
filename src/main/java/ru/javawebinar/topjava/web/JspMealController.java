package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = getLogger(JspMealController.class);
    final MealRestController mealRestController;

    @Autowired
    public JspMealController(MealRestController mealRestController) {
        this.mealRestController = mealRestController;
    }


    @GetMapping()
    public String meals(Model model) {
        log.info("Meal - gatAll");
        model.addAttribute("meals", mealRestController.getAll());
        return "meals";
    }

    @GetMapping(params = {"action=update", "id"})
    public String updateForm(@RequestParam("id") int id, Model model) {
        log.info("Meal - UPDATE FORM, id - {}", id);
        //   Meal meal = mealRestController.getWithUser(id);
        Meal meal = mealRestController.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping(params = {"action=filter"})
    public String filter(Model model, HttpServletRequest request) {
        log.info("Meal -FILTER");
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", mealRestController.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping(params = {"action=create"})
    public String createForm(Model model) {
        log.info("Meal - CREATE FORM");
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping(params = {"action=delete", "id"})
    public String delete(@RequestParam("action") String action, @RequestParam("id") int id, Model model) {
        log.info("Meal - DELETE, id - {}", id);
        mealRestController.delete(id);
        model.addAttribute("meals", mealRestController.getAll());
        return "meals";
    }

    @PostMapping()
    public String update(@Valid Meal meal,
                         BindingResult bindingResult,
                         Model model,
                         HttpServletRequest request,
                         RedirectAttributes redirectAttributes,
                         Locale locale) throws UnsupportedEncodingException {
        //       request.setCharacterEncoding("UTF-8");
        log.info("Meal - UPDATE, meal - {}", meal);
        if (bindingResult.hasErrors()) {
            log.error("Binding error - {}", bindingResult.getAllErrors().toString());
        }

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            mealRestController.create(meal);
        } else {
            mealRestController.update(meal, getId(request));
        }

        return "redirect:meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
