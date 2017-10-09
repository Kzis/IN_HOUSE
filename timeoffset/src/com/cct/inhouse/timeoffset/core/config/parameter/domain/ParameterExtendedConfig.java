package com.cct.inhouse.timeoffset.core.config.parameter.domain;

import java.io.Serializable;

public class ParameterExtendedConfig implements Serializable {

	private static final long serialVersionUID = -5207924021025141086L;

	private static ParameterExtended parameterExtended;

	public static ParameterExtended getParameterExtended() {
		return parameterExtended;
	}

	public static void setParameterExtended(ParameterExtended parameterExtended) {
		ParameterExtendedConfig.parameterExtended = parameterExtended;
	}

}
