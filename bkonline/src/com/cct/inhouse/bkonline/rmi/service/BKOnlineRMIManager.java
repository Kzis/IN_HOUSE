package com.cct.inhouse.bkonline.rmi.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cct.common.CommonSelectItem;
import com.cct.inhouse.bkonline.rmi.domain.BKOnlineMethod;
import com.cct.inhouse.bkonline.rmi.domain.SecretRoom;
import com.cct.inhouse.bkonline.rmi.interfaces.IBKOnlineRMIManager;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.central.rmi.domain.RMISelectItemRequest;

public class BKOnlineRMIManager extends UnicastRemoteObject implements IBKOnlineRMIManager {

	private static final long serialVersionUID = 2436982056125147329L;
	
	private Logger logger = LogUtil.SELECTOR;
	
	public BKOnlineRMIManager() throws RemoteException {
		
	}
	
	private Logger getLogger() {
		return logger;
	}

	@Override
	public List<CommonSelectItem> searchSecretRoomSelectItem(RMISelectItemRequest request, BKOnlineMethod method) throws RemoteException {
		
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		try {
			getLogger().debug("========================= searchDefaultSelectitem =========================");
			getLogger().debug(method.getMethord());
			getLogger().debug(request.getCommonUser());
			getLogger().debug(request.getLocale());
			getLogger().debug(request.getTerm());
			getLogger().debug(request.getLimit());
			getLogger().debug("========================= searchDefaultSelectitem =========================");
			
			CommonSelectItem item = null;
			for (int i = 0; i < 10; i++) {
				item = new CommonSelectItem();
				item.setKey(String.valueOf(i));
				item.setValue("Items : " + i);
				lstResult.add(item);
			}
			
//			CCTConnection conn = null;
//			try {
//				conn = getConection();
//				
//				SelectItemManager manager = new SelectItemManager(getLogger());
//				lstResult = manager.searchDefaultSelectItem(conn
//						, request.getLocale()
//						, request.getCommonUser()
//						, methodName
//						, request.getTerm()
//						, request.getLimit());
//				
//			} catch (Exception e) {
//				getLogger().error(e);
//			} finally {
//				CCTConnectionUtil.close(conn);
//			}
			
			getLogger().debug("Result : " + lstResult);
			
		} catch (Exception e) {
			throw e;
		}
		return lstResult;
	}

	@Override
	public SecretRoom getSecretRoom(String userId) throws RemoteException {
		SecretRoom room = new SecretRoom();
		try {
			room.setRoomName("Room 39");
			room.setMeetingTime("Full Day");
			room.setSubscriber("Ricola");
			
		} catch (Exception e) {
			throw e;
		}
		return room;
	}


}
