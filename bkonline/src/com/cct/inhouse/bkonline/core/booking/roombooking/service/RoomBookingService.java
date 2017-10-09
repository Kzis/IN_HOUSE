package com.cct.inhouse.bkonline.core.booking.roombooking.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import util.calendar.CalendarUtil;
import util.database.CCTConnection;
import util.date.DateUtil;
import util.string.StringDelimeter;
import util.validate.ServerValidateUtil;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonSelectItem;
import com.cct.common.CommonUser;
import com.cct.domain.Active;
import com.cct.exception.CustomException;
import com.cct.exception.DefaultExceptionMessage;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.EmptyRoom;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBooking;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingData;
import com.cct.inhouse.bkonline.core.config.parameter.domain.ParameterExtendedConfig;
import com.cct.inhouse.bkonline.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.bkonline.core.notification.service.NotificationManager;
import com.cct.inhouse.bkonline.core.security.user.domain.AdminUser;
import com.cct.inhouse.bkonline.core.security.user.service.UserManager;
import com.cct.inhouse.bkonline.domain.BKOnlineVariable;
import com.cct.inhouse.central.rmi.service.SelectItemRMIProviders;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.enums.GlobalType;

public class RoomBookingService extends AbstractService {

	private RoomBookingDAO dao = null;

	public RoomBookingService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new RoomBookingDAO(logger, SQLPath.BOOKING_SQL.getSqlPath(), user, locale);
	}

	/**
	 * ตรวจสอบห้องว่าง และจองทันที
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	protected String bookingNow(CCTConnection conn, RoomBooking data) throws Exception {
		// ตรวจสอบวันที่จองต้องอยู่ในช่วงที่กำหนด
		validateBookingDate(conn, data);
			
		// validateBookingNow(conn, data);
		
		String bookingId = null;
		
		// ตรวจสอบว่าห้องนั้นว่าง
		if (checkEmptyRoom(conn, data.getStartDate(), data.getStartTime(), data.getEndDate(), data.getEndTime(), data.getRoomId())) {
			// สร้างรหัสอ้างอิงการจอง
			bookingId = String.valueOf(generateBookingId());
			
			// ต้องลูปบันทึกทีละวัน เผื่อกรณีเลือกมากกว่าหนึ่งวัน
			RoomBooking bookingStatus = searchBookingStatus(BKOnlineVariable.BookingStatus.WAIT.getCode());
			
			RoomBooking tempData = data.clone();
			tempData.setBookingStatusCode(bookingStatus.getBookingStatusCode());
			tempData.setBookingStatusName(bookingStatus.getBookingStatusName());
			
			String dateFormatForDisplay = ParameterConfig.getDateFormat().getForDisplay();
			Locale databaseLocale = ParameterConfig.getApplication().getDatabaseLocale();
			
			Calendar calendarStart = CalendarUtil.getCalendarFromDateString(data.getStartDate(), dateFormatForDisplay, databaseLocale);
			Calendar calendarEnd = CalendarUtil.getCalendarFromDateString(data.getEndDate(), dateFormatForDisplay, databaseLocale);
			
			conn.setAutoCommit(false);
			try {
				while (calendarStart.before(calendarEnd) || calendarStart.equals(calendarEnd)) {
					tempData.setBookingId(bookingId);
					tempData.setStartDate(CalendarUtil.getDateStringFromCalendar(calendarStart, dateFormatForDisplay));
					tempData.setEndDate(tempData.getStartDate());
					
					// จองห้องได้เลย
					dao.add(conn, tempData);
					
					calendarStart.add(Calendar.DAY_OF_MONTH, 1);
				}
			} catch (Exception e) {
				conn.rollback();
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} else {
			throw new Exception("ไม่สามารถจองห้องได้ เนื่องจากห้องถูกจองไปแล้ว");
		}
		
		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			List<RoomBookingData> listForNoti = dao.searchDataEventDrawByBookingId(conn, bookingId);
			for (RoomBookingData forNoti : listForNoti) {
				notificationManager.addBooking(conn, forNoti, getUser());
			}
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
		
		return bookingId;
	}
	
	protected RoomBookingData searchDataEventDrawByBookingId(CCTConnection conn, String bookingId) throws Exception {
		
		RoomBookingData bookingData = null;
		
		List<RoomBookingData> listBookingData = dao.searchDataEventDrawByBookingId(conn, bookingId);
		if (listBookingData.size() > 0) {
			bookingData = listBookingData.get(0);
		}
		return bookingData;
	}
	
	/**
	 * ลบข้อมูลการจองเก่าออก และจองใหม่
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	protected String bookingUpdate(CCTConnection conn, RoomBooking data) throws Exception {
		String bookingId = null;
		
		conn.setAutoCommit(false);
		try {
			
			// ลบข้อมูลการจองเก่าออก
			dao.delete(conn, data.getBookingId());
			
			// จองใหม่
			bookingId = bookingNow(conn, data);
			
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		return bookingId;
	}
	
	/**
	 * ค้นหาห้องว่าง
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	protected List<EmptyRoom> searchListEmptyRoom(CCTConnection conn, String startDate, String startTime, String endDate, String endTime) throws Exception {
		
		String dateFormatForDisplay = ParameterConfig.getDateFormat().getForDisplay();
		Locale databaseLocale = ParameterConfig.getApplication().getDatabaseLocale();
		
		Calendar calendarStart = CalendarUtil.getCalendarFromDateString(startDate, dateFormatForDisplay, databaseLocale);
		Calendar calendarEnd = CalendarUtil.getCalendarFromDateString(endDate, dateFormatForDisplay, databaseLocale);
		
		int bookingDays = 0;
		Map<String, EmptyRoom> mapEmptyRoom = new LinkedHashMap<String, EmptyRoom>();
		Map<String, Integer> mapEmptyRoomCount = new LinkedHashMap<String, Integer>();
		while (calendarStart.before(calendarEnd) || calendarStart.equals(calendarEnd)) {
			
			String dateForCheck = CalendarUtil.getDateStringFromCalendar(calendarStart, dateFormatForDisplay);
			
			// หาห้องที่ถูกปิด
			Map<String, Active> mapClosedRoom = dao.searchMapClosedRoom(conn, dateForCheck, startTime, dateForCheck, endTime, getUser(), getLocale());
			
			// วนนับจำนวนวันว่าง ของห้องนั้นๆ
			List<EmptyRoom> listEmptyRoomTemp = dao.searchListEmptyRoom(conn, dateForCheck, startTime, dateForCheck, endTime, getUser(), getLocale());
			for (EmptyRoom room : listEmptyRoomTemp) {
				
				if (mapClosedRoom.get(room.getId()) != null) {
					// ห้องนั้นถูกปิด ไม่เพิ่มจำนวนวันว่าง
					continue;
				}

				// ห้องนั้นว่างใช้ได้ เพิ่มจำนวนวันว่าง
				if (mapEmptyRoomCount.get(room.getId()) == null) {
					mapEmptyRoomCount.put(room.getId(), 1);
				} else {
					mapEmptyRoomCount.put(room.getId(), mapEmptyRoomCount.get(room.getId()) + 1);
				}
				mapEmptyRoom.put(room.getId(), room);
			}
			
			
			calendarStart.add(Calendar.DAY_OF_MONTH, 1);
			bookingDays++;
		}
		
		for (String key : mapEmptyRoomCount.keySet()) {
			if (mapEmptyRoomCount.get(key) != bookingDays) {
				mapEmptyRoom.remove(key);
			} else {
				getLogger().debug("room [" + key + "] is empty.");
			}
		}
		
		getLogger().debug("bookingDays: " + bookingDays + " Total empty room: " + mapEmptyRoom.size());
		
		EmptyRoom[] roomArray = new EmptyRoom[0];
		return new ArrayList<EmptyRoom>(Arrays.asList(mapEmptyRoom.values().toArray(roomArray)));
	}

	/**
	 * ตรวจสอบห้องว่าง<br>
	 * return true เมื่อห้องนั้นว่าง<br>
	 * return false เมื่อไม่พบห้องว่าง<br>
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	protected boolean checkEmptyRoom(CCTConnection conn, String startDate, String startTime, String endDate, String endTime, String roomId) throws Exception {
		boolean isEmpty = false;
		List<EmptyRoom> listResult = searchListEmptyRoom(conn, startDate, startTime, endDate, endTime);
		if (listResult.size() > 0) {
			
			for (EmptyRoom emptyRoom : listResult) {
				if (emptyRoom.getId().equals(roomId)) {
					isEmpty = true;
					break;
				}
			}
		}
		return isEmpty;
	}

	/**
	 * เปลื่ยนสถานะเป็น Approve
	 * @throws Exception 
	 */
	protected void updateBookingStatusApprove(CCTConnection conn, String eventId) throws Exception {
		UserManager userManager = new UserManager(getLogger());
		String tempUserId = null;
		if (userManager.searchAdminInSystem(conn).get(getUser().getId()) == null) {
			// กรณีไม่ใช่ Admin ต้องตรวจความเป็นเจ้าของด้วย
			tempUserId = getUser().getId();
		}
		
		RoomBooking bookingLastedStatus = dao.searchBookingStatusBeforeApprove(conn, eventId, tempUserId);
		if (bookingLastedStatus != null) {
			getLogger().debug("Found event for approve");
			dao.setBookingStatusApprove(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.APPROVE.getCode()));
		} else {
			getLogger().debug("Not found event for approve");
			throw new Exception("ไม่สามารถ Approve ได้, กรุณาตรวจสอบสถานะอีกครั้ง");
		}
		
		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			RoomBookingData forNoti = dao.searchDataEventDrawByEventId(conn, eventId);
			notificationManager.addApprove(conn, forNoti, bookingLastedStatus, getUser());
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	/**
	 * ใช้สำหรับอัพเดดสถานะ Approve อัตโนมัติ
	 * @param conn
	 * @param eventId
	 * @throws Exception
	 */
	protected void updateBookingStatusApproveByAuto(CCTConnection conn, String eventId) throws Exception {
		
		String tempUserId = null;
		RoomBooking bookingLastedStatus = dao.searchBookingStatusBeforeApprove(conn, eventId, tempUserId);
		if (bookingLastedStatus != null) {
			getLogger().debug("Found event for approve");
			dao.setBookingStatusApprove(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.APPROVE.getCode()));
		} else {
			getLogger().debug("Not found event for approve");
			throw new Exception("ไม่สามารถ Approve ได้, กรุณาตรวจสอบสถานะอีกครั้ง");
		}
		
		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			RoomBookingData forNoti = dao.searchDataEventDrawByEventId(conn, eventId);
			notificationManager.addApprove(conn, forNoti, bookingLastedStatus, getUser());
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	
	/**
	 * เปลื่ยนสถานะเป็น CheckIn
	 * @throws Exception 
	 */
	protected void updateBookingStatusCheckIn(CCTConnection conn, String eventId) throws Exception {
		UserManager userManager = new UserManager(getLogger());
		String tempUserId = null;
		if (userManager.searchAdminInSystem(conn).get(getUser().getId()) == null) {
			// กรณีไม่ใช่ Admin ต้องตรวจความเป็นเจ้าของด้วย
			tempUserId = getUser().getId();
			updateBookingStatusCheckInForGenaral(conn, eventId, tempUserId);
		} else {
			// กรณีเป็น Admin
			updateBookingStatusCheckInForAdmin(conn, eventId, tempUserId);
		}
	}
	
	/**
	 * เปลื่ยนสถานะเป็น CheckIn สำหรับผู้ใช้งานทั่วไป
	 * @throws Exception 
	 */
	private void updateBookingStatusCheckInForGenaral(CCTConnection conn, String eventId, String userId) throws Exception {
		
		RoomBooking bookingLastedStatus = dao.searchBookingStatusBeforeCheckIn(conn, eventId, userId);
		if (bookingLastedStatus != null) {
			getLogger().debug("Found event for check in");
			dao.setBookingStatusCheckInForGenaral(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_IN.getCode()));
		} else {
			getLogger().debug("Not found event for check in");
			throw new CustomException("สามารถ Check In ได้ก่อน " + ParameterExtendedConfig.getParameterExtended().getDataBeforeCheckInMinutes() + " นาที และหลังเริ่มประชุม " + ParameterExtendedConfig.getParameterExtended().getDataAfterCheckInMinutes() + " นาที");
		}
		
		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			RoomBookingData forNoti = dao.searchDataEventDrawByEventId(conn, eventId);
			notificationManager.addCheckIn(conn, forNoti, bookingLastedStatus, getUser());
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	/**
	 * เปลื่ยนสถานะเป็น CheckIn สำหรับ Admin
	 * @throws Exception 
	 */
	private void updateBookingStatusCheckInForAdmin(CCTConnection conn, String eventId, String userId) throws Exception {
		
		RoomBooking bookingLastedStatus = dao.searchBookingStatusBeforeCheckIn(conn, eventId, userId);
		if (bookingLastedStatus != null) {
			getLogger().debug("Found event for CheckIn");
			dao.setBookingStatusCheckInForAdmin(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_IN.getCode()));
		} else {
			getLogger().debug("Not found event for check in");
			throw new CustomException("สามารถ Check In ได้ก่อน " + ParameterExtendedConfig.getParameterExtended().getDataBeforeCheckInMinutes() + " นาที และหลังเริ่มประชุม " + ParameterExtendedConfig.getParameterExtended().getDataAfterCheckInMinutes() + " นาที");
		}
		
		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			RoomBookingData forNoti = dao.searchDataEventDrawByEventId(conn, eventId);
			notificationManager.addCheckIn(conn, forNoti, bookingLastedStatus, getUser());
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	protected void updateBookingStatusCheckOut(CCTConnection conn, String eventId) throws Exception {
		
		UserManager userManager = new UserManager(getLogger());
		String tempUserId = null;
		if (userManager.searchAdminInSystem(conn).get(getUser().getId()) == null) {
			// กรณีไม่ใช่ Admin ต้องตรวจความเป็นเจ้าของด้วย
			tempUserId = getUser().getId();
			updateBookingStatusCheckOutForGenaral(conn, eventId, tempUserId);
		} else {
			// กรณีเป็น Admin
			updateBookingStatusCheckOutForAdmin(conn, eventId, tempUserId);
		}
	}
	
	
	/**
	 * เปลื่ยนสถานะเป็น CheckOut สำหรับผู้ใช้งานทั่วไป
	 * @throws Exception 
	 */
	protected void updateBookingStatusCheckOutForGenaral(CCTConnection conn, String eventId, String userId) throws Exception {
		
		RoomBooking bookingLastedStatus = dao.searchBookingStatusBeforeCheckOut(conn, eventId, userId);
		if (bookingLastedStatus != null) {
			getLogger().debug("Change check in to check out");
			// Check out ก่อนเวลาไหม
			if (dao.searchCheckOutBeforeLimit(conn, eventId)) {
				getLogger().debug("Check out before limit");
				dao.setBookingStatusCheckOutForGenaral(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
			} else {
				dao.setBookingStatusCheckOutForAdmin(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
			}
		} else {
			getLogger().debug("Not found check in for check out");
			bookingLastedStatus = dao.searchBookingStatusBeforeCheckOutIfNotCheckIn(conn, eventId, userId);
			if (bookingLastedStatus != null) {
				getLogger().debug("Change approve to check out");
				// Check out ก่อนเวลาไหม
				if (dao.searchCheckOutBeforeLimit(conn, eventId)) {
					getLogger().debug("Check out before limit");
					dao.setBookingStatusCheckOutForGenaralIfNotCheckIn(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
				} else {
					dao.setBookingStatusCheckOutForAdminIfNotCheckIn(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
				}
			} else {
				getLogger().debug("Not found event for check out");
				throw new CustomException("สามารถ Check Out ได้หลังจากเริ่มประชุม");
			}
		}
		
		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			RoomBookingData forNoti = dao.searchDataEventDrawByEventId(conn, eventId);
			notificationManager.addCheckOut(conn, forNoti, bookingLastedStatus, getUser());
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	/**
	 * เปลื่ยนสถานะเป็น CheckOut สำหรับ Admin
	 * @throws Exception 
	 */
	protected void updateBookingStatusCheckOutForAdmin(CCTConnection conn, String eventId, String userId) throws Exception {
		
		RoomBooking bookingLastedStatus = dao.searchBookingStatusBeforeCheckOut(conn, eventId, userId);
		if (bookingLastedStatus != null) {
			getLogger().debug("Change check in to check out");

			// Check out ก่อนเวลาไหม
			if (dao.searchCheckOutBeforeLimit(conn, eventId)) {
				getLogger().debug("Check out before limit");
				dao.setBookingStatusCheckOutForGenaral(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
			} else {
				dao.setBookingStatusCheckOutForAdmin(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
			}
		} else {
			getLogger().debug("Not found check in for check out");
			bookingLastedStatus = dao.searchBookingStatusBeforeCheckOutIfNotCheckIn(conn, eventId, userId);
			if (bookingLastedStatus != null) {
				getLogger().debug("Change approve to check out");
				
				// Check out ก่อนเวลาไหม
				if (dao.searchCheckOutBeforeLimit(conn, eventId)) {
					getLogger().debug("Check out before limit");
					dao.setBookingStatusCheckOutForGenaralIfNotCheckIn(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
				} else {
					dao.setBookingStatusCheckOutForAdminIfNotCheckIn(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
				}
			} else {
				getLogger().debug("Not found event for check out");
				throw new CustomException("สามารถ Check Out ได้หลังจากเริ่มประชุม");
			}
		}
		
		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			RoomBookingData forNoti = dao.searchDataEventDrawByEventId(conn, eventId);
			notificationManager.addCheckOut(conn, forNoti, bookingLastedStatus, getUser());
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	
	protected void updateBookingStatusOverLimitCheckOut(CCTConnection conn, String eventId, String timeOverLimitCheckOut) throws Exception {
		
		UserManager userManager = new UserManager(getLogger());
		String tempUserId = null;
		if (userManager.searchAdminInSystem(conn).get(getUser().getId()) == null) {
			// กรณีไม่ใช่ Admin ต้องตรวจความเป็นเจ้าของด้วย
			tempUserId = getUser().getId();
		}
		
		RoomBooking bookingLastedStatus = dao.searchBookingStatusBeforeCheckOut(conn, eventId, tempUserId);
		if (bookingLastedStatus != null) {
			getLogger().debug("Change check in to check out");
			dao.setBookingStatusOverLimitCheckOut(conn, eventId, timeOverLimitCheckOut, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
		} else {
			getLogger().debug("Not found check in for check out");
			bookingLastedStatus = dao.searchBookingStatusBeforeCheckOutIfNotCheckIn(conn, eventId, tempUserId);
			if (bookingLastedStatus != null) {
				getLogger().debug("Change approve to check out");
				dao.setBookingStatusOverLimitCheckOutIfNotCheckIn(conn, eventId, timeOverLimitCheckOut, searchBookingStatus(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode()));
			} else {
				getLogger().debug("Not found event for check out");
				throw new CustomException("สามารถ Check Out ได้หลังจากเริ่มประชุม");
			}
		}

		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			RoomBookingData forNoti = dao.searchDataEventDrawByEventId(conn, eventId);
			notificationManager.addCheckOut(conn, forNoti, bookingLastedStatus, getUser());
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	protected void updateBookingStatusCancel(CCTConnection conn, String eventId) throws Exception {
		
		UserManager userManager = new UserManager(getLogger());
		String tempUserId = null;
		if (userManager.searchAdminInSystem(conn).get(getUser().getId()) == null) {
			// กรณีไม่ใช่ Admin ต้องตรวจความเป็นเจ้าของด้วย
			tempUserId = getUser().getId();
			updateBookingStatusCancelForGenaral(conn, eventId, tempUserId);
		} else {
			// กรณีเป็น Admin
			updateBookingStatusCancelForAdmin(conn, eventId, tempUserId);
		}
	}

	/**
	 * เปลื่ยนสถานะเป็น Cancel
	 * @throws Exception 
	 */
	protected void updateBookingStatusCancelForGenaral(CCTConnection conn, String eventId, String userId) throws Exception {
		
		RoomBooking bookingLastedStatus = dao.searchBookingStatusBeforeCancel(conn, eventId, userId); 
		if (bookingLastedStatus != null) {
			getLogger().debug("Found event for cancel");
			dao.setBookingStatusCancel(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CANCELED.getCode()));
		} else {
			getLogger().debug("Not found event for cancel");
			throw new Exception("ไม่สามารถยกเลิกได้, กรุณาตรวจสอบสถานะอีกครั้ง");
		}
		
		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			RoomBookingData forNoti = dao.searchDataEventDrawByEventId(conn, eventId);
			notificationManager.addCancel(conn, forNoti, bookingLastedStatus, getUser());
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	/**
	 * เปลื่ยนสถานะเป็น Cancel
	 * @throws Exception 
	 */
	protected void updateBookingStatusCancelForAdmin(CCTConnection conn, String eventId, String userId) throws Exception {
		
		RoomBooking bookingLastedStatus = dao.searchBookingStatusBeforeCancel(conn, eventId, userId); 
		if (bookingLastedStatus != null) {
			getLogger().debug("Found event for cancel");
			dao.setBookingStatusCancel(conn, eventId, searchBookingStatus(BKOnlineVariable.BookingStatus.CANCELED.getCode()));
		} else {
			getLogger().debug("Not found event for cancel");
			throw new Exception("ไม่สามารถยกเลิกได้, กรุณาตรวจสอบสถานะอีกครั้ง");
		}
		
		try {
			NotificationManager notificationManager = new NotificationManager(getLogger());
			
			RoomBookingData forNoti = dao.searchDataEventDrawByEventId(conn, eventId);
			notificationManager.addCancel(conn, forNoti, bookingLastedStatus, getUser());
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	protected RoomBookingData searchDataEventDrawByEventId(CCTConnection conn, String eventId) throws Exception {
		return dao.searchDataEventDrawByEventId(conn, eventId);
	}
	
	protected List<RoomBookingData> searchDataEventDraw(CCTConnection conn, RoomBookingData data) throws Exception {
		BKOnlineVariable.Mode mode = BKOnlineVariable.Mode.valueOf(data.getCurrentMode().toUpperCase());
		BKOnlineVariable.Step step = null;
		if (!data.getCurrentStep().isEmpty()) {
			step = BKOnlineVariable.Step.valueOf(data.getCurrentStep().toUpperCase());
		}
		getLogger().debug("mode: " + mode + ", step: " + step);
		
		if (mode.equals(BKOnlineVariable.Mode.DAY)) {
			initDayData(conn, data, step);
			
		} else if (mode.equals(BKOnlineVariable.Mode.WEEK)) {
			initWeekData(conn, data, step);
			
		} else if (mode.equals(BKOnlineVariable.Mode.MONTH)) {
			initMonthData(conn, data, step);
		}
		boolean flngAdmin = false;
		if (new UserManager(getLogger()).searchAdminInSystem(conn).get(getUser().getId()) != null) {
			// กรณีไม่ใช่ Admin ต้องตรวจความเป็นเจ้าของด้วย
			flngAdmin = true;
		}
		return dao.searchDataEventDraw(conn, data, flngAdmin, getUser(), getLocale());
	}
	
	private void initNowData(CCTConnection conn, RoomBookingData data) throws Exception {
		
		String currentDay = StringDelimeter.EMPTY.getValue();
		if ((data.getCurrentDay() == null) || data.getCurrentDay().trim().isEmpty()) {
			currentDay =  DateUtil.getCurrentDateDB(conn.getConn(), conn.getDbType());
		} else {
			currentDay = data.getCurrentDay();
		}
		getLogger().debug("currentDay: " + currentDay);
		data.setCurrentDay(currentDay.substring(0, 10));
		data.setCurrentMode(BKOnlineVariable.Mode.WEEK.getValue());
	}
	
	/**
	 * กำหนดตัวแปรสำหรับแสดงผลแบบวันเดียว
	 * @param data
	 * @throws Exception 
	 */
	private void initDayData(CCTConnection conn, RoomBookingData data, BKOnlineVariable.Step step) throws Exception {
		if (step == null) {
			initNowData(conn, data);
		} 
		
		Calendar calendarCurrent = CalendarUtil.getCalendarFromDateString(data.getCurrentDay(), ParameterConfig.getDateFormat().getForDisplay(), ParameterConfig.getApplication().getDatabaseLocale());
		calendarCurrent.setFirstDayOfWeek(Calendar.MONDAY);
		
		if (step == null) {
			// Nothing
		} else if (step.equals(BKOnlineVariable.Step.PREVIOUS)) {
			calendarCurrent.add(Calendar.DAY_OF_MONTH, -1);
			
		} else if (step.equals(BKOnlineVariable.Step.NEXT)) {
			calendarCurrent.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		data.setCurrentDay(CalendarUtil.getDateStringFromCalendar(calendarCurrent, ParameterConfig.getDateFormat().getForDisplay()));
		data.setStartEventDate(CalendarUtil.getDateStringFromCalendar(calendarCurrent, ParameterConfig.getDateFormat().getForDatabaseInsert()));
		data.setEndEventDate(CalendarUtil.getDateStringFromCalendar(calendarCurrent, ParameterConfig.getDateFormat().getForDatabaseInsert()));
		
		getLogger().debug(data.getCurrentDay() + " > " + data.getStartEventDate() + " > " + data.getEndEventDate());
	}
	
	/**
	 * กำหนดตัวแปรสำหรับแสดงผลแบบสัปดาห์
	 * @param data
	 * @throws Exception 
	 */
	private void initWeekData(CCTConnection conn, RoomBookingData data, BKOnlineVariable.Step step) throws Exception {
		if (step == null) {
			initNowData(conn, data);
		} 
		
		Calendar calendarCurrent = CalendarUtil.getCalendarFromDateString(data.getCurrentDay(), ParameterConfig.getDateFormat().getForDisplay(), ParameterConfig.getApplication().getDatabaseLocale());
		calendarCurrent.setFirstDayOfWeek(Calendar.MONDAY);
		
		if (step == null) {
			// Nothing
		} else if (step.equals(BKOnlineVariable.Step.PREVIOUS)) {
			calendarCurrent.add(Calendar.DAY_OF_MONTH, -7);
			
		} else if (step.equals(BKOnlineVariable.Step.NEXT)) {
			calendarCurrent.add(Calendar.DAY_OF_MONTH, 7);
		}
		
		
		Calendar calendarStart = Calendar.getInstance(ParameterConfig.getApplication().getDatabaseLocale());
		calendarStart.setFirstDayOfWeek(Calendar.MONDAY);
		calendarStart.setTimeInMillis(calendarCurrent.getTimeInMillis());
		
		Calendar calendarEnd = Calendar.getInstance(ParameterConfig.getApplication().getDatabaseLocale());
		calendarEnd.setFirstDayOfWeek(Calendar.MONDAY);
		calendarEnd.setTimeInMillis(calendarCurrent.getTimeInMillis());
		
		int[] amountArray = initDayRange(calendarStart);
		int amountStart = amountArray[0];
		int amountEnd = amountArray[1];
		
		calendarEnd.add(Calendar.DAY_OF_MONTH, amountEnd);
		calendarStart.add(Calendar.DAY_OF_MONTH, amountStart);
		
		data.setCurrentDay(CalendarUtil.getDateStringFromCalendar(calendarCurrent, ParameterConfig.getDateFormat().getForDisplay()));
		data.setStartEventDate(CalendarUtil.getDateStringFromCalendar(calendarStart, ParameterConfig.getDateFormat().getForDatabaseInsert()));
		data.setEndEventDate(CalendarUtil.getDateStringFromCalendar(calendarEnd, ParameterConfig.getDateFormat().getForDatabaseInsert()));
		
		getLogger().debug(data.getCurrentDay() + " > " + data.getStartEventDate() + " > " + data.getEndEventDate());
	}
	
	/**
	 * กำหนดตัวแปรสำหรับแสดงผลแบบเดือน
	 * @param data
	 * @throws Exception 
	 */
	private void initMonthData(CCTConnection conn, RoomBookingData data, BKOnlineVariable.Step step) throws Exception {
		if (step == null) {
			initNowData(conn, data);
		} 
		
		Calendar calendarCurrent = CalendarUtil.getCalendarFromDateString(data.getCurrentDay(), ParameterConfig.getDateFormat().getForDisplay(), ParameterConfig.getApplication().getDatabaseLocale());
		calendarCurrent.setFirstDayOfWeek(Calendar.MONDAY);
		
		if (step == null) {
			// Nothing
		} else if (step.equals(BKOnlineVariable.Step.PREVIOUS)) {
			calendarCurrent.add(Calendar.MONTH, -1);
			
		} else if (step.equals(BKOnlineVariable.Step.NEXT)) {
			calendarCurrent.add(Calendar.MONTH, 1);
		}
		
		int firstDay = calendarCurrent.getActualMinimum(Calendar.DAY_OF_MONTH);
		int lastDay = calendarCurrent.getActualMaximum(Calendar.DAY_OF_MONTH);
		getLogger().debug("First & Last: " + firstDay + " > " + lastDay);
		
		Calendar calendarStart = Calendar.getInstance(ParameterConfig.getApplication().getDatabaseLocale());
		calendarStart.setFirstDayOfWeek(Calendar.MONDAY);
		calendarStart.setTimeInMillis(calendarCurrent.getTimeInMillis());
		calendarStart.set(Calendar.DAY_OF_MONTH, firstDay);
		
		Calendar calendarEnd = Calendar.getInstance(ParameterConfig.getApplication().getDatabaseLocale());
		calendarEnd.setFirstDayOfWeek(Calendar.MONDAY);
		calendarEnd.setTimeInMillis(calendarCurrent.getTimeInMillis());
		calendarEnd.set(Calendar.DAY_OF_MONTH, lastDay);

		int[] amountStartArray = initDayRange(calendarStart);
		int amountStart = amountStartArray[0];
		
		int[] amountEndArray = initDayRange(calendarEnd);
		int amountEnd = amountEndArray[1];
		
		System.out.println("amountStart & amountEnd: " + amountStart + " > " + amountEnd);
		
		calendarStart.add(Calendar.DAY_OF_MONTH, amountStart);
		calendarEnd.add(Calendar.DAY_OF_MONTH, amountEnd);
		
		data.setCurrentDay(CalendarUtil.getDateStringFromCalendar(calendarCurrent, ParameterConfig.getDateFormat().getForDisplay()));
		data.setStartEventDate(CalendarUtil.getDateStringFromCalendar(calendarStart, ParameterConfig.getDateFormat().getForDatabaseInsert()));
		data.setEndEventDate(CalendarUtil.getDateStringFromCalendar(calendarEnd, ParameterConfig.getDateFormat().getForDatabaseInsert()));
		
		getLogger().debug(data.getCurrentDay() + " > " + data.getStartEventDate() + " > " + data.getEndEventDate());
	}
	
	private int[] initDayRange(Calendar calendarCurrent) {
		
		int amountStart = 0;
		int amountEnd = 0;
		if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			getLogger().debug("DAY_OF_WEEK: MONDAY");
			// +6
			amountEnd = +6;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			getLogger().debug("DAY_OF_WEEK: TUESDAY");
			// -1, +5
			amountStart = -1;
			amountEnd = +5;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			getLogger().debug("DAY_OF_WEEK: WEDNESDAY");
			// -2, +4 
			amountStart = -2;
			amountEnd = +4;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			getLogger().debug("DAY_OF_WEEK: THURSDAY");
			// -3, +3
			amountStart = -3;
			amountEnd = +3;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			getLogger().debug("DAY_OF_WEEK: FRIDAY");
			// -4, +2
			amountStart = -4;
			amountEnd = +2;
			
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			getLogger().debug("DAY_OF_WEEK: SATURDAY");
			// -5, +1
			amountStart = -5;
			amountEnd = +1;
		
		} else if (calendarCurrent.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			getLogger().debug("DAY_OF_WEEK: SUNDAY");
			// -6, +0
			amountStart = -6;
		}
		
		int[] result = new int[2];
		result[0] = amountStart;
		result[1] = amountEnd;
		return result;
	}
	
	/**
	 * ตรวจสอบวันที่จองล่วงหน้า 90 วัน จองห้องย้อนหลังได้ 15 นาที<br>
	 * ตรวจสอบจากวันที่เริ่มต้น<br>
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	protected void validateBookingDate(CCTConnection conn, RoomBooking data) throws Exception {
		
		long oneDayPerMillisecond = 86400000;
		// long oneHourPerMillisecound = 3600000;
		long oneMinutePerMillisecound = 60000;
		long minutesForCheck = (oneMinutePerMillisecound * ParameterExtendedConfig.getParameterExtended().getBookingRoomBeforeMinutes()) * - 1; // 15 นาที
		long totalDayForCheck = oneDayPerMillisecond * ParameterExtendedConfig.getParameterExtended().getBookingRoomReserveDays(); // 90 วัน
		
		String calendarFormat = ParameterConfig.getDateFormat().getForDisplayHHMM();
		// String dbFormat = ParameterConfig.getDateFormat().getForDatabaseInsertHHMM();
		Locale dbLocale = ParameterConfig.getApplication().getDatabaseLocale();
		
		
		String stringToday = DateUtil.getCurrentDateDB(conn.getConn(), conn.getDbType());
		Calendar calendarToday = CalendarUtil.getCalendarFromDateString(stringToday, calendarFormat, dbLocale);
		
		String stringEventDay = data.getStartDate() + StringDelimeter.BLANK.getValue() + data.getStartTime();
		Calendar calendarEventDay = CalendarUtil.getCalendarFromDateString(stringEventDay, calendarFormat, dbLocale);
		
		// จองห้องย้อนหลังได้ 15 นาที
		long bookingAfter = calendarEventDay.getTimeInMillis() - calendarToday.getTimeInMillis();
		
		getLogger().debug("stringToday: " + stringToday + ", stringEventDay: " + stringEventDay);
		getLogger().debug("bookingAfter: " + bookingAfter + ", totalDayForCheck: " + totalDayForCheck);
		
		if (bookingAfter < minutesForCheck) {
			// จองย้อนหลังเกิน 15 นาที
			throw new Exception("ไม่สามารถจองย้อนหลังเกิน " + ParameterExtendedConfig.getParameterExtended().getBookingRoomBeforeMinutes() + " นาทีได้");
		}
		
		// จองล่วงหน้า
		if (bookingAfter > totalDayForCheck) {
			// จองย้อนหลังเกิน 15 นาที
			throw new Exception("ไม่สามารถจองล่วงหน้าได้เกิน " + ParameterExtendedConfig.getParameterExtended().getBookingRoomReserveDays() + " วัน");
		}
	}
	
	/**
	 * ค้นหาสถานะห้อง
	 * @param bookingStatusCode
	 * @return
	 */
	private RoomBooking searchBookingStatus(String bookingStatusCode) {
		String bookingStatusName = StringDelimeter.EMPTY.getValue();
		List<CommonSelectItem> listBookingStatus = SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.EVENT_STATUS);
		for (CommonSelectItem selectItem : listBookingStatus) {
			if (selectItem.getKey().equals(bookingStatusCode)) {
				bookingStatusName = selectItem.getValue();
				break;
			}
		}
		
		RoomBooking bookingStatus = new RoomBooking();
		bookingStatus.setBookingStatusCode(bookingStatusCode);
		bookingStatus.setBookingStatusName(bookingStatusName);
		return bookingStatus;
	}
	/**
	 * ใช้ตรวจสอบข้อมูลที่บันทึกกับฐานข้อมูลว่ามีไหม
	 * @param conn
	 * @param data
	 * @throws Exception
	 */
	protected void validateBookingNow(CCTConnection conn, RoomBooking data) throws Exception {
		validateUserIdBookingNow(conn, data.getUserBookId(), data.getUserBookName());
		validateRoomIdBookingNow(conn, data.getRoomId(), data.getRoomName());
	}
	
	protected void validateUserIdBookingNow(CCTConnection conn, String userBookId, String userBookName) throws Exception {
		// ตรวจสอบข้อมูลในฐาน ต้องมี ชื่อผู้จอง
		if (!dao.searchUserIdForValidate(conn, userBookId, userBookName)) {
			getLogger().debug("Not found user.");
			ServerValidateUtil.createServerValidateException(DefaultExceptionMessage.SERVER_VALIDATE);
		}
	}
	
	protected void validateRoomIdBookingNow(CCTConnection conn, String roomId, String roomName) throws Exception {
		// ตรวจสอบข้อมูลในฐาน ต้องมี ห้องประชุม
		if (!dao.searchRoomIdForValidate(conn, roomId, roomName)) {
			getLogger().debug("Not found room.");
			ServerValidateUtil.createServerValidateException(DefaultExceptionMessage.SERVER_VALIDATE);
		}
	}
	
	/**
	 * สร้างรหัสอ้างอิงการจอง
	 * @return
	 */
	private long generateBookingId() {
		return Calendar.getInstance(ParameterConfig.getApplication().getDatabaseLocale()).getTimeInMillis();
	}
}
