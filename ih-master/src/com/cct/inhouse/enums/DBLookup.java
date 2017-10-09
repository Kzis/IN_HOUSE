package com.cct.inhouse.enums;

public enum DBLookup {
	MYSQL_INHOUSE("mysql_inhouse");

	private String lookup;

	private DBLookup(String lookup) {
		this.lookup = lookup;
	}

	public String getLookup() {
		return lookup;
	}
	
}