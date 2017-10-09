package com.cct.enums;

public enum ActionMaxExceedType {
	ALERT("A"), CONFIRM("C"), NORMAL("N");

	private String type;

	private ActionMaxExceedType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
