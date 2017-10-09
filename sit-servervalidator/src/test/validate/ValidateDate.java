package test.validate;

import java.util.Calendar;
import java.util.Locale;

import util.calendar.CalendarUtil;

public class ValidateDate {

	public static void main(String[] args) {
		Locale locale = Locale.ENGLISH;
		String date1 = "18/05/2560";
		String formatDDslMMslYYYY = "DD/MM/YYYY";
		
		try {
			Calendar calendarDate = CalendarUtil.getCalendarFromDateString(date1, formatDDslMMslYYYY, locale);
			String stringDate = CalendarUtil.getDateStringFromCalendar(calendarDate, formatDDslMMslYYYY);
			System.out.println(stringDate);
			
			date1 = "32/05/2560";
			calendarDate = CalendarUtil.getCalendarFromDateString(date1, formatDDslMMslYYYY, locale);
			stringDate = CalendarUtil.getDateStringFromCalendar(calendarDate, formatDDslMMslYYYY);
			System.out.println(stringDate);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
