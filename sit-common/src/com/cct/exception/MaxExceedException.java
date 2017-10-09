package com.cct.exception;

/**
 * ใช้เมื่อพบข้อมูลเป็นจำนวนมาก แล้ว Confirm ต้องการค้นหาไหมต่อไหม?
 * @author sittipol.m
 *
 */
public class MaxExceedException extends Exception {

	private static final long serialVersionUID = -3304899183110993591L;

	public MaxExceedException() {
		super(DefaultExceptionMessage.MAX_EXCEED);
	}

	public MaxExceedException(String args0) {
		super(args0);
	}
	
	public MaxExceedException(String args0, Throwable cause) {
		super(args0, cause);
	}
}
