package com.cct.inhouse.timeoffset.core.domain;

import com.cct.inhouse.common.InhouseDomain;

public class TimeOffsetDetail extends InhouseDomain {

	private static final long serialVersionUID = 7498860228033439985L;

	private String timeOffsetId;
	private String projectId;
	private String projectName;
	private String projConId;
	private String projConDetail;
	private String startDateTime;
	private String endDateTime;
	private String day;
	private String hour;
	private String detailWork;
	private String remark;
	private String deleted;

	public String getTimeOffsetId() {
		return timeOffsetId;
	}

	public void setTimeOffsetId(String timeOffsetId) {
		this.timeOffsetId = timeOffsetId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjConId() {
		return projConId;
	}

	public void setProjConId(String projConId) {
		this.projConId = projConId;
	}

	public String getProjConDetail() {
		return projConDetail;
	}

	public void setProjConDetail(String projConDetail) {
		this.projConDetail = projConDetail;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
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

	public String getDetailWork() {
		return detailWork;
	}

	public void setDetailWork(String detailWork) {
		this.detailWork = detailWork;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
