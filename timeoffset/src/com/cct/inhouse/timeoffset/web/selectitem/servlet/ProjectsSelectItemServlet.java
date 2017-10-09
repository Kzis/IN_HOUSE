package com.cct.inhouse.timeoffset.web.selectitem.servlet;

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
		name=SelectItemServletURL.ProjectsSelectItemServlet,
	    description="For search Project",
	    urlPatterns={SelectItemServletURL.ProjectsSelectItemServlet}
)
public class ProjectsSelectItemServlet extends CommonSelectItemBasicServlet {

	private static final long serialVersionUID = 5327782718221445033L;

	@Override
	public List<CommonSelectItem> searchSelectItem(HttpServletRequest request, CCTConnection conn, CommonUser commonUser, Locale locale, String team, String limit) throws Exception {

		List<CommonSelectItem> collectionSelectItem = new ArrayList<CommonSelectItem>();
		
		try {
			collectionSelectItem = SelectItemRMIProviders.requestSelectItem(
					commonUser
					, locale
					, SelectItemMethod.SEARCH_TIMEOFFSET_CON_BY_PROJECT_ID
					, team
					, limit
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
