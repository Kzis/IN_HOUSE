package com.cct.inhouse.timeoffset.core.domain;

import com.cct.inhouse.common.InhouseDomain;

public class TimeOffset extends InhouseDomain {

	private static final long serialVersionUID = -3323844793760426992L;

	private String userId;
	private String userFullName;
	private String approveStatus;
	private String approveStatusDesc;
	private String approveRemark;
	private String approveUser;
	private String approveUserFullName;
	private String approveDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
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

	public String getApproveUserFullName() {
		return approveUserFullName;
	}

	public void setApproveUserFullName(String approveUserFullName) {
		this.approveUserFullName = approveUserFullName;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

}
