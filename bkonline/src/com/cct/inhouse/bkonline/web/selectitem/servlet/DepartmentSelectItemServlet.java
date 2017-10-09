package com.cct.inhouse.bkonline.web.selectitem.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.cct.common.CommonSelectItemObject;
import com.cct.common.CommonSelectItemObjectServlet;
import com.cct.common.CommonUser;
import com.cct.inhouse.central.rmi.domain.SelectItemMethod;
import com.cct.inhouse.central.rmi.service.SelectItemRMIProviders;
import com.cct.inhouse.core.standard.department.domain.Department;

import util.database.CCTConnection;

@WebServlet(
		name=SelectItemServletURL.DepartmentSelectItemServlet, 
		description="For search department", 
		urlPatterns={ SelectItemServletURL.DepartmentSelectItemServlet }
)
public class DepartmentSelectItemServlet extends CommonSelectItemObjectServlet {

	private static final long serialVersionUID = -2990747692635908110L;

	@Override
	public List<CommonSelectItemObject<?>> searchSelectItem(HttpServletRequest request, CCTConnection conn, CommonUser commonUser, Locale locale, String team, String limit) throws Exception {
		
		List<CommonSelectItemObject<?>> collectionSelectItem = new ArrayList<CommonSelectItemObject<?>>();
		
		Department criteria = new Department();
		
		try {
			collectionSelectItem = SelectItemRMIProviders.requestSelectItemObject(
					commonUser
					, locale
					, SelectItemMethod.SEARCH_DEPARTMENT_SELECT_ITEM_OBJ
					, team
					, limit
					, criteria
			);
		} catch (Exception e) {
			getLogger().error(e);
		}
		
		return collectionSelectItem;
	}

	@Override
	public CCTConnection getConection() throws Exception {
		return null;
	}

	@Override
	public boolean invalidateUser(CCTConnection conn, CommonUser commonUser) throws Exception {
		return (commonUser == null);
	}
}
