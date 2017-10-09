package com.cct.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cct.enums.CharacterEncoding;
import com.cct.enums.LanguageSessionAttribute;
import com.cct.enums.UserSessionAttribute;

import util.database.CCTConnection;
import util.log.DefaultLoggerName;
import util.web.SessionUtil;

public abstract class CommonSelectItemServlet extends HttpServlet {

	private static final long serialVersionUID = -2018123156904737038L;
	
	private Logger logger = Logger.getLogger(DefaultLoggerName.SELECTOR.getValue());
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setCharacterEncoding(request, CharacterEncoding.UTF8.getValue());
		initSelectItem(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setCharacterEncoding(request, CharacterEncoding.UTF8.getValue());
		initSelectItem(request, response);
	}

	/**
	 * init parameter ก่อน search
	 * 
	 * @param request
	 * @param response
	 */
	public abstract void initSelectItem(HttpServletRequest request, HttpServletResponse response);

	/**
	 * เชื่อมต่อ database
	 * @return
	 * @throws Exception
	 */
	public abstract CCTConnection getConection() throws Exception;
	
	/**
	 * ตรวจสอบผู้ใช้ถูกต้องหรือไม่<br>
	 * return true เมื่อตรวจสอบไม่ผ่าน , return false เมื่อตรวจสอบถูกต้อง
	 * @param commonUser
	 * @return
	 * @throws Exception
	 */
	public abstract boolean invalidateUser(CCTConnection conn, CommonUser commonUser) throws Exception;
	
	public CommonUser getCommonUser(HttpServletRequest request) {
		CommonUser commonUser = null;
		try {
			Object common = SessionUtil.getAttribute(request, UserSessionAttribute.DEFAULT.getValue());
			if (common != null) {
				if (common instanceof String) {
					commonUser = new CommonUser();
					commonUser.setId(common.toString());
				} else if (common instanceof CommonUser) {
					commonUser = (CommonUser) common;
				}
			} 
		} catch (Exception e) {
			getLogger().error(e);
		}
		return commonUser;
	}
	
	public Locale getLocale(HttpServletRequest request) {
		Locale locale = null;
		try {
			/**
			Object common = SessionUtil.get(LanguageSessionAttribute.DEFAULT.getValue());
			**/
			Object common = SessionUtil.getAttribute(request, LanguageSessionAttribute.DEFAULT.getValue());
			if (common != null) {
				if (common instanceof String) {
					String localeString = common.toString();
					locale = new Locale(localeString.toLowerCase(), localeString.toUpperCase());
				} else if (common instanceof Locale) {
					locale = (Locale) common;
				}
			} 
		} catch (Exception e) {
			getLogger().error(e);
		}
		return locale;
	}

	public void setCharacterEncoding(HttpServletRequest request, String characterEncoding) {
		try {
			request.setCharacterEncoding(characterEncoding);
		} catch (UnsupportedEncodingException e) {
			getLogger().error(e);
		}
	}
	
	public Logger getLogger() {
		return logger;
	}
}
