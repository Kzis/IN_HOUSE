package test.calendar;

import java.util.Calendar;
import java.util.Locale;

import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBooking;

import util.calendar.CalendarUtil;

public class TestCalendar {

	public static void main(String[] args) {
		String displayFormat =  "DD/MM/YYYY";
		Locale displayLocale = Locale.ENGLISH;
		String displayValueStart =  "09/05/2017";
		String displayValueEnd =  "12/05/2017";
		
		
		try {
			
			RoomBooking rb = new RoomBooking();
			rb.setRoomId("roomId");
			
			RoomBooking rbrb = rb.clone();
			rbrb.setRoomName("roomName");
			
			System.out.println(rbrb.getRoomId());
			System.out.println(rbrb.getRoomName());
			
			System.out.println(rb);
			System.out.println(rbrb);
			
			Calendar calendarStart = CalendarUtil.getCalendarFromDateString(displayValueStart, displayFormat, displayLocale);
			Thread.sleep(2000);
			Calendar calendarEnd = CalendarUtil.getCalendarFromDateString(displayValueEnd, displayFormat, displayLocale);
			
			System.out.println(calendarStart.getTimeInMillis());
			System.out.println(calendarEnd.getTimeInMillis());
			while (calendarStart.before(calendarEnd) || calendarStart.equals(calendarEnd)) {
			
				System.out.println(CalendarUtil.getDateStringFromCalendar(calendarStart, displayFormat));
				calendarStart.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
