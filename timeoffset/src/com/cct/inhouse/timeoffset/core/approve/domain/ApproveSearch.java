package com.cct.inhouse.timeoffset.core.approve.domain;

import com.cct.inhouse.common.InhouseDomain;

public class ApproveSearch extends InhouseDomain {

	private static final long serialVersionUID = 2573269651994401584L;

	private String projectAbbr;
	private String projConDetail;
	private String fullName;
	private String day;
	private String hour;
	private String minute;
	private String approveUser;
	private String approveStatus;
	private String approveStatusDesc;
	private String processStatus;

	public String getProjectAbbr() {
		return projectAbbr;
	}

	public void setProjectAbbr(String projectAbbr) {
		this.projectAbbr = projectAbbr;
	}

	public String getProjConDetail() {
		return projConDetail;
	}

	public void setProjConDetail(String projConDetail) {
		this.projConDetail = projConDetail;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApproveStatusDesc() {
		return approveStatusDesc;
	}

	public void setApproveStatusDesc(String approveStatusDesc) {
		this.approveStatusDesc = approveStatusDesc;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

}
