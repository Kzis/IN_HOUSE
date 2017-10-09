package com.cct.inhouse.timeoffset.util;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import util.calendar.CalendarUtil;

import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveSearch;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffset;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetModel;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetSearch;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportSearch;

public class TOUtil {

	/**
	 * ใช้สำหรับแปลงค่า String of long ให้เป็น Long เพื่อใช้ในการ execute query
	 *
	 * @param value
	 * @return
	 */
	public static Long convertLongValue(String value) throws Exception {
		if ((value == null) || value.trim().isEmpty()) {
			return null;
		} else {
			return Long.parseLong(value);
		}
	}

	public static String convertDateTimeForDisplay(String dateValue) throws Exception {
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMMSS();
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat,
				ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}
	
	/**
	 * แปลงค่าจาก DD/MM/YYYY เป็น YYYY-MM-DD
	 *  App Locale To App Locale
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForInsertYYYY_MM_DD(String dateValue) throws Exception {
		String toFormat = "YYYY-MM-DD";
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			}
		}

		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale(), toFormat,
				ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}
	
	/**
	 * แปลงค่าจาก DD/MM/YYYY เป็น YYYY-MM-DD
	 *  Datebase Locale To App Locale
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForInsertYYYY_MM_DD_En_Th(String dateValue) throws Exception {
		String toFormat = "YYYY-MM-DD";
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			}
		}
		
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat,
				ParameterConfig.getParameter().getApplication().getDatetimeLocale());

	}
	
	/**
	 * App Locale To App Locale
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForDisplayYYYY_MM_DD_HHMMSS(String dateValue) throws Exception {
		String fromFormat = "YYYY-MM-DD HH:mm:ss";
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMMSS();
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = "YYYY-MM-DD";
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = "YYYY-MM-DD HH:mm:ss";
			}
		}

		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatetimeLocale(), toFormat,
				ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}
	
	/**
	 * Datebase Locale To App Locale
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForDisplayYYYY_MM_DD_HHMMSS_En_Th(String dateValue) throws Exception {
		String fromFormat = "YYYY-MM-DD HH:mm:ss";
		String toFormat = ParameterConfig.getParameter().getDateFormat().getForDisplayHHMMSS();
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = "YYYY-MM-DD";
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = "YYYY-MM-DD HH:mm:ss";
			}
		}

		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat,
				ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	public static String formatDouble(String value, int digit) throws Exception {

		String returnValue = "";
		if ((value != null) && !value.trim().isEmpty()) {
			String patten = "###0";
			if (digit > 0) {
				patten += ".";
			}

			for (int i = 0; i < digit; i++) {
				patten += "0";
			}

			returnValue = new DecimalFormat(patten).format(Double.parseDouble(value));
		}
		return returnValue;
	}

	/**
	 * 
	 * 2016-06-20 > 31/12/2015
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateFromYYYYMMDDToDDslMMslYYYY(String dateValue) throws Exception {
		String toFormat = "DD/MM/YYYY";
		String fromFormat = "YYYY-MM-DD";
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		}

		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat,
				ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	/**
	 * 
	 * 31/12/2015 23:59 > 2015-12-31- 23:59:59
	 * Datebase Locale To App Locale
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForInsertYYYY_MM_DD_HHMMSS_En_Th(String dateValue) throws Exception {
		String toFormat = "YYYY-MM-DD HH:mm:ss";
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();

			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getDatabaseLocale(), toFormat,
				ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}
	
	/**
	 * 
	 * 31/12/2015 23:59 > 2015-12-31- 23:59:59
	 * App Locale To App Locale
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForInsertYYYY_MM_DD_HHMMSS(String dateValue) throws Exception {
		String toFormat = "YYYY-MM-DD HH:mm:ss";
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return "";
		} else {
			if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMM();
			} else if (dateValue.length() == ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();

			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, ParameterConfig.getParameter().getApplication().getApplicationLocale(), toFormat,
				ParameterConfig.getParameter().getApplication().getDatetimeLocale());
	}

	public static String getCurrentTimeStamp() throws Exception {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		Date now = new Date();
		return sdfDate.format(now);
	}

	public static String replaceSlashToDat(String dateTime) throws Exception {
		return dateTime.replace("/", "-").concat(":00");
	}

	public static String splitToDate(String dateTime) throws Exception {
		return dateTime.split(" ")[0];
	}

	public static String splitToTime(String dateTime) throws Exception {
		return dateTime.split(" ")[1].substring(0, 5);
	}
	
	/**
	 * ใช้สำหรับคำนวณเวลาที่ได้มาจาก HRM 
	 * @param day : จำนวนวันจาก HRM เช่น 2 วัน
	 * @param hour : จำนวนชั่วโมงจาก HRM เช่น 3.5 ชั่วโมง (3 ชั่วโมง 30 นาที)
	 * @return
	 * @throws Exception
	 */
	public static String[] computeTimeHRM(String day, String hour) throws Exception {

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
		newTime[1] = String.valueOf(newHour).split("\\.")[0];
		newTime[2] = String.valueOf(newMinute).split("\\.")[0];

		return newTime;
	}
	
	/*
	 * Use For : Day , Hour , Minute in TO
	 * Because : When get value from database will have type number
	 * Rerurn : String number not have decimal
	 */
	public static String manageDecimalToInteger(String number){
		
		String newNumber = null;
		
		if(number.length() > 0){
			
			if(number.lastIndexOf(".") >= 0){
				newNumber = number.split("\\.")[0];
			}
			
		}
		
		return newNumber;
		
	}
	
	/**
	 * Convert คศ เป็น พศ
	 * Format : dd/mm/yyyy
	 * Comment: ควรใช้กับแปลงจาก input_dateformat เท่านั้น
	 * @param date
	 * @return
	 */
	public static String convertBEToBC(String date){
		
		String newDate = null;
		String newDateArr[] = null;
		
		if(date == null || date.isEmpty()){
			return null;
		}
		
		if(date.indexOf("/") >= 0){
			newDateArr = date.split("/");
			newDateArr[2] = String.valueOf((Integer.parseInt(newDateArr[2]) + 543));
			newDate = newDateArr[0] + "/" + newDateArr[1] + "/" + newDateArr[2]; 
		}
		
		return newDate;
		
	}
	
	/**
	 * Convert คศ เป็น พศ
	 * Format : yyyy-mm-dd
	 * Comment: ควรใช้กับแปลงจาก input_dateformat เท่านั้น
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	public static String convertBEToBCForInsertYYYY_MM_DD(String date) throws Exception{
		
		String newDate = null;
		String newDateArr[] = null;
		
		if(date == null || date.isEmpty()){
			return null;
		}
		
		date = TOUtil.convertDateTimeForInsertYYYY_MM_DD(date);
		
		if(date.indexOf("-") >= 0){
			newDateArr = date.split("-");
			newDateArr[0] = String.valueOf((Integer.parseInt(newDateArr[0]) + 543));
			newDate = newDateArr[0] + "-" + newDateArr[1] + "-" + newDateArr[2]; 
		}
		
		return newDate;
		
	}
	
	/**
	 * Convert DateTimeSync จาก HRM ไปแสดงบนหน้าจอ
	 * @param dateTime
	 * @return
	 */
	public static String convertDateTimeSyncDateForDisplay(String dateTime) {
		
		String arrDateTime[] = dateTime.split(" ");
		String arrDate[] = arrDateTime[0].split("/");
		arrDate[2] = String.valueOf(Integer.parseInt(arrDate[2]) + 543);
		
		String newDateTime = arrDate[0] + "/" + arrDate[1] + "/" + arrDate[2] + " " + arrDateTime[1];
		
		return newDateTime;
	}
	
	/**
	 * Get current DateTime 
	 * Format : YYYYMMDDHHMMSS
	 * Notice : Use for name of report
	 * @return
	 */
	public static String getCurrentDateTimeReport(){
		
		DateTime now = new DateTime();
		String pattern = "yyyyMMdd";
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		String formatted = formatter.print(now);
		
		return formatted;
	}
	
	/**
	 * ใช้สำหรับจัดการ รายละเอียดเวลาชดเชยจากฝั่ง HRM (สำหรับหน้า รายงานข้อมูลการใช้เวลาชดเชย)
	 * แบบใหม่ คืออันนี้
	 * @param data
	 * @param repFlag 
	 * @return
	 * @throws Exception
	 */
	public static InuseManagerReportSearch manageSearchTOWaitStatus(InuseManagerReportSearch data, String repFlag) throws Exception {
		
		// Check ว่า ถ้าไม่เป็นค่าว่างให้ตัดไม่เอาทศนิยม
		if  ( data.getTimeOffset() != null && !(data.getTimeOffset().isEmpty())) {
			data.setTimeOffset(data.getTimeOffset().split("\\.")[0]);
		}
		
		if  ( data.getTimePendingHRM() != null && !(data.getTimePendingHRM().isEmpty())) {
			data.setTimePendingHRM(data.getTimePendingHRM().split("\\.")[0]);
		}
		
		// Check กรณีที่ ไม่มีเวลาชดเชย แต่มีเวลาจาก HRM มา จะ highlight สีเหลือง
		if(data.getTimeOffset().isEmpty() && (  data.getTimePendingHRM() != null && !(data.getTimePendingHRM().isEmpty()) ) ){
			data.setHighlightRow(GlobalVariableTO.HIGHLIGHT_YELLOW);
		}

		// Check เพื่อ Highlight row
		if ((!(data.getTimeOffset().isEmpty()) && data.getTimeOffset() != null) && (!(data.getTimePendingHRM().isEmpty()) && data.getTimePendingHRM() != null)) {

			// ถ้าเวลาชดเชยคงเหลือ < เวลาที่รออนุมัติจาก HRM จะ highlight สีเหลือง
			if (Integer.parseInt(data.getTimeOffset()) < Integer.parseInt(data.getTimePendingHRM())) {
				data.setHighlightRow(GlobalVariableTO.HIGHLIGHT_YELLOW);
			}

			// ถ้าเวลาชดเชยคงเหลือ ติดลบ (พี่ใจอนุมัติให้ในกรณีไม่มีเวลาเหลือ) จะ highlight สีแดง
			if (Integer.parseInt(data.getTimeOffset()) < 0) {
				data.setHighlightRow(GlobalVariableTO.HIGHLIGHT_RED);
			}

		} else if ((!(data.getTimeOffset().isEmpty()) && data.getTimeOffset() != null) && (data.getTimePendingHRM().isEmpty() || data.getTimePendingHRM() == null)) {

			// ถ้าเวลาชดเชยคงเหลือ ติดลบ (พี่ใจอนุมัติให้ในกรณีไม่มีเวลาเหลือ) จะ highlight สีแดง
			if (Integer.parseInt(data.getTimeOffset()) < 0) {
				data.setHighlightRow(GlobalVariableTO.HIGHLIGHT_RED);
			}

		}

		// Check ว่า เวลาชดเชยคงเหลือ เป็นค่าว่างหรือไม่
		if (data.getTimeOffset().isEmpty() || data.getTimeOffset() == null) {
			data.setTimeOffset(GlobalVariableTO.DAT);
		} else {
			data.setTimeOffset(computeTimeOffset(data.getTimeOffset(),repFlag));
		}

		// Check ว่า เวลาชดเชยที่รออนุมัติจาก HRM เป็นค่าว่างหรือไม่
		if (data.getTimePendingHRM().isEmpty() || data.getTimePendingHRM() == null) {
			data.setTimePendingHRM(GlobalVariableTO.DAT);
		} else {
			data.setTimePendingHRM(computeTimePendingHRM(data.getTimePendingHRM(),repFlag));
		}

		// Process check วัน-เวลา Syn ข้อมูล HRM ล่าสุด  ถ้าเวลา Syn ทั้งสองที่ว่างให้เป็น -
		if (data.getPROCESS_RES_DATETIME_BY_USER().isEmpty() && data.getALL_PROCESS_RES_DATETIME().isEmpty()) {
			data.setDataTimeSyndataLasted(GlobalVariableTO.DAT);
		}
		// ถ้าเวลา ALL_PROCESS_RES_DATETIME มีค่าแต่  PROCESS_RES_DATETIME_BY_USER ไม่มีค่า  ให้เท่ากัยALL_PROCESS_RES_DATETIME
		else if (data.getPROCESS_RES_DATETIME_BY_USER().isEmpty() && !(data.getALL_PROCESS_RES_DATETIME().isEmpty())) {
			data.setDataTimeSyndataLasted(convertDateTimeForDisplayYYYY_MM_DD_HHMMSS_En_Th(data.getALL_PROCESS_RES_DATETIME()));
		}
		// ถ้าเวลา PROCESS_RES_DATETIME_BY_USER มีค่าแต่  ALL_PROCESS_RES_DATETIME ไม่มีค่า ให้เท่ากับ PROCESS_RES_DATETIME_BY_USER
		else if (!(data.getPROCESS_RES_DATETIME_BY_USER().isEmpty()) && data.getALL_PROCESS_RES_DATETIME().isEmpty()) {
			data.setDataTimeSyndataLasted(convertDateTimeForDisplayYYYY_MM_DD_HHMMSS_En_Th(data.getPROCESS_RES_DATETIME_BY_USER()));
		}
		// ถ้ามีค่าทั้งสองให้ทำการเปรียบเทียบ
		else {
			SimpleDateFormat sdf = new SimpleDateFormat(GlobalVariableTO.SYN_DATE_TIME_FORMAT);

			// PROCESS_RES_DATETIME_BY_USER > ALL_PROCESS_RES_DATETIME
			if (sdf.parse(data.getPROCESS_RES_DATETIME_BY_USER()).after(sdf.parse(data.getALL_PROCESS_RES_DATETIME()))) {
				data.setDataTimeSyndataLasted(convertDateTimeForDisplayYYYY_MM_DD_HHMMSS_En_Th(data.getPROCESS_RES_DATETIME_BY_USER()));
			} else {
				data.setDataTimeSyndataLasted(convertDateTimeForDisplayYYYY_MM_DD_HHMMSS_En_Th(data.getALL_PROCESS_RES_DATETIME()));
			}
		}

		return data;
	}
	
	/**
	 * ใช้สำหรับคำนวณ เวลาชดเชยคงเหลือ(TO)
	 * @param minute
	 * @param repFlag 
	 * @return
	 * @throws Exception
	 */
	private static String computeTimeOffset(String minute, String repFlag) throws Exception {

		String newTime = GlobalVariableTO.IS_EMPTY;

		String sDay;
		String sHour;
		String sMinute;

		Double newDay = 0.0;
		Double newHour;
		Double newMinute;
		Boolean navNumber = false;

		newMinute = Double.parseDouble(minute);

		if (newMinute < 0) {
			newMinute = (-1) * newMinute;
			navNumber = true;
		}

		newHour = Math.floor(newMinute / 60);
		newMinute = newMinute % 60;

		if (newHour >= 8.0) {
			newDay = newDay + (int) Math.floor(newHour / 8);
			newHour = newHour % 8;
		}

		sDay = String.valueOf(newDay).substring(0, String.valueOf(newDay).indexOf(GlobalVariableTO.DOT));
		sHour = String.valueOf(newHour).substring(0, String.valueOf(newHour).indexOf(GlobalVariableTO.DOT));
		sMinute = String.valueOf(newMinute).substring(0, String.valueOf(newMinute).indexOf(GlobalVariableTO.DOT));
		
		ResourceBundle bundleTO = ResourceBundle.getBundle("resources.bundle.inhouse.timeoffset.MessageTO", Locale.ENGLISH);
		
		if(repFlag.equals(GlobalVariableTO.REPORT)){
						
			if (!(sDay.equals(GlobalVariableTO.ZERO))) {
				newTime = sDay + bundleTO.getString("to.day") + GlobalVariableTO.SPACE;
			}

			if (!(sHour.equals(GlobalVariableTO.ZERO))) {
				newTime = newTime + (sHour + bundleTO.getString("to.hour") + GlobalVariableTO.SPACE);
			}

			if (!(sMinute.equals(GlobalVariableTO.ZERO))) {
				newTime = newTime + (sMinute + bundleTO.getString("to.min") + GlobalVariableTO.SPACE);
			}
		}else if(repFlag.equals(GlobalVariableTO.TOSHOW)){
			
			newTime = sDay + GlobalVariableTO.SPACE + bundleTO.getString("to.day") + GlobalVariableTO.SPACE;
			newTime = newTime + (sHour + GlobalVariableTO.SPACE + bundleTO.getString("to.hour") + GlobalVariableTO.SPACE);
			newTime = newTime + (sMinute + GlobalVariableTO.SPACE + bundleTO.getString("to.min") + GlobalVariableTO.SPACE);
			
		}else{
			if (!(sDay.equals(GlobalVariableTO.ZERO))) {
				newTime = sDay + GlobalVariableTO.DAY_EN + GlobalVariableTO.SPACE;
			}

			if (!(sHour.equals(GlobalVariableTO.ZERO))) {
				newTime = newTime + (sHour + GlobalVariableTO.HOUR_EN + GlobalVariableTO.SPACE);
			}

			if (!(sMinute.equals(GlobalVariableTO.ZERO))) {
				newTime = newTime + (sMinute + GlobalVariableTO.MINUTE_EN + GlobalVariableTO.SPACE);
			}
		}

		if (newTime.isEmpty()) {
			newTime = GlobalVariableTO.DAT;
		} else if (navNumber) {
			newTime = GlobalVariableTO.DAT + newTime;
		}

		return newTime;
	}
	
	/**
	 * ใช้สำหรับคำนวณ เวลาชดเชยที่รออนุมัติจากฝั่ง HRM
	 * @param minute
	 * @return
	 * @throws Exception
	 */
	private static String computeTimePendingHRM(String minute, String repFlag) throws Exception {

		String newTime = GlobalVariableTO.IS_EMPTY;

		String sDay;
		String sHour;
		String sMinute;

		Double newDay = 0.0;
		Double newHour;
		Double newMinute;

		newMinute = Double.parseDouble(minute);

		newHour = Math.floor(newMinute / 60);
		newMinute = newMinute % 60;

		if (newHour >= 8.0) {
			newDay = newDay + (int) Math.floor(newHour / 8);
			newHour = newHour % 8;
		}

		sDay = String.valueOf(newDay).substring(0, String.valueOf(newDay).indexOf(GlobalVariableTO.DOT));
		sHour = String.valueOf(newHour).substring(0, String.valueOf(newHour).indexOf(GlobalVariableTO.DOT));
		sMinute = String.valueOf(newMinute).substring(0, String.valueOf(newMinute).indexOf(GlobalVariableTO.DOT));

		if(repFlag.equals(GlobalVariableTO.REPORT)){
			
			ResourceBundle bundleTO = ResourceBundle.getBundle("resources.bundle.inhouse.timeoffset.MessageTO", Locale.ENGLISH);
			
			if (!(sDay.equals(GlobalVariableTO.ZERO))) {
				newTime = sDay + bundleTO.getString("to.day") + GlobalVariableTO.SPACE;
			}

			if (!(sHour.equals(GlobalVariableTO.ZERO))) {
				newTime = newTime + (sHour + bundleTO.getString("to.hour") + GlobalVariableTO.SPACE);
			}

			if (!(sMinute.equals(GlobalVariableTO.ZERO))) {
				newTime = newTime + (sMinute + bundleTO.getString("to.min") + GlobalVariableTO.SPACE);
			}
			
		}else{
			if (!(sDay.equals(GlobalVariableTO.ZERO))) {
				newTime = sDay + GlobalVariableTO.DAY_EN + GlobalVariableTO.SPACE;
			}

			if (!(sHour.equals(GlobalVariableTO.ZERO))) {
				newTime = newTime + (sHour + GlobalVariableTO.HOUR_EN + GlobalVariableTO.SPACE);
			}

			if (!(sMinute.equals(GlobalVariableTO.ZERO))) {
				newTime = newTime + (sMinute + GlobalVariableTO.MINUTE_EN + GlobalVariableTO.SPACE);
			}
		}

		if (newTime.isEmpty()) {
			newTime = GlobalVariableTO.DAT;
		}

		return newTime;
	}
	
	/**
	 * ใช้สำหรับแปลง พศ ของรายงาน
	 * @param sDate : Format dd/MM/yyyy(English)
	 * @return  sDate : Format dd/MM/yyyy(Thai)
	 * @throws Exception
	 */
	public static String convertDateToReport(String sDate) throws Exception {

		DateFormat df = new SimpleDateFormat(GlobalVariableTO.DATE_DDslMMslYYYY);
		Date date = df.parse(sDate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);

		return String.valueOf((year + 543));
	}
	
	/**
	 * ใช้สำหรับ การจัดการเวลาชดเชย ตอนจะนำข้อมูลออกไปแสดง
	 * @param lstResult
	 * @return
	 * @throws Exception
	 */
	public static List<ManageTimeOffsetSearch> manageTimeTo(List<ManageTimeOffsetSearch> lstResult) throws Exception {

		int day;
		int hour;
		int minute;

		int fracHour;
		int fracDay;
		int fracMinute;
		
		String[] minuteArray;
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		nf.setRoundingMode(RoundingMode.FLOOR);
		
		try {
			
			//Loop ตาม Size ที่ได้มา
			for (int i = 0; i < lstResult.size(); i++) {

				day = 0;
				hour = 0;
				minute = 0;
				
				fracMinute = 0;
				fracHour = 0;
				fracDay = 0;
				
				//Get ค่าวันจาก List
				day = (int)Double.parseDouble(lstResult.get(i).getDay());
				
				if (lstResult.get(i).getHour().indexOf(GlobalVariableTO.DOT) > 0) {
					
					//Split ชั่วโมง เช่น 3.5 เป็น 3 กับ 5 เพื่อนำไปคำนวณ
					minuteArray = lstResult.get(i).getHour().split("\\.");
					
					// ถ้า ชั่วโมงมีทศนิยมมานำไปคำนวณหานาที
					if(Integer.parseInt(minuteArray[1]) > 0 ){
						
						//ทำการคูณ * 6 ตามหลักการคำนวณเวลา เช่น 3.5 ชั่วโมง = 3 ชั่วโมง 30 นาที นำ 5*6 = 30
						fracMinute = Integer.parseInt(minuteArray[1]) * 6; //3 ชั่วโมง ตามตัวอย่างด้านบน
						hour = Integer.parseInt(minuteArray[0]); //30 นาที ตามตัวอย่างด้านบน
						
					}else{
						
						//ชั่วโมงมีทศนิยมมาเป็น 0 เอาแต่ค่าชั่วโมงไปคำนวณต่อ
						hour = (int)Double.parseDouble(lstResult.get(i).getHour());
						
					}		
					
				}
				
				//Get นาที จาก List มารวมกับ นาที ที่ได้จากการคำนวณชั่วโมง
				minute = fracMinute + (int)Double.parseDouble(lstResult.get(i).getMinute());
				
				// ถ้า นาที >= 60 ต้องปรับเป็นชั่วโมง
				if (minute >= 60) {
					
					//นำ นาที ที่ได้ /60 เพื่อแปลงเป็นชั่วโมง และ หานาทีที่เหลือ เช่น 90 นาที = 1 ชั่วโมง 30 นาที
					fracHour = Integer.parseInt(nf.format(minute / 60)); // 1 ชั่วโมง ตามตัวอย่างด้านบน
					minute = minute % 60; //30นาที ตามตัวอย่างด้านบน
					
				}
				
				//นำชั่วโมงที่รับมาตอนแรก รวมกับชั่วโมงที่ได้จากการคำนวณนาที
				hour = hour + fracHour;
				
				// ถ้า ชั่วโมง >= 8 ต้องปรับเป็น 1 วัน
				if (hour >= 8) {
					
					//นำ ชั่วโมง ที่ได้ /8 เพื่อแปลงเป็นวัน และ หาชั่วโมงที่เหลือ เช่น 9 ชั่วโมง = 1 วัน 1 ชั่วโมง
					fracDay = Integer.parseInt(nf.format(hour / 8)); //1 วัน ตามตัวอย่างด้านบน
					hour = hour % 8; //1 ชั่วโมง ตามตัวอย่างด้านบน
					
				}

				day = day + fracDay;

				lstResult.get(i).setDay(String.valueOf(day));

				if (lstResult.get(i).getHour().indexOf(GlobalVariableTO.DOT) > 0) {
					lstResult.get(i).setHour(String.valueOf(hour));
				}
				
				lstResult.get(i).setMinute(String.valueOf(minute));
			}
		} catch (Exception e) {
			throw e;
		}

		return lstResult;
	}
	
	public static List<ApproveSearch> manageTimeToApprove(List<ApproveSearch> lstResult) throws Exception {

		int day;
		int hour;
		int minute;

		int fracHour;
		int fracDay;
		int fracMinute;

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		nf.setRoundingMode(RoundingMode.FLOOR);
		
		try {
			for (int i = 0; i < lstResult.size(); i++) {

				day = 0;
				hour = 0;
				minute = 0;
				
				fracMinute = 0;
				fracHour = 0;
				fracDay = 0;
				
				String[] minuteArray;

				day = (int)Double.parseDouble(lstResult.get(i).getDay());

				if (lstResult.get(i).getHour().indexOf(".") > 0) {
					
					minuteArray = lstResult.get(i).getHour().split("\\.");
					
					if(Integer.parseInt(minuteArray[1]) > 0 ){
						fracMinute = Integer.parseInt(minuteArray[1]) * 6;
						hour = Integer.parseInt(minuteArray[0]);
					}else{
						
						hour = (int)Double.parseDouble(lstResult.get(i).getHour());
						
					}		
					
				}

				minute = fracMinute + (int)Double.parseDouble(lstResult.get(i).getMinute());

				if (minute >= 60) {
					fracHour = Integer.parseInt(nf.format(minute / 60));
					minute = minute % 60;
				}

				hour = hour + fracHour;

				if (hour >= 8) {
					fracDay = Integer.parseInt(nf.format(hour / 8));
					hour = hour % 8;
				}

				day = day + fracDay;

				lstResult.get(i).setDay(String.valueOf(day));

				if (lstResult.get(i).getHour().indexOf(".") > 0) {
					lstResult.get(i).setHour(String.valueOf(hour));
				}
				
				lstResult.get(i).setMinute(String.valueOf(minute));
			}

		} catch (Exception e) {
			throw e;
		}

		return lstResult;
	}

	public static String manageSearchTOUser(ManageTimeOffsetSearch data,String repFlag) throws Exception {

		// Check ว่า ถ้าไม่เป็นค่าว่างให้ตัดไม่เอาทศนิยม
		if  ( data.getTimeOffset() != null && !(data.getTimeOffset().isEmpty())) {
			data.setTimeOffset(data.getTimeOffset().split("\\.")[0]);
		}
		
		// Check ว่า เวลาชดเชยคงเหลือ เป็นค่าว่างหรือไม่
		if (data.getTimeOffset().isEmpty() || data.getTimeOffset() == null) {
			data.setTimeOffset(GlobalVariableTO.DAT);
		} else {
			data.setTimeOffset(computeTimeOffset(data.getTimeOffset(),repFlag));
		}
		
		return data.getTimeOffset();
	}

	public static String setDefaultTO() throws Exception {
		
		ResourceBundle bundleTO = ResourceBundle.getBundle("resources.bundle.inhouse.timeoffset.MessageTO", Locale.ENGLISH);
		
		String defaultTO = GlobalVariableTO.ZERO + GlobalVariableTO.SPACE+ bundleTO.getString("to.day") + GlobalVariableTO.SPACE;
		defaultTO = defaultTO + (GlobalVariableTO.ZERO + GlobalVariableTO.SPACE + bundleTO.getString("to.hour") + GlobalVariableTO.SPACE);
		defaultTO = defaultTO + (GlobalVariableTO.ZERO + GlobalVariableTO.SPACE + bundleTO.getString("to.min") + GlobalVariableTO.SPACE);
	
		return defaultTO;
	}
}
