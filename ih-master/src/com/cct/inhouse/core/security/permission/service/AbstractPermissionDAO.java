package com.cct.inhouse.core.security.permission.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.common.CommonDAO;
import com.cct.common.CommonSQLPath;
import com.cct.domain.Operator;
import com.cct.inhouse.core.config.parameter.domain.SQLPath;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.log.DefaultLogUtil;

public abstract class AbstractPermissionDAO extends CommonDAO {
	private CommonSQLPath sharedSQLPath;

	public AbstractPermissionDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
		
		this.sharedSQLPath = SQLPath.SHARED_PERMISSION_SQL.getSqlPath();
	}

	/**
	 * หาข้อมูลสิทธิ์ (SEARCH MIN, MAX MENU LEVEL)
	 * @param conn
	 * @param systemCode
	 * @param operatorIds
	 * @return
	 * @throws Exception
	 */
	public Operator searchOperatorLevel(CCTConnection conn, String systemCode) throws Exception {
		Operator operatorLevel = new Operator();
		
		Object[] params = new Object[2];
		params[0] = systemCode;

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, sharedSQLPath.getClassName()
				, sharedSQLPath.getPath()
				, "searchOperatorLevel"
				, params);
		DefaultLogUtil.COMMON.debug("SQL: " + sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			if (rst.next()) {
				operatorLevel = new Operator();
				operatorLevel.setMinLevel(rst.getInt("MIN_LEVEL"));
				operatorLevel.setMaxLevel(rst.getInt("MAX_LEVEL"));
			}
		} catch (Exception e) {
			throw e;
		}
		
		DefaultLogUtil.COMMON.debug("Operator Level = (" + operatorLevel.getMinLevel() + "," + operatorLevel.getMaxLevel() + ")");
		return operatorLevel;
	}
	
	public Map<String, Operator> searchOperator(CCTConnection conn, String systemCode) throws Exception {
		Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();
		
		Object[] params = new Object[2];
		params[0] = systemCode;

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, sharedSQLPath.getClassName()
				, sharedSQLPath.getPath()
				, "searchOperator"
				, params);
		DefaultLogUtil.COMMON.debug("SQL: " + sql);
	
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				Operator operator = new Operator();
				operator.setOperatorId(rst.getString("OPERATOR_ID"));
				operator.setParentId(rst.getString("PARENT_ID"));
				operator.setOperatorType(rst.getString("TYPE"));
				operator.setCurrentLevel(rst.getInt("LEVEL"));
				operator.setSystemCode(rst.getString("SYSTEM_CODE"));
				operator.setLabel(rst.getString("LABEL_TH"));
				operator.setUrl(rst.getString("URL"));
				operator.setVisible("Y");
				operator.getActive().setCode(rst.getString("ACTIVE"));
				
				mapOperator.put(operator.getOperatorId(), operator);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		DefaultLogUtil.COMMON.debug("Map Operator size = " + mapOperator.size());
		return mapOperator;
	}
	
	/**
	 * หาข้อมูลสิทธิ์ (SEARCH MENU)
	 * @param conn
	 * @param userId
	 * @param systemCode
	 * @param operatorIds
	 * @return
	 * @throws Exception
	 */
	public Map<String, Operator> searchOperatorByUserId(CCTConnection conn, String userId, String systemCode) throws Exception {
		Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();
		
		Object[] params = new Object[2];
		params[0] = systemCode;
		params[1] = userId;
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, sharedSQLPath.getClassName()
				, sharedSQLPath.getPath()
				, "searchOperatorByUserId"
				, params);
		DefaultLogUtil.COMMON.debug("SQL: " + sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				Operator operator = new Operator();
				operator.setOperatorId(rst.getString("OPERATOR_ID"));
				operator.setParentId(rst.getString("PARENT_ID"));
				operator.setOperatorType(rst.getString("TYPE"));
				operator.setCurrentLevel(rst.getInt("LEVEL"));
				operator.setSystemCode(rst.getString("SYSTEM_CODE"));
				operator.setLabel(rst.getString("LABEL_TH"));
				operator.setUrl(rst.getString("URL"));
				operator.setVisible("Y");
				operator.getActive().setCode(rst.getString("ACTIVE"));
				
				mapOperator.put(operator.getOperatorId(), operator);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		DefaultLogUtil.COMMON.debug("Map Operator size = " + mapOperator.size());
		return mapOperator;
	}
}
