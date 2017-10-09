package com.cct.hrmdata.core.checkuserlogin.service;

import java.util.Locale;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractService;
import com.cct.hrmdata.core.config.domain.SQLPath;

public class CheckUserLoginService extends AbstractService {
	private CheckUserLoginDAO dao;

	public CheckUserLoginService(CCTConnection conn, Locale locale) {
		super(conn, locale);
		
		setDao(new CheckUserLoginDAO());
		getDao().setSqlPath(SQLPath.CHECK_USER_LOGIN_SQL);
	}
	
	public CheckUserLoginDAO getDao() {
		return dao;
	}

	public void setDao(CheckUserLoginDAO dao) {
		this.dao = dao;
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
	protected int checkLogin(String username, String password) throws Exception {
		return getDao().checkLogin(conn, username, password);
	}
}
