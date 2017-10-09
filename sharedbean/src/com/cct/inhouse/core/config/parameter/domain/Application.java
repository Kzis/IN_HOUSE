package com.cct.inhouse.core.config.parameter.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlTransient;

public class Application extends MergeParameter {

	private static final long serialVersionUID = -8328972062264715804L;

	private String title;
	private String theme;
	private String systemCode;
	private String defaultSecUserIds;
	private String applicationLocaleString;
	private String databaseLocaleString;
	private String datetimeLocaleString;
	private String lppString;
	private String defaultLpp;
	private Integer maxExceed;
	private Integer maxExceedReport;
	private String mailConfigPath;

	private String supportLocaleString;
	private List<Language> supportLanguageList;
	private Locale applicationLocale;
	private Locale databaseLocale;
	private Locale datetimeLocale;
	private String[] lpp;
	
	public Application clone() {
		Application cloneObj = new Application();
		
		// setter/getter
		cloneObj.merge(this);
		
		if(this.supportLanguageList != null && !this.supportLanguageList.isEmpty()) {
			cloneObj.supportLanguageList = new ArrayList<Language>(this.supportLanguageList);
		}
		
		if(this.lpp != null) {
			cloneObj.lpp = this.lpp.clone();
		}
		
		return cloneObj;
	}
	
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
		this.supportLanguageList = null;
		this.supportLocaleString = supportLocaleString;
	}

	public String getApplicationLocaleString() {
		return applicationLocaleString;
	}

	public void setApplicationLocaleString(String applicationLocaleString) {
		this.applicationLocale = null;
		this.applicationLocaleString = applicationLocaleString;
	}

	public String getDatetimeLocaleString() {
		return datetimeLocaleString;
	}

	public void setDatetimeLocaleString(String datetimeLocaleString) {
		this.datetimeLocale = null;
		this.datetimeLocaleString = datetimeLocaleString;
	}

	public String getDatabaseLocaleString() {
		return databaseLocaleString;
	}

	public void setDatabaseLocaleString(String databaseLocaleString) {
		this.databaseLocale = null;
		this.databaseLocaleString = databaseLocaleString;
	}

	public String getLppString() {
		return lppString;
	}

	public void setLppString(String lppString) {
		this.lpp = null;
		this.lppString = lppString;
	}

	public Integer getMaxExceed() {
		return maxExceed;
	}

	public void setMaxExceed(Integer maxExceed) {
		this.maxExceed = maxExceed;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@XmlTransient
	public List<Language> getSupportLanguageList() {
		if ((supportLanguageList == null) && (getSupportLocaleString() != null)) {
			supportLanguageList = new ArrayList<Language>();
			String[] supportLocaleString = null;
			if (getSupportLocaleString().indexOf(",") > -1) {
				supportLocaleString = getSupportLocaleString().split(",");
			} else {
				supportLocaleString = new String[1];
				supportLocaleString[0] = getSupportLocaleString();
			}

			for (int i = 0; i < supportLocaleString.length; i++) {
				Language language = new Language();
				language.setLocale(new Locale(supportLocaleString[i].toLowerCase(), supportLocaleString[i].toUpperCase()));
				language.setId(String.valueOf(i + 1));
				language.setCode(language.getLocale().getLanguage());
				language.setDesc(language.getLocale().getDisplayLanguage());
				supportLanguageList.add(language);
			}
		}
		return supportLanguageList;
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

	@XmlTransient
	public String[] getLpp() {
		if (lpp == null) {
			lpp = getLppString().split(",");
		}
		return lpp;
	}

	public void setSupportLanguageList(List<Language> supportLanguageList) {
		this.supportLanguageList = supportLanguageList;
	}

	public Integer getMaxExceedReport() {
		return maxExceedReport;
	}

	public void setMaxExceedReport(Integer maxExceedReport) {
		this.maxExceedReport = maxExceedReport;
	}

	public String getMailConfigPath() {
		return mailConfigPath;
	}

	public void setMailConfigPath(String mailConfigPath) {
		this.mailConfigPath = mailConfigPath;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getDefaultSecUserIds() {
		return defaultSecUserIds;
	}

	public void setDefaultSecUserIds(String defaultSecUserIds) {
		this.defaultSecUserIds = defaultSecUserIds;
	}

	public String getDefaultLpp() {
		return defaultLpp;
	}

	public void setDefaultLpp(String defaultLpp) {
		this.defaultLpp = defaultLpp;
	}
}