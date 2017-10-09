package com.cct.exception;


public class InputValidateException extends Exception {

	private static final long serialVersionUID = -6064627745966146445L;

	public InputValidateException() {
		super(DefaultExceptionMessage.INPUT_VALIDATE);
	}
	
	public InputValidateException(String args0) {
		super(args0);
	}
	
	/**
	 * 
	 * @param args0  : page ที่ต้องการให้ set กลับ เนื่องจาก ผ่านการ manage มาแล้ว page page จะเปลี่ยน
	 * @param cause : คือค่าที่ต้องการให้โชว์ messge หน้าจอ :ex .วันที่ไม่ผ่าน : วันที่มากกว่าค่าที่กำหนด  
	 * แต่ละ message ขั้นด้วย  :
	 */
	public InputValidateException(String args0, Throwable cause) {
		super(args0, cause);
	}
}
