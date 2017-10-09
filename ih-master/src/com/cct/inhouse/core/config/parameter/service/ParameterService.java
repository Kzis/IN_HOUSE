package com.cct.inhouse.core.config.parameter.service;

import com.cct.inhouse.core.config.parameter.domain.Parameter;

import util.log.DefaultLogUtil;
import util.xml.XMLUtil;

public class ParameterService {

	protected void create(String filePath) throws Exception {
		try {

		} catch (Exception e) {
			throw e;
		}
	}

	protected Parameter load(String filePath) throws Exception {
		Parameter parameter = new Parameter();
		try {
			DefaultLogUtil.INITIAL.debug("path :- " + filePath);
			parameter = (Parameter) XMLUtil.xmlToObject(filePath, parameter);
		} catch (Exception e) {
			throw e;
		}
		return parameter;
	}

}
