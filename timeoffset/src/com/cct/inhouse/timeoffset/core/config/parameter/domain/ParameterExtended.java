package com.cct.inhouse.timeoffset.core.config.parameter.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParameterExtended implements Serializable {

	private static final long serialVersionUID = 344350551974878506L;

	// @XmlTransient
	// @XmlAttribute
	// @XmlElement
	private TimeOffsetConfig timeOffsetConf;

	public TimeOffsetConfig getTimeOffsetConf() {
		return timeOffsetConf;
	}

	@XmlElement
	public void setTimeOffsetConf(TimeOffsetConfig timeOffsetConf) {
		this.timeOffsetConf = timeOffsetConf;
	}

}
