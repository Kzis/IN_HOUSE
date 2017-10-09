package com.cct.common;

import java.io.Serializable;
import java.util.Locale;

public class CommonSelectItemRequest implements Serializable {

	private static final long serialVersionUID = 2997656883455070131L;
	
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