package com.cct.inhouse.core.standard.department.domain;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.cct.abstracts.AbstractSelectItemObject;
import com.cct.common.CommonSelectItemObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class Department extends AbstractSelectItemObject {

	private static final long serialVersionUID = 7568091056118505992L;

	@SerializedName(value="departmentId")
	private String departmentId;
	
	@SerializedName(value="departmentCode")
	private String departmentCode;
	
	@SerializedName(value="departmentName")
	private String departmentName;
	
	@SerializedName(value="departmentDesc")
	private String departmentDesc;

	public Department() {

	}
	
	public Department(ResultSet rst) throws Exception {
		super(rst);
		if (rst == null) {
			return;
		}
		
		try {
			setDepartmentId(rst.getString("DEPARTMENT_ID"));
			setDepartmentCode(rst.getString("DEPARTMENT_CODE"));
			setDepartmentName(rst.getString("DEPARTMENT_NAME"));
			setDepartmentDesc(rst.getString("DEPARTMENT_DESC"));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * กำหนด type ให้กับ json เป็นแบบ ArrayList ของ CommonSelectItemObject<Department>
	 * @return
	 */
	public static Type getGenericTypeArrayList() {
		return new TypeToken<ArrayList<CommonSelectItemObject<Department>>>(){}.getType();
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

}
