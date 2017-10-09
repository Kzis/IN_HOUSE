package com.cct.inhouse.core.config.parameter.domain;

public class ContextConfig extends MergeParameter {

	private static final long serialVersionUID = -2660874928751035607L;

	private String url;
	private String servletUrl;
	private String contextCAS;
	private String contextPortal;
	private String contextCentral;
	private String contextQAQC;
	private String contextRM;
	private String contextTO;
	private String contextBK;
	private String contextTM;
	private String contextML;
	private String contextPM;
	private String contextRC;
	
	public ContextConfig clone() {
		ContextConfig cloneObj = new ContextConfig();
		
		// setter/getter
		cloneObj.merge(this);
		
		return cloneObj;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getURLContextCAS() {
		return getUrl() + getContextCAS();
	}
	
	public String getContextCAS() {
		return contextCAS;
	}

	public void setContextCAS(String contextCAS) {
		this.contextCAS = contextCAS;
	}

	public String getURLContextPortal() {
		return getUrl() + getContextPortal();
	}
	
	public String getContextPortal() {
		return contextPortal;
	}

	public void setContextPortal(String contextPortal) {
		this.contextPortal = contextPortal;
	}

	public String getURLContextCentral() {
		return getUrl() + getContextCentral();
	}
	
	public String getContextCentral() {
		return contextCentral;
	}

	public void setContextCentral(String contextCentral) {
		this.contextCentral = contextCentral;
	}

	public String getURLContextQAQC() {
		return getUrl() + getContextQAQC();
	}
	
	public String getContextQAQC() {
		return contextQAQC;
	}

	public void setContextQAQC(String contextQAQC) {
		this.contextQAQC = contextQAQC;
	}

	public String getURLContextRM() {
		return getUrl() + getContextRM();
	}
	
	public String getContextRM() {
		return contextRM;
	}

	public void setContextRM(String contextRM) {
		this.contextRM = contextRM;
	}

	public String getURLContextTO() {
		return getUrl() + getContextTO();
	}
	
	public String getContextTO() {
		return contextTO;
	}

	public void setContextTO(String contextTO) {
		this.contextTO = contextTO;
	}

	public String getURLContextBK() {
		return getUrl() + getContextBK();
	}
	
	public String getContextBK() {
		return contextBK;
	}

	public void setContextBK(String contextBK) {
		this.contextBK = contextBK;
	}

	public String getURLContextTM() {
		return getUrl() + getContextTM();
	}
	
	public String getContextTM() {
		return contextTM;
	}

	public void setContextTM(String contextTM) {
		this.contextTM = contextTM;
	}

	public String getURLContextML() {
		return getUrl() + getContextML();
	}
	
	public String getContextML() {
		return contextML;
	}

	public void setContextML(String contextML) {
		this.contextML = contextML;
	}

	public String getURLContextPM() {
		return getUrl() + getContextPM();
	}
	
	public String getContextPM() {
		return contextPM;
	}

	public void setContextPM(String contextPM) {
		this.contextPM = contextPM;
	}

	public String getURLContextRC() {
		return getUrl() + getContextRC();
	}
	
	public String getContextRC() {
		return contextRC;
	}

	public void setContextRC(String contextRC) {
		this.contextRC = contextRC;
	}

	public String getServletUrl() {
		return servletUrl;
	}

	public void setServletUrl(String servletUrl) {
		this.servletUrl = servletUrl;
	}
	
	public String getServletUrlContextCentral() {
		return getServletUrl() + getContextCentral();
	}

}
