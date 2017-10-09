package com.cct.core.validate.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidatorConfig {
	
	private boolean devMode;
	private String databaseLocale;
	
	private ValidatorField[] validator;

	public ValidatorField[] getValidator() {
		return validator;
	}

	public void setValidator(ValidatorField[] validator) {
		this.validator = validator;
	}

	public boolean isDevMode() {
		return devMode;
	}

	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}

	public String getDatabaseLocale() {
		return databaseLocale;
	}

	public void setDatabaseLocale(String databaseLocale) {
		this.databaseLocale = databaseLocale;
	} 
}
