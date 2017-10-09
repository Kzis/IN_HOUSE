package com.cct.inhouse.timeoffset.core.config.parameter.service;

import util.xml.XMLUtil;

import com.cct.inhouse.timeoffset.core.config.parameter.domain.ParameterExtended;
import com.cct.inhouse.timeoffset.util.log.LogUtil;

public class ParameterExtendedService {

	protected ParameterExtended load(String filePath) throws Exception {
		ParameterExtended parameter = new ParameterExtended();
		try {
			LogUtil.TO_PARAM.debug("path :- " + filePath);
			parameter = (ParameterExtended) XMLUtil.xmlToObject(filePath, parameter);

			LogUtil.TO_PARAM.debug("System Code :- " + parameter.getTimeOffsetConf().getSystemCode());
			LogUtil.TO_PARAM.debug("System Name EN :- " + parameter.getTimeOffsetConf().getSystemNameEn());
			LogUtil.TO_PARAM.debug("System Name TH :- " + parameter.getTimeOffsetConf().getSystemNameTh());

		} catch (Exception e) {
			throw e;
		}

		return parameter;
	}

}
