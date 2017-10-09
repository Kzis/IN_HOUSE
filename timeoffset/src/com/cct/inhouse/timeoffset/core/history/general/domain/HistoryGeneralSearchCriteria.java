package com.cct.inhouse.timeoffset.core.history.general.domain;

import com.cct.domain.HeaderSorts;
import com.cct.domain.SearchCriteria;

public class HistoryGeneralSearchCriteria extends SearchCriteria{

	private static final long serialVersionUID = 3669421928869056397L;
	
	private String employeeId;
	private String employeeName;
	private String startDate;
	private String endDate;
	private String approveStatus;
	
	public HistoryGeneralSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = { 
			new HeaderSorts("", HeaderSorts.ASC, "0"), 
			new HeaderSorts("", HeaderSorts.ASC, "0"), 
			new HeaderSorts("", HeaderSorts.ASC, "0"),
			new HeaderSorts("", HeaderSorts.ASC, "0"), 
			new HeaderSorts("", HeaderSorts.ASC, "0"), 
			new HeaderSorts("", HeaderSorts.ASC, "0"),
			new HeaderSorts("", HeaderSorts.ASC, "0"), 
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
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	
	
}
