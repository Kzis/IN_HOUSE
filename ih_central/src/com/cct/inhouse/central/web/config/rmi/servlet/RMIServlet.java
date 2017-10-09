package com.cct.inhouse.central.web.config.rmi.servlet;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cct.inhouse.central.core.config.parameter.domain.CParameterConfig;
import com.cct.inhouse.central.util.log.LogUtil;

import util.log.DefaultLogUtil;

public class RMIServlet extends HttpServlet{
	private static final long serialVersionUID = -5724888482003755231L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		init();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		init();
	}
	
	public void init() throws ServletException {
		
		// ดึง Object ServletContext ของ Central
		ServletContext currentContext = getServletContext();
		try {
			// เปิด PORT
			LocateRegistry.createRegistry(CParameterConfig.getParameter().getRmi().getPort());

			// Grant สิทธิ์ ให้ RMI สามารถ เข้าถึง class java
			String policyfile = currentContext.getRealPath(getInitParameter("security_policy"));
			LogUtil.INITIAL.debug("policyfile path :- " + policyfile);
			System.setProperty("java.security.policy","file:/" + policyfile);
			
			DefaultLogUtil.INITIAL.debug(" RMI Open Port : ["+CParameterConfig.getParameter().getRmi().getPort()+"]  And grant Permission Complete!!!");
		
		} catch (Exception ex) {
			DefaultLogUtil.INITIAL.error(ex);
		}
	}
}
