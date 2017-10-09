package com.cct.inhouse.bkonline.core.security.user.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.common.CommonManager;
import com.cct.exception.AuthenticateException;
import com.cct.inhouse.bkonline.core.security.user.domain.AdminUser;
import com.cct.inhouse.bkonline.core.security.user.domain.TempUser;
import com.cct.inhouse.common.InhouseUser;

public class UserManager extends CommonManager {

	private UserService service;
	
	public UserManager(Logger logger) {
		super(logger);
		this.service = new UserService(logger);
	}

	public Map<String, AdminUser> searchAdminInSystem(CCTConnection conn) throws Exception {
		return service.searchAdminInSystem(conn);
	}
	
	/**
	 * ค้นหา User ทั่วไปในระบบ
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public InhouseUser searchUserInSystem(CCTConnection conn, String userId) throws Exception {
		return service.searchUserInSystem(conn, userId);
	}
	
	/**
	 * login
	 * @param conn
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public TempUser searchUserLogin(CCTConnection conn, String username, String password) throws Exception {
		
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
	
	/**
	 * updateLineDisplayName
	 * @param conn
	 * @param userId
	 * @param lineDisplayName
	 * @return
	 * @throws Exception
	 */
	public long updateLineDisplayName(CCTConnection conn, String userId,String lineDisplayName) throws Exception {
		return service.updateLineDisplayName(conn, userId,lineDisplayName);
	}
	
	public List<String> searchAuthorizationNormal(CCTConnection conn) throws Exception {
		return service.searchAuthorizationNormal(conn);
	}
}
