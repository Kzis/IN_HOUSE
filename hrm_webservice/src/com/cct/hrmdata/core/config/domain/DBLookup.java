package com.cct.hrmdata.core.config.domain;

public enum DBLookup {
	ORA_OC("0"), MY_SEC("1");

	private String lookup;

	private DBLookup(String lookup) {
		this.lookup = lookup;
	}

	public String getLookup() {
		return lookup;
	}
	
}