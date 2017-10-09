package com.cct.inhouse.timeoffset.web.report.action.inuse;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

import com.cct.domain.Transaction;
import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionResultType;
import com.cct.enums.ActionReturnType;
import com.cct.exception.AuthorizationException;
import com.cct.exception.ServerValidateException;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseModel.PageType;
import com.cct.inhouse.core.security.authorization.domain.PFCode;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportModel;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportSearch;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.domain.InuseManagerReportSearchCriteria;
import com.cct.inhouse.timeoffset.core.report.inuse.manager.service.InuseManagerReportManager;
import com.cct.inhouse.timeoffset.util.TOUtil;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.cct.interfaces.InterfaceAction;
import com.opensymphony.xwork2.ModelDriven;

public class InuseManagerReportAction extends InhouseAction implements InterfaceAction, ModelDriven<InuseManagerReportModel> {

	private static final long serialVersionUID = -279412233355251326L;
	private InuseManagerReportModel model = new InuseManagerReportModel();

	public InuseManagerReportAction() throws Exception {

		try {
			
			getAuthorization(PFCode.TO_REPORT);
			
			getLogger().debug(" \n ##### [Authorization] ##### "
					+ "\n SEARCH : " + ATH.isSearch()
					+ "\n SYN : " + ATH.isSyn()
					+ "\n PRINT : " + ATH.isPrint()
					);

		} catch (Exception e) {
			getLogger().error("", e);
		}
	}

	@Override
	public InuseManagerReportModel getModel() {
		return model;
	}

	@Override
	public String init() throws AuthorizationException,ServerValidateException {

		String result = null;

		CCTConnection conn = null;

		try {

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageInitial(conn, getModel(), new InuseManagerReportSearchCriteria(), getPF_CODE().getSearchFunction(), PageType.SEARCH);

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
			
		} catch (Exception e) {
			getLogger().error("", e);
		}
	}



	@Override
	public String search() throws Exception {

		String result = null;
		CCTConnection conn = null;
		InuseManagerReportManager manager = null;

		try {
			
			// [1] สร้าง connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			// [2] ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());
			
			// [3] ค้นหาข้อมูล
			manager = new InuseManagerReportManager(getLogger(), getUser(), getLocale());
			List<InuseManagerReportSearch> lstResult = manager.search(conn,getModel().getCriteria());

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
	public String export() throws Exception {

		String result = null;
		CCTConnection conn = null;
		InuseManagerReportManager manager = null;
		
		String fileName = null;
		
		try {
			
			//ชื่อไฟล์ที่จะออก InuseManagerReport
			fileName = TOUtil.getCurrentDateTimeReport() + GlobalVariableTO.UNDERSCORE + GlobalVariableTO.REP + GlobalVariableTO.UNDERSCORE + getF_CODE();
			
			//New Connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			//ตรวจสอบสิทธิ์ Print
			checkAuthorization(getPF_CODE().getPrintFunction());
			
			//Set เพื่อคืนหน้าเดิม
			result = null;
			
			manager = new InuseManagerReportManager(getLogger(), getUser(), getLocale());
			
			//Create report
			XSSFWorkbook workbook = manager.export(conn , getModel().getCriteria());
			
			//ไม่มี workbook กลับมาหรือหาไม่เจอ
			if(workbook == null && getModel().getCriteria().getTotalResult() == 0){
				
				//Set Message ว่าไม่พบข้อมูล เหมือนตอน manageSearchResult
				setMessage(getModel(), ActionMessageType.WARING, getText("30011"), "");
				setMessage(ActionMessageType.WARING, getText("30011"), ActionResultType.BASIC);
				
				result = ActionReturnType.INIT.getResult();
				
			}else{
				
				//Export file
				exportExcelFile(workbook, fileName+ ".xlsx");
				
			}
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
			setMessage(getModel(), ActionMessageType.ERROR, getText(e.getMessage()), getErrorMessage(e));
		} finally{
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return result;
		
	}
	
	public String syncData() throws Exception {

		String result = null;
		CCTConnection conn = null;
		InuseManagerReportManager manager = null;

		try {

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			manager = new InuseManagerReportManager(getLogger(), getUser(), getLocale());
			
			//Manage Sync
			checkAuthorization(getPF_CODE().getSynFunction());
			
			//Set เพื่อคืนหน้าเดิม
			result = ActionReturnType.INIT.getResult();
			
//			result = manageOther(conn, getPF_CODE().getSynFunction(),  ActionReturnType.INIT,  ActionMessageType.SUCCESS, "30006",  ActionResultType.BASIC, PageType.PRINT, getModel());
			
			//Sync data
			manager.synData(conn,getModel());
			
			//Process Search after sync data
			result = manageSearchAjax(conn, getModel(), getModel().getCriteria(), getPF_CODE().getSearchFunction());
			
			List<InuseManagerReportSearch> lstResult = manager.search(conn,getModel().getCriteria());

			manageSearchResult(getModel(), lstResult , conn , null);
			
		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getPrintFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}	
		
		return result;

	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.TO_MANAGE;
	}
	
	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		// TODO Auto-generated method stub
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
}
