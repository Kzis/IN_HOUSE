package com.cct.inhouse.timeoffset.core.history.general.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.history.general.domain.HistoryGeneralSearch;
import com.cct.inhouse.timeoffset.core.history.general.domain.HistoryGeneralSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class TimeOffsetHistoryGeneralDAO extends AbstractDAO<HistoryGeneralSearchCriteria, HistoryGeneralSearch, Object> {

	public TimeOffsetHistoryGeneralDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	@Override
	protected HistoryGeneralSearch createResultSearch(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		String[] newTime = new String[3];
		HistoryGeneralSearch data = new HistoryGeneralSearch();

		data = new HistoryGeneralSearch();
		data.setId(StringUtil.nullToString(rst.getString("ONSITEID")));
		data.setOnsiteDateTimeFrom1(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("START_DATETIME")) + GlobalVariableTO.START_TIME_MS).substring(0, 16));
		data.setOnsiteDateTimeTo2(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("END_DATETIME")) + GlobalVariableTO.START_TIME_MS).substring(0, 16));
		data.setSiteServiceRemark(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")));

		// Process compute day hour minute from hrm
		newTime = TOUtil.computeTimeHRM(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")), StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));
		data.setTotalOnsiteDay(newTime[0]);
		data.setTotalOnsiteHour(newTime[1]);
		data.setTotalOnsiteMinute(newTime[2]);

		data.setOnsiteStatus(StringUtil.nullToString(rst.getString("ONSITESTATUS")));
		data.setOnsiteStatusDesc(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
		
		return data;
	}

	@Override
	protected String createSQLCountData(CCTConnection conn, HistoryGeneralSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(criteria.getApproveStatus(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchCountGeneral", params);
		
		return sql;
	}

	@Override
	protected String createSQLSearch(CCTConnection conn, HistoryGeneralSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[6];
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL); //user จาก session 
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getApproveStatus(), conn.getDbType(), ResultType.NULL);

		params[paramIndex++] = criteria.getStart() - 1;
		params[paramIndex] = (criteria.getLinePerPage());

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchHistoryGeneral"
				, params);
		
		return sql;
	}

	protected ResultSet searchReport(CCTConnection conn, Statement stmt, HistoryGeneralSearchCriteria criteria) throws Exception {

		int paramIndex = 0;
		Object[] params = new Object[6];
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getId(), conn.getDbType(), ResultType.NULL); //user จาก session 
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getApproveStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = null;
		params[paramIndex] = null;

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchHistoryGeneral"
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
	protected String createSQLSearchById(CCTConnection conn, String id, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLUpdateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user, Locale locale) throws Exception {
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
	
}
