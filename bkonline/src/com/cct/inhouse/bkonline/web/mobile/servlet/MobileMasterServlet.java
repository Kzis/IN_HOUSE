package com.cct.inhouse.bkonline.web.mobile.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONUtil;

import com.cct.enums.CharacterEncoding;
import com.cct.enums.ContentType;
import com.cct.enums.SelectItemParameter;
import com.cct.inhouse.bkonline.core.mobile.domain.MDExceptionFlag;
import com.cct.inhouse.bkonline.core.mobile.domain.MDStatus;
import com.cct.inhouse.bkonline.core.mobile.domain.MobileData;
import com.cct.inhouse.bkonline.core.mobile.service.MobileManager;

import util.gson.GSONUtil;
import util.web.SessionUtil;

public abstract class MobileMasterServlet extends HttpServlet {

	private static final long serialVersionUID = 2027345894675672974L;

	private Logger logger = loggerInititial();
	public abstract Logger loggerInititial();
	
	/**
	 * สำหรับประมวลตาม Case ที่ Mobile ส่งมาให้
	 * @param mobileData
	 */
	public void processByCase(MobileData mobileData) throws Exception {
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setCharacterEncoding(request, CharacterEncoding.UTF8.getValue());
		processRequest(request, response);
	}
	
	public void processRequest(HttpServletRequest request, HttpServletResponse response) {
		
		// 1. รับ Mobile Data request
		MobileData mobileData = null;
		try {
			mobileData = transfromJSONStringToMobileData(request);
			
			// ประมวลผลตาม case
			processByCase(mobileData);
			
		} catch (MDStatus e) {
			getLogger().error(e);
			if (mobileData == null) {
				mobileData = new MobileData();
			}
			// สร้าง Exception ส่งกลับ
			mobileData.setMdStatus(e);
		} catch (Exception e) {
			getLogger().error(e);
			if (mobileData == null) {
				mobileData = new MobileData();
			}
			// สร้าง Exception ส่งกลับ
			MDStatus status = new MDStatus();
			status.setExceptionFlag(MDExceptionFlag.INTERNAL_SERVER_ERROR.getFlag());
			status.setMessage(MDExceptionFlag.INTERNAL_SERVER_ERROR.getMessage());
			MobileManager.setException(status, e);
			mobileData.setMdStatus(status);
		}
		
		// 2. ตอบ Mobile Data response กลับไป
		responseToClient(mobileData, response);
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
	
	/**
	 * แปลง JSON String ที่ส่งมาจาก Mobile ให้เป็น Object เพื่อนำไปใช้งานต่อไป
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public MobileData transfromJSONStringToMobileData(HttpServletRequest request) throws MDStatus {
		
		MobileData result = null;
		try {
			
			String requestJsonPostParameter = SessionUtil.requestParameter(request, SelectItemParameter.JSON_REQUEST.getValue());
			if (requestJsonPostParameter != null) {
				getLogger().debug("requestJsonPostParameter: " + requestJsonPostParameter);
				result = (MobileData) GSONUtil.transfromJSONStringToObject(requestJsonPostParameter, MobileData.class);
			}
		} catch (Exception e) {
			MDStatus status = new MDStatus();
			status.setExceptionFlag(MDExceptionFlag.INTERNAL_SERVER_ERROR.getFlag());
			status.setMessage(MDExceptionFlag.INTERNAL_SERVER_ERROR.getMessage());
			MobileManager.setException(status, e);
			throw status;
		}
		return result;
	}
	
	/**
	 * แปลง Object ให้เป็น JSON String และส่งกลับไปยัง Mobile
	 * @param mobileData
	 * @param response
	 */
	public void responseToClient(MobileData mobileData, HttpServletResponse response) {
		try {
			String jsonStringResponse = JSONUtil.serialize(mobileData, null, null, false, true);
			response.setCharacterEncoding(CharacterEncoding.UTF8.getValue());
			response.setContentType(ContentType.APPLICATION_JSON.getValue());
			
			PrintWriter printWriter = response.getWriter();
			printWriter.write(jsonStringResponse);
			printWriter.flush();
			
		} catch (Exception e) {
			getLogger().error(e);
		}
	}
}
