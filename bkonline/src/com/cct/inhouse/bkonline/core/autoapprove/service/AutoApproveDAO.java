package com.cct.inhouse.bkonline.core.autoapprove.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cct.common.CommonDAO;
import com.cct.common.CommonSQLPath;
import com.cct.inhouse.bkonline.core.autoapprove.domain.TimeForAutoApprove;
import com.cct.inhouse.util.IHUtil;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;

public class AutoApproveDAO extends CommonDAO {

	public AutoApproveDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	/**
	 * ค้นหาห้องที่ให้ทำอนุมัติอัตโนมัติ
	 * @throws Exception 
	 */
	protected List<TimeForAutoApprove> searchRoomIdForAutoApprove(CCTConnection conn) throws Exception {
		
		List<TimeForAutoApprove> results = new ArrayList<TimeForAutoApprove>();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchRoomIdForAutoApprove");
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				TimeForAutoApprove result = new TimeForAutoApprove();
				result.setAutotimeId(StringUtil.nullToString(rst.getString("AUTOTIME_ID")));
				result.setRoomId(StringUtil.nullToString(rst.getString("ROOM_ID")));	
				results.add(result);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return results;
	}
	
	/**
	 * ค้นหา Event สำหรับห้องที่กำหนดให้อนุมัติอัตโนมัติ
	 * @throws Exception 
	 */
	protected List<String> searchEventIdForAutoApprove(CCTConnection conn, String roomId, String timeForCheck) throws Exception {
		
		List<String> results = new ArrayList<String>();
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = IHUtil.convertLongValue(roomId);
		params[paramIndex++] = IHUtil.convertLongValue(timeForCheck);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchEventIdForAutoApprove", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				results.add(StringUtil.nullToString(rst.getString("EVENT_ID")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return results;
	}
}
