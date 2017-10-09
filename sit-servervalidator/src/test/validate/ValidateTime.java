package test.validate;

import java.util.Calendar;
import java.util.Locale;

import util.calendar.CalendarUtil;

public class ValidateTime {

	public static void main(String[] args) {
		Locale locale = Locale.ENGLISH;
		String time1 = "25:33";
		String formatHHclMM = "HH:mm";
		
		try {
			Calendar calendarTime = CalendarUtil.getCalendarFromDateString(time1, formatHHclMM, locale);
			String stringTime = CalendarUtil.getDateStringFromCalendar(calendarTime, formatHHclMM);
			System.out.println(stringTime);
			
			time1 = "25:33";
			calendarTime = CalendarUtil.getCalendarFromDateString(time1, formatHHclMM, locale);
			stringTime = CalendarUtil.getDateStringFromCalendar(calendarTime, formatHHclMM);
			System.out.println(stringTime);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
