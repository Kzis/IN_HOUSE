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
import com.cct.inhouse.core.standard.department.domain.Department;

import util.database.CCTConnection;
import util.web.SessionUtil;

@WebServlet(
		name=SelectItemServletURL.EmployeeFullnameByDepartmentIdServlet,
	    description="For search Employee fullname by department id",
	    urlPatterns={SelectItemServletURL.EmployeeFullnameByDepartmentIdServlet}
)
public class EmployeeFullnameByDepartmentIdServlet extends CommonSelectItemBasicServlet {

	private static final long serialVersionUID = -1828600991982833454L;

	@Override
	public List<CommonSelectItem> searchSelectItem(HttpServletRequest request, CCTConnection conn, CommonUser commonUser, Locale locale, String team, String limit) throws Exception {
		
		List<CommonSelectItem> collectionSelectItem = new ArrayList<CommonSelectItem>();
		
		Department criteria = new Department();
		criteria.setDepartmentId(SessionUtil.requestParameter("departmentId"));// Filter with Programmer
		getLogger().debug("Filter by DepartmentId: " + criteria.getDepartmentId());

		try {
			collectionSelectItem = SelectItemRMIProviders.requestSelectItem(
					commonUser
					, locale
					, SelectItemMethod.SEARCH_EMPLOYEE_BY_DEPARTMENT_ID
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
