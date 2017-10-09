package com.cct.getworkonsite.core.config.parameter.domain;

import java.io.Serializable;
import java.util.Locale;

import javax.xml.bind.annotation.XmlTransient;

public class Application implements Serializable {

	private static final long serialVersionUID = -8328972062264715804L;

	private String databaseConfigPath;
	private String applicationLocaleString;
	private String filename;

	private Locale applicationLocale;

	public String getDatabaseConfigPath() {
		return databaseConfigPath;
	}

	public void setDatabaseConfigPath(String databaseConfigPath) {
		this.databaseConfigPath = databaseConfigPath;
	}

	public String getApplicationLocaleString() {
		return applicationLocaleString;
	}

	public void setApplicationLocaleString(String applicationLocaleString) {
		this.applicationLocaleString = applicationLocaleString;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@XmlTransient
	public Locale getApplicationLocale() {
		if (applicationLocale == null) {
			applicationLocale = new Locale(getApplicationLocaleString(), getApplicationLocaleString());
		}
		return applicationLocale;
	}

}