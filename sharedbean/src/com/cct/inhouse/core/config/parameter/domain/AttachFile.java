package com.cct.inhouse.core.config.parameter.domain;

public class AttachFile extends MergeParameter {

	private static final long serialVersionUID = 8940963552725725017L;

	private String imageDefaultPath;
	private String defaultPath;
	private String defaultTempPath;
	private String systemPath;

	public AttachFile clone() {
		AttachFile cloneObj = new AttachFile();
		
		// setter/getter
		cloneObj.merge(this);
		
		return cloneObj;
	}
	
	public String getImageDefaultPath() {
		return imageDefaultPath;
	}

	public void setImageDefaultPath(String imageDefaultPath) {
		this.imageDefaultPath = imageDefaultPath;
	}

	public String getDefaultPath() {
		return defaultPath;
	}

	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}

	public String getDefaultTempPath() {
		return defaultTempPath;
	}

	public void setDefaultTempPath(String defaultTempPath) {
		this.defaultTempPath = defaultTempPath;
	}

	public String getSystemPath() {
		return systemPath;
	}

	public void setSystemPath(String systemPath) {
		this.systemPath = systemPath;
	}

}
