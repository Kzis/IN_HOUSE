package com.cct.inhouse.timeoffset.web.selectitem.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cct.common.CommonSelectItemObject;
import com.cct.common.CommonSelectItemObjectServlet;
import com.cct.common.CommonUser;
import com.cct.inhouse.central.rmi.domain.RMISelectItemObjectRequest;
import com.cct.inhouse.central.rmi.domain.SelectItemMethod;
import com.cct.inhouse.central.rmi.service.SelectItemRMIProviders;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffset;

import util.database.CCTConnection;

public class ProjectConditionSelectItemServlet extends CommonSelectItemObjectServlet {

	private static final long serialVersionUID = 3858677120091824834L;

	@Override
	public List<CommonSelectItemObject<?>> searchSelectItem(HttpServletRequest request, CCTConnection conn, CommonUser commonUser, Locale locale, String team, String limit) throws Exception {
		
		List<CommonSelectItemObject<?>> collectionSelectItem = new ArrayList<CommonSelectItemObject<?>>();
		
		ManageTimeOffset manage = new ManageTimeOffset();
		manage.setProjectId(ServletActionContext.getRequest().getParameter("projectId"));
		
		RMISelectItemObjectRequest<String> selectItemRequest = new RMISelectItemObjectRequest<String>();
		selectItemRequest.setTerm(team);
		selectItemRequest.setLimit(limit);
		selectItemRequest.setCommonUser(commonUser);
		selectItemRequest.setLocale(locale);
		selectItemRequest.setObject(manage.getProjectId());

		try {
			collectionSelectItem = SelectItemRMIProviders.requestSelectItemObject(
					selectItemRequest
					, SelectItemMethod.SEARCH_TIMEOFFSET_CON_BY_PROJECT_ID
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
