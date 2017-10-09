package com.cct.inhouse.enums;

/**
 * สำหรับอ้างอิง attribute ที่แชร์มาจาก central
 * @author sittipol.m
 *
 */
public enum CentralContextAttribute {
	
	SHARED_PARAMETER("=kxCs=d-were"),
	SHARED_GLOBAL_DATA("=kxCs=d-erew"),
	CENTRAL_CONTEXT_NAME("CENTRAL_CONTEXT_NAME"),
	LOCAL_CONTEXT("LOCAL_CONTEXT"),
	LOCAL_PARAMETER("LOCAL_PARAMETER"),
	PARAMETER("PARAMETER"),
	MAP_GLOBAL_DATA("MAP_GLOBAL_DATA"),
	MAP_CONTENT_TYPE("MAP_CONTENT_TYPE")
	;
	
	private String value;

	private CentralContextAttribute(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
