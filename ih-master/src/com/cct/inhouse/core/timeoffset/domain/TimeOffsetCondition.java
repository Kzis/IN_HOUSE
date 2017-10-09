package com.cct.inhouse.core.timeoffset.domain;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.cct.abstracts.AbstractSelectItemObject;
import com.cct.common.CommonSelectItemObject;
import com.cct.inhouse.core.standard.department.domain.Department;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class TimeOffsetCondition extends AbstractSelectItemObject {

	private static final long serialVersionUID = 7568091056118505992L;

	@SerializedName(value = "projConId")
	private String projConId;

	@SerializedName(value = "projectId")
	private String projectId;

	@SerializedName(value = "projConDetail")
	private String projConDetail;

	@SerializedName(value = "projConFlag")
	private String projConFlag;

	@SerializedName(value = "hourTot")
	private String hourTot;

	public TimeOffsetCondition() {

	}

	public TimeOffsetCondition(ResultSet rst) throws Exception {
		super(rst);
		if (rst == null) {
			return;
		}

		try {
			setProjConId(rst.getString("PROJ_CON_ID"));
			setProjectId(rst.getString("PROJECT_ID"));
			setProjConDetail(rst.getString("PROJ_CON_DETAIL"));
			setProjConFlag(rst.getString("PROJ_CON_FLAG"));
			setHourTot(rst.getString("HOUR_TOT"));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * กำหนด type ให้กับ json เป็นแบบ ArrayList ของ
	 * CommonSelectItemObject<Department>
	 * 
	 * @return
	 */
	public static Type getGenericTypeArrayList() {
		return new TypeToken<ArrayList<CommonSelectItemObject<Department>>>() {
		}.getType();
	}

	public String getProjConId() {
		return projConId;
	}

	public void setProjConId(String projConId) {
		this.projConId = projConId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjConDetail() {
		return projConDetail;
	}

	public void setProjConDetail(String projConDetail) {
		this.projConDetail = projConDetail;
	}

	public String getProjConFlag() {
		return projConFlag;
	}

	public void setProjConFlag(String projConFlag) {
		this.projConFlag = projConFlag;
	}

	public String getHourTot() {
		return hourTot;
	}

	public void setHourTot(String hourTot) {
		this.hourTot = hourTot;
	}

}
