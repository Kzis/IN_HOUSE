package com.cct.inhouse.bkonline.core.mobile.domain;

import java.util.ArrayList;
import java.util.List;

public class MDNotification {

	private String selectNotificationId;
	
	private int totalTodoMessage;
	private List<MDNotificationMessage> listTodoMessage = new ArrayList<MDNotificationMessage>();

	private int totalActionMessage;
	private List<MDNotificationMessage> listActionMessage = new ArrayList<MDNotificationMessage>();

	public int getTotalTodoMessage() {
		return totalTodoMessage;
	}

	public void setTotalTodoMessage(int totalTodoMessage) {
		this.totalTodoMessage = totalTodoMessage;
	}

	public List<MDNotificationMessage> getListTodoMessage() {
		return listTodoMessage;
	}

	public void setListTodoMessage(List<MDNotificationMessage> listTodoMessage) {
		this.listTodoMessage = listTodoMessage;
	}

	public int getTotalActionMessage() {
		return totalActionMessage;
	}

	public void setTotalActionMessage(int totalActionMessage) {
		this.totalActionMessage = totalActionMessage;
	}

	public List<MDNotificationMessage> getListActionMessage() {
		return listActionMessage;
	}

	public void setListActionMessage(List<MDNotificationMessage> listActionMessage) {
		this.listActionMessage = listActionMessage;
	}

	public String getSelectNotificationId() {
		return selectNotificationId;
	}

	public void setSelectNotificationId(String selectNotificationId) {
		this.selectNotificationId = selectNotificationId;
	}

}
