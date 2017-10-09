package com.cct.inhouse.central.core.config.parameter.service;

import util.log.DefaultLogUtil;
import util.xml.XMLUtil;

import com.cct.inhouse.central.core.config.parameter.domain.ParameterExtended;

public class ParameterExtendedService {

	protected ParameterExtended load(String filePath) throws Exception {
		ParameterExtended parameter = new ParameterExtended();
		try {
			DefaultLogUtil.INITIAL.debug("path :- " + filePath);
			parameter = (ParameterExtended) XMLUtil.xmlToObject(filePath, parameter);
		} catch (Exception e) {
			throw e;
		}
		return parameter;
	}
	
}
