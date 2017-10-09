package com.cct.inhouse.bkonline.core.notification.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.inhouse.common.InhouseModel;

public class NotificationModel extends InhouseModel {

	private static final long serialVersionUID = 8699479122320321142L;

	private String notificationId;
	
	private int totalTodoMessage;
	private List<NotificationMessage> listTodoMessage = new ArrayList<NotificationMessage>();

	private int totalActionMessage;
	private List<NotificationMessage> listActionMessage = new ArrayList<NotificationMessage>();

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public int getTotalTodoMessage() {
		return totalTodoMessage;
	}

	public void setTotalTodoMessage(int totalTodoMessage) {
		this.totalTodoMessage = totalTodoMessage;
	}

	public List<NotificationMessage> getListTodoMessage() {
		return listTodoMessage;
	}

	public void setListTodoMessage(List<NotificationMessage> listTodoMessage) {
		this.listTodoMessage = listTodoMessage;
	}

	public int getTotalActionMessage() {
		return totalActionMessage;
	}

	public void setTotalActionMessage(int totalActionMessage) {
		this.totalActionMessage = totalActionMessage;
	}

	public List<NotificationMessage> getListActionMessage() {
		return listActionMessage;
	}

	public void setListActionMessage(List<NotificationMessage> listActionMessage) {
		this.listActionMessage = listActionMessage;
	}

}
