package com.cct.core.validate.domain;

public enum ConditionType {

	D("D"), M("M"), Y("Y")
	;

	private String type;

	private ConditionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}	
}
