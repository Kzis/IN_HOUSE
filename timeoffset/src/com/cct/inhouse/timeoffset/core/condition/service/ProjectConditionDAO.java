package com.cct.inhouse.timeoffset.core.condition.service;

import java.sql.ResultSet;
import java.util.Locale;

import org.apache.log4j.Logger;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectCondition;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectConditionSearch;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectConditionSearchCriteria;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.util.TOUtil;

public class ProjectConditionDAO extends AbstractDAO<ProjectConditionSearchCriteria, ProjectConditionSearch, ProjectCondition> {

	public ProjectConditionDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	@Override
	protected ProjectConditionSearch createResultSearch(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		ProjectConditionSearch data = new ProjectConditionSearch();
		data.setId(StringUtil.nullToString(rst.getString("PROJ_CON_ID")));
		data.setProjectAbbr(StringUtil.nullToString(rst.getString("PROJECT_ABBR")));
		data.setProjConDetail(StringUtil.nullToString(rst.getString("PROJ_CON_DETAIL")));
		
		if(StringUtil.nullToString(rst.getString("HOUR_TOT")).equals("0") 
				|| StringUtil.nullToString(rst.getString("HOUR_TOT")).equals("0.0") 
				|| StringUtil.nullToString(rst.getString("HOUR_TOT")).equals("")){
			data.setHourTot("-");
		}else{
			data.setHourTot(StringUtil.nullToString(TOUtil.formatDouble(rst.getString("HOUR_TOT"), 1)));
		}
		
		data.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));
		data.getActive().setDesc(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));
		
		return data;
	}
	
	@Override
	protected String createSQLSearch(CCTConnection conn, ProjectConditionSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getActiveStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = criteria.getOrderList();
		
		params[paramIndex++] = criteria.getStart()-1;
		params[paramIndex] = criteria.getLinePerPage();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchProjectCondition"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected ProjectCondition createResultSearchById(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		ProjectCondition data = new ProjectCondition();
		data.setId(rst.getString("PROJ_CON_ID"));
		data.setProjectId(rst.getString("PROJECT_ID"));
		data.setProjConDetail(rst.getString("PROJ_CON_DETAIL"));
		data.setProjConFlag(rst.getString("PROJ_CON_FLAG"));
		data.setHourTot(rst.getString("HOUR_TOT"));
		data.getActive().setCode(rst.getString("ACTIVE"));
		
		return data;
	}

	@Override
	protected String createSQLAdd(CCTConnection conn, ProjectCondition obj, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjConDetail(), conn.getDbType(), ResultType.NULL);
		
		if(obj.getProjConFlag().equals(GlobalVariableTO.TRUE_STATUS)){
			params[paramIndex++] = StringUtil.replaceSpecialString(GlobalVariableTO.ACTIVE, conn.getDbType(), ResultType.NULL);
		}else{
			params[paramIndex++] = StringUtil.replaceSpecialString(GlobalVariableTO.INACTIVE, conn.getDbType(), ResultType.NULL);
		}
		
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getHourTot(), conn.getDbType(), ResultType.NULL);
		
		if(obj.getActive().getCode().equals(GlobalVariableTO.TRUE_STATUS)){
			params[paramIndex++] = StringUtil.replaceSpecialString(GlobalVariableTO.ACTIVE, conn.getDbType(), ResultType.NULL);
		}else{
			params[paramIndex++] = StringUtil.replaceSpecialString(GlobalVariableTO.INACTIVE, conn.getDbType(), ResultType.NULL);
		}
		
		params[paramIndex] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);
				
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertProjectCondition"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLCheckDup(CCTConnection conn, ProjectCondition obj, CommonUser user, Locale locale) throws Exception {

		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjConDetail(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getId(), conn.getDbType(), ResultType.NULL); //PROJ_CON_ID
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "checkDup"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
		
	}

	@Override
	protected String createSQLCountData(CCTConnection conn, ProjectConditionSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProjectId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(criteria.getActiveStatus(), conn.getDbType(), ResultType.NULL);
		
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
		return null;
	}

	@Override
	protected String createSQLEdit(CCTConnection conn, ProjectCondition obj, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[6];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getProjConDetail(), conn.getDbType(), ResultType.NULL);
		
		if(obj.getProjConFlag().equals(GlobalVariableTO.TRUE_STATUS)){
			params[paramIndex++] = StringUtil.replaceSpecialString(GlobalVariableTO.ACTIVE, conn.getDbType(), ResultType.NULL);
			params[paramIndex++] = StringUtil.replaceSpecialString(obj.getHourTot(), conn.getDbType(), ResultType.NULL);
		}else{
			params[paramIndex++] = StringUtil.replaceSpecialString(GlobalVariableTO.INACTIVE, conn.getDbType(), ResultType.NULL);
			params[paramIndex++] = StringUtil.replaceSpecialString("0.0", conn.getDbType(), ResultType.NULL);

		}
		
		if(obj.getActive().getCode().equals(GlobalVariableTO.TRUE_STATUS)){
			params[paramIndex++] = StringUtil.replaceSpecialString(GlobalVariableTO.ACTIVE, conn.getDbType(), ResultType.NULL);
		}else{
			params[paramIndex++] = StringUtil.replaceSpecialString(GlobalVariableTO.INACTIVE, conn.getDbType(), ResultType.NULL);
		}
		
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(obj.getId(), conn.getDbType(), ResultType.NULL);
			
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "editProjectCondition"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLSearchById(CCTConnection conn, String id, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = TOUtil.convertLongValue(id);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchProjectConditionById"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLUpdateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		
		params[paramIndex++] = StringUtil.replaceSpecialString(activeFlag, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = ids;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "setActiveStatus"
				, params);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

}
