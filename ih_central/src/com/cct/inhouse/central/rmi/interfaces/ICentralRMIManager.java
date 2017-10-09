package com.cct.inhouse.central.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemObject;
import com.cct.inhouse.central.rmi.domain.RMISelectItemObjectRequest;
import com.cct.inhouse.central.rmi.domain.RMISelectItemRequest;
import com.cct.inhouse.central.rmi.domain.SelectItemMethod;
import com.cct.inhouse.core.config.parameter.domain.Parameter;

public interface ICentralRMIManager extends Remote {

	public static final String REMOTE_NAME = "CN_RMI";
	
	public List<CommonSelectItem> searchDefaultSelectItem(RMISelectItemRequest request, SelectItemMethod method) throws RemoteException;
	public List<CommonSelectItem> searchCustomSelectItem(RMISelectItemObjectRequest<?> request, SelectItemMethod method)throws RemoteException;
	public List<CommonSelectItemObject<?>> searchDefaultSelectItemObject(RMISelectItemRequest request, SelectItemMethod method) throws RemoteException;
	public List<CommonSelectItemObject<?>> searchCustomSelectItemObject(RMISelectItemObjectRequest<?> request, SelectItemMethod method) throws RemoteException;
	public Parameter getCentralParameter() throws RemoteException;
	public Map<Locale, Map<String, List<CommonSelectItem>>> getCentralGlobalData() throws RemoteException;
}
