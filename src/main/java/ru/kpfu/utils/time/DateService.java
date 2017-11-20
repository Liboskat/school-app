package ru.kpfu.utils.time;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ильшат on 08.11.2017.
 */
public class DateService {
    public static int getCurrentYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }

    public static int toMonthDay(Date date) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH) - 1;
        return dayOfMonth;
    }

    public static int toWeekday(Date date) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek;
    }

    public static int toWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        return week;
    }

    public static int toMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH) + 1;
        return month;
    }

    public static int getNumberOfDays(int month) {
        month = month - 1;
        if((month == Calendar.JANUARY) || (month == Calendar.MARCH) || (month == Calendar.MAY)
                || (month == Calendar.JULY) || (month == Calendar.AUGUST) || (month == Calendar.OCTOBER)
                || (month == Calendar.DECEMBER)) {
            return 31;
        } else if ((month == Calendar.APRIL) || (month == Calendar.JUNE) || (month == Calendar.SEPTEMBER)
                || (month == Calendar.NOVEMBER)) {
            return 30;
        } else if (month == Calendar.FEBRUARY){
            Date date = new Date();
            Calendar c = Calendar.getInstance();
//
            c.setTime(date);
            boolean isLeap = isLeapYear(c.get(Calendar.YEAR));
            return isLeap? 29 : 28;
        }
        return 31;
    }

    private static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    public static Date getStartOfCurrentWeek() {
        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

// get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
// start of the next week
//        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime();
    }

    public static Date getStartOfWeek(Integer week) {
        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.set(Calendar.WEEK_OF_YEAR, week);
        return cal.getTime();
    }

    public static Date getEndOfWeek(Date startOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startOfWeek);

        calendar.add(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        return calendar.getTime();
    }
}
