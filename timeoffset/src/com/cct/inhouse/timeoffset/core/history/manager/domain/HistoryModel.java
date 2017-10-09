package com.cct.inhouse.timeoffset.core.history.manager.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;

public class HistoryModel extends InhouseModel {

	private static final long serialVersionUID = -1936885310262627936L;
	
	private HistorySearchCriteria criteria = new HistorySearchCriteria();
	
	private List<CommonSelectItem> listApprove = new ArrayList<CommonSelectItem>();

	@Override
	public HistorySearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (HistorySearchCriteria)criteria;
	}

	public List<CommonSelectItem> getListApprove() {
		return listApprove;
	}

	public void setListApprove(List<CommonSelectItem> list) {
		this.listApprove = list;
	}
	
	

}
