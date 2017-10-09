package com.cct.inhouse.bkonline.core.config.parameter.service;

import com.cct.inhouse.bkonline.core.config.parameter.domain.ParameterExtended;

import util.log.DefaultLogUtil;
import util.xml.XMLUtil;

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
