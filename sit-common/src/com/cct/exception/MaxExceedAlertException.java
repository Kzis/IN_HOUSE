package com.cct.exception;

/**
 * ใช้เมื่อพบข้อมูลเป็นจำนวนมาก แล้ว alert ให้ใส่เงือนไขค้นหาเพิ่ม
 * @author sittipol.m
 *
 */
public class MaxExceedAlertException extends Exception {

	private static final long serialVersionUID = 5490363466433695748L;

	public MaxExceedAlertException() {
		super(DefaultExceptionMessage.MAX_EXCEED_ALERT);
	}

	public MaxExceedAlertException(String args0) {
		super(args0);
	}
	
	public MaxExceedAlertException(String args0, Throwable cause) {
		super(args0, cause);
	}
}
