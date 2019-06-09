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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals.jsp");
        List<MealTo> allListMealTo = MealsUtil.getFilteredWithExcess(DataSource.getInstance().getUsers(), LocalTime.MIN,LocalTime.MAX,2000);
        log.debug(allListMealTo.toString());
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("meals",allListMealTo);
     //   req.setAttribute("meals",MealsUtil.getFilteredWithExcess(DataSource.getInstance().getUsers(), LocalTime.MIN,LocalTime.MAX,0));

        req.getRequestDispatcher("/meals.jsp").forward(req,resp);
    }
}

