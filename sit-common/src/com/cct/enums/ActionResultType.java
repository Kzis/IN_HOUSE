package com.cct.enums;

/**
 * @Description: Class enum for return result
 */
public enum ActionResultType {
	CHAIN("CHAIN")
	, BASIC("BASIC")
	, AJAX("AJAX");

	private String type;

	private ActionResultType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
