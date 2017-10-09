package com.cct.core.validate.domain;

public class ValidatorField {
	private String className; // Class ที่ต้องการตรวจสอบ
	private String actionMethod; // Action ที่ต้องการตรวจสอบ
	private ValidatorWhat[] validatorWhat; // ต้องการตรวจสอบอะไร

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}

	public ValidatorWhat[] getValidatorWhat() {
		return validatorWhat;
	}

	public void setValidatorWhat(ValidatorWhat[] validatorWhat) {
		this.validatorWhat = validatorWhat;
	}


}
