package com.cct.inhouse.timeoffset.web.config.parameter.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.config.parameter.service.ParameterExtendedManager;
import com.cct.inhouse.timeoffset.util.log.LogUtil;

public class ParameterExtendedConfigServlet extends HttpServlet {

	private static final long serialVersionUID = -1694928035577667440L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.TO_PARAM.info("");
		try {
			init();
		} catch (Exception e) {
			LogUtil.TO_PARAM.error("", e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.TO_PARAM.info("");
		try {
			init();
		} catch (Exception e) {
			LogUtil.TO_PARAM.error("", e);
		}
	}

	public void init() throws ServletException {

		ParameterExtendedManager parameterManager = new ParameterExtendedManager();
		try {
			String parameterFile = getServletContext().getRealPath(getInitParameter("parameterfile"));
			LogUtil.TO_PARAM.debug("Parameter path :- " + parameterFile);

			parameterManager.load(parameterFile);
			LogUtil.TO_PARAM.debug("Load parameter completed.");
			
//			getServletContext().setAttribute("CONTEXT_CENTRAL", ParameterConfig.getParameter().getContextConfig().getURLContextCentral());
//			getServletContext().setAttribute("CONTEXT_OWNER", getServletContext().getContextPath());
//			getServletContext().setAttribute("PREFIX_URL_ICON", ParameterConfig.getParameter().getContextConfig().getURLContextCentral() + "/images/icon");
//			getServletContext().setAttribute("CONTEXT_CAS", ParameterConfig.getParameter().getContextConfig().getURLContextCAS());

			LogUtil.TO_PARAM.debug(" ##### [TEST URL CONTEXT] ##### ");
			LogUtil.TO_PARAM.debug(ParameterConfig.getParameter().getContextConfig().getContextCentral());
			
			LogUtil.TO_PARAM.debug("CONTEXT_CENTRAL(ServletContext) : " + getServletContext().getAttribute("CONTEXT_CENTRAL"));
			LogUtil.TO_PARAM.debug("CONTEXT_CENTRAL(ContextConfig) : " + ParameterConfig.getParameter().getContextConfig().getURLContextCentral());
			
			LogUtil.TO_PARAM.debug("CONTEXT_OWNER(ServletContext) : " + getServletContext().getAttribute("CONTEXT_OWNER"));
			LogUtil.TO_PARAM.debug("CONTEXT_OWNER(ContextConfig) : " + getServletContext().getContextPath());
			
			LogUtil.TO_PARAM.debug("PREFIX_URL_ICON(ServletContext) : " + getServletContext().getAttribute("PREFIX_URL_ICON") );
			LogUtil.TO_PARAM.debug("PREFIX_URL_ICON(ContextConfig) : " + ParameterConfig.getParameter().getContextConfig().getURLContextCentral() + "/images/icon");
			
			LogUtil.TO_PARAM.debug("CONTEXT_CAS(ServletContext) : " + getServletContext().getAttribute("CONTEXT_CAS") );
			LogUtil.TO_PARAM.debug("CONTEXT_CAS(ContextConfig) : " + ParameterConfig.getParameter().getContextConfig().getURLContextCAS());
	
		} catch (Exception e) {
			LogUtil.TO_PARAM.error("", e);
		}

	}

}
