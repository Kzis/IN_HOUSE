package com.cct.inhouse.timeoffset.core.approve.domain;

import com.cct.domain.HeaderSorts;
import com.cct.domain.SearchCriteria;

public class ApproveSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = -7163954346289882384L;

	private String departmentId;
	private String employeeId;
	private String projectId;
	private String projConId;
	private String startDate;
	private String endDate;
	private String approveStatus;
	private String processStatus;
	
	public ApproveSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = { 
			new HeaderSorts("", HeaderSorts.ASC, "0"), //No
			new HeaderSorts("", HeaderSorts.ASC, "0"), //View
			new HeaderSorts("", HeaderSorts.ASC, "0"), //Approve
			new HeaderSorts("PROJECT_ABBR", HeaderSorts.ASC, "0"), //โครงการ
			new HeaderSorts("PROJ_CON_DETAIL", HeaderSorts.ASC, "0"), //เงื่อนไขเวลาชดเชย
			new HeaderSorts("USER_FULLNAME", HeaderSorts.ASC, "0"), //ชื่อ-สกุลพนักงาน
			new HeaderSorts("", HeaderSorts.ASC, "0"), //จำนวนวันชดเชย
			new HeaderSorts("", HeaderSorts.ASC, "0"), //จำนวนชั่วโมงชดเชย
			new HeaderSorts("", HeaderSorts.ASC, "0"), //จำนวนนาทีชดเชย
			new HeaderSorts("", HeaderSorts.ASC, "0"), //ผู้อนุมัติ
			new HeaderSorts("", HeaderSorts.ASC, "0")  //สถานะการประมวลผล
		};

		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	// กำหนด default header sorts ให้สำหรับการกด search ครั้งแรก
	public void setDefaultHeaderSorts() {

		setHeaderSortsSelect("3,4,5");
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjConId() {
		return projConId;
	}

	public void setProjConId(String projConId) {
		this.projConId = projConId;
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

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
}
