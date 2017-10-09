package com.cct.inhouse.bkonline.core.mobile.domain;


/**
 * ใช้สำหรับการจองห้อง ผ่านระบบจอง
 * 
 * @author sittipol.m
 *
 */
public class MDRoomBooking implements Cloneable {

	private String subject; // หัวข้อ
	private String startDate; // เริ่มประชุมวันที่
	private String startTime; // เริ่มประชุมเวลา
	private String endDate; // จบประชุมวันที่
	private String endTime; // จบประชุมเวลา

	private String createUserId; // ไอดีผู้สร้าง
	private String createUserName; // ชื่อผู้สร้าง
	private String createDateTime; // วันที่เวลาที่สร้าง
	private String updateUserId; // ไอดีผู้แก้ไข
	private String updateUserName; // ชื่อผู้แก้ไข
	private String updateDateTime; // วันที่เวลาที่แก้ไข

	private String checkInDateTime; // วันที่เวลาเข้าใช้งานห้อง
	private String checkOutDateTime; // วันที่เวลาออกจากห้อง

	private String detail; // รายละเอียดการประชุม
	private String equipmentListId; // ไอดีอุปกรณ์ที่ต้องการ
	private String equipmentListName; // ชื่ออุปกรณ์ที่ต้องการ

	private String bookingId; // ไอดีการจอง

	private String userBookId; // ไอดีผู้จอง
	private String userBookName; // ชื่อผู้จอง

	private String userBookEmail; // อีเมล์ผุ้จอง
	private String userBookPhone; // เบอร์โทรผู้จอง
	private String userBookLineId; // line id ผู้จอง
	private String userBookDepartmentId; // แผนก สำหรับออกรายงานสถิติ
	private String userBookPositionId; // ตำแหน่ง
	
	private String notificationEmail; // แจ้ง Notification โดยช่องทางไหน
	private String notificationLine; // แจ้ง Notification โดยช่องทางไหน
	private String attendees; // จำนวนผู้เข้าร่วมประชุม

	private String roomId; // ไอดีห้อง
	private String roomName; // ชื่อห้อง

	private String bookingStatusCode; // สถานะการใช้งานห้อง
	private String bookingStatusName; // สถานะการใช้งานห้อง

	private String comment;
	private String deleted;

	public MDRoomBooking clone() throws CloneNotSupportedException {
		return (MDRoomBooking) super.clone();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getCheckInDateTime() {
		return checkInDateTime;
	}

	public void setCheckInDateTime(String checkInDateTime) {
		this.checkInDateTime = checkInDateTime;
	}

	public String getCheckOutDateTime() {
		return checkOutDateTime;
	}

	public void setCheckOutDateTime(String checkOutDateTime) {
		this.checkOutDateTime = checkOutDateTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getEquipmentListId() {
		return equipmentListId;
	}

	public void setEquipmentListId(String equipmentListId) {
		this.equipmentListId = equipmentListId;
	}

	public String getEquipmentListName() {
		return equipmentListName;
	}

	public void setEquipmentListName(String equipmentListName) {
		this.equipmentListName = equipmentListName;
	}

	public String getUserBookId() {
		return userBookId;
	}

	public void setUserBookId(String userBookId) {
		this.userBookId = userBookId;
	}

	public String getUserBookEmail() {
		return userBookEmail;
	}

	public void setUserBookEmail(String userBookEmail) {
		this.userBookEmail = userBookEmail;
	}

	public String getUserBookPhone() {
		return userBookPhone;
	}

	public void setUserBookPhone(String userBookPhone) {
		this.userBookPhone = userBookPhone;
	}

	public String getUserBookLineId() {
		return userBookLineId;
	}

	public void setUserBookLineId(String userBookLineId) {
		this.userBookLineId = userBookLineId;
	}

	public String getAttendees() {
		return attendees;
	}

	public void setAttendees(String attendees) {
		this.attendees = attendees;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getUserBookName() {
		return userBookName;
	}

	public void setUserBookName(String userBookName) {
		this.userBookName = userBookName;
	}

	public String getBookingStatusName() {
		return bookingStatusName;
	}

	public void setBookingStatusName(String bookingStatusName) {
		this.bookingStatusName = bookingStatusName;
	}

	public String getBookingStatusCode() {
		return bookingStatusCode;
	}

	public void setBookingStatusCode(String bookingStatusCode) {
		this.bookingStatusCode = bookingStatusCode;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getUserBookDepartmentId() {
		return userBookDepartmentId;
	}

	public void setUserBookDepartmentId(String userBookDepartmentId) {
		this.userBookDepartmentId = userBookDepartmentId;
	}

	public String getUserBookPositionId() {
		return userBookPositionId;
	}

	public void setUserBookPositionId(String userBookPositionId) {
		this.userBookPositionId = userBookPositionId;
	}

	public String getNotificationEmail() {
		return notificationEmail;
	}

	public void setNotificationEmail(String notificationEmail) {
		this.notificationEmail = notificationEmail;
	}

	public String getNotificationLine() {
		return notificationLine;
	}

	public void setNotificationLine(String notificationLine) {
		this.notificationLine = notificationLine;
	}
}
