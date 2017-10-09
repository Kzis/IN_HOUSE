package com.cct.inhouse.core.config.parameter.service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cct.database.Database;
import com.cct.inhouse.core.config.parameter.domain.Parameter;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.log.DefaultLogUtil;
import util.type.DatabaseType.DbType;

public class ParameterManager {

	public static final String XML_PATH = System.getProperty("user.dir") + "/bkonline/WEB-INF/parameter.xml";

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

	public Parameter get(String xmlPath) throws Exception {
		Parameter parameter = null;
		try {
			parameter = service.load(xmlPath);
		} catch (Exception e) {
			throw e;
		}
		return parameter;
	}

	public void testDBConnection(Database[] dbConfig) {
		DefaultLogUtil.INITIAL.debug("DB Connection test...");
		for (Database database : dbConfig) {
			CCTConnection conn = null;
			try {
				conn = getTestDBConnection(database);
				DefaultLogUtil.INITIAL.debug(database.getKey() + " > " + database.getLookup() + " > is ok.");
			} catch (Exception e) {
				DefaultLogUtil.INITIAL.error(database.getKey() + " > " + database.getLookup() + " > is error.", e);
			} finally {
				CCTConnectionUtil.close(conn);
			}
		}
	}
	
	public CCTConnection getTestDBConnection(Database db) throws Exception {
		CCTConnection conn = null;
		Context context = new InitialContext();
		if (db.isJndi()) {
			context = (Context) context.lookup("java:comp/env");
		}
		DataSource ddss = (DataSource) context.lookup(db.getLookup());
		
		try {
			if (conn == null) {
				conn = new CCTConnection();
			}

			if ((conn.getConn() == null) || conn.getConn().isClosed()) {
				conn.setConn(ddss.getConnection());
				
				conn.getSchemas().clear();
				conn.getSchemas().putAll(db.getSchemasMap());

				DefaultLogUtil.INITIAL.debug(db.getDatabaseType().toUpperCase());
				conn.setDbType(DbType.valueOf(db.getDatabaseType().toUpperCase()));
			}
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
			throw e;
		}
		return conn;
	}
	
	public static void main(String[] args) {
		ParameterManager m = new ParameterManager();
		try {
			m.create();
			ParameterConfig.setParameter(m.get(XML_PATH));
			m.testDBConnection(ParameterConfig.getParameter().getDatabase());
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
}
