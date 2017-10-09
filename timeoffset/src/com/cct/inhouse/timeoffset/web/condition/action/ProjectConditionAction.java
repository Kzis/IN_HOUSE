package com.cct.inhouse.timeoffset.web.condition.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.cct.domain.Transaction;
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
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectCondition;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectConditionModel;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectConditionSearch;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectConditionSearchCriteria;
import com.cct.inhouse.timeoffset.core.condition.service.ProjectConditionManager;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.cct.interfaces.InterfaceAction;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class ProjectConditionAction extends InhouseAction implements InterfaceAction, ModelDriven<ProjectConditionModel> {

	private static final long serialVersionUID = 231460372752394010L;

	private ProjectConditionModel model = new ProjectConditionModel();

	public ProjectConditionAction() throws Exception {

		try {
			
			getAuthorization(PFCode.TO_CON_PROJECT);
			
			getLogger().debug(" \n ##### [Authorization] ##### "
					+ "\n SEARCH : " + ATH.isSearch()
					+ "\n ADD : " + ATH.isAdd()
					+ "\n EDIT : " + ATH.isEdit()
					+ "\n CHANGE : " + ATH.isChange()
					);
			
		} catch (Exception e) {
			getLogger().error("", e);
		}
	}

	@Override
	public ProjectConditionModel getModel() {
		return model;
	}

	@Override
	public String init() throws AuthorizationException,ServerValidateException {
		
		String result = null;
		CCTConnection conn = null;
		
		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน
			result = manageInitial(conn, getModel(), new ProjectConditionSearchCriteria(), getPF_CODE().getSearchFunction(), PageType.SEARCH);
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
		} finally{
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
			
			getModel().setListActiveStatus(
					SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.ACTIVE_STATUS)
			);
			
			getLogger().debug("LIST ACTIVE STATUS : " + getModel().getListActiveStatus());
			
		} catch (Exception e) {
			getLogger().error("",e);
		}
	}

	@Override
	public void getComboForAddEdit(CCTConnection conn) {

		try {
			
			getModel().setListProject(
					SelectItemRMIProviders.requestSelectItem(
							getUser()
							, getLocale()
							, SelectItemMethod.SEARCH_PROJECTS_SELECT_ITEM
					)
			);
			
			getLogger().debug("LIST PROJECT : " + getModel().getListProject());
			
			
		} catch (Exception e) {
			getLogger().error("",e);
		}
	}

	@Override
	public String search() throws Exception {

		String result = null;
		CCTConnection conn = null;
		ProjectConditionManager manager = null;
		
		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());
			
			manager = new ProjectConditionManager(getLogger(), getUser(), getLocale());
			
			// [3] ค้นหาข้อมูล
			List<ProjectConditionSearch> lstResult = manager.search(conn,getModel().getCriteria());
			
			// [4] จัดการผลลัพธ์และข้อความถ้าไม่พบข้อมูล
			manageSearchResult(getModel(), lstResult,conn, null);
			
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
	public String gotoAdd() throws AuthorizationException,ServerValidateException {
	
		String result = null;
		CCTConnection conn = null;
		
		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการเพิ่ม
			result = manageGotoAdd(conn, getModel());
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getAddFunction(), this, e, getModel());
		} finally {
			getComboForAddEdit(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}

	@Override
	public String add() throws AuthorizationException,ServerValidateException {
		
		String result = null;
		CCTConnection conn = null;
		ProjectConditionManager manager = null;
		
		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการเพิ่ม
			result = manageAdd(conn, getModel());
			
			manager = new ProjectConditionManager(getLogger(), getUser(), getLocale());
			
			// [3] เพิ่มข้อมูล
			manager.add(conn,getModel().getProjectCondition());
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getAddFunction(),this, e,getModel(), ActionResultType.BASIC, "");
		} finally {
			getComboForAddEdit(conn);
			CCTConnectionUtil.close(conn);
			getModel().setProjectCondition(new ProjectCondition());
		}
		
		return result;
	}

	@Override
	public String edit() throws AuthorizationException,ServerValidateException {

		String result = null;
		CCTConnection conn = null;
		ProjectConditionManager manager = null;
		
		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการแก้ไข
			result = manageEdit(conn, getModel(),ActionResultType.BASIC);

			manager = new ProjectConditionManager(getLogger(), getUser(), getLocale());
			
			// [3] แก้ไขข้อมูล
			manager.edit(conn,getModel().getProjectCondition());
			
			getModel().setProjectCondition(new ProjectCondition());
			
			manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());
						
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getEditFunction(),this, e,getModel());
			
			// Set result เพื่อคืนหน้าเดิม
			result = ActionReturnType.ADD_EDIT.getResult();
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
		ProjectConditionManager manager = null;
		
		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการแก้ไข
			result = manageGotoEdit(conn, getModel());
			
			manager = new ProjectConditionManager(getLogger(), getUser(), getLocale());
			
			getModel().setProjectCondition(manager.searchById(conn,getModel().getProjectCondition().getId()));
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getEditFunction(), this, e, getModel());
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
		
		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
		    manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());

		} catch (Exception e) {
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return ActionReturnType.SEARCH.getResult();
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

	@Override
	public Logger loggerInititial() {
		return LogUtil.TO_CON_PROJ;
	}
	
	
	public String changeActive() throws Exception {
		
		String result = null;
		CCTConnection conn = null;
		ProjectConditionManager manager = null;
		
		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			manager = new ProjectConditionManager(getLogger(), getUser(), getLocale());
			
			//Validate ว่าต้องมีการติ๊ก Checkbox จากหน้าจอเข้ามา ถึงจะทำการ Update flag ได้
			if(getModel().getCriteria().getSelectedIds()!=null && !getModel().getCriteria().getSelectedIds().equals("")){
				
				getLogger().debug("Change Code : " + PF_CODE.getChangeFunction());
				getLogger().debug("Status For Update : " + getModel().getCriteria().getStatusForUpdate());
				
				// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการอัพเดท
				result = manageUpdateActive(conn, getModel(), ActionResultType.BASIC);
								
				getLogger().debug("Change active id...[" + getModel().getCriteria().getSelectedIds() + "]");
				
				manager.updateActive(conn,getModel().getCriteria().getSelectedIds(), getModel().getCriteria().getStatusForUpdate());
			}
			
			// [3] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());		
			
			// [4] ค้นหาข้อมูล
			List<ProjectConditionSearch> lstResult = manager.search(conn,getModel().getCriteria());
			
			// [5] จัดการผลลัพธ์และข้อความถ้าไม่พบข้อมูล
			manageSearchResult(getModel(), lstResult,conn, null);
			
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getChangeFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	
	}
}
