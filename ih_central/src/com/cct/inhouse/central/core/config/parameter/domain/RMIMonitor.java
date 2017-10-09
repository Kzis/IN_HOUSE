package com.cct.inhouse.central.core.config.parameter.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RMIMonitor implements Serializable {

	private static final long serialVersionUID = -6340821068471904429L;

	private String portStatus;
	private String serverStatus;
	
	private List<Register> lstRegistereds = new ArrayList<Register>();

	public String getPortStatus() {
		return portStatus;
	}

	public void setPortStatus(String portStatus) {
		this.portStatus = portStatus;
	}

	public String getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus;
	}

	public List<Register> getLstRegistereds() {
		return lstRegistereds;
	}

	public void setLstRegistereds(List<Register> lstRegistereds) {
		this.lstRegistereds = lstRegistereds;
	}

}
