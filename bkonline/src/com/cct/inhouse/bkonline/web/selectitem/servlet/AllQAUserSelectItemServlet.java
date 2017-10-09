package com.cct.inhouse.bkonline.web.selectitem.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemBasicServlet;
import com.cct.common.CommonUser;
import com.cct.inhouse.central.rmi.domain.SelectItemMethod;
import com.cct.inhouse.central.rmi.service.SelectItemRMIProviders;

import util.database.CCTConnection;

@WebServlet(
		name=SelectItemServletURL.AllQAUserSelectItemServlet,
	    description="For search QA User",
	    urlPatterns={SelectItemServletURL.AllQAUserSelectItemServlet}
)
public class AllQAUserSelectItemServlet extends CommonSelectItemBasicServlet {

	private static final long serialVersionUID = -8694073148976463485L;

	/**
	 * เชื่อต่อไปยัง Central เพื่อดึงข้อมูล
	 */
	@Override
	public List<CommonSelectItem> searchSelectItem(HttpServletRequest request
			, CCTConnection conn, CommonUser commonUser
			, Locale locale, String team, String limit) throws Exception {
		
		List<CommonSelectItem> collectionSelectItem = new ArrayList<CommonSelectItem>();
		
		try {
			collectionSelectItem = SelectItemRMIProviders.requestSelectItem(
					commonUser
					, locale
					, SelectItemMethod.SEARCH_USER_FULL_DISPLAY_SELECT_ITEM
					, team
					, limit
					, null
			);
		} catch (Exception e) {
			getLogger().error(e);
		}
		
		return collectionSelectItem;
	}

	/**
	 * กรณีที่ต้องใช้ Connection
	 */
	@Override
	public CCTConnection getConection() throws Exception {
		return null;
	}

	/**
	 * ตรวจสอบผู้ใช้
	 */
	@Override
	public boolean invalidateUser(CCTConnection conn
			, CommonUser commonUser) throws Exception {
		return (commonUser == null);
	}
}
