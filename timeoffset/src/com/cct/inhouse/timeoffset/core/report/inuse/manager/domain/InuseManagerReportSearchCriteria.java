package com.cct.inhouse.timeoffset.core.report.inuse.manager.domain;

import com.cct.domain.HeaderSorts;
import com.cct.domain.SearchCriteria;

public class InuseManagerReportSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = 3280632887269770263L;
	
	private String departmentId;
	private String departmentName;
	private String employeeId;
	private String employeeName;
	private String startDate;
	private String endDate;
	
	private String projectId;
	private String activeStatus;
	private String reportType;
	private String approveStatus;
	
	public InuseManagerReportSearchCriteria() {
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
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

}
