package com.cct.inhouse.bkonline.core.booking.roombooking.domain;

import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBooking;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.RoomSettingData;
import com.cct.inhouse.common.InhouseDomain;

public class RoomBookingData extends InhouseDomain {

	private static final long serialVersionUID = 3800015505381915589L;
	
	private String eventIdFocus;
	private String eventIdSelected;
	private String roomIdSelected;
	
	private String startEventDate;
	private String endEventDate;
	
	private String currentTime; // HH:ss
	private String currentDay; // DD/MM/YYYY
	private String currentMode; // Day, Week, Month
	private String currentStep; // Previous, Next
	
	private boolean dataAdmin;
	private boolean dataOwner;
	private boolean dataOverLimitCheckOut;
	private String timeOverLimitCheckOut;
	
	private RoomSettingData roomSettingData = new RoomSettingData();
	private RoomBooking roomBooking = new RoomBooking();

	public RoomBooking getRoomBooking() {
		return roomBooking;
	}

	public void setRoomBooking(RoomBooking roomBooking) {
		this.roomBooking = roomBooking;
	}

	public RoomSettingData getRoomSettingData() {
		return roomSettingData;
	}

	public void setRoomSettingData(RoomSettingData roomSettingData) {
		this.roomSettingData = roomSettingData;
	}

	public String getRoomIdSelected() {
		return roomIdSelected;
	}

	public void setRoomIdSelected(String roomIdSelected) {
		this.roomIdSelected = roomIdSelected;
	}

	public String getStartEventDate() {
		return startEventDate;
	}

	public void setStartEventDate(String startEventDate) {
		this.startEventDate = startEventDate;
	}

	public String getEndEventDate() {
		return endEventDate;
	}

	public void setEndEventDate(String endEventDate) {
		this.endEventDate = endEventDate;
	}

	public String getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
	}

	public String getCurrentMode() {
		return currentMode;
	}

	public void setCurrentMode(String currentMode) {
		this.currentMode = currentMode;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public String getEventIdSelected() {
		return eventIdSelected;
	}

	public void setEventIdSelected(String eventIdSelected) {
		this.eventIdSelected = eventIdSelected;
	}

	public boolean isDataOwner() {
		return dataOwner;
	}

	public void setDataOwner(boolean dataOwner) {
		this.dataOwner = dataOwner;
	}

	public String getEventIdFocus() {
		return eventIdFocus;
	}

	public void setEventIdFocus(String eventIdFocus) {
		this.eventIdFocus = eventIdFocus;
	}

	public boolean isDataOverLimitCheckOut() {
		return dataOverLimitCheckOut;
	}

	public void setDataOverLimitCheckOut(boolean dataOverLimitCheckOut) {
		this.dataOverLimitCheckOut = dataOverLimitCheckOut;
	}

	public String getTimeOverLimitCheckOut() {
		return timeOverLimitCheckOut;
	}

	public void setTimeOverLimitCheckOut(String timeOverLimitCheckOut) {
		this.timeOverLimitCheckOut = timeOverLimitCheckOut;
	}

	public boolean isDataAdmin() {
		return dataAdmin;
	}

	public void setDataAdmin(boolean dataAdmin) {
		this.dataAdmin = dataAdmin;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
}
