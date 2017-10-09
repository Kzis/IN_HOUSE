package com.cct.inhouse.bkonline.core.mobile.domain;

public enum MDPlatformType {

	ANDROID("AND", "ANDROID")
	, IOS("IOS", "IOS");

	private String flag;
	private String message;

	private MDPlatformType(String flag, String message) {
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
