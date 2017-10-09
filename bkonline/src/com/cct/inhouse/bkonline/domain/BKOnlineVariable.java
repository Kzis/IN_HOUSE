package com.cct.inhouse.bkonline.domain;

public class BKOnlineVariable {
	
	public enum Mode {
		DAY("day"), WEEK("week"), MONTH("month");

		private String value;

		private Mode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum Step {
		PREVIOUS("previous"), NEXT("next");

		private String value;

		private Step(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum BookingStatus {
		WAIT("W", "Waiting")
		, APPROVE("A", "Approve")
		, UNSTABLE("U", "Unstable")
		, CHECK_IN("CI", "Check In")
		, CHECK_OUT("CO", "Check Out")
		, CANCELED("C", "Canceled")
		, NOT_CHECK_IN("NI", "Not Check In")
		, NOT_CHECK_OUT("NO", "Not Check Out")
		;

		private String code;
		private String name;

		private BookingStatus(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}
		
		public String getName() {
			return name;
		}
	} 
	
	public enum SessionParameter {
		BOOKING_EVEN_ID("eventId")
		, BOOKING_CURRENT_DAY("currentDay")
		,LINE_USER_ID("userId")
		;

		private String value;

		private SessionParameter(String value) {
			this.value = value;
		}

		
		public String getValue() {
			return value;
		}
	}
}
