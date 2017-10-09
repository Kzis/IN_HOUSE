package com.cct.inhouse.bkonline.rmi.domain;

public enum BKOnlineMethod {
	// Standard
	SEARCH_ROOM_BOOKING_SELECT_ITEM("searchRoomBookingSelectItem");
	
	private String method;

	private BKOnlineMethod(String method) {
		this.method = method;
	}
	
	public String getMethord() {
		return method;
	}
	
}
