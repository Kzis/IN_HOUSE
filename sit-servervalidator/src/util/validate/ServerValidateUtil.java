package util.validate;

import java.util.Calendar;
import java.util.Locale;

import util.calendar.CalendarUtil;
import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.date.DateUtil;
import util.log.DefaultLogUtil;
import util.string.StringDelimeter;

import com.cct.core.validate.domain.ConditionType;
import com.cct.core.validate.domain.ValidateType;
import com.cct.core.validate.domain.ValidatorConstant;
import com.cct.database.CommonConnectionProvider;
import com.cct.exception.DefaultExceptionMessage;
import com.cct.exception.ServerValidateException;
import com.opensymphony.xwork2.ActionSupport;

public class ServerValidateUtil {
	
	/**
	 * สำหรับสร้าง ServerValidateException
	 * @param caption
	 * @param locale
	 * @param code
	 * @throws ServerValidateException
	 */
	public static void createServerValidateException(String caption, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		String messageException = createMessage(caption, locale, actionSupport);
		throw new ServerValidateException(messageException);
	}
	
	/**
	 * สำหรับให้ code ส่วนที่ไม่ใช้ Action เรียกใช้งาน เนื่องจากไม่มี ActionSupport
	 * @param messageException
	 * @throws ServerValidateException
	 */
	public static void createServerValidateException(String messageException) throws ServerValidateException {
		throw new ServerValidateException(messageException);
	}
	
	/**
	 * สำหรับสร้าง ServerValidateException
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void createServerValidateException(Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		createServerValidateException(StringDelimeter.EMPTY.getValue(), locale, actionSupport);
	}
	
	/**
	 * สร้าง message เพื่อแสดงบนหน้า page
	 * @param caption
	 * @param locale
	 * @param actionSupport
	 * @return
	 */
	public static String createMessage(String caption, Locale locale, ActionSupport actionSupport) {
		try {
			if (caption == null || caption.isEmpty()) {
				return getMessage(DefaultExceptionMessage.VALIDATOR_DEFAULT_MESSAGE, actionSupport);
			} else {
				// เป็น bundle
				String captionBundle = getMessage(caption, actionSupport);
				String captionFormat = getMessage(DefaultExceptionMessage.VALIDATOR_FORMAT_MESSAGE, actionSupport);
				return captionFormat.replaceAll("xxx", captionBundle);	
			}
		} catch (Exception e) {
			DefaultLogUtil.SERVER_VALIDATION.error(e);
		}
		return caption;
	}
	
	/**
	 * สร้างหัวข้อ error เพื่อแสดงบนหน้า page
	 * @param locale
	 * @param actionSupport
	 * @return
	 */
	public static String createSubject(Locale locale, ActionSupport actionSupport) {
		String caption = StringDelimeter.EMPTY.getValue();
		try {
			caption = getMessage(DefaultExceptionMessage.SERVER_VALIDATE, actionSupport);
		} catch (Exception e) {
			DefaultLogUtil.SERVER_VALIDATION.error(e);
		}
		return caption;
	}
	
	/**
	 * ดึงค่า message จาก action support
	 * @param message
	 * @param actionSupport
	 * @return
	 */
	public static String getMessage(String message, ActionSupport actionSupport) {
		if (message.startsWith(StringDelimeter.SHARP.getValue())) {
			// กรณีใช้ Bundle จาก sturts
			return actionSupport.getText(message.substring(1));
		} else {
			return message;
		}
	}
	
	/**
	 * ValidateType.requireInput
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkRequire(Object object, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		if (object == null || object.toString().trim().isEmpty()) {
			createServerValidateException(ValidateType.requireInput, locale, actionSupport);
		}
	}
	
	/**
	 * ตรวจสอบว่ามีค่า สำหรับ validate ไหม<br>
	 * return true=มีค่าสามารถ validate ได้<br>
	 * return false=ไม่มีค่าให้ validate<br>
	 * @param object
	 * @param locale
	 * @return
	 * @throws ServerValidateException
	 */
	public static boolean isHaveValue(Object object) throws ServerValidateException {
		boolean isHaveValue = true;
		if (object == null || object.toString().trim().isEmpty()) {
			isHaveValue = false;
		}
		return isHaveValue;
	}
	
	/**
	 * ValidateType.intValue
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkIntValue(Object object, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		try {
			if (isHaveValue(object)) {
				Integer.valueOf(object.toString());	
			}
		} catch (Exception e) {
			createServerValidateException(ValidateType.intValue, locale, actionSupport);
		}
	}
	
	/**
	 * ValidateType.longValue
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkLongValue(Object object, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		try {
			if (isHaveValue(object)) {
				Long.valueOf(object.toString());	
			}
		} catch (Exception e) {
			createServerValidateException(ValidateType.longValue, locale, actionSupport);
		}
	}
	
	/**
	 * ValidateType.dateValue
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkDateValue(Object object, String format, Locale dbLocale, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		try {
			if (isHaveValue(object)) {
				Calendar calendarDate = CalendarUtil.getCalendarFromDateString(object.toString(), format, dbLocale);
				String stringDate = CalendarUtil.getDateStringFromCalendar(calendarDate, format);
				if (!object.equals(stringDate)) {
					// แปลงเป็นวันที่แล้วดึงกลับมาไม่ตรงกัน
					createServerValidateException(ValidateType.dateValue, locale, actionSupport);
				}
			}
		} catch (ServerValidateException sve) {
			throw sve;
		} catch (Exception e) {
			createServerValidateException(locale, actionSupport);
		}
	}
	
	/**
	 * ValidateType.timeValue
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkTimeValue(Object object, String format, Locale dbLocale, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		try {
			if (isHaveValue(object)) {
				Calendar calendarTime = CalendarUtil.getCalendarFromDateString(object.toString(), format, dbLocale);
				String stringTime = CalendarUtil.getDateStringFromCalendar(calendarTime, format);
				if (!object.equals(stringTime)) {
					// แปลงเป็นเวลาแล้วดึงกลับมาไม่ตรงกัน
					createServerValidateException(ValidateType.timeValue, locale, actionSupport);
				}
			}
		} catch (ServerValidateException sve) {
			throw sve;
		} catch (Exception e) {
			createServerValidateException(locale, actionSupport);
		}
	}
	
	/**
	 * ValidateType.stringLength
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkStringLength(Object object, Integer minLength, Integer maxLength, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		try {
			if (isHaveValue(object)) {
				String stringObject = object.toString();
				int currentLength = stringObject.length();
				int min = minLength == null ? 0 : minLength;
				int max = maxLength == null ? Integer.MAX_VALUE : maxLength;
				
				if ((min <= currentLength) && (currentLength <= max)) {
					
				} else {
					createServerValidateException(ValidateType.stringLength, locale, actionSupport);
				}
			}
		} catch (ServerValidateException sve) {
			throw sve;
		} catch (Exception e) {
			createServerValidateException(locale, actionSupport);
		}
	}
	
	/**
	 * ValidateType.dayRangeLimit
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkDayRangeLimit(String startDate, String endDate, String format, Integer rangeLimit, Locale dbLocale, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		try {
			if (isHaveValue(startDate) && isHaveValue(endDate)) {
				// ต้องผ่านการตรวจสอบ dateValue มาก่อน
				checkDateValue(startDate, format, dbLocale, locale, actionSupport);
				checkDateValue(endDate, format, dbLocale, locale, actionSupport);
				
				int limit = rangeLimit == null ? 1 : rangeLimit; 
				long startInMillis = CalendarUtil.getCalendarFromDateString(startDate, format, dbLocale).getTimeInMillis();
				long endInMillis = CalendarUtil.getCalendarFromDateString(endDate, format, dbLocale).getTimeInMillis();
				
				long count = 0;
				long diffInMillis = 0;
				if (startInMillis <= endInMillis) {
					diffInMillis = endInMillis - startInMillis;
				} else if (startInMillis > endInMillis) {
					diffInMillis = startInMillis - endInMillis;
				}
				count = (diffInMillis / ValidatorConstant.ONE_DAY_IN_MILLIS) + 1;
				
				if (count > limit) {
					createServerValidateException(ValidateType.dayRangeLimit, locale, actionSupport);
				}
			} else {
				createServerValidateException(ValidateType.dayRangeLimit, locale, actionSupport);
			}
		} catch (ServerValidateException sve) {
			throw sve;
		} catch (Exception e) {
			createServerValidateException(locale, actionSupport);
		}
	}
	
	/**
	 * ValidateType.emailValue
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkEmailValue(Object object, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		try {
			if (isHaveValue(object)) {
				String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
				java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
				java.util.regex.Matcher m = p.matcher(object.toString());
				if (!m.matches()) {
					createServerValidateException(ValidateType.emailValue, locale,  actionSupport);
				}
			}
		} catch (ServerValidateException sve) {
			throw sve;
		} catch (Exception e) {
			createServerValidateException(locale, actionSupport);
		}
	}
	
	/**
	 * ValidateType.dateBeforeLimit
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkDateBeforeLimit(Object object, String conditionType, Integer rangeLimit, Locale dbLocale, Locale locale, String format, String dbLookup, String dbFormat, ActionSupport actionSupport) throws ServerValidateException {
		try {
			if (isHaveValue(object)) {
				// ต้องผ่านการตรวจสอบ dateValue มาก่อน
				checkDateValue(object, format, dbLocale, locale, actionSupport);
				
				String currentDate = null;
				CCTConnection conn = null;
				try {
					// หาวันที่ปัจจุบันจากฐาน
					conn = new CommonConnectionProvider().getConnection(conn, dbLookup);
					currentDate = DateUtil.getCurrentDateDB(conn.getConn(), dbFormat, conn.getDbType());
				} catch (Exception e) {
					DefaultLogUtil.SERVER_VALIDATION.error(e);
					throw e;
				} finally {
					CCTConnectionUtil.close(conn);
				}
				DefaultLogUtil.SERVER_VALIDATION.debug("currentDate: " + currentDate);
				
				if (currentDate == null) {
					// กรณีไม่ได้วันที่จากฐานข้อมูลมา
					createServerValidateException(ValidateType.dateBeforeLimit, locale, actionSupport);
				} else {
					// เปรียบเทียบช่วงวัน เดือน ปี
					int limit = rangeLimit == null ? 1 : rangeLimit; 
					String cString = conditionType == null ? ConditionType.M.getType() : conditionType;
					Calendar selectCalendar = CalendarUtil.getCalendarFromDateString(object.toString(), format, dbLocale);
					Calendar currentCalendar = CalendarUtil.getCalendarFromDateString(currentDate, format, dbLocale);
					ConditionType cType = ConditionType.valueOf(cString);
					if (cType == null) {
						// กำหนดประเภทผิด
						createServerValidateException(ValidateType.dateBeforeLimit, locale, actionSupport);
					} else if (cType.equals(ConditionType.D)){
						currentCalendar.add(Calendar.DAY_OF_MONTH, -limit);
					} else if (cType.equals(ConditionType.M)){
						currentCalendar.add(Calendar.MONTH, -limit); 
					} else if (cType.equals(ConditionType.Y)){
						currentCalendar.add(Calendar.YEAR, -limit); 
					}
					
					String cc = CalendarUtil.getDateStringFromCalendar(currentCalendar, format);
					String ss = CalendarUtil.getDateStringFromCalendar(selectCalendar, format);
					DefaultLogUtil.SERVER_VALIDATION.debug("current: " + cc);
					DefaultLogUtil.SERVER_VALIDATION.debug("select: " + ss);
					
					// เปรียบเทียบ
					if (currentCalendar.before(selectCalendar) || currentCalendar.equals(selectCalendar)) {
						// อยู่ในขอบเขตที่กำหนด
					} else {
						createServerValidateException(ValidateType.dateBeforeLimit, locale, actionSupport);
					}
				}
			}
		} catch (ServerValidateException sve) {
			// DefaultLogUtil.SERVER_VALIDATION.error(sve);
			throw sve;
		} catch (Exception e) {
			// DefaultLogUtil.SERVER_VALIDATION.error(e);
			createServerValidateException(locale, actionSupport);
		}
	}
	
	/**
	 * ValidateType.idCardValue
	 * @param object
	 * @param locale
	 * @param actionSupport
	 * @throws ServerValidateException
	 */
	public static void checkIdCardValue(Object object, Locale locale, ActionSupport actionSupport) throws ServerValidateException {
		try {
			final int ID_CARD_LENGTH = 13;
			if (isHaveValue(object)) {
				// Check number format
				checkLongValue(object, locale, actionSupport);
				
				// Check id card length
				checkStringLength(object, ID_CARD_LENGTH, ID_CARD_LENGTH, locale, actionSupport);
				
			}
			
		} catch (ServerValidateException sve) {
			throw sve;
		} catch (Exception e) {
			createServerValidateException(locale, actionSupport);
		}
	}
}
