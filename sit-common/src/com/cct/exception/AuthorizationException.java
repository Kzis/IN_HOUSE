package com.cct.exception;

/**
 * ใช้ยืนยันสิทธิ์การเข้าใช้ใช้งาน
 * @author sittipol.m
 *
 */
public class AuthorizationException extends Exception {

	private static final long serialVersionUID = -6835117262742690422L;

	public AuthorizationException() {
		super(DefaultExceptionMessage.AUTHORIZATION);
	}

	public AuthorizationException(String args0) {
		super(args0);
	}
	
	public AuthorizationException(String args0, Throwable e) {
		super(args0, e);
	}

}
