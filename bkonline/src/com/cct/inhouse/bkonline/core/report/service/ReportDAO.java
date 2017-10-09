package com.cct.inhouse.bkonline.core.report.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonSelectItem;
import com.cct.common.CommonUser;
import com.cct.inhouse.bkonline.core.report.domain.Report;
import com.cct.inhouse.bkonline.core.report.domain.ReportData;
import com.cct.inhouse.bkonline.core.report.domain.ReportSearchCriteria;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;

public class ReportDAO extends AbstractDAO<ReportSearchCriteria , ReportData , ReportData>{

	public ReportDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}
	@Override
	protected ReportData createResultSearch(ResultSet rst, CommonUser user, Locale local) throws Exception {
		return null;
	}
	@Override
	protected ReportData createResultSearchById(ResultSet arg0, CommonUser arg1, Locale arg2) throws Exception {
		return null;
	}
	@Override
	protected String createSQLAdd(CCTConnection conn, ReportData even, CommonUser user, Locale local) throws Exception {
		return null;
	}
	@Override
	protected String createSQLCheckDup(CCTConnection conn, ReportData even, CommonUser user, Locale local) throws Exception {
		return null;
	}
	@Override
	protected String createSQLCountData(CCTConnection arg0, ReportSearchCriteria arg1, CommonUser arg2, Locale arg3) throws Exception {
		return null;
	}
	@Override
	protected String createSQLDelete(CCTConnection arg0, String arg1, CommonUser arg2, Locale arg3) throws Exception {
		return null;
	}
	@Override
	protected String createSQLEdit(CCTConnection conn, ReportData data, CommonUser user, Locale local) throws Exception {
		return null;
	}
	@Override
	protected String createSQLSearch(CCTConnection arg0, ReportSearchCriteria arg1, CommonUser arg2, Locale arg3) throws Exception {
		String sql = SQLUtil.getSQLString(
				arg0.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchRoomData"
				 );
		getLogger().debug(sql);
		return sql;
	}
	@Override
	protected String createSQLSearchById(CCTConnection arg0, String arg1, CommonUser arg2, Locale arg3) throws Exception {
		return null;
	}
	@Override
	protected String createSQLUpdateActive(CCTConnection arg0, String arg1, String arg2, CommonUser arg3, Locale arg4) throws Exception {
		return null;
	}
	
	protected List<Report> searchListRoom(CCTConnection conn) throws Exception {
		getLogger().debug("searchListRoom");
		
		List<Report> lstResult = new ArrayList<Report>();
		
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchRoomData"
				);
		
		getLogger().debug("sql searchRoomData : " + sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while (rst.next()) {
				Report rp = new Report();
				rp.setKey(rst.getString("room_id"));
				rp.setValue(rst.getString("room_name"));
				rp.setChecked(true);
				lstResult.add(rp);
			}
			
		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return lstResult;
	}
	
	protected List<CommonSelectItem> searchListMonth(CCTConnection conn) throws Exception {
		getLogger().debug("searchListMonth");
		
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchListMonth"
				);
		
		getLogger().debug("sql searchListMonth : " + sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while (rst.next()) {
				CommonSelectItem rp = new CommonSelectItem();
				rp.setKey(rst.getString("LIST_ON"));
				rp.setValue(rst.getString("GLOBAL_DATA_VALUE"));
				lstResult.add(rp);
			}
			
		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return lstResult;
	}
	protected List<CommonSelectItem> searchListYears(CCTConnection conn) throws Exception {
		getLogger().debug("searchListYears");
		
		List<CommonSelectItem> lstResult = new ArrayList<CommonSelectItem>();
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchListYears"
				);
		
		getLogger().debug("sql searchListYears : " + sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while (rst.next()) {
				CommonSelectItem rp = new CommonSelectItem();
				rp.setKey(rst.getString("LIST_ON"));
				rp.setValue(rst.getString("GLOBAL_DATA_VALUE"));
				lstResult.add(rp);
			}
			
		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return lstResult;
	}
}
