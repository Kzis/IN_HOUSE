package test.validate;

import java.util.Locale;

import util.calendar.CalendarUtil;

public class ValidateDayRangeLimit {

	public static void main(String[] args) {
		String startDate = "20/05/2017";
		String endDate = "19/05/2017";
		String format = "DD/MM/YYYY";
		Locale dbLocale = Locale.ENGLISH;
		
		try {
			long oneDayInMillis = 86400000;
			long startInMillis = CalendarUtil.getCalendarFromDateString(startDate, format, dbLocale).getTimeInMillis();
			long endInMillis = CalendarUtil.getCalendarFromDateString(endDate, format, dbLocale).getTimeInMillis();
			long diffInMillis = endInMillis - startInMillis;
			System.out.println(diffInMillis + " = " + endInMillis + " - " + startInMillis);
			
			long totalDay = diffInMillis / oneDayInMillis;
			System.out.println("totalDay: " + totalDay);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
