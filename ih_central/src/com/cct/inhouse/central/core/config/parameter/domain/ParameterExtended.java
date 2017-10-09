package com.cct.inhouse.central.core.config.parameter.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParameterExtended implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3797363030515668179L;

	private Register[] registers;

	public Register[] getRegisters() {
		return registers;
	}

	public void setRegisters(Register[] registers) {
		this.registers = registers;
	}

}
