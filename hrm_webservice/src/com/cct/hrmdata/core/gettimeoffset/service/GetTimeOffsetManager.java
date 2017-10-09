package com.cct.hrmdata.core.gettimeoffset.service;

import java.util.List;
import java.util.Locale;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractManager;
import com.cct.hrmdata.core.gettimeoffset.domain.WorkOnsite;

public class GetTimeOffsetManager extends AbstractManager {

	private GetTimeOffsetService service = null;

	public GetTimeOffsetManager(CCTConnection conn, Locale locale) {
		super(conn, locale);
		setService(new GetTimeOffsetService(conn, locale));
	}

	public GetTimeOffsetService getService() {
		return service;
	}

	public void setService(GetTimeOffsetService service) {
		this.service = service;
	}

	/**
	 * Search user จาก DB HRM
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<WorkOnsite> searchWorkOnsite(String processDate, String userId) throws Exception {
		return getService().searchWorkOnsite(processDate, userId);
	}

}
