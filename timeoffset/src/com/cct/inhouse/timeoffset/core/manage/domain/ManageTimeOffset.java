package com.cct.inhouse.timeoffset.core.manage.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.inhouse.common.InhouseDomain;

public class ManageTimeOffset extends InhouseDomain {

	private static final long serialVersionUID = 1L;

	private String departmaneName;
	private String name;
	private String surname;
	private String fullName;
	private String projectId;
	private String project;
	private String projectConditionId;
	private String projectCondition; // DETAIL
	private String projectConditionFlag;
	private String hourTot;
	private String startDate;
	private String startTime;
	private String startDateTime;
	private String endDate;
	private String endTime;
	private String endDateTime;
	private String workDate;
	private String hour;
	private String day;
	private String minute;
	private String amountDayOffset;
	private String amountHourOffset;
	private String amountMinuteOffset;
	private String detailWork;
	private String remark;
	private String approveStatus;
	private String approveRemark;
	private String approveUser;
	private String approveDate;
	private String approveStatusDesc;
	private long timeOffsetId;
	private String processStatus;

	private String deletedByEditPage = "N";
	
	private List<ManageTimeOffset> listNewProject = new ArrayList<ManageTimeOffset>();

	public String getDepartmaneName() {
		return departmaneName;
	}

	public void setDepartmaneName(String departmaneName) {
		this.departmaneName = departmaneName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProjectCondition() {
		return projectCondition;
	}

	public void setProjectCondition(String projectCondition) {
		this.projectCondition = projectCondition;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectConditionId() {
		return projectConditionId;
	}

	public void setProjectConditionId(String projectConditionId) {
		this.projectConditionId = projectConditionId;
	}

	public String getApproveStatusDesc() {
		return approveStatusDesc;
	}

	public void setApproveStatusDesc(String approveStatusDesc) {
		this.approveStatusDesc = approveStatusDesc;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getProjectConditionFlag() {
		return projectConditionFlag;
	}

	public void setProjectConditionFlag(String projectConditionFlag) {
		this.projectConditionFlag = projectConditionFlag;
	}

	public String getHourTot() {
		return hourTot;
	}

	public void setHourTot(String hourTot) {
		this.hourTot = hourTot;
	}

	public String getAmountDayOffset() {
		return amountDayOffset;
	}

	public void setAmountDayOffset(String amountDayOffset) {
		this.amountDayOffset = amountDayOffset;
	}

	public String getAmountHourOffset() {
		return amountHourOffset;
	}

	public void setAmountHourOffset(String amountHourOffset) {
		this.amountHourOffset = amountHourOffset;
	}

	public String getAmountMinuteOffset() {
		return amountMinuteOffset;
	}

	public void setAmountMinuteOffset(String amountMinuteOffset) {
		this.amountMinuteOffset = amountMinuteOffset;
	}

	public long getTimeOffsetId() {
		return timeOffsetId;
	}

	public void setTimeOffsetId(long timeOffsetId) {
		this.timeOffsetId = timeOffsetId;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getDeletedByEditPage() {
		return deletedByEditPage;
	}

	public void setDeletedByEditPage(String deletedByEditPage) {
		this.deletedByEditPage = deletedByEditPage;
	}

	public List<ManageTimeOffset> getListNewProject() {
		return listNewProject;
	}

	public void setListNewProject(List<ManageTimeOffset> listNewProject) {
		this.listNewProject = listNewProject;
	}

}
