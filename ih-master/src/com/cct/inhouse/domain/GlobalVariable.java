package com.cct.inhouse.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GlobalVariable implements Serializable {
	private static final long serialVersionUID = 7226578754399777637L;

	public static final Map<String, String> MAP_OF_ACTION_NAME_FOR_SKIP_AUTHENTICATE = new HashMap<String, String>();
	static {
		MAP_OF_ACTION_NAME_FOR_SKIP_AUTHENTICATE.put("initPermission", "initPermission");
		MAP_OF_ACTION_NAME_FOR_SKIP_AUTHENTICATE.put("gotoServiceListPermission", "gotoServiceListPermission");
		MAP_OF_ACTION_NAME_FOR_SKIP_AUTHENTICATE.put("loginUser", "loginUser");
		MAP_OF_ACTION_NAME_FOR_SKIP_AUTHENTICATE.put("savelineProfileUser", "savelineProfileUser");
		
	}

	public enum TimeDefault {
		START_HHMM("00:00")
		, END_HHMM("23:59")
		, START_HHMMSS("00:00:00")
		, END_HHMMSS("23:59:59")
		,START_SS("00")
		,END_SS("59");

		private String value;

		private TimeDefault(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum DeleteStatus {
		DELETED("Y"), USE("N");
		
		private String value;

		private DeleteStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public static final String TRUE_STATUS = "true";
	public static final String FALSE_STATUS = "false";
	
	public static final String START_TIME = "00:00";
	public static final String END_TIME = "23:59";	
	
	public static final String FLAG_ACTIVE = "Y";
	public static final String FLAG_INACTIVE = "N";
	
	public static final String DATE_FORMAT_INSERT_YYYY_MM_DD = "YYYY-MM-DD";
	public static final String DATE_FORMAT_INSERT_YYYY_MM_DD_HH_MM_SS = "YYYY-MM-DD HH:mm:ss";
	public static final String DATE_FORMAT_DISPLAY_DD_MM_YYYY = "DD/MM/YYYY";
	public static final String DATE_FORMAT_DISPLAY_DD_MM_YYYY_HH_MM_SS = "DD/MM/YYYY  HH:mm:ss";
	
	public static final String MONITOR = "monitor";			// ใช้สำหรับ ActionReturnType ของ InitialInterceptor
	
}