package com.cct.inhouse.common;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.cct.common.CommonAction;
import com.cct.domain.SearchCriteria;
import com.cct.enums.ActionMaxExceedType;
import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionResultType;
import com.cct.enums.ActionReturnType;
import com.cct.exception.AuthenticateException;
import com.cct.exception.AuthorizationException;
import com.cct.exception.CustomException;
import com.cct.exception.DefaultExceptionMessage;
import com.cct.exception.DuplicateException;
import com.cct.exception.InputValidateException;
import com.cct.exception.MaxExceedAlertException;
import com.cct.exception.MaxExceedException;
import com.cct.exception.MaxExceedReportException;
import com.cct.exception.ServerValidateException;
import com.cct.inhouse.common.InhouseModel.PageType;
import com.cct.inhouse.core.config.parameter.domain.Parameter;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.core.security.authorization.domain.Authorize;
import com.cct.inhouse.core.security.authorization.domain.PFCode;
import com.cct.inhouse.core.security.authorization.service.AuthorizationService;
import com.cct.inhouse.util.image.BrowseUploadServiceUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

import util.database.CCTConnection;
import util.file.FileManagerUtil;
import util.string.StringDelimeter;
import util.string.StringUtil;
import util.web.SessionUtil;

public abstract class InhouseAction extends CommonAction implements Serializable {

	private static final long serialVersionUID = 2843485154046138037L;

	// suraphong.a (31/08/2017) : แก้ไข error NullPointerException เมื่อพยายามดึง LPP จาก Parameter ที่เป็น Null ในกรณีที่ Initial parameter ไม่ได้
	public String[] LPP = getLPP();

	public Authorize ATH = new Authorize();
	public PFCode PF_CODE;

	public AuthorizationService AUTHORIZATION_SERVICE = new AuthorizationService();
	
	/**
	 * @Description: Class enum for message Code
	 */
	public enum MessageCode {
		MSG_30011("30011"), MSG_30014("30014"), MSG_30018("30018");

		private String type;

		private MessageCode(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

	public enum FileType {
		WORD("application/msword"), EXCEL("application/ms-excel"), PDF("application/pdf");
		private String type;

		private FileType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

	/**
	 * @Description: Load authorization
	 * @Return: void
	 */
	public void getAuthorization(PFCode pfcode) throws Exception {
		if (getUser() == null) {
			getLogger().debug("Session expired. , sessionId: " + SessionUtil.getId() + " , user: " + getUser());
			throw new AuthenticateException(DefaultExceptionMessage.SESSION_EXPIRED);
		}
		this.ATH = AUTHORIZATION_SERVICE.checkAuthorize(pfcode, getUser().getMapOperator());
		setP_CODE(pfcode.getProgramCode());
		this.PF_CODE = pfcode;
	}

	public static InhouseUser getUser() {
		return (InhouseUser) getCommonUser();
	}
	
	public void checkAuthorization(String function) throws AuthorizationException {
		try {
			setF_CODE(null);
			if (function.equals(PF_CODE.getSearchFunction())) {
				if (ATH.isSearch()) {
					setF_CODE(PF_CODE.getSearchFunction());
				}
			} else if (function.equals(PF_CODE.getAddFunction())) {
				if (ATH.isAdd()) {
					setF_CODE(PF_CODE.getAddFunction());
				}
			} else if (function.equals(PF_CODE.getEditFunction())) {
				if (ATH.isEdit()) {
					setF_CODE(PF_CODE.getEditFunction());
				}
			} else if (function.equals(PF_CODE.getViewFunction())) {
				if (ATH.isView()) {
					setF_CODE(PF_CODE.getViewFunction());
				}
			} else if (function.equals(PF_CODE.getDeleteFunction())) {
				if (ATH.isDelete()) {
					setF_CODE(PF_CODE.getDeleteFunction());
				}
			} else if (function.equals(PF_CODE.getChangeFunction())) {
				if (ATH.isChange()) {
					setF_CODE(PF_CODE.getChangeFunction());
				}
			} else if (function.equals(PF_CODE.getPrintFunction())) {
				if (ATH.isPrint()) {
					setF_CODE(PF_CODE.getPrintFunction());
				}
			} else if (function.equals(PF_CODE.getImportFunction())) {
				if (ATH.isImport()) {
					setF_CODE(PF_CODE.getImportFunction());
				}
			} else if (function.equals(PF_CODE.getExportFunction())) {
				if (ATH.isExport()) {
					setF_CODE(PF_CODE.getExportFunction());
				}
			} else if (function.equals(PF_CODE.getCloseFunction())) {
				if (ATH.isClose()) {
					setF_CODE(PF_CODE.getCloseFunction());
				}
			} else if (function.equals(PF_CODE.getCancelFunction())) {
				if (ATH.isCancel()) {
					setF_CODE(PF_CODE.getCancelFunction());
				}
			} else if (function.equals(PF_CODE.getApproveFunction())) {
				if (ATH.isApprove()) {
					setF_CODE(PF_CODE.getApproveFunction());
				}
			} else if (function.equals(PF_CODE.getSynFunction())) {
				if (ATH.isSyn()) {
					setF_CODE(PF_CODE.getSynFunction());
				}
			} else {
				throw new AuthorizationException();
			}

			if (getF_CODE() == null) {
				throw new AuthorizationException();
			}

		} catch (AuthorizationException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Set message for datatable
	 */
	public void setMessage(InhouseModel model, ActionMessageType type, String subject, String detail) {
		model.getMessageAjax().setMessageType(type.getType());
		model.getMessageAjax().setMessage(StringUtil.escapeJavascript(subject));
		model.getMessageAjax().setMessageDetail(StringUtil.escapeJavascript(detail));
	}

	/**
	 * Set message for datatable
	 */
	public void setMessageAlert(InhouseModel model, ActionMaxExceedType type, String msg) {
		model.getMessageAjax().setMessageType(type.getType());
		model.getMessageAjax().setMessage(msg);
	}

	private void setMessageConfrimMaxExceed(String message) {
		setAlertMaxExceed(ActionMaxExceedType.CONFIRM.getType());
		clearMessagesException();
		addActionMessage(message);
	}

	protected void setMessageAlertMaxExceed(String message) {
		setAlertMaxExceed(ActionMaxExceedType.ALERT.getType());
		clearMessagesException();
		addActionMessage(message);
	}

	/**
	 * initial ระบบกรณีที่ Initial ที่มีเงื่อนไข return ไปที่ init
	 *
	 * @param conn
	 * @param model
	 * @param criteria
	 * @param function
	 * @param pageType
	 * @return
	 * @throws Exception
	 */
	public String manageInitial(CCTConnection conn, InhouseModel model, SearchCriteria criteria, String function, PageType pageType) throws Exception {

		checkAuthorization(function);

		String result = ActionReturnType.INIT.getResult();

		model.setPage(pageType);

		if (model.getCriteria().getCriteriaKey() != null && (model.getCriteria().getCriteriaKey().isEmpty() == false)) {
			SessionUtil.remove(model.getCriteria().getCriteriaKey());
			model.getCriteria().setCriteriaKey("");
		}

		model.setCriteria(criteria);

		model.getCriteria().setStart(1);

		// default line per page = 100
		model.getCriteria().setLinePerPage(Integer.parseInt(LPP[0]));

		model.getCriteria().setCheckMaxExceed(true);

		/**
		 * Anusorn.l 2015-06-19 orderSortsSelect datatable
		 **/
		model.getCriteria().setDefaultHeaderSorts();

		if (model.getCriteria().getHeaderSortsSelect() != null) {
			String[] hSelect = model.getCriteria().getHeaderSortsSelect().split(",");
			String order = "";
			for (String head : hSelect) {
				order += "," + model.getCriteria().getHeaderSorts()[Integer.parseInt(head)].getOrder();
			}
			model.getCriteria().setOrderSortsSelect(order.substring(1));
		}

		if (function != null) {
//			LogManager logManager = new LogManager(conn, log);
//			logManager.addEvent(conn, function, getUserIdFromSession(), model);
		}
		return result;
	}

	/**
	 * initial ระบบกรณีที่ต้องการหน้าแรกไม่มี criteria เงื่อนไข return ไปที่
	 * init
	 *
	 * @param conn
	 * @param model
	 * @param function
	 * @param pageType
	 * @return
	 * @throws Exception
	 */
	public String manageInitial(CCTConnection conn, InhouseModel model, String function, PageType pageType) throws Exception {

		checkAuthorization(function);

		String result = ActionReturnType.INIT.getResult();

		model.setPage(pageType);

		if (function != null) {
//			LogManager logManager = new LogManager(conn, log);
//			logManager.addEvent(conn, function, getUserIdFromSession(), model);
		}
		return result;
	}

	/**
	 * Arunya.k ยุบ Code มาไว้ที่เดียว Action ที่ Extend ไปได้มี Code ไม่ยาว
	 * สำหรับตรวจสอบสิทธิ์หน้าแก้ไข, จัดการเงือนไขการค้นหา และ return ไปที่
	 * search
	 *
	 * @param conn
	 * @param model
	 * @param criteriaModel
	 * @param function
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageSearch(CCTConnection conn, InhouseModel model, SearchCriteria criteriaModel, String function) throws AuthorizationException {
		checkAuthorization(function);
		String result = ActionReturnType.SEARCH.getResult();

		if ((model.getCriteria().getCriteriaKey() == null) || (model.getCriteria().getCriteriaKey().equals(""))) {
			model.getCriteria().setDefaultHeaderSorts();
			// เพิ่มเก็บ url ของ search action เพื่อ ให้ตัวกลางวาด table ใช้ตอน
			ActionMapping actionMapping = (ActionMapping) ActionContext.getContext().get("struts.actionMapping");
			model.getCriteria().setUrlSearchAction(actionMapping.getNamespace() + "/" + actionMapping.getName() + "." + actionMapping.getExtension());
		}

		model.setPage(InhouseModel.PageType.SEARCH);
		if ((criteriaModel.getCriteriaKey() == null) || (criteriaModel.getCriteriaKey().equals(""))) {
			getLogger().debug("3. Check criteria...[GENERATE]");
			// clearSearchCriteria(criteriaModel.getClass().getName()); เน�เธ�เน�เน�เธ� error เธ•เธญเธ�เน€เธ�เธดเธ” tab เน�เธซเธกเน�
			String criteriaKey = String.valueOf(Calendar.getInstance().getTimeInMillis());
			getLogger().debug("3.1 Check criteria...[GENERATE] , criteriaKey : " + criteriaKey + " , userId: " + getUser().getId());
			criteriaModel.setCriteriaKey(criteriaKey);

			criteriaModel.setStart(1);
			criteriaModel.setCheckMaxExceed(true);

			SessionUtil.put(criteriaModel.getCriteriaKey(), criteriaModel);
		} else {
			getLogger().debug("3. Check criteria...[LOAD]: " + criteriaModel.getCriteriaKey() + ", sessionId: " + SessionUtil.getId() + " , userId: " + getUser().getId());
			SearchCriteria criteriaSession = (SearchCriteria) SessionUtil.get(criteriaModel.getCriteriaKey());
			getLogger().debug("4. criteriaSession: " + criteriaSession);
			criteriaSession.setCriteriaKey(criteriaModel.getCriteriaKey());

			if (criteriaModel.getStart() > 0) {
				criteriaSession.setStart(criteriaModel.getStart());
				criteriaSession.setCheckMaxExceed(criteriaModel.isCheckMaxExceed());
			}

			getLogger().debug("   Check header sorts select...[" + criteriaModel.getHeaderSortsSelect() + "]");
			if (criteriaModel.getHeaderSortsSelect() != null) {
				criteriaSession.setHeaderSorts(criteriaModel.getHeaderSorts());
				criteriaSession.setHeaderSortsSelect(criteriaModel.getHeaderSortsSelect());
			}

			criteriaModel = criteriaSession;
			model.setCriteria(criteriaModel);

		}

		if (function != null) {
//			LogManager logManager = new LogManager(conn, log);
//			logManager.addEvent(conn, function, getUserIdFromSession(), model);
		}

		return result;
	}

	/**
	 * manageSearchAjax
	 *
	 * @param conn
	 * @param model
	 * @param criteriaModel
	 * @param function
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageSearchAjax(CCTConnection conn, InhouseModel model, SearchCriteria criteriaModel, String function) throws AuthorizationException {

		checkAuthorization(function);
		String result = ActionReturnType.SEARCH_AJAX.getResult();
		
		//INIT HEADER SORT FOR MULTI DATATABLE
		criteriaModel.initHeaderSorts();

		if ((model.getCriteria().getCriteriaKey() == null) || (model.getCriteria().getCriteriaKey().equals(""))) {
			model.getCriteria().setDefaultHeaderSorts();

			/** Perapol.p เพิ่มการเก็บตัวแปร ให้รทำการเก็บ log event สำหรับ  method managerSearchResult **/
			model.setChkSearch("Y");

			/** Anusorn.l 2015-06-19 เพิ่มการเก็บตัวแปร  orderSortsSelect ใช้สำหรับ datatable **/
			model.getCriteria().setDefaultHeaderSorts();

			if (model.getCriteria().getHeaderSortsSelect() != null && !model.getCriteria().getHeaderSortsSelect().equals("")) {
				String[] hSelect = model.getCriteria().getHeaderSortsSelect().split(",");
				String order = "";
				for (String head : hSelect) {
					order += "," + model.getCriteria().getHeaderSorts()[Integer.parseInt(head)].getOrder();
				}
				model.getCriteria().setOrderSortsSelect(order.substring(1));
			}
			/** Anusorn.l 2015-06-19 **/

			// เพิ่มเก็บ url ของ search action เพื่อ ให้ตัวกลางวาด table ใช้ตอน
			ActionMapping actionMapping = (ActionMapping) ActionContext.getContext().get("struts.actionMapping");
			model.getCriteria().setUrlSearchAction(actionMapping.getNamespace() + "/" + actionMapping.getName() + "." + actionMapping.getExtension());
		}

		model.setPage(InhouseModel.PageType.SEARCH);
		if ((criteriaModel.getCriteriaKey() == null) || (criteriaModel.getCriteriaKey().equals(""))) {
			getLogger().info("3. Check criteria...[GENERATE]");
//			clearSearchCriteria(criteriaModel.getClass().getName()); comment เพื่อทดสอบ BlacklistAlert Criteria ใน Session หาย
			String criteriaKey = String.valueOf(Calendar.getInstance().getTimeInMillis());
			//.info("3.1 Check criteria...[GENERATE] , criteriaKey : " + criteriaKey + " , userId: " + getUser().getUserId());

			criteriaModel.setCriteriaKey(criteriaKey);

			criteriaModel.setStart(1);
			//criteriaModel.setCheckMaxExceed(true);

			if(criteriaModel.isAlertMaxExceed()) {
				criteriaModel.setCheckMaxExceed(false);
				criteriaModel.setAlertMaxExceed(false);
			} else {
				criteriaModel.setCheckMaxExceed(true);
			}

			SessionUtil.put(criteriaModel.getCriteriaKey(), criteriaModel);
		} else {
			//getLogger().info("3. Check criteria...[LOAD]: " + criteriaModel.getCriteriaKey() + ", sessionId: " + SessionUtil.getId() + ", userId: " + getUser().getUserId());
			SearchCriteria criteriaSession = (SearchCriteria) SessionUtil.get(criteriaModel.getCriteriaKey());
			getLogger().info("4. criteriaSession: " + criteriaSession);

			// แก้ไขกรณี criteriaKey มี แต่ criteriaSession ไม่มี
			if (criteriaSession == null) {
				criteriaSession = criteriaModel;
				SessionUtil.put(criteriaModel.getCriteriaKey(), criteriaModel);
				getLogger().info("5. criteriaSession: is null set criteriaSession = criteriaModel. " + criteriaSession);
			}

			criteriaSession.setCriteriaKey(criteriaModel.getCriteriaKey());

			String startStr = (String) SessionUtil.requestParameter("start");
			int start = startStr == null ? 0 : Integer.parseInt(startStr);
			if (criteriaModel.getStart() > 0) {
				criteriaSession.setStart(start + 1);
				criteriaSession.setCheckMaxExceed(criteriaModel.isCheckMaxExceed());
			}

			int orderableCount = 0;
			String orderCol[] = null;
			String orderDir[] = null;
			if (model.getCriteria().getHeaderSortsSelect() != null) {
				orderableCount = model.getCriteria().getHeaderSortsSelect().split(",").length;

				//จำนวน column sorting
				orderCol = new String[orderableCount];
				orderDir = new String[orderableCount];
				for (int i = 0; i < orderableCount; i++) {
					orderCol[i] = (String) SessionUtil.requestParameter("order[" + i + "][column]");
					orderDir[i] = (String) SessionUtil.requestParameter("order[" + i + "][dir]");
				}

				StringBuilder headerSortsSelect = new StringBuilder();
				StringBuilder orderSortsSelect = new StringBuilder();
				for (int i = 0; i < orderCol.length; i++) {
					if (orderCol[i] != null) {
						headerSortsSelect.append(",").append(orderCol[i]);
						orderSortsSelect.append(",").append(orderDir[i]);
						criteriaModel.getHeaderSorts()[Integer.parseInt(orderCol[i])].setOrder(orderDir[i].toUpperCase());
					}
				}

				// getLogger().debug("   Check header sorts select...[" + criteriaModel.getHeaderSortsSelect() + "]");
				if (criteriaModel.getHeaderSortsSelect() != null && !criteriaModel.getHeaderSortsSelect().equals("") && !criteriaModel.getHeaderSortsSelect().equals("0")) {
					criteriaSession.setHeaderSorts(criteriaModel.getHeaderSorts());
					criteriaSession.setHeaderSortsSelect(headerSortsSelect.toString().isEmpty() ? "" : headerSortsSelect.toString().substring(1));
					criteriaSession.setOrderSortsSelect(orderSortsSelect.toString().isEmpty() ? "" : orderSortsSelect.toString().substring(1));
				}
			} else if (criteriaSession.getHeaderSortsSelect() != null && !criteriaSession.getHeaderSortsSelect().equals("")) {
				String[] hSelect = criteriaSession.getHeaderSortsSelect().split(",");
				String order = "";
				for (String head : hSelect) {
					order += "," + model.getCriteria().getHeaderSorts()[Integer.parseInt(head)].getOrder();
				}
				model.getCriteria().setOrderSortsSelect(order.substring(1));
			}

			criteriaModel = criteriaSession;
			model.setCriteria(criteriaModel);

		}

		if (function != null) {
//			LogManager logManager = new LogManager(conn, log);
//			logManager.addEvent(conn, function, getUserIdFromSession(), model);
		}

		return result;
	}

	/**
	 * สำหรับจัดการก่อนเข้าหน้าเพิ่ม return ไปที่ addEdit
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageGotoAdd(CCTConnection conn, InhouseModel model) throws AuthorizationException {

		return manageGotoAdd(conn, model,  ActionReturnType.ADD_EDIT);
	}

	/**
	 * สำหรับจัดการก่อนเข้าหน้าเพิ่ม return ไปที่ addEdit
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageGotoAdd(CCTConnection conn, InhouseModel model, ActionReturnType returnType) throws AuthorizationException {

		checkAuthorization(PF_CODE.getAddFunction());

		String result = returnType.getResult();

		model.setPage(InhouseModel.PageType.ADD);

//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, PF_CODE.getAddFunction(), getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());
		
		return result;
	}

	/**
	 * สำหรับจัดการหลังบันทึกเพิ่มเสร็จ return ไปที่ addEdit
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageAdd(CCTConnection conn, InhouseModel model) throws AuthorizationException {
		return manageAdd(conn, model, ActionResultType.BASIC);
	}


	/**
	 * สำหรับจัดการหลังบันทึกเพิ่มเสร็จ return ไปที่ addEdit
	 * รับ Message Code เพิ่ม  กรณี แจ้ง Susscess ไม่เป็น messge Default
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageAdd(CCTConnection conn, InhouseModel model ,String messageCode) throws AuthorizationException {
		return manageAdd(conn, model, ActionResultType.BASIC ,messageCode);
	}

	/**
	 * สำหรับจัดการหลังบันทึกเพิ่มเสร็จ return ไปที่ addEdit
	 * @param conn
	 * @param model
	 * @param resultType
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageAdd(CCTConnection conn, InhouseModel model, ActionResultType resultType) throws AuthorizationException {
		return manageAdd(conn, model, resultType, "30003");
	}

	/**
	 *  * สำหรับจัดการหลังบันทึกเพิ่มเสร็จ return ไปที่ addEdit
	 * 	ปรับเปลี่ยนจาก Framework เดิม  โดย
	 * 	 page  == ADD_VIEW
	 * @param conn
	 * @param model
	 * @param resultType
	 * @param messageCode
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageAdd(CCTConnection conn, InhouseModel model, ActionResultType resultType ,String messageCode) throws AuthorizationException {

		checkAuthorization(PF_CODE.getAddFunction());

		String result = ActionReturnType.ADD_EDIT.getResult();

		setMessage(ActionMessageType.SUCCESS, getText(messageCode), resultType);

		model.setPage(InhouseModel.PageType.ADD);

//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, PF_CODE.getAddFunction(), getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());
		
		return result;
	}

	/**
	 * สำหรับจัดการก่อนเข้าหน้าแก้ไข return ไปที่ addEdit
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 * @throws IOException
	 * @throws DuplicateException
	 */
	public String manageGotoEdit(CCTConnection conn, InhouseModel model) throws AuthorizationException, IOException {

		checkAuthorization(PF_CODE.getEditFunction());

		if (model.getCriteria() != null && model.getCriteria().getCriteriaKey() == null) {
			String urlInit = ServletActionContext.getRequest().getContextPath() + ServletActionContext.getActionMapping().getNamespace() + "/"
					+ ServletActionContext.getActionMapping().getName().replace("gotoEdit", "init") + ".action";
			ServletActionContext.getResponse().sendRedirect(urlInit);
		}

		String result = ActionReturnType.ADD_EDIT.getResult();

		model.setPage(InhouseModel.PageType.EDIT);

		model.setUrlEdit(ServletActionContext.getRequest().getContextPath() + ServletActionContext.getActionMapping().getNamespace() + "/"
				+ ServletActionContext.getActionMapping().getName() + ".action");

//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, PF_CODE.getEditFunction(), getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());

		return result;
	}

	/**
	 * สำหรับจัดการหลังบันทึกแ้ก้ไขเสร็จ return ไปที่ searchDo
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageEdit(CCTConnection conn, InhouseModel model) throws AuthorizationException {
		return manageEdit(conn, model, ActionResultType.CHAIN);
	}

	/**
	 * สำหรับจัดการหลังบันทึกแ้ก้ไขเสร็จ return ไปที่  addEdit
	 *
	 * @param conn
	 * @param model
	 * @param resultType
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageEdit(CCTConnection conn, InhouseModel model, ActionResultType resultType) throws AuthorizationException {

		return manageEdit(conn, model, resultType, "30006");
	}

	/**
	 * * สำหรับจัดการหลังบันทึกแ้ก้ไขเสร็จ return ไปที่ addEdit
	 *
	 * ปรับเปลี่ยนจาก Framework เดิม  โดย
	 * 	 result  == ADD_EDIT
	 *   page == EDIT_VIEW
	 *
	 * @param conn
	 * @param model
	 * @param resultType
	 * @param messageCode
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageEdit(CCTConnection conn, InhouseModel model, ActionResultType resultType ,String messageCode) throws AuthorizationException {

		checkAuthorization(PF_CODE.getEditFunction());

		String result = ActionReturnType.SEARCH_DO.getResult();

		setMessage(ActionMessageType.SUCCESS, getText(messageCode), resultType);

		model.setPage(InhouseModel.PageType.EDIT);

//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, PF_CODE.getEditFunction(), getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());
		
		return result;
	}



	/**
	 * สำหรับจัดการก่อนเข้าหน้าแสดง return ไปที่ addEdit
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 * @throws IOException
	 */
	public String manageGotoView(CCTConnection conn, InhouseModel model) throws AuthorizationException, IOException {

		checkAuthorization(PF_CODE.getViewFunction());

		if (model.getCriteria() != null && model.getCriteria().getCriteriaKey() == null) {
			String urlInit = ServletActionContext.getRequest().getContextPath() + ServletActionContext.getActionMapping().getNamespace() + "/"
					+ ServletActionContext.getActionMapping().getName().replace("gotoView", "init") + ".action";
			ServletActionContext.getResponse().sendRedirect(urlInit);
		}

		String result = ActionReturnType.ADD_EDIT.getResult();

		model.setPage(InhouseModel.PageType.VIEW);
		
//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, PF_CODE.getViewFunction(), getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());
		
		return result;
	}

	/**
	 * สำหรับจัดการหลังบันทึกสถานะเสร็จ return ไปที่ searchDo
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageUpdateActive(CCTConnection conn, InhouseModel model) throws AuthorizationException {
		return manageUpdateActive(conn, model, ActionResultType.CHAIN);
	}

	/**
	 * สำหรับจัดการหลังบันทึกสถานะเสร็จ return ไปที่ searchDo
	 *
	 * @param conn
	 * @param model
	 * @param resultType
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageUpdateActive(CCTConnection conn, InhouseModel model, ActionResultType resultType) throws AuthorizationException {

		checkAuthorization(PF_CODE.getChangeFunction());

		String result = ActionReturnType.SEARCH_DO.getResult();

		if (model.getCriteria().getStatusForUpdate().equals("Y")) {
			setMessage(model, ActionMessageType.SUCCESS, getText("30001"), "");
			setMessage(ActionMessageType.SUCCESS, getText("30001"), resultType);

		} else if (model.getCriteria().getStatusForUpdate().equals("N")) {
			setMessage(model, ActionMessageType.SUCCESS, getText("30002"), "");
			setMessage(ActionMessageType.SUCCESS, getText("30002"), resultType);

		} else if (model.getCriteria().getStatusForUpdate().equals("P")) {
			// กรณีที่ เป็นการระงับการใช้งาน ACTIVE = P Msg = 30019 :
			// เปลี่ยนเป็นสถานะระงับใช้งานเรียบร้อยแล้ว
			setMessage(model, ActionMessageType.SUCCESS, getText("30019"), "");
			setMessage(ActionMessageType.SUCCESS, getText("30019"), resultType);
		}

//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, PF_CODE.getChangeFunction(), getUserIdFromSession(), model);
		
		return result;
	}

	/**
	 * สำหรับจัดการหลังบันทึกสถานะเสร็จ return ไปที่ searchDo
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageDelete(CCTConnection conn, InhouseModel model) throws AuthorizationException {
		return manageDelete(conn, model, ActionResultType.CHAIN);
	}


	/**
	 * สำหรับจัดการหลังบันทึกสถานะเสร็จ return ไปที่ searchDo
	 *
	 * @param conn
	 * @param model
	 * @param resultType
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageDelete(CCTConnection conn, InhouseModel model, ActionResultType resultType) throws AuthorizationException {

		checkAuthorization(PF_CODE.getDeleteFunction());

		String result = ActionReturnType.SEARCH_DO.getResult();

		setMessage(ActionMessageType.SUCCESS, getText("30008"), resultType);

//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, PF_CODE.getDeleteFunction(), getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());
		
		return result;
	}

	/**
	 * สำหรับจัดการหลังส่งออกข้อมูลเสร็จ return ไปที่ download
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageExport(CCTConnection conn, InhouseModel model) throws AuthorizationException {

		checkAuthorization(PF_CODE.getExportFunction());

		String result = ActionReturnType.DOWNLOAD.getResult();

		model.setPage(InhouseModel.PageType.PRINT);

//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, PF_CODE.getExportFunction(), getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());
		
		return result;
	}

	/**
	 * สำหรับจัดการหลังนำเข้าข้อมูลเสร็จ return ไปที่ upload
	 *
	 * @param conn
	 * @param model
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageImport(CCTConnection conn, InhouseModel model) throws AuthorizationException {
		return manageImport(conn, model, ActionResultType.BASIC);
	}

	/**
	 * สำหรับจัดการหลังนำเข้าข้อมูลเสร็จ return ไปที่ upload
	 *
	 * @param conn
	 * @param model
	 * @param resultType
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageImport(CCTConnection conn, InhouseModel model, ActionResultType resultType) throws AuthorizationException {

		checkAuthorization(PF_CODE.getImportFunction());

		String result = ActionReturnType.UPLOAD.getResult();

		setMessage(ActionMessageType.SUCCESS, getText("30003"), resultType);

		model.setPage(InhouseModel.PageType.ADD);

//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, PF_CODE.getImportFunction(), getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());
		
		return result;
	}

	/**
	 * สำหรับจัดการหลังนำเข้าข้อมูลเสร็จ return ไปที่ upload
	 *
	 * @param conn
	 * @param model
	 * @param resultType
	 * @return
	 * @throws AuthorizationException
	 */
	public String manageOther(CCTConnection conn, String functionCode, ActionReturnType returnType, ActionMessageType messageType, String messageCode, ActionResultType resultType,
			PageType pageType, InhouseModel model) throws AuthorizationException {

		checkAuthorization(functionCode);

		String result = returnType.getResult();

		setMessage(model, messageType, getText(messageCode), StringDelimeter.EMPTY.getValue());
		setMessage(messageType, getText(messageCode), resultType);

		model.setPage(pageType);

//		LogManager logManager = new LogManager(conn, log);
//		logManager.addEvent(conn, functionCode, getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());

		return result;
	}


	

	/**
	 * เมื่อเกิด Error ใช้  e.getMessage  โชว์ message
	 * @param conn
	 * @param operatorId  : สำหรับ insert log
	 * @param className  : สำหรับ insert log
	 * @param e
	 * @param model  : สำหรับ insert log
	 * @throws AuthorizationException
	 * @throws ServerValidateException
	 */
	public void manageException(CCTConnection conn, String operatorId, Object className, Exception e, InhouseModel model) throws AuthorizationException,ServerValidateException {
		manageException(conn, operatorId, className, e, model, ActionResultType.BASIC, null ,null);
	}
	
	/**
	 * กรณี ทีมี resultType : BASIC หรือ CHAIN  
	
	 * @param conn
	 * @param operatorId: สำหรับ insert log
	 * @param className : สำหรับ insert log
	 * @param e : สำหรับ insert log และ แสดง message ที่หน้าจอ
	 * @param model : setPage  และ  setMessage ต่างๆ
	 * @param resultType  
	 *  *  (ถ้าเป็นการกลับไปที่หน้า jsp ให้ใช้ Basic, ถ้าเป็นการข้าม
	 *            action ให้ใช้ Chain)
	 * @param message : สำหรับ  แสดง message ที่หน้าจอ ควรส่งเป็น  message ที่พร้อมจะแสดงที่หน้าจอเลย
	 * @throws AuthorizationException
	 * @throws ServerValidateException
	 */
	public void manageException(CCTConnection conn, String operatorId, Object className, Exception e, InhouseModel model, ActionResultType resultType, String message)
			throws AuthorizationException,ServerValidateException {
		manageException(conn, operatorId, className, e, model, resultType, message ,null);
	}
	
	/**
	 * แสดง message เฉพาะ
	 * @param conn
	 * @param operatorId  : สำหรับ insert log
	 * @param className   : สำหรับ insert log
	 * @param e   : สำหรับ insert log และ แสดง message ที่หน้าจอ
	 * @param model  : setPage  และ  setMessage ต่างๆ
	 * @param message  : สำหรับ  แสดง message ที่หน้าจอ ควรส่งเป็น  message ที่พร้อมจะแสดงที่หน้าจอเลย
	 * @throws AuthorizationException
	 * @throws ServerValidateException
	 */
	public void manageException(CCTConnection conn, String operatorId, Object className, Exception e, InhouseModel model, String message)
			throws AuthorizationException,ServerValidateException {
		manageException(conn, operatorId, className, e, model, ActionResultType.BASIC, message ,null);
	}
	
	/**
	 * แสดง message เฉพาะ  page ที่กำหนด
	 * @param conn
	 * @param operatorId  : สำหรับ insert log
	 * @param className   : สำหรับ insert log
	 * @param e  : สำหรับ insert log และ แสดง message ที่หน้าจอ
	 * @param model  : setPage  และ  setMessage ต่างๆ
	 * @param message  : สำหรับ  แสดง message ที่หน้าจอ ควรส่งเป็น  message ที่พร้อมจะแสดงที่หน้าจอเลย
	 * @param pageType  : กำหนด page เพื่อการแสดง ผลที่หน้า jsp
	 *			ex.PageType.EDIT_VIEW ,PageType.ADD ,PageType.EDIT 	
	 *
	 * @throws AuthorizationException
	 * @throws ServerValidateException
	 * @throws AuthenticateException 
	 */
	public void manageException(CCTConnection conn, String operatorId, Object className, Exception e, InhouseModel model, String message ,PageType pageType)
			throws AuthorizationException,ServerValidateException {
		manageException(conn, operatorId, className, e, model, ActionResultType.BASIC, message ,pageType);
	}
	/**
	 * จัดการ Error ต่างๆ โดยสามารถกำหนด ActionResultType และ MessageCode เพิ่มเติมได้
	 *
	 * @param conn
	 * @param operatorId: สำหรับ insert log
	 * @param className: สำหรับ insert log
	 * @param e  : สำหรับ insert log และ แสดง message ที่หน้าจอ
	 * @param model : setPage  และ  setMessage ต่างๆ
	 * @param resultType
	 *            (ถ้าเป็นการกลับไปที่หน้า jsp ให้ใช้ Basic, ถ้าเป็นการข้าม
	 *            action ให้ใช้ Chain)
	 * @param message  : สำหรับ  แสดง message ที่หน้าจอ ควรส่งเป็น  message ที่พร้อมจะแสดงที่หน้าจอเลย
	 *  
	 * @throws AuthorizationException
	 */
	

	/**
	 * จัดการ Error ต่างๆ โดยสามารถกำหนด ActionResultType และ  message และ pageType เพิ่มเติมได้
	 * @param conn
	 * @param operatorId: สำหรับ insert log
	 * @param className: สำหรับ insert log
	 * @param e : สำหรับ insert log และ แสดง message ที่หน้าจอ
	 * @param model : setPage  และ  setMessage ต่างๆ
	 * @param resultType
	 *            (ถ้าเป็นการกลับไปที่หน้า jsp ให้ใช้ Basic, ถ้าเป็นการข้าม
	 *            action ให้ใช้ Chain)
	 * @param message  : สำหรับ  แสดง message ที่หน้าจอ ควรส่งเป็น  message ที่พร้อมจะแสดงที่หน้าจอเลย
	 * 
	 * @throws AuthorizationException
	 * @throws ServerValidateException
	 */
	public void manageException(CCTConnection conn, String operatorId, Object className, Exception e, InhouseModel model, ActionResultType resultType, String message,PageType pageType)
			throws AuthorizationException,ServerValidateException {
		
		if (e instanceof MaxExceedException) {
			setMessageAlert(model, ActionMaxExceedType.CONFIRM, getText(e.getMessage()).replace("xxx", String.valueOf(model.getCriteria().getTotalResult())));
			setMessageConfrimMaxExceed(getText(e.getMessage()).replace("xxx", String.valueOf(model.getCriteria().getTotalResult())));

		} else if (e instanceof MaxExceedAlertException) {
			setMessageAlert(model, ActionMaxExceedType.ALERT, getText(e.getMessage()));
			setMessageAlertMaxExceed(getText(e.getMessage()));

		} else if (e instanceof MaxExceedReportException) {
			setMessageAlert(model, ActionMaxExceedType.ALERT, getText(e.getMessage()));
			setMessageAlertMaxExceed(getText(e.getMessage()));

		} else if (e instanceof DuplicateException) {
			/** แก้ไขการแสดง message ข้อมูลซ้ำ โดยทำการใช้ message ที่รับเข้ามา เนื่องจากแต่ละระบบมี message เป็นของตัวเอง * */
			if (e.getMessage() == null) {
				
			}
			setMessage(ActionMessageType.WARING, getText(e.getMessage()), resultType);
			
			if(e.getCause() != null){				
				//LogManager.addAuditLog(conn, operatorId, e.getCause().getMessage(), getUser());
			}
			
			if(pageType != null){
				model.setPage(pageType);
			}
			
			
		} else if (e instanceof InputValidateException) {

			/** แก้ไขการแสดง message ข้อมูลซ้ำ โดยทำการใช้ message ที่รับเข้ามา เนื่องจากแต่ละระบบมี message เป็นของตัวเอง * */
			setMessage(ActionMessageType.OTHER, getText(manageMessage(e.getCause().getMessage())), resultType);

			if(pageType != null){
				model.setPage(pageType);
			}
			
			
			
		} else if (e instanceof AuthenticateException) {
			setMessage(ActionMessageType.ERROR, getText(e.getMessage()), resultType);

		} else if (e instanceof AuthorizationException) {
			throw (AuthorizationException) e;
			
		} else if (e instanceof ServerValidateException) {
			setMessage(ActionMessageType.ERROR, DefaultExceptionMessage.SERVER_VALIDATE, ActionResultType.CHAIN);
			throw (ServerValidateException) e;
			
		} else if (e instanceof CustomException) {
			setMessage(ActionMessageType.WARING, getText(e.getMessage()),resultType);
			
		} else {
			getLogger().error("userId: " + getUserIdFromSession() + ", sessionId: " + SessionUtil.getId() + " , className: " + className + ", model: " + model, e);

			//LogManager.addErrorLog(conn, operatorId, className, e, getUser());

			if ((message != null) && (message.trim().isEmpty() == false)) {
				setMessage(ActionMessageType.ERROR, message, getErrorMessage(e), resultType);
				setMessage(model, ActionMessageType.ERROR, message, getErrorMessage(e));
			} else {
				setMessage(ActionMessageType.ERROR, getText(e.getMessage()), getErrorMessage(e), resultType);
				setMessage(model, ActionMessageType.ERROR, getText(e.getMessage()), getErrorMessage(e));
			}
		}
	}

	public void exportFile(HttpServletResponse response, byte[] bytes, String fileName, FileType contentType) {
		exportFile(response, bytes, fileName, contentType.getType());
	}

	public void exportFile(HttpServletResponse response, byte[] bytes, String fileName, String contentType) {
		getLogger().info("exportFile");
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setContentType(contentType);
			response.setContentLength(bytes.length);
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	public void exportFilePreview(HttpServletResponse response, byte[] bytes, String fileName, String contentType) {
		getLogger().info("exportFile");
		try {
			response.setHeader("Content-Disposition", "inline;filename=" + fileName);
			response.setContentType(contentType);
			response.setContentLength(bytes.length);
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	public void exportExcelFile(XSSFWorkbook workbook, String fileName) {
		getLogger().info("exportExcelFile");
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	/**
	 * สำหรับ upload เอกสาร โดยไม่แสดง thumbnail
	 *
	 * @return String
	 */
	public String browseWithoutThumbnail() {
		getLogger().debug("");
		try {

			Object[] tmpFile = createTemp();

			ValueStack valueStack = (ValueStack) ServletActionContext.getRequest().getAttribute("struts.valueStack");
			InhouseModel model = (InhouseModel) valueStack.getRoot().get(0);

			FileManagerUtil.crateDirectoryWithoutOverwrite(getParameter().getAttachFile().getDefaultTempPath());
			BrowseUploadServiceUtil.performBrowse(tmpFile[1].toString(), model.getFileMeta(), false);

			model.getFileMeta().setFileUploadFileNameTmp((String[]) tmpFile[0]);
		} catch (Exception e) {
			getLogger().error(e);
		}
		return "filemeta";
	}

	/**
	 * สำหรับ upload เอกสาร
	 *
	 * @return String
	 */
	public String browse() {
		try {

			Object[] tmpFile = createTemp();

			ValueStack valueStack = (ValueStack) ServletActionContext.getRequest().getAttribute("struts.valueStack");
			InhouseModel model = (InhouseModel) valueStack.getRoot().get(0);

			String[] fileThumbnail64 = BrowseUploadServiceUtil.performBrowse(tmpFile[1].toString(), model.getFileMeta());

			model.getFileMeta().setFileThumbnail(fileThumbnail64);
			model.getFileMeta().setFileUploadFileNameTmp((String[]) tmpFile[0]);
		} catch (Exception e) {
			getLogger().error(e);
		}
		return "filemeta";
	}

	/**
	 *
	 * @return object-0: filename, object-1: filename with path
	 * @throws Exception
	 */
	private Object[] createTemp() throws Exception {

		Object[] returnTmp = new Object[2];

		String[] tmpFile = { Calendar.getInstance(getParameter().getApplication().getDatabaseLocale()).getTimeInMillis() + ".tmp" };
		String tmpFilePath = getParameter().getAttachFile().getDefaultTempPath() + tmpFile[0];

		try {
			getLogger().debug("create tmp: " + tmpFilePath);
			FileManagerUtil.crateDirectoryWithoutOverwrite(getParameter().getAttachFile().getDefaultTempPath());

			returnTmp[0] = tmpFile;
			returnTmp[1] = tmpFilePath;

		} catch (Exception e) {
			throw e;
		}
		return returnTmp;
	}

	/**
	 * Clear temporary file for single upload only
	 *
	 * @return
	 */
	public String clearTemp() {
		String fileDelete = SessionUtil.requestParameter("name");
		try {
			if (fileDelete != null && !fileDelete.equals("")) {
				FileManagerUtil.deleteQuietly(getParameter().getAttachFile().getDefaultTempPath() + fileDelete);
			}
		} catch (Exception e) {
			getLogger().error(e);
		}
		return "filemeta";
	}

	public void setMessagePopup(InhouseModel model, MessageCode msgCode, String msgDecs) {

		if (msgCode != null) {
			// กรณีมีค่า MSG_CODE
			switch (msgCode) {
			case MSG_30011:
				// ไม่พบข้อมูลที่ต้องกาาร
				model.setMessagePopup(ActionMessageType.WARING + "::" + getText("30011"));
				break;
			case MSG_30014:
				// จำนวนข้อมูลที่ค้นพบ = xxx รายการ ต้องการแสดงข้อมูลหรือไม่ ?
				model.setMessagePopup(getText("30014").replace("xxx", msgDecs));
				break;
			case MSG_30018:
				// จำนวนข้อมูลที่ค้นพบมีจำนวนมาก กรุณาระบุเงื่อนไขในการค้นหา
				model.setMessagePopup(ActionMessageType.WARING + "::" + getText("30018"));
				break;
			default:
				break;
			}
		} else {
			// กรณี Exception
			model.setMessagePopup(MessageType.ERROR + "::" + getText("30010") + "::" + msgDecs);
		}

	}

	public void manageExceedExceptionPopup(InhouseModel model, SearchCriteria criteria) {
		if (criteria.getNavigatePopup().equals("true")) {
			setMessagePopup(model, MessageCode.MSG_30014, String.valueOf(criteria.getTotalResult()));
		} else {
			setMessagePopup(model, MessageCode.MSG_30018, StringDelimeter.EMPTY.getValue());
		}
	}

	public void manageInitialPopup(SearchCriteria criteria) throws Exception {

		try {
			criteria.setStart(1);
			criteria.setLinePerPage(100);
			criteria.setTotalResult(0);
		} catch (Exception e) {
			throw e;
		}

	}

	public PFCode getPF_CODE() {
		return PF_CODE;
	}

	/**
	 * จัดการ message การตรวจสอบ validate
	 * @param message
	 * @return
	 */
	public String manageMessage(String message){

		String msg = "";
		String msgArr[] = null;
		try{
			if(StringUtil.stringToNull(message) != null){
				msgArr = message.split(":");
			}

			for(String m : msgArr){
				msg += m + "<br/>";
			}

		}catch (Exception e){
			getLogger().error(e);
		}

		return msg;

	}
	
	public Parameter getParameter() {
		return ParameterConfig.getParameter();
	}

	/**
	 * กำหนดค่าเริ่มต้น Criteria 
	 * @param model
	 * @return
	 */
	public String initCriteria() {
		
		ValueStack valueStack = (ValueStack) ServletActionContext.getRequest().getAttribute("struts.valueStack");
		InhouseModel model = (InhouseModel) valueStack.getRoot().get(0);
		
		if(model.getCriteria().getCriteriaKey() == null || model.getCriteria().getCriteriaKey().isEmpty()) {
			
			model.setCriteria(model.getCriteria());
			
			model.getCriteria().setStart(1);
			model.getCriteria().setCheckMaxExceed(true);

			/**
			 * Anusorn.l 2015-06-19 เพิ่มการเก็บตัวแปร orderSortsSelect ใช้สำหรับ
			 * datatable
			 **/
			model.getCriteria().initHeaderSorts();
			model.getCriteria().setDefaultHeaderSorts();

			if (model.getCriteria().getHeaderSortsSelect() != null) {
				String[] hSelect = model.getCriteria().getHeaderSortsSelect().split(",");
				String order = "";
				for (String head : hSelect) {
					order += "," + model.getCriteria().getHeaderSorts()[Integer.parseInt(head)].getOrder();
				}
				model.getCriteria().setOrderSortsSelect(order.substring(1));
			}
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	public String[] getLPP() {
		String[] lpp = {"10"};
		try {
			lpp = ParameterConfig.getParameter().getApplication().getLpp();

		} catch (Exception e) {
			
		}
		return lpp;
	}
	
	// TODO: Start New version สามารถ Copy ไปไว้ใน Common ได้ ----------------------------------------------
	
	/**
	 * สำหรับจัดการผลลัพธ์การค้นหา กรณีไม่พบข้อมูลให้แสดง message
	 * ไม่พบข้อมูลที่ต้องการ
	 *
	 * @param model
	 * @param lstResult
	 * @param resultType
	 * @throws Exception
	 */
	@Deprecated
	public void manageSearchResult(InhouseModel model, List<?> lstResult, ActionResultType resultType , CCTConnection conn, String outputMessage) throws Exception {
		manageSearchResult(model, lstResult, ActionResultType.BASIC, conn, outputMessage ,"30004");
	}

	/**
	 * สำหรับจัดการผลลัพธ์การค้นหา กรณีไม่พบข้อมูลให้แสดง message
	 * ไม่พบข้อมูลที่ต้องการ
	 *
	 * @param model
	 * @param lstResult
	 * @throws Exception
	 */
	@Deprecated
	public void manageSearchResult(InhouseModel model, List<?> lstResult, ActionResultType resultType , CCTConnection conn, String outputMessage ,String msgCode) throws Exception {


		try {
			if (lstResult == null || lstResult.size() == 0) {
				setMessage(model, ActionMessageType.WARING, getText(msgCode), StringDelimeter.EMPTY.getValue());
				setMessage(ActionMessageType.WARING, getText(msgCode), resultType);
				//outputMessage = outputMessage + ":" + getText("30004");

			} else {
				model.setLstResult(lstResult);
				model.setRecordsTotal(model.getCriteria().getTotalResult());
				model.setRecordsFiltered(model.getCriteria().getTotalResult());
				model.setResult(lstResult);
				//outputMessage = outputMessage + ":" + getText("30031");
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * เปลี่ยนไปใช้ manageSearchResult(InhouseModel model, List<?> listResult, CCTConnection conn); แทน<br>
	 * <br>
	 * สำหรับจัดการผลลัพธ์การค้นหา กรณีไม่พบข้อมูลให้แสดง message
	 * ไม่พบข้อมูลที่ต้องการ
	 *
	 * @param model
	 * @param lstResult
	 * @throws Exception
	 */
	@Deprecated
	public void manageSearchResult(InhouseModel model, List<?> lstResult, CCTConnection conn, String outputMessage) throws Exception {
		manageSearchResult(model, lstResult, ActionResultType.BASIC, conn, outputMessage);
	}
	
	/**
	 * เปลี่ยนไปใช้ เปลี่ยนไปใช้ manageSearchResult(InhouseModel model, List<?> listResult, CCTConnection conn); แทน<br>
	 * @param model
	 * @param criteria
	 * @param listResultPopup
	 * @throws Exception
	 */
	@Deprecated
	public void manageSearchResultPopup(InhouseModel model, SearchCriteria criteria, List<?> listResultPopup) throws Exception {
		try {
			if (listResultPopup == null || listResultPopup.size() == 0) {
				setMessagePopup(model, MessageCode.MSG_30011, StringDelimeter.EMPTY.getValue());
				criteria.setStart(1);
				criteria.setTotalResult(0);
			} else {

			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * @param model
	 * @param listResult
	 * @param conn
	 * @throws Exception
	 */
	public void manageSearchResult(InhouseModel model, List<?> listResult, CCTConnection conn) throws Exception {
		manageSearchResult(model, listResult, ActionResultType.BASIC, conn, null);
	}
	
	/**
	 * ใช้ดึง และจัดการ Message จาก Bundle<br>
	 * ถ้าส่ง objectForSet เข้ามา Message ที่ได้จะอยู่ในรูปแบบ Message Format
	 * @param messageCode
	 * @param objectForSet
	 * @return
	 */
	public String generateMessageResponse(String messageCode, Object... objectForSetMessage) {
		String messageDesc = messageCode;
		try {
			messageDesc = getText(messageDesc);
			
			if (objectForSetMessage.length > 0) {
				messageDesc = convertMessageFormatToMesageValue(messageDesc, objectForSetMessage);
			}
		} catch (Exception e) {
			getLogger().error(e);
		}
		
		return messageDesc;
	}
	
	
	/**
	 * แปลง Message Format ให้เป็น Message ที่ใช้งาน
	 * @param messageFormat
	 * @param objectForSet
	 * @return
	 */
	public String convertMessageFormatToMesageValue(String messageFormat, Object... objectForSetMessage) {
		return String.format(messageFormat, objectForSetMessage);
	}
}
