package com.cct.inhouse.timeoffset.core.report.inuse.manager.domain;

import java.util.List;

import com.cct.inhouse.common.InhouseDomain;

public class InuseManagerReportSearch extends InhouseDomain {

	private static final long serialVersionUID = 4590044658765480707L;
	
	private String department;
	private String fullName;
	private String nickName;
	private String timeOffset;
	private String timePendingHRM;
	private String dataTimeSyndataLasted;
	private String highlightRow;
	private String allProcessResDatetime;
	private String processResDatetimeByUser;
	
	private List<InuseManagerReportDetail> listDetail;
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTimeOffset() {
		return timeOffset;
	}
	public void setTimeOffset(String timeOffset) {
		this.timeOffset = timeOffset;
	}
	public List<InuseManagerReportDetail> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<InuseManagerReportDetail> listDetail) {
		this.listDetail = listDetail;
	}
	public String getHighlightRow() {
		return highlightRow;
	}
	public void setHighlightRow(String highlightRow) {
		this.highlightRow = highlightRow;
	}
	public String getTimePendingHRM() {
		return timePendingHRM;
	}
	public void setTimePendingHRM(String timePendingHRM) {
		this.timePendingHRM = timePendingHRM;
	}
	public String getDataTimeSyndataLasted() {
		return dataTimeSyndataLasted;
	}
	public void setDataTimeSyndataLasted(String dataTimeSyndataLasted) {
		this.dataTimeSyndataLasted = dataTimeSyndataLasted;
	}
	public String getALL_PROCESS_RES_DATETIME() {
		return allProcessResDatetime;
	}
	public void setALL_PROCESS_RES_DATETIME(String aLL_PROCESS_RES_DATETIME) {
		allProcessResDatetime = aLL_PROCESS_RES_DATETIME;
	}
	public String getPROCESS_RES_DATETIME_BY_USER() {
		return processResDatetimeByUser;
	}
	public void setPROCESS_RES_DATETIME_BY_USER(String pROCESS_RES_DATETIME_BY_USER) {
		processResDatetimeByUser = pROCESS_RES_DATETIME_BY_USER;
	}
	
}
