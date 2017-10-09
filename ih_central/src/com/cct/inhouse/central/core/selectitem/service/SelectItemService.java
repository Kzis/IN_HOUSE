package com.cct.inhouse.central.core.selectitem.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemObject;
import com.cct.common.CommonService;
import com.cct.common.CommonUser;
import com.cct.inhouse.central.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.core.standard.department.domain.Department;

public class SelectItemService extends CommonService {
	
	private SelectItemDAO dao = null;

	/**
	 * Constructor
	 * @param logger
	 * @param conn
	 * @param user
	 * @param locale
	 */
	public SelectItemService(Logger logger) {
		super(logger);
		this.dao = new SelectItemDAO(logger, SQLPath.SELECT_ITEM_SQL.getSqlPath());
	}
	
	/**
	 * ดึงข้อมูลเฉพาะ Global data
	 * @param language
	 * @return
	 * @throws Exception
	 */
	protected Map<String, List<CommonSelectItem>> searchGlobalDataSelectItem(CCTConnection conn, Locale locale) throws Exception {
		return getDao().searchGlobalDataSelectItem(conn, locale);
	}
		
	/**
	 * ดึงข้อมูล Select item 
	 * @param methordName
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<CommonSelectItem> searchDefaultSelectItem(CCTConnection conn, Locale locale, CommonUser user, String methordName, String term, String limit) throws Exception {
		// สามารถใช้งาน MethodUtil.invoke(arg0, arg1, arg2) ได้ด้วย
		Method method = getDao().getClass().getDeclaredMethod(methordName, CCTConnection.class, Locale.class, CommonUser.class, String.class, String.class);			
		return (List<CommonSelectItem>)method.invoke(getDao(), conn, locale, user, term, limit);
	}
	
	@SuppressWarnings("unchecked")
	protected List<CommonSelectItemObject<?>> searchSelectObjectItem(CCTConnection conn, Locale locale, CommonUser user, String methordName, String term, String limit) throws Exception {
		// สามารถใช้งาน MethodUtil.invoke(arg0, arg1, arg2) ได้ด้วย
		Method method = getDao().getClass().getDeclaredMethod(methordName, CCTConnection.class, Locale.class, CommonUser.class, String.class, String.class);			
		return (List<CommonSelectItemObject<?>>)method.invoke(getDao(), conn, locale, user, term, limit);
	}
	
	/**
	 * Combo ชื่อ-สกุล พนักงาน (filter ตาม ฝ่าย/แผนก)
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchEmployeeFullnameByDepartmentId(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			String departmentId = null;
			if (obj != null) {
				Department department = (Department)obj;
				departmentId = department.getDepartmentId();
				
				getLogger().debug("Department Id : " + department.getDepartmentId() );
				getLogger().debug("Department Code : "	+ department.getDepartmentCode());
				getLogger().debug("Department Desc : "	+ department.getDepartmentDesc());
				getLogger().debug("Department Name : "	+ department.getDepartmentName());
			}
			
			lstResult = getDao().searchEmployeeFullnameByDepartmentId(conn, locale, user, term, limit, departmentId);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	/**
	 * Combo แผนก (ส่ง id เข้าไป where in)s
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchDepartmentSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			String departmentId = null;
			if (obj != null) {
				departmentId = (String)obj;
			}
			getLogger().debug("Department Id : " + departmentId);
			
			lstResult = getDao().searchDepartmentSelectItem(conn, locale, user, term, limit, departmentId);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	/**
	 * Combo เงื่อนไขการจัดการเวลาชดเชย
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItemObject<?>> searchTimeoffsetConditionByProjectId(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItemObject<?>> lstResult = new ArrayList<CommonSelectItemObject<?>>();
		try {
			String projectId = (String)obj;
			getLogger().debug("Project Id : " + projectId);
			
			lstResult = getDao().searchTimeoffsetConditionByProjectId(conn, locale, user, term, limit, projectId);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	public SelectItemDAO getDao() {
		return dao;
	}
	
	/**
	 * Search department select item by department id
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param Department
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItemObject<?>> searchDepartmentSelectItemObj(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItemObject<?>> lstResult = new ArrayList<CommonSelectItemObject<?>>();
		try {
			String departmentId = null;
			if (obj != null) {
				departmentId = (String)obj;
			}
			getLogger().debug("Department Id : " + departmentId);
			
			lstResult = getDao().searchDepartmentSelectItemObj(conn, locale, user, term, limit, departmentId);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	/** 
	 * Autocomplete ตำแหน่ง 
	 * 
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchPositionsSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			String departmentId = null;
			if (obj != null) {
				Department department = (Department)obj;
				departmentId = department.getDepartmentId();
			}
			getLogger().debug("Department Id : " + departmentId);
			
			lstResult = getDao().searchPositionsSelectItem(conn, locale, user, term, limit, departmentId);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	protected List<CommonSelectItem> searchScenarioSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			String systemId = null;
			if (obj != null) {
				systemId = (String)obj;
			}
			getLogger().debug("System Id : " + systemId);
			
			lstResult = getDao().searchScenarioSelectItem(conn, locale, user, term, limit, systemId);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	/** 
	 * ค้นหาข้อมูลระบบ (system)
	 * 
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchSystemsSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			String projectId = null;
			if (obj != null) {
				projectId = (String)obj;
			}
			getLogger().debug("Project Id : " + projectId);
			
			lstResult = getDao().searchSystemsSelectItem(conn, locale, user, term, limit, projectId);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	/** 
	 * ค้นหาระบบย่อย (sub system)
	 * 
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchSubSystemsSelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			String systemId = null;
			if (obj != null) {
				systemId = (String)obj;
			}
			getLogger().debug("System Id : " + systemId);
			
			lstResult = getDao().searchSubSystemsSelectItem(conn, locale, user, term, limit, systemId);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	/**
	 * Combo ผู้ใช้งานระบบ [SD-Suraphong Amonphornwitthaya (AE)]
	 * @param conn
	 * @param locale
	 * @param user
	 * @param term
	 * @param limit
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchUserFullDisplaySelectItem(CCTConnection conn, Locale locale, CommonUser user, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			String departmentId = null;
			if (obj != null) {
				departmentId = (String)obj;
			}
			getLogger().debug("Department Id : " + departmentId);
			
			lstResult = getDao().searchUserFullDisplaySelectItem(conn, locale, user, term, limit, departmentId);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
}
