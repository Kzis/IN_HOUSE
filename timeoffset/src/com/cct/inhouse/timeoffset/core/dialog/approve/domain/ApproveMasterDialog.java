package com.cct.inhouse.timeoffset.core.dialog.approve.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.inhouse.common.InhouseDomain;

public class ApproveMasterDialog extends InhouseDomain {

	private static final long serialVersionUID = -1951736650051777566L;

	private List<ApproveDialog> listDetail = new ArrayList<ApproveDialog>();

	public List<ApproveDialog> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<ApproveDialog> listDetail) {
		this.listDetail = listDetail;
	}

}
