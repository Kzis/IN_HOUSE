package com.cct.hrmdata.core.getdatauser.service;

import java.util.List;
import java.util.Locale;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.hrmdata.core.config.domain.SQLPath;

public class GetUserService extends AbstractService{


	private GetUserDAO dao = null;
	
	public GetUserService(CCTConnection conn, Locale locale) {
		super(conn, locale);
		setDao(new GetUserDAO());
		getDao().setSqlPath(SQLPath.GET_USER_SQL);
	}
	
	public GetUserDAO getDao() {
		return dao;
	}

	public void setDao(GetUserDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * Search user จาก DB HRM
	 * @return
	 * @throws Exception
	 */
	protected List<CommonUser> searchUser() throws Exception{
		return getDao().searchUser(conn);
	}
	
	

}
