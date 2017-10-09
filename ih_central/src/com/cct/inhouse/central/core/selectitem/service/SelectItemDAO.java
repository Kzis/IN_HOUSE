package com.cct.inhouse.central.core.selectitem.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractSelectItemDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemObject;
import com.cct.common.CommonUser;
import com.cct.inhouse.central.core.config.parameter.domain.CentralSQLPath;
import com.cct.inhouse.core.security.user.domain.User;
import com.cct.inhouse.core.standard.department.domain.Department;
import com.cct.inhouse.core.timeoffset.domain.TimeOffsetCondition;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class SelectItemDAO extends AbstractSelectItemDAO {
	
	/**
	 * SelectItem Constructor
	 * @param logger
	 * @param conn
	 * @param sqlPath
	 * @param locale
	 */
	public SelectItemDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	/**
	 * Search global data select item
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected Map<String, List<CommonSelectItem>> searchGlobalDataSelectItem(CCTConnection conn, Locale locale) throws Exception {		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = locale.getLanguage();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchGlobalDataSelectItem"
				, params);
		
		return searchGlobalDataSelectItem(conn, sql, "GLOBAL_TYPE_CODE", "GLOBAL_DATA_CODE", "GLOBAL_DATA_VALUE");
	}
	
	/**
	 * Search department select item by department id
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItemObject<?>> searchDepartmentSelectItemObj(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, String departmentId) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = departmentId;
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchDepartmentSelectItem"
				, params);
		
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "DEPARTMENT_ID", "DEPARTMENT_NAME", Department.class);
	}
	
	/**
	 * Combo ผู้ใช้งานระบบ [SD-Suraphong Amonphornwitthaya (AE)]
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchUserFullDisplaySelectItem(CCTConnection conn, Locale locale, CommonUser user
			, String term, String limit, String departmentId) throws Exception {			
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = departmentId;
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchUserFullDisplaySelectItem"
				, params);
		
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "USER_ID", "FULL_NAME");
	}
	
	/**
	 * Combo โครงการ (project)
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchProjectsSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(limit);
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchProjectsSelectItem"
				, params);
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "PROJECT_ID", "PROJECT_NAME");
	}
	
	
	/**
	 * Combo ชื่อ-สกุล พนักงาน (filter ตาม ฝ่าย/แผนก)
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchEmployeeFullnameByDepartmentId(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, String departmentId) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.stringToLong(departmentId);
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchEmployeeFullnameByDepartmentId"
				, params);
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "USER_ID", "USER_FULLNAME");
	}
	
	/**
	 * Combo เงื่อนไขการจัดการเวลาชดเชย
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItemObject<?>> searchTimeoffsetConditionByProjectId(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, String projectId) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = projectId;
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchTimeoffsetConditionByProjectId"
				, params);
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "PROJ_CON_ID", "PROJ_CON_DETAIL", TimeOffsetCondition.class);
	}

	@Override
	public CentralSQLPath getSqlPath() {
		return (CentralSQLPath)super.getSqlPath();
	}
	
	/**
	 * Search department select item by department id
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchDepartmentSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, String departmentId) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = departmentId;
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchDepartmentSelectItem"
				, params);
		
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "DEPARTMENT_ID", "DEPARTMENT_NAME");
	}
	
	/**
	 * ค้นหาผู้ใช้ทั้งหมด โดยแสดงในรูปแบบ ชื่อจริง (ชื่อเล่น)
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItemObject<?>> searchAllUserDisplayNameAndNicknameSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchAllUserDisplayNameAndNickname"
				, params);
		
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "USER_ID", "BOOKING_NAME", User.class);
	}
	
	/** 
	 *  ค้นหาข้อมูลระบบ (system)
	 *  
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchSystemsSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, String projectId) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = projectId;
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchSystemsSelectItem"
				, params);
		
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "SYSTEM_ID", "SYSTEM_NAME");
	}
	
	/** 
	 * ค้นหาระบบย่อย (sub system)
	 * 
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchSubSystemsSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, String projectId) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = projectId;
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchSubSystemsSelectItem"
				, params);
		
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "SUB_SYSTEM_ID", "SUB_SYSTEM_NAME");
	}
	
	/** 
	 * ตำแหน่ง
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchPositionsSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, String departmentId) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = departmentId;
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchPositionsSelectItem"
				, params);
		
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "DEPARTMENT_ID", "DEPARTMENT_CODE");
	}
	
	/**
	 * ค้นหา scenario (scenario)
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchScenarioSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, String systemId) throws Exception {			
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = systemId;
		params[paramIndex++] = StringUtil.replaceSpecialString(term, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.stringToLong(limit);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath()
				, "searchScenarioSelectItem"
				, params);
		
		getLogger().debug(sql);
		return searchDataSelectItem(conn, sql, "SCENARIO_ID", "SCENARIO_NAME");
	}
}
