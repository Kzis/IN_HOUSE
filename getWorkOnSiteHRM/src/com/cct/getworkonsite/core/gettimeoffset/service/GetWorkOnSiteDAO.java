package com.cct.getworkonsite.core.gettimeoffset.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.log.LogUtil;

import com.cct.getworkonsite.core.config.parameter.domain.ParameterConfig;
import com.cct.getworkonsite.core.config.parameter.domain.SQLPath;
import com.cct.hrmdata.web.gettimeoffset.ws.WorkOnsite;

public class GetWorkOnSiteDAO {

	private SQLPath sqlPath;

	public SQLPath getSqlPath() {
		return sqlPath;
	}

	public void setSqlPath(SQLPath sqlPath) {
		this.sqlPath = sqlPath;
	}

	protected String searchLastProcessDate(CCTConnection conn) throws Exception {
		
		String processDate = null;

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchLastProcessDate");
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			LogUtil.GET_WORK_ONSITE.debug("SQL : " + sql);
			
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				if (rst.getString("PROCESS_DATE") != null) {
					String oldFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMMSS();
					Locale locale = ParameterConfig.getParameter().getApplication().getApplicationLocale();
					String newFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
					
					SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, locale);
					Date d = sdf.parse(rst.getString("PROCESS_DATE"));
					sdf.applyPattern(newFormat);
					processDate = sdf.format(d);
				}
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return processDate;
	}

	protected int insertLog(CCTConnection conn) throws Exception {
		
		int lastWsProcessId = 0;
		
		Integer paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = ParameterConfig.getParameter().getBgConfig().getUserProcess();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertLog"
				, params);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			LogUtil.GET_WORK_ONSITE.debug("SQL : " + sql);
			
			stmt = conn.createStatement();
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rst = stmt.getGeneratedKeys();
			if (rst.next()) {
				lastWsProcessId = rst.getInt(1);
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return lastWsProcessId;
	}

	protected void updateLog(CCTConnection conn, String processStatus, String msgDesc, int sizeArrWorkOnSite, int lastWsProcessId) throws Exception {
		
		Integer paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = processStatus;
		params[paramIndex++] = msgDesc;
		params[paramIndex++] = sizeArrWorkOnSite;
		params[paramIndex++] = ParameterConfig.getParameter().getBgConfig().getUserProcess();
		params[paramIndex++] = lastWsProcessId;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateLog"
				, params);
		
		Statement stmt = null;
		try {
			LogUtil.GET_WORK_ONSITE.debug("SQL : " + sql);
			
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
	}

	protected int updateWorkOnSite(CCTConnection conn, WorkOnsite workOnsite) throws Exception {
		
		int exec = 0;

		Integer paramIndex = 0;
		Object[] params = new Object[21];
		params[paramIndex++] = workOnsite.getUserid();
		params[paramIndex++] = workOnsite.getEmployeeid();
		params[paramIndex++] = workOnsite.getFirstnametha();
		params[paramIndex++] = workOnsite.getLastnametha();
		params[paramIndex++] = workOnsite.getNickname();
		params[paramIndex++] = workOnsite.getWorkonsiteid();
		params[paramIndex++] = workOnsite.getOnsitedatefrom1();
		params[paramIndex++] = workOnsite.getOnsitetimefrom1();
		params[paramIndex++] = workOnsite.getOnsitedateto2();
		params[paramIndex++] = workOnsite.getOnsitetimeto2();
		params[paramIndex++] = workOnsite.getTotalOnsiteday();
		params[paramIndex++] = workOnsite.getTotalOnsitetime();
		params[paramIndex++] = workOnsite.getSiteservice();
		params[paramIndex++] = workOnsite.getOnsitestatus();
		params[paramIndex++] = workOnsite.getApprovets();
		params[paramIndex++] = workOnsite.getSiteserviceRemark();
		params[paramIndex++] = workOnsite.getOnsitestatusRemark();
		params[paramIndex++] = workOnsite.getApproverid();
		params[paramIndex++] = ParameterConfig.getParameter().getBgConfig().getUserProcess();
		params[paramIndex++] = workOnsite.getOnsiteid();
		params[paramIndex++] = workOnsite.getUserid();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateWorkOnSite"
				, params);
		
		Statement stmt = null;
		try {
			LogUtil.GET_WORK_ONSITE.debug("SQL : " + sql);
			
			stmt = conn.createStatement();
			exec = stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		
		return exec;
	}

	protected int insertWorkOnSite(CCTConnection conn, WorkOnsite workOnsite) throws Exception {

		int exec = 0;

		Integer paramIndex = 0;
		Object[] params = new Object[20];
		params[paramIndex++] = workOnsite.getOnsiteid();
		params[paramIndex++] = workOnsite.getUserid();
		params[paramIndex++] = workOnsite.getEmployeeid();
		params[paramIndex++] = workOnsite.getFirstnametha();
		params[paramIndex++] = workOnsite.getLastnametha();
		params[paramIndex++] = workOnsite.getNickname();
		params[paramIndex++] = workOnsite.getWorkonsiteid();
		params[paramIndex++] = workOnsite.getOnsitedatefrom1();
		params[paramIndex++] = workOnsite.getOnsitetimefrom1();
		params[paramIndex++] = workOnsite.getOnsitedateto2();
		params[paramIndex++] = workOnsite.getOnsitetimeto2();
		params[paramIndex++] = workOnsite.getTotalOnsiteday();
		params[paramIndex++] = workOnsite.getTotalOnsitetime();
		params[paramIndex++] = workOnsite.getSiteservice();
		params[paramIndex++] = workOnsite.getOnsitestatus();
		params[paramIndex++] = workOnsite.getApprovets();
		params[paramIndex++] = workOnsite.getSiteserviceRemark();
		params[paramIndex++] = workOnsite.getOnsitestatusRemark();
		params[paramIndex++] = workOnsite.getApproverid();
		params[paramIndex++] = ParameterConfig.getParameter().getBgConfig().getUserProcess();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertWorkOnSite"
				, params);
		
		Statement stmt = null;
		try {
			LogUtil.GET_WORK_ONSITE.debug("SQL : " + sql);
			
			stmt = conn.createStatement();
			exec = stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		
		return exec;
	}
}
