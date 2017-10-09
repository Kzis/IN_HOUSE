package com.cct.inhouse.bkonline.web.security.permission.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.domain.Operator;
import com.cct.enums.LanguageSessionAttribute;
import com.cct.enums.UserSessionAttribute;
import com.cct.inhouse.bkonline.core.security.permission.service.PermissionManager;
import com.cct.inhouse.bkonline.core.security.user.domain.AdminUser;
import com.cct.inhouse.bkonline.core.security.user.service.UserManager;
import com.cct.inhouse.bkonline.domain.BKOnlineVariable;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

public class PermissionAction extends InhouseAction {
	
	private static final long serialVersionUID = 6498977971884514500L;

	public String init() {
		getLogger().debug(StringDelimeter.BLANK.getValue());

		String result = "initScheduler";

		CCTConnection conn = null;
		try {
			// ลบ object ทุกอย่างออกจาก session ยกเว้น ignore user และภาษา
			String[] ignore = { UserSessionAttribute.DEFAULT.getValue(), LanguageSessionAttribute.DEFAULT.getValue(), BKOnlineVariable.SessionParameter.LINE_USER_ID.getValue()};
			SessionUtil.removesIgnore(ignore);

			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());

			// user for test
			UserManager userManager = new UserManager(getLogger());
			
			String userId = (String) SessionUtil.get(BKOnlineVariable.SessionParameter.LINE_USER_ID.getValue());
			getLogger().debug("Temp user id [" + userId + "]");
			InhouseUser user = getUser();
			if (userId != null) {
				user = null;
				SessionUtil.remove(BKOnlineVariable.SessionParameter.LINE_USER_ID.getValue());
			}
			
			if (user != null) {
				getLogger().debug("User Not null...");
			} else {
				getLogger().debug("User is Null...");
				
				user = userManager.searchUserInSystem(conn, userId);
				user.setLocale(ParameterConfig.getParameter().getApplication().getApplicationLocale());
				
			}

			Map<String, Operator> mapMenuAndFunction = new LinkedHashMap<String, Operator>();
			Map<String, AdminUser> mapAdminUser = userManager.searchAdminInSystem(conn);
			// search menu
			PermissionManager manager = new PermissionManager(getLogger(), getUser(), getLocale());
			if (mapAdminUser.get(user.getId()) == null) {
				List<String> listAuthori = userManager.searchAuthorizationNormal(conn);
				
				Map<String, Operator> mapAllAuthori = manager.searchMenuAndFunction(conn, ParameterConfig.getParameter().getApplication().getDefaultSecUserIds());
				for (String operatorId : listAuthori) {
					mapMenuAndFunction.put(operatorId, mapAllAuthori.get(operatorId));
				}
			} else {
				mapMenuAndFunction = manager.searchMenuAndFunction(conn, ParameterConfig.getParameter().getApplication().getDefaultSecUserIds());
			}
			
			user.setMapOperator(mapMenuAndFunction);	
			// user back to session
			SessionUtil.put(UserSessionAttribute.DEFAULT.getValue(), user);

			getLogger().debug("Result : " + result);
		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
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
	public String dbLookupInititial() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}
}
