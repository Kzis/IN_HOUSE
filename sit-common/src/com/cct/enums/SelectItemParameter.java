package com.cct.enums;

public enum SelectItemParameter {
	
	TERM("term"), LIMIT("limit"), JSON_REQUEST("json");
	
	private String value;

	private SelectItemParameter(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
