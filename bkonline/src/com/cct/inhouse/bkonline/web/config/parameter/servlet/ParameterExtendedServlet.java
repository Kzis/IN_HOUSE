package com.cct.inhouse.bkonline.web.config.parameter.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cct.inhouse.bkonline.core.config.parameter.domain.ParameterExtended;
import com.cct.inhouse.bkonline.core.config.parameter.domain.ParameterExtendedConfig;
import com.cct.inhouse.bkonline.core.config.parameter.service.ParameterExtendedManager;
import com.cct.inhouse.bkonline.thread.autoapprove.AutoApproveThread;
import com.cct.inhouse.bkonline.thread.checktimeinout.CheckTimeInOutThread;

import util.log.DefaultLogUtil;
import util.string.StringDelimeter;

public class ParameterExtendedServlet extends HttpServlet {

	private static final long serialVersionUID = 7844505563210366302L;

	public static CheckTimeInOutThread checkTimeInOutThread = null;
	public static AutoApproveThread autoApproveThread = null;
	
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
		
		startCheckTimeInOutThread();
		startAutoApproveThread();
		
	}
	
	@Override
	public void destroy() {
		stopCheckTimeInOutThread();
		stopAutoApproveThread();
	}
	
	/**
	 * สำหรับตรวจสอบการ check in and check out
	 */
	private void startCheckTimeInOutThread() {
		try {
			checkTimeInOutThread = new CheckTimeInOutThread();
			checkTimeInOutThread.setDaemon(true);
			checkTimeInOutThread.start();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
	
	private void stopCheckTimeInOutThread() {
		try {
			checkTimeInOutThread.interrupt();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
	
	/**
	 * สำหรับตรวจสอบการอนุมัติ อัตโนมัติ
	 */
	private void startAutoApproveThread() {
		try {
			autoApproveThread = new AutoApproveThread();
			autoApproveThread.setDaemon(true);
			autoApproveThread.start();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
	
	private void stopAutoApproveThread() {
		try {
			autoApproveThread.interrupt();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
}
