package com.cct.inhouse.core.config.parameter.domain;



public class Rmi extends MergeParameter {

	private static final long serialVersionUID = 1061894440878031750L;

	private int port;

	public Rmi clone() {
		Rmi cloneObj = new Rmi();
		
		// setter/getter
		cloneObj.merge(this);
		
		return cloneObj;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


}
