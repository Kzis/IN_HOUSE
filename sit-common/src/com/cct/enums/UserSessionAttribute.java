package com.cct.enums;

public enum UserSessionAttribute {
	
	DEFAULT("USER_SESSION");
	
	private String value;

	private UserSessionAttribute(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
