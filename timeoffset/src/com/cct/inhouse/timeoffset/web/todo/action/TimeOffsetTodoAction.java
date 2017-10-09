package com.cct.inhouse.timeoffset.web.todo.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.cct.domain.Transaction;
import com.cct.enums.ActionResultType;
import com.cct.enums.ActionReturnType;
import com.cct.exception.AuthorizationException;
import com.cct.exception.ServerValidateException;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseModel.PageType;
import com.cct.inhouse.core.security.authorization.domain.PFCode;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.timeoffset.core.todo.domain.TodoModel;
import com.cct.inhouse.timeoffset.core.todo.domain.TodoSearch;
import com.cct.inhouse.timeoffset.core.todo.domain.TodoSearchCriteria;
import com.cct.inhouse.timeoffset.core.todo.service.TimeOffsetTodoManager;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.cct.interfaces.InterfaceAction;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class TimeOffsetTodoAction extends InhouseAction implements InterfaceAction, ModelDriven<TodoModel> {

	private static final long serialVersionUID = -5628677186096059478L;
	private TodoModel model = new TodoModel();

	public TimeOffsetTodoAction() throws Exception {
		
		try {
			getAuthorization(PFCode.TO_TODO);
			
			getLogger().debug(" \n ##### [Authorization] ##### "
					+ "\n SEARCH : " + ATH.isSearch()
					+ "\n APPROVE : " + ATH.isApprove()
					);
			
		} catch (Exception e) {
			getLogger().error("", e);
		}
	}

	@Override
	public TodoModel getModel() {
		return model;
	}

	@Override
	public String init() throws AuthorizationException,ServerValidateException {

		String result = null;
		CCTConnection conn = null;

		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageInitial(conn, getModel(), new TodoSearchCriteria(), getPF_CODE().getSearchFunction(), PageType.SEARCH);

		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
		} finally {
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public String search() throws Exception {

		String result = null;
		CCTConnection conn = null;
		TimeOffsetTodoManager manager = null;

		try {
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());
			
			// [3] ค้นหาข้อมูล
			manager = new TimeOffsetTodoManager(getLogger(), getUser(), getLocale());
			List<TodoSearch> lstResult = manager.search(conn,getModel().getCriteria());

			// [4] จัดการผลลัพธ์และข้อความถ้าไม่พบข้อมูล
			manageSearchResult(getModel(), lstResult , conn , null);

		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
		} finally {
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	public String gotoApprove() throws AuthorizationException,ServerValidateException {

		String result = null;
		CCTConnection conn = null;

		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = ActionReturnType.ADD_EDIT.getResult();

			getModel().setPage(PageType.APPROVE);

		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getApproveFunction(), this, e, getModel());
		} finally {
			CCTConnectionUtil.close(conn);
		}

		return result;
	}
	
	public String changeActive() throws Exception {
		
		//ยกเลิก ไม่ใช้แล้ว
		
		String result = null;
		CCTConnection conn = null;
		TimeOffsetTodoManager manager = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			manager = new TimeOffsetTodoManager(getLogger(), getUser(), getLocale());
			
			if(getModel().getCriteria().getSelectedIds()!=null && !getModel().getCriteria().getSelectedIds().equals("")){
				
				result = manageUpdateActive(conn, getModel(), ActionResultType.BASIC);
				
				getLogger().debug("Update approve status id...[" + getModel().getCriteria().getSelectedIds() + "]");
				
				manager.updateActive(conn,getModel().getCriteria().getSelectedIds(), getModel().getCriteria().getStatusForUpdate());
			}
			
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());		

			List<TodoSearch> lstResult = manager.search(conn,getModel().getCriteria());

			manageSearchResult(getModel(), lstResult , conn , null);
			
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getChangeFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.TO_TODO;
	}
	
	@Override
	public void getComboForSearch(CCTConnection conn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		// TODO Auto-generated method stub

	}

	@Override
	public String gotoAdd() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gotoEdit() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gotoView() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateActive() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String export() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showTransaction(Transaction transaction) {
		// TODO Auto-generated method stub

	}
}
