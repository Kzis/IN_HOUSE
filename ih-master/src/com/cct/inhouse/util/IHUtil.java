package com.cct.inhouse.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.struts2.json.JSONUtil;

import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemRequest;
import com.cct.common.CommonUser;
import com.cct.enums.CharacterEncoding;
import com.cct.enums.SelectItemParameter;
import com.cct.enums.UserSessionAttribute;
import com.cct.exception.ServerValidateException;
import com.cct.inhouse.core.config.parameter.domain.Parameter;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;

import util.bundle.BundleUtil;
import util.calendar.CalendarUtil;
import util.io.IOUtil;
import util.log.DefaultLogUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

public class IHUtil {

	private static final String REQUEST_METHOD = "POST";
	private static final int CONNECTION_TIMEOUT = 60000;
	private static final int READING_TIMEOUT = 60000;

	public static String requestSelectItem(String selectItemUrl, CommonSelectItemRequest csiRequest) throws Exception {
		StringBuffer responseBuilder = new StringBuffer();
		
		StringBuilder parameterBuilder = new StringBuilder();
		parameterBuilder.append(SelectItemParameter.JSON_REQUEST.getValue() + StringDelimeter.EQUAL.getValue());
		parameterBuilder.append(JSONUtil.serialize(csiRequest, null, null, false, true, true));
		
		URL url = new URL(selectItemUrl);
		HttpURLConnection httpURLConn = (HttpURLConnection) url.openConnection();
		httpURLConn.setRequestMethod(REQUEST_METHOD);
		httpURLConn.setConnectTimeout(CONNECTION_TIMEOUT);
		httpURLConn.setReadTimeout(READING_TIMEOUT);
		httpURLConn.setRequestProperty("Accept-Language", CharacterEncoding.UTF8.getValue());
		httpURLConn.setDoOutput(true);
		
		
		OutputStreamWriter outputStreamWriter = null;
		try {
			DefaultLogUtil.SELECTOR.debug(parameterBuilder.toString());
			outputStreamWriter = new OutputStreamWriter(httpURLConn.getOutputStream());
			outputStreamWriter.write(parameterBuilder.toString());
			outputStreamWriter.flush();
		} catch (Exception e) {
			DefaultLogUtil.SELECTOR.error(e);
		} finally {
			IOUtil.close(outputStreamWriter);
		}


		int responseCode = 0;
		try {
			responseCode = httpURLConn.getResponseCode();
		} catch (Exception e) {
			DefaultLogUtil.SELECTOR.error(e);
		}
		DefaultLogUtil.SELECTOR.debug("responseCode: " + responseCode);
		if (responseCode == 200) {
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			
			try {
			
				inputStreamReader = new InputStreamReader(httpURLConn.getInputStream(), CharacterEncoding.UTF8.getValue());
				bufferedReader = new BufferedReader(inputStreamReader);

				String inputLine;
				while ((inputLine = bufferedReader.readLine()) != null) {
					responseBuilder.append(inputLine);
				}
				
				if (responseBuilder.toString().equals("[]")) {
					responseBuilder.delete(0, responseBuilder.length());
				}
			} catch (Exception e) {
				DefaultLogUtil.SELECTOR.error(e);
			} finally {
				IOUtil.close(bufferedReader);
				IOUtil.close(inputStreamReader);
			}
		}
		
		return responseBuilder.toString();
	}
	
	/**
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น
	 * 31/12/2015 23:59:59 > 31/12/2558 23:59:59
	 *
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForDisplayHHMMSS(String dateValue) throws Exception {
		String toFormat = getParameter().getDateFormat().getForDisplayHHMMSS();
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return StringDelimeter.EMPTY.getValue();
		} else {
			if (dateValue.length() == getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, getParameter().getApplication().getDatabaseLocale(), toFormat, getParameter()
				.getApplication().getDatetimeLocale());
	}
	
	/**
	 * แปลงวันที่ที่ได้รับจาก database เป็นวันที่ที่ใช้ในการแสดงบนหน้าเว็บ เช่น
	 * 31/12/2015 > 31/12/2558
	 *
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDateTimeForDisplay(String dateValue) throws Exception {
		String toFormat = getParameter().getDateFormat().getForDisplay();
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return StringDelimeter.EMPTY.getValue();
		} else {
			if (dateValue.length() == getParameter().getDateFormat().getForDatabaseSelect().length()) {
				fromFormat = getParameter().getDateFormat().getForDatabaseSelect();
			} else if (dateValue.length() == getParameter().getDateFormat().getForDatabaseSelectHHMMSS().length()) {
				fromFormat = getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, getParameter().getApplication().getDatabaseLocale(), toFormat, getParameter()
				.getApplication().getDatetimeLocale());
	}
	
	/**
	 * แปลงวันที่ที่ได้รับจากหน้าจอ เป็นวันที่ที่ใช้ในการ insert เช่น <br>
	 * 28/02/2560 23:59:59 > 2017-02-28 23:59:59
	 *
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static String convertDateTimeForDatabase(String dateValue) throws Exception {
		String toFormat = getParameter().getDateFormat().getForDatabaseInsertHHMMSS();
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return StringDelimeter.EMPTY.getValue();
		} else {
			if (dateValue.length() == getParameter().getDateFormat().getForDisplay().length()) {
				fromFormat = getParameter().getDateFormat().getForDisplay();
			} else if (dateValue.length() == getParameter().getDateFormat().getForDisplayHHMMSS().length()) {
				fromFormat = getParameter().getDateFormat().getForDisplayHHMMSS();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, getParameter().getApplication().getDatetimeLocale(), toFormat, getParameter()
				.getApplication().getDatabaseLocale());
	}
	
	/**
	 * แปลงวันที่ที่ได้รับจาก datepicker เป็นวันที่ที่ใช้ในการ insert เช่น <br>
	 * 28/02/2560 23:59:59 > 2017-02-28 23:59:59
	 *
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static String convertDatePickerForDatabase(String dateValue) throws Exception {
		String toFormat = getParameter().getDateFormat().getForDatabaseInsertHHMMSS();
		String fromFormat = null;
		if ((dateValue == null) || dateValue.isEmpty()) {
			return StringDelimeter.EMPTY.getValue();
		} else {
			if (dateValue.length() == getParameter().getDateFormat().getForDisplay().length()) {
				fromFormat = getParameter().getDateFormat().getForDisplay();
			} else if (dateValue.length() == getParameter().getDateFormat().getForDisplayHHMMSS().length()) {
				fromFormat = getParameter().getDateFormat().getForDisplayHHMMSS();
			}
		}
		return CalendarUtil.convertDateString(dateValue, fromFormat, getParameter().getApplication().getDatabaseLocale(), toFormat, getParameter()
				.getApplication().getDatabaseLocale());
	}
	
	/**
	 * ใช้สำหรับแปลงค่า String of long ให้เป็น Long เพื่อใช้ในการ execute query
	 *
	 * @param value
	 * @return
	 */
	public static Long convertLongValue(String value) {
		if ((value == null) || value.trim().isEmpty()) {
			return null;
		} else {
			return Long.parseLong(value);
		}
	}
	
	/**
	 * สำหรับดึง CommonUser จาก session (ใช้แทนการดึงผ่าน CommonAction)
	 * @return
	 */
	public static CommonUser getUser() {
		return (CommonUser) SessionUtil.get(UserSessionAttribute.DEFAULT.getValue());
	}
	
	public static Parameter getParameter() {
		return ParameterConfig.getParameter();
	}
	
	public static Map<String, String> getMapContenType() {
		return ParameterConfig.getMapContenType();
	}
	
	public static Map<Locale, Map<String, List<CommonSelectItem>>> getMapGlobalData() {
		return ParameterConfig.getMapGlobalData();
	}
	
	public static boolean isDefaultUserId(String id) {
		boolean isDefaultUserId = false;
		String defaultUserIds = getParameter().getApplication().getDefaultSecUserIds();
		if (defaultUserIds != null) {
			String[] defaultUserIdsArray = defaultUserIds.split(StringDelimeter.COMMA.getValue());
			for (String userId : defaultUserIdsArray) {
				if (userId.equals(id)) {
					isDefaultUserId = true;
					break;
					
				}
			}
		}
		return isDefaultUserId;
	}
	
	/**
	 * Check validate require
	 * @param object
	 * @param locale
	 * @throws ServerValidateException
	 */
	public static void checkRequire(String object,Locale locale) throws ServerValidateException {
		ResourceBundle bundle = null;
		try {
			bundle = BundleUtil.getBundle("resources.bundle.common.MessageAlert", locale);
		} catch (Exception e) {
			DefaultLogUtil.UTIL.error("", e);
		}
		
		if(!IHLogicValidate.checkRequire(object)){
			throw new ServerValidateException(bundle.getString("10003").replaceAll("xxx", ""));
		}
	}
}
