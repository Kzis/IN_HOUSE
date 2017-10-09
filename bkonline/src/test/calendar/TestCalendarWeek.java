package test.calendar;

import java.util.Calendar;
import java.util.Locale;

import util.calendar.CalendarUtil;

public class TestCalendarWeek {

	public static void main(String[] args) {
		String displayFormat =  "DD/MM/YYYY";
		Locale displayLocale = Locale.ENGLISH;
		String valueCurrent = "24/07/2017";
		String valueStart =  "";
		String valueEnd =  "";
		
		try {
			Calendar calendarCurrent = CalendarUtil.getCalendarFromDateString(valueCurrent, displayFormat, displayLocale);
			calendarCurrent.setFirstDayOfWeek(Calendar.MONDAY);
			
			Calendar calendarStart = CalendarUtil.getCalendarFromDateString(valueCurrent, displayFormat, displayLocale);
			calendarStart.setFirstDayOfWeek(Calendar.MONDAY);
			
			Calendar calendarEnd = CalendarUtil.getCalendarFromDateString(valueCurrent, displayFormat, displayLocale);
			calendarEnd.setFirstDayOfWeek(Calendar.MONDAY);
			
			System.out.println("DAY_OF_WEEK_IN_MONTH: " + calendarCurrent.get(Calendar.DAY_OF_WEEK_IN_MONTH));
			System.out.println("WEEK_OF_MONTH: " + calendarCurrent.get(Calendar.WEEK_OF_MONTH)); // สัปดาห์ที่เท่าไหร่ในเดือนนั้น เริ่มจาก 1
			System.out.println("DATE: " + calendarCurrent.get(Calendar.DATE));
			System.out.println("DAY_OF_MONTH: " + calendarCurrent.get(Calendar.DAY_OF_MONTH));
			System.out.println("DAY_OF_WEEK: " + calendarCurrent.get(Calendar.DAY_OF_WEEK)); // 1 = Sunday, 2 = Monday, ..., 7 = Saturday
			
			int[] amountArray = initDay(calendarStart);
			int amountStart = amountArray[0];
			int amountEnd = amountArray[1];
			
			calendarEnd.add(Calendar.DAY_OF_MONTH, amountEnd);
			calendarStart.add(Calendar.DAY_OF_MONTH, amountStart);
			
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
