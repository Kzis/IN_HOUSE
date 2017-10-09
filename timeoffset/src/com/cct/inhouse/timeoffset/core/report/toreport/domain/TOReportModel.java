package com.cct.inhouse.timeoffset.core.report.toreport.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;

public class TOReportModel extends InhouseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3148557365561370215L;

	private TOReportSearchCriteria criteria = new TOReportSearchCriteria();

	private TOReport report = new TOReport();

	private String testtt;
	private List<CommonSelectItem> listProject = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listActiveStatus = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listApprove = new ArrayList<CommonSelectItem>();

	@Override
	public TOReportSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (TOReportSearchCriteria) criteria;
	}

	public List<CommonSelectItem> getListProject() {
		return listProject;
	}

	public void setListProject(List<CommonSelectItem> listProject) {
		this.listProject = listProject;
	}

	public List<CommonSelectItem> getListActiveStatus() {
		return listActiveStatus;
	}

	public void setListActiveStatus(List<CommonSelectItem> listActiveStatus) {
		this.listActiveStatus = listActiveStatus;
	}

	public String getTesttt() {
		return testtt;
	}

	public void setTesttt(String testtt) {
		this.testtt = testtt;
	}

	public List<CommonSelectItem> getListApprove() {
		return listApprove;
	}

	public void setListApprove(List<CommonSelectItem> listApprove) {
		this.listApprove = listApprove;
	}

	public TOReport getReport() {
		return report;
	}

	public void setReport(TOReport report) {
		this.report = report;
	}

}
