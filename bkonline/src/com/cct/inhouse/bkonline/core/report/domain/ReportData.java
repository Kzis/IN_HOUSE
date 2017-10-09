package com.cct.inhouse.bkonline.core.report.domain;

import com.cct.inhouse.common.InhouseDomain;

public class ReportData extends InhouseDomain {

	private static final long serialVersionUID = 2435178586649432746L;
	
	private String date;
	private String room;
	private String subject;
	private String startTime;
	private String endTime;	
	private String userBooking;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUserBooking() {
		return userBooking;
	}
	public void setUserBooking(String userBooking) {
		this.userBooking = userBooking;
	}
	
	
}
