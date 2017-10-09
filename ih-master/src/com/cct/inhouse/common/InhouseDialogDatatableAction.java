package com.cct.inhouse.common;

import java.util.List;

import org.apache.log4j.Logger;

import com.cct.domain.SearchCriteria;
import com.cct.enums.ActionReturnType;
import com.cct.exception.AuthorizationException;
import com.cct.exception.ServerValidateException;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class InhouseDialogDatatableAction extends InhouseAction implements ModelDriven<InhouseModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6631272065836982263L;
	
	/**
	 * Concrete method for initialize criteria
	 * @return criteria
	 */
	public SearchCriteria initSearchCriteria() throws Exception {
		return null;
	}
	
	/**
	 * Concrete method for initialize combo
	 * @param conn
	 */
	public void getComboForSearch(CCTConnection conn) {
		
	}
	
	/**
	 * Concrete method for search
	 * @param conn
	 */
	public List<InhouseDomain> search(CCTConnection conn) throws Exception{
		return null;
	}
	
	public List<InhouseDomain> searchByIds(CCTConnection conn) throws Exception {
		return null;
	}
	
	@Override
	public InhouseModel getModel() {
		return null;
	}
	
	private void initCriteriaDialogDatatable(InhouseModel model) {
		
		getModel().getCriteria().setDefaultHeaderSorts();

		if (getModel().getCriteria().getHeaderSortsSelect() != null) {
			
			String[] hSelect = getModel().getCriteria().getHeaderSortsSelect().split(",");
			String order = "";
			for (String head : hSelect) {
				order += "," + getModel().getCriteria().getHeaderSorts()[Integer.parseInt(head)].getOrder();
			}
			
			getModel().getCriteria().setOrderSortsSelect(order.substring(1));
		}
	}
	
	public String init() {
		
		CCTConnection conn = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			//INIT CRITERIA
			SearchCriteria criteria = initSearchCriteria();
			
			getModel().setCriteria(criteria);
			
			initCriteriaDialogDatatable(getModel());
			
		} catch (Exception e) {
			
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return ActionReturnType.SEARCH_AJAX.getResult();

	}
	
	public String searchByIds() throws AuthorizationException, ServerValidateException {
		
		CCTConnection conn 	= null;
		
		try {

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			List<InhouseDomain> lstResult = searchByIds(conn);
			
			getModel().setLstResult(lstResult);

		} catch (Exception e) {
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
			
		} finally {
			CCTConnectionUtil.close(conn);
		}

		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	public String search() throws AuthorizationException, ServerValidateException {
		
		String result = null;
		CCTConnection conn 	= null;
		
		try {

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());
			
			List<InhouseDomain> lstResult = search(conn);
			
			manageSearchResult(getModel(), lstResult, conn, null);

		} catch (Exception e) {
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
			
		} finally {
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public Logger loggerInititial() {
		return null;
	}

}
