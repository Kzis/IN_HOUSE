package com.cct.hrmdata.core.gettimeoffset.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.database.CCTConnection;
import util.database.SQLUtil;
import util.log.LogUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

import com.cct.abstracts.AbstractDAO;
import com.cct.hrmdata.core.gettimeoffset.domain.WorkOnsite;

public class GetTimeOffsetDAO extends AbstractDAO{

	protected List<WorkOnsite> searchWorkOnsite(CCTConnection conn, String processDate, String userId) throws Exception {
		
		List<WorkOnsite> listWorkOnSite = new ArrayList<WorkOnsite>();
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.stringToNull(processDate);
		params[paramIndex++] = StringUtil.replaceSpecialString(userId, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToNull(processDate);
		params[paramIndex++] = StringUtil.replaceSpecialString(userId, conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchWorkOnSite"
				, params
				 );
		LogUtil.GET_TIME_OFFSET.debug("SQL searchWorkOnSite :" +sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try{
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			WorkOnsite workOnsite = null;
			while (rst.next()) {
				workOnsite = new WorkOnsite();
				workOnsite.setOnsiteid(rst.getString("ONSITEID"));
				workOnsite.setUserid(rst.getString("USERID"));
				workOnsite.setEmployeeid(rst.getString("EMPLOYEEID"));
				workOnsite.setFirstnametha(rst.getString("FIRSTNAMETHA"));
				workOnsite.setLastnametha(rst.getString("LASTNAMETHA"));
				workOnsite.setNickname(rst.getString("NICKNAME"));
				workOnsite.setWorkonsiteid(rst.getString("WORKONSITEID"));
				workOnsite.setOnsitedatefrom1(rst.getString("ONSITEDATEFROM1"));
				workOnsite.setOnsitetimefrom1(rst.getString("ONSITETIMEFROM1"));
				workOnsite.setOnsitedateto2(rst.getString("ONSITEDATETO2"));
				workOnsite.setOnsitetimeto2(rst.getString("ONSITETIMETO2"));
				workOnsite.setTotalOnsiteday(rst.getInt("TOTAL_ONSITEDAY"));
				workOnsite.setTotalOnsitetime(rst.getInt("TOTAL_ONSITETIME"));
				workOnsite.setSiteservice(rst.getString("SITESERVICE"));
				workOnsite.setOnsitestatus(rst.getString("ONSITESTATUS"));
				workOnsite.setApproverid(rst.getString("APPROVERID"));
				workOnsite.setApprovets(rst.getString("APPROVETS"));
				workOnsite.setSiteserviceRemark(rst.getString("SITESERVICE_REMARK"));
				workOnsite.setOnsitestatusRemark(rst.getString("ONSITESTATUS_REMARK"));
				listWorkOnSite.add(workOnsite);
				
			}
			
		}catch (Exception e){
			throw e;
		}
		
		return listWorkOnSite;
	}

}
