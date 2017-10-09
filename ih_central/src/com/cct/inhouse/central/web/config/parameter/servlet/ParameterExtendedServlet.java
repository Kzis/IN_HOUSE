package com.cct.inhouse.central.web.config.parameter.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.log.DefaultLogUtil;
import util.string.StringDelimeter;

import com.cct.inhouse.central.core.config.parameter.domain.ParameterExtended;
import com.cct.inhouse.central.core.config.parameter.domain.ParameterExtendedConfig;
import com.cct.inhouse.central.core.config.parameter.service.ParameterExtendedManager;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

public class ParameterExtendedServlet extends HttpServlet {

	private static final long serialVersionUID = 7844505563210366302L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DefaultLogUtil.INITIAL.info(StringDelimeter.EMPTY.getValue());
		try {
			init();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DefaultLogUtil.INITIAL.info(StringDelimeter.EMPTY.getValue());
		try {
			init();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}

	public void init() throws ServletException {
		// ดึง Object ServletContext ของ BKOnline
		ServletContext currentContext = getServletContext();
		try {
			// โหลด parameter.xml ผ่าน Method ParameterManager.get()
			String parameterFile = currentContext.getRealPath(getInitParameter("parameterfile"));
			DefaultLogUtil.INITIAL.debug("Parameter path :- " + parameterFile);

			ParameterExtendedManager manager = new ParameterExtendedManager();
			ParameterExtended parameterExtended = manager.get(parameterFile);

			ParameterExtendedConfig.setParameterExtended(parameterExtended);

		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
	
	@Override
	public void destroy() {
		try {
		    AbandonedConnectionCleanupThread.shutdown();
		} catch (InterruptedException e) {
			DefaultLogUtil.INITIAL.error("SEVERE problem cleaning up: " + e.getMessage(), e);
		}
	}
	
}
