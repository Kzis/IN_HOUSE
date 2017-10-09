package com.cct.inhouse.bkonline.core.report.domain;

import com.cct.domain.SearchCriteria;

public class ReportSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = -6798970587744463742L;

	private String roomid;
	private String type;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;	
	private String week;
	
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}	
}
