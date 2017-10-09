package com.cct.inhouse.timeoffset.web.security.user.action;

import org.apache.log4j.Logger;

import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionResultType;
import com.cct.enums.ActionReturnType;
import com.cct.enums.LanguageSessionAttribute;
import com.cct.enums.UserSessionAttribute;
import com.cct.exception.AuthenticateException;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.timeoffset.core.security.permission.service.PermissionManager;
import com.cct.inhouse.timeoffset.core.security.user.domain.TempUser;
import com.cct.inhouse.timeoffset.core.security.user.domain.TempUsermodel;
import com.cct.inhouse.timeoffset.core.security.user.service.UserManager;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

public class UserAction extends InhouseAction implements ModelDriven<TempUsermodel> {

	private static final long serialVersionUID = -4851510109118453171L;
	
	private TempUsermodel model = new TempUsermodel();
	
	public String init() {
		getLogger().debug(StringDelimeter.BLANK.getValue());

		String result = "init";

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
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
		
		String result = "mainPage";

		CCTConnection conn = null;
		
		try {
			
			// ลบ object ทุกอย่างออกจาก session ยกเว้น ignore user และภาษา
			String[] ignore = { UserSessionAttribute.DEFAULT.getValue(), LanguageSessionAttribute.DEFAULT.getValue()};
			SessionUtil.removesIgnore(ignore);
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
						
			UserManager manager = new UserManager(getLogger());
			
			TempUser commonUser = manager.searchUserLogin(conn, getModel().getUsername(), getModel().getPassword());
			
			if(commonUser != null){
				
				SessionUtil.put("userId", commonUser.getId());
				
				// user for test
				InhouseUser user = new InhouseUser();
				user.setId(commonUser.getId());
				user.setName(commonUser.getName());
				user.setGivenName(commonUser.getSurName());
				user.setFullName(commonUser.getFullName());
				user.setFamilyName(commonUser.getName());
				user.setLocale(ParameterConfig.getParameter().getApplication().getApplicationLocale());
					
				// search menu
				PermissionManager managerP = new PermissionManager(getLogger(), getUser(), getLocale());
				user.setMapOperator(managerP.searchMenuAndFunction(conn, user.getId()));

				// user back to session
				SessionUtil.put(UserSessionAttribute.DEFAULT.getValue(), user);
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
	
	@Override
	public TempUsermodel getModel() {
		return model;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.SEC;
	}

}
