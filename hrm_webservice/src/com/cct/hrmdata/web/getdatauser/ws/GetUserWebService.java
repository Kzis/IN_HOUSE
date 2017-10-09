
package com.cct.hrmdata.web.getdatauser.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;

import com.cct.common.CommonUser;
import com.cct.hrmdata.core.config.domain.DBLookup;
import com.cct.hrmdata.core.getdatauser.service.GetUserManager;


@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public class GetUserWebService {

	@WebMethod
	public List<CommonUser> getUser() {
		List<CommonUser> response = new ArrayList<CommonUser>();
		
		CCTConnection conn = null;
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			
			GetUserManager manager = new GetUserManager(conn, null);
			response = manager.searchUser();
			
		} catch (Exception e) {
			LogUtil.GET_USER.error("", e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return response;
	}
}
