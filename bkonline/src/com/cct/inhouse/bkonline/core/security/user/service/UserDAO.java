package com.cct.inhouse.bkonline.core.security.user.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.common.CommonDAO;
import com.cct.common.CommonSQLPath;
import com.cct.inhouse.bkonline.core.security.user.domain.AdminUser;
import com.cct.inhouse.bkonline.core.security.user.domain.TempUser;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.util.IHUtil;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class UserDAO extends CommonDAO {

	public UserDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	/** 
	 * ค้นหา User admin
	 * @throws Exception 
	 */
	protected Map<String, AdminUser> searchAdminInSystem(CCTConnection conn) throws Exception {
		
		Map<String, AdminUser> mapResult = new HashMap<String, AdminUser>();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchAdminInSystem");

		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()){
				AdminUser result = new AdminUser();
				result.setAdminId(StringUtil.nullToString(rst.getString("ADMIN_ID")));
				result.setId(StringUtil.nullToString(rst.getString("USER_ID")));
				result.setSystemCode(StringUtil.nullToString(rst.getString("SYSTEM_CODE")));
				result.setDisplayName(StringUtil.nullToString(rst.getString("DISPLAY_NAME")));
				
				result.setEmail(StringUtil.nullToString(rst.getString("EMAIL")));
				result.setGmail(StringUtil.nullToString(rst.getString("GMAIL")));
				result.setLineUserId(StringUtil.nullToString(rst.getString("LINE_USER_ID")));
				
				mapResult.put(result.getId(), result);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return mapResult;
	}
	
	/**
	 * ค้นหา User ทั่วไป
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected InhouseUser searchUserInSystem(CCTConnection conn, String userId) throws Exception {
		
		InhouseUser result = null;
		
		if ((userId == null) || userId.trim().isEmpty()) {
			return result;
		}
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.stringToLong(userId);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchUserInSystem", params);

		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()){
				result = new InhouseUser();
				result.setId(StringUtil.nullToString(rst.getString("USER_ID")));
				result.setDepartmentId(StringUtil.nullToString(rst.getString("DEPARTMENT_ID")));
				result.setPositionId(StringUtil.nullToString(rst.getString("POSITION_ID")));
				result.setTitleName(StringUtil.nullToString(rst.getString("TITLE_NAME")));
				result.setName(StringUtil.nullToString(rst.getString("NAME")));
				result.setSurName(StringUtil.nullToString(rst.getString("SURNAME")));
				result.setNickName(StringUtil.nullToString(rst.getString("NICKNAME")));
				result.setDisplayName(StringUtil.nullToString(rst.getString("DISPLAY_NAME")));
				
				result.setEmail(StringUtil.nullToString(rst.getString("EMAIL")));
				result.setGmail(StringUtil.nullToString(rst.getString("GMAIL")));
				result.setLineUserId(StringUtil.nullToString(rst.getString("LINE_USER_ID")));
				
				result.setColorCode(StringUtil.nullToString(rst.getString("COLOR_CODE")));
				result.setTableNo(StringUtil.nullToString(rst.getString("TABLE_NO")));
				result.setInsidePhoneNumber(StringUtil.nullToString(rst.getString("INSIDE_PHONE_NUMBER")));
				result.setCardId(StringUtil.nullToString(rst.getString("CARD_ID")));
				result.setUpdateDate(StringUtil.nullToString(rst.getString("UPDATE_DATE")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return result;
	}
	
	/**
	 * 
	 * ค้นหาข้อมูล login
	 * @param conn
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	protected TempUser searchUserLogin(CCTConnection conn, String username, String password) throws Exception {
		
		TempUser result = null;
		
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(username, conn.getDbType() , ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(password, conn.getDbType() , ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchUserLogin", params);

		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()){
				result = new TempUser();
				result.setId(StringUtil.nullToString(rst.getString("USER_ID")));
				result.setDepartmentId(StringUtil.nullToString(rst.getString("DEPARTMENT_ID")));
				result.setPositionId(StringUtil.nullToString(rst.getString("POSITION_ID")));
				result.setTitleName(StringUtil.nullToString(rst.getString("TITLE_NAME")));
				result.setName(StringUtil.nullToString(rst.getString("NAME")));
				result.setSurName(StringUtil.nullToString(rst.getString("SURNAME")));
				result.setNickName(StringUtil.nullToString(rst.getString("NICKNAME")));
				result.setDisplayName(StringUtil.nullToString(rst.getString("DISPLAY_NAME")));
				
				result.setEmail(StringUtil.nullToString(rst.getString("EMAIL")));
				result.setGmail(StringUtil.nullToString(rst.getString("GMAIL")));
				result.setLineUserId(StringUtil.nullToString(rst.getString("LINE_USER_ID")));
				result.setLineDisplayName(StringUtil.nullToString(rst.getString("LINE_DISPLAY_NAME")));
				
				result.setColorCode(StringUtil.nullToString(rst.getString("COLOR_CODE")));
				result.setTableNo(StringUtil.nullToString(rst.getString("TABLE_NO")));
				result.setInsidePhoneNumber(StringUtil.nullToString(rst.getString("INSIDE_PHONE_NUMBER")));
				result.setCardId(StringUtil.nullToString(rst.getString("CARD_ID")));
				result.setUpdateDate(StringUtil.nullToString(rst.getString("UPDATE_DATE")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return result;
	}
	
	
	/**
	 * updateLineDisplayName
	 * @param conn
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	protected long updateLineDisplayName(CCTConnection conn, String userId,String lineDisplayName) throws Exception {
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(lineDisplayName, conn.getDbType() , ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(userId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"updateLineDisplayName", params);
		
		getLogger().debug(sql);
		
		return executeUpdate(conn, sql); 
		
	}
	
	protected List<String> searchAuthorizationNormal(CCTConnection conn) throws Exception {
		
		List<String> listResult = new ArrayList<String>();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchAuthorizationNormal");
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while(rst.next()) {
				listResult.add(StringUtil.nullToString(rst.getString("OPERATOR_ID")));
			}
		} catch (Exception e) {
			throw e;
		}
		return listResult;
	}
}
