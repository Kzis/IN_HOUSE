package com.cct.inhouse.bkonline.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.inhouse.bkonline.rmi.domain.BKOnlineMethod;
import com.cct.inhouse.bkonline.rmi.domain.SecretRoom;
import com.cct.inhouse.central.rmi.domain.RMISelectItemRequest;

public interface IBKOnlineRMIManager extends Remote {

	public static final String REMOTE_NAME = "BK_RMI";

	public List<CommonSelectItem> searchSecretRoomSelectItem(RMISelectItemRequest request, BKOnlineMethod method) throws RemoteException;
	public SecretRoom getSecretRoom(String userId) throws RemoteException;
	
}
