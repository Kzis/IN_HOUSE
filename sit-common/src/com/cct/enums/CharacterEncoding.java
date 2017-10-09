package com.cct.enums;

public enum CharacterEncoding {

	UTF8("UTF-8"), TIS620("TIS-620")
	;

	private String value;

	private CharacterEncoding(String value) {
		this.value = value;
	}
		
	public String getValue() {
		return value;
	}
}
