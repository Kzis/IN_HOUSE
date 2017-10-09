package com.cct.enums;

/**
 * @Description: Class enum for message type
 */
public enum ActionMessageType {
	ERROR("E")
	, SUCCESS("S")
	, WARING("W")
	, CONFIRM("C")
	, OTHER("O");

	private String type;

	private ActionMessageType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
