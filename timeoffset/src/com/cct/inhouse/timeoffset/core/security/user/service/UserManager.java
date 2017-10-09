package com.cct.inhouse.timeoffset.core.security.user.service;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.common.CommonManager;
import com.cct.exception.AuthenticateException;
import com.cct.inhouse.timeoffset.core.security.user.domain.TempUser;


public class UserManager extends CommonManager {

	private UserService service;
	
	public UserManager(Logger logger) {
		super(logger);
		this.service = new UserService(logger);
	}

	public TempUser searchUserLogin(CCTConnection conn, String username,String password) throws Exception {
		
		TempUser commonUser = null;
		
		try {
			
			commonUser = service.searchUserLogin(conn, username,password);
			
			if(commonUser == null){
				throw new AuthenticateException("Invalid username or password.");
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return commonUser;
	}

}
