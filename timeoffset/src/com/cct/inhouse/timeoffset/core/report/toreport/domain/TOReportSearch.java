package com.cct.inhouse.timeoffset.core.report.toreport.domain;

import com.cct.inhouse.common.InhouseDomain;

public class TOReportSearch extends InhouseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -264093916069432929L;

	String departmentId;
	String departmentName;
	String employeeId;
	String employeeName;
	String startDate;
	String endDate;
	String totalDay;
	String totalHour;
	String totalMinute;
	String approveStatus;

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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(String totalDay) {
		this.totalDay = totalDay;
	}

	public String getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(String totalHour) {
		this.totalHour = totalHour;
	}

	public String getTotalMinute() {
		return totalMinute;
	}

	public void setTotalMinute(String totalMinute) {
		this.totalMinute = totalMinute;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

}
