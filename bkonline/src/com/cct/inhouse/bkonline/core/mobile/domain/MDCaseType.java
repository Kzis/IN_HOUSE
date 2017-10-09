package com.cct.inhouse.bkonline.core.mobile.domain;

public enum MDCaseType {

	NEW_LOGIN("NL", "New Login"),
	TODO("TD", "TODO"),
	CLEAR("CL", "Clear"),
	APPROVE("AP", "Approve"),
	CHECK_OUT("CO", "Check Out"),
	CHECK_OUT_OVER_LIMIT("COL", "Check Out Over Limit"),
	CANCEL("CC", "Cancel"),
	EVENT("EV", "Event"),
	ROOM("RM", "Room");

	private String flag;
	private String message;

	private MDCaseType(String flag, String message) {
		this.flag = flag;
		this.message = message;
	}

	public String getFlag() {
		return flag;
	}

	public String getMessage() {
		return message;
	}

}
