package com.cct.inhouse.central.rmi.domain;

import java.io.Serializable;
import java.util.Locale;

import com.cct.common.CommonUser;

public class RMISelectItemRequest implements Serializable {

	private static final long serialVersionUID = -3122068807453117594L;
	
	private String term;
	private String limit;
	private CommonUser commonUser;
	private Locale locale;
	
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public CommonUser getCommonUser() {
		return commonUser;
	}

	public void setCommonUser(CommonUser commonUser) {
		this.commonUser = commonUser;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
