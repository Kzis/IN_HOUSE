package com.cct.inhouse.timeoffset.core.report.toreport.domain;

import com.cct.domain.HeaderSorts;
import com.cct.domain.SearchCriteria;

public class TOReportSearchCriteria extends SearchCriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1695161904846290286L;
	
	private String departmentId;
	private String departmentName;
	private String employeeId;
	private String employeeName;
	private String startDate;
	private String endDate;
	
	private String projectId;
	private String projectName;
	private String projConId;
	private String projConName;
	private String approveStatus;
	
	public TOReportSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = { 
			new HeaderSorts("", HeaderSorts.ASC, "0"), 
			new HeaderSorts("", HeaderSorts.ASC, "0"), 
			new HeaderSorts("", HeaderSorts.ASC, "0"),
			new HeaderSorts("", HeaderSorts.ASC, "0") 
		};
		
		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	// กำหนด default header sorts ให้สำหรับการกด search ครั้งแรก
	public void setDefaultHeaderSorts() {
		for (int i = 0; i < getHeaderSortsSize(); i++) {
			getHeaderSorts()[i].setOrder(HeaderSorts.ASC);
			getHeaderSorts()[i].setSeq("0");
		}
		setHeaderSortsSelect(null);
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getProjConId() {
		return projConId;
	}

	public void setProjConId(String projConId) {
		this.projConId = projConId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjConName() {
		return projConName;
	}

	public void setProjConName(String projConName) {
		this.projConName = projConName;
	}
}
