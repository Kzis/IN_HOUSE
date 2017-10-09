package com.cct.getworkonsite.core.config.parameter.domain;

import java.io.Serializable;

import resources.sql.getworkonsite.GetWorkOnSiteSQL;

public enum SQLPath implements Serializable {
	/**
	 * @Description: Class enum for data base lookup
	 */
	GET_WORK_ONSITE(GetWorkOnSiteSQL.class, "resources/sql/getworkonsite/GetWorkOnSite.sql"),
	;

	private String path;
	private Class<?> className;

	private SQLPath(Class<?> className, String path) {
		this.path = path;
		this.className = className;
	}

	public String getPath() {
		return path;
	}

	public Class<?> getClassName() {
		return className;
	}
}