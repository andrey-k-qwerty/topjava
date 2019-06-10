package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.DataSource;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/mealEditOrAdd.jsp";
    private static final String LIST_MEAL = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");
        action = action == null ? "" : action.toLowerCase();
        switch (action) {

            case "add": {
                forward = INSERT_OR_EDIT;
                break;
            }
            default: {
                forward = LIST_MEAL;
                List<MealTo> allListMealTo = MealsUtil.getFilteredWithExcess(DataSource.getInstance().getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
                log.debug(allListMealTo.toString());
                req.setAttribute("meals", allListMealTo);

            }
        }

        log.debug("forward to {}",forward);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<MealTo> allListMealTo = MealsUtil.getFilteredWithExcess(DataSource.getInstance().getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", allListMealTo);
        req.getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }
}

