package com.cct.getworkonsite.core.config.parameter.domain;

import java.io.Serializable;

public class BgConfig implements Serializable {

	private static final long serialVersionUID = -8328972062264715804L;

	private String userProcess;
	private String urlWebService;

	public String getUserProcess() {
		return userProcess;
	}

	public void setUserProcess(String userProcess) {
		this.userProcess = userProcess;
	}

	public String getUrlWebService() {
		return urlWebService;
	}

	public void setUrlWebService(String urlWebService) {
		this.urlWebService = urlWebService;
	}

}