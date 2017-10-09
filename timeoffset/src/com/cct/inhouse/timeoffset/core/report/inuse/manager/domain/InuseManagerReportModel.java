package com.cct.inhouse.timeoffset.core.report.inuse.manager.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;

public class InuseManagerReportModel extends InhouseModel {

	private static final long serialVersionUID = 1054614837375512355L;
	
	private InuseManagerReport report = new InuseManagerReport();
	
	private InuseManagerReportSearchCriteria criteria = new InuseManagerReportSearchCriteria();
	
	private List<CommonSelectItem> listProject = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listActiveStatus = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listApprove = new ArrayList<CommonSelectItem>();

	

	@Override
	public InuseManagerReportSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (InuseManagerReportSearchCriteria)criteria;
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

	public InuseManagerReport getReport() {
		return report;
	}

	public void setReport(InuseManagerReport report) {
		this.report = report;
	}

	public void setCriteria(InuseManagerReportSearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	
}
