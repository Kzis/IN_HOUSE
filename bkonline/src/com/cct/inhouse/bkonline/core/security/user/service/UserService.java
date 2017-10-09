package com.cct.inhouse.bkonline.core.security.user.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.common.CommonService;
import com.cct.inhouse.bkonline.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.bkonline.core.security.user.domain.AdminUser;
import com.cct.inhouse.bkonline.core.security.user.domain.TempUser;
import com.cct.inhouse.common.InhouseUser;

public class UserService extends CommonService {

	private UserDAO dao = null;
	
	public UserService(Logger logger) {
		super(logger);
		this.dao = new UserDAO(logger, SQLPath.USER_SQL.getSqlPath());
	}

	/**
	 * ค้นหา Admin ในระบบ
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected Map<String, AdminUser> searchAdminInSystem(CCTConnection conn) throws Exception {
		return dao.searchAdminInSystem(conn);
	}
	
	/**
	 * ค้นหา User ทั่วไปในระบบ
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected InhouseUser searchUserInSystem(CCTConnection conn, String userId) throws Exception {
		return dao.searchUserInSystem(conn, userId);
	}
	
	/**
	 * ค้นหาข้อมูล login
	 * @param conn
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	protected TempUser searchUserLogin(CCTConnection conn, String username, String password) throws Exception {
		return dao.searchUserLogin(conn, username,password);
	}
	
	/**
	 * updateLineDisplayName
	 * @param conn
	 * @param userId
	 * @param lineDisplayName
	 * @return
	 * @throws Exception
	 */
	protected long updateLineDisplayName(CCTConnection conn, String userId,String lineDisplayName) throws Exception {
		return dao.updateLineDisplayName(conn, userId,lineDisplayName);
	}
	
	protected List<String> searchAuthorizationNormal(CCTConnection conn) throws Exception {
		return dao.searchAuthorizationNormal(conn);
	}
}
