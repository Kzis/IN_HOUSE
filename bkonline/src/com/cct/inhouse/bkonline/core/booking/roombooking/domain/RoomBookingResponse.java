package com.cct.inhouse.bkonline.core.booking.roombooking.domain;

import java.util.ArrayList;
import java.util.List;

public class RoomBookingResponse extends RoomBookingMessage {

	private List<EmptyRoom> listEmptyRoom = new ArrayList<EmptyRoom>();

	public List<EmptyRoom> getListEmptyRoom() {
		return listEmptyRoom;
	}

	public void setListEmptyRoom(List<EmptyRoom> listEmptyRoom) {
		this.listEmptyRoom = listEmptyRoom;
	}
	
}
