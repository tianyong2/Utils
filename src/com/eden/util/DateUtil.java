package com.eden.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	/**
	 * 
	 * @param date
	 *            format should be yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public Date parseDateTime(String date) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(date);
	}

	/**
	 * 
	 * @param date
	 *            format should be yyyy-MM-dd
	 * @return
	 * @throws ParseException
	 */
	public Date parseDate(String date) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(date);
	}

	/**
	 * 
	 * @param time12Hours
	 *            09:14 PM
	 * @return Date
	 * @throws ParseException
	 */
	public Date parseTime12Hours(String time12Hours) throws ParseException {
		return new SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse(time12Hours);
	}

	/**
	 * 
	 * @param date
	 * @return formated date YYYY-MM-DD
	 */
	public String formatDate(Date date) {
		return String.format("%1$tY-%1$tm-%1$td", date);
	}

	/**
	 * YYYY-MM-DD HH:MM
	 */
	public String formatDateTimeHHMM(Date date) {
		return String.format("%1$tY-%1$tm-%1$td    %1$TR", date);
	}

	/**
	 * YYYY-MM-DD
	 */
	public String formatDateTimeYYMMDD(Date date) {
		return String.format("%1$tY-%1$tm-%1$td", date);
	}

	/**
	 * YYYY-MM-DD HH:MM:SS
	 * 
	 */
	public String formatDateTime(Date date) {
		return String.format("%1$tY-%1$tm-%1$td %1$TT", date);
	}

	/**
	 * HH:MM AM/PM
	 */
	public String formatTime(Date time) {
		return String.format("%1$Tr", time);
	}

	/**
	 * MM-DD HH:MM
	 */
	public String formatDateTime24(Date time) {
		return String.format("%1$tm-%1$td %1$TR", time);
	}

	/**
	 * HH:MM
	 */
	public String formatTime24(Date time) {
		return String.format("%1$TR", time);
	}

	/**
	 * Saturday Sunday Monday etc
	 */
	public String formatDayOfWeek(Date day) {
		return String.format(Locale.ENGLISH, "%1$TA", day);
	}
}
