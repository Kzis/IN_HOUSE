package com.cct.getworkonsite.core.config.parameter.service;

import util.log.LogUtil;
import util.xml.XMLUtil;

import com.cct.getworkonsite.core.config.parameter.domain.Database;
import com.cct.getworkonsite.core.config.parameter.domain.DatabaseParameter;
import com.cct.getworkonsite.core.config.parameter.domain.Parameter;

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
			LogUtil.INITIAL.debug("Parameter path :- " + filePath);
			parameter = (Parameter) XMLUtil.xmlToObject(filePath, parameter);

		} catch (Exception e) {
			throw e;
		}
		return parameter;
	}

	protected DatabaseParameter loadDb(String filePath) throws Exception {
		DatabaseParameter parameter = new DatabaseParameter();
		try {
			LogUtil.INITIAL.debug("DbParameter path :- " + filePath);

			parameter = (DatabaseParameter) XMLUtil.xmlToObject(filePath, parameter);

			LogUtil.INITIAL.debug("Database size :- " + parameter.getDatabase().length);
			for (Database db : parameter.getDatabase()) {
				LogUtil.INITIAL.debug("URL :- " + db.getUrl());
			}
		} catch (Exception e) {
			throw e;
		}
		return parameter;
	}
}
