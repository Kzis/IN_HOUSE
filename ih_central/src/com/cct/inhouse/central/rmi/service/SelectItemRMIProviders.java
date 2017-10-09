package com.cct.inhouse.central.rmi.service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import util.log.DefaultLogUtil;

import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemObject;
import com.cct.common.CommonUser;
import com.cct.inhouse.central.rmi.domain.RMISelectItemObjectRequest;
import com.cct.inhouse.central.rmi.domain.RMISelectItemRequest;
import com.cct.inhouse.central.rmi.domain.SelectItemMethod;
import com.cct.inhouse.central.rmi.interfaces.ICentralRMIManager;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.enums.GlobalType;
import com.cct.inhouse.util.IHUtil;

public class SelectItemRMIProviders {

	/** CommonSelectItem **/
	
	/**
	 * Load CommonSelectItem from GlobalData
	 * @param locale
	 * @param key
	 * @return
	 */
	public static List<CommonSelectItem> getSelectItems(Locale locale, GlobalType key) {
		List<CommonSelectItem> collectionSelectItem = new ArrayList<CommonSelectItem>();
		try {
			collectionSelectItem = IHUtil.getMapGlobalData().get(locale).get(key.getValue());
			
		} catch (Exception e) {
			DefaultLogUtil.SELECTOR.debug("Locale or Data not found!.");
		}
		return collectionSelectItem;
	}
	
	/**
	 * Load CommonSelectItem from RMI Central (Dropdownlist)
	 * @param user
	 * @param locale
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItem> requestSelectItem(CommonUser user, Locale locale, SelectItemMethod methodName) throws Exception {
		return requestSelectItem(user, locale, methodName, null, null);
	}
	
	/**
	 * Load CommonSelectItem from RMI Central (Autocomplete)
	 * @param user
	 * @param locale
	 * @param methodName
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItem> requestSelectItem(CommonUser user, Locale locale, SelectItemMethod methodName, String term, String limit) throws Exception {
		List<CommonSelectItem> collectionSelectItem = new ArrayList<CommonSelectItem>();
		try {
			DefaultLogUtil.SELECTOR.debug(methodName.getMethod());
			
			RMISelectItemRequest rmiRequest = new RMISelectItemRequest();
			rmiRequest.setTerm(term);
			rmiRequest.setLimit(limit);
			rmiRequest.setCommonUser(user);
			rmiRequest.setLocale(locale);
			DefaultLogUtil.SELECTOR.debug(rmiRequest);
			
			collectionSelectItem = requestSelectItem(rmiRequest, methodName);
			
		} catch (Exception e) {
			throw e;
		}
		return collectionSelectItem;
	}
	
	/**
	 * Load CommonSelectItem from RMI Central (Self create request)
	 * @param rmiRequest
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItem> requestSelectItem(RMISelectItemRequest rmiRequest, SelectItemMethod methodName) throws Exception {
		List<CommonSelectItem> collectionSelectItem = new ArrayList<CommonSelectItem>();
		try {
			DefaultLogUtil.SELECTOR.debug(methodName.getMethod());
			DefaultLogUtil.SELECTOR.debug(rmiRequest);
			
			// Get Registry from port
			Registry registry = null;
			if (registry == null) {
				registry = LocateRegistry.getRegistry(ParameterConfig.getParameter().getRmi().getPort());
			}

			// Get RMI from lookup
			if (registry != null) {
				DefaultLogUtil.SELECTOR.debug("REMOTE_NAME: " + ICentralRMIManager.REMOTE_NAME);
				DefaultLogUtil.SELECTOR.debug(registry.lookup(ICentralRMIManager.REMOTE_NAME));

				ICentralRMIManager cnRmi = (ICentralRMIManager)registry.lookup(ICentralRMIManager.REMOTE_NAME);
				if (cnRmi != null) {
					try {
						collectionSelectItem = cnRmi.searchDefaultSelectItem(rmiRequest, methodName);
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
	
	/**
	 * Load CommonSelectItem from RMI Central with option (Dropdownlist)
	 * @param user
	 * @param locale
	 * @param obj
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItem> requestSelectItem(CommonUser user, Locale locale, SelectItemMethod methodName, Object obj) throws Exception {
		return requestSelectItem(user, locale, methodName, null, null, obj);
	}
	
	/**
	 * Load CommonSelectItem from RMI Central with option (Autocomplete)
	 * @param user
	 * @param locale
	 * @param methodName
	 * @param term
	 * @param limit
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItem> requestSelectItem(CommonUser user, Locale locale, SelectItemMethod methodName, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItem> collectionSelectItem = new ArrayList<CommonSelectItem>();
		try {
			DefaultLogUtil.SELECTOR.debug(methodName.getMethod());
			
			RMISelectItemObjectRequest<Object> rmiRequest = new RMISelectItemObjectRequest<>();
			rmiRequest.setTerm(term);
			rmiRequest.setLimit(limit);
			rmiRequest.setCommonUser(user);
			rmiRequest.setLocale(locale);
			rmiRequest.setObject(obj);
			DefaultLogUtil.SELECTOR.debug(rmiRequest);
			
			collectionSelectItem = requestSelectItem(rmiRequest, methodName);
			
		} catch (Exception e) {
			throw e;
		}
		return collectionSelectItem;
	}
	
	/**
	 * Load CommonSelectItem from RMI Central (Self create request)
	 * @param rmiRequest
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItem> requestSelectItem(RMISelectItemObjectRequest<?> rmiRequest, SelectItemMethod methodName) throws Exception {
		List<CommonSelectItem> collectionSelectItem = new ArrayList<CommonSelectItem>();
		try {
			DefaultLogUtil.SELECTOR.debug(methodName.getMethod());
			DefaultLogUtil.SELECTOR.debug(rmiRequest);
			
			// Get Registry from port
			Registry registry = null;
			if (registry == null) {
				registry = LocateRegistry.getRegistry(ParameterConfig.getParameter().getRmi().getPort());
			}

			// Get RMI from lookup
			if (registry != null) {
				DefaultLogUtil.SELECTOR.debug("REMOTE_NAME: " + ICentralRMIManager.REMOTE_NAME);
				DefaultLogUtil.SELECTOR.debug(registry.lookup(ICentralRMIManager.REMOTE_NAME));

				ICentralRMIManager cnRmi = (ICentralRMIManager)registry.lookup(ICentralRMIManager.REMOTE_NAME);
				if (cnRmi != null) {
					try {
						collectionSelectItem = cnRmi.searchCustomSelectItem(rmiRequest, methodName);
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
	
	/** CommonSelectItemObject **/
	
	/**
	 * Load CommonSelectItemObject from RMI Central (Dropdownlist)
	 * @param user
	 * @param locale
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItemObject<?>> requestSelectItemObject(CommonUser user, Locale locale, SelectItemMethod methodName) throws Exception {
		return requestSelectItemObject(user, locale, methodName, null, null);
	}
	
	/**
	 * Load CommonSelectItemObject from RMI Central (Autocomplete)
	 * @param user
	 * @param locale
	 * @param methodName
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItemObject<?>> requestSelectItemObject(CommonUser user, Locale locale, SelectItemMethod methodName, String term, String limit) throws Exception {
		List<CommonSelectItemObject<?>> collectionSelectItem = new ArrayList<CommonSelectItemObject<?>>();
		try {
			DefaultLogUtil.SELECTOR.debug(methodName.getMethod());
			
			RMISelectItemRequest rmiRequest = new RMISelectItemRequest();
			rmiRequest.setTerm(term);
			rmiRequest.setLimit(limit);
			rmiRequest.setCommonUser(user);
			rmiRequest.setLocale(locale);
			DefaultLogUtil.SELECTOR.debug(rmiRequest);
			
			collectionSelectItem = requestSelectItemObject(rmiRequest, methodName);
			
		} catch (Exception e) {
			throw e;
		}
		return collectionSelectItem;
	}
	
	/**
	 * Load CommonSelectItemObject from RMI Central (Self create request)
	 * @param rmiRequest
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItemObject<?>> requestSelectItemObject(RMISelectItemRequest rmiRequest, SelectItemMethod methodName) throws Exception {
		List<CommonSelectItemObject<?>> collectionSelectItem = new ArrayList<CommonSelectItemObject<?>>();
		try {
			DefaultLogUtil.SELECTOR.debug(methodName.getMethod());
			DefaultLogUtil.SELECTOR.debug(rmiRequest);
			
			// Get Registry from port
			Registry registry = null;
			if (registry == null) {
				registry = LocateRegistry.getRegistry(ParameterConfig.getParameter().getRmi().getPort());
			}

			// Get RMI from lookup
			if (registry != null) {
				DefaultLogUtil.SELECTOR.debug("REMOTE_NAME: " + ICentralRMIManager.REMOTE_NAME);
				DefaultLogUtil.SELECTOR.debug(registry.lookup(ICentralRMIManager.REMOTE_NAME));

				ICentralRMIManager cnRmi = (ICentralRMIManager)registry.lookup(ICentralRMIManager.REMOTE_NAME);
				if (cnRmi != null) {
					try {
						collectionSelectItem = cnRmi.searchDefaultSelectItemObject(rmiRequest, methodName);
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
	
	/**
	 * Load CommonSelectItemObject from RMI Central (Dropdownlist)
	 * @param user
	 * @param locale
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItemObject<?>> requestSelectItemObject(CommonUser user, Locale locale, SelectItemMethod methodName, Object obj) throws Exception {
		return requestSelectItemObject(user, locale, methodName, null, null, obj);
	}
	
	/**
	 * Load CommonSelectItemObject from RMI Central (Autocomplete)
	 * @param user
	 * @param locale
	 * @param methodName
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItemObject<?>> requestSelectItemObject(CommonUser user, Locale locale, SelectItemMethod methodName, String term, String limit, Object obj) throws Exception {
		List<CommonSelectItemObject<?>> collectionSelectItem = new ArrayList<CommonSelectItemObject<?>>();
		try {
			DefaultLogUtil.SELECTOR.debug(methodName.getMethod());
			
			RMISelectItemObjectRequest<Object> rmiRequest = new RMISelectItemObjectRequest<>();
			rmiRequest.setTerm(term);
			rmiRequest.setLimit(limit);
			rmiRequest.setCommonUser(user);
			rmiRequest.setLocale(locale);
			rmiRequest.setObject(obj);
			DefaultLogUtil.SELECTOR.debug(rmiRequest);
			
			collectionSelectItem = requestSelectItemObject(rmiRequest, methodName);
			
		} catch (Exception e) {
			throw e;
		}
		return collectionSelectItem;
	}
	
	/**
	 * Load CommonSelectItemObject from RMI Central (Self create request)
	 * @param rmiRequest
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static List<CommonSelectItemObject<?>> requestSelectItemObject(RMISelectItemObjectRequest<?> rmiRequest, SelectItemMethod methodName) throws Exception {
		List<CommonSelectItemObject<?>> collectionSelectItem = new ArrayList<CommonSelectItemObject<?>>();
		try {
			DefaultLogUtil.SELECTOR.debug(methodName.getMethod());
			DefaultLogUtil.SELECTOR.debug(rmiRequest);
			
			// Get Registry from port
			Registry registry = null;
			if (registry == null) {
				registry = LocateRegistry.getRegistry(ParameterConfig.getParameter().getRmi().getPort());
			}

			// Get RMI from lookup
			if (registry != null) {
				DefaultLogUtil.SELECTOR.debug("REMOTE_NAME: " + ICentralRMIManager.REMOTE_NAME);
				DefaultLogUtil.SELECTOR.debug(registry.lookup(ICentralRMIManager.REMOTE_NAME));

				ICentralRMIManager cnRmi = (ICentralRMIManager)registry.lookup(ICentralRMIManager.REMOTE_NAME);
				if (cnRmi != null) {
					try {
						collectionSelectItem = cnRmi.searchCustomSelectItemObject(rmiRequest, methodName);
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
}
