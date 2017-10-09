package com.cct.inhouse.timeoffset.core.security.user.domain;

import com.cct.inhouse.common.InhouseUser;

public class TempUser extends InhouseUser {

	private static final long serialVersionUID = -2554341782158679735L;

	private String lineDisplayName;

	public String getLineDisplayName() {
		return lineDisplayName;
	}

	public void setLineDisplayName(String lineDisplayName) {
		this.lineDisplayName = lineDisplayName;
	}
	
}
