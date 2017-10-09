
package com.cct.hrmdata.web.timemoney.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;

import com.cct.hrmdata.core.config.domain.DBLookup;
import com.cct.hrmdata.core.timemoney.domain.TimeMoney;
import com.cct.hrmdata.core.timemoney.service.TimeMoneyManager;


@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public class TimeMoneyWebService {
	
	
	
	
	/**
	 * SEARCH TIME_MONEY
	 * @param startDate
	 * @param endDate
	 * @param secUserIds
	 * @return
	 */
	@WebMethod
	public List<TimeMoney> searchTimeMoney(String startDate, String endDate, String secUserIds) {
		LogUtil.TIME_MONEY.info("[WS] SEARCH TIME_MONEY");
		
		LogUtil.TIME_MONEY.debug("\n###############################################################################"
	    		+"\n###WEB SERVICE PARAMETER...[SEARC TIME_MONEY]"
	    		+"\n###############################################################################"
				+"\n###DATE START   : "+startDate
				+"\n###DATE END     : "+endDate
				+"\n###SEC USER IDS : "+secUserIds
				+"\n###############################################################################\n");
		List<TimeMoney> response = new ArrayList<TimeMoney>();
		
		CCTConnection conn = null;
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			
			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
			response = manager.searchTimeMoney(startDate, endDate, secUserIds);
			
		} catch (Exception e) {
			LogUtil.TIME_MONEY.error("", e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return response;
	}
	
	
	
	/**
	 *  SEARCH EMPLOYEE_ID
	 * @param secUserId
	 * @return
	 */
	@WebMethod
	public String searchEmployeeId(String secUserId) {
		LogUtil.TIME_MONEY.info("[WS] SEARCH EMPLOYEE_ID");
		
		LogUtil.TIME_MONEY.debug("\n###############################################################################"
	    		+"\n###WEB SERVICE PARAMETER..."
	    		+"\n###############################################################################"
				+"\n###SEC USER ID   : "+secUserId
				+"\n###############################################################################\n");
		String employeeId = null;
		
		CCTConnection conn = null;
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			
			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
			employeeId = manager.searchEmployeeId(secUserId);
			
		} catch (Exception e) {
			LogUtil.TIME_MONEY.error("", e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return employeeId;
	}
	
	
	
	
	
//	/**
//	 * COUNT SEARCH MAIN
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIds
//	 * @return
//	 */
//	@WebMethod
//	public long countSearchMain(String startDate, String endDate, String secUserIds) {
//		LogUtil.TIME_MONEY.info("[WS] COUNT SEARCH MAIN");
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[COUNT SEARCH MAIN]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		long countResult = 0;
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			countResult = manager.countSearchMain(startDate, endDate, secUserIds);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return countResult;
//	}
//	
//	
//	
//	/**
//	 * COUNT SEARCH PERSON
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIds
//	 * @param searchType
//	 * @return
//	 */
//	@WebMethod
//	public long countSearchPerson(String startDate, String endDate, String secUserIds) {
//		LogUtil.TIME_MONEY.info("[WS] COUNT SEARCH PERSON");
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[COUNT SEARCH PERSON]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		long countResult = 0;
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			countResult = manager.countSearchPerson(startDate, endDate, secUserIds);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return countResult;
//	}
//	
//	
//	
//	/**
//	 * COUNT SEARCH DEPARTMENT
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIdss
//	 * @param searchType
//	 * @return
//	 */
//	@WebMethod
//	public long countSearchDepartment(String startDate, String endDate, String secUserIds) {
//		LogUtil.TIME_MONEY.info("[WS] COUNT SEARCH DEPARTMENT");
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[COUNT SEARCH DEPARTMENT]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		long countResult = 0;
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			countResult = manager.countSearchDepartment(startDate, endDate, secUserIds);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return countResult;
//	}
//	
//	
//	
//	/**
//	 * COUNT SEARCH RANK PERSON
//	 * @param startDate
//	 * @param endDate
//	 * @return
//	 */
//	@WebMethod
//	public long countSearchRankPerson(String startDate, String endDate, String secUserIds) {
//		LogUtil.TIME_MONEY.info("[WS] COUNT SEARCH RANK PERSON");
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[COUNT SEARCH RANK PERSON]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		long countResult = 0;
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			countResult = manager.countSearchRankPerson(startDate, endDate, secUserIds);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return countResult;
//	}
//	
//	
//	
//	/**
//	 * COUNT SEARCH RANK DEPARTMENT
//	 * @param startDate
//	 * @param endDate
//	 * @return
//	 */
//	@WebMethod
//	public long countSearchRankDepartment(String startDate, String endDate, String secUserIds) {
//		LogUtil.TIME_MONEY.info("[WS] COUNT SEARCH RANK DEPARTMENT");
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[COUNT SEARCH RANK DEPARTMENT]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		long countResult = 0;
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			countResult = manager.countSearchRankDepartment(startDate, endDate, secUserIds);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return countResult;
//	}
//	
//	
//	
//	/**
//	 * COUNT SEARCH REPORT
//	 * @param startDate
//	 * @param endDate
//	 * @param departmentIds
//	 * @return
//	 */
//	@WebMethod
//	public long countSearchReport(String startDate, String endDate, String secUserIds) {
//		LogUtil.TIME_MONEY.info("[WS] COUNT SEARCH REPORT");
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[COUNT SEARCH REPORT]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		long countResult = 0;
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			countResult = manager.countSearchReport(startDate, endDate, secUserIds);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return countResult;
//	}
//	
//	
//	
//
//	
//	
//	/**
//	 * SEARCH MAIN
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserIds
//	 * @return
//	 */
//	@WebMethod
//	public List<TimeMoney> searchMain(String startDate, String endDate, String secUserIds) {
//		LogUtil.TIME_MONEY.info("[WS] SEARCH MAIN");
//		
////		startDate = "01/05/2010"; 	//FIXME
////		endDate = "30/05/2010";		//FIXME
//		
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[SEARC MAIN]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		List<TimeMoney> response = new ArrayList<TimeMoney>();
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			response = manager.searchMain(startDate, endDate, secUserIds);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return response;
//	}
//
//	
//	
//	/**
//	 * SEARCH PERSON
//	 * @param startDate
//	 * @param endDate
//	 * @param secUserId
//	 * @return
//	 */
//	@WebMethod
//	public List<TimeMoney> searchPerson(String startDate, String endDate, String secUserIds, String searchType) {
//		LogUtil.TIME_MONEY.info("[WS] SEARCH PERSON");
//		
////		startDate = "01/05/2010";	//FIXME
////		endDate = "30/05/2010";		//FIXME
//		
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[SEARCH PERSON]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###SEARCH TYPE  : "+searchType
//				+"\n###############################################################################\n");
//		List<TimeMoney> response = new ArrayList<TimeMoney>();
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			response = manager.searchPerson(startDate, endDate, secUserIds, searchType);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return response;
//	}
//	
//	
//	
//	/**
//	 * SEARCH DEPARTMENT
//	 * @param startDate
//	 * @param endDate
//	 * @param departmentIds
//	 * @return
//	 */
//	@WebMethod
//	public List<TimeMoney> searchDepartment(String startDate, String endDate, String secUserIds, String searchType, String jsonMapDepartment) {
//		LogUtil.TIME_MONEY.info("[WS] SEARCH DEPARTMENT");
//		
////		startDate	= "01/05/2010"; //FIXME
////		endDate 	= "30/05/2010"; //FIXME
//		
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[SEARCH DEPARTMENT]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###SEARCH TYPE  : "+searchType
//				+"\n###############################################################################\n");
//		List<TimeMoney> response = new ArrayList<TimeMoney>();
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			response = manager.searchDepartment(startDate, endDate, secUserIds, searchType, jsonMapDepartment);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return response;
//	}
//	
//	
//	
//	/**
//	 * SEARCH RANK_PERSON
//	 * @param startDate
//	 * @param endDate
//	 * @param positionIds
//	 * @return
//	 */
//	@WebMethod
//	public List<TimeMoney> searchRankPerson(String startDate, String endDate, String secUserIds) {
//		LogUtil.TIME_MONEY.info("[WS] SEARCH RANK PERSON");
//		
////		startDate 	= "01/05/2010";	//FIXME
////		endDate 	= "30/05/2010";	//FIXME
//		
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[SEARCH RANK PERSON]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		List<TimeMoney> response = new ArrayList<TimeMoney>();
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			response = manager.searchRankPerson(startDate, endDate, secUserIds);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return response;
//	}
//	
//	
//	
//	/**
//	 * SEARCH RANK_DEPARTMENT
//	 * @param startDate
//	 * @param endDate
//	 * @return
//	 */
//	@WebMethod
//	public List<TimeMoney> searchRankDepartment(String startDate, String endDate, String secUserIds, String jsonMapDepartment) {
//		LogUtil.TIME_MONEY.info("[WS] SEARCH RANK DEPARTMENT");
//		
////		startDate 	= "01/05/2010"; //FIXME
////		endDate 	= "30/05/2010";	//FIXME
//		
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[SEARCH RANK DEPARTMENT]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		List<TimeMoney> response = new ArrayList<TimeMoney>();
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			response = manager.searchRankDepartment(startDate, endDate, secUserIds, jsonMapDepartment);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return response;
//	}
//	
//	
//	
//	/**
//	 * SEARCH REPORT
//	 * @param startDate
//	 * @param endDate
//	 * @param departmentIds
//	 * @return
//	 */
//	@WebMethod
//	public List<TimeMoney> searchReport(String startDate, String endDate, String secUserIds, String jsonMapDepartment) {
//		LogUtil.TIME_MONEY.info("[WS] SEARCH REPORT");
//		
////		startDate 	= "01/05/2010"; //FIXME
////		endDate 	= "30/05/2010"; //FIXME
//		
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[SEARCH REPORT]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		List<TimeMoney> response = new ArrayList<TimeMoney>();
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			response = manager.searchReport(startDate, endDate, secUserIds, "", jsonMapDepartment);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return response;
//	}
//	
//	
//	
//	/**
//	 * SEARCH REPORT_TOPTEN
//	 * @param startDate
//	 * @param endDate
//	 * @param departmentIds
//	 * @return
//	 */
//	@WebMethod
//	public List<TimeMoney> searchReportTopTen(String startDate, String endDate, String secUserIds, String jsonMapDepartment) {
//		LogUtil.TIME_MONEY.info("[WS] SEARCH REPORT TOP_TEN");
//		
////		startDate 	= "01/05/2010"; //FIXME
////		endDate 	= "30/05/2010"; //FIXME
//		
//		LogUtil.TIME_MONEY.debug("\n###############################################################################"
//	    		+"\n###WEB SERVICE PARAMETER...[SEARCH REPORT TOP_TEN]"
//	    		+"\n###############################################################################"
//				+"\n###DATE START   : "+startDate
//				+"\n###DATE END     : "+endDate
//				+"\n###SEC USER IDS : "+secUserIds
//				+"\n###############################################################################\n");
//		List<TimeMoney> response = new ArrayList<TimeMoney>();
//		
//		CCTConnection conn = null;
//		try {
//			
//			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
//			
//			TimeMoneyManager manager = new TimeMoneyManager(conn, null);
//			response = manager.searchReport(startDate, endDate, secUserIds, "TOPTEN", jsonMapDepartment);
//			
//		} catch (Exception e) {
//			LogUtil.TIME_MONEY.error("", e);
//		} finally {
//			CCTConnectionUtil.close(conn);
//		}
//		return response;
//	}
//	
//	
	
	
}
