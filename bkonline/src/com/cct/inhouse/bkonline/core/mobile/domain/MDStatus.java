package com.cct.inhouse.bkonline.core.mobile.domain;

import util.string.StringDelimeter;

public class MDStatus extends Exception {

	private static final long serialVersionUID = -437911521198830077L;

	private String exceptionFlag = StringDelimeter.EMPTY.getValue();
	private String exceptionDesc;
	private String message;

	public String getExceptionFlag() {
		return exceptionFlag;
	}

	public void setExceptionFlag(String exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExceptionDesc() {
		return exceptionDesc;
	}

	public void setExceptionDesc(String exceptionDesc) {
		this.exceptionDesc = exceptionDesc;
	}
}
