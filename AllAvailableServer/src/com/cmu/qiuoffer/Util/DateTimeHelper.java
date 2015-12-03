package com.cmu.qiuoffer.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {
	public static String getDateTime() {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		return df.format(new Date());
	}

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

		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
		return formatter.format(cal2.getTime());
	}
}
