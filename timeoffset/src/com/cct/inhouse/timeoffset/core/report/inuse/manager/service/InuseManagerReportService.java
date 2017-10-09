package com.cct.inhouse.timeoffset.core.report.inuse.manager.service;

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

import util.database.CCTConnection;

import com.cct.abstracts.AbstractService;
import com.cct.hrmdata.web.gettimeoffset.ws.WorkOnsite;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportDetail;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportSearch;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.timeoffset.util.report.ReportPOIManagerUtil;

public class InuseManagerReportService extends AbstractService {

	private InuseManagerReportDAO dao = null;

	ResourceBundle bundle = null;
	ResourceBundle bundleTO = null;

	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;

	// Create Cell Style
	XSSFCellStyle styleHeader;
	XSSFCellStyle styleCriteriaLabelRight;
	XSSFCellStyle styleCriteriaValue;
	XSSFCellStyle styleTableHeader;
	XSSFCellStyle styleTableHeader2;

	XSSFCellStyle styleNormalCenter;
	XSSFCellStyle styleNormalLeft;
	XSSFCellStyle styleNormalRight;

	XSSFCellStyle styleNormalCenterNoBorder;
	XSSFCellStyle styleNormalLeftNoBorder;
	XSSFCellStyle styleNormalRightNoBorder;

	XSSFCellStyle styleRedHighlightNormalCenterNoBorder;
	XSSFCellStyle styleRedHighlightNormalLeftNoBorder;
	XSSFCellStyle styleRedHighlightNormalRightNoBorder;

	XSSFCellStyle styleYellowHighlightNormalCenterNoBorder;
	XSSFCellStyle styleYellowHighlightNormalLeftNoBorder;
	XSSFCellStyle styleYellowHighlightNormalRightNoBorder;

	public InuseManagerReportService(Logger logger, InhouseUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new InuseManagerReportDAO(logger,SQLPath.TO_REPORT.getSqlPath(), user, locale);

		try {

			bundle = ResourceBundle.getBundle("resources.bundle.inhouse.common.Message", locale);
			bundleTO = ResourceBundle.getBundle("resources.bundle.inhouse.timeoffset.MessageTO", locale);

			// Create Cell Style
			styleHeader = ReportPOIManagerUtil.createHeaderStyle(workbook);
			styleCriteriaLabelRight = createCriteriaLabelStyle(workbook);
			styleCriteriaValue = createCriteriaValueStyle(workbook);
			styleTableHeader = createTableHeaderStyle(workbook);
			styleTableHeader2 = createTableHeaderStyle2(workbook);

			styleNormalCenter = ReportPOIManagerUtil.createStyleCN14(workbook, 1, 1, 1, 1);
			styleNormalLeft = ReportPOIManagerUtil.createStyleLN14(workbook, 1, 1, 1, 1);
			styleNormalRight = ReportPOIManagerUtil.createStyleRN14(workbook, 1, 1, 1, 1);

			styleNormalCenterNoBorder = ReportPOIManagerUtil.createStyleCN14(workbook, 0, 0, 0, 0);
			styleNormalLeftNoBorder = ReportPOIManagerUtil.createStyleLN14(workbook, 0, 0, 0, 0);
			styleNormalRightNoBorder = ReportPOIManagerUtil.createStyleRN14(workbook, 0, 0, 0, 0);

			styleRedHighlightNormalCenterNoBorder = createRedHighlightNormalCenterNoBorderStyle(workbook, 0, 0, 0, 0);
			styleRedHighlightNormalLeftNoBorder = createRedHighlightNormalLeftNoBorderStyle(workbook, 0, 0, 0, 0);
			styleRedHighlightNormalRightNoBorder = createRedHighlightNormalRightNoBorderStyle(workbook, 0, 0, 0, 0);

			styleYellowHighlightNormalCenterNoBorder = createYellowHighlightNormalCenterNoBorderStyle(workbook, 0, 0, 0, 0);
			styleYellowHighlightNormalLeftNoBorder = createYellowHighlightNormalLeftNoBorderStyle(workbook, 0, 0, 0, 0);
			styleYellowHighlightNormalRightNoBorder = createYellowHighlightNormalRightNoBorderStyle(workbook, 0, 0, 0, 0);

		} catch (Exception e) {
			LogUtil.TO_HISTORY.error("", e);
		}
	}

	private XSSFCellStyle createRedHighlightNormalCenterNoBorderStyle(XSSFWorkbook wb, int borderTop, int borderRigth, int borderBottom, int borderLeft) {

		XSSFCellStyle style;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop((short) borderTop);
			style.setBorderBottom((short) borderBottom);
			style.setBorderLeft((short) borderLeft);
			style.setBorderRight((short) borderRigth);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.CORAL.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createRedHighlightNormalLeftNoBorderStyle(XSSFWorkbook wb, int borderTop, int borderRigth, int borderBottom, int borderLeft) {

		XSSFCellStyle style;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop((short) borderTop);
			style.setBorderBottom((short) borderBottom);
			style.setBorderLeft((short) borderLeft);
			style.setBorderRight((short) borderRigth);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.CORAL.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createRedHighlightNormalRightNoBorderStyle(XSSFWorkbook wb, int borderTop, int borderRigth, int borderBottom, int borderLeft) {

		XSSFCellStyle style;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop((short) borderTop);
			style.setBorderBottom((short) borderBottom);
			style.setBorderLeft((short) borderLeft);
			style.setBorderRight((short) borderRigth);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.CORAL.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createYellowHighlightNormalCenterNoBorderStyle(XSSFWorkbook wb, int borderTop, int borderRigth, int borderBottom, int borderLeft) {

		XSSFCellStyle style;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop((short) borderTop);
			style.setBorderBottom((short) borderBottom);
			style.setBorderLeft((short) borderLeft);
			style.setBorderRight((short) borderRigth);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createYellowHighlightNormalLeftNoBorderStyle(XSSFWorkbook wb, int borderTop, int borderRigth, int borderBottom, int borderLeft) {

		XSSFCellStyle style;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop((short) borderTop);
			style.setBorderBottom((short) borderBottom);
			style.setBorderLeft((short) borderLeft);
			style.setBorderRight((short) borderRigth);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

		} catch (Exception e) {
			throw e;
		}

		return style;
	}

	private XSSFCellStyle createYellowHighlightNormalRightNoBorderStyle(XSSFWorkbook wb, int borderTop, int borderRigth, int borderBottom, int borderLeft) {

		XSSFCellStyle style;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			style.setBorderTop((short) borderTop);
			style.setBorderBottom((short) borderBottom);
			style.setBorderLeft((short) borderLeft);
			style.setBorderRight((short) borderRigth);
			style.setWrapText(true);

			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

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
			style.setWrapText(true);

		} catch (Exception e) {
			throw e;
		}
		return style;

	}

	private XSSFCellStyle createTableHeaderStyle2(XSSFWorkbook wb) throws Exception {

		XSSFCellStyle style;
		short border = 0;

		try {
			XSSFFont font = wb.createFont();
			font.setFontName(GlobalVariableTO.TH_SARABUN);
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderTop(border);
			style.setBorderBottom(border);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());

		} catch (Exception e) {
			throw e;
		}
		return style;
	}

	protected long countData(CCTConnection conn, InuseManagerReportSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria);
	}
	
	protected List<InuseManagerReportSearch> search(CCTConnection conn, InuseManagerReportSearchCriteria criteria) throws Exception {

		// Search หา list รายชื่อขึ้นมา
		List<InuseManagerReportSearch> lstResult = dao.search(conn, criteria,GlobalVariableTO.IS_EMPTY);

		return lstResult;
	}
	
	protected List<InuseManagerReportSearch> searchReport(CCTConnection conn, InuseManagerReportSearchCriteria criteria) throws Exception {

		// Search หา list รายชื่อขึ้นมา
		List<InuseManagerReportSearch> lstResult = dao.search(conn, criteria,GlobalVariableTO.REPORT);

		return lstResult;
	}

	protected XSSFWorkbook exportReport(InuseManagerReportSearchCriteria criteria, List<InuseManagerReportSearch> listResult) throws Exception {

		try {

			int curRow = 0;
			String sheetName = criteria.getDepartmentName();

			// วาดหัวรายงาน
			curRow = createHeader(curRow, criteria, sheetName);

			// วาดหัวตารางของข้อมูล
			curRow = createHeaderDetail(curRow);

			// วาดตารางข้อมูล
			curRow = createDetail(curRow, listResult);

		} catch (Exception e) {
			throw e;
		}

		return workbook;
	}

	private int createHeaderSubTable(int curRow) throws Exception {

		int colIndex = 2;

		row = sheet.createRow(curRow);
		row.setHeight((short) 400);

		// วันที่ - เวลา เริ่มต้น
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.startDateTime"));

		// วันที่ - เวลา สิ้นสุด
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.endDateTime"));

		// จำนวนวัน
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalDay"));

		// จำนวนชั่วโมง
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalHour"));

		// จำนวนนาที
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalMinute"));

		// สถานที่ปฏิบัติงาน
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.workPlace"));

		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 7, 8));

		curRow++;

		return curRow;
	}

	private int createDetailSubTable(int curRow, InuseManagerReportDetail data) throws Exception {

		int colIndex = 2;

		row = sheet.createRow(curRow);
		row.setHeight((short) 400);

		// วันที่ - เวลา เริ่มต้น
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleNormalCenterNoBorder);
		cell.setCellValue(data.getStartDateTime());

		// วันที่ - เวลา สิ้นสุด
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleNormalCenterNoBorder);
		cell.setCellValue(data.getEndDateTime());

		// จำนวนวัน
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleNormalRightNoBorder);
		cell.setCellValue(data.getTotalDay());

		// จำนวนชั่วโมง
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleNormalRightNoBorder);
		cell.setCellValue(data.getTotalHour());

		// จำนวนนาที
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleNormalRightNoBorder);
		cell.setCellValue(data.getTotalMinute());

		// สถานที่ปฏิบัติงาน
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleNormalLeftNoBorder);
		cell.setCellValue(data.getWorkPlace());

		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleNormalLeftNoBorder);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 7, 8));

		curRow++;

		return curRow;
	}

	private int createHeader(int curRow, InuseManagerReportSearchCriteria criteria, String sheetName) throws Exception {

		sheet = workbook.createSheet(sheetName);

		// Set sheet style
		sheet.setAutobreaks(true);
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setLandscape(false); // เอกสารแนวนอน
		sheet.getPrintSetup().setValidSettings(true);
		sheet.getPrintSetup().setFitHeight((short) 0); // fit กระดาษตั้ง
		sheet.getPrintSetup().setFitWidth((short) 1); // fit กระดาษแนวนอน
		sheet.getPrintSetup().setLandscape(false); // set กระดาษให้เป็นแนวนอน
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		sheet.getPrintSetup().setPageOrder(PageOrder.OVER_THEN_DOWN);

		sheet.setColumnWidth(0, 1000); //ช่องว่าง A
		sheet.setColumnWidth(1, 1500); //ลำดับ B
		sheet.setColumnWidth(2, 6300); // C
		sheet.setColumnWidth(3, 6300); // D
		sheet.setColumnWidth(4, 2500); // E
		sheet.setColumnWidth(5, 2500); // F
		sheet.setColumnWidth(6, 2500); // G
		sheet.setColumnWidth(7, 6500); // G
			
		// Set Header
		row = sheet.createRow(curRow); // first row
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		cell.setCellStyle(styleHeader);
		cell.setCellValue(bundleTO.getString("to.reportTO")); //รายงานข้อมูลการใช้เวลาชดเชย
		curRow++;

		// ฝ่าย-แผนก
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 2));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.department") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 3);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 3, 7));
		cell.setCellStyle(styleCriteriaValue);
		cell.setCellValue(criteria.getDepartmentName());
		curRow++;

		// ชื่อ-สกุล พนักงาน
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 2));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.fullNameEmp") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 3);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 3, 7));
		cell.setCellStyle(styleCriteriaValue);

		if (criteria.getEmployeeName() != null && !criteria.getEmployeeName().isEmpty()) {
			cell.setCellValue(criteria.getEmployeeName().substring(criteria.getEmployeeName().indexOf(GlobalVariableTO.DAT) + 1, criteria.getEmployeeName().length()));
		} else {
			cell.setCellValue(GlobalVariableTO.DAT_SPACE);
		}

		curRow++;

		// ตั้งแต่วันที่
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 2));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.dateFrom") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 3);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 3, 3));
		cell.setCellStyle(styleCriteriaValue);

		if (criteria.getStartDate() != null && !criteria.getStartDate().isEmpty()) {
			cell.setCellValue(criteria.getStartDate().substring(0, 6) + TOUtil.convertDateToReport(criteria.getStartDate()));
		} else {
			cell.setCellValue(GlobalVariableTO.DAT_SPACE);
		}

		// ถึงวันที่
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
		curRow++;

		return curRow;
	}

	private int createHeaderDetail(int curRow) throws Exception {

		int colIndex = 1;

		// Set Header 1
		row = sheet.createRow(curRow);

		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader2);
		cell.setCellValue(bundleTO.getString("to.useTimeOffsetDetail")); //รายละเอียดข้อมูลการใช้เวลาชดเชย

		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader2);

		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader2);

		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader2);

		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader2);

		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader2);

		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader2);

//		cell = row.createCell((short) colIndex++);
//		cell.setCellStyle(styleTableHeader2);

		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 1, 7));
		curRow++;

		// Set Header 2
		// ลำดับ
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 1);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundle.getString("no"));

		// ชื่อ - สกุล พนักงาน
		cell = row.createCell((short) 2);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.fullNameEmp"));

//		cell = row.createCell((short) 3);
//		cell.setCellStyle(styleTableHeader);

//		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 3));

		// เวลาชดเชยคงเหลือ
		cell = row.createCell((short) 3);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.timeoffset"));

		// เวลาชดเชยที่รออนุมัติจาก HRM
		cell = row.createCell((short) 4);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.timePendingHRM"));
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 4, 5));

		// วัน - เวลา Syn ข้อมูล HRM ล่าสุด
		cell = row.createCell((short) 6);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.dateTimeSyndataLasted"));
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 6, 7));

		row.setHeight((short) 700);
		curRow++;

		return curRow;
	}

	private int createDetail(int curRow, List<InuseManagerReportSearch> listResult) throws Exception {

		int colIndex;

		try {

			for (int i = 0; i < listResult.size(); i++) {

				colIndex = 1;

				InuseManagerReportSearch data = listResult.get(i);

				row = sheet.createRow(curRow);

				// NORMAL
				if (data.getHighlightRow() == null) {
					// ลำดับ
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(i + 1);

					// ชื่อ - สกุล พนักงาน
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalLeftNoBorder);
					cell.setCellValue(data.getFullName());

//					cell = row.createCell((short) colIndex++);
//					cell.setCellStyle(styleNormalLeftNoBorder);
//					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 3));

					// เวลาชดเชยคงเหลือ
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(data.getTimeOffset());

					// เวลาชดเชยที่รออนุมัติ จาก HRM
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(data.getTimePendingHRM());

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalRightNoBorder);
					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 4, 5));

					// วันที่ - เวลา Syn ข้อมูล HRM ล่าสุด
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(data.getDataTimeSyndataLasted());

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleNormalCenterNoBorder);
					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 6, 7));

				}

				// RED HIGHLIGHT
				else if (data.getHighlightRow().equals(GlobalVariableTO.HIGHLIGHT_RED)) {

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleRedHighlightNormalCenterNoBorder);
					cell.setCellValue(i + 1);

					// ชื่อ - สกุล พนักงาน
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleRedHighlightNormalLeftNoBorder);
					cell.setCellValue(data.getFullName());

//					cell = row.createCell((short) colIndex++);
//					cell.setCellStyle(styleRedHighlightNormalLeftNoBorder);
//					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 3));

					// เวลาชดเชยคงเหลือ
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleRedHighlightNormalRightNoBorder);
					cell.setCellValue(data.getTimeOffset());

					// เวลาชดเชยที่รออนุมัติ จาก HRM
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleRedHighlightNormalRightNoBorder);
					cell.setCellValue(data.getTimePendingHRM());

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleRedHighlightNormalRightNoBorder);
					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 4, 5));

					// วันที่ - เวลา Syn ข้อมูล HRM ล่าสุด
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleRedHighlightNormalCenterNoBorder);
					cell.setCellValue(data.getDataTimeSyndataLasted());

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleRedHighlightNormalCenterNoBorder);
					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 6, 7));

				}

				// YELLOW HIGHLIGHT
				else if (data.getHighlightRow().equals(GlobalVariableTO.HIGHLIGHT_YELLOW)) {

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleYellowHighlightNormalCenterNoBorder);
					cell.setCellValue(i + 1);

					// ชื่อ - สกุล พนักงาน
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleYellowHighlightNormalLeftNoBorder);
					cell.setCellValue(data.getFullName());
			
//					cell = row.createCell((short) colIndex++);
//					cell.setCellStyle(styleYellowHighlightNormalLeftNoBorder);
//					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 3));

					// เวลาชดเชยคงเหลือ
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleYellowHighlightNormalRightNoBorder);
					cell.setCellValue(data.getTimeOffset());

					// เวลาชดเชยที่รออนุมัติ จาก HRM
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleYellowHighlightNormalRightNoBorder);
					cell.setCellValue(data.getTimePendingHRM());

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleYellowHighlightNormalRightNoBorder);
					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 4, 5));

					// วันที่ - เวลา Syn ข้อมูล HRM ล่าสุด
					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleYellowHighlightNormalCenterNoBorder);
					cell.setCellValue(data.getDataTimeSyndataLasted());

					cell = row.createCell((short) colIndex++);
					cell.setCellStyle(styleYellowHighlightNormalCenterNoBorder);
					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 6, 7));

				}

				curRow++;

				// Check ว่ามี list ของการรออนุมัติ หรือไม่
				if (data.getListDetail() != null && data.getListDetail().size() > 0) {

					colIndex = 2;

					// วาดหัวตารางของข้อมูลย่อย
					curRow = createHeaderSubTable(curRow);

					// Loop วาด detail ของ sub table
					for (int j = 0; j < data.getListDetail().size(); j++) {
						curRow = createDetailSubTable(curRow, data.getListDetail().get(j));
					}
				}

			}
			
//			sheet.autoSizeColumn(2);

		} catch (Exception e) {
			throw e;
		}

		return curRow;
	}

	

	protected String searchProcessReqDateTimeUser(CCTConnection conn, String id) throws Exception {
		return dao.searchProcessReqDateTime(conn, id);
	}
	
	protected String searchProcessReqDateTimeAll(CCTConnection conn) throws Exception {
		return dao.searchProcessReqDateTimeAll(conn);
	}

	protected int insertLogCallWS(CCTConnection conn, String id) throws Exception {
		return dao.insertLogCallWS(conn, id, getUser(), getLocale());
	}

	protected void updateLogCallWS(CCTConnection conn,String processStatus, String msgDesc, int sizeArrWorkOnSite, int lastWsProcessId) throws Exception {
		dao.updateLogCallWS(conn, processStatus, msgDesc, sizeArrWorkOnSite, lastWsProcessId, getUser(), getLocale());
	}

	protected int insertWorkOnSite(CCTConnection conn,WorkOnsite workOnsite) throws Exception {
		return dao.insertWorkOnSite(conn, workOnsite,getUser(), getLocale());
	}

	protected int updateWorkOnSite(CCTConnection conn,WorkOnsite workOnsite) throws Exception {
		return dao.updateWorkOnSite(conn, workOnsite, getUser(), getLocale());
	}

}