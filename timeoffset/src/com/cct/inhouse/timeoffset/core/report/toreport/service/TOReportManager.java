package com.cct.inhouse.timeoffset.core.report.toreport.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cct.abstracts.AbstractManager;
import com.cct.exception.MaxExceedReportException;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReport;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReportSearch;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReportSearchCriteria;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class TOReportManager extends AbstractManager<TOReportSearchCriteria, TOReportSearch, TOReport, InhouseUser, Locale> {

	private TOReportService service;

	public TOReportManager(Logger logger, InhouseUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new TOReportService(logger, user, locale);
	}

	public XSSFWorkbook export(CCTConnection conn , TOReportSearchCriteria criteria) throws Exception {

		Statement stmt = null;
		ResultSet rst = null;
		
		List<List<TOReport>> listResult = new ArrayList<List<TOReport>>();
		
		try {

			criteria.setTotalResult(service.countData(conn,criteria));

			if (criteria.getTotalResult() == 0) {
				return null;
			} else if ((criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceedReport())) {
				throw new MaxExceedReportException();
			} else {

				stmt = conn.createStatement();

				// ค้นหาข้อมูล
				listResult = service.searchReport(conn,criteria, stmt);
				
				if(listResult.size() > 0){
					return service.exportReport(criteria, listResult);
				}
							
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return null;
	}

	@Override
	public List<TOReportSearch> search(CCTConnection conn,
			TOReportSearchCriteria criteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TOReport searchById(CCTConnection conn, String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long add(CCTConnection conn, TOReport obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(CCTConnection conn, TOReport obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(CCTConnection conn, String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateActive(CCTConnection conn, String ids, String activeFlag)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
