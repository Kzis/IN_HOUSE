package com.cct.inhouse.bkonline.util.log;

import org.apache.log4j.Logger;

import util.log.DefaultLogUtil;

public class LogUtil extends DefaultLogUtil {

	public static final Logger SETTING = Logger.getLogger("SETTING");
	public static final Logger BOOKING =Logger.getLogger("BOOKING");
	public static final Logger CALENDAR = Logger.getLogger("CALENDAR");
	public static final Logger REPORT = Logger.getLogger("REPORT");
	public static final Logger SEC = Logger.getLogger("SEC");
	public static final Logger CHECK_TIME_IN_OUT = Logger.getLogger("TIME_IN_OUT");
	public static final Logger AUTO_APPROVE = Logger.getLogger("AUTO_APPROVE");
	public static final Logger ANDROID = Logger.getLogger("ANDROID");
	public static final Logger IOS = Logger.getLogger("IOS");
}
