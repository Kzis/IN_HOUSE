package com.cct.enums;

public enum LanguageSessionAttribute {
	
	DEFAULT("LANGUAGE_SESSION");
	
	private String value;

	private LanguageSessionAttribute(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
