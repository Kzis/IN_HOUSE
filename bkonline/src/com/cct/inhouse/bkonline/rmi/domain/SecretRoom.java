package com.cct.inhouse.bkonline.rmi.domain;

import java.io.Serializable;

public class SecretRoom implements Serializable {

	private static final long serialVersionUID = 4973655077628175423L;
	
	private String roomName;
	private String subscriber;
	private String meetingTime;

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}

	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

}
