package com.cct.inhouse.bkonline.web.config.rmi.servlet;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.log.DefaultLogUtil;

import com.cct.inhouse.bkonline.rmi.interfaces.IBKOnlineRMIManager;
import com.cct.inhouse.bkonline.rmi.service.BKOnlineRMIManager;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

public class BKOnlineRMIServlet extends HttpServlet {

	private static final long serialVersionUID = -5962385044405066997L;

	private static IBKOnlineRMIManager bkRmiObj = null;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		init();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		init();
	}
	
	public void init() throws ServletException {
		try {
			// Registry Central RMI if port is opened
			registBkRmi();
			
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}

	private void registBkRmi() throws Exception {
		try {
			// Create remote object
			bkRmiObj = new BKOnlineRMIManager();
			
			// Export remote object
			UnicastRemoteObject.unexportObject(bkRmiObj, true);
			IBKOnlineRMIManager bkRmi = (IBKOnlineRMIManager) UnicastRemoteObject.exportObject(bkRmiObj, 0);
			
			DefaultLogUtil.INITIAL.debug("PORT :" + ParameterConfig.getParameter().getRmi().getPort());
			Registry registry = LocateRegistry.getRegistry(ParameterConfig.getParameter().getRmi().getPort());
			
			registry.rebind(IBKOnlineRMIManager.REMOTE_NAME, bkRmi);
			
			DefaultLogUtil.INITIAL.debug("Registry BKOnline RMI Complete!");
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void destroy() {
		try {
			// unbind ข้อมูลออกจาก port RMI
			Registry registry = LocateRegistry.getRegistry(ParameterConfig.getParameter().getRmi().getPort());
			registry.unbind(IBKOnlineRMIManager.REMOTE_NAME);
			
			UnicastRemoteObject.unexportObject(bkRmiObj, true);
			
			AbandonedConnectionCleanupThread.shutdown();
			
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
}
