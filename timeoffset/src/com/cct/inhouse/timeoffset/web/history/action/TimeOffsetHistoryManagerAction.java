package com.cct.inhouse.timeoffset.web.history.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cct.domain.Transaction;
import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionResultType;
import com.cct.enums.ActionReturnType;
import com.cct.exception.AuthorizationException;
import com.cct.exception.ServerValidateException;
import com.cct.inhouse.central.rmi.service.SelectItemRMIProviders;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseModel.PageType;
import com.cct.inhouse.core.security.authorization.domain.PFCode;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.enums.GlobalType;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.history.manager.domain.HistoryModel;
import com.cct.inhouse.timeoffset.core.history.manager.domain.HistorySearch;
import com.cct.inhouse.timeoffset.core.history.manager.domain.HistorySearchCriteria;
import com.cct.inhouse.timeoffset.core.history.manager.service.TimeOffsetHistoryManager;
import com.cct.inhouse.timeoffset.util.TOUtil;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.cct.interfaces.InterfaceAction;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;
import util.string.StringUtil;

public class TimeOffsetHistoryManagerAction extends InhouseAction implements InterfaceAction, ModelDriven<HistoryModel> {

	private static final long serialVersionUID = 7261291911099337437L;
	private HistoryModel model = new HistoryModel();
	
	public TimeOffsetHistoryManagerAction() throws Exception {
		
		try {
			
			getAuthorization(PFCode.TO_HISTORY);
			
			getLogger().debug(" \n ##### [Authorization] ##### "
					+ "\n SEARCH : " + ATH.isSearch()
					+ "\n PRINT : " + ATH.isPrint()
					);
			
			
		} catch (Exception e) {
			getLogger().error("", e);
		}	
	}
	
	@Override
	public HistoryModel getModel() {
		return model;
	}

	@Override
	public String init() throws AuthorizationException,ServerValidateException {
	
		getLogger().info(" ### init");
		
		String result = null;
		CCTConnection conn = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageInitial(conn, getModel(), new HistorySearchCriteria(), getPF_CODE().getSearchFunction(), PageType.SEARCH);
			
			model.setCriteria(new HistorySearchCriteria());
			
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
			
			getModel().setListApprove(
					SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.APPROVE_STATUS)
			);
			getLogger().debug("LIST APPROVE STATUS : " + getModel().getListApprove());

			
		} catch (Exception e) {
			getLogger().error("", e);
		}
		
	}

	@Override
	public String search() throws Exception {

		getLogger().info(" ### search");
		
		String result = null;
		CCTConnection conn = null;
		TimeOffsetHistoryManager manager = null;
		
		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());
			
			// [3] ค้นหาข้อมูล
			manager = new TimeOffsetHistoryManager(getLogger(), getUser(), getLocale());
			List<HistorySearch> lstResult = manager.search(conn,getModel().getCriteria());
			
			// [4] จัดการผลลัพธ์และข้อความถ้าไม่พบข้อมูล
			manageSearchResult(getModel(), lstResult , conn , null);
			
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
	public String export() throws Exception {

		getLogger().info(" ### export");
		
		String result = null;
		CCTConnection conn = null;
		TimeOffsetHistoryManager manager = null;
		
		String fileName = null;
		
		try {

			//ชื่อไฟล์ที่จะออก Report
			fileName = TOUtil.getCurrentDateTimeReport() + GlobalVariableTO.UNDERSCORE + GlobalVariableTO.REP + GlobalVariableTO.UNDERSCORE + getF_CODE();
			
			//New Connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			//ตรวจสอบสิทธิ์ Print
			checkAuthorization(getPF_CODE().getPrintFunction());
			
			//Set เพื่อคืนหน้าเดิม
			result = null;
			
			manager = new TimeOffsetHistoryManager(getLogger(), getUser(), getLocale());
			
			//Create report
			XSSFWorkbook workbook = manager.export(conn,getModel().getCriteria());
			
			//ไม่มี workbook กลับมาหรือหาไม่เจอ
			if(workbook == null && getModel().getCriteria().getTotalResult() == 0){
				
				//Set Message ว่าไม่พบข้อมูล เหมือนตอน manageSearchResult
				setMessage(getModel(), ActionMessageType.WARING, getText("30011"), "");
				setMessage(ActionMessageType.WARING, getText("30011"), ActionResultType.BASIC);
				
				result = ActionReturnType.INIT.getResult();
				
			}else{
				exportExcelFile(workbook, fileName+ ".xlsx");
			}
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getPrintFunction(), this, e, getModel());
			setMessage(getModel(), ActionMessageType.ERROR, getText(e.getMessage()), getErrorMessage(e));
		} finally{
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.TO_HISTORY;
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
	public void getComboForAddEdit(CCTConnection conn) {
		// TODO Auto-generated method stub
		
	}

}
