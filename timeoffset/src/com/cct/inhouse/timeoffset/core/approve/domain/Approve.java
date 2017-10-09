package com.cct.inhouse.timeoffset.core.approve.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.inhouse.common.InhouseDomain;

public class Approve extends InhouseDomain {

	private static final long serialVersionUID = -1567578876190819459L;

	private String department;
	private String fullName;
	private List<Detail> listDetail = new ArrayList<Detail>();
	private String approveStatus;
	private String remark;
	private String projectABBR;
	private String projectConDetail;
	private String timeOffsetId;
	private String todoId;
	private String processStatus;

	// ##### [PAGE VIEW] #####
	private String approveUser;
	private String approveDate;
	private String approveTime;
	private String approveStatusDesc;

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

	public List<Detail> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<Detail> listDetail) {
		this.listDetail = listDetail;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveStatusDesc() {
		return approveStatusDesc;
	}

	public void setApproveStatusDesc(String approveStatusDesc) {
		this.approveStatusDesc = approveStatusDesc;
	}

	public String getProjectABBR() {
		return projectABBR;
	}

	public void setProjectABBR(String projectABBR) {
		this.projectABBR = projectABBR;
	}

	public String getProjectConDetail() {
		return projectConDetail;
	}

	public void setProjectConDetail(String projectConDetail) {
		this.projectConDetail = projectConDetail;
	}

	public String getTimeOffsetId() {
		return timeOffsetId;
	}

	public void setTimeOffsetId(String timeOffsetId) {
		this.timeOffsetId = timeOffsetId;
	}

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

}
