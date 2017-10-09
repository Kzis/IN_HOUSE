package com.cct.getworkonsite.core.config.parameter.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Parameter implements Serializable {

	private static final long serialVersionUID = -578325849007499211L;

	// @XmlTransient
	// @XmlAttribute
	// @XmlElement
	private Application application;
	private DateFormat dateFormat;
	private BgConfig bgConfig;

	public Application getApplication() {
		return application;
	}

	@XmlElement
	public void setApplication(Application application) {
		this.application = application;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	@XmlElement
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public BgConfig getBgConfig() {
		return bgConfig;
	}

	@XmlElement
	public void setBgConfig(BgConfig bgConfig) {
		this.bgConfig = bgConfig;
	}

}
