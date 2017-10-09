package com.cct.inhouse.central.core.config.parameter.domain;

import java.io.Serializable;

public class Register implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4261550484962611937L;
	
	private String registKey;
	private String registName;
	private String registUrl;
	private String registStatus;
	private String active;

	public String getRegistKey() {
		return registKey;
	}

	public void setRegistKey(String registKey) {
		this.registKey = registKey;
	}

	public String getRegistName() {
		return registName;
	}

	public void setRegistName(String registName) {
		this.registName = registName;
	}

	public String getRegistUrl() {
		return registUrl;
	}

	public void setRegistUrl(String registUrl) {
		this.registUrl = registUrl;
	}

	public String getRegistStatus() {
		return registStatus;
	}

	public void setRegistStatus(String registStatus) {
		this.registStatus = registStatus;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
