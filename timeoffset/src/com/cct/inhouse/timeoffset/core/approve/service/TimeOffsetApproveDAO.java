package com.cct.inhouse.timeoffset.core.approve.service;

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
import com.cct.inhouse.timeoffset.core.approve.domain.Approve;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveSearch;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveSearchCriteria;
import com.cct.inhouse.timeoffset.core.approve.domain.Detail;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.util.TOUtil;

public class TimeOffsetApproveDAO extends AbstractDAO<ApproveSearchCriteria, ApproveSearch, Approve> {

	public TimeOffsetApproveDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	@Override
	protected ApproveSearch createResultSearch(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		ApproveSearch data = new ApproveSearch();
		data.setId(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID")));
		data.setProjectAbbr(StringUtil.nullToString(rst.getString("PROJECT_ABBR")));
		data.setProjConDetail(StringUtil.nullToString(rst.getString("PROJ_CON_DETAIL")));
		data.setFullName(StringUtil.nullToString(rst.getString("USER_FULLNAME")));
		data.setDay(StringUtil.nullToString(rst.getString("DAY")));
		data.setHour(StringUtil.nullToString(rst.getString("HOUR")));
		data.setMinute(StringUtil.nullToString(rst.getString("MINUTE")));
		data.setApproveUser(StringUtil.nullToString(rst.getString("APPROVE_USER")));
		data.setApproveStatusDesc(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
		data.setProcessStatus(StringUtil.nullToString(rst.getString("PROCESS_STATUS")));
		
		return data;
	}

	@Override
	protected Approve createResultSearchById(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		Approve data = new Approve();
		List<Detail> listDetail = new ArrayList<Detail>();
		
		data.setId(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID")));
		data.setFullName(StringUtil.nullToString(rst.getString("USER_FULLNAME")));
		
		Detail obj = new Detail();
		obj.setId(StringUtil.nullToString(rst.getString("TIMEOFFSET_DET_ID")));
		obj.setTimeOffsetId(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID")));
		obj.setTimeOffsetDetId(StringUtil.nullToString(rst.getString("TIMEOFFSET_DET_ID")));
		obj.setWorkDetail(StringUtil.nullToString(rst.getString("DETAIL_WORK")));
		obj.setStartDateTime(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))));
		obj.setEndDateTime(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))));
		obj.setDay(StringUtil.nullToString(rst.getString("DAY")));
		obj.setHour(StringUtil.nullToString(rst.getString("HOUR")));
		obj.setMinute(StringUtil.nullToString(rst.getString("MINUTE")));
		obj.setRemark(StringUtil.nullToString(rst.getString("REMARK")));
		obj.setApproveStatus(StringUtil.nullToString(rst.getString("APPROVE_STATUS")));
		obj.setApproveStatusDesc(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
		obj.setRownum(String.valueOf(1));
		listDetail.add(obj);
		
		data.setApproveStatus(StringUtil.nullToString(rst.getString("APPROVE_STATUS"))); // W = wait , A = approve , D = disApprove
		data.setRemark(StringUtil.nullToString(rst.getString("REMARK")));
		data.setApproveUser(StringUtil.nullToString(rst.getString("APPROVE_USER_FULLNAME")));
		data.setApproveDate(TOUtil.convertDateFromYYYYMMDDToDDslMMslYYYY(StringUtil.nullToString(rst.getString("APPROVE_DATE"))));
		data.setApproveStatusDesc(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
		data.setProjectABBR(StringUtil.nullToString(rst.getString("PROJECT_ABBR")));
		data.setProjectConDetail(StringUtil.nullToString(rst.getString("PROJ_CON_DETAIL")));
		data.setListDetail(listDetail);
		
		return data;
	}

	@Override
	protected String createSQLCountData(CCTConnection conn, ApproveSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDepartmentId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getEmployeeId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjConId(), conn.getDbType(), ResultType.NULL);
		
		if(criteria.getStartDate() != null && !(criteria.getStartDate().isEmpty())
				&& criteria.getEndDate() != null && !(criteria.getEndDate().isEmpty())){
			
			criteria.setStartDate(TOUtil.convertBEToBC(criteria.getStartDate()));
			criteria.setEndDate(TOUtil.convertBEToBC(criteria.getEndDate()));
			
			params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()) + GlobalVariableTO.SPACE_START_TIME_FULL, conn.getDbType(), ResultType.NULL);
			params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()) + GlobalVariableTO.SPACE_END_TIME_FULL , conn.getDbType(), ResultType.NULL);
		}else{
			params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()), conn.getDbType(), ResultType.NULL);
			params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()), conn.getDbType(), ResultType.NULL);
		}
		
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
	protected String createSQLSearch(CCTConnection conn, ApproveSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[10];		
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDepartmentId(), conn.getDbType(), ResultType.NULL); 
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getEmployeeId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjConId(), conn.getDbType(), ResultType.NULL);
		
		if(criteria.getStartDate() != null && !(criteria.getStartDate().isEmpty())
				&& criteria.getEndDate() != null && !(criteria.getEndDate().isEmpty())){
					
			params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()) + GlobalVariableTO.SPACE_START_TIME_FULL, conn.getDbType(), ResultType.NULL);
			params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()) + GlobalVariableTO.SPACE_END_TIME_FULL, conn.getDbType(), ResultType.NULL);
		}else{
			params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()), conn.getDbType(), ResultType.NULL);
			params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()), conn.getDbType(), ResultType.NULL);
		}
		
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProcessStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = criteria.getOrderList();
		
		params[paramIndex++] = criteria.getStart()-1;
		params[paramIndex] = criteria.getLinePerPage();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchApprove"
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
				, "searchApproveById"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	protected int editMaster(CCTConnection conn, String id, String processStatus) throws Exception {
		
		int paramIndex = 0;
		
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getId() , conn.getDbType() , ResultType.NULL); //UPDATE_USER
		params[paramIndex++] = StringUtil.replaceSpecialString(processStatus , conn.getDbType() , ResultType.NULL);
		params[paramIndex] = id;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateTOMaster"
				, params);
		
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
		return 0 ;
	}
	
	protected int edit(CCTConnection conn, Approve obj, int i) throws Exception {
		
		int paramIndex = 0;
		
		Object[] params = new Object[11];
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD_HHMMSS(obj.getListDetail().get(i).getStartDateTime()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD_HHMMSS(obj.getListDetail().get(i).getEndDateTime()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getListDetail().get(i).getDay(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getListDetail().get(i).getHour(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getListDetail().get(i).getMinute(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getListDetail().get(i).getRemark(), conn.getDbType(), ResultType.NULL);	
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getListDetail().get(i).getApproveStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getRemark(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(obj.getListDetail().get(i).getTimeOffsetDetId(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateTODetail"
				, params);
		
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
		return 0 ;
	}

	protected Approve searchByIdCustom(CCTConnection conn, String id) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchApproveById"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		
		Approve data = new Approve();
		List<Detail> listDetail = new ArrayList<Detail>();
		
		int i = 1;
		
		try {
			
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while(rst.next()){
				
				data.setId(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID")));
				data.setFullName(StringUtil.nullToString(rst.getString("USER_FULLNAME")));
				
				Detail obj = new Detail();
				obj.setId(StringUtil.nullToString(rst.getString("TIMEOFFSET_DET_ID")));
				obj.setTimeOffsetId(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID")));
				obj.setTimeOffsetDetId(StringUtil.nullToString(rst.getString("TIMEOFFSET_DET_ID")));
				obj.setWorkDetail(StringUtil.nullToString(rst.getString("DETAIL_WORK")));
				obj.setStartDateTime(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))));
				obj.setEndDateTime(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))));
				obj.setDay(StringUtil.nullToString(rst.getString("DAY").split("\\.")[0]));
				obj.setHour(StringUtil.nullToString(rst.getString("HOUR").split("\\.")[0]));
				obj.setMinute(StringUtil.nullToString(rst.getString("MINUTE").split("\\.")[0]));
				obj.setRemark(StringUtil.nullToString(rst.getString("REMARK")));
				obj.setApproveStatus(StringUtil.nullToString(rst.getString("APPROVE_STATUS")));
				obj.setApproveStatusDesc(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
				obj.setRownum(String.valueOf(i));
				listDetail.add(obj);
				
				data.setApproveStatus(StringUtil.nullToString(rst.getString("APPROVE_STATUS"))); // W = wait , A = approve , D = disApprove
				data.setRemark(StringUtil.nullToString(rst.getString("APPROVE_REMARK")));
				data.setApproveUser(StringUtil.nullToString(rst.getString("APPROVE_USER_FULLNAME")));
				data.setApproveDate(TOUtil.convertDateFromYYYYMMDDToDDslMMslYYYY(StringUtil.nullToString(rst.getString("APPROVE_DATE"))));
				data.setApproveStatusDesc(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
				data.setProjectABBR(StringUtil.nullToString(rst.getString("PROJECT_ABBR")));
				data.setProjectConDetail(StringUtil.nullToString(rst.getString("PROJ_CON_DETAIL")));
				
				i++;
			}
			
			data.setListDetail(listDetail);
			
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return data;
	}
	
	@Override
	protected String createSQLAdd(CCTConnection conn, Approve obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLCheckDup(CCTConnection conn, Approve obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected String createSQLUpdateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected String createSQLDelete(CCTConnection conn, String id, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLEdit(CCTConnection conn, Approve obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
		
}
