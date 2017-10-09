package com.cct.inhouse.bkonline.core.mobile.domain;


public class MDRoomBookingData{

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
	
	private MDRoomSettingData roomSettingData = new MDRoomSettingData();
	private MDRoomBooking roomBooking = new MDRoomBooking();
	
	
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public boolean isDataAdmin() {
		return dataAdmin;
	}
	public void setDataAdmin(boolean dataAdmin) {
		this.dataAdmin = dataAdmin;
	}
	public String getEventIdFocus() {
		return eventIdFocus;
	}
	public void setEventIdFocus(String eventIdFocus) {
		this.eventIdFocus = eventIdFocus;
	}
	public String getEventIdSelected() {
		return eventIdSelected;
	}
	public void setEventIdSelected(String eventIdSelected) {
		this.eventIdSelected = eventIdSelected;
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
	public boolean isDataOwner() {
		return dataOwner;
	}
	public void setDataOwner(boolean dataOwner) {
		this.dataOwner = dataOwner;
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
	public MDRoomSettingData getRoomSettingData() {
		return roomSettingData;
	}
	public void setRoomSettingData(MDRoomSettingData roomSettingData) {
		this.roomSettingData = roomSettingData;
	}
	public MDRoomBooking getRoomBooking() {
		return roomBooking;
	}
	public void setRoomBooking(MDRoomBooking roomBooking) {
		this.roomBooking = roomBooking;
	}

}
