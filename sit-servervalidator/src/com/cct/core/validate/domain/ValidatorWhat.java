package com.cct.core.validate.domain;

public class ValidatorWhat {
	private String inputName; // Input name ที่ผูกกับ Model
	private String inputNameFormat; // Input name ที่ผูกกับ Model
	private ValidatorCondition[] condition; // เงือนไขที่ใช้ในการ Validate

	private ValidatorWhat[] validatorCollection; // ต้องการตรวจสอบอะไร กรณีเป็น List
	
	public String getInputName() {
		return inputName;
	}
	
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public ValidatorCondition[] getCondition() {
		return condition;
	}
	public void setCondition(ValidatorCondition[] condition) {
		this.condition = condition;
	}

	public String getInputNameFormat() {
		return inputNameFormat;
	}

	public void setInputNameFormat(String inputNameFormat) {
		this.inputNameFormat = inputNameFormat;
	}

	public ValidatorWhat[] getValidatorCollection() {
		return validatorCollection;
	}

	public void setValidatorCollection(ValidatorWhat[] validatorCollection) {
		this.validatorCollection = validatorCollection;
	}

}
