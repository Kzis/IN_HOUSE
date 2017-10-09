package com.cct.inhouse.core.security.authorization.service;

import java.util.Map;

import com.cct.domain.Operator;
import com.cct.inhouse.core.security.authorization.domain.Authorize;
import com.cct.inhouse.core.security.authorization.domain.PFCode;


public class AuthorizationService {

	public Authorize checkAuthorize(PFCode pfcode, Map<String, Operator> mapFunction) throws Exception {

		Authorize ath = new Authorize();
		try {
			if (mapFunction.get(pfcode.getAddFunction()) != null) {
				ath.setAdd(true);
			}
			if (mapFunction.get(pfcode.getSearchFunction()) != null) {
				ath.setSearch(true);
			}
			if (mapFunction.get(pfcode.getEditFunction()) != null) {
				ath.setEdit(true);
			}
			if (mapFunction.get(pfcode.getViewFunction()) != null) {
				ath.setView(true);
			}
			if (mapFunction.get(pfcode.getChangeFunction()) != null) {
				ath.setChange(true);
			}
			if (mapFunction.get(pfcode.getConfigFunction()) != null) {
				ath.setConfig(true);
			}
			if (mapFunction.get(pfcode.getImportFunction()) != null) {
				ath.setImport(true);
			}
			if (mapFunction.get(pfcode.getPrintFunction()) != null) {
				ath.setPrint(true);
			}
			if (mapFunction.get(pfcode.getExportFunction()) != null) {
				ath.setExport(true);
			}
			if (mapFunction.get(pfcode.getCloseFunction()) != null) {
				ath.setClose(true);
			}
			if (mapFunction.get(pfcode.getCancelFunction()) != null) {
				ath.setCancel(true);
			}
			
			// TO
			if (mapFunction.get(pfcode.getSynFunction()) != null) {
				ath.setSyn(true);
			}
			if (mapFunction.get(pfcode.getApproveFunction()) != null) {
				ath.setApprove(true);
			}
			if (mapFunction.get(pfcode.getDeleteFunction()) != null) {
				ath.setDelete(true);
			}
		} catch (Exception e) {
			throw e;
		}
		return ath;
	}
}
