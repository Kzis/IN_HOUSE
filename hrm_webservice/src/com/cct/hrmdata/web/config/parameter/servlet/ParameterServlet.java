package com.cct.hrmdata.web.config.parameter.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.log.LogUtil;

import com.cct.hrmdata.core.config.domain.ParameterConfig;
import com.cct.hrmdata.core.config.service.ParameterManager;

public class ParameterServlet extends HttpServlet {

	private static final long serialVersionUID = -1326931967347560900L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.INITIAL.info("");
		try {
			init();
		} catch (Exception e) {
			LogUtil.INITIAL.error("", e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.INITIAL.info("");
		try {
			init();
		} catch (Exception e) {
			LogUtil.INITIAL.error("", e);
		}
	}

	public void init() throws ServletException {

		ParameterManager parameterManager = new ParameterManager();

		try {
			String parameterFile = getServletContext().getRealPath(getInitParameter("parameterfile"));
			LogUtil.INITIAL.debug("Parameter path :- " + parameterFile);

			parameterManager.load(parameterFile);


			LogUtil.INITIAL.debug("Load parameter completed.");
			LogUtil.INITIAL.debug(ParameterConfig.getParameter().getDatabase());

		} catch (Exception e) {
			LogUtil.INITIAL.error("Can't load paramter!!!");
			LogUtil.INITIAL.error("", e);
		}

	}
}
