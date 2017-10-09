package com.cct.inhouse.core.config.crosscontext.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.cct.common.CommonManager;
import com.cct.common.CommonSelectItem;
import com.cct.inhouse.core.config.parameter.domain.Parameter;
import com.cct.inhouse.enums.CentralContextAttribute;

import util.string.StringDelimeter;

public class CrossContextManager extends CommonManager {

	public static final int MAX_WAIT_ROUND = 20;
	public static final long MAX_WAIT_TIME_IN_MILLIS = 500;
	
	public CrossContextManager(Logger logger) {
		super(logger);
	}

	/**
	 * ดึง Context ฝั่ง Central
	 * 
	 * @param localContext
	 * @param centralContextName
	 * @return
	 */
	public ServletContext getCentralContext(ServletContext localContext, String centralContextName) {
		return localContext.getContext(StringDelimeter.BACKSLASH.getValue() + centralContextName);
	}

	/**
	 * ดึง Parameter จาก Central
	 * 
	 * @param localContext
	 * @return
	 */
	public Parameter getCentralParameter(ServletContext centralContext) {
		return (Parameter) centralContext.getAttribute(CentralContextAttribute.SHARED_PARAMETER.getValue());
	}

	/**
	 * ดึง Global Data จาก Central
	 * 
	 * @param localContext
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Locale, Map<String, List<CommonSelectItem>>> getCentralGlobalData(ServletContext centralContext) {
		return (HashMap<Locale, Map<String, List<CommonSelectItem>>>) centralContext
				.getAttribute(CentralContextAttribute.SHARED_GLOBAL_DATA.getValue());
	}

	/**
	 * Get Central Context and Wait for ready
	 * @param localContext
	 * @param centralContextName
	 * @return
	 */
	public ServletContext getCentralContextWait(ServletContext localContext, String centralContextName) {
		int waitRound = MAX_WAIT_ROUND;
		ServletContext centralContext = null;
		do {
			centralContext = getCentralContext(localContext, centralContextName);
			if (centralContext == null) {
				try {
					Thread.sleep(MAX_WAIT_TIME_IN_MILLIS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getLogger().debug(centralContextName + " not found, wait for context.");
				waitRound--;
				if (waitRound <= 0) {
					break;
				}
				continue;
			} else {
				getLogger().debug(centralContextName + " is ready.");
			}
		} while (centralContext == null);
		
		return centralContext;
	}

	/**
	 * Get Central Parameter and Wait for ready
	 * @param localContext
	 * @param centralContextName
	 * @throws Exception
	 */
	public Parameter getCentralParameterWait(ServletContext localContext, String centralContextName) throws Exception {
		// ต้องได้ context มาก่อน
		ServletContext centralContext = getCentralContextWait(localContext, centralContextName);
		
		int waitRound = MAX_WAIT_ROUND;
		Parameter centralParameter = null;
		do {
			centralParameter = getCentralParameter(centralContext);
			if (centralParameter == null) {
				try {
					Thread.sleep(MAX_WAIT_TIME_IN_MILLIS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				getLogger().debug("Central Parameter is null, wait for load.");
				waitRound--;
				if (waitRound <= 0) {
					break;
				}
				continue;
			} else {
				getLogger().debug(centralParameter + " is ready.");
			}
		} while (centralParameter == null);
		
		return centralParameter;
	}

	/**
	 * Get Central Global Data and Wait for ready
	 * 
	 * @param localContext
	 * @param centralContextName
	 * @throws Exception
	 */
	public Map<Locale, Map<String, List<CommonSelectItem>>> getCentralGlobalDataWait(ServletContext localContext, String centralContextName) throws Exception {
		// ต้องได้ context มาก่อน
		ServletContext centralContext = getCentralContextWait(localContext, centralContextName);
		
		int waitRound = MAX_WAIT_ROUND;
		Map<Locale, Map<String, List<CommonSelectItem>>> mapCentralGlobalData = null;
		do {
			mapCentralGlobalData = getCentralGlobalData(centralContext);
			if (mapCentralGlobalData == null) {
				try {
					Thread.sleep(MAX_WAIT_TIME_IN_MILLIS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				getLogger().debug("GlobalData is null, wait for load.");
				waitRound--;
				if (waitRound <= 0) {
					break;
				}
				continue;
			} else {
				getLogger().debug("GlobalData have size: " + mapCentralGlobalData.size());
			}
		} while (mapCentralGlobalData == null);
		
		return mapCentralGlobalData;
	}
}
