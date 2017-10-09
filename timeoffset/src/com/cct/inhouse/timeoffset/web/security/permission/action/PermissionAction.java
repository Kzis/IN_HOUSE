package com.cct.inhouse.timeoffset.web.security.permission.action;

import org.apache.log4j.Logger;

import com.cct.enums.LanguageSessionAttribute;
import com.cct.enums.UserSessionAttribute;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.timeoffset.core.security.permission.service.PermissionManager;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

public class PermissionAction extends InhouseAction {
	
	private static final long serialVersionUID = 6498977971884514500L;

	public PermissionAction() {
		
		try {

			// user for test
			InhouseUser user = getUser();
			getLogger().debug("User is Null...");
			user = new InhouseUser();
			user.setId("218");
			user.setName("Admin");
			user.setFullName("NAME FIX");
			user.setGivenName("Given");
			user.setFamilyName("Family");
			user.setLocale(ParameterConfig.getParameter().getApplication().getApplicationLocale());

			// user back to session
			SessionUtil.put(UserSessionAttribute.DEFAULT.getValue(), user);

		} catch (Exception e) {
			getLogger().error(e);
		}
		
	}

	public String init() {
		getLogger().debug(StringDelimeter.BLANK.getValue());

		String result = "initScheduler";

		CCTConnection conn = null;
		try {
			// ลบ object ทุกอย่างออกจาก session ยกเว้น ignore user และภาษา
			String[] ignore = { UserSessionAttribute.DEFAULT.getValue(), LanguageSessionAttribute.DEFAULT.getValue()};
			SessionUtil.removesIgnore(ignore);

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			// user for test
			InhouseUser user = getUser();
			if (user != null) {
				getLogger().debug("User Not null...");
			} else {
				getLogger().debug("User is Null...");
				user = new InhouseUser();
				user.setId("218");
				user.setName("Admin");
				user.setGivenName("Given");
				user.setFamilyName("Family");
				user.setFullName("NAME FIX");
				user.setLocale(ParameterConfig.getParameter().getApplication().getApplicationLocale());
			}

			// search menu
			PermissionManager manager = new PermissionManager(getLogger(), getUser(), getLocale());
			user.setMapOperator(manager.searchMenuAndFunction(conn, user.getId()));

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
		return LogUtil.TO_UTIL;
	}
}
