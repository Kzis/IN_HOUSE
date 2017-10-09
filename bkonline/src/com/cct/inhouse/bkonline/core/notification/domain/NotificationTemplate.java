package com.cct.inhouse.bkonline.core.notification.domain;

import com.cct.inhouse.common.InhouseDomain;

public class NotificationTemplate extends InhouseDomain {

	private static final long serialVersionUID = -6359063116341612579L;

	private String subject;
	private String detail;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
