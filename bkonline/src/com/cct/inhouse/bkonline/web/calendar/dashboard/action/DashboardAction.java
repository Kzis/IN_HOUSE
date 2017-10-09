package com.cct.inhouse.bkonline.web.calendar.dashboard.action;

import org.apache.log4j.Logger;

import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionReturnType;
import com.cct.exception.CustomException;
import com.cct.inhouse.bkonline.core.booking.roombooking.service.RoomBookingManager;
import com.cct.inhouse.bkonline.core.calendar.dashboard.domain.DashboardModel;
import com.cct.inhouse.bkonline.core.setting.roomsetting.service.SettingManager;
import com.cct.inhouse.bkonline.domain.BKOnlineVariable;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseModel.PageType;
import com.cct.inhouse.core.security.authorization.domain.PFCode;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.date.DateUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

public class DashboardAction extends InhouseAction implements ModelDriven<DashboardModel> {

	private static final long serialVersionUID = 2737334550572859540L;
	
	private DashboardModel model = new DashboardModel();

	public DashboardAction() {
		try {
			getAuthorization(PFCode.BK_DASHBOARD);
			ATH.setView(true); // Default view mode
			getLogger().debug("Add: " + ATH.isAdd() + ", Edit: " + ATH.isEdit() + ", View: " + ATH.isView());
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
		getLogger().debug(StringDelimeter.EMPTY.getValue());
		
		String result = ActionReturnType.INIT.getResult();
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn,getDbLookup());
			
			// จัดการค่าเริ่มต้นต่างๆ 
			if (ATH.isEdit()) {
				getLogger().debug("Mode [Admin]");
				manageInitial(conn, getModel(), getPF_CODE().getEditFunction(), PageType.EDIT);
			} else {
				getLogger().debug("Mode [General]");
				manageInitial(conn, getModel(), getPF_CODE().getViewFunction(), PageType.VIEW);
			}
			
			String currentDay = StringDelimeter.EMPTY.getValue();
			String eventIdFocus = StringDelimeter.EMPTY.getValue();
			// กำหนดค่า EventId และ CurrentDay สำหรับส่งไป focus ที่หน้าปฏิทิน
			String tempEventId = (String) SessionUtil.get(BKOnlineVariable.SessionParameter.BOOKING_EVEN_ID.getValue());
			String tempCurrentDay = (String) SessionUtil.get(BKOnlineVariable.SessionParameter.BOOKING_CURRENT_DAY.getValue());
			if (tempCurrentDay != null) {
				SessionUtil.remove(BKOnlineVariable.SessionParameter.BOOKING_EVEN_ID.getValue());
				SessionUtil.remove(BKOnlineVariable.SessionParameter.BOOKING_CURRENT_DAY.getValue());
				currentDay = tempCurrentDay;
				eventIdFocus = tempEventId; 
			} else {
				currentDay = DateUtil.getCurrentDateDB(conn.getConn(), conn.getDbType());
			}
			getLogger().debug("currentDay: " + currentDay);
			getModel().getDashboardData().setCurrentDay(currentDay.substring(0, 10));
			getModel().getDashboardData().setCurrentMode(BKOnlineVariable.Mode.WEEK.getValue());
			getModel().getDashboardData().setEventIdFocus(eventIdFocus);
		} catch (Exception e) {
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return result;
	}
	
	public String ajaxSearchSelectRoom() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			// จัดการค่าเริ่มต้นต่างๆ 
			if (ATH.isEdit()) {
				manageInitial(conn, getModel(), getPF_CODE().getEditFunction(), PageType.EDIT);
			} else {
				manageInitial(conn, getModel(), getPF_CODE().getViewFunction(), PageType.VIEW);
			}
		    
			// โหลดข้อมูล ห้องทั้งหมดพร้อมสถานะ
			SettingManager manager = new SettingManager(getLogger(), getUser(),getLocale());
			getModel().setListSelectRoom(manager.search(conn, null));
			
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	public String ajaxSearchEventData() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			// จัดการค่าเริ่มต้นต่างๆ 
			if (ATH.isEdit()) {
				manageInitial(conn, getModel(), getPF_CODE().getEditFunction(), PageType.EDIT);
			} else {
				manageInitial(conn, getModel(), getPF_CODE().getViewFunction(), PageType.VIEW);
			}
		    
		    getLogger().debug(getModel().getDashboardData().getCurrentMode() + " > " + getModel().getDashboardData().getCurrentDay() + " > " + getModel().getDashboardData().getCurrentStep());
		    getLogger().debug("RoomIdSelected: " + getModel().getDashboardData().getRoomIdSelected());
		    
		    searchListDashboardData(conn);
		    
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	public String ajaxApproveEventData() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			// จัดการค่าเริ่มต้นต่างๆ 
			if (ATH.isEdit()) {
				manageInitial(conn, getModel(), getPF_CODE().getEditFunction(), PageType.EDIT);
			} else {
				manageInitial(conn, getModel(), getPF_CODE().getViewFunction(), PageType.VIEW);
			}
		    
		    RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), getUser(), getLocale());
		    roomBookingManager.updateBookingStatusApprove(conn, getModel().getDashboardData().getEventIdSelected());
		    
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		} finally {
			searchListDashboardData(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	public String ajaxCheckInData() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			// จัดการค่าเริ่มต้นต่างๆ 
			if (ATH.isEdit()) {
				manageInitial(conn, getModel(), getPF_CODE().getEditFunction(), PageType.EDIT);
			} else {
				manageInitial(conn, getModel(), getPF_CODE().getViewFunction(), PageType.VIEW);
			}
		    
		    RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), getUser(), getLocale());
		    roomBookingManager.updateBookingStatusCheckIn(conn, getModel().getDashboardData().getEventIdSelected());
			
		} catch (CustomException ce) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.OTHER.getType());
			getModel().getMessageAjax().setMessage(ce.getMessage());
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		} finally {
			searchListDashboardData(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	public String ajaxCheckOutData() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			// จัดการค่าเริ่มต้นต่างๆ 
			if (ATH.isEdit()) {
				manageInitial(conn, getModel(), getPF_CODE().getEditFunction(), PageType.EDIT);
			} else {
				manageInitial(conn, getModel(), getPF_CODE().getViewFunction(), PageType.VIEW);
			}
		    
		    RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), getUser(), getLocale());
		    roomBookingManager.updateBookingStatusCheckOut(conn, getModel().getDashboardData().getEventIdSelected());

		} catch (CustomException ce) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.OTHER.getType());
			getModel().getMessageAjax().setMessage(ce.getMessage());
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		} finally {
			searchListDashboardData(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	
	public String ajaxOverLimitCheckOutData() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			// จัดการค่าเริ่มต้นต่างๆ 
			if (ATH.isEdit()) {
				manageInitial(conn, getModel(), getPF_CODE().getEditFunction(), PageType.EDIT);
			} else {
				manageInitial(conn, getModel(), getPF_CODE().getViewFunction(), PageType.VIEW);
			}
		    
		    RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), getUser(), getLocale());
		    roomBookingManager.updateBookingStatusOverLimitCheckOut(conn, getModel().getDashboardData().getEventIdSelected(), getModel().getDashboardData().getTimeOverLimitCheckOut());
		    
		} catch (CustomException ce) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.OTHER.getType());
			getModel().getMessageAjax().setMessage(ce.getMessage());
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		} finally {
			searchListDashboardData(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	public String ajaxCancelData() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			// จัดการค่าเริ่มต้นต่างๆ 
			if (ATH.isEdit()) {
				manageInitial(conn, getModel(), getPF_CODE().getEditFunction(), PageType.EDIT);
			} else {
				manageInitial(conn, getModel(), getPF_CODE().getViewFunction(), PageType.VIEW);
			}
		    
		    RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), getUser(), getLocale());
		    roomBookingManager.updateBookingStatusCancel(conn, getModel().getDashboardData().getEventIdSelected());
			
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		} finally {
			searchListDashboardData(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.SEARCH_AJAX.getResult();
	}
	
	private void searchListDashboardData(CCTConnection conn) {
		RoomBookingManager manager = new RoomBookingManager(getLogger(), getUser(),getLocale());
		try {
			getModel().setListDashboardData(manager.searchDataEventDraw(conn, getModel().getDashboardData()));
		} catch (Exception e) {
			getModel().getMessageAjax().setMessageType(ActionMessageType.ERROR.getType());
			getModel().getMessageAjax().setMessage(e.getMessage());
			getModel().getMessageAjax().setMessageDetail(getErrorMessage(e));
			getLogger().error(e);
		}
	}

	@Override
	public DashboardModel getModel() {
		return model;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.CALENDAR;
	}
	
	@Override
	public String dbLookupInititial() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}
}
