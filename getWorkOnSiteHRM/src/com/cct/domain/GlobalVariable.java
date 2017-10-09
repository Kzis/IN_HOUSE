package com.cct.domain;

import java.io.Serializable;

public class GlobalVariable implements Serializable {

	private static final long serialVersionUID = 7226578754399777637L;

	public static final String CONFIG_PARAMETER_FILE = "getworkonsite-parameter.xml";
	public static final String CONFIG_LOG4J_FILE = "getworkonsite-log4j.properties";
	
	
	public static final String PROCESS_STATUS_W = "W";
	public static final String PROCESS_STATUS_Y = "Y";
	public static final String PROCESS_STATUS_N = "N";
}