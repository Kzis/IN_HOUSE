package com.cct.inhouse.timeoffset.core.dialog.approve.service;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonUser;
import com.cct.inhouse.common.InhouseDomain;
import com.cct.inhouse.timeoffset.core.dialog.approve.domain.ApproveDialog;
import com.cct.inhouse.timeoffset.core.dialog.approve.domain.ApproveDialogSearchCriteria;

import util.database.CCTConnection;

public class ApproveDialogManager extends AbstractManager<ApproveDialogSearchCriteria, InhouseDomain, ApproveDialog, CommonUser, Locale> {

	ApproveDialogService service = null;

	public ApproveDialogManager(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new ApproveDialogService(logger, user, locale);
	}

	@Override
	public long add(CCTConnection conn, ApproveDialog obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(CCTConnection conn, String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(CCTConnection conn, ApproveDialog obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<InhouseDomain> search(CCTConnection conn, ApproveDialogSearchCriteria criteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApproveDialog searchById(CCTConnection conn, String id) throws Exception {
		return service.searchById(conn, id);
	}

	@Override
	public int updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
