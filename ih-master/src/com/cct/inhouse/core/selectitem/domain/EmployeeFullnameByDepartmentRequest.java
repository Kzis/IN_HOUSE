package com.cct.inhouse.core.selectitem.domain;

import com.cct.common.CommonSelectItemRequest;

public class EmployeeFullnameByDepartmentRequest extends CommonSelectItemRequest {

	private static final long serialVersionUID = -2352901849328699803L;
	
	private String departmentId;

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	
}

