package com.cct.inhouse.timeoffset.core.report.inuse.manager.domain;

import com.cct.inhouse.common.InhouseDomain;

public class InuseManagerReportDetail extends InhouseDomain {

	private static final long serialVersionUID = -918969644475210732L;
	
	private String startDateTime;
	private String endDateTime;
	private String totalDay;
	private String totalHour;
	private String totalMinute;
	private String workPlace;

	
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
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}


}
