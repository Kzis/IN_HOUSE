package com.cct.getworkonsite.core.config.parameter.service;

import org.apache.log4j.PropertyConfigurator;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;

import com.cct.domain.GlobalVariable;
import com.cct.getworkonsite.core.config.parameter.domain.Database;
import com.cct.getworkonsite.core.config.parameter.domain.DatabaseParameter;
import com.cct.getworkonsite.core.config.parameter.domain.Parameter;
import com.cct.getworkonsite.core.config.parameter.domain.ParameterConfig;

public class ParameterManager {

	public static final String XML_PATH = System.getProperty("user.dir") + "/" + GlobalVariable.CONFIG_PARAMETER_FILE;

	private ParameterService service = null;

	public ParameterManager() {
		this.service = new ParameterService();
	}

	public void create() throws Exception {
		try {
			service.create(XML_PATH);
		} catch (Exception e) {
			throw e;
		}
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
			Parameter parameter = service.load(xmlPath);
			DatabaseParameter dbParameter = service.loadDb(parameter.getApplication().getDatabaseConfigPath());

			ParameterConfig.setParameter(parameter);
			ParameterConfig.setDbParameter(dbParameter);

			testDBConnection(ParameterConfig.getDbParameter().getDatabase());
		} catch (Exception e) {
			throw e;
		}
	}

	public void testDBConnection(Database[] dbConfig) {
		LogUtil.INITIAL.debug("DB Connection test...");
		for (Database database : dbConfig) {
			CCTConnection conn = null;
			try {
				conn = new CCTConnectionProvider().getConnection(conn, database.getIndex());
				LogUtil.INITIAL.debug(database.getIndex() + " > " + database.getUrl() + " > " + database.getSchemas() + " > is ok.");
			} catch (Exception e) {
				LogUtil.INITIAL.error(database.getIndex() + " > " + database.getUrl() + " > " + database.getSchemas() + " > is error.");
				LogUtil.INITIAL.error("", e);
			} finally {
				CCTConnectionUtil.close(conn);
			}
		}
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure(GlobalVariable.CONFIG_LOG4J_FILE);
		ParameterManager m = new ParameterManager();
		try {
			m.create();
			m.load();
			m.testDBConnection(ParameterConfig.getDbParameter().getDatabase());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
