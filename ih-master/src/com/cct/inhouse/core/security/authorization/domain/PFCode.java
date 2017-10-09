package com.cct.inhouse.core.security.authorization.domain;

import java.util.Map;

import org.apache.struts2.json.JSONUtil;

import util.log.DefaultLogUtil;


public enum PFCode {
	// ---------: Program code and Function code :-----------//
	SEC_CONFIG("10800300", "{'" + FunctionType.SEARCH + "':'10800301' , '" + FunctionType.ADD + "':'10800303' , '" + FunctionType.VIEW + "':'10800304' , '" + FunctionType.CHANGE + "':'10800305' , '" + FunctionType.EDIT + "':'10800302'}")
	
	
	// TO
	, TO_CON_PROJECT("10300100", "{'" + FunctionType.SEARCH + "':'10300101','" + FunctionType.ADD + "':'10300102','" + FunctionType.EDIT + "':'10300103','" + FunctionType.CHANGE + "':'10300105'}")
	, TO_MANAGE("10300200", "{'" + FunctionType.SEARCH + "':'10300201','" + FunctionType.ADD + "':'10300202','" + FunctionType.EDIT + "':'10300203','" + FunctionType.VIEW + "':'10300204','" + FunctionType.DELETE + "':'10300207'}")
	, TO_TODO("10300300", "{'" + FunctionType.SEARCH + "':'10300301','" + FunctionType.APPROVE + "':'10300305'}")
	, TO_APPROVE("10300400", "{'" + FunctionType.SEARCH + "':'10300401','" + FunctionType.APPROVE + "':'10300406','" + FunctionType.VIEW + "':'10300404'}")
	, TO_HISTORY("10300500", "{'" + FunctionType.SEARCH + "':'10300501', '" + FunctionType.PRINT + "':'10300508'}")
	, TO_HISTORY_GENERAL("10300800", "{'" + FunctionType.SEARCH + "':'10300801', '" + FunctionType.PRINT + "':'10300808'}")
	, TO_IN_USE_REPORT("10300900", "{'" + FunctionType.PRINT + "':'10300908'}")
	, TO_REPORT("10300600", "{'" + FunctionType.SEARCH + "':'10300601','" + FunctionType.SYN + "':'10300609','" + FunctionType.PRINT + "':'10300608'}")
	, TO_REPORT_GENERAL("10300700", "{'" + FunctionType.PRINT + "':'10300708'}")
	
	// QA
	, QA_PROJECTS_MANAGEMENT("10600100", "{'" + FunctionType.SEARCH + "':'10600101','" + FunctionType.ADD + "':'10600102','" + FunctionType.EDIT + "':'10600103','" + FunctionType.VIEW + "':'10600104','" + FunctionType.CHANGE + "':'10600105'}")
	, QA_SYSTEMS_MANAGEMENT("10600200", "{'" + FunctionType.SEARCH + "':'10600201','" + FunctionType.ADD + "':'10600202','" + FunctionType.EDIT + "':'10600203','" + FunctionType.VIEW + "':'10600204','" + FunctionType.CHANGE + "':'10600205'}")
	, QA_SUBSYSTEMS_MANAGEMENT("10600300", "{'" + FunctionType.SEARCH + "':'10600301','" + FunctionType.ADD + "':'10600302','" + FunctionType.EDIT + "':'10600303','" + FunctionType.VIEW + "':'10600304','" + FunctionType.CHANGE + "':'10600305'}")
	, QA_SCENARIOS_MANAGEMENT("10600400", "{'" + FunctionType.SEARCH + "':'10600401','" + FunctionType.ADD + "':'10600402','" + FunctionType.EDIT + "':'10600403','" + FunctionType.VIEW + "':'10600404','" + FunctionType.CHANGE + "':'10600405'}")
	, QA_CASES_MANAGEMENT("10600500", "{'" + FunctionType.SEARCH + "':'10600501','" + FunctionType.ADD + "':'10600502','" + FunctionType.EDIT + "':'10600503','" + FunctionType.VIEW + "':'10600504','" + FunctionType.CHANGE + "':'10600505','" + FunctionType.IMPORT + "':'10600506','" + FunctionType.EXPORT + "':'10600507'}")
	, QA_TEST_EXECUTE("10600600", "{'" + FunctionType.SEARCH + "':'10600601','" + FunctionType.ADD + "':'10600602','" + FunctionType.EDIT + "':'10600603','" + FunctionType.VIEW + "':'10600604','" + FunctionType.CHANGE + "':'10600605'}")
	, QA_TEST_EXECUTE_REPORT("10600700", "{'" + FunctionType.SEARCH + "':'10600701','" + FunctionType.VIEW + "':'10600704','" + FunctionType.EXPORT + "':'10600707'}")
		
	// RM
	, RM_NEWS_MANAGEMENT("10800100", "{'" + FunctionType.SEARCH + "':'10800201', '" + FunctionType.ADD + "':'10800202','" + FunctionType.EDIT + "':'10800203', '" + FunctionType.VIEW + "':'10800204','" + FunctionType.DELETE + "':'10800105'}")
	, RM_RESOURCES_TYPE("10800100", "{'" + FunctionType.SEARCH + "':'10800201', '" + FunctionType.ADD + "':'10800202','" + FunctionType.EDIT + "':'10800203', '" + FunctionType.VIEW + "':'10800204','" + FunctionType.DELETE + "':'10800105'}")
	, RM_RESOURCES("10800100", "{'" + FunctionType.SEARCH + "':'10800201', '" + FunctionType.ADD + "':'10800202','" + FunctionType.EDIT + "':'10800203', '" + FunctionType.VIEW + "':'10800204','" + FunctionType.DELETE + "':'10800105'}")

	, PM_TODO("10400100", "{'" + FunctionType.SEARCH + "':'10400101'}")
	, PM_PROJECT_MANAGEMENT("10400200", "{'" + FunctionType.SEARCH + "':'10400201', '" + FunctionType.ADD + "':'10400202','" + FunctionType.EDIT + "':'10400203', '" + FunctionType.VIEW + "':'10400204','" + FunctionType.DELETE + "':'10400205'}")
	, PM_DASHBOARD("10400300", "{'" + FunctionType.SEARCH + "':'10400301'}")
	
	, BK_SETTING("10210000", "{'" + FunctionType.ADD + "':'10210001', '" + FunctionType.EDIT + "':'10210002', '" + FunctionType.VIEW + "':'10210003'}")
	, BK_BOOKING("10220000", "{'" + FunctionType.ADD + "':'10220001', '" + FunctionType.EDIT + "':'10220002', '" + FunctionType.VIEW + "':'10220003'}")
	, BK_DASHBOARD("10230000", "{'" + FunctionType.EDIT + "':'10230001', '" + FunctionType.VIEW + "':'10230002'}")
	
	;
	// ---------: Program code and Function code :-----------
	// ---------: Fucntion code :-----------
	private String searchFunction;
	private String addFunction;
	private String editFunction;
	private String viewFunction;
	private String deleteFunction;
	private String changeFunction;
	private String configFunction;
	private String importFunction;
	private String exportFunction;
	private String printFunction;
	private String cancelFunction;
	private String closeFunction;
	private String loginFunction;
	private String termsAndConditionsFunction;
	private String changePasswordForcedFunction;
	private String changePasswordVoluntaryFunction;
	private String forgotPasswordFunction;

	private String sendEmailResetPassword;
	private String sendEmailAccountExpired;
	private String sendEmailEditCarrier;
	
	// TO
	private String approveFunction;
	private String synFunction;
	// ---------: Fucntion code :-----------

	private String programCode;
	private Map<String, String> funcs = null;

	@SuppressWarnings("unchecked")
	private PFCode(String programCode, String functions) {
		this.programCode = programCode;
		try {
			this.funcs = (Map<String, String>) JSONUtil.deserialize(functions);
			searchFunction = funcs.get(FunctionType.SEARCH);
			addFunction = funcs.get(FunctionType.ADD);
			editFunction = funcs.get(FunctionType.EDIT);
			viewFunction = funcs.get(FunctionType.VIEW);
			deleteFunction = funcs.get(FunctionType.DELETE);
			changeFunction = funcs.get(FunctionType.CHANGE);
			configFunction = funcs.get(FunctionType.CONFIG);
			importFunction = funcs.get(FunctionType.IMPORT);
			exportFunction = funcs.get(FunctionType.EXPORT);
			printFunction = funcs.get(FunctionType.PRINT);
			cancelFunction = funcs.get(FunctionType.CANCEL);
			closeFunction = funcs.get(FunctionType.CLOSE);

			loginFunction = funcs.get(FunctionType.LOGIN);
			termsAndConditionsFunction = funcs.get(FunctionType.TERMSANDCONDITIONS);
			changePasswordForcedFunction = funcs.get(FunctionType.CHANGEPASSWORDFORCED);
			changePasswordVoluntaryFunction = funcs.get(FunctionType.CHANGEPASSWORDVOLUNTARY);

			forgotPasswordFunction = funcs.get(FunctionType.FORGOTPASSWORD);

			sendEmailResetPassword = funcs.get(FunctionType.SEND_E_MAIL_RESET_PASSWORD);
			sendEmailAccountExpired = funcs.get(FunctionType.SEND_E_MAIL_ACCOUNT_EXPIRED);
			sendEmailEditCarrier = funcs.get(FunctionType.SEND_E_MAIL_EDIT_CARRIER);

			// TO
			approveFunction = funcs.get(FunctionType.APPROVE);
			synFunction = funcs.get(FunctionType.SYN);
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}

	public String getProgramCode() {
		return programCode;
	}

	public Map<String, String> getFuncs() {
		return funcs;
	}

	public String getFunctionCode(String programCode, String functionType) {
		String functionCode = null;
		for (PFCode code : PFCode.values()) {
			if (code.getProgramCode().equals(programCode)) {
				functionCode = code.getFuncs().get(functionType);
				break;
			}
		}
		return functionCode;
	}

	public String getSearchFunction() {
		return searchFunction;
	}

	public String getSearchFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.SEARCH);
	}

	public String getAddFunction() {
		return addFunction;
	}

	public String getAddFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.ADD);
	}

	public String getEditFunction() {
		return editFunction;
	}

	public String getEditFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.EDIT);
	}

	public String getViewFunction() {
		return viewFunction;
	}

	public String getViewFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.VIEW);
	}

	public String getDeleteFunction() {
		return deleteFunction;
	}

	public String getDeleteFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.DELETE);
	}

	public String getChangeFunction() {
		return changeFunction;
	}

	public String getChangeFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CHANGE);
	}

	public String getConfigFunction() {
		return configFunction;
	}

	public String getConfigFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CONFIG);
	}

	public String getImportFunction() {
		return importFunction;
	}

	public String getImportFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.IMPORT);
	}

	public String getExportFunction() {
		return exportFunction;
	}

	public String getExportFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.EXPORT);
	}

	public String getPrintFunction() {
		return printFunction;
	}

	public String getPrintFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.PRINT);
	}




	public String getLoginFunction() {
		return loginFunction;
	}

	public String getLoginFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.LOGIN);
	}

	public String getTermsAndConditionsFunction() {
		return termsAndConditionsFunction;
	}

	public String getTermsAndConditionsFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.TERMSANDCONDITIONS);
	}

	public String getChangePasswordForcedFunction() {
		return changePasswordForcedFunction;
	}

	public String getChangePasswordForcedFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CHANGEPASSWORDFORCED);
	}

	public String getChangePasswordVoluntaryFunction() {
		return changePasswordVoluntaryFunction;
	}

	public String getChangePasswordVoluntaryFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CHANGEPASSWORDVOLUNTARY);
	}

	public String getForgotPasswordFunction() {
		return forgotPasswordFunction;
	}

	public String getForgotPasswordFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.FORGOTPASSWORD);
	}

	public String getCancelFunction() {
		return cancelFunction;
	}

	public String getCancelFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CANCEL);
	}


	public String getCloseFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.CLOSE);
	}

	public String getSendEmailResetPassword() {
		return sendEmailResetPassword;
	}

	public String getSendEmailResetPassword(String programCode) {
		return getFunctionCode(programCode, FunctionType.SEND_E_MAIL_RESET_PASSWORD);
	}

	public String getSendEmailAccountExpired() {
		return sendEmailAccountExpired;
	}

	public String getSendEmailAccountExpired(String programCode) {
		return getFunctionCode(programCode, FunctionType.SEND_E_MAIL_ACCOUNT_EXPIRED);
	}

	public String getSendEmailEditCarrier() {
		return sendEmailEditCarrier;
	}

	public String getSendEmailEditCarrier(String programCode) {
		return getFunctionCode(programCode, FunctionType.SEND_E_MAIL_EDIT_CARRIER);
	}

	public String getCloseFunction() {
		return closeFunction;
	}

	public String getSynFunction() {
		return synFunction;
	}

	public String getSynFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.SYN);
	}

	public String getApproveFunction() {
		return approveFunction;
	}

	public String getApproveFunction(String programCode) {
		return getFunctionCode(programCode, FunctionType.APPROVE);
	}
}