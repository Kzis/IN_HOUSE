package com.cct.inhouse.timeoffset.core.manage.domain;

import com.cct.domain.HeaderSorts;
import com.cct.domain.SearchCriteria;

public class ManageTimeOffsetSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = -8122059658001793489L;

	private String departmentId;
	private String fullNameEmpId;
	private String projectId;
	private String approveStatus;
	private String processStatus;

	public ManageTimeOffsetSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = { 
			new HeaderSorts("", HeaderSorts.ASC, "0"), //No
			new HeaderSorts("", HeaderSorts.ASC, "0"), //CheckBox
			new HeaderSorts("", HeaderSorts.ASC, "0"), //View
			new HeaderSorts("", HeaderSorts.ASC, "0"), //Edit
			new HeaderSorts("PROJECT_ABBR", HeaderSorts.ASC, "0"), //โครงการ
			new HeaderSorts("", HeaderSorts.ASC, "0"), //เงื่อนไขเวลาชดเชย
			new HeaderSorts("", HeaderSorts.ASC, "0"), //จำนวนวันชดเชย
			new HeaderSorts("", HeaderSorts.ASC, "0"), //จำนวนชั่วโมงชดเชย
			new HeaderSorts("", HeaderSorts.ASC, "0"), //จำนวนนาทีชดเชย
			new HeaderSorts("", HeaderSorts.ASC, "0"), //สถานะการประมวลผล
		};

		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	// กำหนด default header sorts ให้สำหรับการกด search ครั้งแรก
	public void setDefaultHeaderSorts() {

		setHeaderSortsSelect("4");
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getFullNameEmpId() {
		return fullNameEmpId;
	}

	public void setFullNameEmpId(String fullNameEmpId) {
		this.fullNameEmpId = fullNameEmpId;
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

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	
}
