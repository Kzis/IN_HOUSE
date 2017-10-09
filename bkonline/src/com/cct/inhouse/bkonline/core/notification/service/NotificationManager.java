package com.cct.inhouse.bkonline.core.notification.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.cct.common.CommonManager;
import com.cct.common.CommonUser;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBooking;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingData;
import com.cct.inhouse.bkonline.core.notification.domain.NotificationMessage;

import util.database.CCTConnection;

public class NotificationManager extends CommonManager {

	private NotificationService service = null;
	
	public NotificationManager(Logger logger) {
		super(logger);
		this.service = new NotificationService(logger);
	}
	
	/**
	 * นับจำนวน Message ใหม่
	 * @param conn
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int searchCountNotificationStatusTodo(CCTConnection conn, CommonUser user) throws Exception {
		return service.searchCountNotificationStatusTodo(conn, user);
	}
	
	public int searchCountNotificationStatusAction(CCTConnection conn, CommonUser user) throws Exception {
		return service.searchCountNotificationStatusAction(conn, user);
	}
	
	/**
	 * ค้น Message ล่าสุดที่อ่านแล้ว และยังไม่อ่านมาแสดง
	 * @param conn
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public List<NotificationMessage> searchNotificationTodo(CCTConnection conn, CommonUser user) throws Exception {
		return service.searchNotificationTodo(conn, user);
	}
	
	/**
	 * ค้น Message ล่าสุดที่อ่านแล้ว และยังไม่อ่านมาแสดง
	 * @param conn
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public List<NotificationMessage> searchNotificationAction(CCTConnection conn, CommonUser user) throws Exception {
		return service.searchNotificationAction(conn, user);
	}
	
	/**
	 * สร้าง noti ของการจอง 
	 * @param conn
	 * @param bookingData
	 * @throws Exception
	 */
	public void addBooking(CCTConnection conn, RoomBookingData bookingData, CommonUser user) throws Exception {
		service.addBooking(conn, bookingData, user);
	}
	
	/**
	 * สร้าง noti ของ Approve
	 * @param conn
	 * @param bookingData
	 * @throws Exception
	 */
	public void addApprove(CCTConnection conn, RoomBookingData bookingData, RoomBooking lastStatus, CommonUser user) throws Exception {
		service.addApprove(conn, bookingData, lastStatus, user);
	}
	
	/**
	 * สร้าง noti ของ CheckIn
	 * @param conn
	 * @param bookingData
	 * @throws Exception
	 */
	public void addCheckIn(CCTConnection conn, RoomBookingData bookingData, RoomBooking lastStatus, CommonUser user) throws Exception {
		service.addCheckIn(conn, bookingData, lastStatus, user);
	}
	
	/**
	 * สร้าง noti ของ CheckOut
	 * @param conn
	 * @param bookingData
	 * @throws Exception
	 */
	public void addCheckOut(CCTConnection conn, RoomBookingData bookingData, RoomBooking lastStatus, CommonUser user) throws Exception {
		service.addCheckOut(conn, bookingData, lastStatus, user);
	}
	
	/**
	 * สร้าง noti ของ Cancel
	 * @param conn
	 * @param bookingData
	 * @throws Exception
	 */
	public void addCancel(CCTConnection conn, RoomBookingData bookingData, RoomBooking lastStatus, CommonUser user) throws Exception {
		service.addCancel(conn, bookingData, lastStatus, user);
	}

	/**
	 * สร้าง noti ของ Not CheckIn
	 * @param conn
	 * @throws Exception
	 */
	public void addNotCheckIn(CCTConnection conn) throws Exception {
		service.addNotCheckIn(conn);
	}
	
	/**
	 * สร้าง noti ของ Not CheckOut
	 * @param conn
	 * @throws Exception
	 */
	public void addNotCheckOut(CCTConnection conn) throws Exception {
		service.addNotCheckOut(conn);
	}
	
	/**
	 * เคลียร์ Badge และ Message กรณีคลิกแล้ว
	 * @param conn
	 * @param bookingData
	 * @throws Exception
	 */
	public void clearNotificationMessage(CCTConnection conn, String notificationId, CommonUser user) throws Exception {
		service.clearNotificationMessage(conn, notificationId, user);
	}
}
