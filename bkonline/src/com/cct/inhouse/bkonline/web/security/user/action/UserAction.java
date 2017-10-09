package com.cct.inhouse.bkonline.web.security.user.action;

import org.apache.log4j.Logger;

import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionResultType;
import com.cct.enums.ActionReturnType;
import com.cct.exception.AuthenticateException;
import com.cct.inhouse.bkonline.core.security.user.domain.TempUser;
import com.cct.inhouse.bkonline.core.security.user.domain.TempUsermodel;
import com.cct.inhouse.bkonline.core.security.user.service.UserManager;
import com.cct.inhouse.bkonline.domain.BKOnlineVariable;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;
import util.string.StringUtil;
import util.web.SessionUtil;

public class UserAction extends InhouseAction implements ModelDriven<TempUsermodel> {
	
	private static final long serialVersionUID = 6498977971884514500L;
	
	private TempUsermodel model = new TempUsermodel();

	public String init() {
		getLogger().debug(StringDelimeter.BLANK.getValue());

		String result = "init";

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			getLogger().debug("Result : " + result);
		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}
	
	
	
	/**
	 * เมื่อทำการ login 
	 * @return
	 */
	public String login() {
		getLogger().debug("Login");
		
		String result = "lineprofile";

		CCTConnection conn = null;
		
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			UserManager manager = new UserManager(getLogger());
			
			TempUser commonUser = manager.searchUserLogin(conn, getModel().getUsername(), getModel().getPassword());
			
			if(commonUser != null){
				SessionUtil.put(BKOnlineVariable.SessionParameter.LINE_USER_ID.getValue(), commonUser.getId());
				if(StringUtil.stringToNull(commonUser.getLineDisplayName()) != null){
					getModel().setLineDisplayName(commonUser.getLineDisplayName());
					// result = "lineprofile";
				}
			}
			
		}catch (AuthenticateException ae)	{
			result = ActionReturnType.INIT.getResult();
			setMessage(ActionMessageType.ERROR, getText(ae.getMessage()), ActionResultType.BASIC);
		} catch (Exception e) {
			getLogger().error(e);
		}finally{
			CCTConnectionUtil.close(conn);
		}
		
		getLogger().debug("result : " + result);
		return result;
	}
	
	/**
	 * update Line DisplayName
	 * @return
	 */
	public String savelineProfile() {
		getLogger().debug("savelineProfile");
		
		String result = "barcode";
		
		CCTConnection conn = null;
		
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());

			String userId = (String)SessionUtil.get(BKOnlineVariable.SessionParameter.LINE_USER_ID.getValue());
			getLogger().debug("USER_ID : " + userId);
			
			UserManager manager = new UserManager(getLogger());
			manager.updateLineDisplayName(conn, userId,getModel().getLineDisplayName());
			
			
		} catch (Exception e) {
			getLogger().error(e);
		}finally{
			CCTConnectionUtil.close(conn);
		}
		getLogger().debug("result : " + result);
		return result;
	}
	

	public String gotoServiceList() {
		getLogger().debug(StringDelimeter.BLANK.getValue());
		// TODO: Something
		return "serviceList";
	}

	public String logout() {
		getLogger().debug(StringDelimeter.BLANK.getValue());
		// TODO: Something
		return "casLogin";
	}

	
	@Override
	public Logger loggerInititial() {
		return LogUtil.SEC;
	}
	
	@Override
	public TempUsermodel getModel() {
		return model;
	}
	
	@Override
	public String dbLookupInititial() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}
	
}
