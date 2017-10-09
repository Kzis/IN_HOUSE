package com.cct.exception;

/**
 * ใช้สำหรับยืนยันตัวตน
 * @author sittipol.m
 *
 */
public class AuthenticateException extends Exception {

	private static final long serialVersionUID = -7351163217961819178L;

	public AuthenticateException() {
		super(DefaultExceptionMessage.AUTHENTICATE);
	}

	public AuthenticateException(String args0) {
		super(args0);
	}
	
	public AuthenticateException(String args0, Throwable e) {
		super(args0, e);
	}

}