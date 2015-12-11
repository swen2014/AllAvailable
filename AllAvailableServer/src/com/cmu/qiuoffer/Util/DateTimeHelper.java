package com.cmu.qiuoffer.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The tool to parse the date and time
 * 
 * @author Xi Wang
 * @version 1.0
 */
public class DateTimeHelper {
	/**
	 * Get the formated time string
	 * 
	 * @return the formated time string
	 */
	public static String getDateTime() {
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm");
		return df.format(new Date());
	}

	/**
	 * Check whether the time periods overlap
	 * 
	 * @param time
	 * @param lastTime
	 * @param lastDuration
	 * @return whether the time periods overlap
	 */
	public static boolean checkTimeOverlap(String time, String lastTime,
			double lastDuration) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY,
				Integer.parseInt(lastTime.substring(0, lastTime.indexOf(":"))));
		cal1.set(Calendar.MINUTE,
				Integer.parseInt(lastTime.substring(lastTime.indexOf(":") + 1)));

		cal2.set(Calendar.HOUR_OF_DAY,
				Integer.parseInt(time.substring(0, time.indexOf(":"))));
		cal2.set(Calendar.MINUTE,
				Integer.parseInt(time.substring(time.indexOf(":") + 1)));

		long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
		if (diff < (lastDuration * 60 * 60 * 1000)) {
			return true;
		}

		return false;
	}

	/**
	 * Add two times to get a new time
	 * 
	 * @param time
	 * @param duration
	 * @return a new time string
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
}
