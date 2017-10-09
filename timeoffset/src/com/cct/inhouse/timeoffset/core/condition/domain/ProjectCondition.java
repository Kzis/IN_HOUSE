package com.cct.inhouse.timeoffset.core.condition.domain;

import com.cct.inhouse.common.InhouseDomain;

public class ProjectCondition extends InhouseDomain {

	private static final long serialVersionUID = -4972317553588637989L;

	private String projectId;
	private String projConDetail;
	private String projConFlag;
	private String hourTot;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjConDetail() {
		return projConDetail;
	}

	public void setProjConDetail(String projConDetail) {
		this.projConDetail = projConDetail;
	}

	public String getProjConFlag() {
		return projConFlag;
	}

	public void setProjConFlag(String projConFlag) {
		this.projConFlag = projConFlag;
	}

	public String getHourTot() {
		return hourTot;
	}

	public void setHourTot(String hourTot) {
		this.hourTot = hourTot;
	}

}
