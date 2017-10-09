package com.cct.inhouse.bkonline.core.mobile.domain;

public enum MDExceptionFlag {

	INTERNAL_SERVER_ERROR("SE", "Internal Server Error")
	, LOGIN_FAIL("LF", "Login fail");

	private String flag;
	private String message;

	private MDExceptionFlag(String flag, String message) {
		this.flag = flag;
		this.message = message;
	}

	public String getFlag() {
		return flag;
	}

	public String getMessage() {
		return message;
	}

}
