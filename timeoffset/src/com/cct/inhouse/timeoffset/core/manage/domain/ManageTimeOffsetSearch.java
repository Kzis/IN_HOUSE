package com.cct.inhouse.timeoffset.core.manage.domain;

import com.cct.inhouse.common.InhouseDomain;

public class ManageTimeOffsetSearch extends InhouseDomain {

	private static final long serialVersionUID = 9205890985395769307L;

	private String projectAbbr;
	private String projConDetail;
	private String day;
	private String hour;
	private String minute;

	private String approveStatus;
	private String processStatus;
	private String approveStatusDesc;
	private String processStatusDesc;
	
	//จำนวนเวลาชดเชยคงเหลือ
	private String styleColor;
	private String timeOffset;
		
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

	public String getProcessStatusDesc() {
		return processStatusDesc;
	}

	public void setProcessStatusDesc(String processStatusDesc) {
		this.processStatusDesc = processStatusDesc;
	}

	public String getStyleColor() {
		return styleColor;
	}

	public void setStyleColor(String styleColor) {
		this.styleColor = styleColor;
	}

	public String getTimeOffset() {
		return timeOffset;
	}

	public void setTimeOffset(String timeOffset) {
		this.timeOffset = timeOffset;
	}
	
}
