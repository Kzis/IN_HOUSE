package com.cct.inhouse.bkonline.core.booking.roombooking.service;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonUser;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.EmptyRoom;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBooking;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingData;

import util.database.CCTConnection;

public class RoomBookingManager extends AbstractManager<RoomBooking, RoomBooking, RoomBooking, CommonUser, Locale> {

	private RoomBookingService service = null;

	public RoomBookingManager(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new RoomBookingService(logger, user, locale);

	}
	
	/**
	 * ค้นหาห้องว่าง
	 */
	public List<EmptyRoom> searchListEmptyRoom(CCTConnection conn, String startDate, String startTime, String endDate, String endTime) throws Exception {
		return service.searchListEmptyRoom(conn, startDate, startTime, endDate, endTime);
	}
	
	/**
	 * ตรวจสอบห้องว่าง และจองทันที
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String bookingNow(CCTConnection conn, RoomBooking data) throws Exception {
		return service.bookingNow(conn, data);
	}
	
	public RoomBookingData searchDataEventDrawByBookingId(CCTConnection conn, String bookingId) throws Exception {
		return service.searchDataEventDrawByBookingId(conn, bookingId);
	}
	
	/**
	 * ลบข้อมูลการจองเก่าออก และจองใหม่
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String bookingUpdate(CCTConnection conn, RoomBooking data) throws Exception {
		return service.bookingUpdate(conn, data);
	}

	/**
	 * เปลื่ยนสถานเป็น Approve
	 * @param conn
	 * @param eventId
	 * @throws Exception
	 */
	public void updateBookingStatusApprove(CCTConnection conn, String eventId) throws Exception {
		service.updateBookingStatusApprove(conn, eventId);
	}
	
	/**
	 * ใช้สำหรับอัพเดดสถานะ Approve อัตโนมัติ
	 * @param conn
	 * @param eventId
	 * @throws Exception
	 */
	public void updateBookingStatusApproveByAuto(CCTConnection conn, String eventId) throws Exception {
		service.updateBookingStatusApproveByAuto(conn, eventId);
	}
	
	/**
	 * เปลื่ยนสถานะเป็น Check in สำหรับผุ้ใช้ทั่วไป<br>
	 * เปลื่ยนสถานะเป็น Check in สำหรับ Admin
	 * @param conn
	 * @param eventId
	 * @throws Exception
	 */
	public void updateBookingStatusCheckIn(CCTConnection conn, String eventId) throws Exception {
		service.updateBookingStatusCheckIn(conn, eventId);
	}
	
	/**
	 * เปลื่ยนสถานะเป็น CheckOut สำหรับผุ้ใช้ทั่วไป<br>
	 * ปลื่ยนสถานะเป็น CheckOut สำหรับ Admin
	 * @param conn
	 * @param eventId
	 * @throws Exception
	 */
	public void updateBookingStatusCheckOut(CCTConnection conn, String eventId) throws Exception {
		service.updateBookingStatusCheckOut(conn, eventId);
	}
	
	/**
	 * เปลื่ยนสถานะเป็น CheckOut สำหรับผุ้ใช้ทั่วไป<br>
	 * ปลื่ยนสถานะเป็น CheckOut สำหรับ Admin
	 * @param conn
	 * @param eventId
	 * @throws Exception
	 */
	public void updateBookingStatusOverLimitCheckOut(CCTConnection conn, String eventId, String timeOverLimitCheckOut) throws Exception {
		 service.updateBookingStatusOverLimitCheckOut(conn, eventId, timeOverLimitCheckOut);
	}
	
	
	
	/**
	 * เปลื่ยนสถานะเป็น Cancel
	 * @throws Exception 
	 */
	public void updateBookingStatusCancel(CCTConnection conn, String eventId) throws Exception {
		service.updateBookingStatusCancel(conn, eventId);
	}
	
	
	public List<RoomBookingData> searchDataEventDraw(CCTConnection conn, RoomBookingData data) throws Exception {
		return service.searchDataEventDraw(conn, data);
	}
	
	public RoomBookingData searchDataEventDrawByEventId(CCTConnection conn, String eventId) throws Exception {
		return service.searchDataEventDrawByEventId(conn, eventId);
	}
	
	
	public void validateUserIdBookingNow(CCTConnection conn, String userBookId, String userBookName) throws Exception {
		service.validateUserIdBookingNow(conn, userBookId, userBookName);
	}
	
	public void validateRoomIdBookingNow(CCTConnection conn, String roomId, String roomName) throws Exception {
		service.validateRoomIdBookingNow(conn, roomId, roomName);
	}
	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	public long add(CCTConnection arg0, RoomBooking arg1) throws Exception {
		return 0;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	public long delete(CCTConnection arg0, String arg1) throws Exception {
		return 0;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	public long edit(CCTConnection arg0, RoomBooking arg1) throws Exception {
		return 0;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	public RoomBooking searchById(CCTConnection arg0, String arg1) throws Exception {
		return null;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	public long updateActive(CCTConnection arg0, String arg1, String arg2) throws Exception {
		return 0;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	public List<RoomBooking> search(CCTConnection arg0, RoomBooking arg1) throws Exception {
		return null;
	}
}