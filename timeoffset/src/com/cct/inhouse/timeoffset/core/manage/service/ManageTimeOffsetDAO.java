package com.cct.inhouse.timeoffset.core.manage.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffset;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetSearch;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;

public class ManageTimeOffsetDAO extends AbstractDAO<ManageTimeOffsetSearchCriteria, ManageTimeOffsetSearch, ManageTimeOffset> {
		
	public ManageTimeOffsetDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	@Override
	protected ManageTimeOffsetSearch createResultSearch(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		ManageTimeOffsetSearch data = new ManageTimeOffsetSearch();
		data.setId(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID")));
		data.setProjectAbbr(StringUtil.nullToString(rst.getString("PROJECT_ABBR")));
		data.setProjConDetail(StringUtil.nullToString(rst.getString("PROJ_CON_DETAIL")));
		
		if(StringUtil.nullToString(rst.getString("DAY")).isEmpty()){
			data.setDay("0.0");
		}else{
			data.setDay(StringUtil.nullToString(rst.getString("DAY")));
		}
		
		if(StringUtil.nullToString(rst.getString("HOUR")).isEmpty()){
			data.setHour("0.0");
		}else{
			data.setHour(StringUtil.nullToString(rst.getString("HOUR")));
		}
		
		if(StringUtil.nullToString(rst.getString("MINUTE")).isEmpty()){
			data.setMinute("0.0");
		}else{
			data.setMinute(StringUtil.nullToString(rst.getString("MINUTE")));
		}

		data.setProcessStatus(StringUtil.nullToString(rst.getString("PROCESS_STATUS")));
		data.setProcessStatusDesc(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
		
		return data;
	}

	@Override
	protected ManageTimeOffset createResultSearchById(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		ManageTimeOffset data = new ManageTimeOffset();
		data.setTimeOffsetId(Integer.parseInt(rst.getString("TIMEOFFSET_ID")));
		data.setId(StringUtil.nullToString(rst.getString("TIMEOFFSET_DET_ID")));
		data.setFullName(StringUtil.nullToString(rst.getString("USER_FULLNAME")));
		data.setProjectId(StringUtil.nullToString(rst.getString("PROJECT_ID")));
		data.setProject(StringUtil.nullToString(rst.getString("PROJECT_ABBR"))); // ABBR
		data.setProjectConditionId(StringUtil.nullToString(rst.getString("PROJ_CON_ID")));
		data.setProjectCondition(StringUtil.nullToString(rst.getString("PROJ_CON_DETAIL"))); //DETAIL
		data.setRemark(StringUtil.nullToString(rst.getString("REMARK")));
		data.setApproveRemark(StringUtil.nullToString(rst.getString("APPROVE_REMARK")));
		data.setApproveUser(StringUtil.nullToString(rst.getString("APPROVE_USER_FULLNAME")));
		
		return data;
	}

	@Override
	protected String createSQLAdd(CCTConnection conn, ManageTimeOffset obj, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[8];
		params[paramIndex++] = obj.getTimeOffsetId();
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getStartDate())+ " " +(obj.getStartTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getEndDate())+ " " +(obj.getEndTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getDay(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getHour(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getMinute(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getDetailWork(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertDetailTO"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLCheckDup(CCTConnection conn, ManageTimeOffset obj, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		
		Object[] params = new Object[9];
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjectConditionId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getStartDate())+ " " +(obj.getStartTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getStartDate())+ " " +(obj.getStartTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getStartDate())+ " " +(obj.getStartTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getEndDate())+ " " +(obj.getEndTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getEndDate())+ " " +(obj.getEndTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getEndDate())+ " " +(obj.getEndTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "checkDup"
				, params );
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLCountData(CCTConnection conn, ManageTimeOffsetSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(criteria.getProcessStatus(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCount"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLDelete(CCTConnection conn, String ids, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		
		params[paramIndex] = ids;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteTODetail"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLEdit(CCTConnection conn, ManageTimeOffset obj, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		
		Object[] params = new Object[9];
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getStartDate())+ " " +(obj.getStartTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL); 
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getEndDate())+ " " +(obj.getEndTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getDay(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getHour(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getMinute(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getDetailWork(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getRemark(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(),ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(obj.getId(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "editTimeOffset"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLSearch(CCTConnection conn, ManageTimeOffsetSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[6];		
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProcessStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = criteria.getOrderList();
		
		params[paramIndex++] = criteria.getStart()-1;
		params[paramIndex] = criteria.getLinePerPage();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchTimeOffset"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLSearchById(CCTConnection conn, String id, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchTimeOffsetById"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	protected long countTOT(CCTConnection conn, long id) throws Exception {
		
		long count = 0;

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = id;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCountTOT"
				, params);
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				count = rst.getLong("TOT");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return count;
	}

	protected List<ManageTimeOffset> searchListById(CCTConnection conn, String id) throws Exception {
		
		List<ManageTimeOffset> listNewProject = new ArrayList<ManageTimeOffset>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchTimeOffsetById"
				, params);
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			int i =0;
			while (rst.next()) {
				ManageTimeOffset data = new ManageTimeOffset();
				
				//Table
				data.setId(StringUtil.nullToString(rst.getString("TIMEOFFSET_DET_ID")));
				data.setTimeOffsetId(Integer.parseInt(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID"))));
				data.setDetailWork(StringUtil.nullToString(rst.getString("DETAIL_WORK")));
				data.setStartDateTime(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))));
				data.setEndDateTime(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))));
				data.setDay(TOUtil.manageDecimalToInteger(StringUtil.nullToString(rst.getString("DAY"))));
				data.setHour(TOUtil.manageDecimalToInteger(StringUtil.nullToString(rst.getString("HOUR"))));
				data.setMinute(TOUtil.manageDecimalToInteger(StringUtil.nullToString(rst.getString("MINUTE"))));
				data.setApproveStatus(StringUtil.nullToString(rst.getString("APPROVE_STATUS")));
				data.setApproveStatusDesc(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
				data.setWorkDate(TOUtil.splitToDate(data.getStartDateTime()));
				
				//Edit
				data.setProjectId(StringUtil.nullToString(rst.getString("PROJECT_ID")));
				data.setProject(StringUtil.nullToString(rst.getString("PROJECT_ABBR")));
				data.setProjectConditionId(StringUtil.nullToString(rst.getString("PROJ_CON_ID")));
				data.setProjectCondition(StringUtil.nullToString(rst.getString("PROJ_CON_DETAIL")));
				data.setProjectConditionFlag(StringUtil.nullToString(rst.getString("PROJ_CON_FLAG")));
				data.setHourTot(StringUtil.nullToString(rst.getString("HOUR_TOT")));
				data.setStartDate(TOUtil.splitToDate(data.getStartDateTime()));
				data.setStartTime(TOUtil.splitToTime(data.getStartDateTime()));
				data.setEndDate(TOUtil.splitToDate(data.getEndDateTime()));
				data.setEndTime(TOUtil.splitToTime(data.getEndDateTime()));
				data.setDay(TOUtil.manageDecimalToInteger(StringUtil.nullToString(rst.getString("DAY"))));
				data.setHour(TOUtil.manageDecimalToInteger(StringUtil.nullToString(rst.getString("HOUR"))));
				data.setMinute(TOUtil.manageDecimalToInteger(StringUtil.nullToString(rst.getString("MINUTE"))));
				data.setRemark(StringUtil.nullToString(rst.getString("REMARK")));
				
				//View
				data.setApproveRemark(StringUtil.nullToString(rst.getString("APPROVE_REMARK")));
				data.setApproveUser(rst.getString("APPROVE_USER_FULLNAME"));
				data.setRownum(String.valueOf(i));
				listNewProject.add(data);
				
				i++;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return listNewProject;
	}

	protected int addDetailEdit(CCTConnection conn, ManageTimeOffset obj) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[9];
		params[paramIndex++] = obj.getTimeOffsetId();

		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getStartDate())+ " " +(obj.getStartTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getEndDate())+ " " +(obj.getEndTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getDay(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getHour(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getMinute(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getDetailWork(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getRemark(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(getUser().getId(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertDetailTOEdit"
				, params);
		
		getLogger().debug(sql);

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		
		return 0 ;
	}

	protected long addMaster(CCTConnection conn, ManageTimeOffset obj) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getId(), conn.getDbType(), ResultType.NULL); //USER_ID
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjectConditionId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(getUser().getId(), conn.getDbType(), ResultType.NULL); //CREATE_USER
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertMasterTO"
				, params);
		
		getLogger().debug(sql);
		
		long timeOffsetId;
		
		try {
			timeOffsetId = conn.executeInsertStatement(sql);
			getLogger().debug("SQL id : " + timeOffsetId);
		} catch (Exception e) {
			throw e;
		}
		
		return timeOffsetId;
	}
	
	protected int editMaster(CCTConnection conn, long id, String processStatus) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(processStatus, conn.getDbType(), ResultType.NULL); 
		params[paramIndex++] = id;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "editMasterTO"
				, params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		return 0 ;
	}

	protected String searchMasterIsNull(CCTConnection conn) throws Exception{
			
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchMasterIsNull"
				);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		
		String ids = "";
		int i= 1;
		
		try {
			
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while (rst.next()) {
				
				if(i==1){
					ids = rst.getString("TIMEOFFSET_ID");
				}else{
					ids = ids + ", ";
					ids = ids + rst.getString("TIMEOFFSET_ID");
				}
				i++;	
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return ids;
	}
	
	protected Object deleteMaster(CCTConnection conn,String ids) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = ids;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteTOMaster"
				, params);
		
		getLogger().debug(sql);
		
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
	
	protected int checkDupMaster(CCTConnection conn, ManageTimeOffset obj) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(obj.getProjectConditionId(), conn.getDbType(), ResultType.NULL);
	
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "checkDupMaster"
				, params);
		
		getLogger().debug(sql);
		
		int timeOffsetId = 0;
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				timeOffsetId = Integer.parseInt(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID")));
			}
			
			getLogger().debug("SQL id : " + timeOffsetId);
		} catch (Exception e) {
			throw e;
		}
		
		return timeOffsetId;
	}

	protected int deleteTODetailInEdit(CCTConnection conn, long id) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = id;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteTODetailInEdit"
				, params);
		
		getLogger().debug(sql);
		
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
	
	protected int deleteTODetailInEdit(CCTConnection conn, String selectedIds) throws Exception{
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = selectedIds;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteTODetailInEdit2"
				, params);
		
		getLogger().debug(sql);
		
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

	protected boolean editCheckDup(CCTConnection conn, ManageTimeOffset obj,CommonUser user, Locale locale) throws Exception {
		
		boolean checkDup = false;
		int count = 0 ;
		int paramIndex = 0;
		
		Object[] params = new Object[10];
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjectConditionId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getStartDate())+ " " +(obj.getStartTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getStartDate())+ " " +(obj.getStartTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getStartDate())+ " " +(obj.getStartTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getEndDate())+ " " +(obj.getEndTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getEndDate())+ " " +(obj.getEndTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(obj.getEndDate())+ " " +(obj.getEndTime().concat(GlobalVariableTO.START_TIME_MS)), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(obj.getId(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "checkDup"
				, params );
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			if (rst.next()) {
				count = rst.getInt("CNT");
			}
			if(count != 0){
				checkDup = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return checkDup;
	}
	
	public List<ManageTimeOffsetSearch> searchTO(CCTConnection conn, String id, List<ManageTimeOffsetSearch> lstResult) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[8];
		params[paramIndex++] = id;
		params[paramIndex++] = id;
		params[paramIndex++] = id;
		params[paramIndex++] = id;
		params[paramIndex++] = id;
		params[paramIndex++] = id;
		params[paramIndex++] = id;
		params[paramIndex] = id; //ไม่ใช้ StringUtil.replaceSpecialString เพราะไม่ต้องการ '' ไปใน SQL

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchTO"
				, params);

		getLogger().debug("SQL : " + sql);
		
		Statement stmt = null;
		ResultSet rst = null;

		try {

			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				lstResult.get(0).setTimeOffset(StringUtil.nullToString(rst.getString("TOTAL_TIMEOFFSET_MIN")));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return lstResult;
	}
	
	@Override
	protected String createSQLUpdateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
