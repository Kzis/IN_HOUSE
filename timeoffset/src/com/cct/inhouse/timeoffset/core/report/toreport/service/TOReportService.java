package com.cct.inhouse.timeoffset.core.report.toreport.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
import util.string.StringUtil;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReport;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReportSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.timeoffset.util.report.ReportPOIManagerUtil;

public class TOReportService extends AbstractService {

	private TOReportDAO dao;

	ResourceBundle bundleTO = null;
	ResourceBundle bundle = null;

	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;

	// Create Cell Style
	XSSFCellStyle styleHeader;
	XSSFCellStyle styleCriteriaLabelRight;
	XSSFCellStyle styleCriteriaValue;

	XSSFCellStyle styleTableHeader;
	XSSFCellStyle styleTableHeaderDepartment;

	XSSFCellStyle styleNormalCenterNoBorder;
	XSSFCellStyle styleNormalLeftNoBorder;
	XSSFCellStyle styleNormalRightNoBorder;

	public TOReportService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new TOReportDAO(logger,SQLPath.TO_REPORT.getSqlPath(), user, locale);

		try {
			bundle = ResourceBundle.getBundle("resources.bundle.inhouse.common.Message", locale);
			bundleTO = ResourceBundle.getBundle("resources.bundle.inhouse.timeoffset.MessageTO", locale);

			// Create Cell Style
			styleHeader = ReportPOIManagerUtil.createHeaderStyle(workbook);
			styleCriteriaLabelRight = createCriteriaLabelStyle(workbook);
			styleCriteriaValue = createCriteriaValueStyle(workbook);

			styleTableHeader = createTableHeaderStyle(workbook);
			styleTableHeaderDepartment = createTableHeaderDepartment(workbook);

			styleNormalCenterNoBorder = ReportPOIManagerUtil.createStyleCN14(workbook, 0, 0, 0, 0);
			styleNormalLeftNoBorder = ReportPOIManagerUtil.createStyleLN14(workbook, 0, 0, 0, 0);
			styleNormalRightNoBorder = ReportPOIManagerUtil.createStyleRN14(workbook, 0, 0, 0, 0);

		} catch (Exception e) {
			LogUtil.TO_HISTORY.error("", e);
		}

	}

	private XSSFCellStyle createTableHeaderDepartment(XSSFWorkbook wb) throws Exception {

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
			style.setFillForegroundColor(IndexedColors.TAN.getIndex());

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
			font.setColor(HSSFColor.GREY_25_PERCENT.index);

			style = wb.createCellStyle();
			style.setFont(font);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderTop(border);
			style.setBorderBottom(border);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			style.setWrapText(true);

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

	protected long countData(CCTConnection conn,TOReportSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria);
	}
	
	protected List<List<TOReport>> searchReport(CCTConnection conn,TOReportSearchCriteria criteria, Statement stmt) throws Exception {
		
		List<TOReport> listResult = new ArrayList<TOReport>();
		List<List<TOReport>> listNewResult = new ArrayList<List<TOReport>>();
		
		listResult = dao.searchReport(conn, stmt, criteria,getUser(),getLocale());
		
		listNewResult = manageListResult(listResult);
		
		return listNewResult;
				
				
	}

	private List<List<TOReport>> manageListResult(List<TOReport> listResult) throws Exception {
		
		List<List<TOReport>> listNewResult = new ArrayList<List<TOReport>>();
		List<TOReport> listTemp = new ArrayList<TOReport>();
		
		for(int i = 0; i<listResult.size(); i++){
			
			//ถ้ามีตัวเดียวกลับได้เลย
			if(listResult.size() == 1){
				listNewResult.add(listResult);
			}
						
			//ครั้งแรกใส่ได้เลย
			if( listTemp.size() == 0){
				listTemp.add(listResult.get(i));
				continue;
			}
			
			//ถ้าชื่อ Project ตรงกัน
			if(listResult.get(i).getProjectABBR().equals(listTemp.get(listTemp.size()-1).getProjectABBR())){
				
				listTemp.add(listResult.get(i));
				
				if(i==listResult.size()-1){
					listNewResult.add(listTemp);
				}
				
			}else{
				listNewResult.add(listTemp);
				listTemp = new ArrayList<TOReport>();
				listTemp.add(listResult.get(i));
			}

			
		}
		
		return listNewResult;
	
	}
	
	public XSSFWorkbook exportReport(TOReportSearchCriteria criteria,List<List<TOReport>> listResult) throws Exception {
		
		try {
			
			int curRow = 0;
			String sheetName;
			
			//Loop ตาม List ที่แยกเป็นประเภทไว้หลังจาก Manage List
			for(int i = 0; i < listResult.size(); i++){
				
				sheetName = listResult.get(i).get(0).getProjectABBR();

				curRow = 0;
				
				// วาดหัวรายงาน
				curRow = createHeader(curRow, criteria, sheetName);

				// วาดหัวตารางของข้อมูล
				curRow = createHeaderDetail(curRow);
				
				// วาดตารางข้อมูล
				curRow = createDetail2(curRow, listResult.get(i));
				
			}
		
		} catch (Exception e) {
			throw e;
		}

		return workbook;
	}
	
	private int createDetail2(int curRow, List<TOReport> list) throws Exception {

		String departmentBefore = "";
		String employeeNameBefore = "";
		String[] newTime = new String[3];

		try {

			for(int i=0; i<list.size(); i++){

				// Check เพื่อวาด header department ถ้าชื่อปัจจุบันไม่ตรงกับชื่อก่อนหน้าให้วาด tab header ใหม่
				if (!(departmentBefore.equals(list.get(i).getDepartmentName()))) {

					departmentBefore = list.get(i).getDepartmentName();

					row = sheet.createRow(curRow);
					row.setHeight((short) 500);
					cell = row.createCell((short) 0);
					cell.setCellStyle(styleTableHeaderDepartment);
					cell.setCellValue(departmentBefore);

					cell = row.createCell((short) 1);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 2);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 3);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 4);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 5);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 6);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 7);
					cell.setCellStyle(styleTableHeaderDepartment);

					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 7));

					curRow++;

					// วาดหัวตารางเสร็จแล้วต้องวาด row แรก
					row = sheet.createRow(curRow);
					row.setHeight((short) 500);

					if (!(employeeNameBefore.equals(list.get(i).getUserFullName()))) {

						employeeNameBefore = list.get(i).getUserFullName();

						cell = row.createCell((short) 1);
						cell.setCellStyle(styleNormalLeftNoBorder);
						cell.setCellValue(list.get(i).getUserFullName());

					}

					cell = row.createCell((short) 2);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(list.get(i).getStartDateTime()));

					cell = row.createCell((short) 3);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(list.get(i).getEndDateTime()));

					// Process compute day hour minute from hrm
					newTime = computeTime(list.get(i).getMinute());

					cell = row.createCell((short) 4);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[0]);

					cell = row.createCell((short) 5);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[1]);

					cell = row.createCell((short) 6);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[2]);

					cell = row.createCell((short) 7);
					cell.setCellStyle(styleNormalLeftNoBorder);
					cell.setCellValue(list.get(i).getApproveStatusTitle());

				}
				// ถ้าชื่อตรงกันไม่ต้องวาด tab header ใหม่
				else {

					row = sheet.createRow(curRow);
					row.setHeight((short) 500);

					if (!(employeeNameBefore.equals(list.get(i).getUserFullName()))) {

						employeeNameBefore = list.get(i).getUserFullName();

						cell = row.createCell((short) 1);
						cell.setCellStyle(styleNormalLeftNoBorder);
						cell.setCellValue(StringUtil.nullToString(list.get(i).getUserFullName()));

					}

					cell = row.createCell((short) 2);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(list.get(i).getStartDateTime())));

					cell = row.createCell((short) 3);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(list.get(i).getEndDateTime())));
										
					// Process compute day hour minute from hrm
					newTime = computeTime(list.get(i).getMinute());

					cell = row.createCell((short) 4);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[0]);

					cell = row.createCell((short) 5);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[1]);

					cell = row.createCell((short) 6);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[2]);

					cell = row.createCell((short) 7);
					cell.setCellStyle(styleNormalLeftNoBorder);
					cell.setCellValue(list.get(i).getApproveStatusTitle());

				}

				curRow++;

			}
			
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			sheet.autoSizeColumn(7);
			sheet.autoSizeColumn(8);

			curRow++;

		} catch (Exception e) {
			throw e;
		}

		return curRow;
	}

	private int createDetail(int curRow, ResultSet rst) throws Exception {

		String departmentBefore = "";
		String employeeNameBefore = "";
		String[] newTime = new String[3];

		try {

			while (rst.next()) {

				// Check เพื่อวาด header department
				// ถ้าชื่อปัจจุบันไม่ตรงกับชื่อก่อนหน้าให้วาด tab header ใหม่
				if (!(departmentBefore.equals(StringUtil.nullToString(rst.getString("DEPARTMENT"))))) {

					departmentBefore = StringUtil.nullToString(rst.getString("DEPARTMENT"));

					row = sheet.createRow(curRow);
					row.setHeight((short) 500);
					cell = row.createCell((short) 0);
					cell.setCellStyle(styleTableHeaderDepartment);
					cell.setCellValue(departmentBefore);

					cell = row.createCell((short) 1);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 2);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 3);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 4);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 5);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 6);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 7);
					cell.setCellStyle(styleTableHeaderDepartment);

					cell = row.createCell((short) 8);
					cell.setCellStyle(styleTableHeaderDepartment);

					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 8));

					curRow++;

					// วาดหัวตารางเสร็จแล้วต้องวาด row แรก
					row = sheet.createRow(curRow);
					row.setHeight((short) 500);

					if (!(employeeNameBefore.equals(StringUtil.nullToString(rst.getString("USER_FULLNAME"))))) {

						employeeNameBefore = StringUtil.nullToString(rst.getString("USER_FULLNAME"));

						cell = row.createCell((short) 1);
						cell.setCellStyle(styleNormalLeftNoBorder);
						cell.setCellValue(StringUtil.nullToString(rst.getString("USER_FULLNAME")));

					}

					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 1, 2));

					cell = row.createCell((short) 3);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))));

					cell = row.createCell((short) 4);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))));

					// Process compute day hour minute from hrm
					newTime = computeTime(StringUtil.nullToString(rst.getString("mintue")));

					cell = row.createCell((short) 5);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[0]);

					cell = row.createCell((short) 6);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[1]);

					cell = row.createCell((short) 7);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[2]);

					cell = row.createCell((short) 8);
					cell.setCellStyle(styleNormalLeftNoBorder);
					cell.setCellValue(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));

				}
				// ถ้าชื่อตรงกันไม่ต้องวาด tab header ใหม่
				else {

					row = sheet.createRow(curRow);
					row.setHeight((short) 500);

					if (!(employeeNameBefore.equals(StringUtil.nullToString(rst.getString("USER_FULLNAME"))))) {

						employeeNameBefore = StringUtil.nullToString(rst.getString("USER_FULLNAME"));

						cell = row.createCell((short) 1);
						cell.setCellStyle(styleNormalLeftNoBorder);
						cell.setCellValue(StringUtil.nullToString(rst.getString("USER_FULLNAME")));

					}

					sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 1, 2));

					cell = row.createCell((short) 3);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))));

					cell = row.createCell((short) 4);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))));

					// Process compute day hour minute from hrm
					newTime = computeTime(StringUtil.nullToString(rst.getString("mintue")));

					cell = row.createCell((short) 5);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[0]);

					cell = row.createCell((short) 6);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[1]);

					cell = row.createCell((short) 7);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[2]);

					cell = row.createCell((short) 8);
					cell.setCellStyle(styleNormalLeftNoBorder);
					cell.setCellValue(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE")));

				}

				curRow++;

			}

			curRow++;

		} catch (Exception e) {
			throw e;
		}

		return curRow;
	}

	private int createHeaderDetail(int curRow) throws Exception {

		int curCell = 0;
		row = sheet.createRow(curRow);
		cell = row.createCell((short) curCell++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.department"));

		cell = row.createCell((short) curCell++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.fullNameNickName"));

		cell = row.createCell((short) curCell++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.startDateTime"));

		cell = row.createCell((short) curCell++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.endDateTime"));

		cell = row.createCell((short) curCell++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalDay"));

		cell = row.createCell((short) curCell++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalHour"));

		cell = row.createCell((short) curCell++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalMinute"));

		cell = row.createCell((short) curCell++);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("approveStatus"));

		curRow++;

		return curRow;
	}

	private int createHeader(int curRow, TOReportSearchCriteria criteria, String sheetName) throws Exception {

		sheet = workbook.createSheet(sheetName);

		// Set sheet style
		sheet.setAutobreaks(true);
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setLandscape(false); // เอกสารแนวนอน
		sheet.getPrintSetup().setValidSettings(true);
		sheet.getPrintSetup().setFitHeight((short) 0); // fit กระดาษตั้ง
		sheet.getPrintSetup().setFitWidth((short) 1); // fit กระดาษแนวนอน
		sheet.getPrintSetup().setLandscape(false);
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		sheet.getPrintSetup().setPageOrder(PageOrder.OVER_THEN_DOWN);

		// Set column width
		sheet.setColumnWidth(0, 2500);
		sheet.setColumnWidth(1, 3500);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 3000);
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);

		// Set Header
		row = sheet.createRow(curRow); // first row
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		cell.setCellStyle(styleHeader);
		cell.setCellValue(bundleTO.getString("to.reportTOInUse"));
		curRow++;

		// Set โครงการ
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 1));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.project") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 2);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 7));
		cell.setCellStyle(styleCriteriaValue);
		cell.setCellValue(criteria.getProjectName());
		curRow++;

		// Set เงื่อนไขเวลาชดเชย
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 1));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.conditionTime") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 2);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 7));
		cell.setCellStyle(styleCriteriaValue);
		cell.setCellValue(criteria.getProjConName());
		curRow++;

		// ตั้งแต่วันที่
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

		// Set เงื่อนไขเวลาชดเชย
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 1));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.approveStatusTO") + GlobalVariableTO.SEPERLATE);

		cell = row.createCell((short) 2);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 2, 7));
		cell.setCellStyle(styleCriteriaValue);

		if (criteria.getApproveStatus() != null && !criteria.getApproveStatus().isEmpty()) {

			if (criteria.getApproveStatus().equals(GlobalVariableTO.WAIT_APPROVE_STATUS)) {
				cell.setCellValue(bundleTO.getString("to.waitApprove"));
			} else if (criteria.getApproveStatus().equals(GlobalVariableTO.DIS_APPROVE_STATUS)) {
				cell.setCellValue(bundleTO.getString("to.disApprove"));
			} else if (criteria.getApproveStatus().equals(GlobalVariableTO.APPROVE_STATUS)) {
				cell.setCellValue(bundleTO.getString("to.approve"));
			}

		} else {
			cell.setCellValue(bundle.getString("all"));
		}

		curRow++;

		row = sheet.createRow(curRow);
		row.setHeight((short) 500);

		curRow++;

		return curRow;
	}

	/**
	 * คำนวณเฉพาะหน้านี้ 
	 * @param minute
	 * @return
	 * @throws Exception
	 */
	private String[] computeTime(String minute) throws Exception {
		
		String[] newTime = new String[3];
		Double newDay = 0.0;
		Double newHour = 0.0;
		Double newMinute = 0.0;

		newMinute = Double.parseDouble(minute);

		if (newMinute >= 60) {
			newHour = Math.floor(newMinute / 60);
			newMinute = newMinute % 60;
		}

		if (newHour >= 8.0) {
			newDay = newDay + (int) Math.floor(newHour / 8);
			newHour = newHour % 8;
		}

		newTime[0] = String.valueOf(newDay).split("\\.")[0];
		newTime[1] = String.valueOf(newHour).split("\\.")[0];
		newTime[2] = String.valueOf(newMinute).split("\\.")[0];

		return newTime;
	}


}
