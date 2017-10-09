package com.cct.inhouse.bkonline.web.notification.action;

import org.apache.log4j.Logger;

import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionReturnType;
import com.cct.inhouse.bkonline.core.notification.domain.NotificationModel;
import com.cct.inhouse.bkonline.core.notification.service.NotificationManager;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class NotificationAction extends InhouseAction implements ModelDriven<NotificationModel> {

	private static final long serialVersionUID = 3320543265505478804L;
	
	private NotificationModel model = new NotificationModel();

	public String ajaxSearchMessage() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			searchNotification(conn);
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	public String ajaxClearMessage() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			NotificationManager manager = new NotificationManager(getLogger());
			manager.clearNotificationMessage(conn, getModel().getNotificationId(), getUser());
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		} finally {
			searchNotification(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	private void searchNotification(CCTConnection conn) {
		try {
			NotificationManager manager = new NotificationManager(getLogger());
			getModel().setTotalTodoMessage(manager.searchCountNotificationStatusTodo(conn, getUser()));
			getModel().setListTodoMessage(manager.searchNotificationTodo(conn, getUser()));
			
			getModel().setTotalActionMessage(manager.searchCountNotificationStatusAction(conn, getUser()));
			getModel().setListActionMessage(manager.searchNotificationAction(conn, getUser()));
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	@Override
	public NotificationModel getModel() {
		return model;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.CALENDAR;
	}
	
	@Override
	public String dbLookupInititial() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}
	
}
