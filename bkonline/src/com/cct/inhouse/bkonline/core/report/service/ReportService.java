package com.cct.inhouse.bkonline.core.report.service;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonSelectItem;
import com.cct.common.CommonUser;
import com.cct.inhouse.bkonline.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.bkonline.core.report.domain.Report;
import com.cct.inhouse.bkonline.core.report.domain.ReportData;
import com.cct.inhouse.bkonline.core.report.domain.ReportSearchCriteria;

import util.database.CCTConnection;


public class ReportService extends AbstractService {
	
	private ReportDAO dao=null;
	
	
	public ReportService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new ReportDAO(logger, SQLPath.SETTING_SQL.getSqlPath(), user, locale);
	}
	
	protected List<ReportData> search(CCTConnection conn,ReportSearchCriteria criteria) throws Exception {
		return dao.search(conn, criteria);
	}
	protected List<Report> searchListRoom(CCTConnection conn) throws Exception {
		return dao.searchListRoom(conn);
	}
	protected List<CommonSelectItem> searchListMonth(CCTConnection conn) throws Exception {
		return dao.searchListMonth(conn);
	}
//	protected List<CommonSelectItem> searchListYears(CCTConnection conn) throws Exception {
//		return dao.searchListYears(conn);
//	}

}
