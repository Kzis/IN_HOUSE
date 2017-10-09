package com.cct.inhouse.timeoffset.core.report.toreport.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReport;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReportSearch;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReportSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class TOReportDAO extends AbstractDAO<TOReportSearchCriteria, TOReportSearch, Object> {

	public TOReportDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user,Locale locale) {
		super(logger, sqlPath, user, locale);
	}
	
	protected List<TOReport> searchReport(CCTConnection conn, Statement stmt, TOReportSearchCriteria criteria, CommonUser commonUser, Locale locale) throws Exception {

		String startDate;
		String endDate;

		List<TOReport> listResult = new ArrayList<TOReport>();
		
		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjConId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDepartmentId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getEmployeeId(), conn.getDbType(), ResultType.NULL);
		
		if (criteria.getStartDate() != null && !(criteria.getStartDate().isEmpty())) {
			startDate = TOUtil.convertBEToBCForInsertYYYY_MM_DD(criteria.getStartDate()) + GlobalVariableTO.SPACE_START_TIME_FULL;
		} else {
			startDate = criteria.getStartDate();
		}

		params[paramIndex++] = StringUtil.replaceSpecialString(startDate, conn.getDbType(), ResultType.NULL);

		if (criteria.getEndDate() != null && !(criteria.getStartDate().isEmpty())) {
			endDate = TOUtil.convertBEToBCForInsertYYYY_MM_DD(criteria.getEndDate()) + GlobalVariableTO.SPACE_END_TIME_FULL;
		} else {
			endDate = criteria.getEndDate();
		}

		params[paramIndex++] = StringUtil.replaceSpecialString(endDate, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(criteria.getApproveStatus(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchTOInUse"
				, params);

		getLogger().debug(sql);

		ResultSet rst = null;
		
		try {
			
			rst = stmt.executeQuery(sql);
			
			while(rst.next()){
				
				TOReport data = new TOReport();
				
				data.setDepartmentId(StringUtil.nullToString(rst.getString("DEPARTMENT_ID")));
				data.setDepartmentName(StringUtil.nullToString(rst.getString("DEPARTMENT")));
				data.setProjectABBR(StringUtil.nullToString(rst.getString("PROJECT_ABBR")));
				data.setUserId(StringUtil.nullToString(rst.getString("USER_ID")));
				data.setUserFullName(StringUtil.nullToString(rst.getString("USER_FULLNAME")));
				data.setStartDateTime(StringUtil.nullToString(rst.getString("START_DATETIME")));
				data.setEndDateTime(StringUtil.nullToString(rst.getString("END_DATETIME")));
				data.setApproveStatus(StringUtil.nullToString(rst.getString("APPROVE_STATUS")));
				data.setApproveStatusTitle(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
				data.setMinute(StringUtil.nullToString(rst.getString("mintue")));
				
				listResult.add(data);
			
			}
			
			
		} catch (Exception e) {
			throw e;
		}

		return listResult;
	}

	@Override
	protected String createSQLCountData(CCTConnection conn,TOReportSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
	
		String startDate;
		String endDate;

		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjConId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDepartmentId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getEmployeeId(), conn.getDbType(), ResultType.NULL);

		if (criteria.getStartDate() != null && !(criteria.getStartDate().isEmpty())) {
			startDate = TOUtil.convertBEToBCForInsertYYYY_MM_DD(criteria.getStartDate()) + GlobalVariableTO.SPACE_START_TIME_FULL;
		} else {
			startDate = criteria.getStartDate();
		}

		params[paramIndex++] = StringUtil.replaceSpecialString(startDate, conn.getDbType(), ResultType.NULL);

		if (criteria.getEndDate() != null && !(criteria.getStartDate().isEmpty())) {
			endDate = TOUtil.convertBEToBCForInsertYYYY_MM_DD(criteria.getEndDate()) + GlobalVariableTO.SPACE_END_TIME_FULL;
		} else {
			endDate = criteria.getEndDate();
		}

		params[paramIndex++] = StringUtil.replaceSpecialString(endDate, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(criteria.getApproveStatus(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCount"
				, params);

		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLSearch(CCTConnection conn,
			TOReportSearchCriteria criteria, CommonUser user, Locale locale)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TOReportSearch createResultSearch(ResultSet rst, CommonUser user,
			Locale locale) throws Exception {
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
