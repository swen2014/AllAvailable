package com.cmu.smartphone.allavailable.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The tool about setting the date and time
 *
 * @author Xi Wang
 * @version 1.0
 */
public class DateTimeHelper {

    private static final String[] WEEK_DAYS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    /**
     * Add the time based on the input
     *
     * @param time
     * @param duration
     * @return the time after calculation
     */
    public static String addTime(String time, double duration) {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY,
                Integer.parseInt(time.substring(0, time.indexOf(":"))));
        cal1.set(Calendar.MINUTE,
                Integer.parseInt(time.substring(time.indexOf(":") + 1)));
        long newTime = cal1.getTimeInMillis()
                + (long) (duration * 60 * 60 * 1000);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(newTime);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(cal2.getTime());
    }

    /**
     * Get the day string of the week
     *
     * @param index
     * @return the day string of the week
     */
    public static String getDayOfWeek(int index) {
        return WEEK_DAYS[index];
    }


}
