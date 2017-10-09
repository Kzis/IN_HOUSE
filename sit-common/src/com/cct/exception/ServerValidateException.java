package com.cct.exception;

/**
 * ใช้เมื่อมีการตรวจสอบข้อมูลฝั่ง Server แล้วไม่ผ่าน
 * @author sittipol.m
 *
 */
public class ServerValidateException extends Exception {

	private static final long serialVersionUID = 8063417434820899233L;

	public ServerValidateException() {
		super(DefaultExceptionMessage.SERVER_VALIDATE);
	}

	public ServerValidateException(String args0) {
		super(args0);
	}
	
	public ServerValidateException(String args0, Throwable cause) {
		super(args0, cause);
	}
}