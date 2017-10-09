package com.cct.common;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cct.enums.ActionMaxExceedType;
import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionResultType;
import com.cct.enums.UserSessionAttribute;
import com.opensymphony.xwork2.ActionSupport;

import util.log.DefaultLogUtil;
import util.string.StringDelimeter;
import util.string.StringUtil;
import util.web.SessionUtil;

public abstract class CommonAction extends ActionSupport implements Serializable {

	private static final long serialVersionUID = 2843485154046138037L;

	private Logger logger = loggerInititial();
	public abstract Logger loggerInititial();

	private String P_CODE;
	private String F_CODE;

	private boolean useAddMenu = true;
	private boolean useProfileMenu = true;
	private boolean useHomeMenu = true;
	private boolean useRefreshMenu = true;
	private String alertMaxExceed = ActionMaxExceedType.NORMAL.getType();

	public static final int LIMIT_LINE_ERROR = 10;
	public static final String MESSAGE = "message";
	public static final String MESSAGE_ALERT = "message_alert";

	/**
	 * @Description: Constuctor for load user and locale
	 */
	public CommonAction() {
		getLogger().debug(StringDelimeter.EMPTY.getValue());

		if (SessionUtil.get(MESSAGE) == null && SessionUtil.get(MESSAGE_ALERT) == null) {
			clearMessages();

		} else if (SessionUtil.get(MESSAGE) != null) {
			clearMessages();
			SessionUtil.remove(MESSAGE_ALERT);

			Object object = SessionUtil.get(MESSAGE);
			if ((object instanceof String[]) == true) {
				String[] messages = (String[]) object;
				for (String message : messages) {
					addActionMessage(message);
				}
			} else {
				addActionMessage(object.toString());
			}
			SessionUtil.remove(MESSAGE);
		} else if (SessionUtil.get(MESSAGE_ALERT) != null) {
			clearMessages();
			Object object = SessionUtil.get(MESSAGE_ALERT);
			addActionMessage(object.toString());
			SessionUtil.remove(MESSAGE_ALERT);
		}
	}

	/**
	 * ดึง user จาก session
	 *
	 * @return
	 */
	public static CommonUser getCommonUser() {
		return (CommonUser) SessionUtil.get(UserSessionAttribute.DEFAULT.getValue());
	}

	public void clearSearchCriteria(String className) {
		try {
			Map<String, Object> sessions = SessionUtil.get();
			for (String key : sessions.keySet()) {
				getLogger().debug("Key :- " + key);
				getLogger().debug("Value :- " + sessions.get(key));
				getLogger().debug("Class :- [" + sessions.get(key).getClass().getName() + " == " + className + "]");
				if (sessions.get(key).getClass().getName().equals(className)) {
					getLogger().debug("Remove :- [" + key + "]");
					SessionUtil.remove(key);
				}
			}
			getLogger().debug("------");
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	public static String getErrorMessage(Exception e) {
		String error = e.getMessage() + "<br/>";
		for (int i = 0; i < LIMIT_LINE_ERROR; i++) {
			if (e.getStackTrace().length <= i) {
				break;
			} else {
				error += e.getStackTrace()[i] + "<br/>";
			}
		}
		return error;
	}

	public void setMessage(ActionMessageType type, String subject, ActionResultType resultType) {
		setMessage(type, subject, null, resultType);
	}

	public void setMessageAlert(String message, ActionResultType resultType) {

		if (resultType.equals(ActionResultType.CHAIN)) {
			SessionUtil.put(MESSAGE_ALERT, message);
		} else {
			SessionUtil.remove(MESSAGE_ALERT);
			clearMessages();
			addActionMessage(message);
		}
	}

	public void setMessage(ActionMessageType type, String subject, String detail, ActionResultType resultType) {
		
		if (subject == null) {
			subject = StringDelimeter.EMPTY.getValue();
		}
		subject = StringUtil.escapeJavascript(subject);
		
		if (detail == null) {
			detail = StringDelimeter.EMPTY.getValue();
		}
		detail = StringUtil.escapeJavascript(detail);
		
		if (resultType.equals(ActionResultType.CHAIN)) {
			String[] messages = { type.getType(), subject, detail };
			SessionUtil.put(MESSAGE, messages);
		} else {
			clearMessagesException();
			addActionMessage(type.getType());
			addActionMessage(subject);
			addActionMessage(detail);	
		}
	}

	public void clearMessagesException() {
		SessionUtil.remove(MESSAGE);
		clearMessages();
	}

	public String getP_CODE() {
		return P_CODE;
	}

	public void setP_CODE(String p_CODE) {
		P_CODE = p_CODE;
	}

	public String getF_CODE() {
		return F_CODE;
	}

	public void setF_CODE(String f_CODE) {
		F_CODE = f_CODE;
	}

	public boolean isUseAddMenu() {
		return useAddMenu;
	}

	public void setUseAddMenu(boolean useAddMenu) {
		this.useAddMenu = useAddMenu;
	}

	public boolean isUseProfileMenu() {
		return useProfileMenu;
	}

	public void setUseProfileMenu(boolean useProfileMenu) {
		this.useProfileMenu = useProfileMenu;
	}

	public boolean isUseHomeMenu() {
		return useHomeMenu;
	}

	public void setUseHomeMenu(boolean useHomeMenu) {
		this.useHomeMenu = useHomeMenu;
	}

	public boolean isUseRefreshMenu() {
		return useRefreshMenu;
	}

	public void setUseRefreshMenu(boolean useRefreshMenu) {
		this.useRefreshMenu = useRefreshMenu;
	}

	public void exportFile(HttpServletRequest request, HttpServletResponse response, byte[] bytes, String fileName, String contentType) {
		getLogger().debug(StringDelimeter.EMPTY.getValue());
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + getContentDisposition(request, fileName));
			response.setContentType(contentType);
			response.setContentLength(bytes.length);
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	public String getContentDisposition(HttpServletRequest request, String fileName) throws Exception {
		String disposition = "";
		String userAgent = request.getHeader("user-agent");
		boolean isInternetExplorer = (userAgent.indexOf("MSIE") > -1);

		try {
			byte[] fileNameBytes = fileName.getBytes((isInternetExplorer) ? ("tis-620") : ("utf-8"));
			String dispositionFileName = "";
			for (byte b : fileNameBytes)
				dispositionFileName += (char) (b & 0xff);

			disposition = "\"" + dispositionFileName + "\"";
		} catch (Exception e) {
			throw e;
		}
		return disposition;
	}

	public String getAlertMaxExceed() {
		return alertMaxExceed;
	}

	public void setAlertMaxExceed(String alertMaxExceed) {
		this.alertMaxExceed = alertMaxExceed;
	}

	public String getUserIdFromSession() {
		String userId = null;
		if (getCommonUser() != null) {
			userId = getCommonUser().getId();
		}
		return userId;
	}

	/**
	 * จัดการ message การตรวจสอบ validate
	 * @param message
	 * @return
	 */
	public String manageMessage(String message){

		String msg = "";
		String msgArr[] = null;
		try{
			if(StringUtil.stringToNull(message) != null){
				msgArr = message.split(":");
			}

			for(String m : msgArr){
				msg += m + "<br/>";
			}

		}catch (Exception e){
			getLogger().error(e);
		}
		return msg;

	}
	
	public Logger getLogger() {
		if (logger == null) {
			return  DefaultLogUtil.COMMON;
		} else {
			return logger;
		}
	}
	
}