package com.cct.inhouse.bkonline.core.security.user.domain;

import com.cct.inhouse.common.InhouseUser;

public class AdminUser extends InhouseUser {

	private static final long serialVersionUID = -2554341782158679735L;

	private String adminId;
	private String systemCode;
	
	public String getAdminId() {
		return adminId;
	}
	
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	public String getSystemCode() {
		return systemCode;
	}
	
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
}
