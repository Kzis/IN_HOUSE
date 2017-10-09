package com.cct.inhouse.timeoffset.core.report.toreport.domain;

import com.cct.inhouse.common.InhouseDomain;

public class TOReport extends InhouseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -532685947948611501L;

	private String projConName;
	private String projectName;
	
	//Draw Report
	private String departmentId;
	private String departmentName;
	private String projectABBR;
	private String userId;
	private String userFullName;
	private String startDateTime;
	private String endDateTime;
	private String approveStatus;
	private String approveStatusTitle;
	private String minute;
	
	public String getProjConName() {
		return projConName;
	}

	public void setProjConName(String projConName) {
		this.projConName = projConName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProjectABBR() {
		return projectABBR;
	}

	public void setProjectABBR(String projectABBR) {
		this.projectABBR = projectABBR;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApproveStatusTitle() {
		return approveStatusTitle;
	}

	public void setApproveStatusTitle(String approveStatusTitle) {
		this.approveStatusTitle = approveStatusTitle;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}
	
	

}
