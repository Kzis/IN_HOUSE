package com.cct.inhouse.bkonline.core.report.service;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonSelectItem;
import com.cct.common.CommonUser;
import com.cct.inhouse.bkonline.core.report.domain.Report;
import com.cct.inhouse.bkonline.core.report.domain.ReportData;
import com.cct.inhouse.bkonline.core.report.domain.ReportSearchCriteria;

import util.database.CCTConnection;

public class ReportManager
		extends
		AbstractManager<ReportSearchCriteria, ReportData, ReportData, CommonUser, Locale> {
	private ReportService service = null;

	public ReportManager(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new ReportService(logger, user, locale);

	}
	public List<Report> searchListRoom(CCTConnection conn)throws Exception  {
		return service.searchListRoom(conn);
	}
	public List<CommonSelectItem> searchListMonth(CCTConnection conn)throws Exception  {
		return service.searchListMonth(conn);
	}
//	public List<CommonSelectItem> searchListYears(CCTConnection conn)throws Exception  {
//		return service.searchListYears(conn);
//	}
	@Override
	public long add(CCTConnection conn, ReportData event) throws Exception {
	    return 0;
	}

	@Override
	public long delete(CCTConnection arg0, String arg1) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long edit(CCTConnection arg0, ReportData arg1) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ReportData> search(CCTConnection arg0,
			ReportSearchCriteria arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportData searchById(CCTConnection arg0, String arg1)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long updateActive(CCTConnection arg0, String arg1, String arg2)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}