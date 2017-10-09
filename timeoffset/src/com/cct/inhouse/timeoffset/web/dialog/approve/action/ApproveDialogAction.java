package com.cct.inhouse.timeoffset.web.dialog.approve.action;

import org.apache.log4j.Logger;

import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseDialogAction;
import com.cct.inhouse.common.InhouseDomain;
import com.cct.inhouse.timeoffset.core.dialog.approve.domain.ApproveDialogModel;
import com.cct.inhouse.timeoffset.core.dialog.approve.service.ApproveDialogManager;
import com.cct.inhouse.timeoffset.util.log.LogUtil;

import util.database.CCTConnection;

public class ApproveDialogAction extends InhouseDialogAction {

	private static final long serialVersionUID = -1099125229042417943L;

	ApproveDialogModel model = new ApproveDialogModel();
	private ApproveDialogManager manager = null;

	@Override
	public SearchCriteria initSearchCriteria() {
		return null;

	}

	@Override
	public InhouseDomain searchDetailById(CCTConnection conn) throws Exception {

		getLogger().debug("##### [DIALOG ACTION : searchDetailById ] #####");

		manager = new ApproveDialogManager(getLogger(), getUser(), getLocale());

		return manager.searchById(conn,getModel().getIdx());
	}

	@Override
	public ApproveDialogModel getModel() {
		return model;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.TO_DIALOG;
	}

}
