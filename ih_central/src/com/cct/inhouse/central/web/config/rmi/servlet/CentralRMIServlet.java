package com.cct.inhouse.central.web.config.rmi.servlet;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cct.inhouse.central.core.config.parameter.domain.CParameterConfig;
import com.cct.inhouse.central.rmi.interfaces.ICentralRMIManager;
import com.cct.inhouse.central.rmi.service.CentralRMIManager;

import util.log.DefaultLogUtil;

public class CentralRMIServlet extends HttpServlet{

	private static final long serialVersionUID = -5962385044405066997L;

	private static ICentralRMIManager cnRmiObj = null;
	
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
			registCnRmi();
			
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}

	private void registCnRmi() throws Exception {
		try {
			// Create remote object
			cnRmiObj = new CentralRMIManager();
			
			// Export remote object
			UnicastRemoteObject.unexportObject(cnRmiObj, true);
			ICentralRMIManager cnRmi = (ICentralRMIManager)
					UnicastRemoteObject.exportObject(cnRmiObj, 0);
			
			DefaultLogUtil.INITIAL.debug("PORT :" + CParameterConfig.getParameter().getRmi().getPort());
			
			Registry registry = LocateRegistry.getRegistry(
					CParameterConfig.getParameter().getRmi().getPort());
			
			registry.rebind(ICentralRMIManager.REMOTE_NAME, cnRmi);
			
			DefaultLogUtil.INITIAL.debug("Registry Central RMI Complete!");
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void destroy() {
		try {
			UnicastRemoteObject.unexportObject(cnRmiObj, true);
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
}
