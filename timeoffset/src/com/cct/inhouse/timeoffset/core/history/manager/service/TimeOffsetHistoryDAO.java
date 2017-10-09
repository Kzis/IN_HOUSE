package com.cct.inhouse.timeoffset.core.history.manager.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

import org.apache.log4j.Logger;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.history.manager.domain.HistorySearch;
import com.cct.inhouse.timeoffset.core.history.manager.domain.HistorySearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;

public class TimeOffsetHistoryDAO extends AbstractDAO<HistorySearchCriteria, HistorySearch, Object> {

	public TimeOffsetHistoryDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	@Override
	protected HistorySearch createResultSearch(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		HistorySearch data = new HistorySearch();
		String[] newTime = new String[3];

		data.setId(StringUtil.nullToString(rst.getString("ONSITEID")));
		data.setEmployeeName(StringUtil.nullToString(rst.getString("FULLNAME")));
		data.setOnsiteDateTimeFrom1(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("START_DATETIME")) + GlobalVariableTO.START_TIME_MS).substring(0, 16));
		data.setOnsiteDateTimeTo2(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("END_DATETIME")) + GlobalVariableTO.START_TIME_MS).substring(0, 16));
		data.setSiteServiceRemark(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")));

		// Process compute day hour minute from hrm
		newTime = TOUtil.computeTimeHRM(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")), StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));
		data.setTotalOnsiteDay(newTime[0]);
		data.setTotalOnsiteHour(newTime[1]);
		data.setTotalOnsiteMinute(newTime[2]);

		data.setOnsiteStatus(StringUtil.nullToString(rst.getString("ONSITESTATUS")));
		
		return data;
	}

	@Override
	protected String createSQLCountData(CCTConnection conn, HistorySearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getEmployeeId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDepartmentId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(criteria.getApproveStatus(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCountManager"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLSearch(CCTConnection conn, HistorySearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getEmployeeId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDepartmentId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getApproveStatus(), conn.getDbType(), ResultType.NULL);

		params[paramIndex++] = criteria.getStart() - 1;
		params[paramIndex] = (criteria.getLinePerPage());

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchHistoryManager"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	protected ResultSet searchReport(CCTConnection conn, Statement stmt, HistorySearchCriteria criteria) throws Exception {

		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getEmployeeId(), conn.getDbType(), ResultType.NULL); // USER_ID: Get จาก Session Login
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDepartmentId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getApproveStatus(), conn.getDbType(), ResultType.NULL);

		params[paramIndex++] = null;
		params[paramIndex] = null;

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchHistoryManager"
				, params);
		
		getLogger().debug(sql);

		ResultSet resultSet = null;
		try {
			resultSet = stmt.executeQuery(sql);
		} catch (Exception e) {
			throw e;
		}

		return resultSet;
	}
	
	@Override
	protected Object createResultSearchById(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLAdd(CCTConnection conn, Object obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLCheckDup(CCTConnection conn, Object obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLDelete(CCTConnection conn, String ids, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLEdit(CCTConnection conn, Object obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String createSQLSearchById(CCTConnection conn, String id, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLUpdateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
