package com.cct.hrmdata.web.checkuserlogin.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;

import com.cct.hrmdata.core.checkuserlogin.service.CheckUserLoginManager;
import com.cct.hrmdata.core.config.domain.DBLookup;

@WebService
public class CheckUserLoginWebService {
	
	/*@WebMethod
	public int checkUserLogin(String username, String password) {		
		return 1;
	}*/
	
	@WebMethod
	public int checkUserLogin(String username, String password) {
		int response = -99;
		
		CCTConnection conn = null;
		try {
			// Get Connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// Call manager to check login
			CheckUserLoginManager manager = new CheckUserLoginManager(conn, null);
			response = manager.checkLogin(username, password);
			
		} catch (Exception e) {
			LogUtil.GET_TIME_OFFSET.error("", e);
			
		} finally {
			CCTConnectionUtil.close(conn);			
		}
		
		return response;
	}
}
