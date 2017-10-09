package com.cct.inhouse.timeoffset.core.report.inuse.general.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;
import com.cct.inhouse.core.config.parameter.domain.Report;

public class InuseReportModel extends InhouseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3409675487468702293L;

	private InuseReportSearchCriteria criteria = new InuseReportSearchCriteria();

	private Report report = new Report();

	private List<CommonSelectItem> listProject = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listActiveStatus = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listApprove = new ArrayList<CommonSelectItem>();

	@Override
	public InuseReportSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (InuseReportSearchCriteria) criteria;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
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

	public List<CommonSelectItem> getListApprove() {
		return listApprove;
	}

	public void setListApprove(List<CommonSelectItem> listApprove) {
		this.listApprove = listApprove;
	}

}
