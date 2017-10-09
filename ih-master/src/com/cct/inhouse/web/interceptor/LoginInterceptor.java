package com.cct.inhouse.web.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionReturnType;
import com.cct.enums.LanguageSessionAttribute;
import com.cct.exception.AuthenticateException;
import com.cct.exception.AuthorizationException;
import com.cct.exception.DefaultExceptionMessage;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.domain.GlobalVariable;
import com.cct.inhouse.util.IHUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import util.log.DefaultLogUtil;
import util.web.SessionUtil;

public class LoginInterceptor implements Interceptor {

	private static final long serialVersionUID = -1921944000166163942L;

	private Logger logger = DefaultLogUtil.INTERCEPTOR;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String returnvalue = ActionReturnType.HOME.getResult();
		String localeNameFromParameter = null;
		InhouseUser commonUser = null;
		String userId = null;
		String sessionId = null;
		String actionName = invocation.getProxy().getActionName();
		
		try {
			// Set ค่าลงตัวแปรเพื่อใช้ในการตรวจสอบ
			localeNameFromParameter = SessionUtil.requestParameter(LanguageSessionAttribute.DEFAULT.getValue());
			commonUser = getUserFromSession();
			userId = getUserIdFromSession();
			sessionId = SessionUtil.getId();
		} catch (Exception e) {
			manageException(e);
			return returnvalue;
		}

		getLogger().debug("uid: " + userId + ", actionName: " + actionName);
		
		try {
			// Action ดังต่อไปนี้ไม่ต้องตรวจสอบ User
			if (GlobalVariable.MAP_OF_ACTION_NAME_FOR_SKIP_AUTHENTICATE.get(actionName) != null) {
				getLogger().debug("Skip authen: " + actionName);
				setLocaleToAction(invocation, localeNameFromParameter, commonUser);
				setNoCache(invocation);
				return invocation.invoke();
			}
		} catch (Exception e) {
			manageException(e);
			return returnvalue;
		}

		
		try {
			// Action ดังต่อไปนี้ต้องตรวจสอบ User
			if (commonUser == null) {
				// ไม่มี User ใน Session
				setLocaleToAction(invocation, localeNameFromParameter, commonUser);
				String[] messages = { ActionMessageType.ERROR.getType(), DefaultExceptionMessage.SESSION_EXPIRED, null };
				SessionUtil.put(InhouseAction.MESSAGE, messages);
			} else {
				// มี User ใน Session ให้ทำงานได้
				setLocaleToAction(invocation, localeNameFromParameter, commonUser);
				setNoCache(invocation);
				returnvalue = invocation.invoke();
			}
		} catch (Exception e) {
			manageException(e);
		}
		return returnvalue;
	}

	private void setLocaleToAction(ActionInvocation invocation, String localeNameFromParameter, InhouseUser commonUser) {
		if (commonUser == null) {
			if ((localeNameFromParameter == null) || localeNameFromParameter.trim().isEmpty()) {
				if ((SessionUtil.get(LanguageSessionAttribute.DEFAULT.getValue())) == null) {
					//LogUtil.INTERCEPTOR.debug("ถ้าไม่มี user และ ไม่มีการขอเปลื่ยน locale ใช้ default from application");
					localeNameFromParameter = IHUtil.getParameter().getApplication().getApplicationLocale().getLanguage();
				} else {
					localeNameFromParameter = SessionUtil.get(LanguageSessionAttribute.DEFAULT.getValue()).toString();
				}
				invocation.getInvocationContext().setLocale(new Locale(localeNameFromParameter.toLowerCase(), localeNameFromParameter.toUpperCase()));

			} else {// รอรับกรณีหน้าที่ไม่มีการ login
				//LogUtil.INTERCEPTOR.debug("ถ้าไม่มี user แต่มีการขอเปลื่ยน locale ให้ใช้ locale จากที่ขอเปลื่ยน");
				SessionUtil.put(LanguageSessionAttribute.DEFAULT.getValue(), localeNameFromParameter);
				invocation.getInvocationContext().setLocale(new Locale(localeNameFromParameter.toLowerCase(), localeNameFromParameter.toUpperCase()));
			}
		} else {
			if ((localeNameFromParameter == null) || localeNameFromParameter.trim().isEmpty()) {
				if ((SessionUtil.get(LanguageSessionAttribute.DEFAULT.getValue())) == null) {
					//LogUtil.INTERCEPTOR.debug("ถ้ามี user และไม่มีการขอเปลื่ยน locale ให้ใช้ locale from user");
					localeNameFromParameter = commonUser.getLocale().getLanguage();
				} else {
					localeNameFromParameter = SessionUtil.get(LanguageSessionAttribute.DEFAULT.getValue()).toString();
				}
				invocation.getInvocationContext().setLocale(new Locale(localeNameFromParameter.toLowerCase(), localeNameFromParameter.toUpperCase()));
			} else {
				//LogUtil.INTERCEPTOR.debug("ถ้ามี user และมีการขอเปลื่ยน locale ให้ใช้ locale จากที่ขอเปลื่ยน และกำหนดค่าให้กับ user locale ด้วย");
				SessionUtil.put(LanguageSessionAttribute.DEFAULT.getValue(), localeNameFromParameter);
				commonUser.setLocale(new Locale(localeNameFromParameter.toLowerCase(), localeNameFromParameter.toUpperCase()));
				invocation.getInvocationContext().setLocale(commonUser.getLocale());
			}
		}
	}

	private void setNoCache(ActionInvocation invocation) {
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setHeader("Cache-Control", "no-cache");

		// Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store");

		// Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache");

		// Directs caches not to store the page under any circumstance
		response.setDateHeader("Expires", 0);
	}

	private void manageException(Exception e) {
		getLogger().error("uid: " + getUserIdFromSession() + ", sid: "+ SessionUtil.getId(), e);
		if (e instanceof AuthorizationException) {
			String[] messages = { ActionMessageType.ERROR.getType(), DefaultExceptionMessage.NO_PERMISSIONS, null };
			SessionUtil.put(InhouseAction.MESSAGE, messages);
		} else if (e instanceof AuthenticateException) {
			String[] messages = { ActionMessageType.ERROR.getType(), DefaultExceptionMessage.SESSION_EXPIRED, null };
			SessionUtil.put(InhouseAction.MESSAGE, messages);
		} else {
			String[] messages = { ActionMessageType.ERROR.getType(), e.getMessage(), InhouseAction.getErrorMessage(e) };
			SessionUtil.put(InhouseAction.MESSAGE, messages);
		}
	}
	
	private String getUserIdFromSession() {
		String userId = null;
		InhouseUser user = getUserFromSession();
		if (user != null) {
			userId = user.getId();
		}
		return userId;
	}

	private InhouseUser getUserFromSession() {
		InhouseUser user = InhouseAction.getUser();
		return user;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public void destroy() {	}

	@Override
	public void init() { }
}
