package com.cct.inhouse.timeoffset.web.approve.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cct.domain.Transaction;
import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionResultType;
import com.cct.enums.ActionReturnType;
import com.cct.exception.AuthorizationException;
import com.cct.exception.ServerValidateException;
import com.cct.inhouse.central.rmi.domain.SelectItemMethod;
import com.cct.inhouse.central.rmi.service.SelectItemRMIProviders;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseModel.PageType;
import com.cct.inhouse.core.security.authorization.domain.PFCode;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.enums.GlobalType;
import com.cct.inhouse.timeoffset.core.approve.domain.Approve;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveModel;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveSearch;
import com.cct.inhouse.timeoffset.core.approve.domain.ApproveSearchCriteria;
import com.cct.inhouse.timeoffset.core.approve.service.TimeOffsetApproveManager;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.cct.interfaces.InterfaceAction;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.web.SessionUtil;

public class TimeOffsetApproveAction extends InhouseAction implements InterfaceAction, ModelDriven<ApproveModel> {

	private static final long serialVersionUID = -8799286730954655186L;
	private ApproveModel model = new ApproveModel();
	String todoId;

	public TimeOffsetApproveAction() throws Exception {

		try {
			getAuthorization(PFCode.TO_APPROVE);
			
			getLogger().debug(" \n ##### [Authorization] ##### "
					+ "\n SEARCH : " + ATH.isSearch()
					+ "\n APPROVE : " + ATH.isApprove()
					+ "\n VIEW : " + ATH.isView()
					);
			
		} catch (Exception e) {
			getLogger().error("", e);
		}
	}

	@Override
	public ApproveModel getModel() {
		return model;
	}

	@Override
	public String init() throws AuthorizationException,ServerValidateException {

		String result = ActionReturnType.INIT.getResult();

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageInitial(conn, getModel(), new ApproveSearchCriteria(), getPF_CODE().getSearchFunction(), PageType.SEARCH);
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public void getComboForSearch(CCTConnection conn) {
		
		try {
			
			getModel().setListProject(
					SelectItemRMIProviders.requestSelectItem(
							getUser()
							, getLocale()
							, SelectItemMethod.SEARCH_PROJECTS_SELECT_ITEM
					)
			);
			getLogger().debug("LIST PROJECT : " + getModel().getListProject());
			
			
			getLogger().debug("============ SEARCH_DEPARTMENT_SELECT_ITEM ============");
			getModel().setListDepartment(
					SelectItemRMIProviders.requestSelectItem(
							getUser()
							, getLocale()
							, SelectItemMethod.SEARCH_DEPARTMENT_SELECT_ITEM_OBJ
							, "3,4,5,6,13"
					)
			);
			getLogger().debug("LIST DEPARTMENT : " + getModel().getListDepartment());
			
			
			getModel().setListProcessStatus(
					SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.PROCESS_STATUS)
			);
			getLogger().debug("LIST PROCESS STATUS : " + getModel().getListProcessStatus());
			
		} catch (Exception e) {
			getLogger().error("", e);
		}

	}

	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		
		try {

			getModel().setListApprovalStatus(
				SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.APPROVE_STATUS)
			);
			getLogger().debug("LIST APPROVE STATUS : " + getModel().getListApprovalStatus());
			
		} catch (Exception e) {
			getLogger().error("", e);
		}
		
	}

	@Override
	public String search() throws Exception {

		String result = null;
		CCTConnection conn = null;
		TimeOffsetApproveManager manager = null;

		try {
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), PF_CODE.getSearchFunction());

			// [3] ค้นหาข้อมูล
			manager = new TimeOffsetApproveManager(getLogger(), getUser(), getLocale());
			List<ApproveSearch> lstResult = manager.search(conn,getModel().getCriteria());

			// [4] จัดการผลลัพธ์และข้อความถ้าไม่พบข้อมูล
			manageSearchResult(getModel(), lstResult , conn , null);

		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public String edit() throws AuthorizationException,ServerValidateException {

		String result = null;
		CCTConnection conn = null;
		TimeOffsetApproveManager manager = null;
		
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			checkAuthorization(getPF_CODE().getApproveFunction());
			
			result = ActionReturnType.ADD_EDIT.getResult();

			manager = new TimeOffsetApproveManager(getLogger(), getUser(), getLocale());
			manager.edit(conn,getModel().getApprove());
				
			todoId = getModel().getApprove().getTodoId();
			
			//check เพื่อนคืนหน้าหลังการ Approve ว่ามาจากหน้าไหน
			if(todoId.equals(null) || todoId.equals("")){ 
				result = ActionReturnType.SEARCH.getResult();
			}else{ 
				result = "todoApprove";
			}
			
			//Clear ค่า
			getModel().setApprove(new Approve());
			
			//Set Message เพืื่อแสดงหนาจอ ในกรณี แก้ไขสำเร็จ
			setMessage(getModel(), ActionMessageType.SUCCESS, getText("30003"), "");
			setMessage(ActionMessageType.SUCCESS, getText("30003"), ActionResultType.BASIC);
			
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getApproveFunction(), this, e, getModel());
		} finally {
			getComboForAddEdit(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}

	@Override
	public String gotoEdit() throws AuthorizationException,ServerValidateException {

		String result = null;
		CCTConnection conn = null;
		TimeOffsetApproveManager manager = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			checkAuthorization(getPF_CODE().getApproveFunction());

			if (getModel().getCriteria() != null && getModel().getCriteria().getCriteriaKey() == null) {
				String urlInit = ServletActionContext.getRequest().getContextPath() + ServletActionContext.getActionMapping().getNamespace() + "/"
						+ ServletActionContext.getActionMapping().getName().replace("gotoEdit", "init") + ".action";
				ServletActionContext.getResponse().sendRedirect(urlInit);
			}
			
			result = ActionReturnType.ADD_EDIT.getResult();

			getModel().setPage(PageType.APPROVE);

			getModel().setUrlEdit(ServletActionContext.getRequest().getContextPath() + ServletActionContext.getActionMapping().getNamespace() + "/"
					+ ServletActionContext.getActionMapping().getName() + ".action");
			
			manager = new TimeOffsetApproveManager(getLogger(), getUser(), getLocale());
			
			//รับค่า Id จากหน้า TodoSearch
			todoId = (String) SessionUtil.requestParameter("todoId");
			
			if(todoId == null){ 
				
				//มาจากหน้า Approve
				getModel().setApprove(manager.searchById(conn,getModel().getApprove().getId()));
				getModel().getApprove().setTodoId(null);
				
			}else{ 
				
				getAuthorization(PFCode.TO_TODO);
				
				getLogger().debug(" \n ##### [Authorization] ##### "
						+ "\n SEARCH : " + ATH.isSearch()
						+ "\n APPROVE : " + ATH.isApprove()
						);
				
				//ตรวจสอบสิทธิ์ Chanage จากหน้า TODO ก็คือสิทธิ์ในการ Approve
				checkAuthorization(getPF_CODE().getApproveFunction());
				
				//มาจากหน้า TODO
				getModel().setApprove(manager.searchById(conn,todoId));
				getModel().getApprove().setTodoId(todoId);
				
			}
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getApproveFunction(), this, e, getModel());
		} finally {
			getComboForAddEdit(conn);
			CCTConnectionUtil.close(conn);
		}
		

		return result;
	}

	@Override
	public String gotoView() throws AuthorizationException,ServerValidateException {

		String result = null;
		CCTConnection conn = null;
		TimeOffsetApproveManager manager = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageGotoView(conn, getModel());
			
			manager = new TimeOffsetApproveManager(getLogger(), getUser(), getLocale());
			getModel().setApprove(manager.searchById(conn,getModel().getApprove().getId()));
	
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getViewFunction(), this, e, getModel());
		} finally {
			getComboForAddEdit(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}

	/**
	 * สำหรับปิดหน้าจอ และยกเลิก
	 */
	public String cancel() throws AuthorizationException,ServerValidateException {
		
		CCTConnection conn = null;
		String result = null;
		try {
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
		    todoId = getModel().getApprove().getTodoId();
			
			if(todoId.equals(null) || todoId.equals("")){ 
				
				// ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			    manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());
			    
				result = ActionReturnType.SEARCH.getResult();
				
			}else{ 
				result = "todoApprove";
			}
		    
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return result;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.TO_APPROVE;
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


}
