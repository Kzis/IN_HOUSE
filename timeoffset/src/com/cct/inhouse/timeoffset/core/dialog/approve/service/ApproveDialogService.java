package com.cct.inhouse.timeoffset.core.dialog.approve.service;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.timeoffset.core.dialog.approve.domain.ApproveDialog;

import util.database.CCTConnection;

public class ApproveDialogService extends AbstractService {

	private ApproveDialogDAO dao = null;

	public ApproveDialogService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new ApproveDialogDAO(logger, SQLPath.TO_APPROVE.getSqlPath(), user, locale);
	}

	protected ApproveDialog searchById(CCTConnection conn, String id) throws Exception {
		return dao.searchById(conn, id);
	}

}
