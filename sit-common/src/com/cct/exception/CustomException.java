package com.cct.exception;

/**
 * กำหนดเอง
 * @author sittipol.m
 *
 */
public class CustomException extends Exception {
	
	private static final long serialVersionUID = 2459926760687599073L;

	public CustomException() {
		super(DefaultExceptionMessage.CUSTOM);
	}

	public CustomException(String args0) {
		super(args0);
	}

	public CustomException(String args0, Throwable e) {
		super(args0, e);
	}
}
