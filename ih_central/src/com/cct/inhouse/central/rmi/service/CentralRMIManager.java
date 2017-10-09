package com.cct.inhouse.central.rmi.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemObject;
import com.cct.inhouse.central.core.config.parameter.domain.CParameterConfig;
import com.cct.inhouse.central.core.selectitem.service.SelectItemManager;
import com.cct.inhouse.central.rmi.domain.RMISelectItemObjectRequest;
import com.cct.inhouse.central.rmi.domain.RMISelectItemRequest;
import com.cct.inhouse.central.rmi.domain.SelectItemMethod;
import com.cct.inhouse.central.rmi.interfaces.ICentralRMIManager;
import com.cct.inhouse.central.util.database.CConnectionProvider;
import com.cct.inhouse.central.util.log.LogUtil;
import com.cct.inhouse.core.config.parameter.domain.Parameter;
import com.cct.inhouse.enums.DBLookup;

public class CentralRMIManager extends UnicastRemoteObject implements ICentralRMIManager {

	private static final long serialVersionUID = -6360913665812611430L;
	
	private Logger logger = LogUtil.SELECTOR;

	public CentralRMIManager() throws RemoteException {
		
	}
	
	private CCTConnection getConection() throws Exception {
		return new CConnectionProvider().getConnection(null, DBLookup.MYSQL_INHOUSE.getLookup());
	}
	
	private Logger getLogger() {
		return logger;
	}

	@Override
	public List<CommonSelectItem> searchDefaultSelectItem(RMISelectItemRequest request, SelectItemMethod methodName) throws RemoteException {
		LogUtil.SELECTOR.debug("searchDefaultSelectitem");
		
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			getLogger().debug("========================= searchDefaultSelectitem =========================");
			getLogger().debug(methodName.getMethod());
			getLogger().debug(request.getCommonUser());
			getLogger().debug(request.getLocale());
			getLogger().debug(request.getTerm());
			getLogger().debug(request.getLimit());
			getLogger().debug("========================= searchDefaultSelectitem =========================");
			
			CCTConnection conn = null;
			try {
				conn = getConection();
				
				SelectItemManager manager = new SelectItemManager(getLogger());
				lstResult = manager.searchDefaultSelectItem(conn
						, request.getLocale()
						, request.getCommonUser()
						, methodName
						, request.getTerm()
						, request.getLimit());
				
			} catch (Exception e) {
				getLogger().error(e);
			} finally {
				CCTConnectionUtil.close(conn);
			}
			
			getLogger().debug("Result : " + lstResult);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}

	@Override
	public List<CommonSelectItemObject<?>> searchDefaultSelectItemObject(RMISelectItemRequest request, SelectItemMethod methodName) throws RemoteException {
		LogUtil.SELECTOR.debug("searchDefaultSelectitem");
		
		List<CommonSelectItemObject<?>> lstResult = new ArrayList<CommonSelectItemObject<?>>();
		try {
			getLogger().debug("========================= searchObjectSelectitem =========================");
			getLogger().debug(methodName.getMethod());
			getLogger().debug(request.getCommonUser());
			getLogger().debug(request.getLocale());
			getLogger().debug(request.getTerm());
			getLogger().debug(request.getLimit());
			getLogger().debug("========================= searchObjectSelectitem =========================");
			
			CCTConnection conn = null;
			try {
				conn = getConection();
				
				SelectItemManager manager = new SelectItemManager(getLogger());
				lstResult = manager.searchSelectObjectItem(conn
						, request.getLocale()
						, request.getCommonUser()
						, methodName
						, request.getTerm()
						, request.getLimit()
						);
				
			} catch (Exception e) {
				getLogger().error(e);
			} finally {
				CCTConnectionUtil.close(conn);
			}
			
			getLogger().debug("Result : " + lstResult);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}

	@Override
	public List<CommonSelectItem> searchCustomSelectItem(RMISelectItemObjectRequest<?> request, SelectItemMethod methodName) throws RemoteException {
		LogUtil.SELECTOR.debug("searchCustomSelectitem");
		
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			getLogger().debug("========================= searchCustomSelectitem =========================");
			getLogger().debug(methodName.getMethod());
			getLogger().debug(request.getCommonUser());
			getLogger().debug(request.getLocale());
			getLogger().debug(request.getTerm());
			getLogger().debug(request.getLimit());
			getLogger().debug("========================= searchCustomSelectitem =========================");
			
			CCTConnection conn = null;
			try {
				conn = getConection();
				
				SelectItemManager manager = new SelectItemManager(getLogger());
				lstResult = manager.searchCustomSelectitem(conn
						, request.getLocale()
						, request.getCommonUser()
						, methodName
						, request.getTerm()
						, request.getLimit()
						, request.getObject()
						);
				
			} catch (Exception e) {
				getLogger().error(e);
			} finally {
				CCTConnectionUtil.close(conn);
			}
			
			getLogger().debug("Result : " + lstResult);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}
	
	@Override
	public List<CommonSelectItemObject<?>> searchCustomSelectItemObject(RMISelectItemObjectRequest<?> request, SelectItemMethod methodName) throws RemoteException {
		LogUtil.SELECTOR.debug("searchCustomSelectItemObject");
		
		List<CommonSelectItemObject<?>> lstResult = new ArrayList<CommonSelectItemObject<?>>();
		try {
			getLogger().debug("========================= searchCustomSelectItemObject =========================");
			getLogger().debug(methodName.getMethod());
			getLogger().debug(request.getCommonUser());
			getLogger().debug(request.getLocale());
			getLogger().debug(request.getTerm());
			getLogger().debug(request.getLimit());
			getLogger().debug("========================= searchCustomSelectItemObject =========================");
			
			CCTConnection conn = null;
			try {
				conn = getConection();
				
				SelectItemManager manager = new SelectItemManager(getLogger());
				lstResult = manager.searchCustomSelectItemObject(conn
						, request.getLocale()
						, request.getCommonUser()
						, methodName
						, request.getTerm()
						, request.getLimit()
						, request.getObject()
						);
				
			} catch (Exception e) {
				getLogger().error(e);
			} finally {
				CCTConnectionUtil.close(conn);
			}
			
			getLogger().debug("Result : " + lstResult);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}

	/**
	 * สำหรับให้ Web อื่นๆ เข้ามาดึง Parameter ไปใช้
	 */
	@Override
	public Parameter getCentralParameter() throws RemoteException {
		return CParameterConfig.getParameter();
	}

	/**
	 * สำหรับให้ Web อื่นๆ เข้ามาดึง GlobalData ไปใช้
	 */
	@Override
	public Map<Locale, Map<String, List<CommonSelectItem>>> getCentralGlobalData() throws RemoteException {
		return SelectItemManager.getMapGlobalData();
	}
	
}