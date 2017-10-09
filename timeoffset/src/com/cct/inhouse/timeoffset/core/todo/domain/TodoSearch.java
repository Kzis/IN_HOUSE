package com.cct.inhouse.timeoffset.core.todo.domain;

import com.cct.inhouse.common.InhouseDomain;

public class TodoSearch extends InhouseDomain {

	private static final long serialVersionUID = 5296686709875161773L;

	private String projectABBR;
	private String projectConditionDETAIL;
	private String department;
	private String fullName;
	private String day;
	private String hour;
	private String minute;
	private String timeOffsetID;

	public String getProjectABBR() {
		return projectABBR;
	}

	public void setProjectABBR(String projectABBR) {
		this.projectABBR = projectABBR;
	}

	public String getProjectConditionDETAIL() {
		return projectConditionDETAIL;
	}

	public void setProjectConditionDETAIL(String projectConditionDETAIL) {
		this.projectConditionDETAIL = projectConditionDETAIL;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getTimeOffsetID() {
		return timeOffsetID;
	}

	public void setTimeOffsetID(String timeOffsetID) {
		this.timeOffsetID = timeOffsetID;
	}

}
