package com.cct.inhouse.bkonline.web.mobile.servlet;

import org.apache.log4j.Logger;

import com.cct.inhouse.bkonline.util.log.LogUtil;

public class AndroidServlet extends MobileServlet {

	private static final long serialVersionUID = 1712517162125380557L;

	@Override
	public Logger loggerInititial() {
		return LogUtil.ANDROID;
	}

}
