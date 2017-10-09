package com.cct.inhouse.core.config.parameter.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.cct.common.CommonSelectItem;
import com.cct.database.Database;
import com.cct.inhouse.core.config.crosscontext.service.CrossContextRMIManager;
import com.cct.inhouse.core.config.parameter.service.ParameterManager;
import com.cct.inhouse.util.image.BrowseUploadServiceUtil;

import util.log.DefaultLogUtil;

public class ParameterConfig implements Serializable {

	private static final long serialVersionUID = -2187894195556282622L;

	private static String centralContextName;
	private static ServletContext localContext;
	private static Parameter parameter;
	private static Parameter localParameter;
	private static Map<Locale, Map<String, List<CommonSelectItem>>> mapGlobalData;
	private static Map<String, String> mapContenType;

	public static void init(HttpServlet servlet) {
		// ดึง Object ServletContext ของ Client
		ServletContext localContext = servlet.getServletContext();

		try {

			// โหลด parameter.xml ของ Client ผ่าน Method ParameterManager.get()
			String parameterFile = localContext.getRealPath(servlet.getInitParameter("parameterfile"));
			DefaultLogUtil.INITIAL.debug("Parameter path :- " + parameterFile);

			Parameter localParameter = new ParameterManager().get(parameterFile);

			// โหลด Central Parameter ผ่าน Method
			CrossContextRMIManager crossContextManager = new CrossContextRMIManager(DefaultLogUtil.INITIAL);
			Parameter centralParameter = crossContextManager.getCentralParameterWait(localParameter);

			// โหลด Central Parameter ผ่าน Method
			Map<Locale, Map<String, List<CommonSelectItem>>> mapCentralGlobalData = crossContextManager
					.getCentralGlobalDataWait(localParameter);

			// จัดเก็บ Client context และ Parameter เพื่อใช้สำหรับ Override
			// ในครั้งต่อไป
			setCentralContextName(localParameter.getContextConfig().getContextCentral());
			setLocalContext(localContext);
			setLocalParameter(localParameter);
			setParameter(centralParameter);
			setMapGlobalData(mapCentralGlobalData);

			// ทุกครั้งที่มีการดึง parameter ไปใช้งาน ระบบจะดึง Parameter จาก
			// Central ใหม่ทุกครั้ง และจัดเก็บไว้ที่ ParameterConfig
			// ถ้าดึงไม่สำเร็จ ระบบจะใช้ Central Parameter ตัวเดิมที่เก็บอยู่ที่
			// ParameterConfig แทน
			getParameter();
			getMapGlobalData();

			DefaultLogUtil.INITIAL.debug(getMapContenType());
			DefaultLogUtil.INITIAL.debug("Title :- " + getParameter().getApplication().getTitle());
			DefaultLogUtil.INITIAL.debug("Theme :- " + getParameter().getApplication().getTheme());
			DefaultLogUtil.INITIAL.debug("Load " + getParameter().getApplication().getTitle() + " completed.");

		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error("Can't load paramter!!!", e);
		}

	}

	public static Parameter getParameter() {
		try {
			CrossContextRMIManager ccManager = new CrossContextRMIManager(DefaultLogUtil.INITIAL);
			Parameter centralParameter = ccManager.getCentralParameter(getLocalParameter());
			if (centralParameter != null) {
				Parameter tempParameter = centralParameter.clone();
				tempParameter.merge(getLocalParameter());
				setParameter(tempParameter);
			}
		} catch (Exception e) {
			// LogUtil.INITIAL.error(e);
		}
		return parameter;
	}
	
	public static void setParameter(Parameter parameter) {
		ParameterConfig.parameter = parameter;
	}

	public static Map<Locale, Map<String, List<CommonSelectItem>>> getMapGlobalData() {
		try {
			CrossContextRMIManager ccManager = new CrossContextRMIManager(DefaultLogUtil.INITIAL);

			Map<Locale, Map<String, List<CommonSelectItem>>> mapCentralGlobalData = ccManager.getCentralGlobalData(getLocalParameter());
			if (mapCentralGlobalData != null) {
				setMapGlobalData(mapCentralGlobalData);
			}
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
		return mapGlobalData;
	}

	public static void setMapGlobalData(Map<Locale, Map<String, List<CommonSelectItem>>> mapGlobalData) {
		ParameterConfig.mapGlobalData = mapGlobalData;
	}

	public static ServletContext getLocalContext() {
		return localContext;
	}

	public static void setLocalContext(ServletContext localContext) {
		ParameterConfig.localContext = localContext;
	}

	public static void setLocalParameter(Parameter localParameter) {
		ParameterConfig.localParameter = localParameter;
	}

	public static Parameter getLocalParameter() {
		return localParameter;
	}

	public static Map<String, String> getMapContenType() {
		if (mapContenType == null) {
			try {
				mapContenType = BrowseUploadServiceUtil.loadMapContentType();
			} catch (Exception e) {
				DefaultLogUtil.INITIAL.error(e);
			}
		}
		return mapContenType;
	}

	public static String getCentralContextName() {
		return centralContextName;
	}

	public static void setCentralContextName(String centralContextName) {
		ParameterConfig.centralContextName = centralContextName;
	}

	public static AttachFile getAttachFile() {
		return getParameter().getAttachFile();
	}

	public static Autocomplete getAutocomplete() {
		return getParameter().getAutocomplete();
	}

	public static ContextConfig getContextConfig() {
		return getParameter().getContextConfig();
	}

	public static Database[] getDatabase() {
		return getParameter().getDatabase();
	}

	public static Map<String, Database> getDatabaseMap() {
		return getParameter().getDatabaseMap();
	}

	public static DateFormat getDateFormat() {
		return getParameter().getDateFormat();
	}

	public static Report getReport() {
		return getParameter().getReport();
	}
	
	public static Rmi getRmi() {
		return getParameter().getRmi();
	}

	public static Application getApplication() {
		return getParameter().getApplication();
	}
}
