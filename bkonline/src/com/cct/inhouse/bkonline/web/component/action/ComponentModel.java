package com.cct.inhouse.bkonline.web.component.action;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemObject;

public class ComponentModel {

	private String page;
	private List<CommonSelectItem> listAllQAUserSelectItem = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItemObject<?>> listDepartmentSelectItem = new ArrayList<CommonSelectItemObject<?>>();
	private List<CommonSelectItem> listEmployeeFullnameByDepartmentIdSelectItem = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listProjectsSelectItem = new ArrayList<CommonSelectItem>();

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public List<CommonSelectItemObject<?>> getListDepartmentSelectItem() {
		return listDepartmentSelectItem;
	}

	public void setListDepartmentSelectItem(List<CommonSelectItemObject<?>> listDepartmentSelectItem) {
		this.listDepartmentSelectItem = listDepartmentSelectItem;
	}

	public List<CommonSelectItem> getListEmployeeFullnameByDepartmentIdSelectItem() {
		return listEmployeeFullnameByDepartmentIdSelectItem;
	}

	public void setListEmployeeFullnameByDepartmentIdSelectItem(
			List<CommonSelectItem> listEmployeeFullnameByDepartmentIdSelectItem) {
		this.listEmployeeFullnameByDepartmentIdSelectItem = listEmployeeFullnameByDepartmentIdSelectItem;
	}

	public List<CommonSelectItem> getListProjectsSelectItem() {
		return listProjectsSelectItem;
	}

	public void setListProjectsSelectItem(List<CommonSelectItem> listProjectsSelectItem) {
		this.listProjectsSelectItem = listProjectsSelectItem;
	}

	public List<CommonSelectItem> getListAllQAUserSelectItem() {
		return listAllQAUserSelectItem;
	}

	public void setListAllQAUserSelectItem(List<CommonSelectItem> listAllQAUserSelectItem) {
		this.listAllQAUserSelectItem = listAllQAUserSelectItem;
	}
}
