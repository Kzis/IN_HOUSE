package com.cct.hrmdata.core.checkuserlogin.service;

import java.sql.ResultSet;
import java.sql.Statement;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.log.LogUtil;

import com.cct.abstracts.AbstractDAO;

public class CheckUserLoginDAO extends AbstractDAO {
	
	/**
	 * Check Login by username and password
	 * 
	 * @param conn CCTConnection
	 * @param username
	 * @param password
	 * 
	 * @return boolean is found.
	 * @throws Exception
	 */
	protected int checkLogin(CCTConnection conn, String username, String password) throws Exception {
		int found = -99;
		
		// Set parameter
		Object[] params = new Object[2];
		params[0] = username;
		params[1] = password;
		
		// Get SQL
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchUserByUsernameAndPassword"
				, params);
		LogUtil.GET_USER.debug("SQL searchUserByUsernameAndPassword :" + sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {			
				// found user return user id
				found = rst.getInt("USERID");
				break;
			}
			
		} catch (Exception e){
			throw e;
			
		} finally {
			if(rst != null) {
				rst.close();
			}
			
			if(stmt != null) {
				stmt.close();
			}
		}
		
		return found;
	}
	
}
