package com.cct.hrmdata.core.getdatauser.service;

import java.util.List;
import java.util.Locale;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonUser;

public class GetUserManager extends AbstractManager{
	
	private GetUserService service = null;
	
	public GetUserManager(CCTConnection conn, Locale locale) {
		super(conn, locale);
		setService(new GetUserService(conn, locale));
	}
	
	public GetUserService getService() {
		return service;
	}

	public void setService(GetUserService service) {
		this.service = service;
	}
	
	/**
	 * Search user จาก DB HRM
	 * @return
	 * @throws Exception
	 */
	public List<CommonUser> searchUser() throws Exception{
		return getService().searchUser();
	}
	
	
	

}
