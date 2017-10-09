package com.cct.inhouse.bkonline.core.booking.roombooking.domain;

import com.cct.inhouse.common.InhouseDomain;

public class EmptyRoom extends InhouseDomain {

	private static final long serialVersionUID = 1913783305595047149L;

	private String roomName;
	private String roomColor;
	private String autotimeId;
	private String autotimeValue;
	private String attendeesMax;
	private String roomPhone;
	private String roomDetail;
	private String deleted;

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomColor() {
		return roomColor;
	}

	public void setRoomColor(String roomColor) {
		this.roomColor = roomColor;
	}

	public String getAutotimeId() {
		return autotimeId;
	}

	public void setAutotimeId(String autotimeId) {
		this.autotimeId = autotimeId;
	}

	public String getAutotimeValue() {
		return autotimeValue;
	}

	public void setAutotimeValue(String autotimeValue) {
		this.autotimeValue = autotimeValue;
	}

	public String getAttendeesMax() {
		return attendeesMax;
	}

	public void setAttendeesMax(String attendeesMax) {
		this.attendeesMax = attendeesMax;
	}

	public String getRoomPhone() {
		return roomPhone;
	}

	public void setRoomPhone(String roomPhone) {
		this.roomPhone = roomPhone;
	}

	public String getRoomDetail() {
		return roomDetail;
	}

	public void setRoomDetail(String roomDetail) {
		this.roomDetail = roomDetail;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
}
