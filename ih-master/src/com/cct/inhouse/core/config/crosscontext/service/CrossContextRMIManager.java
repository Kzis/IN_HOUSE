package com.cct.inhouse.core.config.crosscontext.service;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.common.CommonManager;
import com.cct.common.CommonSelectItem;
import com.cct.inhouse.central.rmi.interfaces.ICentralRMIManager;
import com.cct.inhouse.core.config.parameter.domain.Parameter;

import util.log.DefaultLogUtil;

public class CrossContextRMIManager extends CommonManager {

	public static final int MAX_WAIT_ROUND = 20;
	public static final long MAX_WAIT_TIME_IN_MILLIS = 500;
	
	public CrossContextRMIManager(Logger logger) {
		super(logger);
	}

	/**
	 * ดึง Parameter จาก Central
	 * 
	 * @param localContext
	 * @return
	 */
	public Parameter getCentralParameter(Parameter localParameter) {
		Parameter response = null;
		try {
			Registry registry = LocateRegistry.getRegistry(localParameter.getRmi().getPort());
			ICentralRMIManager stub = (ICentralRMIManager) registry.lookup(ICentralRMIManager.REMOTE_NAME);
			response = stub.getCentralParameter();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
		return response;
	}

	/**
	 * ดึง Global Data จาก Central
	 * 
	 * @param localContext
	 * @return
	 */
	public Map<Locale, Map<String, List<CommonSelectItem>>> getCentralGlobalData(Parameter localParameter) {
		Map<Locale, Map<String, List<CommonSelectItem>>> response = null;
		try {
			Registry registry = LocateRegistry.getRegistry(localParameter.getRmi().getPort());
			ICentralRMIManager stub = (ICentralRMIManager) registry.lookup(ICentralRMIManager.REMOTE_NAME);
			response = stub.getCentralGlobalData();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
		return response;
	}

	/**
	 * Get Central Parameter and Wait for ready
	 * @param localContext
	 * @param centralContextName
	 * @throws Exception
	 */
	public Parameter getCentralParameterWait(Parameter localParameter) throws Exception {
		int waitRound = MAX_WAIT_ROUND;
		Parameter centralParameter = null;
		do {
			centralParameter = getCentralParameter(localParameter);
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
	public Map<Locale, Map<String, List<CommonSelectItem>>> getCentralGlobalDataWait(Parameter localParameter) throws Exception {
		int waitRound = MAX_WAIT_ROUND;
		Map<Locale, Map<String, List<CommonSelectItem>>> mapCentralGlobalData = null;
		do {
			mapCentralGlobalData = getCentralGlobalData(localParameter);
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
