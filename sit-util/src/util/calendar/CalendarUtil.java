package util.calendar;

import java.util.Calendar;
import java.util.Locale;

/**
 * 2017-10-24: add format  
 * 	DD/MM/YYYY HH:mm:ss.SSS > 24/01/2017 13:35:35.166
 * @author SD-Sittipol
 *
 */
public class CalendarUtil {

	public static final String DELIMITER_SLASH = "/";
	public static final String DELIMITER_COLON = ":";
	public static final String DELIMITER_BLANK = " ";
	public static final String DELIMITER_MINUS = "-";

	public static final String DELIMITER_DD = "DD";
	public static final String DELIMITER_MM = "MM";
	public static final String DELIMITER_YYYY = "YYYY";
	// public static final String DELIMITER_YY = "YY";
	public static final String DELIMITER_HH = "HH";
	public static final String DELIMITER_MI = "mm";
	public static final String DELIMITER_SS = "ss";
	public static final String DELIMITER_SSS = "SSS";
	
	/**
	public static final Map<String, String> MAP_CHECK_FORMAT = new HashMap<String, String>();
	static {
		MAP_CHECK_FORMAT.put("d", "d");
		MAP_CHECK_FORMAT.put("y", "y");
		MAP_CHECK_FORMAT.put("h", "h");
		MAP_CHECK_FORMAT.put("S", "S");
	}
	**/
		
	
	/**
	 * จัดการข้อมูลวันที่
	 * @param dateValue 	: 2015/12/30 21:50:59 [input value]
	 * @param fromFormat 	: YYYY/MM/DD HH:mm:ss [input format]
	 * @param fromLocale 	: Locale.ENGLISH [input locale]
	 * @param toFormat 		: DD/MM/YYYY HH:mm:ss [output format]
	 * @param toLocale 		: new Locale("th", "TH") [output local]
	 * @return				: 30/12/2558 21:50:59
	 * @throws Exception 
	 */
	public static String convertDateString(String dateValue, String fromFormat, Locale fromLocale, String toFormat, Locale toLocale) throws Exception {
		//[1] จัดการวันที่ เปลี่ยนรูปแบบวันที่จาก String เป็น Calendar เพื่อจัดการ Format ต่างๆ
		Calendar cFrom = CalendarUtil.getCalendarFromDateString(dateValue, fromFormat, fromLocale);
		
		//[2] เปลี่ยน Locale วันที่
		Calendar cTo = CalendarUtil.changeLocale(cFrom, fromLocale, toLocale);
		
		//[3] จัดการวันที่ เปลี่ยนรูปแบบวันที่ตาม FORMAT จาก Calendar เป็น String เพื่อแสดงผล
		return CalendarUtil.getDateStringFromCalendar(cTo, toFormat);
	}

	
	
	/**
	 * เปลี่ยน Locale วันที่
	 * @param fromCalendar
	 * @param fromLocale
	 * @param toLocale
	 * @return
	 */
	public static Calendar changeLocale(Calendar fromCalendar, Locale fromLocale, Locale toLocale) {
		Calendar toCalendar = Calendar.getInstance(toLocale);
		toCalendar.setTimeInMillis(fromCalendar.getTimeInMillis());
		return toCalendar;
	}
	
	
	
	/**
	 * จัดการวันที่ เปลี่ยนรูปแบบวันที่จาก String เป็น Calendar
	 * @param dateValue
	 * @param format
	 * @param toLocale
	 * @return
	 * @throws Exception 
	 */
	public static Calendar getCalendarFromDateString(String dateValue, String format, Locale locale) throws Exception {

		// หา index ของ format และตรวจสอบ format
		int[] indexDD = findIndexOfFormat(DELIMITER_DD, format);
		int[] indexMM = findIndexOfFormat(DELIMITER_MM, format);
		int[] indexYYYY = findIndexOfFormat(DELIMITER_YYYY, format);
		int[] indexHH = findIndexOfFormat(DELIMITER_HH, format);
		int[] indexMI = findIndexOfFormat(DELIMITER_MI, format);
		int[] indexSS = findIndexOfFormat(DELIMITER_SS, format);
		int[] indexSSS = findIndexOfFormat(DELIMITER_SSS, format);

		// หา value ตาม index ที่ได้
		String DD = getDateByField(indexDD, dateValue);
		String MM = getDateByField(indexMM, dateValue);
		String YYYY = getDateByField(indexYYYY, dateValue);
		String HH = getDateByField(indexHH, dateValue);
		String MI = getDateByField(indexMI, dateValue);
		String SS = getDateByField(indexSS, dateValue);
		String SSS = getDateByField(indexSSS, dateValue);
		

		// set ค่าให้ calendar
		Calendar calendar = Calendar.getInstance(locale);
		setCalendarByField(Calendar.DAY_OF_MONTH, DD, calendar);
		setCalendarByField(Calendar.MONTH, MM, calendar);
		setCalendarByField(Calendar.YEAR, YYYY, calendar);
		setCalendarByField(Calendar.HOUR_OF_DAY, HH, calendar);
		setCalendarByField(Calendar.MINUTE, MI, calendar);
		setCalendarByField(Calendar.SECOND, SS, calendar);
		setCalendarByField(Calendar.MILLISECOND, SSS, calendar);
		return calendar;
	}

	
	
	/**
	 * จัดการวันที่ เปลี่ยนรูปแบบวันที่ตาม FORMAT จาก Calendar เป็น String เพื่อแสดงผล
	 * @param calendar
	 * @param format
	 * @return
	 */
	public static String getDateStringFromCalendar(Calendar calendar, String format) {

		// หา value จาก calendar
		String DD = getCalendarByField(Calendar.DAY_OF_MONTH, calendar);
		String MM = getCalendarByField(Calendar.MONTH, calendar);
		String YYYY = getCalendarByField(Calendar.YEAR, calendar);
		String HH = getCalendarByField(Calendar.HOUR_OF_DAY, calendar);
		String MI = getCalendarByField(Calendar.MINUTE, calendar);
		String SS = getCalendarByField(Calendar.SECOND, calendar);
		String SSS = getCalendarByField(Calendar.MILLISECOND, calendar);
		
		// นำ value แทนใน format
		String dateValue = format;
		dateValue = setValueToFormat(DELIMITER_DD, dateValue, DD);
		dateValue = setValueToFormat(DELIMITER_MM, dateValue, MM);
		dateValue = setValueToFormat(DELIMITER_YYYY, dateValue, YYYY);
		dateValue = setValueToFormat(DELIMITER_HH, dateValue, HH);
		dateValue = setValueToFormat(DELIMITER_MI, dateValue, MI);
		dateValue = setValueToFormat(DELIMITER_SS, dateValue, SS);
		dateValue = setValueToFormat(DELIMITER_SSS, dateValue, SSS);
		
		return dateValue;
	}

	/**
	 * เซท value แทนค่าใน format pattern
	 * @param fieldFormat
	 * @param format
	 * @param dateValue
	 * @return
	 */
	private static String setValueToFormat(String fieldFormat, String format, String dateValue) {
		return format.replace(fieldFormat, dateValue);
	}
	
	/**
	private static String setValueToFormatYear(String format, String dateValue) {
		if (format.indexOf(DELIMITER_YYYY) > -1) {
			dateValue = setValueToFormat(DELIMITER_YYYY, format, dateValue);
		} else if (format.indexOf(DELIMITER_YY) > -1) {
			dateValue = setValueToFormat(DELIMITER_YY, format, dateValue.substring(2));
		}
		return dateValue;
	}
	**/

	/**
	 * เซท value เข้าไปใน Calendar ตาม Field ต่างๆ
	 * @param field
	 * @param dateValue
	 * @param calendar
	 */
	private static void setCalendarByField(int field, String dateValue, Calendar calendar) {

		int value = 0;
		if (dateValue == null) {
			if ((Calendar.DAY_OF_MONTH == field) || (Calendar.MONTH == field)) {
				value = 1;
			}else if(Calendar.YEAR == field){
				value = calendar.get(Calendar.YEAR);
			}
		
		} else {
			value = Integer.parseInt(dateValue);
		}

		
		
		// month for java use (0-11) 
		if (Calendar.MONTH == field) {
			value = value - 1;
/**
		} else if(Calendar.YEAR == field){
			if (value < 100) {
				int yearTemp = calendar.get(Calendar.YEAR);
				value = Integer.valueOf(String.valueOf(yearTemp).substring(0, 2) + value);
			}
*/
		}

		calendar.set(field, value);
	}

	/**
	 * หาค่า value จาก Calendar
	 * @param field
	 * @param calendar
	 * @return
	 */
	private static String getCalendarByField(int field, Calendar calendar) {
		
		String returnValue = null;
		int value = calendar.get(field);
		
		// month for common use (1-12) 
		if (Calendar.MONTH == field) {
			value = value + 1;
		}

		if (Calendar.MILLISECOND == field) {
			returnValue = paddingLeftDigit(value, 3);
		} else if (Calendar.YEAR == field) {
			returnValue = paddingLeftDigit(value, 4);
		} else {
			returnValue = paddingLeftDigit(value, 2);
		}
		return returnValue;
	}

	/**
	 * หาค่า value ตาม index ที่ได้
	 * @param index
	 * @param dateValue
	 * @return
	 */
	private static String getDateByField(int[] index, String dateValue) {
		if (index == null) {
			return null;
		} else if (dateValue == null){
			return null;
		} else {
			return dateValue.substring(index[0], index[1]);
		}
	}
	
	/**
	 * หา index ของ format , ตรวจสอบ format
	 * @param formatFind
	 * @param format
	 * @return
	 * @throws Exception 
	 */
	private static int[] findIndexOfFormat(String formatFind, String format) throws Exception {
		int[] index = null;
		//ตรวจสอบ Format pattern
		if (checkFormat(format, formatFind.substring(0, 1), formatFind.length())) {
			index = new int[2];
			index[0] = format.indexOf(formatFind);
			index[1] = index[0] + formatFind.length();
		}
		return index;
	}
	
	/**
	private static int[] findIndexOfFormatYear(String format) throws Exception {
		String formatFind = DELIMITER_YYYY;
		if (format.indexOf(DELIMITER_YYYY) > -1) {
			// Nothing
		} else if (format.indexOf(DELIMITER_YY) > -1) {
			formatFind = DELIMITER_YY;
		}
		return findIndexOfFormat(formatFind, format);
	}
	**/
	
	
	/**
	 * ตรวจสอบ Format pattern 	(เช่น DD/MM/YYYY ถูก , DD/Mm/YYYY ผิด)
	 * @param format	: format pattern input
	 * @param findWith	: ตัวที่ต้องการตรวจสอบใน format เช่น D , M, Y, H, m, s
	 * @param findFound	: จำนวนความยาวของตัวอักษร เช่น D=2 , M2, Y=4
	 * @return
	 * @throws Exception
	 */
	public static boolean checkFormat(String format, String findWith, int findFound) throws Exception {
		//หาไม่เจอจะ return -1
		int a = format.indexOf(findWith);
		if (a == -1 ) {
			return false;
		}
		
		int ab = -1;
		//หาเจอมาตรวจสอบว่า ตัวถัดไปเป็นตัวเดียวกันไหม เช่นส่ง D เข้ามาตัวต่อไปต้องเป็น D ตามจำนวนรอบตัวอักษรที่ส่งเข้ามา (findFound)
		//		- D, M, H, m, s = วน 1 รอบ
		//		- Y				= วน 3 รอบ
		for (int i = 1; i < findFound; i++) {
			ab = format.indexOf(findWith, a + i);
			if ((a - ab) != (-1 * i)) {
				throw new Exception("Invalid format " + findWith);	
			}
		}
		
		//ตรวจสอบกรณีที่เกิน อย่างเช่น  เดือนปกติจะเป็น MM หากระบุ MMM เกินมาคือผิด Format
		if (ab != -1) {
			ab = format.indexOf(findWith, ab + 1);
			if (ab != -1) {
				throw new Exception("Invalid format "+findWith);
			}
		}
		
		return true;
	}	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * ดึงวันที่จาก Calendar ในรูปแบบ DD/MM/YYYY
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getDDslMMslYYYY(Calendar cal) {
		String DDslMMslYYYY = "";
		DDslMMslYYYY = convert2Digit(cal.get(Calendar.DAY_OF_MONTH));
		DDslMMslYYYY += DELIMITER_SLASH + convert2Digit(cal.get(Calendar.MONTH) + 1);
		DDslMMslYYYY += DELIMITER_SLASH + String.valueOf(cal.get(Calendar.YEAR));
		return DDslMMslYYYY;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ DD/MM/YYYY
	 * @param DDslMMslYYYY (DD/MM/YYYY)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromDDslMMslYYYY(String DDslMMslYYYY, Locale toLocale) {
		int YYYY = Integer.parseInt(DDslMMslYYYY.substring(6, 10));
		int MM = Integer.parseInt(DDslMMslYYYY.substring(3, 5));
		int DD = Integer.parseInt(DDslMMslYYYY.substring(0, 2));

		Calendar calendar = Calendar.getInstance(toLocale);
		calendar.set(Calendar.YEAR, YYYY);
		calendar.set(Calendar.MONTH, MM - 1);
		calendar.set(Calendar.DAY_OF_MONTH, DD);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	/**
	 * ดึงวันที่จาก Calendar ในรูปแบบ MM/YYYY
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getMMslYYYY(Calendar cal) {
		String MMslYYYY = "";
		MMslYYYY = convert2Digit(cal.get(Calendar.MONTH) + 1);
		MMslYYYY += DELIMITER_SLASH + String.valueOf(cal.get(Calendar.YEAR));
		return MMslYYYY;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ MM/YYYY
	 * @param MMslYYYY (MM/YYYY)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromMMslYYYY(String MMslYYYY, Locale toLocale) {
		int YYYY = Integer.parseInt(MMslYYYY.substring(3, 7));
		int MM = Integer.parseInt(MMslYYYY.substring(0, 2));

		Calendar calendar = Calendar.getInstance(toLocale);
		calendar.set(Calendar.YEAR, YYYY);
		calendar.set(Calendar.MONTH, MM - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	/**
	 * ดึงวันที่จาก Calendar ในรูปแบบ YYYY
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getYYYY(Calendar cal) {
		String YYYY = String.valueOf(cal.get(Calendar.YEAR));
		return YYYY;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ YYYY
	 * @param YYYY (YYYY)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromYYYY(String YYYY, Locale toLocale) {
		int YYYX = Integer.parseInt(YYYY.substring(0, 4));

		Calendar calendar = Calendar.getInstance(toLocale);
		calendar.set(Calendar.YEAR, YYYX);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	/**
	 * ดึงวันที่จาก Calendar ในรูปแบบ YYYYMMDD
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getYYYYMMDD(Calendar cal) {
		String YYYYMMDD = "";
		YYYYMMDD = String.valueOf(cal.get(Calendar.YEAR));
		YYYYMMDD += convert2Digit(cal.get(Calendar.MONTH) + 1);
		YYYYMMDD += convert2Digit(cal.get(Calendar.DAY_OF_MONTH));
		return YYYYMMDD;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ YYYYMMDD
	 * @param YYYYMMDD (YYYYMMDD)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromYYYYMMDD(String YYYYMMDD, Locale toLocale) {
		int yyyy = Integer.parseInt(YYYYMMDD.substring(0, 4));
		int mm = Integer.parseInt(YYYYMMDD.substring(4, 6));
		int dd = Integer.parseInt(YYYYMMDD.substring(6, 8));

		Calendar calendar = Calendar.getInstance(toLocale);
		calendar.set(Calendar.YEAR, yyyy);
		calendar.set(Calendar.MONTH, mm - 1);
		calendar.set(Calendar.DAY_OF_MONTH, dd);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	/**
	 * ดึงวันที่จาก Calendar ในรูปแบบ YYYYMM
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getYYYYMM(Calendar cal) {
		String YYYYMM = "";
		YYYYMM = String.valueOf(cal.get(Calendar.YEAR));
		YYYYMM += convert2Digit(cal.get(Calendar.MONTH) + 1);
		return YYYYMM;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ YYYYMM
	 * @param YYYYMM (YYYYMM)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromYYYYMM(String YYYYMM, Locale toLocale) {
		int yyyy = Integer.parseInt(YYYYMM.substring(0, 4));
		int mm = Integer.parseInt(YYYYMM.substring(4, 6));

		Calendar calendar = Calendar.getInstance(toLocale);
		calendar.set(Calendar.YEAR, yyyy);
		calendar.set(Calendar.MONTH, mm - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	/**
	 * ดึงวันที่จาก Calendar ในรูปแบบ YYYY-MM-DD
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getYYYYmiMMmiDD(Calendar cal) {
		String YYYYmiMMmiDD = "";
		YYYYmiMMmiDD = String.valueOf(cal.get(Calendar.YEAR));
		YYYYmiMMmiDD += DELIMITER_MINUS + convert2Digit(cal.get(Calendar.MONTH) + 1);
		YYYYmiMMmiDD += DELIMITER_MINUS + convert2Digit(cal.get(Calendar.DAY_OF_MONTH));
		return YYYYmiMMmiDD;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ YYYY-MM-DD
	 * @param YYYYmiMMmiDD (YYYY-MM-DD)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromYYYYmiMMmiDD(String YYYYmiMMmiDD, Locale toLocale) {
		int YYYY = Integer.parseInt(YYYYmiMMmiDD.substring(0, 4));
		int MM = Integer.parseInt(YYYYmiMMmiDD.substring(5, 7));
		int DD = Integer.parseInt(YYYYmiMMmiDD.substring(8, 10));

		Calendar calendar = Calendar.getInstance(toLocale);
		calendar.set(Calendar.YEAR, YYYY);
		calendar.set(Calendar.MONTH, MM - 1);
		calendar.set(Calendar.DAY_OF_MONTH, DD);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	/**
	 * ดึงวันที่จาก Calendar ในรูปแบบ YYYY-MM
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getYYYYmiMM(Calendar cal) {
		String YYYYmiMM = "";
		YYYYmiMM = String.valueOf(cal.get(Calendar.YEAR));
		YYYYmiMM += DELIMITER_MINUS + convert2Digit(cal.get(Calendar.MONTH) + 1);
		return YYYYmiMM;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ YYYY-MM
	 * @param YYYYmiMM (YYYY-MM)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromYYYYmiMM(String YYYYmiMM, Locale toLocale) {
		int YYYY = Integer.parseInt(YYYYmiMM.substring(0, 4));
		int MM = Integer.parseInt(YYYYmiMM.substring(5, 7));

		Calendar calendar = Calendar.getInstance(toLocale);
		calendar.set(Calendar.YEAR, YYYY);
		calendar.set(Calendar.MONTH, MM - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	/**
	 * ดึงเวลาจาก Calendar ในรูปแบบ HH24:MI:SS
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getHH24clMIclSS(Calendar cal) {
		String HH24_MM_SS = "";
		HH24_MM_SS = convert2Digit(cal.get(Calendar.HOUR_OF_DAY));
		HH24_MM_SS += DELIMITER_COLON + convert2Digit(cal.get(Calendar.MINUTE));
		HH24_MM_SS += DELIMITER_COLON + convert2Digit(cal.get(Calendar.SECOND));
		return HH24_MM_SS;
	}

	/**
	 * ดึงเวลาจาก Calendar ในรูปแบบ HH24:MI
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getHH24clMI(Calendar cal) {
		String HH24clMI = "";
		HH24clMI = convert2Digit(cal.get(Calendar.HOUR_OF_DAY));
		HH24clMI += DELIMITER_COLON + convert2Digit(cal.get(Calendar.MINUTE));
		return HH24clMI;
	}

	/**
	 * ดึงเวลาจาก Calendar ในรูปแบบ HH24MISS
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getHH24MISS(Calendar cal) {
		String HH24MISS = "";
		HH24MISS = convert2Digit(cal.get(Calendar.HOUR_OF_DAY));
		HH24MISS += convert2Digit(cal.get(Calendar.MINUTE));
		HH24MISS += convert2Digit(cal.get(Calendar.SECOND));
		return HH24MISS;
	}

	/**
	 * ดึงเวลาจาก Calendar ในรูปแบบ HH24MM
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getHH24MI(Calendar cal) {
		String HH24MI = "";
		HH24MI = convert2Digit(cal.get(Calendar.HOUR_OF_DAY));
		HH24MI += convert2Digit(cal.get(Calendar.MINUTE));
		return HH24MI;
	}

	/**
	 * ดึงวันที่และเวลาจาก Calendar ในรูปแบบ DD/MM/YYYY HH24:MI:SS
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getDDslMMslYYYYblHH24clMIclSS(Calendar cal) {
		String DDslMMslYYYY = getDDslMMslYYYY(cal);
		String HH24clMIclSS = getHH24clMIclSS(cal);
		return DDslMMslYYYY + DELIMITER_BLANK + HH24clMIclSS;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ DD/MM/YYYY HH24:MI:SS
	 * @param DDslMMslYYYYblHH24clMIclSS (DD/MM/YYYY HH24:MI:SS)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromDDslMMslYYYYblHH24clMIclSS(String DDslMMslYYYYblHH24clMIclSS, Locale toLocale) {
		int HH = Integer.parseInt(DDslMMslYYYYblHH24clMIclSS.substring(11, 13));
		int MI = Integer.parseInt(DDslMMslYYYYblHH24clMIclSS.substring(14, 16));
		int SS = Integer.parseInt(DDslMMslYYYYblHH24clMIclSS.substring(17, 19));
		Calendar calendar = getCalendarFromDDslMMslYYYY(DDslMMslYYYYblHH24clMIclSS.substring(0, 10), toLocale);
		calendar.set(Calendar.HOUR_OF_DAY, HH);
		calendar.set(Calendar.MINUTE, MI);
		calendar.set(Calendar.SECOND, SS);
		return calendar;
	}

	/**
	 * ดึงวันที่และเวลาจาก Calendar ในรูปแบบ DD/MM/YYYY HH24:MI
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getDDslMMslYYYYblHH24clMI(Calendar cal) {
		String DDslMMslYYYY = getDDslMMslYYYY(cal);
		String HH24clMI = getHH24clMI(cal);
		return DDslMMslYYYY + DELIMITER_BLANK + HH24clMI;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ DD/MM/YYYY HH24:MI
	 * @param DDslMMslYYYYblHH24clMI (DD/MM/YYYY HH24:MI)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromDDslMMslYYYYblHH24clMI(String DDslMMslYYYYblHH24clMI, Locale toLocale) {
		int HH = Integer.parseInt(DDslMMslYYYYblHH24clMI.substring(11, 13));
		int MI = Integer.parseInt(DDslMMslYYYYblHH24clMI.substring(14, 16));
		Calendar calendar = getCalendarFromDDslMMslYYYY(DDslMMslYYYYblHH24clMI.substring(0, 10), toLocale);
		calendar.set(Calendar.HOUR_OF_DAY, HH);
		calendar.set(Calendar.MINUTE, MI);
		calendar.set(Calendar.SECOND, 0);
		return calendar;
	}

	/**
	 * ดึงวันที่และเวลาจาก Calendar ในรูปแบบ YYYYMMDDHH24MISS
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getYYYYMMDDHH24MISS(Calendar cal) {
		String YYYYMMDD = getYYYYMMDD(cal);
		String HH24MISS = getHH24MISS(cal);
		return YYYYMMDD + HH24MISS;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ YYYYMMDDHH24MISS
	 * @param YYYYMMDDHH24MISS (YYYYMMDDHH24MISS)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromYYYYMMDDHH24MISS(String YYYYMMDDHH24MISS, Locale toLocale) {
		int HH = Integer.parseInt(YYYYMMDDHH24MISS.substring(8, 10));
		int MI = Integer.parseInt(YYYYMMDDHH24MISS.substring(10, 12));
		int SS = Integer.parseInt(YYYYMMDDHH24MISS.substring(12, 14));
		Calendar calendar = getCalendarFromYYYYMMDD(YYYYMMDDHH24MISS.substring(0, 8), toLocale);
		calendar.set(Calendar.HOUR_OF_DAY, HH);
		calendar.set(Calendar.MINUTE, MI);
		calendar.set(Calendar.SECOND, SS);
		return calendar;
	}

	/**
	 * ดึงวันที่และเวลาจาก Calendar ในรูปแบบ YYYYMMDDHH24MI
	 * @param cal Calendar object
	 * @return
	 * @deprecated
	 */
	public static String getYYYYMMDDHH24MI(Calendar cal) {
		String YYYYMMDD = getYYYYMMDD(cal);
		String HH24MM = getHH24MI(cal);
		return YYYYMMDD + HH24MM;
	}

	/**
	 * ดึง Calendar จาก String ในรูปแบบ YYYYMMDDHH24MI
	 * @param YYYYMMDDHH24MI (YYYYMMDDHH24MI)
	 * @param toLocale Locale ของ Calendar
	 * @return
	 * @deprecated
	 */
	public static Calendar getCalendarFromYYYYMMDDHH24MI(String YYYYMMDDHH24MI, Locale toLocale) {
		int HH = Integer.parseInt(YYYYMMDDHH24MI.substring(8, 10));
		int MI = Integer.parseInt(YYYYMMDDHH24MI.substring(10, 12));
		Calendar calendar = getCalendarFromYYYYMMDD(YYYYMMDDHH24MI.substring(0, 8), toLocale);
		calendar.set(Calendar.HOUR_OF_DAY, HH);
		calendar.set(Calendar.MINUTE, MI);
		calendar.set(Calendar.SECOND, 0);
		return calendar;
	}

	/**
	 * แปลงรูปแบบจาก HH24MISS >> HH24:MI:SS
	 * @param HH24MISS (HH24MISS)
	 * @return
	 * @deprecated
	 */
	public static String getHH24clMIclSS(String HH24MISS) {
		String HH24 = HH24MISS.substring(0, 2);
		String MI = HH24MISS.substring(2, 4);
		String SS = HH24MISS.substring(4, 6);
		return HH24 + DELIMITER_COLON + MI + DELIMITER_COLON + SS;
	}

	/**
	 * แปลงรูปแบบจาก HH24:MI:SS >> HH24MISS
	 * @param HH24clMIclSS (HH24:MI:SS)
	 * @return
	 * @deprecated
	 */
	public static String getHH24MISS(String HH24clMIclSS) {
		String HH24 = HH24clMIclSS.substring(0, 2);
		String MI = HH24clMIclSS.substring(3, 5);
		String SS = HH24clMIclSS.substring(6, 8);
		return HH24 + MI + SS;
	}

	/**
	 * แปลงรูปแบบจาก HH24MI >> HH24:MI
	 * @param HH24MI (HH24MI)
	 * @return
	 * @deprecated
	 */
	public static String getHH24clMI(String HH24MI) {
		String HH24 = HH24MI.substring(0, 2);
		String MI = HH24MI.substring(2, 4);
		return HH24 + DELIMITER_COLON + MI;
	}

	/**
	 * แปลงรูปแบบจาก HH24:MI >> HH24MI
	 * @param HH24clMI (HH24:MI)
	 * @return
	 * @deprecated
	 */
	public static String getHH24MI(String HH24clMI) {
		String HH24 = HH24clMI.substring(0, 2);
		String MI = HH24clMI.substring(3, 5);
		return HH24 + MI;
	}

	/**
	 * แปลงรูปแบบจาก YYYYMMDD >> DD/MM/YYYY
	 * @param YYYYMMDD (YYYYMMDD)
	 * @return
	 * @deprecated
	 */
	public static String getDDslMMslYYYY(String YYYYMMDD) {
		String YYYY = YYYYMMDD.substring(0, 4);
		String MM = YYYYMMDD.substring(4, 6);
		String DD = YYYYMMDD.substring(6, 8);
		return DD + DELIMITER_SLASH + MM + DELIMITER_SLASH + YYYY;
	}

	/**
	 * แปลงรูปแบบจาก DD/MM/YYYY >> YYYYMMDD
	 * @param DDslMMslYYYY (DD/MM/YYYY)
	 * @return
	 * @deprecated
	 */
	public static String getYYYYMMDD(String DDslMMslYYYY) {
		String YYYY = DDslMMslYYYY.substring(6, 10);
		String MM = DDslMMslYYYY.substring(3, 5);
		String DD = DDslMMslYYYY.substring(0, 2);
		return YYYY + MM + DD;
	}

	/**
	 * แปลงรูปแบบจาก YYYYMMDDHH24MISS >> DD/MM/YYYY HH24:MI:SS
	 * @param YYYYMMDDHH24MISS (YYYYMMDDHH24MISS)
	 * @return
	 * @deprecated
	 */
	public static String getDDslMMslYYYYblHH24clMIclSS(String YYYYMMDDHH24MISS) {
		String DDslMMslYYYY = getDDslMMslYYYY(YYYYMMDDHH24MISS.substring(0, 8));
		String HH24clMIclSS = getHH24clMIclSS(YYYYMMDDHH24MISS.substring(8, 14));
		return DDslMMslYYYY + DELIMITER_BLANK + HH24clMIclSS;
	}

	/**
	 * แปลงรูปแบบจาก DD/MM/YYYY HH24:MI:SS >> YYYYMMDDHH24MISS
	 * @param DDslMMslYYYYblHH24clMIclSS (DD/MM/YYYY HH24:MI:SS)
	 * @return
	 * @deprecated
	 */
	public static String getYYYYMMDDHH24MISS(String DDslMMslYYYYblHH24clMIclSS) {
		String YYYYMMDD = getYYYYMMDD(DDslMMslYYYYblHH24clMIclSS.substring(0, 10));
		String HH24MISS = getHH24MISS(DDslMMslYYYYblHH24clMIclSS.substring(11, 19));
		return YYYYMMDD + HH24MISS;
	}

	/**
	 * แปลงรูปแบบจาก YYYYMMDDHH24MI >> DD/MM/YYYY HH24:MI
	 * @param YYYYMMDDHH24MI (YYYYMMDDHH24MI)
	 * @return
	 * @deprecated
	 */
	public static String getDDslMMslYYYYblHH24clMI(String YYYYMMDDHH24MI) {
		String DDslMMslYYYY = getDDslMMslYYYY(YYYYMMDDHH24MI.substring(0, 8));
		String HH24clMI = getHH24clMI(YYYYMMDDHH24MI.substring(8, 12));
		return DDslMMslYYYY + DELIMITER_BLANK + HH24clMI;
	}

	/**
	 * แปลงรูปแบบจาก DD/MM/YYYY HH24:MI >> YYYYMMDDHH24MI
	 * @param YYYYMMDDHH24MI (YYYYMMDDHH24MI)
	 * @return
	 * @deprecated
	 */
	public static String getYYYYMMDDHH24MI(String DDslMMslYYYYblHH24clMI) {
		String YYYYMMDD = getYYYYMMDD(DDslMMslYYYYblHH24clMI.substring(0, 10));
		String HH24MI = getHH24MI(DDslMMslYYYYblHH24clMI.substring(11, 16));
		return YYYYMMDD + HH24MI;
	}

	private static String paddingLeftDigit(int number, int digit) {
		StringBuilder numberBuilder = new StringBuilder();
		numberBuilder.append(number);
		for (int start = numberBuilder.length(); start < digit; start++) {
			numberBuilder.insert(0, 0);
		}
		return numberBuilder.toString();
	}
	
	private static String convert2Digit(int number) {
		String tempnumber = (100 + number) + "";
		return tempnumber.substring(1);
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ DD/MM/YYYY
	 *
	 * @param DDslMMslYYYY (DD/MM/YYYY)
	 *            - date time in string (31/01/2013 >> 31/01/2556)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getDDslMMslYYYYWithLocale(String DDslMMslYYYY, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromDDslMMslYYYY(DDslMMslYYYY, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getDDslMMslYYYY(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ MM/YYYY
	 *
	 * @param MMslYYYY (MM/YYYY)
	 *            - date time in string (01/2013 >> 01/2556)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getMMslYYYYWithLocale(String MMslYYYY, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromMMslYYYY(MMslYYYY, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getMMslYYYY(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ YYYY
	 *
	 * @param YYYY (YYYY)
	 *            - date time in string (2013 >> 2556)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getYYYYWithLocale(String YYYY, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromYYYY(YYYY, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getYYYY(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ YYYYMMDD
	 *
	 * @param YYYYMMDD (YYYYMMDD)
	 *            - date time in string (20140514 >> 25570514)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getYYYYMMDDWithLocale(String YYYYMMDD, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromYYYYMMDD(YYYYMMDD, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getYYYYMMDD(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ YYYYMM
	 *
	 * @param YYYYMM (YYYYMM)
	 *            - date time in string (201405 >> 255705)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getYYYYMMWithLocale(String YYYYMM, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromYYYYMM(YYYYMM, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getYYYYMM(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ YYYY-MM-DD
	 *
	 * @param YYYY-MM-DD (YYYY-MM-DD)
	 *            - date time in string (2014-05-14 >> 2557-05-14)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getYYYYmiMMmiDDWithLocale(String YYYYmiMMmiDD, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromYYYYmiMMmiDD(YYYYmiMMmiDD, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getYYYYmiMMmiDD(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ YYYY-MM
	 *
	 * @param YYYY-MM (YYYY-MM)
	 *            - date time in string (2014-05 >> 2557-05)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getYYYYmiMMWithLocale(String YYYYmiMM, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromYYYYmiMM(YYYYmiMM, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getYYYYmiMM(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ DD/MM/YYYY HH24:MI:SS
	 *
	 * @param DDslMMslYYYYblHH24clMIclSS
	 *            - date time in string (31/01/2013 23:59:59 >> 31/01/2556 23:59:59)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getDDslMMslYYYYblHH24clMIclSSWithLocale(String DDslMMslYYYYblHH24clMIclSS, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromDDslMMslYYYYblHH24clMIclSS(DDslMMslYYYYblHH24clMIclSS, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getDDslMMslYYYYblHH24clMIclSS(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ DD/MM/YYYY HH24:MI
	 *
	 * @param DDslMMslYYYYblHH24clMI
	 *            - date time in string (31/01/2013 23:59 >> 31/01/2556 23:59)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getDDslMMslYYYYblHH24clMIWithLocale(String DDslMMslYYYYblHH24clMI, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromDDslMMslYYYYblHH24clMI(DDslMMslYYYYblHH24clMI, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getDDslMMslYYYYblHH24clMI(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ YYYYMMDDHH24MISS
	 *
	 * @param YYYYMMDDHH24MISS
	 *            - date time in string (20130131235959 >> 25560131235959)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getYYYYMMDDHH24MISSWithLocale(String YYYYMMDDHH24MISS, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromYYYYMMDDHH24MISS(YYYYMMDDHH24MISS, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getYYYYMMDDHH24MISS(calendar);

		return dateString;
	}

	/**
	 * เปลื่ยน locale ของ date ในรูปแบบ YYYYMMDDHH24MI
	 *
	 * @param YYYYMMDDHH24MI
	 *            - date time in string (201301312459 >> 255601312459)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated
	 */
	public static String getYYYYMMDDHH24MIWithLocale(String YYYYMMDDHH24MI, Locale fromLocale, Locale toLocale) {

		String dateString;

		Calendar calendarTmp = getCalendarFromYYYYMMDDHH24MI(YYYYMMDDHH24MI, fromLocale);

		Calendar calendar = Calendar.getInstance(toLocale);

		calendar.setTimeInMillis(calendarTmp.getTimeInMillis());

		dateString = getYYYYMMDDHH24MI(calendar);

		return dateString;
	}


	/**
	 * เปลื่ยน locale และ format ของ date จากรูปแบบ YYYYMMDD >> DD/MM/YYYY
	 *
	 * @param YYYYMMDD
	 *            - date in string (20130131 >> 31/01/2556)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated           
	 */
	public static String getDDslMMslYYYYFromYYYYMMDDWithLocale(String YYYYMMDD, Locale fromLocale, Locale toLocale) {

		String dateString;

		// 20130131 >> 31/01/2013
		String dateStringTemp = getDDslMMslYYYY(YYYYMMDD);

		// 31/01/2013 >> 31/01/2556
		dateString = getDDslMMslYYYYWithLocale(dateStringTemp, fromLocale, toLocale);

		return dateString;
	}

	/**
	 * เปลื่ยน locale และ format ของ date จากรูปแบบ DD/MM/YYYY >> YYYYMMDD
	 *
	 * @param YYYYMMDD
	 *            - date in string (31/01/2013 >> 25560131)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated           
	 */
	public static String getYYYYMMDDFromDDslMMslYYYYWithLocale(String DDslMMslYYYY, Locale fromLocale, Locale toLocale) {

		String dateString;

		// 31/01/2013 >> 20130131
		String dateStringTemp = getYYYYMMDD(DDslMMslYYYY);

		// 20130131 >> 25560131
		dateString = getYYYYMMDDWithLocale(dateStringTemp, fromLocale, toLocale);

		return dateString;
	}

	/**
	 * เปลื่ยน locale และ format ของ date จากรูปแบบ YYYYMMDDHH24MISS >> DD/MM/YYYY HH24:MI:SS
	 *
	 * @param YYYYMMDD
	 *            - date in string (20130131235959 >> 31/01/2556 23:59:59)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated           
	 */
	public static String getDDslMMslYYYYblHH24clMIclSSFromYYYYMMDDHH24MISSWithLocale(String YYYYMMDDHH24MISS, Locale fromLocale, Locale toLocale) {

		String dateString;

		// 20130131235959 >> 31/01/2013 23:59:59
		String dateStringTemp = getDDslMMslYYYYblHH24clMIclSS(YYYYMMDDHH24MISS);

		// 31/01/2013 23:59:59 >> 31/01/2556 23:59:59
		dateString = getDDslMMslYYYYblHH24clMIclSSWithLocale(dateStringTemp, fromLocale, toLocale);

		return dateString;
	}

	/**
	 * เปลื่ยน locale และ format ของ date จากรูปแบบ DD/MM/YYYY HH24:MI:SS >> YYYYMMDDHH24MISS
	 *
	 * @param YYYYMMDD
	 *            - date in string (31/01/2556 23:59:59 >> 20130131235959)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated           
	 */
	public static String getYYYYMMDDHH24MISSFromDDslMMslYYYYblHH24clMIclSSWithLocale(String DDslMMslYYYYblHH24clMIclSS, Locale fromLocale, Locale toLocale) {

		String dateString;

		// 31/01/2013 23:59:59 >> 20130131235959
		String dateStringTemp = getYYYYMMDDHH24MISS(DDslMMslYYYYblHH24clMIclSS);

		// 20130131235959 >> 25560131235959
		dateString = getYYYYMMDDHH24MISSWithLocale(dateStringTemp, fromLocale, toLocale);

		return dateString;
	}


	/**
	 * เปลื่ยน locale และ format ของ date จากรูปแบบ YYYYMMDDHH24MI >> DD/MM/YYYY HH24:MI
	 *
	 * @param YYYYMMDD
	 *            - date in string (201301312359 >> 31/01/2556 23:59)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated           
	 */
	public static String getDDslMMslYYYYblHH24clMIFromYYYYMMDDHH24MIWithLocale(String YYYYMMDDHH24MI, Locale fromLocale, Locale toLocale) {

		String dateString;

		// 201301312359 >> 31/01/2013 23:59
		String dateStringTemp = getDDslMMslYYYYblHH24clMI(YYYYMMDDHH24MI);

		// 31/01/2013 23:59 >> 31/01/2556 23:59
		dateString = getDDslMMslYYYYblHH24clMIWithLocale(dateStringTemp, fromLocale, toLocale);

		return dateString;
	}

	/**
	 * เปลื่ยน locale และ format ของ date จากรูปแบบ DD/MM/YYYY HH24:MI >> YYYYMMDDHH24MI
	 *
	 * @param YYYYMMDD
	 *            - date in string (31/01/2556 23:59 >> 201301312359)
	 * @param fromLocale
	 *            - original locale
	 * @param toLocale
	 *            - new locale
	 * @deprecated           
	 */
	public static String getYYYYMMDDHH24MIFromDDslMMslYYYYblHH24clMIWithLocale(String DDslMMslYYYYblHH24clMI, Locale fromLocale, Locale toLocale) {

		String dateString;

		// 31/01/2013 23:59 >> 201301312359
		String dateStringTemp = getYYYYMMDDHH24MI(DDslMMslYYYYblHH24clMI);

		// 201301312359 >> 255601312359
		dateString = getYYYYMMDDHH24MIWithLocale(dateStringTemp, fromLocale, toLocale);

		return dateString;
	}
}