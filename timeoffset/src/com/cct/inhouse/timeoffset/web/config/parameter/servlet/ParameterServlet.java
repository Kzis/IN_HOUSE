package com.cct.inhouse.timeoffset.web.config.parameter.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.util.log.LogUtil;

public class ParameterServlet extends HttpServlet {

	private static final long serialVersionUID = -1326931967347560900L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.TO_PARAM.info("");
		try {
			init();
		} catch (Exception e) {
			LogUtil.TO_PARAM.error(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.TO_PARAM.info("");
		try {
			init();
		} catch (Exception e) {
			LogUtil.TO_PARAM.error(e);
		}
	}

	public void init() throws ServletException {
		ParameterConfig.init(this);
	}

}
