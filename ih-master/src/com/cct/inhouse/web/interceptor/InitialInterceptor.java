package com.cct.inhouse.web.interceptor;

import org.apache.log4j.Logger;

import util.log.DefaultLogUtil;

import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.domain.GlobalVariable;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class InitialInterceptor implements Interceptor {

	private static final long serialVersionUID = -7903274294098317807L;
	
	private Logger logger = DefaultLogUtil.INTERCEPTOR;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String returnvalue = GlobalVariable.MONITOR;
		try {
			// ตรวจสอบ Initial Parameter
			if (ParameterConfig.getParameter() == null) {
				return returnvalue;
			}
			
			// ตรวจสอบ Initial GlobalData
			if (ParameterConfig.getMapGlobalData() == null) {
				return returnvalue;
			}
			
		} catch (Exception e) {
			return returnvalue;
		}
		return invocation.invoke();
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
		
	}

}
