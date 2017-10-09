package com.cct.inhouse.bkonline.rmi.service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import util.log.DefaultLogUtil;

import com.cct.common.CommonSelectItem;
import com.cct.common.CommonUser;
import com.cct.inhouse.bkonline.rmi.domain.BKOnlineMethod;
import com.cct.inhouse.bkonline.rmi.domain.SecretRoom;
import com.cct.inhouse.bkonline.rmi.interfaces.IBKOnlineRMIManager;
import com.cct.inhouse.central.rmi.domain.RMISelectItemRequest;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;

public class BKOnlineRMIProviders {

	public static List<CommonSelectItem> searchSecretRoomSelectItem(CommonUser user, Locale locale, BKOnlineMethod methodName, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItem> collectionSelectItem = new ArrayList<CommonSelectItem>();
		try {
			DefaultLogUtil.SELECTOR.debug(methodName.getMethord());
			
			RMISelectItemRequest rmiRequest = new RMISelectItemRequest();
			rmiRequest.setTerm(term);
			rmiRequest.setLimit(limit);
			rmiRequest.setCommonUser(user);
			rmiRequest.setLocale(locale);
			DefaultLogUtil.SELECTOR.debug(rmiRequest);
			
			// Get Registry from port
			Registry registry = null;
			if (registry == null) {
				registry = LocateRegistry.getRegistry(ParameterConfig.getParameter().getRmi().getPort());
			}

			// Get RMI from lookup
			if (registry != null) {
				DefaultLogUtil.SELECTOR.debug("REMOTE_NAME: " + IBKOnlineRMIManager.REMOTE_NAME);
				DefaultLogUtil.SELECTOR.debug(registry.lookup(IBKOnlineRMIManager.REMOTE_NAME));

				IBKOnlineRMIManager bkRmi = (IBKOnlineRMIManager)registry.lookup(IBKOnlineRMIManager.REMOTE_NAME);
				if (bkRmi != null) {
					try {
						collectionSelectItem = bkRmi.searchSecretRoomSelectItem(rmiRequest, methodName);
					} catch (Exception e) {
						DefaultLogUtil.SELECTOR.error("Invoke selectitems ERROR!", e);
					}
				}
			}
			
		} catch (RemoteException e) {
			DefaultLogUtil.SELECTOR.error("Can't find Registry in port!", e);
		} catch (NotBoundException e) {
			DefaultLogUtil.SELECTOR.error("Can't find RMI in lookup!", e);
		} catch (Exception e) {
			DefaultLogUtil.SELECTOR.error("RMI Client side error!", e);
		}
		return collectionSelectItem;
	}
	
	public static SecretRoom getSecretRoom(String userId) throws RemoteException {
		DefaultLogUtil.SELECTOR.debug("getSecretRoom");
		
		SecretRoom room = new SecretRoom();
		try {
			// Get Registry from port
			Registry registry = null;
			if (registry == null) {
				registry = LocateRegistry.getRegistry(ParameterConfig.getParameter().getRmi().getPort());
			}

			// Get RMI from lookup
			if (registry != null) {
				DefaultLogUtil.SELECTOR.debug("REMOTE_NAME: " + IBKOnlineRMIManager.REMOTE_NAME);
				DefaultLogUtil.SELECTOR.debug(registry.lookup(IBKOnlineRMIManager.REMOTE_NAME));

				IBKOnlineRMIManager bkRmi = (IBKOnlineRMIManager)registry.lookup(IBKOnlineRMIManager.REMOTE_NAME);
				if (bkRmi != null) {
					try {
						room = bkRmi.getSecretRoom("00");
						
					} catch (Exception e) {
						DefaultLogUtil.SELECTOR.error("Invoke selectitems ERROR!", e);
					}
				}
			}
			
		} catch (RemoteException e) {
			DefaultLogUtil.SELECTOR.error("Can't find Registry in port!", e);
		} catch (NotBoundException e) {
			DefaultLogUtil.SELECTOR.error("Can't find RMI in lookup!", e);
		} catch (Exception e) {
			DefaultLogUtil.SELECTOR.error("RMI Client side error!", e);
		}
		return room;
	}
}
