package com.cct.hrmdata.core.config.service;

import util.log.LogUtil;
import util.xml.XMLUtil;

import com.cct.hrmdata.core.config.domain.Parameter;

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
			LogUtil.INITIAL.debug("path :- " + filePath);
			parameter = (Parameter) XMLUtil.xmlToObject(filePath, parameter);


		} catch (Exception e) {
			throw e;
		}
		return parameter;
	}

}
