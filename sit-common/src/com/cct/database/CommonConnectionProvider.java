package com.cct.database;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import util.database.CCTConnection;
import util.log.DefaultLogUtil;
import util.type.DatabaseType.DbType;

public class CommonConnectionProvider {
	
	private static boolean INIT = false;
	private static Map<String, DataSource> dataSource = new LinkedHashMap<String, DataSource>();
	private static Map<String, Map<String, String>> schemasMap = new LinkedHashMap<String, Map<String, String>>();
	private static Map<String, String> databaseType = new LinkedHashMap<String, String>();

	public CommonConnectionProvider() throws Exception {
		
	}
	
	public CommonConnectionProvider(Database[] databaseArray) throws Exception {
		init(databaseArray);
	}
	
	private void init(Database[] databaseArray) throws Exception {
		if (INIT == false) {
			try {
				dataSource.clear();
				schemasMap.clear();
				databaseType.clear();
				for (int i = 0; i < databaseArray.length; i++) {
					Database db = databaseArray[i];

					Context context = new InitialContext();
					if (db.isJndi()) {
						context = (Context) context.lookup("java:comp/env");
					}

					/**
					DefaultLogUtil.INITIAL.debug("db.getKey(): " + db.getKey());
					**/
					DataSource ddss = (DataSource) context.lookup(db.getLookup());
					dataSource.put(db.getKey(), ddss);
					schemasMap.put(db.getKey(), db.getSchemasMap());
					databaseType.put(db.getKey(), db.getDatabaseType());
				}

				INIT = true;
			} catch (Exception e) {
				throw e;
			}
		}
	}

	/**
	 * @param conn
	 * @param lookup
	 * @return
	 * @throws Exception
	 */
	public CCTConnection getConnection(CCTConnection conn, String lookup) throws Exception {
		try {
			if (conn == null) {
				conn = new CCTConnection();
			}

			if ((conn.getConn() == null) || conn.getConn().isClosed()) {
				conn.setConn(dataSource.get(lookup).getConnection());

				/**
				DefaultLogUtil.INITIAL.debug("schemasMap: " + schemasMap.size());
				DefaultLogUtil.INITIAL.debug("databaseType: " + databaseType.size());
				**/
				conn.getSchemas().clear();
				conn.getSchemas().putAll(schemasMap.get(lookup));

				conn.setDbType(DbType.valueOf(databaseType.get(lookup).toUpperCase()));
			}
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
			throw e;
		}
		return conn;
	}
}
