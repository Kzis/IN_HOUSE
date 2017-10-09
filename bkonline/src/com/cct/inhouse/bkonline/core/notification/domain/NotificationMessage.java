package com.cct.inhouse.bkonline.core.notification.domain;

public class NotificationMessage {

	private String notificationId;
	private String eventId;
	private String receiverUserId;
	private String subject;
	private String detail;
	private String statusForm;
	private String statusTo;
	private String statusRead;
	private String userId;
	private String userName;
	private String createDateTime;
	
	private String roomName;
	private String eventSubject;
	private String eventStartDate;
	private String eventStartTime;
	private String eventEndTime;

	public String getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatusForm() {
		return statusForm;
	}

	public void setStatusForm(String statusForm) {
		this.statusForm = statusForm;
	}

	public String getStatusTo() {
		return statusTo;
	}

	public void setStatusTo(String statusTo) {
		this.statusTo = statusTo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getStatusRead() {
		return statusRead;
	}

	public void setStatusRead(String statusRead) {
		this.statusRead = statusRead;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getEventSubject() {
		return eventSubject;
	}

	public void setEventSubject(String eventSubject) {
		this.eventSubject = eventSubject;
	}

	public String getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public String getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(String eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public String getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

}
