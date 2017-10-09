package com.cct.core.validate.domain;

public class ValidatorCondition {
	private String validateType; // ประเภทการ Validate
	private String message; // ข้อความตอบกลับเมื่อ Validate ไม่ผ่าน
	private Integer maxLength;
	private Integer minLength;
	private String format;
	private boolean userLocale;
	
	private String startDate;
	private String endDate;
	private Integer rangeLimit;
	
	private String validateTypeCondition;
	private String dbLookup;
	private String dbFormat;
	
	public String getValidateType() {
		return validateType;
	}

	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isUserLocale() {
		return userLocale;
	}

	public void setUserLocale(boolean userLocale) {
		this.userLocale = userLocale;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getRangeLimit() {
		return rangeLimit;
	}

	public void setRangeLimit(Integer rangeLimit) {
		this.rangeLimit = rangeLimit;
	}

	public String getDbLookup() {
		return dbLookup;
	}

	public void setDbLookup(String dbLookup) {
		this.dbLookup = dbLookup;
	}

	public String getDbFormat() {
		return dbFormat;
	}

	public void setDbFormat(String dbFormat) {
		this.dbFormat = dbFormat;
	}

	public String getValidateTypeCondition() {
		return validateTypeCondition;
	}

	public void setValidateTypeCondition(String validateTypeCondition) {
		this.validateTypeCondition = validateTypeCondition;
	}
}
