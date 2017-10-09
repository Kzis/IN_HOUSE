package com.cct.inhouse.bkonline.core.setting.roomsetting.domain;

import com.cct.inhouse.common.InhouseDomain;

public class RoomClosed extends InhouseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8469013320363296679L;

	private String roomId;		// ID ห้อง
	private String startDate;	// วันที่ปิดใช้งานเริ่มต้น
	private String startDateForShow;
	private String startTime;	// เวลาที่ปิดใช้งานเริ่มต้น
	private String endDate;		// วันที่ปิดใช้งานสิ้นสุด
	private String endDateForShow;
	private String endTime;		// เวลาที่ปิดใช้งานสิ้นสุด

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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

	public String getStartDateForShow() {
		return startDateForShow;
	}

	public void setStartDateForShow(String startDateForShow) {
		this.startDateForShow = startDateForShow;
	}

	public String getEndDateForShow() {
		return endDateForShow;
	}

	public void setEndDateForShow(String endDateForShow) {
		this.endDateForShow = endDateForShow;
	}

}
