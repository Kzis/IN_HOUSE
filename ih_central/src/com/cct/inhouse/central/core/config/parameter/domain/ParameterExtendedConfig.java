package com.cct.inhouse.central.core.config.parameter.domain;

public class ParameterExtendedConfig {

	private static ParameterExtended parameterExtended;

	public static ParameterExtended getParameterExtended() {
		return parameterExtended;
	}

	public static void setParameterExtended(ParameterExtended parameterExtended) {
		ParameterExtendedConfig.parameterExtended = parameterExtended;
	}
}
