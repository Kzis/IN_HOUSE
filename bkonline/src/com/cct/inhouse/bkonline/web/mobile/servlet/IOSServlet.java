package com.cct.inhouse.bkonline.web.mobile.servlet;

import org.apache.log4j.Logger;

import com.cct.inhouse.bkonline.util.log.LogUtil;

public class IOSServlet extends MobileServlet {

	private static final long serialVersionUID = 7391436663956358373L;

	@Override
	public Logger loggerInititial() {
		return LogUtil.IOS;
	}

}
