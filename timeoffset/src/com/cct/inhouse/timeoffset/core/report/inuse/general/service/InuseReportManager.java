package com.cct.inhouse.timeoffset.core.report.inuse.general.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cct.abstracts.AbstractManager;
import com.cct.exception.MaxExceedReportException;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.report.inuse.general.domain.InuseReport;
import com.cct.inhouse.timeoffset.core.report.inuse.general.domain.InuseReportSearch;
import com.cct.inhouse.timeoffset.core.report.inuse.general.domain.InuseReportSearchCriteria;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class InuseReportManager extends AbstractManager<InuseReportSearchCriteria, InuseReportSearch, InuseReport, InhouseUser, Locale> {

	InuseReportService service = null;

	public InuseReportManager(Logger logger, InhouseUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new InuseReportService(logger, user, locale);
	}

	public XSSFWorkbook exportExcel(CCTConnection conn, InuseReportSearchCriteria criteria) throws Exception {

		Statement stmt = null;
		ResultSet rst = null;

		try {

			criteria.setTotalResult(service.countData(conn,criteria));

			if (criteria.getTotalResult() == 0) {
				return null;
			} else if ((criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceedReport())) {
				throw new MaxExceedReportException();
			} else {

				stmt = conn.createStatement();

				// ค้นหาข้อมูล
				rst = service.searchReport(conn,criteria, stmt);

				if (rst != null) {
					return service.exportReportExcel(criteria, rst);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return null;
	}

	public byte[] exportPdf(CCTConnection conn,InuseReportSearchCriteria criteria) throws Exception {

		Statement stmt = null;
		ResultSet rst = null;

		try {

			criteria.setTotalResult(service.countData(conn,criteria));

			if (criteria.getTotalResult() == 0) {
				return null;
			} else if ((criteria.isCheckMaxExceed()) && (criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceedReport())) {
				throw new MaxExceedReportException();
			} else {

				stmt = conn.createStatement();

				// ค้นหาข้อมูล
				rst = service.searchReport(conn,criteria, stmt);

				if (rst != null) {
					return service.exportPdf(criteria, rst);
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
	public List<InuseReportSearch> search(CCTConnection conn,
			InuseReportSearchCriteria criteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InuseReport searchById(CCTConnection conn, String id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long add(CCTConnection conn, InuseReport obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(CCTConnection conn, InuseReport obj) throws Exception {
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
