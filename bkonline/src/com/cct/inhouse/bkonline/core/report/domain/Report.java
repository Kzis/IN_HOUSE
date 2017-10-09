package com.cct.inhouse.bkonline.core.report.domain;

import com.cct.inhouse.common.InhouseDomain;

public class Report extends InhouseDomain {

	private static final long serialVersionUID = 2435178586649432746L;
	
	private String key;
	private String value;
	private boolean checked;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
