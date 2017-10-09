package com.cct.inhouse.bkonline.web.report.action;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.enums.ActionReturnType;
import com.cct.inhouse.bkonline.core.report.domain.ReportModel;
import com.cct.inhouse.bkonline.core.report.service.ReportManager;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public class ReportAction extends InhouseAction implements
		ModelDriven<ReportModel> {

	private static final long serialVersionUID = 2737334550572859540L;
	private ReportModel model = new ReportModel();

	public ReportAction() {
		try {
			// getAuthorization(PFCode.SEC_CONFIG);
			this.ATH.setSearch(true);
			this.ATH.setAdd(true);
			this.ATH.setEdit(true);
			this.ATH.setView(true);
			this.ATH.setPrint(true);
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	/**
	 * Let set the first call process
	 * 
	 * @return
	 * @throws Exception
	 */
	public String init() throws Exception {
		getLogger().debug("init");
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn,
					getDbLookup());

		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return ActionReturnType.INIT.getResult();
	}

	// public String ajaxSearchRoom() throws Exception {
	// getLogger().debug("ajaxSearchRoom");
	//
	// CCTConnection conn = null;
	// try {
	// conn = new
	// CCTConnectionProvider().getConnection(conn,getDbLookup());
	//
	// ReportManager manager = new ReportManager(getLogger(),
	// getUser(),getLocale());
	//
	// getModel().setListRoom(manager.searchListRoom(conn,getModel().getEvent()));
	// getModel().setRecordsTotal(getModel().getListRoom().size());
	//
	// } catch (Exception e) {
	// getLogger().error(e);
	// } finally {
	// getComboForSearch(conn);
	// CCTConnectionUtil.close(conn);
	// }
	// return ActionReturnType.SEARCH_AJAX.getResult();
	// }

	public String export() throws Exception {
		getLogger().debug("init");
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn,
					getDbLookup());

		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return ActionReturnType.INIT.getResult();
	}

	public void getComboForSearch(CCTConnection conn) {
		try {

			// getModel().setListstatus(SelectItemProviders.getSelectItems(getLocale(),	GlobalType.EVENT_STATUS));
			ReportManager manager = new ReportManager(getLogger(), getUser(),
					getLocale());
			getModel().setListRoom(manager.searchListRoom(conn));
			getModel().setListMonth(manager.searchListMonth(conn));
			// getModel().setListYears(manager.searchListYears(conn));
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	public Locale getLocale() {
		return new Locale("en", "EN");
	}

	@Override
	public ReportModel getModel() {
		return model;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.REPORT;
	}
	
	@Override
	public String dbLookupInititial() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}
}
