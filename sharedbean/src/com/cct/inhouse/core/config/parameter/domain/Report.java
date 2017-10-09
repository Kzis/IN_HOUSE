package com.cct.inhouse.core.config.parameter.domain;



public class Report extends MergeParameter {

	private static final long serialVersionUID = 1061894440878031750L;

	private String defaultFont;
	private String defaultPathFile;
	private String defaultPasswordReport;
	private String defaultPathLicense;
	private String systemPath;

	public Report clone() {
		Report cloneObj = new Report();
		
		// setter/getter
		cloneObj.merge(this);
		
		return cloneObj;
	}
	
	public String getSystemPath() {
		return systemPath;
	}

	public void setSystemPath(String systemPath) {
		this.systemPath = systemPath;
	}

	public String getDefaultFont() {
		return defaultFont;
	}

	public void setDefaultFont(String defaultFont) {
		this.defaultFont = defaultFont;
	}

	public String getDefaultPathFile() {
		return defaultPathFile;
	}

	public void setDefaultPathFile(String defaultPathFile) {
		this.defaultPathFile = defaultPathFile;
	}

	public String getDefaultPasswordReport() {
		return defaultPasswordReport;
	}

	public void setDefaultPasswordReport(String defaultPasswordReport) {
		this.defaultPasswordReport = defaultPasswordReport;
	}

	public String getDefaultPathLicense() {
		return defaultPathLicense;
	}

	public void setDefaultPathLicense(String defaultPathLicense) {
		this.defaultPathLicense = defaultPathLicense;
	}

}
