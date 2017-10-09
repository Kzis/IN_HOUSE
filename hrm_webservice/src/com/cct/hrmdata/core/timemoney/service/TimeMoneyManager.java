package com.cct.hrmdata.core.timemoney.service;

import java.util.List;
import java.util.Locale;

import util.database.CCTConnection;

import com.cct.abstracts.AbstractManager;
import com.cct.hrmdata.core.timemoney.domain.TimeMoney;

public class TimeMoneyManager extends AbstractManager{
	
	private TimeMoneyService service = null;
	
	public TimeMoneyManager(CCTConnection conn, Locale locale) {
		super(conn, locale);
		setService(new TimeMoneyService(conn, locale));
	}
	
	
//	public long countSearchMain(String startDate, String endDate, String secUserIds) throws Exception{
//		return service.countSearchTimeMoney(startDate, endDate, secUserIds);
//	}
//	
//	public long countSearchPerson(String startDate, String endDate, String secUserIds) throws Exception{
//		return service.countSearchTimeMoney(startDate, endDate, secUserIds);
//	}
//	
//	public long countSearchDepartment(String startDate, String endDate, String secUserIds) throws Exception{
//		return service.countSearchTimeMoney(startDate, endDate, secUserIds);
//	}
//	
//	public long countSearchRankPerson(String startDate, String endDate, String secUserIds) throws Exception{
//		return service.countSearchTimeMoney(startDate, endDate, secUserIds);
//	}
//	
//	public long countSearchRankDepartment(String startDate, String endDate, String secUserIds) throws Exception{
//		return service.countSearchTimeMoney(startDate, endDate, secUserIds);
//	}
//	
//	public long countSearchReport(String startDate, String endDate, String secUserIds) throws Exception{
//		return service.countSearchTimeMoney(startDate, endDate, secUserIds);
//	}
	
	
	
	
	
	
	
	
	public String searchEmployeeId(String secUserId) throws Exception{
		return service.searchEmployeeId(secUserId);
	}
	
	
	
	public List<TimeMoney> searchTimeMoney(String startDate, String endDate, String secUserIds) throws Exception{
		return service.searchTimeMoney(startDate, endDate, secUserIds);
	}
	
	
	
	
	
	
	
	
//	public List<TimeMoney> searchMain(String startDate, String endDate, String secUserIds) throws Exception{
//		return service.searchMain(startDate, endDate, secUserIds);
//	}
//	
//	public List<TimeMoney> searchPerson(String startDate, String endDate, String secUserIds, String searchType) throws Exception{
//		return service.searchPerson(startDate, endDate, secUserIds, searchType);
//	}
//	
//	public List<TimeMoney> searchDepartment(String startDate, String endDate, String secUserIds, String searchType, String jsonMapDepartment) throws Exception{
//		return service.searchDepartment(startDate, endDate, secUserIds, searchType, jsonMapDepartment);
//	}
//	
//	public List<TimeMoney> searchRankPerson(String startDate, String endDate, String secUserIds) throws Exception{
//		return service.searchRankPerson(startDate, endDate, secUserIds);
//	}
//	
//	public List<TimeMoney> searchRankDepartment(String startDate, String endDate, String secUserIds, String jsonMapDepartment) throws Exception{
//		return service.searchRankDepartment(startDate, endDate, secUserIds, jsonMapDepartment);
//	}
//
//	public List<TimeMoney> searchReport(String startDate, String endDate, String secUserIds, String reportType, String jsonMapDepartment) throws Exception{
//		return service.searchReport(startDate, endDate, secUserIds, reportType, jsonMapDepartment);
//	}

	
	
	
	
	
	
	
	
	
	public TimeMoneyService getService() {
		return service;
	}

	public void setService(TimeMoneyService service) {
		this.service = service;
	}
}
