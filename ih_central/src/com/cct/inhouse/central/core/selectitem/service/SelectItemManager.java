package com.cct.inhouse.central.core.selectitem.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.common.CommonManager;
import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemObject;
import com.cct.common.CommonUser;
import com.cct.inhouse.central.core.config.parameter.domain.CParameterConfig;
import com.cct.inhouse.central.rmi.domain.SelectItemMethod;
import com.cct.inhouse.central.util.log.LogUtil;
import com.cct.inhouse.core.config.parameter.domain.Language;

public class SelectItemManager extends CommonManager {
	
	private static Map<Locale, Map<String, List<CommonSelectItem>>> mapGlobalData = new HashMap<Locale, Map<String, List<CommonSelectItem>>>();

	private SelectItemService service = null;
	
	/**
	 * Constructor
	 * @param logger
	 * @param conn
	 * @param user
	 * @param locale
	 */
	public SelectItemManager(Logger logger) {		
		super(logger);
		this.service = new SelectItemService(logger);
	}

	/**
	 * กำหนดค่าเริ่มต้นให้กับ Combo ที่ไม่มีการเปลื่ยนบ่อย
	 *
	 * @throws Exception
	 */
	public void init(CCTConnection conn) throws Exception {
		try {
			initGlobalDataSelectItem(conn);
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void initGlobalDataSelectItem(CCTConnection conn) throws Exception {
		// Loop for config language
		for (Language language : CParameterConfig.getParameter().getApplication().getSupportLanguageList()) {
			
			// Get global data by language
			Map<String, List<CommonSelectItem>> mapSelectItem = getService().searchGlobalDataSelectItem(conn, language.getLocale());
			if (mapSelectItem.size() == 0) {
				mapSelectItem = mapGlobalData.get(CParameterConfig.getParameter().getApplication().getApplicationLocale());
			}
			
			// Keep to Map
			mapGlobalData.put(language.getLocale(), mapSelectItem);
		}
	}
	
	public Map<Locale, Map<String, List<CommonSelectItem>>> loadGlobalData(CCTConnection conn) throws Exception {
		
		Map<Locale, Map<String, List<CommonSelectItem>>> mapGlobalData = new HashMap<Locale, Map<String, List<CommonSelectItem>>>();
		
		// Loop for config language
		for (Language language : CParameterConfig.getParameter().getApplication().getSupportLanguageList()) {
					
			// Get global data by language
			Map<String, List<CommonSelectItem>> mapSelectItem = getService().searchGlobalDataSelectItem(conn, language.getLocale());
			if (mapSelectItem.size() == 0) {
				mapSelectItem = mapGlobalData.get(CParameterConfig.getParameter().getApplication().getApplicationLocale());
			}
					
			// Keep to Map
			mapGlobalData.put(language.getLocale(), mapSelectItem);
		}
				
		return mapGlobalData;
	}
		
	public List<CommonSelectItem> searchDefaultSelectItem(CCTConnection conn, Locale locale, CommonUser user, SelectItemMethod methord, String term, String limit) throws Exception {
		return getService().searchDefaultSelectItem(conn, locale, user, methord.getMethod(), term, limit);
	}

	public List<CommonSelectItemObject<?>> searchSelectObjectItem(CCTConnection conn, Locale locale, CommonUser user, SelectItemMethod methord, String term, String limit) throws Exception {
		return getService().searchSelectObjectItem(conn, locale, user, methord.getMethod(), term, limit);
	}
	
	public SelectItemService getService() {
		return service;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommonSelectItem> searchCustomSelectitem(CCTConnection conn, Locale locale, CommonUser user, SelectItemMethod methodName, String term, String limit, Object obj) throws Exception {
		LogUtil.SELECTOR.debug("searchCustomSelectitem");
		
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			getLogger().debug("========================= searchCustomSelectitem =========================");
			getLogger().debug(methodName.getMethod());
			getLogger().debug(user);
			getLogger().debug(locale);
			getLogger().debug(term);
			getLogger().debug(limit);
			getLogger().debug("========================= searchCustomSelectitem =========================");
			
				
			SelectItemService service = new SelectItemService(getLogger());
			Method method = service.getClass().getDeclaredMethod(methodName.getMethod(), CCTConnection.class
					, Locale.class, CommonUser.class, String.class, String.class, Object.class);
			
			lstResult = (List<CommonSelectItem>) method.invoke(service, conn, locale, user, term, limit, obj);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommonSelectItemObject<?>> searchCustomSelectItemObject(CCTConnection conn, Locale locale, CommonUser user, SelectItemMethod methodName, String term, String limit, Object obj) throws Exception {
		LogUtil.SELECTOR.debug("searchCustomSelectItemObject");
		
		List<CommonSelectItemObject<?>> lstResult = new ArrayList<CommonSelectItemObject<?>>();
		try {
			getLogger().debug("========================= searchCustomSelectItemObject =========================");
			getLogger().debug(methodName.getMethod());
			getLogger().debug(user);
			getLogger().debug(locale);
			getLogger().debug(term);
			getLogger().debug(limit);
			getLogger().debug("========================= searchCustomSelectItemObject =========================");

			SelectItemService service = new SelectItemService(getLogger());
			Method method = service.getClass().getDeclaredMethod(methodName.getMethod(), CCTConnection.class
					, Locale.class, CommonUser.class, String.class, String.class, Object.class);
			
			lstResult = (List<CommonSelectItemObject<?>>) method.invoke(service, conn, locale, user, term, limit, obj);
			
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}

	public static Map<Locale, Map<String, List<CommonSelectItem>>> getMapGlobalData() {
		return mapGlobalData;
	}
}
