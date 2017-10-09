package com.cct.inhouse.timeoffset.core.report.inuse.general.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.report.inuse.general.domain.InuseReportSearch;
import com.cct.inhouse.timeoffset.core.report.inuse.general.domain.InuseReportSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class InuseReportDAO extends AbstractDAO<InuseReportSearchCriteria, InuseReportSearch, Object> {

	public InuseReportDAO(Logger logger, CommonSQLPath sqlPath,CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	protected ResultSet searchReport(CCTConnection conn, Statement stmt, InuseReportSearchCriteria criteria, CommonUser commonUser, Locale locale) throws Exception {

		String startDate;
		String endDate;

		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(commonUser.getId(), conn.getDbType(), ResultType.NULL);

		if (criteria.getStartDate() != null && !(criteria.getStartDate().isEmpty())) {
			startDate = TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()) + GlobalVariableTO.SPACE_START_TIME_FULL;
		} else {
			startDate = criteria.getStartDate();
		}

		params[paramIndex++] = StringUtil.replaceSpecialString(startDate, conn.getDbType(), ResultType.NULL);

		if (criteria.getEndDate() != null && !(criteria.getStartDate().isEmpty())) {
			endDate = TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()) + GlobalVariableTO.SPACE_END_TIME_FULL;
		} else {
			endDate = criteria.getEndDate();
		}

		params[paramIndex++] = StringUtil.replaceSpecialString(endDate, conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchGeneral"
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
	protected String createSQLCountData(CCTConnection conn,InuseReportSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {

		String startDate;
		String endDate;

		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);

		if (criteria.getStartDate() != null && !(criteria.getStartDate().isEmpty())) {
			startDate = TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getStartDate()) + GlobalVariableTO.SPACE_START_TIME_FULL;
		} else {
			startDate = criteria.getStartDate();
		}

		params[paramIndex++] = StringUtil.replaceSpecialString(startDate, conn.getDbType(), ResultType.NULL);

		if (criteria.getEndDate() != null && !(criteria.getStartDate().isEmpty())) {
			endDate = TOUtil.convertDateTimeForInsertYYYY_MM_DD(criteria.getEndDate()) + GlobalVariableTO.SPACE_END_TIME_FULL;
		} else {
			endDate = criteria.getEndDate();
		}

		params[paramIndex++] = StringUtil.replaceSpecialString(endDate, conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCountGeneral"
				, params);

		getLogger().debug("SQL : " + sql);
	
		return sql;
	}

	@Override
	protected String createSQLSearch(CCTConnection conn,
			InuseReportSearchCriteria criteria, CommonUser user, Locale locale)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected InuseReportSearch createResultSearch(ResultSet rst,
			CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
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
