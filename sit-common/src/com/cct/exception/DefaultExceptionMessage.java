package com.cct.exception;

/**
 * ค่า Default Exception Message ต่างๆ ที่จัดเตรียมไว้ให้
 * @author sittipol.m
 *
 */
public class DefaultExceptionMessage {

	public static String AUTHENTICATE = "User is not authenticated.";
	public static String AUTHORIZATION = "No permissions use task function.";
	public static String CUSTOM;
	public static String DUPLICATE = "Duplicate data.";
	public static String INPUT_VALIDATE;
	public static String MAX_EXCEED_ALERT = "Too many records were found. Please define more specific condition and try again.";
	public static String MAX_EXCEED = "Number of search result = xxx information. Too many records were found. Do you want to display information?";
	public static String MAX_EXCEED_REPORT = "Too many records were found. Please define more specific condition and try again. ";
	public static String SERVER_VALIDATE = "Process not completed.";
	
	public static String VALIDATOR_DEFAULT_MESSAGE = "Invalid Data. Please check again.";
	public static String VALIDATOR_FORMAT_MESSAGE = "xxx is invalid.";
	
	public static String NO_PERMISSIONS = "No permissions use task function.";
	public static String SESSION_EXPIRED = "Session expired.";
}
