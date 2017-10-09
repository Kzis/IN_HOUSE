package com.cct.inhouse.timeoffset.core.approve.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;

public class ApproveModel extends InhouseModel {

	private static final long serialVersionUID = -3732051427273216016L;

	private ApproveSearchCriteria criteria = new ApproveSearchCriteria();

	private Approve approve = new Approve();

	private List<CommonSelectItem> listDepartment = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listProject = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listApprove = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listApprovalStatus = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listProcessStatus = new ArrayList<CommonSelectItem>();

	@Override
	public ApproveSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (ApproveSearchCriteria) criteria;
	}

	public List<CommonSelectItem> getListDepartment() {
		return listDepartment;
	}

	public void setListDepartment(List<CommonSelectItem> listDepartment) {
		this.listDepartment = listDepartment;
	}

	public List<CommonSelectItem> getListProject() {
		return listProject;
	}

	public void setListProject(List<CommonSelectItem> listProject) {
		this.listProject = listProject;
	}

	public List<CommonSelectItem> getListApprove() {
		return listApprove;
	}

	public void setListApprove(List<CommonSelectItem> list) {
		this.listApprove = list;
	}

	public Approve getApprove() {
		return approve;
	}

	public void setApprove(Approve approve) {
		this.approve = approve;
	}

	public List<CommonSelectItem> getListApprovalStatus() {
		return listApprovalStatus;
	}

	public void setListApprovalStatus(List<CommonSelectItem> listApprovalStatus) {
		this.listApprovalStatus = listApprovalStatus;
	}

	public List<CommonSelectItem> getListProcessStatus() {
		return listProcessStatus;
	}

	public void setListProcessStatus(List<CommonSelectItem> listProcessStatus) {
		this.listProcessStatus = listProcessStatus;
	}

}
