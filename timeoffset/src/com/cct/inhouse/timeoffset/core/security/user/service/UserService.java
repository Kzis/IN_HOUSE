package com.cct.inhouse.timeoffset.core.security.user.service;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.common.CommonService;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.timeoffset.core.security.user.domain.TempUser;

public class UserService extends CommonService {

	private UserDAO dao = null;
	
	public UserService(Logger logger) {
		super(logger);
		this.dao = new UserDAO(logger, SQLPath.USER_SQL.getSqlPath());
	}

	protected TempUser searchUserLogin(CCTConnection conn, String username,String password) throws Exception {
		return dao.searchUserLogin(conn, username,password);
	}

}
