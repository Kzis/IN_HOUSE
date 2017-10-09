package com.cct.enums;

public enum Log4jFile {
	
	DEFAULT_FILENAME("log4j.properties");
	
	private String value;

	private Log4jFile(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
