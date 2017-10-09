package com.cct.inhouse.bkonline.core.mobile.domain;

import java.util.ArrayList;
import java.util.List;

public class MobileData {

	private String caseType;
	private MDUser mdUser;
	private MDStatus mdStatus = new MDStatus();
	private MDNotification mdNotification;
	
	
	private MDRoomBookingData mdRoomSelect;
	private MDRoomBookingData mdEventSelect;
	
	private List<MDRoomSettingData> mdRoom = new ArrayList<MDRoomSettingData>();
	private List<MDRoomBookingData> mdEvent = new ArrayList<MDRoomBookingData>();

	public List<MDRoomSettingData> getMdRoom() {
		return mdRoom;
	}

	public void setMdRoom(List<MDRoomSettingData> mdRoom) {
		this.mdRoom = mdRoom;
	}

	public List<MDRoomBookingData> getMdEvent() {
		return mdEvent;
	}

	public void setMdEvent(List<MDRoomBookingData> mdEvent) {
		this.mdEvent = mdEvent;
	}

	public MDStatus getMdStatus() {
		return mdStatus;
	}

	public void setMdStatus(MDStatus mdStatus) {
		this.mdStatus = mdStatus;
	}

	public MDUser getMdUser() {
		return mdUser;
	}

	public void setMdUser(MDUser mdUser) {
		this.mdUser = mdUser;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public MDNotification getMdNotification() {
		return mdNotification;
	}

	public void setMdNotification(MDNotification mdNotification) {
		this.mdNotification = mdNotification;
	}

	public MDRoomBookingData getMdRoomSelect() {
		return mdRoomSelect;
	}

	public void setMdRoomSelect(MDRoomBookingData mdRoomSelect) {
		this.mdRoomSelect = mdRoomSelect;
	}

	public MDRoomBookingData getMdEventSelect() {
		return mdEventSelect;
	}

	public void setMdEventSelect(MDRoomBookingData mdEventSelect) {
		this.mdEventSelect = mdEventSelect;
	}


}
