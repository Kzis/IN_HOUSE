package com.cct.hrmdata.core.gettimeoffset.service;

import java.util.List;
import java.util.Locale;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractService;
import com.cct.hrmdata.core.config.domain.SQLPath;
import com.cct.hrmdata.core.gettimeoffset.domain.WorkOnsite;

public class GetTimeOffsetService extends AbstractService {

	private GetTimeOffsetDAO dao = null;

	public GetTimeOffsetService(CCTConnection conn, Locale locale) {
		super(conn, locale);
		setDao(new GetTimeOffsetDAO());
		getDao().setSqlPath(SQLPath.TIME_OFFSET_SQL);
	}

	public GetTimeOffsetDAO getDao() {
		return dao;
	}

	public void setDao(GetTimeOffsetDAO dao) {
		this.dao = dao;
	}

	/**
	 * Search user จาก DB HRM
	 * 
	 * @return
	 * @throws Exception
	 */
	protected List<WorkOnsite> searchWorkOnsite(String processDate, String userId) throws Exception {
		return getDao().searchWorkOnsite(conn, processDate, userId);
	}

}
