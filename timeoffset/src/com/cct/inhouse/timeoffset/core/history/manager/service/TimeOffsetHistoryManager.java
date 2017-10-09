package com.cct.inhouse.timeoffset.core.history.manager.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonUser;
import com.cct.exception.MaxExceedException;
import com.cct.exception.MaxExceedReportException;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.history.manager.domain.HistorySearch;
import com.cct.inhouse.timeoffset.core.history.manager.domain.HistorySearchCriteria;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class TimeOffsetHistoryManager extends AbstractManager<HistorySearchCriteria, HistorySearch, Object, CommonUser, Locale> {

	private TimeOffsetHistoryService service = null;

	public TimeOffsetHistoryManager(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new TimeOffsetHistoryService(logger, user, locale);
	}

	@Override
	public List<HistorySearch> search(CCTConnection conn, HistorySearchCriteria criteria) throws Exception {
		
		List<HistorySearch> listResult = new ArrayList<HistorySearch>();
		try {
			criteria.setTotalResult(service.countData(conn, criteria));

			if (criteria.getTotalResult() == 0) {

			} else if ( (criteria.isCheckMaxExceed()) && (criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceed())) {
				throw new MaxExceedException();
			} else {
				// ค้นหาข้อมูล
				listResult = service.search(conn, criteria);
			}
		} catch (Exception e) {
			throw e;
		}

		return listResult;
	}


	public XSSFWorkbook export(CCTConnection conn, HistorySearchCriteria criteria) throws Exception{

		Statement stmt = null;
		ResultSet rst = null;
		
		try {
			criteria.setTotalResult(service.countData(conn, criteria));
			
			if (criteria.getTotalResult() == 0) {
				return null;
			} else if (criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceedReport()) {
				throw new MaxExceedReportException();
			} else {
				stmt = conn.createStatement();
				
				// ค้นหาข้อมูล
				rst = service.searchReport(conn, criteria,stmt);
							
				if(rst != null){
					return service.exportReport(criteria,rst);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally{
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return null;
	}
	

	@Override
	public long add(CCTConnection conn, Object obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(CCTConnection conn, String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(CCTConnection conn, Object obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object searchById(CCTConnection conn, String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
