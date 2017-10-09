package util.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import util.log.DefaultLoggerName;

public class SessionUtil {

	private static Logger logger = Logger.getLogger(DefaultLoggerName.UTIL.getValue());
	
	/**
	 * สำหรับเก็บค่าลงใน session ผ่าน Action class เช่น การเรียกใช้ผ่าน Action ต่างๆ ของ struts
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value) {
		setAttribute(key, value);
	}

	/**
	 * สำหรับดึงค่าจาก session ผ่าน Action class เช่น การเรียกใช้ผ่าน Action ต่างๆ ของ struts
	 * @param key
	 * @param value
	 */
	public static Object get(String key) {
		return getAttribute(key);
	}

	/**
	 * สำหรับดึง parameter ที่ส่งมากับ request
	 * @param key
	 * @return
	 */
	public static String requestParameter(String key) {
		return requestParameter(ServletActionContext.getRequest(), key);
	}

	public static String requestParameter(HttpServletRequest request, String key) {
		String parameterValue = null;
		if (request != null) {
			parameterValue = request.getParameter(key);
		}
		return parameterValue;
	}
	
	/**
	 * สำหรับเก็บค่าลงใน session ผ่าน http request เช่น การเรียกใช้ผ่าน servlet class
	 * @param key
	 * @param value
	 */
	public static void setAttribute(String key, Object value) {
		setAttribute(ServletActionContext.getRequest(), key, value);
	}

	public static void setAttribute(HttpServletRequest request, String key, Object value) {
		if (request != null) {
			request.getSession().setAttribute(key, value);
		}
	}
	
	/**
	 * สำหรับเก็บค่าลงใน session ผ่าน http request เช่น การเรียกใช้ผ่าน servlet class
	 * @param key
	 */
	public static Object getAttribute(String key) {
		return getAttribute(ServletActionContext.getRequest(), key);
	}
	
	public static Object getAttribute(HttpServletRequest request, String key) {
		Object attributeValue = null;
		if (request != null) {
			attributeValue = request.getSession().getAttribute(key);
		}
		return attributeValue;
	}
	
	/**
	 * สำหรับดึงชื่อ context ของ web นั้น
	 * @param key
	 * @param value
	 */
	public static String getContextName() {
		return getContextName(ServletActionContext.getRequest());
	}
	
	public static String getContextName(HttpServletRequest request) {
		String contextPath = null;
		if (request != null) {
			contextPath = request.getContextPath();
		}
		return contextPath;
	}

	public static Map<String, Object> get() {
		return get(ServletActionContext.getRequest());
	}
	
	public static Map<String, Object> get(HttpServletRequest request) {
		Map<String, Object> mapObject = null;
		if (request != null) {
			ActionContext actionContext = ServletActionContext.getActionContext(request);
			if (actionContext != null) {
				mapObject = actionContext.getSession();
			}
		}
		return mapObject;
	}

	public static void remove(String key) {
		remove(ServletActionContext.getRequest(), key);
	}
	
	public static void remove(HttpServletRequest request, String key) {
		if (request != null) {
			ActionContext actionContext = ServletActionContext.getActionContext(request);
			if (actionContext != null) {
				actionContext.getSession().remove(key);
			}
		}
	}

	public static void removes() {
		Map<String, Object> mapObject = get();
		for (String key : mapObject.keySet()) {
			remove(key);
		}
	}

	public static void removesIgnore(String keyIgnore) {
		Map<String, Object> mapObject = get();
		for (String key : mapObject.keySet()) {
			if (keyIgnore.equals(key)) {
				continue;
			}
			remove(key);
		}
	}

	public static void removesIgnore(String[] keyIgnores) {
		Map<String, Object> mapObject = get();
		for (String key : mapObject.keySet()) {
			if (haveKeyIn(key, keyIgnores)) {
				continue;
			}
			remove(key);
		}
	}

	private static boolean haveKeyIn(String key, String[] keyIgnores) {
		boolean haveKey = false;
		for (String keyIgnore : keyIgnores) {
			if (key.equals(keyIgnore)) {
				haveKey = true;
				break;
			}
		}
		return haveKey;
	}

	public static String getId() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		return session.getId();
	}

	public static void setTimeout(int timeout) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setMaxInactiveInterval(timeout);
	}

	public static long getCreationTime() {
		return getCreationTime(ServletActionContext.getRequest());
	}
	
	public static long getCreationTime(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return session.getCreationTime();
	}

	public static long getLastAccessedTime() {
		return getLastAccessedTime(ServletActionContext.getRequest());
	}
	
	public static long getLastAccessedTime(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return session.getLastAccessedTime();
	}

	public static String getLocalIPAddress() {
		return getLocalIPAddress(ServletActionContext.getRequest());
	}
	
	public static String getLocalIPAddress(HttpServletRequest request) {
		String localAddr = request.getLocalAddr();
		return localAddr;
	}
	
	public static String getRemoteIPAddress() {
		HttpServletRequest request = (HttpServletRequest) ServletActionContext.getRequest();
		return getRemoteIPAddress(request);
	}
	
	public static String getRemoteIPAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader(HTTPHeaderParameter.X_FORWARDED_FOR.getValue());
		try {
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.PROXY_CLIENT_IPADDRESS.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.WL_PROXY_CLIENT_IPADDRESS.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.HTTP_CLIENT_IPADDRESS.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.HTTP_X_FORWARDED_FOR.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
			}
		} catch (Exception e) {
			logger.error(e);
		}
		
		return ipAddress;
	}
}
