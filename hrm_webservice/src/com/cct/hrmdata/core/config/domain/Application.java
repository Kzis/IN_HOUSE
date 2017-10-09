package com.cct.hrmdata.core.config.domain;

import java.io.Serializable;
import java.util.Locale;

import javax.xml.bind.annotation.XmlTransient;

public class Application implements Serializable {

	private static final long serialVersionUID = -8328972062264715804L;

	private String title;
	private String supportLocaleString;
	private String applicationLocaleString;
	private String datetimeLocaleString;
	private String databaseLocaleString;


	private Locale applicationLocale;
	private Locale databaseLocale;
	private Locale datetimeLocale;

	private String context;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSupportLocaleString() {
		return supportLocaleString;
	}

	public void setSupportLocaleString(String supportLocaleString) {
		this.supportLocaleString = supportLocaleString;
	}

	public String getApplicationLocaleString() {
		return applicationLocaleString;
	}

	public void setApplicationLocaleString(String applicationLocaleString) {
		this.applicationLocaleString = applicationLocaleString;
	}

	public String getDatetimeLocaleString() {
		return datetimeLocaleString;
	}

	public void setDatetimeLocaleString(String datetimeLocaleString) {
		this.datetimeLocaleString = datetimeLocaleString;
	}

	public String getDatabaseLocaleString() {
		return databaseLocaleString;
	}

	public void setDatabaseLocaleString(String databaseLocaleString) {
		this.databaseLocaleString = databaseLocaleString;
	}




	@XmlTransient
	public Locale getApplicationLocale() {
		if (applicationLocale == null) {
			applicationLocale = new Locale(getApplicationLocaleString(), getApplicationLocaleString());
		}
		return applicationLocale;
	}

	@XmlTransient
	public Locale getDatabaseLocale() {
		if (databaseLocale == null) {
			databaseLocale = new Locale(getDatabaseLocaleString().toLowerCase(), getDatabaseLocaleString().toUpperCase());
		}
		return databaseLocale;
	}

	@XmlTransient
	public Locale getDatetimeLocale() {
		if (datetimeLocale == null) {
			datetimeLocale = new Locale(getDatetimeLocaleString().toLowerCase(), getDatetimeLocaleString().toUpperCase());
		}
		return datetimeLocale;
	}


	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}


}