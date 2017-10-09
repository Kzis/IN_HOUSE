package com.cct.common;

import org.apache.log4j.Logger;

public class CommonService {

	private Logger logger = null;

	public CommonService(Logger logger) {
		this.logger = logger;
	}
	
	public Logger getLogger() {
		return logger;
	}
}
