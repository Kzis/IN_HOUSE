package com.cct.inhouse.timeoffset.core.condition.domain;

import com.cct.inhouse.common.InhouseDomain;

public class ProjectConditionSearch extends InhouseDomain {

	private static final long serialVersionUID = 1647051162107060814L;

	private String projectAbbr;
	private String projConDetail;
	private String projConFlag;
	private String hourTot;

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
