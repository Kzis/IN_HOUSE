package com.cct.inhouse.bkonline.core.booking.roombooking.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.inhouse.common.InhouseModel;

public class RoomBookingModel extends InhouseModel {

	private static final long serialVersionUID = -1298126139309310825L;

	private String tempEventId;
	private String tempCurrentDay;
	
	private RoomBooking roomBooking = new RoomBooking();

	private List<CommonSelectItem> listEquipment = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listNotificationUse = new ArrayList<CommonSelectItem>();

	public RoomBooking getRoomBooking() {
		return roomBooking;
	}

	public void setRoomBooking(RoomBooking roomBooking) {
		this.roomBooking = roomBooking;
	}

	public List<CommonSelectItem> getListEquipment() {
		return listEquipment;
	}

	public void setListEquipment(List<CommonSelectItem> listEquipment) {
		this.listEquipment = listEquipment;
	}

	public List<CommonSelectItem> getListNotificationUse() {
		return listNotificationUse;
	}

	public void setListNotificationUse(List<CommonSelectItem> listNotificationUse) {
		this.listNotificationUse = listNotificationUse;
	}

	public String getTempEventId() {
		return tempEventId;
	}

	public void setTempEventId(String tempEventId) {
		this.tempEventId = tempEventId;
	}

	public String getTempCurrentDay() {
		return tempCurrentDay;
	}

	public void setTempCurrentDay(String tempCurrentDay) {
		this.tempCurrentDay = tempCurrentDay;
	}
}
