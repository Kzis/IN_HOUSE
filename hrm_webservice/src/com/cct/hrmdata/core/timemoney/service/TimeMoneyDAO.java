package com.cct.hrmdata.core.timemoney.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.log.LogUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

import com.cct.abstracts.AbstractDAO;
import com.cct.hrmdata.core.timemoney.domain.TimeMoney;

public class TimeMoneyDAO extends AbstractDAO{
	/**
	 * SEARCH COUNT TIME_MONEY
	 * @param conn
	 * @param dateStart
	 * @param dateEnd
	 * @param secUserIds
	 * @return
	 * @throws Exception
	 */
//	protected long searchCountTimeMoney(CCTConnection conn, String dateStart, String dateEnd, String secUserIds) throws Exception {
//		long countResult = 0;
//		
//		int paramIndex = 0;
//		Object[] params = new Object[21];
//		dateStart 	= StringUtil.replaceSpecialString(dateStart, conn.getDbType(), ResultType.NULL);
//		dateEnd 	= StringUtil.replaceSpecialString(dateEnd, conn.getDbType(), ResultType.NULL);
//		secUserIds 	= StringUtil.replaceSpecialString(secUserIds, conn.getDbType(), ResultType.NULL);
//		params[paramIndex++] = dateStart;
//		params[paramIndex++] = dateEnd;
//		params[paramIndex++] = secUserIds == null ? "" : "AND SE.USERID IN( "+secUserIds+" )";
//		
//		params[paramIndex++] = dateStart;
//		params[paramIndex++] = dateEnd;
//		params[paramIndex++] = dateStart;
//		params[paramIndex++] = dateStart;
//		params[paramIndex++] = dateEnd;
//		params[paramIndex++] = secUserIds == null ? "" : "AND A.USERID IN( "+secUserIds+" )";
//		params[paramIndex++] = dateStart;
//		params[paramIndex++] = dateEnd;
//		params[paramIndex++] = secUserIds == null ? "" : "AND SE.USERID IN( "+secUserIds+" )";
//		
//		params[paramIndex++] = dateStart;
//		params[paramIndex++] = dateEnd;
//		params[paramIndex++] = dateStart;
//		params[paramIndex++] = dateStart;
//		params[paramIndex++] = dateEnd;
//		params[paramIndex++] = secUserIds == null ? "" : "AND B.USERID IN( "+secUserIds+" )";
//		params[paramIndex++] = dateStart;
//		params[paramIndex++] = dateEnd;
//		params[paramIndex++] = secUserIds == null ? "" : "AND SE.USERID IN( "+secUserIds+" )";
//		
//		String sql = SQLUtil.getSQLString(
//										conn.getSchemas()
//										, getSqlPath().getClassName()
//										, getSqlPath().getPath()
//										, "searchCountTimeMoney"
//										, params
//				 						);
////		LogUtil.TIME_MONEY.debug("\n\n—————————————————————————————————————————————————————————————————————————————————"
////				+ "\n[ SQL ] SEARCH COUNT TIME_MONEY"
////				+"\n—————————————————————————————————————————————————————————————————————————————————\n\n"
////				+ sql +"\n\n");
//		Statement stmt = null;
//		ResultSet rst = null;
//		try{
//			stmt = conn.createStatement();
//			rst = stmt.executeQuery(sql);
//			
//			if (rst.next()) {
//				countResult = rst.getLong("TOT");
//			}
//			
//			
//		}catch (Exception e){
//			throw e;
//		}finally{
//			CCTConnectionUtil.closeAll(rst, stmt);
//		}
//		LogUtil.TIME_MONEY.debug("\n\n—————————————————————————————————————————————————————————————————————————————————"
//				+ "\n[ SQL ] SEARCH COUNT TIME_MONEY"
//				+"\n—————————————————————————————————————————————————————————————————————————————————\n"
//				+"###TOTAL : "+countResult
//				+"\n\n");
//		return countResult;
//		
//	}
	
	
	
	/**
	 * SEARCH EMPLOYYE_ID
	 * @param conn
	 * @param secUserId
	 * @return
	 * @throws Exception
	 */
	protected String searchEmployeeId(CCTConnection conn, String secUserId) throws Exception {
		String employeeId = null;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.replaceSpecialString(secUserId, conn.getDbType(), ResultType.NULL);
		String sql = SQLUtil.getSQLString(
										conn.getSchemas()
										, getSqlPath().getClassName()
										, getSqlPath().getPath()
										, "searchEmployeeId"
										, params
				 						);
		LogUtil.TIME_MONEY.debug("\n\n—————————————————————————————————————————————————————————————————————————————————"
				+ "\n[ SQL ] EMPLOYEE_ID"
				+"\n—————————————————————————————————————————————————————————————————————————————————\n\n"
				+ sql +"\n\n");
		
		Statement stmt = null;
		ResultSet rst = null;
		try{
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				employeeId = StringUtil.nullToString(rst.getString("EMPLOYEEID"));
			}
			
			
		}catch (Exception e){
			throw e;
		}finally{
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return employeeId;
		
	}
	
	
	
	/**
	 * SEARCH TIME_MONEY
	 * @param conn
	 * @param dateStart
	 * @param dateEnd
	 * @param secUserIds
	 * @param searchType
	 * @return
	 * @throws Exception
	 */
	protected List<TimeMoney> searchTimeMoney(CCTConnection conn, String dateStart, String dateEnd, String secUserIds, String searchType) throws Exception {
		List<TimeMoney> lstResult = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[22];
		dateStart 	= StringUtil.replaceSpecialString(dateStart, conn.getDbType(), ResultType.NULL);
		dateEnd 	= StringUtil.replaceSpecialString(dateEnd, conn.getDbType(), ResultType.NULL);
		secUserIds 	= StringUtil.replaceSpecialString(secUserIds, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = dateStart;
		params[paramIndex++] = dateEnd;
		params[paramIndex++] = secUserIds == null ? "" : "AND SE.USERID IN( "+secUserIds+" )";
		
		params[paramIndex++] = dateStart;
		params[paramIndex++] = dateEnd;
		params[paramIndex++] = dateStart;
		params[paramIndex++] = dateStart;
		params[paramIndex++] = dateEnd;
		params[paramIndex++] = secUserIds == null ? "" : "AND A.USERID IN( "+secUserIds+" )";
		params[paramIndex++] = dateStart;
		params[paramIndex++] = dateEnd;
		params[paramIndex++] = secUserIds == null ? "" : "AND SE.USERID IN( "+secUserIds+" )";
		
		params[paramIndex++] = dateStart;
		params[paramIndex++] = dateEnd;
		params[paramIndex++] = dateStart;
		params[paramIndex++] = dateStart;
		params[paramIndex++] = dateEnd;
		params[paramIndex++] = secUserIds == null ? "" : "AND B.USERID IN( "+secUserIds+" )";
		params[paramIndex++] = dateStart;
		params[paramIndex++] = dateEnd;
		params[paramIndex++] = secUserIds == null ? "" : "AND SE.USERID IN( "+secUserIds+" )";
		
		if(searchType.equals("CHART")){
			params[paramIndex] = "ORDER BY MONTHS ASC, FIRSTNAMETHA ASC";	
		}else if(searchType.equals("MONTH")){
			params[paramIndex] = "ORDER BY MONTHS DESC, FIRSTNAMETHA DESC";	
		}else if(searchType.equals("REPORT")){
			//REPORT
			params[paramIndex] = "ORDER BY FIRSTNAMETHA, MONTHS ASC, DAYS ASC";
		}else{
			//SEARCH MAIN , SEARCH RANK
			params[paramIndex] = "ORDER BY MONTHS DESC, DAYS DESC, LATE DESC";
		}
		
		String sql = SQLUtil.getSQLString(
										conn.getSchemas()
										, getSqlPath().getClassName()
										, getSqlPath().getPath()
										, "searchTimeMoney"
										, params
				 						);
		LogUtil.TIME_MONEY.debug("\n\n—————————————————————————————————————————————————————————————————————————————————"
				+ "\n[ SQL ] SEARCH TIME_MONEY"
				+"\n—————————————————————————————————————————————————————————————————————————————————\n\n"
				+ sql +"\n\n");
		Statement stmt = null;
		ResultSet rst = null;
		try{
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			TimeMoney obj = null;
			String imageAvartar = "";
			while (rst.next()) {
				obj = new TimeMoney();
				obj.setLateTime(rst.getLong("LATE"));
				obj.setEmployeeId(rst.getInt("EMPLOYEEID"));
				obj.setSecUserId(rst.getInt("USERID"));
				obj.setWorkDate(StringUtil.nullToString(rst.getString("WORKDATES")));
				obj.setCheckLeave(0);
				obj.setPositionId(StringUtil.nullToString(rst.getString("POSITIONID")));
				obj.setCardId(StringUtil.nullToString(rst.getString("CARDID")));
				obj.setFirstName(StringUtil.nullToString(rst.getString("FIRSTNAMETHA")));
				obj.setLastName(StringUtil.nullToString(rst.getString("LASTNAMETHA")));
				obj.setNickName(StringUtil.nullToString(rst.getString("NICKNAME")));
				obj.setWorkTimeIn(StringUtil.nullToString(rst.getString("WORKTIME_IN")));
				obj.setWorkTimeOut(StringUtil.nullToString(rst.getString("WORKTIME_OUT")));
				obj.setScanTimeIn(StringUtil.nullToString(rst.getString("SCANTIME_IN")));
				obj.setScanTimeOut(StringUtil.nullToString(rst.getString("SCANTIME_OUT")));
				obj.setScanTimeInDisplay(StringUtil.nullToString(rst.getString("SCANTIME_IN_DISPLAY")));
				obj.setDays(StringUtil.nullToString(rst.getString("DAYS")));
				obj.setYears(StringUtil.nullToString(rst.getString("YEARS")));
				obj.setMonths(StringUtil.nullToString(rst.getString("MONTHS")));
				imageAvartar = StringUtil.nullToString(rst.getString("CARDID"));
				obj.setImageAvartar(imageAvartar == null ? "default.jpg": imageAvartar + ".jpg");
				
				lstResult.add(obj);
			}
			
		}catch (Exception e){
			throw e;
		}finally{
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return lstResult;
		
	}
	
	
	
}
