package com.cct.inhouse.timeoffset.core.report.inuse.general.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.cct.inhouse.timeoffset.core.report.inuse.general.domain.InuseReportSearchCriteria;
import com.cct.inhouse.timeoffset.util.TOUtil;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.timeoffset.util.report.ReportPOIManagerUtil;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class InuseReportService extends AbstractService{
	
	private InuseReportDAO dao;
		
	private static String DAY = "";
	private static String HOUR = "";
	private static String MINUTE = "";
	
	private static int TOTAL_DAY_APPROVE = 0;
	private static int TOTAL_HOUR_APPROVE = 0;
	private static int TOTAL_MINUTE_APPROVE = 0;
	
	private static int TOTAL_DAY_DIS_APPROVE = 0;
	private static int TOTAL_HOUR_DIS_APPROVE = 0;
	private static int TOTAL_MINUTE_DIS_APPROVE = 0;
	
	private static int TOTAL_DAY_WAIT_APPROVE = 0;
	private static int TOTAL_HOUR_WAIT_APPROVE = 0;
	private static int TOTAL_MINUTE_WAIT_APPROVE = 0;
	
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
	
	XSSFCellStyle styleNormalRightBoldNoBorder;	
	XSSFCellStyle styleNormalLeftBoldNoBorder;
	
	//PDF
	private static String FONT16BOLD = "FONT16BOLD";
	private static String FONT16NORMAL = "FONT16NORMAL";
	private static String FONT16NORMALBLUE = "FONT16NORMALBLUE";
	private static String FONT14BOLD = "FONT14BOLD";
	private static String FONT14NORMAL = "FONT14NORMAL";
	private static String FONT12BOLD = "FONT12BOLD";
	private static String FONT12NORMAL = "FONT12NORMAL";
	
	Font FONT_16BOLD;
	Font FONT_16NORMAL;
	Font FONT_16NORMALBLUE;
	Font FONT_14BOLD;
	Font FONT_14NORMAL;
	Font FONT_12BOLD;
	Font FONT_12NORMAL;
	
	
	
	private static final int OFFSET = 36;
	
	public InuseReportService(Logger logger, CommonUser user,Locale locale) {
		super(logger, user, locale);
		this.dao = new InuseReportDAO(logger,SQLPath.TO_REPORT.getSqlPath(), user, locale);

		try {
			
			bundle = ResourceBundle.getBundle("resources.bundle.inhouse.common.Message", locale);
			bundleTO = ResourceBundle.getBundle("resources.bundle.inhouse.timeoffset.MessageTO", locale);
			
			DAY = " " + bundleTO.getString("to.day") + " ";
			HOUR = " " + bundleTO.getString("to.hour") + " ";
			MINUTE = " " + bundleTO.getString("to.min") + " ";
			
			// Create Cell Style
			styleHeader = ReportPOIManagerUtil.createHeaderStyle(workbook);
			styleCriteriaLabelRight = createCriteriaLabelStyle(workbook);
			styleCriteriaValue = createCriteriaValueStyle(workbook);
			styleTableHeader = createTableHeaderStyle(workbook);
			styleTableHeader2 = createTableHeaderStyle2(workbook);
			
			styleNormalCenter = ReportPOIManagerUtil.createStyleCN14(workbook, 1, 1, 1, 1);
			styleNormalLeft = ReportPOIManagerUtil.createStyleLN14(workbook, 1, 1, 1, 1);
			styleNormalRight = ReportPOIManagerUtil.createStyleRN14(workbook,1,1,1,1);
			
			styleNormalCenterNoBorder = ReportPOIManagerUtil.createStyleCN14(workbook, 0, 0, 0, 0);		
			styleNormalLeftNoBorder = ReportPOIManagerUtil.createStyleLN14(workbook, 0, 0, 0, 0);
			styleNormalRightNoBorder = ReportPOIManagerUtil.createStyleRN14(workbook, 0, 0, 0, 0);
			
			styleRedHighlightNormalCenterNoBorder = createRedHighlightNormalCenterNoBorderStyle(workbook,0,0,0,0);
			styleRedHighlightNormalLeftNoBorder = createRedHighlightNormalLeftNoBorderStyle(workbook,0,0,0,0);
			styleRedHighlightNormalRightNoBorder = createRedHighlightNormalRightNoBorderStyle(workbook,0,0,0,0);
			
			styleYellowHighlightNormalCenterNoBorder = createYellowHighlightNormalCenterNoBorderStyle(workbook,0,0,0,0);
			styleYellowHighlightNormalLeftNoBorder = createYellowHighlightNormalLeftNoBorderStyle(workbook,0,0,0,0);
			styleYellowHighlightNormalRightNoBorder = createYellowHighlightNormalRightNoBorderStyle(workbook,0,0,0,0);
			
			styleNormalRightBoldNoBorder = createCriteriaLabelStyle(workbook);
			styleNormalLeftBoldNoBorder = createCriteriaValueStyle(workbook);
			
			
			// PDF
			BaseFont bfComic = BaseFont.createFont("THSARABUN.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			FONT_16BOLD = new Font(bfComic, 16, Font.BOLD); 	
			FONT_16NORMAL = new Font(bfComic, 16, Font.NORMAL);
			
			FONT_16NORMALBLUE = new Font(bfComic, 16, Font.NORMAL);
			FONT_16NORMALBLUE.setColor(Color.BLUE);

			FONT_14BOLD = new Font(bfComic, 14, Font.BOLD);
			FONT_14NORMAL = new Font(bfComic, 14, Font.NORMAL);
			FONT_12BOLD = new Font(bfComic, 12, Font.BOLD);
			FONT_12NORMAL = new Font(bfComic, 12, Font.NORMAL);
			
		} catch(Exception e){
			LogUtil.TO_HISTORY.error("",e);
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

			style.setBorderTop((short)borderTop);
			style.setBorderBottom((short)borderBottom);
			style.setBorderLeft((short)borderLeft);
			style.setBorderRight((short)borderRigth);
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

			style.setBorderTop((short)borderTop);
			style.setBorderBottom((short)borderBottom);
			style.setBorderLeft((short)borderLeft);
			style.setBorderRight((short)borderRigth);
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

			style.setBorderTop((short)borderTop);
			style.setBorderBottom((short)borderBottom);
			style.setBorderLeft((short)borderLeft);
			style.setBorderRight((short)borderRigth);
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

			style.setBorderTop((short)borderTop);
			style.setBorderBottom((short)borderBottom);
			style.setBorderLeft((short)borderLeft);
			style.setBorderRight((short)borderRigth);
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

			style.setBorderTop((short)borderTop);
			style.setBorderBottom((short)borderBottom);
			style.setBorderLeft((short)borderLeft);
			style.setBorderRight((short)borderRigth);
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

			style.setBorderTop((short)borderTop);
			style.setBorderBottom((short)borderBottom);
			style.setBorderLeft((short)borderLeft);
			style.setBorderRight((short)borderRigth);
			style.setWrapText(true);
			
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			
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
	
	private XSSFCellStyle createTableHeaderStyle2(XSSFWorkbook wb) throws Exception{
		
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

	private XSSFCellStyle createCriteriaLabelStyle(XSSFWorkbook wb) throws Exception{
		
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
	
	protected long countData(CCTConnection conn, InuseReportSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria);
	}

	protected ResultSet searchReport(CCTConnection conn, InuseReportSearchCriteria criteria,Statement stmt) throws Exception {
		return dao.searchReport(conn,stmt,criteria,getUser(),getLocale());
	}

	protected XSSFWorkbook exportReportExcel(InuseReportSearchCriteria criteria,ResultSet rst) throws Exception {
		
		try {
			
			TOTAL_DAY_APPROVE = 0;
			TOTAL_HOUR_APPROVE = 0;
			TOTAL_MINUTE_APPROVE = 0;
			
			TOTAL_DAY_DIS_APPROVE = 0;
			TOTAL_HOUR_DIS_APPROVE = 0;
			TOTAL_MINUTE_DIS_APPROVE = 0;
			
			TOTAL_DAY_WAIT_APPROVE = 0;
			TOTAL_HOUR_WAIT_APPROVE = 0;
			TOTAL_MINUTE_WAIT_APPROVE = 0;
			
			int curRow = 0;
			String sheetName = bundleTO.getString("to.reportTO");
			
			//วาดหัวรายงาน
			curRow = createHeader(curRow,criteria,sheetName);
			
			//วาดหัวตารางของข้อมูล
			curRow = createHeaderDetail(curRow);
			
			//วาดตารางข้อมูล
			curRow = createDetail(curRow,rst);
			
			//วาดท้ายรายงาน
			curRow = createFooter(curRow);
			
		}catch (Exception e) {
			throw e;
		}
		
		return workbook;
	}
	
	
	
	private int createFooter(int curRow)throws Exception {
		
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		cell.setCellStyle(styleNormalRightBoldNoBorder);
		cell.setCellValue(bundleTO.getString("to.totalTOApprove") + GlobalVariableTO.SEPERLATE);	
		
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
		cell.setCellValue(bundleTO.getString("to.totalTODisApprove") + GlobalVariableTO.SEPERLATE);	
		
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
		
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		cell.setCellStyle(styleNormalRightBoldNoBorder);
		cell.setCellValue(bundleTO.getString("to.totalTOWaitApprove") + GlobalVariableTO.SEPERLATE);	
		
		cell = row.createCell((short) 1);
		cell.setCellStyle(styleNormalRightBoldNoBorder);
		
		cell = row.createCell((short) 2);
		cell.setCellStyle(styleNormalRightBoldNoBorder);	
		
		cell = row.createCell((short) 3);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 3));
		
		cell = row.createCell((short) 4);
		cell.setCellStyle(styleNormalLeftBoldNoBorder);
		manageTimeWaitApprove();
		cell.setCellValue(TOTAL_DAY_WAIT_APPROVE + DAY + TOTAL_HOUR_WAIT_APPROVE + HOUR + TOTAL_MINUTE_WAIT_APPROVE + MINUTE);	
		
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

	private int createDetail(int curRow, ResultSet rst) throws Exception {

		String[] newTime = new String[3];
		String approveStatusHRM;
		
		try {
			
			while(rst.next()){

				row = sheet.createRow(curRow);
				
				approveStatusHRM = StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE"));
				
				//ถ้า สถานะอนุมัติจาก HRM เป็น ไม่อนุมัติ จะใช้ style สีแดง
				if(approveStatusHRM.equals(bundleTO.getString("to.disApprove"))){
					
					//วันที่ - เวลา เริ่มต้น : START_DATETIME
					cell = row.createCell((short) 1);
					cell.setCellStyle(styleRedHighlightNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("START_DATETIME")) + GlobalVariableTO.START_TIME_MS));
					
					//วันที่ - เวลา สิ้นสุด : END_DATETIME
					cell = row.createCell((short) 2);
					cell.setCellStyle(styleRedHighlightNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("END_DATETIME")) + GlobalVariableTO.START_TIME_MS));
					
					//Process compute day hour minute from hrm
					newTime = TOUtil.computeTimeHRM(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")),StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));
					
					//จำนวนวัน : TOTAL_ONSITE_DAY
					cell = row.createCell((short) 3);
					cell.setCellStyle(styleRedHighlightNormalRightNoBorder);
					cell.setCellValue(newTime[0]);
					TOTAL_DAY_DIS_APPROVE = TOTAL_DAY_DIS_APPROVE + Integer.parseInt(newTime[0]);
					
					//จำนวนชั่วโมง : TOTAL_ONSITE_HOUR
					cell = row.createCell((short) 4);
					cell.setCellStyle(styleRedHighlightNormalRightNoBorder);
					cell.setCellValue(newTime[1]);
					TOTAL_HOUR_DIS_APPROVE = TOTAL_HOUR_DIS_APPROVE + Integer.parseInt(newTime[1]);
					
					//จำนวนนาที : TOTAL_ONSITE_MINUTE
					cell = row.createCell((short) 5);
					cell.setCellStyle(styleRedHighlightNormalRightNoBorder);
					cell.setCellValue(newTime[2]);
					TOTAL_MINUTE_DIS_APPROVE = TOTAL_MINUTE_DIS_APPROVE + Integer.parseInt(newTime[2]);
					
					//สถานที่ปฏิบัติงาน : SITESERVICE_REMARK
					cell = row.createCell((short) 6);
					cell.setCellStyle(styleRedHighlightNormalLeftNoBorder);
					cell.setCellValue(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")));
					
					//สถานะอนุมัติจาก HRM : GLOBAL_DATA_VALUE
					cell = row.createCell((short) 7);
					cell.setCellStyle(styleRedHighlightNormalLeftNoBorder);
					cell.setCellValue(approveStatusHRM);
				}
				
				//ถ้า สถานะอนุมัติจาก HRM เป็น รออนุมัติ จะใช้ style สีเหลือง
				else if(approveStatusHRM.equals(bundleTO.getString("to.waitApprove"))){
					
					//วันที่ - เวลา เริ่มต้น : START_DATETIME
					cell = row.createCell((short) 1);
					cell.setCellStyle(styleYellowHighlightNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("START_DATETIME")) + GlobalVariableTO.START_TIME_MS));
					
					//วันที่ - เวลา สิ้นสุด : END_DATETIME
					cell = row.createCell((short) 2);
					cell.setCellStyle(styleYellowHighlightNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("END_DATETIME")) + GlobalVariableTO.START_TIME_MS));
					
					//Process compute day hour minute from hrm
					newTime = TOUtil.computeTimeHRM(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")),StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));
					
					//จำนวนวัน : TOTAL_ONSITE_DAY
					cell = row.createCell((short) 3);
					cell.setCellStyle(styleYellowHighlightNormalRightNoBorder);
					cell.setCellValue(newTime[0]);
					TOTAL_DAY_WAIT_APPROVE = TOTAL_DAY_WAIT_APPROVE + Integer.parseInt(newTime[0]);
					
					//จำนวนชั่วโมง : TOTAL_ONSITE_HOUR
					cell = row.createCell((short) 4);
					cell.setCellStyle(styleYellowHighlightNormalRightNoBorder);
					cell.setCellValue(newTime[1]);
					TOTAL_HOUR_WAIT_APPROVE = TOTAL_HOUR_WAIT_APPROVE + Integer.parseInt(newTime[1]);
					
					//จำนวนนาที : TOTAL_ONSITE_MINUTE
					cell = row.createCell((short) 5);
					cell.setCellStyle(styleYellowHighlightNormalRightNoBorder);
					cell.setCellValue(newTime[2]);
					TOTAL_MINUTE_WAIT_APPROVE = TOTAL_MINUTE_WAIT_APPROVE + Integer.parseInt(newTime[2]);
					
					//สถานที่ปฏิบัติงาน : SITESERVICE_REMARK
					cell = row.createCell((short)6);
					cell.setCellStyle(styleYellowHighlightNormalLeftNoBorder);
					cell.setCellValue(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")));
					
					//สถานะอนุมัติจาก HRM : GLOBAL_DATA_VALUE
					cell = row.createCell((short) 7);
					cell.setCellStyle(styleYellowHighlightNormalLeftNoBorder);
					cell.setCellValue(approveStatusHRM);
				}
				
				//ถ้า สถานะอนุมัติจาก HRM ไม่ใช่ รออนุมัติ และ ไม่อนุมัติ ให้ใช้ style ปกติ
				else{
					
					//วันที่ - เวลา เริ่มต้น : START_DATETIME
					cell = row.createCell((short) 1);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("START_DATETIME")) + GlobalVariableTO.START_TIME_MS));
					
					//วันที่ - เวลา สิ้นสุด : END_DATETIME
					cell = row.createCell((short) 2);
					cell.setCellStyle(styleNormalCenterNoBorder);
					cell.setCellValue(TOUtil.convertDateTimeForDisplay(StringUtil.nullToString(rst.getString("END_DATETIME")) + GlobalVariableTO.START_TIME_MS));
					
					//Process compute day hour minute from hrm
					newTime = TOUtil.computeTimeHRM(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")),StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));
					
					//จำนวนวัน : TOTAL_ONSITE_DAY
					cell = row.createCell((short) 3);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[0]);
					TOTAL_DAY_APPROVE = TOTAL_DAY_APPROVE + Integer.parseInt(newTime[0]);
					
					//จำนวนชั่วโมง : TOTAL_ONSITE_HOUR
					cell = row.createCell((short) 4);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[1]);
					TOTAL_HOUR_APPROVE = TOTAL_HOUR_APPROVE + Integer.parseInt(newTime[1]);
					
					//จำนวนนาที : TOTAL_ONSITE_MINUTE
					cell = row.createCell((short) 5);
					cell.setCellStyle(styleNormalRightNoBorder);
					cell.setCellValue(newTime[2]);
					TOTAL_MINUTE_APPROVE = TOTAL_MINUTE_APPROVE + Integer.parseInt(newTime[2]);
					
					//สถานที่ปฏิบัติงาน : SITESERVICE_REMARK
					cell = row.createCell((short) 6);
					cell.setCellStyle(styleNormalLeftNoBorder);
					cell.setCellValue(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")));
					
					//สถานะอนุมัติจาก HRM : GLOBAL_DATA_VALUE
					cell = row.createCell((short) 7);
					cell.setCellStyle(styleNormalLeftNoBorder);
					cell.setCellValue(approveStatusHRM);
				}
				
				curRow++;
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		curRow++;
		
		return curRow;
	}

	private int createHeaderDetail(int curRow) throws Exception {
		
		int colIndex = 1;
		
		//Set Header 1
	    row = sheet.createRow(curRow-1);
	    row.setHeight((short)500);
	    
	    row = sheet.createRow(curRow);
	    
		cell = row.createCell((short) colIndex++);
		cell.setCellStyle(styleTableHeader2);
		cell.setCellValue(bundleTO.getString("to.useTimeOffsetDetail"));
		
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
		
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 1, 7));
		curRow++;
		
		//Set Header 2
		//วันที่ - เวลา เริ่มต้น
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 1);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.startDateTime"));
		
		//วันที่ - เวลา เริ่มต้น
		cell = row.createCell((short) 2);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.endDateTime"));
		
		//จำนวนวัน
		cell = row.createCell((short) 3);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalDay"));
		
		//จำนวนชั่วโมง
		cell = row.createCell((short) 4);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalHour"));
				
		//จำนวนนาที
		cell = row.createCell((short) 5);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.totalMinute"));
		
		//สถานที่ปฏิบัติงาน
		cell = row.createCell((short) 6);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.workPlace"));
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 6, 6));
		
		//สถานะอนุมัติจาก HRM
		cell = row.createCell((short) 7);
		cell.setCellStyle(styleTableHeader);
		cell.setCellValue(bundleTO.getString("to.approveStatusHRM"));
		
		curRow++;
		
		return curRow;
	}

	private int createHeader(int curRow, InuseReportSearchCriteria criteria,String sheetName) throws Exception {

		sheet = workbook.createSheet(sheetName);
		
		//Set sheet style
		sheet.setAutobreaks(true);
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setLandscape(false);				// เอกสารแนวนอน
		sheet.getPrintSetup().setValidSettings(true);
		sheet.getPrintSetup().setFitHeight((short) 0);			// fit กระดาษตั้ง
		sheet.getPrintSetup().setFitWidth((short) 1);			// fit กระดาษแนวนอน
		sheet.getPrintSetup().setLandscape(false);
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		sheet.getPrintSetup().setPageOrder(PageOrder.OVER_THEN_DOWN);
		
		//Set ความกว้างของ cell
		sheet.setColumnWidth(0, 1000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 7500);
		sheet.setColumnWidth(7, 4000);
		
		//Set Header
	    row = sheet.createRow(curRow); // first row
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		cell.setCellStyle(styleHeader);
		cell.setCellValue(bundleTO.getString("to.reportTO"));
		curRow++;
		
		//ชื่อ-สกุล พนักงาน
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 2));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.fullNameEmp") + GlobalVariableTO.SEPERLATE);
						
		cell = row.createCell((short) 3);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 3, 7));
		cell.setCellStyle(styleCriteriaValue);
		cell.setCellValue(getUser().getFamilyName() + " " + getUser().getGivenName());
		curRow++;
		
		//ตั้งแต่วันที่
		row = sheet.createRow(curRow);
		cell = row.createCell((short) 0);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 0, 2));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.dateFrom") + GlobalVariableTO.SEPERLATE);
				
		cell = row.createCell((short) 3);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 3, 4));
		cell.setCellStyle(styleCriteriaValue);
				
		if(criteria.getStartDate() != null && !criteria.getStartDate().isEmpty()){
			cell.setCellValue(criteria.getStartDate().substring(0, 6) + TOUtil.convertDateToReport(criteria.getStartDate()));
		}else{
			cell.setCellValue(GlobalVariableTO.DAT_SPACE);
		}
				
		//ถึงวันที่
		cell = row.createCell((short) 5);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 5, 6));
		cell.setCellStyle(styleCriteriaLabelRight);
		cell.setCellValue(bundleTO.getString("to.dateTo") + GlobalVariableTO.SEPERLATE);
				
		cell = row.createCell((short) 7);
		sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 7, 7));
		cell.setCellStyle(styleCriteriaValue);
				
		if(criteria.getEndDate() != null && !criteria.getEndDate().isEmpty()){
			cell.setCellValue(criteria.getEndDate().substring(0, 6) + TOUtil.convertDateToReport(criteria.getEndDate()));
		}else{
			cell.setCellValue(GlobalVariableTO.DAT_SPACE);
		}
				
		curRow++;
		
		curRow++;
		
		return curRow;
	}
		
	private void manageTimeApprove() throws Exception {
		
		if(TOTAL_MINUTE_APPROVE > 60){
			TOTAL_HOUR_APPROVE = TOTAL_HOUR_APPROVE + (int)(Math.floor(TOTAL_MINUTE_APPROVE/60));
			TOTAL_MINUTE_APPROVE = TOTAL_MINUTE_APPROVE%60;
		}
		
		if(TOTAL_HOUR_APPROVE > 8){
			TOTAL_DAY_APPROVE = TOTAL_DAY_APPROVE + (int)(Math.floor(TOTAL_HOUR_APPROVE/8));
			TOTAL_HOUR_APPROVE = TOTAL_HOUR_APPROVE%8;
		}
	}
	
	private void manageTimeDisApprove() throws Exception {
		
		if(TOTAL_MINUTE_DIS_APPROVE > 60){
			TOTAL_HOUR_DIS_APPROVE = TOTAL_HOUR_DIS_APPROVE + (int)(Math.floor(TOTAL_MINUTE_DIS_APPROVE/60));
			TOTAL_MINUTE_DIS_APPROVE = TOTAL_MINUTE_DIS_APPROVE%60;
		}
		
		if(TOTAL_HOUR_DIS_APPROVE > 8){
			TOTAL_DAY_DIS_APPROVE = TOTAL_DAY_DIS_APPROVE + (int)(Math.floor(TOTAL_HOUR_DIS_APPROVE/8));
			TOTAL_HOUR_DIS_APPROVE = TOTAL_HOUR_DIS_APPROVE%8;
		}
	}
	
	private void manageTimeWaitApprove() throws Exception {
		
		if(TOTAL_MINUTE_WAIT_APPROVE > 60){
			TOTAL_HOUR_WAIT_APPROVE = TOTAL_HOUR_WAIT_APPROVE + (int)(Math.floor(TOTAL_MINUTE_WAIT_APPROVE/60));
			TOTAL_MINUTE_WAIT_APPROVE = TOTAL_MINUTE_WAIT_APPROVE%60;
		}
		
		if(TOTAL_HOUR_WAIT_APPROVE > 8){
			TOTAL_DAY_WAIT_APPROVE = TOTAL_DAY_WAIT_APPROVE + (int)(Math.floor(TOTAL_HOUR_WAIT_APPROVE/8));
			TOTAL_HOUR_WAIT_APPROVE = TOTAL_HOUR_WAIT_APPROVE%8;
		}
	}

	protected byte[] exportPdf(InuseReportSearchCriteria criteria,ResultSet rst) throws Exception {
		
		ByteArrayOutputStream baos = null;
		final int rowStart = 0;								//row start
		final int criteriaWidth = 8;						//width of all criteria column
		
		try {
			
			TOTAL_DAY_APPROVE = 0;
			TOTAL_HOUR_APPROVE = 0;
			TOTAL_MINUTE_APPROVE = 0;
			
			TOTAL_DAY_DIS_APPROVE = 0;
			TOTAL_HOUR_DIS_APPROVE = 0;
			TOTAL_MINUTE_DIS_APPROVE = 0;
			
			TOTAL_DAY_WAIT_APPROVE = 0;
			TOTAL_HOUR_WAIT_APPROVE = 0;
			TOTAL_MINUTE_WAIT_APPROVE = 0;
			
			float[] tbColumnWidth = getPdfColumnWidthArr();		//table column width
			HashMap<String, Font> mapFont = createMapFont();										//create mapFont for PDF font
			
			baos = new ByteArrayOutputStream();
			
			Document document = new Document(PageSize.A4);
			
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			
			document.open();
			document.setMargins(OFFSET, OFFSET, OFFSET, OFFSET); 		//ตั้งค่าขอบกระดาษ(Left, Right, Top, Button)
			
			//create table
	        PdfPTable table = new PdfPTable(tbColumnWidth);
	        
	        table = createPdfHeader(table, mapFont, rowStart, criteriaWidth, tbColumnWidth, criteria);
	        
	        table = createPdfDetailHeader(table, mapFont,criteriaWidth);
	        
	        table = createPdfDetailData(table,mapFont,criteriaWidth,rst);
	        
	        table = createPdfFooter(table,mapFont);
	        
	        table.setTotalWidth(tbColumnWidth);
		    table.setLockedWidth(true);
			
		    PdfContentByte canvas = writer.getDirectContent();
		    PdfTemplate template = canvas.createTemplate(table.getTotalWidth(), table.getTotalHeight());
		    
		    table.writeSelectedRows(0, -1, 0, table.getTotalHeight(), template);
		    
		    document.add(table);
		    document.close();
		    
		}catch(Exception e){
			throw e;
		}
		
		return baos.toByteArray();
	}

	private PdfPTable createPdfFooter(PdfPTable table,HashMap<String, Font> mapFont) throws Exception {
		
		Phrase phrase = null;
		
		try {
			
			//Blank
			phrase = new Phrase("", mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 8 , null));
			
			//จำนวนชั่วโมงชดเชยที่ได้รับการอนุมัติให้ใช้งาน
			phrase = new Phrase(bundleTO.getString("to.totalTOApprove") + GlobalVariableTO.SEPERLATE , mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 4 , null));
			
			manageTimeApprove();
			phrase = new Phrase(TOTAL_DAY_APPROVE + DAY + TOTAL_HOUR_APPROVE + HOUR + TOTAL_MINUTE_APPROVE + MINUTE, mapFont.get(FONT16NORMALBLUE));
			table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 4 , null));
			
			//จำนวนชั่วโมงชดเชยที่ไม่ได้รับการอนุมัติ
			phrase = new Phrase(bundleTO.getString("to.totalTODisApprove") + GlobalVariableTO.SEPERLATE, mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 4 , null));
			
			manageTimeDisApprove();
			phrase = new Phrase(TOTAL_DAY_DIS_APPROVE + DAY + TOTAL_HOUR_DIS_APPROVE + HOUR + TOTAL_MINUTE_DIS_APPROVE + MINUTE, mapFont.get(FONT16NORMALBLUE));
			table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 4 , null));
			
			//จำนวนชั่วโมงชดเชยที่รออนุมัติ
			phrase = new Phrase(bundleTO.getString("to.totalTOWaitApprove") + GlobalVariableTO.SEPERLATE, mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 4 , null));
			
			manageTimeWaitApprove();
			phrase = new Phrase(TOTAL_DAY_WAIT_APPROVE + DAY + TOTAL_HOUR_WAIT_APPROVE + HOUR + TOTAL_MINUTE_WAIT_APPROVE + MINUTE, mapFont.get(FONT16NORMALBLUE));
			table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 4 , null));
			
		} catch (Exception e) {
			throw e;
		}
		
		return table;
	}

	private PdfPTable createPdfDetailData(PdfPTable table,HashMap<String, Font> mapFont, int criteriaWidth, ResultSet rst) throws Exception {
		
		Phrase phrase = null;
		
		String[] newTime = new String[3];
		
		String approveStatus;
		
		try {
			
			while(rst.next()){
				
				approveStatus = StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE"));
				
				//ถ้า สถานะอนุมัติจาก HRM เป็น ไม่อนุมัติ จะใช้ style สีแดง
				if(approveStatus.equals(bundleTO.getString("to.disApprove"))){
					
					//Blank
					phrase = new Phrase("", mapFont.get(FONT16BOLD));
					table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , Color.RED));
					
					//วันที่ - เวลาเริ่มต้น
					phrase = new Phrase(convertToYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))), mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.RED));
					
					//วันที่ - เวลาสิ้นสุด
					phrase = new Phrase(convertToYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))), mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.RED));
					
					//Process compute day hour minute from hrm
					newTime = TOUtil.computeTimeHRM(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")),StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));
					
					//จำนวนวัน
					phrase = new Phrase(newTime[0], mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0 , Color.RED));
					TOTAL_DAY_DIS_APPROVE = TOTAL_DAY_DIS_APPROVE + Integer.parseInt(newTime[0]);
					
					//จำนวนชั่วโมง
					phrase = new Phrase(newTime[1], mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0 , Color.RED));
					TOTAL_HOUR_DIS_APPROVE = TOTAL_HOUR_DIS_APPROVE + Integer.parseInt(newTime[1]);
					
					//จำนวนนาที
					phrase = new Phrase(newTime[2], mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0 , Color.RED));
					TOTAL_MINUTE_DIS_APPROVE = TOTAL_MINUTE_DIS_APPROVE + Integer.parseInt(newTime[2]);
					
					//สถานที่ปฏิบัติงาน
					phrase = new Phrase(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")), mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , Color.RED));
					
					//สถานะอนุมัติจาก HRM
					phrase = new Phrase(approveStatus, mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , Color.RED));
					
					
				}
				
				//ถ้า สถานะอนุมัติจาก HRM เป็น รออนุมัติ จะใช้ style สีเหลือง
				else if(approveStatus.equals(bundleTO.getString("to.waitApprove"))){
					
					//Blank
					phrase = new Phrase("", mapFont.get(FONT16BOLD));
					table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , Color.YELLOW));
					
					//วันที่ - เวลาเริ่มต้น
					phrase = new Phrase(convertToYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))), mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.YELLOW));
					
					//วันที่ - เวลาสิ้นสุด
					phrase = new Phrase(convertToYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))), mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.YELLOW));
					
					//Process compute day hour minute from hrm
					newTime = TOUtil.computeTimeHRM(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")),StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));
					
					//จำนวนวัน
					phrase = new Phrase(newTime[0], mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0 , Color.YELLOW));
					TOTAL_DAY_WAIT_APPROVE = TOTAL_DAY_WAIT_APPROVE + Integer.parseInt(newTime[0]);
					
					//จำนวนชั่วโมง
					phrase = new Phrase(newTime[1], mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0 , Color.YELLOW));
					TOTAL_HOUR_WAIT_APPROVE = TOTAL_HOUR_WAIT_APPROVE + Integer.parseInt(newTime[1]);
					
					//จำนวนนาที
					phrase = new Phrase(newTime[2], mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0 , Color.YELLOW));
					TOTAL_MINUTE_WAIT_APPROVE = TOTAL_MINUTE_WAIT_APPROVE + Integer.parseInt(newTime[2]);
					
					//สถานที่ปฏิบัติงาน
					phrase = new Phrase(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")), mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , Color.YELLOW));
					
					//สถานะอนุมัติจาก HRM
					phrase = new Phrase(approveStatus, mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , Color.YELLOW));
					
				}
				
				//ถ้า สถานะอนุมัติจาก HRM ไม่ใช่ รออนุมัติ และ ไม่อนุมัติ ให้ใช้ style ปกติ
				else{
					
					//Blank
					phrase = new Phrase("", mapFont.get(FONT16BOLD));
					table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , null));
					
					//วันที่ - เวลาเริ่มต้น
					phrase = new Phrase(convertToYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("START_DATETIME"))), mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , null));
					
					//วันที่ - เวลาสิ้นสุด
					phrase = new Phrase(convertToYYYY_MM_DD_HHMMSS(StringUtil.nullToString(rst.getString("END_DATETIME"))), mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , null));
					
					//Process compute day hour minute from hrm
					newTime = TOUtil.computeTimeHRM(StringUtil.nullToString(rst.getString("TOTAL_ONSITEDAY")),StringUtil.nullToString(rst.getString("TOTAL_ONSITETIME")));
					
					//จำนวนวัน
					phrase = new Phrase(newTime[0], mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0 , null));
					TOTAL_DAY_APPROVE = TOTAL_DAY_APPROVE + Integer.parseInt(newTime[0]);
					
					//จำนวนชั่วโมง
					phrase = new Phrase(newTime[1], mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0 , null));
					TOTAL_HOUR_APPROVE = TOTAL_HOUR_APPROVE + Integer.parseInt(newTime[1]);
					
					//จำนวนนาที
					phrase = new Phrase(newTime[2], mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0 , null));
					TOTAL_MINUTE_APPROVE = TOTAL_MINUTE_APPROVE + Integer.parseInt(newTime[2]);
					
					//สถานที่ปฏิบัติงาน
					phrase = new Phrase(StringUtil.nullToString(rst.getString("SITESERVICE_REMARK")), mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , null));
					
					//สถานะอนุมัติจาก HRM
					phrase = new Phrase(approveStatus, mapFont.get(FONT14BOLD));
					table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , null));
										
				}
				
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return table;
	}

	private PdfPTable createPdfDetailHeader(PdfPTable table,HashMap<String, Font> mapFont,int criteriaWidth) throws Exception {
		
		Phrase phrase = null;
		
		try{
			
			//Blank
			phrase = new Phrase("", mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, criteriaWidth , null));
			
			//รายละเอียดข้อมูลการใช้เวลาชดเชย
			phrase = new Phrase(bundleTO.getString("to.useTimeOffsetDetail"), mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, criteriaWidth , Color.lightGray));
			
			//Blank
			phrase = new Phrase("", mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0 , Color.GRAY));
			
			//วันที่ - เวลาเริ่มต้น
			phrase = new Phrase(bundleTO.getString("to.startDateTime"), mapFont.get(FONT14BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.GRAY));
			
			//วันที่ - เวลา สิ้นสุด
			phrase = new Phrase(bundleTO.getString("to.endDateTime"), mapFont.get(FONT14BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.GRAY));
			
			//จำนวนวัน
			phrase = new Phrase(bundleTO.getString("to.totalDay"), mapFont.get(FONT14BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.GRAY));
			
			//จำนวนชั่วโมง
			phrase = new Phrase(bundleTO.getString("to.totalHour"), mapFont.get(FONT14BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.GRAY));
			
			//จำนวนนาที
			phrase = new Phrase(bundleTO.getString("to.totalMinute"), mapFont.get(FONT14BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.GRAY));
			
			//สถานที่ปฏิบัติงาน
			phrase = new Phrase(bundleTO.getString("to.workPlace"), mapFont.get(FONT14BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.GRAY));
				
			//สถานะอนุมัติจาก HRM
			phrase = new Phrase(bundleTO.getString("to.approveStatusHRM"), mapFont.get(FONT14BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0 , Color.GRAY));
			
			
		} catch (Exception e) {
			throw e;
		}
		return table;
	}

	private PdfPTable createPdfHeader(PdfPTable table,HashMap<String, Font> mapFont, int rowStart, int criteriaWidth,float[] tableWidth, InuseReportSearchCriteria criteria) throws Exception {

		Phrase phrase = null;
		
		try {
			
			//Set Header
			phrase = new Phrase(bundleTO.getString("to.reportTO"), mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, criteriaWidth , null));
			
			//ชื่อ-สกุล พนักงาน
			phrase = new Phrase(bundleTO.getString("to.fullNameEmp") + GlobalVariableTO.SEPERLATE, mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 3, null));
			
			phrase = new Phrase(getUser().getFamilyName() + " " + getUser().getGivenName(), mapFont.get(FONT16NORMALBLUE));
			table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 5, null));
			
			//ตั้งแต่วันที่
			phrase = new Phrase(bundleTO.getString("to.dateFrom") + GlobalVariableTO.SEPERLATE, mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 3, null));
			
			phrase = new Phrase(criteria.getStartDate(), mapFont.get(FONT16NORMALBLUE));
			table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0, null));
			
			phrase = new Phrase(bundleTO.getString("to.dateTo") + GlobalVariableTO.SEPERLATE, mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignRight(phrase, 0, 0, 0, 0, 0, null));
			
			phrase = new Phrase(criteria.getEndDate(), mapFont.get(FONT16NORMALBLUE));
			table.addCell(getCellAlignLeft(phrase, 0, 0, 0, 0, 0, null));
			
			phrase = new Phrase("", mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0, null));
			
			phrase = new Phrase("", mapFont.get(FONT16BOLD));
			table.addCell(getCellAlignCenter(phrase, 0, 0, 0, 0, 0, null));
			
		} catch (Exception e) {
			throw e;
		}
		return table;
	}

	private HashMap<String, Font> createMapFont() throws Exception {
		
		HashMap<String, Font> mapFont = new HashMap<String, Font>();
		
		try {
			
			mapFont.put(FONT16BOLD, FONT_16BOLD);
			mapFont.put(FONT16NORMAL, FONT_16NORMAL);
			mapFont.put(FONT16NORMALBLUE, FONT_16NORMALBLUE);
			mapFont.put(FONT14BOLD, FONT_14BOLD);
			mapFont.put(FONT14NORMAL,FONT_14NORMAL);
			mapFont.put(FONT12BOLD, FONT_12BOLD);
			mapFont.put(FONT12NORMAL, FONT_12NORMAL);
			
		} catch (Exception e) {
			throw e;
		}
        return mapFont;
	}

	private float[] getPdfColumnWidthArr() throws Exception {
		
		float[] columnWidthArr = null;
		try {
			int index = 0;
			
			columnWidthArr = new float[8];
			columnWidthArr[index++] = 5f;				//Blank
			columnWidthArr[index++] = 100f;				//วันที่ - เวลา เริ่มต้น
			columnWidthArr[index++] = 100f;				//วันที่  - เวลา สิ้นสุด
			columnWidthArr[index++] = 60f;				//จำนวนวัน
			columnWidthArr[index++] = 65f;				//จำนวนชั่วโมง
			columnWidthArr[index++] = 60f;				//จำนวนนาที
			columnWidthArr[index++] = 100f;				//สถานที่ปฏิบัติงาน
			columnWidthArr[index++] = 90f;				//สถานะอนุมัติจาก HRM
				
			
		} catch (Exception e) {
			throw e;
		}
		return columnWidthArr;
	}
	
	private PdfPCell getCellAlignCenter(Phrase phrase, float bdLeft, float bdRight, float bdTop, float bdBottom, int colspan, Color bgColor) throws Exception {
		PdfPCell cell = null;
		try {
			cell = new PdfPCell();
			cell.setBorderWidthLeft(bdLeft);
			cell.setBorderWidthRight(bdRight);
			cell.setBorderWidthTop(bdTop);
			cell.setBorderWidthBottom(bdBottom);
			cell.setBackgroundColor(bgColor);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(colspan);
			cell.setPhrase(phrase);
			
		} catch (Exception e) {
			throw e;
		}
		return cell;
	} 
	
	private PdfPCell getCellAlignRight(Phrase phrase, float bdLeft, float bdRight, float bdTop, float bdBottom, int colspan, Color bgColor) throws Exception {
		PdfPCell cell = null;
		try {
			cell = new PdfPCell();
			cell.setBorderWidthLeft(bdLeft);
			cell.setBorderWidthRight(bdRight);
			cell.setBorderWidthTop(bdTop);
			cell.setBorderWidthBottom(bdBottom);
			cell.setBackgroundColor(bgColor);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(colspan);
			cell.setPhrase(phrase);
			
		} catch (Exception e) {
			throw e;
		}
		return cell;
	} 
	
	private PdfPCell getCellAlignLeft(Phrase phrase, float bdLeft, float bdRight, float bdTop, float bdBottom, int colspan, Color bgColor) throws Exception {
		PdfPCell cell = null;
		try {
			cell = new PdfPCell();
			cell.setBorderWidthLeft(bdLeft);
			cell.setBorderWidthRight(bdRight);
			cell.setBorderWidthTop(bdTop);
			cell.setBorderWidthBottom(bdBottom);
			cell.setBackgroundColor(bgColor);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(colspan);
			cell.setPhrase(phrase);
			cell.setNoWrap(false);
		} catch (Exception e) {
			throw e;
		}
		return cell;
	} 
	
	
	/**
	 * Example : 14/01/2010 08:30:00 ==> 2010-01-14 08:30:00
	 * @param sDate : String date format dd/MM/yyyy HH:mm:ss
	 * @return String date format yyyy-MM-dd HH:mm:ss
	 * @throws Exception
	 */
	private static String convertToYYYY_MM_DD_HHMMSS(String sDate) throws Exception{
		
		String oldFormat = GlobalVariableTO.FORMAT_DD_MM_YYYY_HHMMSS;
	    String newFormat = GlobalVariableTO.FORMAT_YYYY_MM_DD_HHMMSS;
	    
	    SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
	    SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
	    
	    String x = sdf2.format(sdf1.parse(sDate + GlobalVariableTO.START_TIME_MS));
	    String y = TOUtil.convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(x);
		
		return y;
	}
	
}
