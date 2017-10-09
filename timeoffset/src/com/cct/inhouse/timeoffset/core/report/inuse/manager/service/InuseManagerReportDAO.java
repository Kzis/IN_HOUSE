package com.cct.inhouse.timeoffset.core.report.inuse.manager.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.hrmdata.web.gettimeoffset.ws.WorkOnsite;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportDetail;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportSearch;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;
import com.cct.inhouse.timeoffset.util.log.LogUtil;

public class InuseManagerReportDAO extends AbstractDAO<InuseManagerReportSearchCriteria, InuseManagerReportSearch, Object> {

	public InuseManagerReportDAO(Logger logger, CommonSQLPath sqlPath,CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	protected String searchProcessReqDateTime(CCTConnection conn, String id) throws Exception {

		String processDate = null;

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = id;

		String sql = SQLUtil.getSQLString(conn.getSchemas()
							, getSqlPath().getClassName()
							, getSqlPath().getPath()
							, "searchProcessReqDateTime", params
							);

		getLogger().debug("SQL : " + sql);

		Statement stmt = null;
		ResultSet rst = null;

		try {

			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next()) {
				if (rst.getString("PROCESS_DATE") != null) {
					
					// String oldFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMMSS();
					String oldFormat = "yyyy-MM-dd HH:mm:ss";
					Locale localeNew = ParameterConfig.getParameter().getApplication().getApplicationLocale();
					
					// String newFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
					String newFormat = "dd/MM/yyy HH:mm:ss";

					SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, localeNew);
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
	
	public String searchProcessReqDateTimeAll(CCTConnection conn) throws Exception {
		
		String processDate = null;

		String sql = SQLUtil.getSQLString(conn.getSchemas()
							, getSqlPath().getClassName()
							, getSqlPath().getPath()
							, "searchProcessReqDateTimeAll"
							);

		getLogger().debug("SQL : " + sql);

		Statement stmt = null;
		ResultSet rst = null;

		try {

			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next()) {
				if (rst.getString("PROCESS_DATE") != null) {
					String oldFormat = "yyyy-MM-dd HH:mm:ss";
					Locale localeNew = ParameterConfig.getParameter().getApplication().getApplicationLocale();
					String newFormat = "dd/MM/yyy HH:mm:ss";

					SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, localeNew);
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

	protected int insertLogCallWS(CCTConnection conn, String id, CommonUser commonUser, Locale locale) throws Exception {

		LogUtil.TO_REPORT.debug("##### [insertLogCallWS] #####");

		int lastWsProcessId = 0;

		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = id;
		params[paramIndex] = StringUtil.replaceSpecialString(commonUser.getId(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
					, getSqlPath().getClassName()
					, getSqlPath().getPath()
					, "insertLogCallWS"
					, params
					);

		getLogger().debug("SQL : " + sql);

		try {
			lastWsProcessId = (int) conn.executeInsertStatement(sql);
			LogUtil.TO_REPORT.debug("SQL id : " + lastWsProcessId);

		} catch (Exception e) {
			throw e;
		}

		return lastWsProcessId;
	}

	protected void updateLogCallWS(CCTConnection conn, String processStatus, String msgDesc, int sizeArrWorkOnSite, int lastWsProcessId, CommonUser commonUser, Locale locale)
			throws Exception {

		LogUtil.TO_REPORT.debug("##### [updateLogCallWS] #####");

		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString(processStatus, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(msgDesc, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = sizeArrWorkOnSite;
		params[paramIndex++] = StringUtil.replaceSpecialString(commonUser.getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = lastWsProcessId;

		String sql = SQLUtil.getSQLString(conn.getSchemas()
					, getSqlPath().getClassName()
					, getSqlPath().getPath()
					, "updateLogCallWS"
					, params
					);

		getLogger().debug("SQL : " + sql);

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}

	}

	protected int insertWorkOnSite(CCTConnection conn, WorkOnsite workOnsite, CommonUser commonUser, Locale locale) throws Exception {

		LogUtil.TO_REPORT.debug("##### [insertWorkOnSite] #####");

		int paramIndex = 0;
		Object[] params = new Object[20];
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsiteid(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getUserid(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getEmployeeid(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getFirstnametha(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getLastnametha(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getNickname(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getWorkonsiteid(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitedatefrom1(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitetimefrom1(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitedateto2(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitetimeto2(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = workOnsite.getTotalOnsiteday();
		params[paramIndex++] = workOnsite.getTotalOnsitetime();
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getSiteservice(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitestatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getApprovets(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getSiteserviceRemark(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitestatusRemark(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getApproverid(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(commonUser.getId(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
					, getSqlPath().getClassName()
					, getSqlPath().getPath()
					, "insertWorkOnSite"
					, params
					);

		getLogger().debug("SQL : " + sql);

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}

		return 0;
	}

	protected int updateWorkOnSite(CCTConnection conn, WorkOnsite workOnsite, CommonUser commonUser, Locale locale) throws Exception {

		LogUtil.TO_REPORT.debug("##### [updateWorkOnSite] #####");

		int paramIndex = 0;
		Object[] params = new Object[20];
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getUserid(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getEmployeeid(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getFirstnametha(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getLastnametha(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getNickname(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getWorkonsiteid(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitedatefrom1(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitetimefrom1(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitedateto2(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitetimeto2(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = workOnsite.getTotalOnsiteday();
		params[paramIndex++] = workOnsite.getTotalOnsitetime();
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getSiteservice(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitestatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getApprovets(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getSiteserviceRemark(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getOnsitestatusRemark(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(workOnsite.getApproverid(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(commonUser.getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(workOnsite.getOnsiteid(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(), "updateWorkOnSite", params);

		getLogger().debug("SQL : " + sql);

		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}

		return 0;
	}

	@Override
	protected String createSQLCountData(CCTConnection conn,InuseManagerReportSearchCriteria criteria, CommonUser user,Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDepartmentId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(criteria.getEmployeeId(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCountManager"
				, params);

		getLogger().debug("SQL : " + sql);
		
		return sql;

	}
	
	private InuseManagerReportSearch searchTOWaitStatus(CCTConnection conn, InuseManagerReportSearch data) throws Exception {

		int paramIndex = 0;
		Object[] params = new Object[8];
		params[paramIndex++] = data.getId();
		params[paramIndex++] = data.getId();
		params[paramIndex++] = data.getId();
		params[paramIndex++] = data.getId();
		params[paramIndex++] = data.getId();
		params[paramIndex++] = data.getId();
		params[paramIndex++] = data.getId();
		params[paramIndex] = data.getId();

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchManagerTime"
				, params);

		getLogger().debug("SQL : " + sql);

		Statement stmt = null;
		ResultSet rst = null;

		try {

			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			while (rst.next()) {

				data.setTimeOffset(StringUtil.nullToString(rst.getString("TOTAL_TIMEOFFSET_MIN")));
				data.setTimePendingHRM(StringUtil.nullToString(rst.getString("TOTAL_WAIT_A_HRM")));
				data.setPROCESS_RES_DATETIME_BY_USER(StringUtil.nullToString(rst.getString("PROCESS_RES_DATETIME_BY_USER")));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return data;
	}
	
	protected InuseManagerReportSearch searchDetailTOWaitStatus(CCTConnection conn, InuseManagerReportSearchCriteria criteria, InuseManagerReportSearch data)throws Exception {
		
		List<InuseManagerReportDetail> lstDetail = new ArrayList<InuseManagerReportDetail>();
		String[] newTime = new String[3];

		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = data.getId();
		
		if( (criteria.getStartDate() != null && !(criteria.getStartDate().isEmpty()) ) 
			&& (criteria.getEndDate() != null && !(criteria.getEndDate().isEmpty()) )  ){
			params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getStartDate().concat(GlobalVariableTO.SPACE_START_TIME), conn.getDbType(), ResultType.NULL);
			params[paramIndex] = StringUtil.replaceSpecialString(criteria.getEndDate().concat(GlobalVariableTO.SPACE_END_TIME), conn.getDbType(), ResultType.NULL);
		}else{
			params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getStartDate(), conn.getDbType(), ResultType.NULL);
			params[paramIndex] = StringUtil.replaceSpecialString(criteria.getEndDate(), conn.getDbType(), ResultType.NULL);
		}

		String sql = SQLUtil.getSQLString(conn.getSchemas()
						, getSqlPath().getClassName()
						, getSqlPath().getPath()
						, "searchManagerDetail"
						, params
						);

		getLogger().debug("SQL : " + sql);

		Statement stmt = null;
		ResultSet rst = null;

		try {

			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			while (rst.next()) {

				InuseManagerReportDetail dataDetail = new InuseManagerReportDetail();
				
				dataDetail.setStartDateTime(TOUtil.convertDateTimeSyncDateForDisplay(StringUtil.nullToString(rst.getString("START_DATETIME"))));
				dataDetail.setEndDateTime(TOUtil.convertDateTimeSyncDateForDisplay(StringUtil.nullToString(rst.getString("END_DATETIME"))));

				// Process compute day hour minute from hrm
				newTime = TOUtil.computeTimeHRM(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")), StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));
				dataDetail.setTotalDay(newTime[0]);
				dataDetail.setTotalHour(newTime[1]);
				dataDetail.setTotalMinute(newTime[2]);

				dataDetail.setWorkPlace(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")));

				lstDetail.add(dataDetail);
			}

			data.setListDetail(lstDetail);

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return data;
	}
	

	public List<InuseManagerReportSearch> search(CCTConnection conn,InuseManagerReportSearchCriteria criteria,String repFlag) throws Exception {
		
		List<InuseManagerReportSearch> lstResult = new ArrayList<InuseManagerReportSearch>();
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDepartmentId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getEmployeeId(), conn.getDbType(), ResultType.NULL);

		params[paramIndex++] = criteria.getStart() - 1;
		params[paramIndex] = (criteria.getLinePerPage());

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchManager"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		
		try {
			
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while (rst.next()) {
				
				InuseManagerReportSearch data = new InuseManagerReportSearch();
				
				data.setId(StringUtil.nullToString(rst.getString("USER_ID")));
				data.setDepartment(StringUtil.nullToString(rst.getString("DEPARTMENT")));
				data.setFullName(StringUtil.nullToString(rst.getString("FULLNAME")));
				data.setALL_PROCESS_RES_DATETIME((StringUtil.nullToString(rst.getString("ALL_PROCESS_RES_DATETIME"))));
				
				if(data.getId() != null){
					data = searchTOWaitStatus(conn,data);
					data = TOUtil.manageSearchTOWaitStatus(data,repFlag);
					data = searchDetailTOWaitStatus(conn, criteria, data);
				}
				
				lstResult.add(data);
			}
			
		} catch (Exception e) {
			throw e;
		} finally{
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return lstResult;
	}
	
	@Override
	protected String createSQLSearch(CCTConnection conn,InuseManagerReportSearchCriteria criteria, CommonUser user,Locale locale) throws Exception {
		
		return null;
	}

	@Override
	protected InuseManagerReportSearch createResultSearch(ResultSet rst,CommonUser user, Locale locale) throws Exception {
		
		return null;
	}

	@Override
	protected String createSQLSearchById(CCTConnection conn, String id,
			CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object createResultSearchById(ResultSet rst, CommonUser user,
			Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLAdd(CCTConnection conn, Object obj,
			CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLEdit(CCTConnection conn, Object obj,
			CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLDelete(CCTConnection conn, String ids,
			CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLUpdateActive(CCTConnection conn, String ids,
			String activeFlag, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLCheckDup(CCTConnection conn, Object obj,
			CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




}
