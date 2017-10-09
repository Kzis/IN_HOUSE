package com.cct.inhouse.timeoffset.web.manage.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

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
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffset;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetModel;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetSearch;
import com.cct.inhouse.timeoffset.core.manage.domain.ManageTimeOffsetSearchCriteria;
import com.cct.inhouse.timeoffset.core.manage.service.ManageTimeOffsetManager;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.cct.interfaces.InterfaceAction;
import com.opensymphony.xwork2.ModelDriven;

public class ManageTimeOffsetAction extends InhouseAction implements InterfaceAction, ModelDriven<ManageTimeOffsetModel> {

	private static final long serialVersionUID = 231460372752394010L;
	private ManageTimeOffsetModel model = new ManageTimeOffsetModel();

	public ManageTimeOffsetAction() throws Exception {

		try {
			
			getAuthorization(PFCode.TO_MANAGE);
			
			getLogger().debug(" \n ##### [Authorization] ##### "
					+ "\n SEARCH : " + ATH.isSearch()
					+ "\n ADD : " + ATH.isAdd()
					+ "\n EDIT : " + ATH.isEdit()
					+ "\n VIEW : " + ATH.isView()
					+ "\n DELETE : " + ATH.isDelete()
					);
		
		} catch (Exception e) {
			getLogger().error("", e);
		}
	}

	@Override
	public ManageTimeOffsetModel getModel() {
		return model;
	}

	@Override
	public String init() throws AuthorizationException,ServerValidateException {

		String result = ActionReturnType.INIT.getResult();

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageInitial(conn, getModel(), new ManageTimeOffsetSearchCriteria(), getPF_CODE().getSearchFunction(), PageType.SEARCH);
		
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
		
			getModel().setListProject(
					SelectItemRMIProviders.requestSelectItem(
							getUser()
							, getLocale()
							, SelectItemMethod.SEARCH_PROJECTS_SELECT_ITEM
					)
			);
			
			getLogger().debug("LIST PROJECT : " + getModel().getListProject());
			
		} catch (Exception e) {
			getLogger().error("", e);
		}

	}

	@Override
	public String search() throws Exception {

		String result = null;
		CCTConnection conn = null;
		ManageTimeOffsetManager manager = null;

		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());
			
			// [3] ค้นหาข้อมูล
			manager = new ManageTimeOffsetManager(getLogger(), getUser(), getLocale());
			List<ManageTimeOffsetSearch> lstResult = manager.search(conn,getModel().getCriteria(),getModel());
			
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
	public String gotoAdd() throws AuthorizationException,ServerValidateException {

		String result = null;
		CCTConnection conn = null;
		
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			result = manageGotoAdd(conn, getModel());
			
			getModel().setManage(new ManageTimeOffset());
			
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
		ManageTimeOffsetManager manager = null;
		
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			result = manageAdd(conn, getModel());
			
			manager = new ManageTimeOffsetManager(getLogger(),getUser(),getLocale());
			
			manager.add(conn,getModel().getManage());
			
			
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getAddFunction(),this,e,getModel(),ActionResultType.BASIC, "");
		} finally{
			getComboForAddEdit(conn);
			CCTConnectionUtil.close(conn);
			
			getModel().setManage(new ManageTimeOffset());
			getModel().setListNewProject(new ArrayList<ManageTimeOffset>());
		}

		return result;
	}

	@Override
	public String edit() throws AuthorizationException,ServerValidateException {

		String result = null;
		CCTConnection conn = null;
		ManageTimeOffsetManager manager = null;
		
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			result = manageEdit(conn, getModel(),ActionResultType.BASIC);
			
			manager = new ManageTimeOffsetManager(getLogger(), getUser(), getLocale());
			getModel().getManage().setTimeOffsetId(Long.parseLong(getModel().getManage().getId()));
			manager.edit(conn,getModel().getManage());
			
			getModel().setManage(new ManageTimeOffset());
			
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getEditFunction(),this, e,getModel());
			
			// Set result เพื่อคืนหน้าเดิม
			result = "editView";
		} finally{
			getComboForAddEdit(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}

	@Override
	public String gotoEdit() throws AuthorizationException,ServerValidateException {
		
		String result = null;
		CCTConnection conn = null;
		ManageTimeOffsetManager manager = null;
		
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			result = manageGotoEdit(conn, getModel());
			
			manager = new ManageTimeOffsetManager(getLogger(), getUser(), getLocale());
			
			getModel().setListNewProject(manager.searchListById(conn,getModel().getManage().getId()));
			
			if(getModel().getListNewProject().size() > 0){
				getModel().getManage().setRemark(getModel().getListNewProject().get(0).getRemark());
			}
		
			getModel().getManage().setListNewProject(getModel().getListNewProject());
			
		} catch (Exception e) {
			getLogger().error("", e);
			 manageException(conn, getPF_CODE().getEditFunction(), this, e, getModel());
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
		ManageTimeOffsetManager manager = null;

		try {

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageGotoView(conn, getModel());

			manager = new ManageTimeOffsetManager(getLogger(), getUser(), getLocale());
			getModel().setListNewProject(manager.searchListById(conn,getModel().getManage().getId()));
			getModel().getManage().setListNewProject(getModel().getListNewProject());
			getModel().getManage().setRemark(getModel().getListNewProject().get(0).getRemark());
			getModel().getManage().setApproveRemark(getModel().getListNewProject().get(0).getApproveRemark());
			getModel().getManage().setApproveUser(getModel().getListNewProject().get(0).getApproveUser());
					
			result = manageGotoView(conn, getModel());
			
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
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
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
	public Logger loggerInititial() {
		return LogUtil.TO_MANAGE;
	}
	
	public String delTO() throws Exception{
		
		String result = null;
		CCTConnection conn = null;
		ManageTimeOffsetManager manager = null;

		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			manager = new ManageTimeOffsetManager(getLogger(), getUser(), getLocale());
			
			//Check ว่ามีการเลือกมามั้ย
			if(getModel().getCriteria().getSelectedIds()!=null && !getModel().getCriteria().getSelectedIds().equals("")){
				
				result = manageDelete(conn, getModel());
				
				getLogger().debug("Delete id...[" + getModel().getCriteria().getSelectedIds() + "]");
				
				//Delete TO Detail In Master
				manager.deleteMasterSearchPage(conn,getModel().getCriteria().getSelectedIds());
				
				setMessage(getModel(), ActionMessageType.SUCCESS, getText("30005"), "");
				setMessage(ActionMessageType.SUCCESS, getText("30005"), ActionResultType.BASIC);
			}
			
			//Set result เพื่อกลับไปหน้า search
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());		
			
			//Search อีกรอบ
			List<ManageTimeOffsetSearch> lstResult = manager.search(conn,getModel().getCriteria());
			manageSearchResult(getModel(), lstResult , conn , null);
			
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
			getLogger().error(e);
		} finally{
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}
	
	public String delTOFromEditPage() throws Exception{
		
		String result = null;
		CCTConnection conn = null;
		ManageTimeOffsetManager manager = null;
		String idCheck = "";

		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			manager = new ManageTimeOffsetManager(getLogger(), getUser(), getLocale());
			
			//Check ว่ามี listNewProject หรือไม่
			if(getModel().getManage().getListNewProject() != null && !getModel().getManage().getListNewProject().isEmpty()){
				
				for (int i = 0; i < getModel().getManage().getListNewProject().size(); i++) {
					
					if(getModel().getManage().getListNewProject().get(i) != null){
						
						if(getModel().getManage().getListNewProject().get(i).getDeletedByEditPage().equals("Y")){
							
							idCheck = idCheck + getModel().getManage().getListNewProject().get(i).getId().split(",")[0] + ", ";
								
						}
						
					}
					
				}
				
				//นำค่า Flag ที่ได้ไป Set ลง Criteria เพื่อจะได้ใช้ Delete ของตัวกลางเหมือนระบบทั่วไป
				getModel().getCriteria().setSelectedIds(idCheck.substring(0, idCheck.length()-2));
				
			}
			
			
			//Check ว่ามีการเลือกมามั้ย
			if(getModel().getCriteria().getSelectedIds()!=null && !getModel().getCriteria().getSelectedIds().equals("")){

				result = manageDelete(conn, getModel());
				
				getLogger().debug("Delete id...[" + getModel().getCriteria().getSelectedIds() + "]");
				
				//Delete
				manager.deleteTODetailInEdit(conn,getModel().getManage(),getModel().getCriteria().getSelectedIds());
				
				setMessage(getModel(), ActionMessageType.SUCCESS, getText("30005"), "");
				setMessage(ActionMessageType.SUCCESS, getText("30005"), ActionResultType.BASIC);
			}
			
			//Set result เพื่อกลับไปหน้า search
			result = ActionReturnType.SEARCH.getResult();		
						
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
			getLogger().error(e);
		} finally{
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}
	
	@Override
	public String updateActive() throws AuthorizationException {
		// TODO Auto-generated method stube
		return null;
	}

	@Override
	public String delete() throws AuthorizationException {
		// TODO Auto-generated method stube
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
