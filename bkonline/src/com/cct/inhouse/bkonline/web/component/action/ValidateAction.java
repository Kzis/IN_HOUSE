package com.cct.inhouse.bkonline.web.component.action;

import org.apache.log4j.Logger;

import com.cct.enums.ActionReturnType;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.enums.DBLookup;
import com.opensymphony.xwork2.ModelDriven;

public class ValidateAction extends InhouseAction implements ModelDriven<ValidateModel> {

	private static final long serialVersionUID = -2274119547608930812L;
	
	private ValidateModel model = new ValidateModel();

	public String init() {
	
		return ActionReturnType.INIT.getResult();
	}

	public String add() {
		getLogger().debug("");
		return ActionReturnType.ADD_EDIT.getResult();
	}
	
	@Override
	public ValidateModel getModel() {
		return model;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.INITIAL;
	}
	
	@Override
	public String dbLookupInititial() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}

}
