package com.cct.inhouse.timeoffset.core.config.parameter.service;

import com.cct.inhouse.timeoffset.core.config.parameter.domain.ParameterExtended;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.ParameterExtendedConfig;

public class ParameterExtendedManager {

	public static final String XML_PATH = System.getProperty("user.dir") + "/WebContent/WEB-INF/parameter-extended.xml";

	private ParameterExtendedService service = null;

	public ParameterExtendedManager() {
		this.service = new ParameterExtendedService();
	}

	public void load() throws Exception {
		try {
			load(XML_PATH);
		} catch (Exception e) {
			throw e;
		}
	}

	public void load(String xmlPath) throws Exception {

		try {
			ParameterExtended parameter = service.load(xmlPath);
			ParameterExtendedConfig.setParameterExtended(parameter);
		} catch (Exception e) {
			throw e;
		}
	}

}
