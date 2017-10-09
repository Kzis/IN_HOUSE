package com.cct.enums;

public enum ContentType {
	
	APPLICATION_JSON("application/json")
	;

	private String value;

	private ContentType(String value) {
		this.value = value;
	}
		
	public String getValue() {
		return value;
	}
}
