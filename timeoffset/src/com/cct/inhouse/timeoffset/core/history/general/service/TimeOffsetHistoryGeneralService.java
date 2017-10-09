package com.cct.inhouse.timeoffset.core.history.general.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PageOrder;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.history.general.domain.HistoryGeneralSearch;
import com.cct.inhouse.timeoffset.core.history.general.domain.HistoryGeneralSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;
import com.cct.inhouse.timeoffset.util.report.ReportPOIManagerUtil;

import util.database.CCTConnection;
import util.string.StringUtil;

public class TimeOffsetHistoryGeneralService extends AbstractService {

	private TimeOffsetHistoryGeneralDAO dao = null;

	public TimeOffsetHistoryGeneralService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new TimeOffsetHistoryGeneralDAO(logger, SQLPath.TO_HISTORY.getSqlPath(), user, locale);

		try {
			bundle = ResourceBundle.getBundle("resources.bundle.inhouse.common.Message", locale);
			bundleTO = ResourceBundle.getBundle("resources.bundle.inhouse.timeoffset.MessageTO", locale);	
			
			// Create Cell Style
			styleHeader = ReportPOIManagerUtil.createHeaderStyle(workbook);
			styleCriteriaLabelRight = createCriteriaLabelStyle(workbook);
			styleCriteriaValue = createCriteriaValueStyle(workbook);
			styleTableHeader = createTableHeaderStyle(workbook);

			styleNormalCenter = ReportPOIManagerUtil.createStyleCN14(workbook, 1, 1, 1, 1);
			styleNormalLeft = ReportPOIManagerUtil.createStyleLN14(workbook, 1, 1, 1, 1);
			styleNormalRight = ReportPOIManagerUtil.createStyleRN14(workbook, 1, 1, 1, 1);

			styleNormalCenterNoBorder = ReportPOIManagerUtil.createStyleCN14(workbook, 0, 0, 0, 0);
			styleNormalLeftNoBorder = ReportPOIManagerUtil.createStyleLN14(workbook, 0, 0, 0, 0);
			styleNormalRightNoBorder = ReportPOIManagerUtil.createStyleRN14(workbook, 0, 0, 0, 0);

			styleWaitApproveNormalCenter = createWaitApproveNormalCenterStyle(workbook);
			styleWaitApproveNormalLeft = createWaitApproveNormalLeftStyle(workbook);
			styleWaitApproveNormalRight = createWaitApproveNormalRightStyle(workbook);

			styleDisApproveNormalCenter = createDisApproveNormalCenterStyle(workbook);
			styleDisApproveNormalLeft = createDisApproveNormalLeftStyle(workbook);
			styleDisApproveNormalRight = createDisApproveNormalRightStyle(workbook);

			styleNormalRightBoldNoBorder = createCriteriaLabelStyle(workbook);
			styleNormalLeftBoldNoBorder = createCriteriaValueStyle(workbook);

			DAY = " " + bundleTO.getString("to.day") + " ";
			HOUR = " " + bundleTO.getString("to.hour") + " ";
			MINUTE = " " + bundleTO.getString("to.min") + " ";
			
		} catch (Exception e) {
			getLogger().error("", e);
		}
	}

	protected long countData(CCTConnection conn, HistoryGeneralSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria);
	}

	protected List<HistoryGeneralSearch> search(CCTConnection conn, HistoryGeneralSearchCriteria criteria) throws Exception {
		return dao.search(conn, criteria);
	}

	protected ResultSet searchReport(CCTConnection conn, HistoryGeneralSearchCriteria criteria, Statement stmt) throws Exception {
		return dao.searchReport(conn, stmt, criteria);
	}

	private static int TOTAL_DAY_APPROVE = 0;
	private static int TOTAL_HOUR_APPROVE = 0;
	private static int TOTAL_MINUTE_APPROVE = 0;

	private static int TOTAL_DAY_DIS_APPROVE = 0;
	private static int TOTAL_HOUR_DIS_APPROVE = 0;
	private static int TOTAL_MINUTE_DIS_APPROVE = 0;

	XSSFWorkbook workbook = new XSSFWorkbook();

	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;

	// Create Cell Style
	XSSFCellStyle styleHeader;
	XSSFCellStyle styleCriteriaLabelRight;
	XSSFCellStyle styleCriteriaValue;
	XSSFCellStyle styleTableHeader;

	XSSFCellStyle styleNormalCenter;
	XSSFCellStyle styleNormalLeft;
	XSSFCellStyle styleNormalRight;

	XSSFCellStyle styleNormalCenterNoBorder;
	XSSFCellStyle styleNormalLeftNoBorder;
	XSSFCellStyle styleNormalRightNoBorder;

	XSSFCellStyle styleWaitApproveNormalCenter;
	XSSFCellStyle styleWaitApproveNormalLeft;
	XSSFCellStyle styleWaitApproveNormalRight;

	XSSFCellStyle styleDisApproveNormalCenter;
	XSSFCellStyle styleDisApproveNormalLeft;
	XSSFCellStyle styleDisApproveNormalRight;

	XSSFCellStyle styleNormalRightBoldNoBorder;
	XSSFCellStyle styleNormalLeftBoldNoBorder;

	private static String DAY = "";
	private static String HOUR = "";
	private static String MINUTE = "";

	ResourceBundle bundleTO = null;
	ResourceBundle bundle = null;

	private XSSFCellStyle createCriteriaLabelStyle(XSSFWorkbook wb) throws Exception {

		XSSFCellStyle style;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 16);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		} catch (Exception e) {
			throw e;
		}
		return style;
	}

	private XSSFCellStyle createCriteriaValueStyle(XSSFWorkbook wb) throws Exception {

		XSSFCellStyle style;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 16);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.LIGHT_BLUE.index);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		} catch (Exception e) {
			throw e;
		}
		return style;
	}

	private XSSFCellStyle createTableHeaderStyle(XSSFWorkbook wb) throws Exception {

		XSSFCellStyle style;
		short border = 0;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderTop(border);
			style.setBorderBottom(border);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

		} catch (Exception e) {
			throw e;
		}
		return style;

	}

	private XSSFCellStyle createWaitApproveNormalCenterStyle(XSSFWorkbook wb) throws Exception {

		XSSFCellStyle style;
		short border = 0;
		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop(border);
			style.setBorderBottom(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createDisApproveNormalRightStyle(XSSFWorkbook wb) throws Exception {
		XSSFCellStyle style;
		short border = 0;
		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop(border);
			style.setBorderBottom(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.CORAL.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createDisApproveNormalLeftStyle(XSSFWorkbook wb) throws Exception {
		XSSFCellStyle style;
		short border = 0;
		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop(border);
			style.setBorderBottom(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.CORAL.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createDisApproveNormalCenterStyle(XSSFWorkbook wb) throws Exception {

		XSSFCellStyle style;
		short border = 0;
		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop(border);
			style.setBorderBottom(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.CORAL.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createWaitApproveNormalRightStyle(XSSFWorkbook wb) throws Exception {

		XSSFCellStyle style;
		short border = 0;
		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop(border);
			style.setBorderBottom(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createWaitApproveNormalLeftStyle(XSSFWorkbook wb) throws Exception {

		XSSFCellStyle style;
		short border = 0;
		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop(border);
			style.setBorderBottom(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	protected XSSFWorkbook exportReport(HistoryGeneralSearchCriteria criteria, ResultSet rst) throws Exception {

		try {
			TOTAL_DAY_APPROVE = 0;
			TOTAL_HOUR_APPROVE = 0;
			TOTAL_MINUTE_APPROVE = 0;

			TOTAL_DAY_DIS_APPROVE = 0;
			TOTAL_HOUR_DIS_APPROVE = 0;
			TOTAL_MINUTE_DIS_APPROVE = 0;

			int curRow = 0;
			String sheetName = bundleTO.getString("to.reportHistory");

			// วาดหัวรายงาน
			curRow = createHeader(curRow, criteria, sheetName);

			// วาดหัวตารางของข้อมูล
			curRow = createHeaderDetail(curRow);

			// วาดตารางข้อมูล
			curRow = createDetail(curRow, rst);

			// วาดท้ายรายงาน
			curRow = createFooter(curRow);

		} catch (Exception e) {
			throw e;
		}

		return workbook;
	}

	private int createHeader(int curRow, HistoryGeneralSearchCriteria criteria, String sheetName) throws Exception {

		sheet = workbook.createSheet(sheetName);

		// Set sheet style
		sheet.setAutobreaks(true);
		sheet.getPrintSetup().setLandscape(false); // เอกสารแนวนอน
		sheet.getPrintSetup().setValidSettings(true);
		sheet.getPrintSetup().setFitHeight((short) 0); // fit กระดาษตั้ง
		sheet.getPrintSetup().setFitWidth((short) 1); // fit กระดาษแนวนอน
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		sheet.getPrintSetup().setPageOrder(PageOrder.OVER_THEN_DOWN);

		// Set Header
		row = sheet.createRow(curRow); // first row
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		cell.setCellStyle(styleHeader);
		cell.setCellValue(bundleTO.getString("to.reportHistory"));
		curRow++;

		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 1));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.dateFrom") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 2);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 3));
		cell.setCellStyle(styleCriteriaValue);

		if (criteria.getStartDate() != null && !criteria.getStartDate().isEmpty()) {
			cell.setCellValue(criteria.getStartDate().substring(0, 6) + TOUtil.convertDateToReport(criteria.getStartDate()));
		} else {
			cell.setCellValue(GlobalVariableTO.DAT_SPACE);
		}

		cell = row.createCell((short) 4);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 4, 5));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.dateTo") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 6);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 6, 7));
		cell.setCellStyle(styleCriteriaValue);

		if (criteria.getEndDate() != null && !criteria.getEndDate().isEmpty()) {
			cell.setCellValue(criteria.getEndDate().substring(0, 6) + TOUtil.convertDateToReport(criteria.getEndDate()));
		} else {
			cell.setCellValue(GlobalVariableTO.DAT_SPACE);
		}

		curRow++;

		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 1));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.approveStatusHRM") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 2);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 7));
		cell.setCellStyle(styleCriteriaValue);

		if (criteria.getApproveStatus() != null && !criteria.getApproveStatus().isEmpty()) {

			if (criteria.getApproveStatus().equals("W")) {
				cell.setCellValue(bundleTO.getString("to.waitApprove"));
			} else if (criteria.getApproveStatus().equals("D")) {
				cell.setCellValue(bundleTO.getString("to.disApprove"));
			} else if (criteria.getApproveStatus().equals("A")) {
				cell.setCellValue(bundleTO.getString("to.approve"));
			}

		} else {
			cell.setCellValue(bundle.getString("all"));
		}

		curRow++;
		curRow++;

		return curRow;
	}

	private int createHeaderDetail(int curRow) throws Exception {

		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.startDateTime"));

		cell = row.createCell((short) 1);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.endDateTime"));

		cell = row.createCell((short) 2);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.workPlace"));

		cell = row.createCell((short) 3);
		cell.setCellStyle(styleTableHeader);

		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 3));

		cell = row.createCell((short) 4);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalDay"));

		cell = row.createCell((short) 5);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalHour"));

		cell = row.createCell((short) 6);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalMinute"));

		cell = row.createCell((short) 7);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.approveStatusHRM"));

		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 10000);
		sheet.setColumnWidth(3, 0);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 4500);

		curRow++;

		return curRow;
	}

	private int createDetail(int curRow, ResultSet rst) throws Exception {

		try {

			String[] newTime = new String[3];
			String[] newDateTime = new String[2];
			int colIndex;

			while (rst.next()) {

				colIndex = 0;
				row = sheet.createRow((short) curRow);

				String onsiteStatus = StringUtil.nullToString(rst.getString("ONSITESTATUS"));

				if (onsiteStatus.equals(GlobalVariableTO.APPROVE_STATUS)) {

					onsiteStatus = bundleTO.getString("to.approve");

					// START_DATETIME
					//Process convert date time คศ เป็น พศ
					newDateTime = computeDateTime(StringUtil.nullToString(rst.getString("START_DATETIME")));
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(newDateTime[0] + " " + newDateTime[1]);

					// END_DATETIME
					//Process convert date time คศ เป็น พศ
					newDateTime = computeDateTime(StringUtil.nullToString(rst.getString("END_DATETIME")));
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(newDateTime[0] + " " + newDateTime[1]);

					// SITESERVICE_REMARK
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalLeftNoBorder);
					cell.setCellValue(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")));

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalLeftNoBorder);

					// Process compute day hour minute from hrm
					newTime = computeTime(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")), StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));

					// TOTAL_ONSITE_DAY
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[0]);
					TOTAL_DAY_APPROVE = TOTAL_DAY_APPROVE + Integer.parseInt(newTime[0]);

					// TOTAL_ONSITE_HOUR
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[1]);
					TOTAL_HOUR_APPROVE = TOTAL_HOUR_APPROVE + Integer.parseInt(newTime[1]);

					// TOTAL_ONSITE_MINUTE
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[2]);
					TOTAL_MINUTE_APPROVE = TOTAL_MINUTE_APPROVE + Integer.parseInt(newTime[2]);

					// ONSITESTATUS
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalLeftNoBorder);
					cell.setCellValue(onsiteStatus);

				} else if (onsiteStatus.equals(GlobalVariableTO.DIS_APPROVE_STATUS)) {

					onsiteStatus = bundleTO.getString("to.disApprove");

					// START_DATETIME
					//Process convert date time คศ เป็น พศ
					newDateTime = computeDateTime(StringUtil.nullToString(rst.getString("START_DATETIME")));
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleDisApproveNormalCenter);
					cell.setCellValue(newDateTime[0] + " " + newDateTime[1]);
					
					// END_DATETIME
					//Process convert date time คศ เป็น พศ
					newDateTime = computeDateTime(StringUtil.nullToString(rst.getString("END_DATETIME")));
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleDisApproveNormalCenter);
					cell.setCellValue(newDateTime[0] + " " + newDateTime[1]);

					// SITESERVICE_REMARK
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleDisApproveNormalLeft);
					cell.setCellValue(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")));

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleDisApproveNormalLeft);

					// Process compute day hour minute from hrm
					newTime = computeTime(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")), StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));

					// TOTAL_ONSITE_DAY
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleDisApproveNormalRight);
					cell.setCellValue(newTime[0]);

					// TOTAL_ONSITE_HOUR
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleDisApproveNormalRight);
					cell.setCellValue(newTime[1]);

					// TOTAL_ONSITE_MINUTE
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleDisApproveNormalRight);
					cell.setCellValue(newTime[2]);

					// ONSITESTATUS
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleDisApproveNormalLeft);
					cell.setCellValue(onsiteStatus);

				} else if (onsiteStatus.equals(GlobalVariableTO.WAIT_APPROVE_STATUS)) {

					onsiteStatus = bundleTO.getString("to.waitApprove");

					// START_DATETIME
					//Process convert date time คศ เป็น พศ
					newDateTime = computeDateTime(StringUtil.nullToString(rst.getString("START_DATETIME")));
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleWaitApproveNormalCenter);
					cell.setCellValue(newDateTime[0] + " " + newDateTime[1]);

					// END_DATETIME
					//Process convert date time คศ เป็น พศ
					newDateTime = computeDateTime(StringUtil.nullToString(rst.getString("END_DATETIME")));
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleWaitApproveNormalCenter);
					cell.setCellValue(newDateTime[0] + " " + newDateTime[1]);

					// SITESERVICE_REMARK
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleWaitApproveNormalLeft);
					cell.setCellValue(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")));

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleWaitApproveNormalLeft);

					// Process compute day hour minute from hrm
					newTime = computeTime(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")), StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));

					// TOTAL_ONSITE_DAY
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleWaitApproveNormalRight);
					cell.setCellValue(newTime[0]);
					TOTAL_DAY_DIS_APPROVE = TOTAL_DAY_DIS_APPROVE + Integer.parseInt(newTime[0]);

					// TOTAL_ONSITE_HOUR
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleWaitApproveNormalRight);
					cell.setCellValue(newTime[1]);
					TOTAL_HOUR_DIS_APPROVE = TOTAL_HOUR_DIS_APPROVE + Integer.parseInt(newTime[1]);

					// TOTAL_ONSITE_MINUTE
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleWaitApproveNormalRight);
					cell.setCellValue(newTime[2]);
					TOTAL_MINUTE_DIS_APPROVE = TOTAL_MINUTE_DIS_APPROVE + Integer.parseInt(newTime[2]);

					// ONSITESTATUS
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleWaitApproveNormalLeft);
					cell.setCellValue(onsiteStatus);
				}

				curRow++;
			}
			
//			sheet.autoSizeColumn(0);
//			sheet.autoSizeColumn(1);
//			sheet.autoSizeColumn(2);
//			sheet.autoSizeColumn(3);
//			sheet.autoSizeColumn(4);
//			sheet.autoSizeColumn(5);
//			sheet.autoSizeColumn(6);

			curRow++;

		} catch (Exception e) {
			throw e;
		}

		return curRow;
	}

	private int createFooter(int curRow) throws Exception {

		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		cell.setCellStyle(styleNormalRightBoldNoBorder);
		cell.setCellValue(bundleTO.getString("to.totalTimeApproveHRM") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 1);
		cell.setCellStyle(styleNormalRightBoldNoBorder);

		cell = row.createCell((short) 2);
		cell.setCellStyle(styleNormalRightBoldNoBorder);

		cell = row.createCell((short) 3);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 3));

		cell = row.createCell((short) 4);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);
		manageTimeApprove();
		cell.setCellValue(TOTAL_DAY_APPROVE + DAY + TOTAL_HOUR_APPROVE + HOUR + TOTAL_MINUTE_APPROVE + MINUTE);

		cell = row.createCell((short) 5);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);

		cell = row.createCell((short) 6);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);

		cell = row.createCell((short) 7);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 4, 7));

		curRow++;

		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		cell.setCellStyle(styleNormalRightBoldNoBorder);
		cell.setCellValue(bundleTO.getString("to.totalTimeWaitHRM") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 1);
		cell.setCellStyle(styleNormalRightBoldNoBorder);

		cell = row.createCell((short) 2);
		cell.setCellStyle(styleNormalRightBoldNoBorder);

		cell = row.createCell((short) 3);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 3));

		cell = row.createCell((short) 4);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);
		manageTimeDisApprove();
		cell.setCellValue(TOTAL_DAY_DIS_APPROVE + DAY + TOTAL_HOUR_DIS_APPROVE + HOUR + TOTAL_MINUTE_DIS_APPROVE + MINUTE);

		cell = row.createCell((short) 5);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);

		cell = row.createCell((short) 6);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);

		cell = row.createCell((short) 7);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 4, 7));

		curRow++;

		return curRow;
	}

	private String[] computeTime(String day, String hour) throws Exception {

		String[] newTime = new String[3];
		int newDay = Integer.parseInt(day);
		Double newHour;
		Double newMinute;

		newMinute = Double.parseDouble(hour) * 60;

		newHour = Math.floor(newMinute / 60);
		newMinute = newMinute % 60;

		if (newHour >= 8.0) {
			newDay = newDay + (int) Math.floor(newHour / 8);
			newHour = newHour % 8;
		}

		newTime[0] = String.valueOf(newDay);
		newTime[1] = String.valueOf(newHour).substring(0, String.valueOf(newHour).indexOf("."));
		newTime[2] = String.valueOf(newMinute).substring(0, String.valueOf(newMinute).indexOf("."));

		return newTime;
	}
	
	private String[] computeDateTime(String dateTime) throws Exception{
		
		String[] newDateTime = new String[2];
		
		if( dateTime!= null && !(dateTime.isEmpty())){
			
			newDateTime = dateTime.split(" ");
			newDateTime[0] = TOUtil.convertBEToBC(newDateTime[0]);
			
		}else{
			return null;
		}
		
		return newDateTime;
	}

	private void manageTimeApprove() throws Exception {

		if (TOTAL_MINUTE_APPROVE > 60) {
			TOTAL_HOUR_APPROVE = TOTAL_HOUR_APPROVE + (int) (Math.floor(TOTAL_MINUTE_APPROVE / 60));
			TOTAL_MINUTE_APPROVE = TOTAL_MINUTE_APPROVE % 60;
		}

		if (TOTAL_HOUR_APPROVE > 8) {
			TOTAL_DAY_APPROVE = TOTAL_DAY_APPROVE + (int) (Math.floor(TOTAL_HOUR_APPROVE / 8));
			TOTAL_HOUR_APPROVE = TOTAL_HOUR_APPROVE % 8;
		}
	}

	private void manageTimeDisApprove() throws Exception {

		if (TOTAL_MINUTE_DIS_APPROVE > 60) {
			TOTAL_HOUR_DIS_APPROVE = TOTAL_HOUR_DIS_APPROVE + (int) (Math.floor(TOTAL_MINUTE_DIS_APPROVE / 60));
			TOTAL_MINUTE_DIS_APPROVE = TOTAL_MINUTE_DIS_APPROVE % 60;
		}

		if (TOTAL_HOUR_DIS_APPROVE > 8) {
			TOTAL_DAY_DIS_APPROVE = TOTAL_DAY_DIS_APPROVE + (int) (Math.floor(TOTAL_HOUR_DIS_APPROVE / 8));
			TOTAL_HOUR_DIS_APPROVE = TOTAL_HOUR_DIS_APPROVE % 8;
		}
	}

}
