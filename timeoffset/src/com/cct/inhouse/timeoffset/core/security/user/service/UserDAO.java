package com.cct.inhouse.timeoffset.core.security.user.service;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

import com.cct.common.CommonDAO;
import com.cct.common.CommonSQLPath;
import com.cct.inhouse.timeoffset.core.security.user.domain.TempUser;

public class UserDAO extends CommonDAO {

	public UserDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected TempUser searchUserLogin(CCTConnection conn, String username,String password) throws Exception {
		
		TempUser result = null;
		
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(username, conn.getDbType() , ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(password, conn.getDbType() , ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchUserLogin"
				, params);

		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = stmt.executeQuery(sql);
			if (rst.next()){
				result = new TempUser();
				result.setId(StringUtil.nullToString(rst.getString("USER_ID")));
				result.setName(StringUtil.nullToString(rst.getString("NAME")));
				result.setSurName(StringUtil.nullToString(rst.getString("SURNAME")));
				result.setFullName(StringUtil.nullToString(rst.getString("NAME")) + " " + StringUtil.nullToString(rst.getString("SURNAME")));
				result.setDepartmentId(StringUtil.nullToString(rst.getString("DEPARTMENT_ID")));
				result.setPositionId(StringUtil.nullToString(rst.getString("POSITION_ID")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return result;
	}

}
