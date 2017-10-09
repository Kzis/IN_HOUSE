package com.cct.exception;

/**
 * ใช้เมื่อพบการซ้ำของข้อมูล
 * @author sittipol.m
 *
 */
public class DuplicateException extends Exception {

	private static final long serialVersionUID = -942141140348828205L;

	public DuplicateException() {
		super(DefaultExceptionMessage.DUPLICATE);
	}
	
	public DuplicateException(String args0) {
		super(args0);
	}
	
	public DuplicateException(String args0, Throwable cause) {
		super(args0, cause);
	}
}
