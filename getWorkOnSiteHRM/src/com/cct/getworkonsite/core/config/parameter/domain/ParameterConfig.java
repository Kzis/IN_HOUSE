package com.cct.getworkonsite.core.config.parameter.domain;

import java.io.Serializable;

public class ParameterConfig implements Serializable {

	private static final long serialVersionUID = -2187894195556282622L;

	private static Parameter parameter;
	private static DatabaseParameter dbParameter;

	public static Parameter getParameter() {
		return parameter;
	}

	public static void setParameter(Parameter parameter) {
		ParameterConfig.parameter = parameter;
	}

	public static DatabaseParameter getDbParameter() {
		return dbParameter;
	}

	public static void setDbParameter(DatabaseParameter dbParameter) {
		ParameterConfig.dbParameter = dbParameter;
	}
}
