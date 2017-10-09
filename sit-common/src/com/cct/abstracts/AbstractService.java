package com.cct.abstracts;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.common.CommonService;
import com.cct.common.CommonUser;

public abstract class AbstractService extends CommonService {
	
	private CommonUser user = null;
	private Locale locale = null;
	
	public AbstractService(Logger logger, CommonUser user, Locale locale) {
		super(logger);
		this.user = user;
		this.locale = locale;
	}

	public CommonUser getUser() {
		return user;
	}

	public Locale getLocale() {
		return locale;
	}
}
