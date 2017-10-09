package com.cct.inhouse.timeoffset.web.report.action.inuse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

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
import com.cct.inhouse.timeoffset.core.report.inuse.general.domain.InuseReportModel;
import com.cct.inhouse.timeoffset.core.report.inuse.general.domain.InuseReportSearchCriteria;
import com.cct.inhouse.timeoffset.core.report.inuse.general.service.InuseReportManager;
import com.cct.inhouse.timeoffset.util.TOUtil;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.cct.interfaces.InterfaceAction;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class InuseReportAction extends InhouseAction implements InterfaceAction, ModelDriven<InuseReportModel> {

	private static final long serialVersionUID = -279412233355251326L;
	private InuseReportModel model = new InuseReportModel();

	public InuseReportAction() throws Exception {

		try {
			getAuthorization(PFCode.TO_REPORT_GENERAL);
			
			getLogger().debug(" \n ##### [Authorization] ##### "
					+ "\n PRINT : " + ATH.isPrint()
					);
			
		} catch (Exception e) {
			getLogger().error("", e);
		}
	}

	@Override
	public InuseReportModel getModel() {
		return model;
	}

	@Override
	public String init() throws AuthorizationException,ServerValidateException {

		String result = null;

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			result = manageInitial(conn, getModel(), new InuseReportSearchCriteria(), getPF_CODE().getPrintFunction(), PageType.SEARCH);

		} catch (Exception e) {
			getLogger().error("", e);
			manageException(conn, getPF_CODE().getPrintFunction(), this, e, getModel());
		} finally {

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
	public String export() throws Exception {

		String result = null;
		CCTConnection conn = null;
		InuseReportManager manager = null;
		
		String fileName = null;
		
		try {
			
			//New Connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			
			//ตรวจสอบสิทธิ์ Print
			checkAuthorization(getPF_CODE().getPrintFunction());
			
			manager = new InuseReportManager(getLogger(), getUser(), getLocale());

			//Excel
			if(getModel().getCriteria().getReportType().equals(GlobalVariableTO.EXCEL)){
				
				fileName = TOUtil.getCurrentDateTimeReport() + GlobalVariableTO.UNDERSCORE + GlobalVariableTO.REP + GlobalVariableTO.UNDERSCORE + getF_CODE();
				
				//Create report
				XSSFWorkbook workbook = manager.exportExcel(conn , getModel().getCriteria());
				
				//ไม่มี workbook กลับมาหรือหาไม่เจอ
				if(workbook == null && getModel().getCriteria().getTotalResult() == 0){
					
					//Set Message ว่าไม่พบข้อมูล เหมือนตอน manageSearchResult
					setMessage(getModel(), ActionMessageType.WARING, getText("30011"), "");
					setMessage(ActionMessageType.WARING, getText("30011"), ActionResultType.BASIC);

					result = ActionReturnType.INIT.getResult();
					
				}else{
					exportExcelFile(workbook, fileName+ ".xlsx");
				}
				
			}
			//PDF
			else if(getModel().getCriteria().getReportType().equals(GlobalVariableTO.PDF)){
				
				fileName = TOUtil.getCurrentDateTimeReport() + GlobalVariableTO.UNDERSCORE + GlobalVariableTO.REP + GlobalVariableTO.UNDERSCORE + getF_CODE() + ".pdf";
				
				String contentType = ServletActionContext.getRequest().getParameter("contentType");
				
				byte[] document = manager.exportPdf(conn,getModel().getCriteria());
				
				exportFile(ServletActionContext.getResponse(), document, fileName, contentType);
				
			}
			else{
				throw new Exception();
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
