package com.cct.inhouse.central.web.config.rmi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONUtil;

import util.log.DefaultLogUtil;

import com.cct.enums.CharacterEncoding;
import com.cct.enums.ContentType;
import com.cct.inhouse.central.core.config.parameter.domain.CParameterConfig;
import com.cct.inhouse.central.core.config.parameter.domain.ParameterExtendedConfig;
import com.cct.inhouse.central.core.config.parameter.domain.RMIMonitor;
import com.cct.inhouse.central.core.config.parameter.domain.Register;
import com.cct.inhouse.central.util.log.LogUtil;
import com.cct.inhouse.domain.GlobalVariable;

public class RMIMonitorServlet extends HttpServlet {

	private static final long serialVersionUID = -3364504037222313632L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		init(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		init(req, resp);
	}
	
	public void init(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			DefaultLogUtil.INITIAL.debug("RMICheckStatusServlet");
			
			RMIMonitor monitor = checkRMIStatus();
					
			String jsonString = JSONUtil.serialize(monitor, null, null, false, true);
			resp.setCharacterEncoding(CharacterEncoding.UTF8.getValue());
			resp.setContentType(ContentType.APPLICATION_JSON.getValue());
			
			PrintWriter out = resp.getWriter();
			out.print(jsonString);
			
			DefaultLogUtil.INITIAL.debug(jsonString);
			
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
	
	private RMIMonitor checkRMIStatus() {
		RMIMonitor monitor = new RMIMonitor();
		try {
			DefaultLogUtil.INITIAL.debug(LocateRegistry.getRegistry(CParameterConfig.getParameter().getRmi().getPort()));
			Registry registry = LocateRegistry.getRegistry(CParameterConfig.getParameter().getRmi().getPort());
			try {
				String[] arrRegisted = registry.list();
				// FIXME
				LogUtil.INITIAL.debug("========================================");
				for (String string : arrRegisted) {
					LogUtil.INITIAL.debug(string);
				}
				
				monitor.setPortStatus(GlobalVariable.FLAG_ACTIVE);
				
				ArrayList<Register> lstRegisterStatus = new ArrayList<Register>();
				
				// loop ตาม parameter
				Register[] registers = ParameterExtendedConfig.getParameterExtended().getRegisters();
				for (Register register : registers) {
					if (register.getActive().equalsIgnoreCase(GlobalVariable.FLAG_ACTIVE)) {
						Register reg = register;
						reg.setRegistStatus(GlobalVariable.FLAG_INACTIVE);
						
						for (String registered : arrRegisted) {
							if (register.getRegistKey().equalsIgnoreCase(registered)) {
								reg.setRegistStatus(GlobalVariable.FLAG_ACTIVE);
							}
						}
						
						lstRegisterStatus.add(reg);
					}
				}
				
				monitor.setLstRegistereds(lstRegisterStatus);
				
			} catch (Exception e) {
				DefaultLogUtil.INITIAL.debug("Port is Not Opened");
				monitor.setPortStatus(GlobalVariable.FLAG_INACTIVE);
			}
			
			DefaultLogUtil.INITIAL.debug("Port : " + monitor.getPortStatus());
			for (Register registerStatus : monitor.getLstRegistereds()) {
				DefaultLogUtil.INITIAL.debug(registerStatus.getRegistName() + " : " + registerStatus.getRegistStatus());
			}
			
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
		return monitor;
	}

}
