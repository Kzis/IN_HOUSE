package test.calendar;

import java.util.Calendar;
import java.util.Locale;

import util.calendar.CalendarUtil;

public class TestCalendarMonth {

	public static void main(String[] args) {
		String displayFormat =  "DD/MM/YYYY";
		Locale displayLocale = Locale.ENGLISH;
//		String valueCurrent = "02/07/2017";
//		DAY_OF_WEEK_IN_MONTH: 1
//		WEEK_OF_MONTH: 2
//		DATE: 2
//		DAY_OF_MONTH: 2
//		DAY_OF_WEEK: 1
//		DAY_OF_WEEK: SUNDAY
//		String valueCurrent = "10/07/2017"; // 26/06/2017 - 06/08/2017
		String valueCurrent = "32/07/2017"; // 26/06/2017 - 06/08/2017
		String valueStart =  "";
		String valueEnd =  "";
		
		try {
			Calendar calendarCurrent = CalendarUtil.getCalendarFromDateString(valueCurrent, displayFormat, displayLocale);
			calendarCurrent.setFirstDayOfWeek(Calendar.MONDAY);
			int firstDay = calendarCurrent.getActualMinimum(Calendar.DAY_OF_MONTH);
			int lastDay = calendarCurrent.getActualMaximum(Calendar.DAY_OF_MONTH);
			System.out.println("First & Last: " + firstDay + " - " + lastDay);
			
			Calendar calendarStart = CalendarUtil.getCalendarFromDateString(valueCurrent, displayFormat, displayLocale);
			calendarStart.setFirstDayOfWeek(Calendar.MONDAY);
			calendarStart.set(Calendar.DAY_OF_MONTH, firstDay);
			
			Calendar calendarEnd = CalendarUtil.getCalendarFromDateString(valueCurrent, displayFormat, displayLocale);
			calendarEnd.setFirstDayOfWeek(Calendar.MONDAY);
			calendarEnd.set(Calendar.DAY_OF_MONTH, lastDay);

			int[] amountStartArray = initDay(calendarStart);
			int amountStart = amountStartArray[0];
			
			int[] amountEndArray = initDay(calendarEnd);
			int amountEnd = amountEndArray[1];
			
			System.out.println("amountStart & amountEnd: " + amountStart + " , " + amountEnd);
			
			calendarStart.add(Calendar.DAY_OF_MONTH, amountStart);
			calendarEnd.add(Calendar.DAY_OF_MONTH, amountEnd);
			
			System.out.println(CalendarUtil.getDateStringFromCalendar(calendarStart, displayFormat) + " - " + CalendarUtil.getDateStringFromCalendar(calendarEnd, displayFormat));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int[] initDay(Calendar calendarCurrent) {
	
		int amountStart = 0;
		int amountEnd = 0;
		if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			System.out.println("DAY_OF_WEEK: MONDAY");
			// +6
			amountEnd = +6;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			System.out.println("DAY_OF_WEEK: TUESDAY");
			// -1, +5
			amountStart = -1;
			amountEnd = +5;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			System.out.println("DAY_OF_WEEK: WEDNESDAY");
			// -2, +4 
			amountStart = -2;
			amountEnd = +4;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			System.out.println("DAY_OF_WEEK: THURSDAY");
			// -3, +3
			amountStart = -3;
			amountEnd = +3;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			System.out.println("DAY_OF_WEEK: FRIDAY");
			// -4, +2
			amountStart = -4;
			amountEnd = +2;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			System.out.println("DAY_OF_WEEK: SATURDAY");
			// -5, +1
			amountStart = -5;
			amountEnd = +1;
		
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			System.out.println("DAY_OF_WEEK: SUNDAY");
			// -6, +0
			amountStart = -6;
		}
		
		int[] result = new int[2];
		result[0] = amountStart;
		result[1] = amountEnd;
		return result;
	}
}
