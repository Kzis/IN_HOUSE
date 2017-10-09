package com.cct.inhouse.timeoffset.core.manage.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;

public class ManageTimeOffsetModel extends InhouseModel {

	private static final long serialVersionUID = 8768102824131528808L;

	private ManageTimeOffsetSearchCriteria criteria = new ManageTimeOffsetSearchCriteria();

	private ManageTimeOffset manage = new ManageTimeOffset();

	private List<CommonSelectItem> listDepartment = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listProject = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listProjectCondition = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listApprove = new ArrayList<CommonSelectItem>();

	private List<ManageTimeOffset> listNewProject = new ArrayList<ManageTimeOffset>();
	private List<CommonSelectItem> listProcessStatus = new ArrayList<CommonSelectItem>();

	public List<CommonSelectItem> getListDepartment() {
		return listDepartment;
	}

	public void setListDepartment(List<CommonSelectItem> listDepartment) {
		this.listDepartment = listDepartment;
	}

	public List<CommonSelectItem> getListApprove() {
		return listApprove;
	}

	public void setListApprove(List<CommonSelectItem> list) {
		this.listApprove = list;
	}

	public List<CommonSelectItem> getListProject() {
		return listProject;
	}

	public void setListProject(List<CommonSelectItem> listProject) {
		this.listProject = listProject;
	}

	@Override
	public ManageTimeOffsetSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (ManageTimeOffsetSearchCriteria) criteria;
	}

	public ManageTimeOffset getManage() {
		return manage;
	}

	public void setManage(ManageTimeOffset manage) {
		this.manage = manage;
	}

	public List<CommonSelectItem> getListProjectCondition() {
		return listProjectCondition;
	}

	public void setListProjectCondition(List<CommonSelectItem> listProjectCondition) {
		this.listProjectCondition = listProjectCondition;
	}

	public List<CommonSelectItem> getListProcessStatus() {
		return listProcessStatus;
	}

	public void setListProcessStatus(List<CommonSelectItem> listProcessStatus) {
		this.listProcessStatus = listProcessStatus;
	}

	public List<ManageTimeOffset> getListNewProject() {
		return listNewProject;
	}

	public void setListNewProject(List<ManageTimeOffset> listNewProject) {
		this.listNewProject = listNewProject;
	}

	public void setCriteria(ManageTimeOffsetSearchCriteria criteria) {
		this.criteria = criteria;
	}
	
}
