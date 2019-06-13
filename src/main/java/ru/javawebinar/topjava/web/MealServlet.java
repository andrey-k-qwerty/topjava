package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.*;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/mealEditOrAdd.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private static  MealDAO mealDAO = new MealDAOImpl();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");
        action = action == null ? "" : action.toLowerCase();
        switch (action) {

            case "add": {
                forward = INSERT_OR_EDIT;
                // тут нужно создавать еду через базу с получением айдишника
                req.setAttribute("meal", new Meal(LocalDateTime.now(),"",0));
                break;
            }
            case "edit": {
                forward = INSERT_OR_EDIT;
                Integer id = Integer.parseInt(req.getParameter("Id"));
                req.setAttribute("meal", mealDAO.getByID(id));
                break;
            }
            case "delete": {
                Integer id = Integer.parseInt(req.getParameter("Id"));
                mealDAO.delete(mealDAO.getByID(id));
            }
            default: {
                forward = LIST_MEAL;
                List<MealTo> allListMealTo = MealsUtil.getFilteredWithExcess(mealDAO.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000);
                log.debug(allListMealTo.toString());
                req.setAttribute("meals", allListMealTo);

            }
        }

        log.debug("forward to {}",forward);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String date = req.getParameter("date");
        log.debug("date - {}", date);
        String description = req.getParameter("description");
        log.debug("description - {}" ,description);
        String calories = req.getParameter("calories");
        log.debug("calories - {}", calories);

        Integer id = Integer.parseInt(req.getParameter("id"));
        if (id == null || id == 0)
          mealDAO.add(new Meal( LocalDateTime.parse(date,formatter),description,Integer.parseInt(calories)));
        else
            mealDAO.update(new Meal( id, LocalDateTime.parse(date,formatter),description,Integer.parseInt(calories)));

        List<MealTo> allListMealTo = MealsUtil.getFilteredWithExcess(mealDAO.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", allListMealTo);
        req.getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }
}

