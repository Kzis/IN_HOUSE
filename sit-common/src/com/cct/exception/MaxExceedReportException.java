package com.cct.exception;

/**
 * ใช้เมื่อพบข้อมูลเป็นจำนวนมาก แล้ว alert ให้ใส่เงือนไขค้นหาเพิ่ม
 * @author sittipol.m
 *
 */
public class MaxExceedReportException extends Exception {

	private static final long serialVersionUID = -2820921874862308334L;

	public MaxExceedReportException() {
		super(DefaultExceptionMessage.MAX_EXCEED_REPORT);
	}

	public MaxExceedReportException(String args0) {
		super(args0);
	}
	
	public MaxExceedReportException(String args0, Throwable cause) {
		super(args0, cause);
	}
}
