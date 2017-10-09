package com.cct.inhouse.web.interceptor;

import org.apache.log4j.Logger;
import org.apache.struts2.util.TokenHelper;

import com.cct.common.CommonUser;
import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionReturnType;
import com.cct.enums.UserSessionAttribute;
import com.cct.inhouse.common.InhouseAction;
import com.opensymphony.xwork2.ActionInvocation;

import util.log.DefaultLogUtil;
import util.web.SessionUtil;

public class TokenInterceptor extends org.apache.struts2.interceptor.TokenInterceptor {

	private static final long serialVersionUID = 5521505366657681585L;

	private Logger logger = DefaultLogUtil.INTERCEPTOR;
	
	/**
	 * ตรวจสอบ token ไม่ผ่านให้กลับไปที่หน้า Init
	 */
	protected String handleInvalidToken(ActionInvocation invocation) {
		String tokenName = TokenHelper.getTokenName();
		String tokenForm = TokenHelper.getToken(tokenName);
		String tokenSession = (String) SessionUtil.get(tokenName);
		
		String actionName = invocation.getProxy().getActionName();
		String returnvalue = ActionReturnType.INIT.getResult();
		
		getLogger().warn("uid: " + getUserIdFromSession() + ", sid: " + SessionUtil.getId() 
			+ ", aname: " + actionName + ", tkn: " + tokenName + ", Form token " + tokenForm + " does not match the session token " + tokenSession + ".");
		getLogger().warn("Forward to :- [" + returnvalue + "]");
		
		String[] messages = { ActionMessageType.ERROR.getType(), "Double post.", null };
		SessionUtil.put(InhouseAction.MESSAGE, messages);
		return returnvalue;
	}

	protected String handleValidToken(ActionInvocation invocation) {
		String returnvalue = ActionReturnType.HOME.getResult();
		String actionName = invocation.getProxy().getActionName();
		try {
			returnvalue = invocation.invoke();
		} catch (Exception e) {
			getLogger().error("uid: " + getUserIdFromSession() + ", sid: " + SessionUtil.getId() + ", aname: " + actionName, e);
		}
		return returnvalue;
	}
	
	private String getUserIdFromSession() {
		CommonUser user = (CommonUser) SessionUtil.get(UserSessionAttribute.DEFAULT.getValue());
		return user.getId();
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
