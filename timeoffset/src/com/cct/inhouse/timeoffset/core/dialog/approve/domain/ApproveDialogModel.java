package com.cct.inhouse.timeoffset.core.dialog.approve.domain;

import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;

public class ApproveDialogModel extends InhouseModel {

	private static final long serialVersionUID = -2795962924653716424L;

	private ApproveDialogSearchCriteria criteriaPopup = new ApproveDialogSearchCriteria();

	private ApproveDialog approveDialog = new ApproveDialog();

	private String idx;

	@Override
	public void setCriteriaPopup(SearchCriteria criteriaPopup) {
		this.criteriaPopup = (ApproveDialogSearchCriteria) criteriaPopup;
	}

	@Override
	public ApproveDialogSearchCriteria getCriteriaPopup() {
		return criteriaPopup;
	}

	public void setCriteriaPopup(ApproveDialogSearchCriteria criteriaPopup) {
		this.criteriaPopup = criteriaPopup;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public ApproveDialog getApproveDialog() {
		return approveDialog;
	}

	public void setApproveDialog(ApproveDialog approveDialog) {
		this.approveDialog = approveDialog;
	}

}
