package com.cct.inhouse.timeoffset.core.config.parameter.domain;

import java.io.Serializable;

public class TimeOffsetConfig implements Serializable {

	private static final long serialVersionUID = -6757507919955891652L;
	
	private String systemCode;
	private String systemNameTh;
	private String systemNameEn;
	
	private WebServiceConfig webServiceConf;

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSystemNameTh() {
		return systemNameTh;
	}

	public void setSystemNameTh(String systemNameTh) {
		this.systemNameTh = systemNameTh;
	}

	public String getSystemNameEn() {
		return systemNameEn;
	}

	public void setSystemNameEn(String systemNameEn) {
		this.systemNameEn = systemNameEn;
	}

	public WebServiceConfig getWebServiceConf() {
		return webServiceConf;
	}

	public void setWebServiceConf(WebServiceConfig webServiceConf) {
		this.webServiceConf = webServiceConf;
	}
	
}
