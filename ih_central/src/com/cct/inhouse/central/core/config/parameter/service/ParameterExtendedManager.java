package com.cct.inhouse.central.core.config.parameter.service;

import com.cct.inhouse.central.core.config.parameter.domain.ParameterExtended;

public class ParameterExtendedManager {

	ParameterExtendedService service = new ParameterExtendedService();
	
	public ParameterExtended get(String xmlPath) throws Exception {
		ParameterExtended parameter = null;
		try {
			parameter = service.load(xmlPath);
		} catch (Exception e) {
			throw e;
		}
		return parameter;
	}
}
