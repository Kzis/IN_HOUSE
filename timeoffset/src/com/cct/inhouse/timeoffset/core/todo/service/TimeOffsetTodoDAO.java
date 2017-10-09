package com.cct.inhouse.timeoffset.core.todo.service;

import java.sql.ResultSet;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.todo.domain.Todo;
import com.cct.inhouse.timeoffset.core.todo.domain.TodoSearch;
import com.cct.inhouse.timeoffset.core.todo.domain.TodoSearchCriteria;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class TimeOffsetTodoDAO extends AbstractDAO<TodoSearchCriteria, TodoSearch, Todo> {

	public TimeOffsetTodoDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	@Override
	protected TodoSearch createResultSearch(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		
		TodoSearch data = new TodoSearch();
		data.setTimeOffsetID(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID"))); //MASTER_ID
		data.setId(StringUtil.nullToString(rst.getString("TIMEOFFSET_ID")));
		data.setProjectABBR(StringUtil.nullToString(rst.getString("PROJECT_ABBR")));
		data.setProjectConditionDETAIL(StringUtil.nullToString(rst.getString("PROJ_CON_DETAIL")));
		data.setFullName(StringUtil.nullToString(rst.getString("USER_FULLNAME")));
		data.setDay(StringUtil.nullToString(rst.getString("DAY").split("\\.")[0]));
		data.setHour(StringUtil.nullToString(rst.getString("HOUR").split("\\.")[0]));
		data.setMinute(StringUtil.nullToString(rst.getString("MINUTE").split("\\.")[0]));
		
		return data;
	}

	@Override
	protected String createSQLCountData(CCTConnection conn, TodoSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCount"
				);
		
		getLogger().debug("SQL : " + sql);
		
		return sql;
	}

	@Override
	protected String createSQLSearch(CCTConnection conn, TodoSearchCriteria criteria, CommonUser user, Locale locale) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		
		params[paramIndex] = criteria.getOrderList();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchTODO"
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

	@Override
	protected Todo createResultSearchById(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLAdd(CCTConnection conn, Todo obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLCheckDup(CCTConnection conn, Todo obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected String createSQLSearchById(CCTConnection conn, String id, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected String createSQLDelete(CCTConnection conn, String ids, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createSQLEdit(CCTConnection conn, Todo obj, CommonUser user, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
