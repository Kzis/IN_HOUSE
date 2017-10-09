package com.cct.common;

import org.apache.log4j.Logger;

public class CommonManager {

	private Logger logger = null;

	public CommonManager(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}
}
