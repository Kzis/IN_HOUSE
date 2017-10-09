package com.cct.inhouse.timeoffset.core.dialog.approve.domain;

import com.cct.inhouse.common.InhouseDomain;

public class ApproveDialog extends InhouseDomain {

	private static final long serialVersionUID = -7059422274951775680L;

	private String projectName;
	private String projectCode;
	private String projectConditionID;
	private String projectConditionDETAIL;
	private String projectConditionFlag;
	private String hourTot;
	private String workDetail;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String workDate;
	private String day;
	private String hour;
	private String min;
	private String remark;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectConditionID() {
		return projectConditionID;
	}

	public void setProjectConditionID(String projectConditionID) {
		this.projectConditionID = projectConditionID;
	}

	public String getProjectConditionDETAIL() {
		return projectConditionDETAIL;
	}

	public void setProjectConditionDETAIL(String projectConditionDETAIL) {
		this.projectConditionDETAIL = projectConditionDETAIL;
	}

	public String getWorkDetail() {
		return workDetail;
	}

	public void setWorkDetail(String workDetail) {
		this.workDetail = workDetail;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getProjectConditionFlag() {
		return projectConditionFlag;
	}

	public void setProjectConditionFlag(String projectConditionFlag) {
		this.projectConditionFlag = projectConditionFlag;
	}

	public String getHourTot() {
		return hourTot;
	}

	public void setHourTot(String hourTot) {
		this.hourTot = hourTot;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

}
