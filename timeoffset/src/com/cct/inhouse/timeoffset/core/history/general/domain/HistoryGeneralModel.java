package com.cct.inhouse.timeoffset.core.history.general.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;

public class HistoryGeneralModel extends InhouseModel {

	private static final long serialVersionUID = 1L;
	
	private HistoryGeneralSearchCriteria criteria = new HistoryGeneralSearchCriteria();
	
	private List<CommonSelectItem> listApprove = new ArrayList<CommonSelectItem>();

	@Override
	public HistoryGeneralSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (HistoryGeneralSearchCriteria)criteria;
	}

	public List<CommonSelectItem> getListApprove() {
		return listApprove;
	}

	public void setListApprove(List<CommonSelectItem> listApprove) {
		this.listApprove = listApprove;
	}

	
	
	
}
