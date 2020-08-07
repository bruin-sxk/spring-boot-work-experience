package com.bruin.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static final String GMT_US_DATE = "E, dd MMM yyyy HH:mm:ss z";
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";


	public static String format(Date date) {
		return format(date, null);
	}

	public static String format(Date date, String pattern) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
				.format(DateTimeFormatter.ofPattern(pattern == null ? DEFAULT_FORMAT : pattern));
	}

	public static String formatGMT(String dateStr) {
		return formatGMT(dateStr, null);
	}

	public static String formatGMT(String dateStr, String pattern) {
		return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(GMT_US_DATE, Locale.US))
				.format(DateTimeFormatter.ofPattern(pattern == null ? DEFAULT_FORMAT : pattern));
	}

}
