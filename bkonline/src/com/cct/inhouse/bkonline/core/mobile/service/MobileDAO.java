package com.cct.inhouse.bkonline.core.mobile.service;

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
import com.cct.inhouse.bkonline.core.mobile.domain.MDUser;

public class MobileDAO extends CommonDAO {

	public MobileDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected void deleteDeviceInfo(CCTConnection conn, String deviceId) throws Exception {
		
		if ((deviceId == null) || deviceId.isEmpty()) {
			throw new Exception("DeviceId is Black");
		}
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = deviceId;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"deleteDeviceByDeviceId", params);
		getLogger().debug(sql);
		
		executeUpdate(conn, sql);
	}

	protected void addDeviceInfo(CCTConnection conn, String userId, String deviceId, String plateForm, String tokenId)throws Exception  {
		
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.replaceSpecialString(userId, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(deviceId, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(plateForm, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(tokenId, conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), 
					getSqlPath().getClassName(),
					getSqlPath().getPath(),
					"insertDeviceInfo", 
					params);

		getLogger().debug(sql);
		executeInsert(conn, sql);
		
	}

	protected boolean checkTokenId(CCTConnection conn, MDUser mdUser) throws Exception{
		boolean result = false;
		
		if (mdUser.getTokenId() == null) {
			return result;
		}
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.replaceSpecialString(mdUser.getTokenId(),conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchTokenByTokenId", params);

		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = true;
				mdUser.setUserId(StringUtil.nullToString(rst.getString("USER_ID")));
			}
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return result;
	}
}
