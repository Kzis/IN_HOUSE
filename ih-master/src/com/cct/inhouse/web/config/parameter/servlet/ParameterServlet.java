package com.cct.inhouse.web.config.parameter.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.log.DefaultLogUtil;
import util.string.StringDelimeter;

import com.cct.inhouse.core.config.parameter.domain.ContextConfig;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

public class ParameterServlet extends HttpServlet {

	private static final long serialVersionUID = -1326931967347560900L;

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
		ParameterConfig.init(this);
		initGlobalAttribute();
	}

	public void initGlobalAttribute() {
		ContextConfig contextConfig = ParameterConfig.getParameter().getContextConfig();
		String contextOwner = getServletContext().getContextPath();
		getServletContext().setAttribute("CONTEXT_OWNER", contextOwner);
		getServletContext().setAttribute("CONTEXT_CENTRAL", contextConfig.getURLContextCentral());
		getServletContext().setAttribute("PREFIX_URL_ICON", contextConfig.getURLContextCentral() + "/images/icon");
		getServletContext().setAttribute("CONTEXT_CAS", contextConfig.getURLContextCAS());
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
