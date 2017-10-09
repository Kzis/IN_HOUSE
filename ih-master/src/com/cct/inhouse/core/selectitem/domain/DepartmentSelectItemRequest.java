package com.cct.inhouse.core.selectitem.domain;

import com.cct.common.CommonSelectItemRequest;
import com.cct.inhouse.core.standard.department.domain.Department;


public class DepartmentSelectItemRequest extends CommonSelectItemRequest {

	private static final long serialVersionUID = -6285936377717361452L;
	
	private Department department;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}
