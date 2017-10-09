package com.cct.inhouse.core.security.user.domain;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.cct.abstracts.AbstractSelectItemObject;
import com.cct.common.CommonSelectItemObject;
import com.google.gson.reflect.TypeToken;

public class User extends AbstractSelectItemObject {
	
	private static final long serialVersionUID = 1256941103095500345L;
	
	private String userId;
	private String departmentId;
	private String positionId;
	private String nameWithNickName;
	private String insidePhoneNumber;
	private String email;
	private String lineUserId;
	  
	public User() {
		
	}
	
	public User(ResultSet rst) throws Exception {
		super(rst);
		if (rst == null) {
			return;
		}
		
		try {
			setUserId(rst.getString("USER_ID"));
			setDepartmentId(rst.getString("DEPARTMENT_ID"));
			setPositionId(rst.getString("POSITION_ID"));
			setNameWithNickName(rst.getString("BOOKING_NAME"));
			setInsidePhoneNumber(rst.getString("INSIDE_PHONE_NUMBER"));
			setEmail(rst.getString("EMAIL"));
			setLineUserId(rst.getString("LINE_USER_ID"));
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * กำหนด type ให้กับ json เป็นแบบ ArrayList ของ CommonSelectItemObject<User>
	 * @return
	 */
	public static Type getGenericTypeArrayList() {
		return new TypeToken<ArrayList<CommonSelectItemObject<User>>>(){}.getType();
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getNameWithNickName() {
		return nameWithNickName;
	}

	public void setNameWithNickName(String nameWithNickName) {
		this.nameWithNickName = nameWithNickName;
	}

	public String getInsidePhoneNumber() {
		return insidePhoneNumber;
	}

	public void setInsidePhoneNumber(String insidePhoneNumber) {
		this.insidePhoneNumber = insidePhoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLineUserId() {
		return lineUserId;
	}

	public void setLineUserId(String lineUserId) {
		this.lineUserId = lineUserId;
	}
	
}
