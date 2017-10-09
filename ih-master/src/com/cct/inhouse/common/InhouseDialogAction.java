package com.cct.inhouse.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cct.domain.SearchCriteria;
import com.cct.enums.ActionMessageType;
import com.cct.enums.UserSessionAttribute;
import com.cct.exception.MaxExceedException;
import com.cct.inhouse.core.config.parameter.domain.Parameter;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.log.DefaultLogUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

public abstract class InhouseDialogAction extends ActionSupport implements ModelDriven<InhouseModel> {
	
	private static final long serialVersionUID = -5847283287868762635L;

	private String functionCodePopup;
	
	private Logger logger = loggerInititial();
	public abstract Logger loggerInititial();
	
	@Override
	public abstract InhouseModel getModel();

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
	
	public SearchCriteria initSearchCriteria() {
		return null;
	}

	public List<InhouseDomain> initForSearchListById(CCTConnection conn, String id) throws Exception {
		return new ArrayList<InhouseDomain>();
	}

	public List<InhouseDomain> search(CCTConnection conn) throws Exception {
		return new ArrayList<InhouseDomain>();
	}

	public List<InhouseDomain> searchById(CCTConnection conn, String id) throws Exception {
		return new ArrayList<InhouseDomain>();
	}

	public InhouseDomain searchData(CCTConnection conn, SearchCriteria criteriaPopup, String id) throws Exception {
		return new InhouseDomain();
	}

	public InhouseDomain searchDetailById(CCTConnection conn) throws Exception {
		return new InhouseDomain();
	}
	
	public String init() {
		getLogger().debug(StringDelimeter.EMPTY.getValue());
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());
			SearchCriteria criteria = initSearchCriteria();
			manageInitialPopup(criteria);
			criteria.setDefaultHeaderSorts();
			getModel().setCriteriaPopup(criteria);
		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return "initialDialog";
	}

	public String initForSearchListById() {
		getLogger().debug(StringDelimeter.EMPTY.getValue());

		List<InhouseDomain> listResult = new ArrayList<InhouseDomain>();
		CCTConnection conn = null;
		try {
			String ids = "";
			if (ServletActionContext.getRequest().getParameter("id") != null) {
				ids = ServletActionContext.getRequest().getParameter("id");
			}

			SearchCriteria criteria = initSearchCriteria();
			manageInitialPopup(criteria);
			criteria.setDefaultHeaderSorts();
			getModel().setCriteriaPopup(criteria);
			getModel().setReferenceDialogId(ids);

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			// Add Event
			if (getFunctionCodePopup() != null) {
//				LogManager logManager = new LogManager(conn, log);
//				logManager.addEvent(conn, getFunctionCodePopup(), getUser().getUserId(), getModel());
			}

			listResult = initForSearchListById(conn, ids);
		} catch (Exception e) {
			getLogger().error(e);
			setMessagePopup(conn, e, getFunctionCodePopup(), this, getModel(), null, e.toString());
		} finally {
			CCTConnectionUtil.close(conn);
			getModel().setLstResult(listResult);
		}

		return "initialDialog";
	}

	public String searchList() {
		getLogger().debug(StringDelimeter.EMPTY.getValue());

		List<InhouseDomain> listResult = new ArrayList<InhouseDomain>();
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			// Add Event
			if (getFunctionCodePopup() != null) {
//				LogManager logManager = new LogManager(conn, log);
//				logManager.addEvent(conn, getFunctionCodePopup(), getUser().getUserId(), getModel());
			}

			listResult = search(conn);
			manageSearchResultPopup(getModel(), getModel().getCriteriaPopup(), listResult);
		} catch (MaxExceedException e) {
			manageExceedExceptionPopup(getModel(), getModel().getCriteriaPopup());
		} catch (Exception e) {
			getLogger().error(e);
			setMessagePopup(conn, e, getFunctionCodePopup(), this, getModel(), null, e.toString());
		} finally {
			CCTConnectionUtil.close(conn);
			getModel().setLstResult(listResult);
		}
		return "searchList";
	}

	public String searchListDetailById() {
		getLogger().debug(StringDelimeter.EMPTY.getValue());

		List<InhouseDomain> listResult = new ArrayList<InhouseDomain>();
		CCTConnection conn = null;
		try {
			String id = null;
			if (ServletActionContext.getRequest().getParameter("id") != null) {
				id = ServletActionContext.getRequest().getParameter("id");
			}

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			// Add Event
			if (getFunctionCodePopup() != null) {
//				LogManager logManager = new LogManager(conn, log);
//				logManager.addEvent(conn, getFunctionCodePopup(), getUser().getUserId(), getModel());
			}

			listResult = searchById(conn, id);

			manageSearchResultPopup(getModel(), getModel().getCriteriaPopup(), listResult);
		} catch (Exception e) {
			setMessagePopup(conn, e, getFunctionCodePopup(), this, getModel(), null, e.toString());
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
			getModel().setLstResult(listResult);
		}
		return "searchListById";
	}

	public String searchDetailById() {
		getLogger().debug(StringDelimeter.EMPTY.getValue());

		InhouseDomain common = null;

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			// Add Event
			if (getFunctionCodePopup() != null) {
//				LogManager logManager = new LogManager(conn, log);
//				logManager.addEvent(conn, getFunctionCodePopup(), getUser().getUserId(), getModel());
			}

			common = searchDetailById(conn);
		} catch (Exception e) {
			setMessagePopup(conn, e, getFunctionCodePopup(), this, getModel(), null, e.toString());
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}

		getModel().setObjectPopup(common);
		return "searchDetail";
	}

	public void manageInitialPopup(SearchCriteria criteria) throws Exception {
		try {
			criteria.setStart(1);
			// criteria.setLinePerPage(ParameterConfig.getParameter().getApplication().getLppPopup());
			criteria.setTotalResult(0);

		} catch (Exception e) {
			getLogger().error(e);
			throw e;
		}
	}

	public void manageSearchResultPopup(InhouseModel model, SearchCriteria criteria, List<?> listResultPopup) throws Exception {
		try {
			if (listResultPopup == null || listResultPopup.size() == 0) {
				setMessagePopup(null, null, null, null, model, MessageCode.MSG_30011, "");

				criteria.setStart(1);
				criteria.setTotalResult(0);
			} else {

			}
		} catch (Exception e) {
			getLogger().error(e);
			throw e;
		}
	}

	public void manageExceedExceptionPopup(InhouseModel model, SearchCriteria criteria) {
		if (criteria.getNavigatePopup().equals("true")) {
			setMessagePopup(null, null, null, null, model, MessageCode.MSG_30014, String.valueOf(criteria.getTotalResult()));
		} else {
			setMessagePopup(null, null, null, null, model, MessageCode.MSG_30018, "");
		}
	}

	public void manageExceedExceptionPopup(InhouseModel model, SearchCriteria criteria, String siteId) {

		if (criteria.getNavigatePopup().equals("true")) {
			setMessagePopup(null, null, null, null, model, MessageCode.MSG_30014, String.valueOf(criteria.getTotalResult()));
		} else {
			setMessagePopup(null, null, null, null, model, MessageCode.MSG_30018, String.valueOf(criteria.getTotalResult()));
		}

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
			model.setMessagePopup(ActionMessageType.ERROR + "::" + getText("30010") + "::" + msgDecs);
		}
	}

	/**
	 *
	 * @param conn
	 * @param e
	 * @param operatorId
	 * @param className
	 * @param model
	 * @param msgCode
	 * @param msgDecs
	 */
	public void setMessagePopup(CCTConnection conn, Exception e, String operatorId, Object className, InhouseModel model, MessageCode msgCode, String msgDecs) {
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

			if (conn != null && getFunctionCodePopup() != null) {
//				LogManager logManager = new LogManager(conn, log);
//				logManager.addError(conn, operatorId, getUser().getUserId(), className, e);
			}

			// กรณี Exception
			model.setMessagePopup(ActionMessageType.ERROR + "::" + getText("30010") + "::" + msgDecs);
		}
	}

	public void getComboForSearch(CCTConnection conn) {
		
	}
	
	/**
	 * ดึง user จาก session
	 *
	 * @return
	 */
	public InhouseUser getUser() {
		return (InhouseUser) SessionUtil.get(UserSessionAttribute.DEFAULT.getValue());
	}
	
	public String getFunctionCodePopup() {
		return functionCodePopup;
	}

	public void setFunctionCodePopup(String functionCodePopup) {
		this.functionCodePopup = functionCodePopup;
	}
	
	public Logger getLogger() {
		if (logger == null) {
			return  DefaultLogUtil.COMMON;
		} else {
			return logger;
		}
	}

	public Parameter getParameter() {
		return ParameterConfig.getParameter();
	}
}
