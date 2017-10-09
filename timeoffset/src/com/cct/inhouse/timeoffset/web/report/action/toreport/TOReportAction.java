package com.cct.inhouse.timeoffset.web.report.action.toreport;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
import com.cct.inhouse.timeoffset.core.domain.GlobalVariableTO;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReportModel;
import com.cct.inhouse.timeoffset.core.report.toreport.domain.TOReportSearchCriteria;
import com.cct.inhouse.timeoffset.core.report.toreport.service.TOReportManager;
import com.cct.inhouse.timeoffset.util.TOUtil;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.cct.interfaces.InterfaceAction;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class TOReportAction extends InhouseAction implements InterfaceAction, ModelDriven<TOReportModel> {

	private static final long serialVersionUID = 6145040970833549590L;
	private TOReportModel model = new TOReportModel();

	public TOReportAction() throws Exception {

		try {
			
			getAuthorization(PFCode.TO_IN_USE_REPORT);
			
			getLogger().debug(" \n ##### [Authorization] ##### "
					+ "\n PRINT : " + ATH.isPrint()
					);
			
		} catch (Exception e) {
			getLogger().error("", e);
		}
	}

	@Override
	public TOReportModel getModel() {
		return model;
	}

	@Override
	public String init() throws AuthorizationException,ServerValidateException {

		String result = null;

		CCTConnection conn = null;

		try {

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageInitial(conn, getModel(), new TOReportSearchCriteria(), getPF_CODE().getPrintFunction(), PageType.SEARCH);

		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		getModel().getReport().setProjectName("");
		getModel().getReport().setProjConName("");

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
			
			getModel().setListApprove(
					SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.APPROVE_STATUS)
			);
			
			getLogger().debug("LIST APPROVE STATUS : " + getModel().getListApprove());

		} catch (Exception e) {
			getLogger().error("", e);
		}

	}

	

	@Override
	public String export() throws Exception {

		String result = null;
		CCTConnection conn = null;
		TOReportManager manager = null;

		String fileName = null;

		try {
			
			//New Connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			//ตรวจสอบสิทธิ์ Print
			checkAuthorization(getPF_CODE().getPrintFunction());
			
			manager = new TOReportManager(getLogger(), getUser(), getLocale());

			// Search name ของ โครงการ กับ เงื่อนไขเวลาชดเชย
			getModel().getCriteria().setProjectName(getModel().getReport().getProjectName());
			getModel().getCriteria().setProjConName(getModel().getReport().getProjConName());

			// ชื่อไฟล์ที่จะออก Report
			fileName = TOUtil.getCurrentDateTimeReport() + GlobalVariableTO.UNDERSCORE + GlobalVariableTO.REP + GlobalVariableTO.UNDERSCORE + getF_CODE();
			
			// Create report
			XSSFWorkbook workbook = manager.export(conn,getModel().getCriteria());

			// ไม่มี workbook กลับมาหรือหาไม่เจอ
			if (workbook == null && getModel().getCriteria().getTotalResult() == 0) {

				getModel().setCriteria(new TOReportSearchCriteria());

				// Set Message ว่าไม่พบข้อมูล เหมือนตอน manageSearchResult
				setMessage(getModel(), ActionMessageType.WARING, getText("30011"), "");
				setMessage(ActionMessageType.WARING, getText("30011"), ActionResultType.BASIC);
				
				result = ActionReturnType.INIT.getResult();

			} else {
				exportExcelFile(workbook, fileName + ".xlsx");
			}

		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
			setMessage(getModel(), ActionMessageType.ERROR, getText(e.getMessage()), getErrorMessage(e));
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.TO_REPORT;
	}
	
	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		// TODO Auto-generated method stub
	}

	@Override
	public String search() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
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
	public void showTransaction(Transaction transaction) {
		// TODO Auto-generated method stub

	}

}
