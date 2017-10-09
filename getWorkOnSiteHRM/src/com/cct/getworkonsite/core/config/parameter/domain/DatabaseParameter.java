package com.cct.getworkonsite.core.config.parameter.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatabaseParameter implements Serializable {

	private static final long serialVersionUID = 52503395986283112L;

	// @XmlAttribute
	// @XmlElement
	private Database[] database;

	public Database[] getDatabase() {
		return database;
	}

	@XmlElement
	public void setDatabase(Database[] database) {
		this.database = database;
	}
}
