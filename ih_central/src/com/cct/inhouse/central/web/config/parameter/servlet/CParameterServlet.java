package com.cct.inhouse.central.web.config.parameter.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cct.inhouse.central.core.config.parameter.domain.CParameterConfig;
import com.cct.inhouse.central.util.log.LogUtil;
import com.cct.inhouse.core.config.parameter.service.ParameterManager;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import util.log.DefaultLogUtil;
import util.string.StringDelimeter;

public class CParameterServlet extends HttpServlet {

	private static final long serialVersionUID = -1326931967347560900L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.INITIAL.info(StringDelimeter.EMPTY.getValue());
		try {
			init();
		} catch (Exception e) {
			LogUtil.INITIAL.error(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.INITIAL.info(StringDelimeter.EMPTY.getValue());
		try {
			init();
		} catch (Exception e) {
			LogUtil.INITIAL.error(e);
		}
	}

	public void init() throws ServletException {

		// ดึง Object ServletContext ของ Central
		ServletContext currentContext = getServletContext();
		try {
			
			// โหลด parameter.xml ผ่าน Method ParameterManager.get()
			String parameterFile = currentContext.getRealPath(
					getInitParameter("parameterfile"));
			LogUtil.INITIAL.debug("Parameter path :- " + parameterFile);
			
			ParameterManager parameterManager = new ParameterManager();
			CParameterConfig.setParameter(parameterManager.get(parameterFile));
			
			// ทดสอบการเชื่อมต่อ Database
			parameterManager.testDBConnection(
					CParameterConfig.getParameter().getDatabase());
			
			LogUtil.INITIAL.debug("Title: " + CParameterConfig.getParameter().getApplication().getTitle());
			LogUtil.INITIAL.debug("Theme :- " + CParameterConfig.getParameter().getApplication().getTheme());
			LogUtil.INITIAL.debug("Load central completed.");
			
//			// สำหรับแรียกใช้งานในหน้า RMI Monitor
			getServletContext().setAttribute("CONTEXT_CENTRAL", CParameterConfig.getParameter().getContextConfig().getServletUrlContextCentral());
			LogUtil.INITIAL.debug("===================================================");
			LogUtil.INITIAL.debug(getServletContext().getAttribute("CONTEXT_CENTRAL"));
			
		} catch (Exception e) {
			LogUtil.INITIAL.error("Can't load paramter!!!", e);
		}
	}
	
	@Override
	public void destroy() {
//		try {
//		    AbandonedConnectionCleanupThread.shutdown();
//		} catch (InterruptedException e) {
//			DefaultLogUtil.INITIAL.error("SEVERE problem cleaning up: " + e.getMessage(), e);
//		}
	}
}
