package com.cct.inhouse.bkonline.core.report.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;

public class ReportModel extends InhouseModel {

	private static final long serialVersionUID = -1298126139309310825L;

	private ReportSearchCriteria criteria = new ReportSearchCriteria();
	private ReportData event = new ReportData();
	
	//dropdownlist s:select
	private List<Report> listRoom = new ArrayList<Report>();
	private List<CommonSelectItem> listMonth = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listYears = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> liststatus = new ArrayList<CommonSelectItem>();
	
	@Override
	public ReportSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (ReportSearchCriteria) criteria;
	}

	public ReportData getEvent() {
		return event;
	}

	public void setEvent(ReportData event) {
		this.event = event;
	}

	public List<Report> getListRoom() {
		return listRoom;
	}

	public void setListRoom(List<Report> listRoom) {
		this.listRoom = listRoom;
	}

	public List<CommonSelectItem> getListstatus() {
		return liststatus;
	}

	public void setListstatus(List<CommonSelectItem> liststatus) {
		this.liststatus = liststatus;
	}

	public List<CommonSelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<CommonSelectItem> listYears) {
		this.listYears = listYears;
	}

	public List<CommonSelectItem> getListMonth() {
		return listMonth;
	}

	public void setListMonth(List<CommonSelectItem> listMonth) {
		this.listMonth = listMonth;
	}
}
