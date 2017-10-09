package com.cct.hrmdata.core.checkuserlogin.service;

import java.util.Locale;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractManager;

public class CheckUserLoginManager extends AbstractManager {
	private CheckUserLoginService service = null;

	public CheckUserLoginManager(CCTConnection conn, Locale locale) {
		super(conn, locale);
		setService(new CheckUserLoginService(conn, locale));
	}

	public CheckUserLoginService getService() {
		return service;
	}

	public void setService(CheckUserLoginService service) {
		this.service = service;
	}
	
	/**
	 * Check Login by username and password
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return boolean is found.
	 * @throws Exception
	 */
	public int checkLogin(String username, String password) throws Exception {
		return getService().checkLogin(username, password);
	}
}
