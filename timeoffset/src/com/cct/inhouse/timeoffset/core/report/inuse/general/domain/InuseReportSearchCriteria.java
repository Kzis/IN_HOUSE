package com.cct.inhouse.timeoffset.core.report.inuse.general.domain;

import com.cct.domain.SearchCriteria;

public class InuseReportSearchCriteria extends SearchCriteria{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9169200395850719806L;
	
	private String projectId;
	private String startDate;
	private String endDate;
	private String reportType;
	
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	
	
}
