package com.cct.getworkonsite.core.config.parameter.domain;

import java.io.Serializable;

public class DateFormat implements Serializable {

	private static final long serialVersionUID = -7829158200049419117L;

	private String forDisplayHHMMSS;
	private String forDatabaseSelectHHMMSS;

	public String getForDisplayHHMMSS() {
		return forDisplayHHMMSS;
	}

	public void setForDisplayHHMMSS(String forDisplayHHMMSS) {
		this.forDisplayHHMMSS = forDisplayHHMMSS;
	}

	public String getForDatabaseSelectHHMMSS() {
		return forDatabaseSelectHHMMSS;
	}

	public void setForDatabaseSelectHHMMSS(String forDatabaseSelectHHMMSS) {
		this.forDatabaseSelectHHMMSS = forDatabaseSelectHHMMSS;
	}

}
