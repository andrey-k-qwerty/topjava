package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    //    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
//        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
//    }
    public static <T extends Comparable> boolean isBetween(T lt, T startTime, T endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate toDateOrDateMin(String ldt) {
        return ldt == null || ldt.equals("") ? LocalDate.MIN : LocalDate.parse(ldt, DATE_FORMATTER);
    }

    public static LocalDate toDateOrDateMax(String ldt) {
        return ldt == null || ldt.equals("") ? LocalDate.MAX : LocalDate.parse(ldt, DATE_FORMATTER);
    }

    public static LocalTime toTimeOrTimeMax(String ldt) {
        return ldt == null || ldt.equals("") ? LocalTime.MAX : LocalTime.parse(ldt, TIME_FORMATTER);
    }

    public static LocalTime toTimeOrTimeMin(String ldt) {
        return ldt == null || ldt.equals("") ? LocalTime.MIN : LocalTime.parse(ldt, TIME_FORMATTER);
    }


}
