package com.cct.inhouse.timeoffset.web.menu.action;


import org.apache.log4j.Logger;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

import com.cct.enums.ActionReturnType;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.timeoffset.core.menu.domain.MenuModel;
import com.cct.inhouse.timeoffset.util.log.LogUtil;
import com.opensymphony.xwork2.ModelDriven;

public class MenuAction extends InhouseAction implements ModelDriven<MenuModel>{

	private static final long serialVersionUID = -257515186126025036L;

	private MenuModel model = new MenuModel();
	
	public String init() throws Exception {
		
		getLogger().debug("init");
		
		String result = ActionReturnType.INIT.getResult();
		CCTConnection conn = null;
		
		try {
			
		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
		return result;
	}
	
	@Override
	public MenuModel getModel() {
		return model;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.TO_PERMISSION;
	}

}
