package com.cct.inhouse.bkonline.web.booking.roombooking.action;

import org.apache.log4j.Logger;

import com.cct.enums.ActionResultType;
import com.cct.enums.ActionReturnType;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingData;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingModel;
import com.cct.inhouse.bkonline.core.booking.roombooking.service.RoomBookingManager;
import com.cct.inhouse.bkonline.domain.BKOnlineVariable;
import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.central.rmi.service.SelectItemRMIProviders;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.common.InhouseModel.PageType;
import com.cct.inhouse.core.security.authorization.domain.PFCode;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.enums.GlobalType;
import com.cct.inhouse.util.database.CCTConnectionProvider;
import com.opensymphony.xwork2.ModelDriven;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

public class RoomBookingAction extends InhouseAction implements ModelDriven<RoomBookingModel> {

	private static final long serialVersionUID = 2737334550572859540L;

	private RoomBookingModel model = new RoomBookingModel();

	public RoomBookingAction() {
		try {
			getAuthorization(PFCode.BK_BOOKING);
			getLogger().debug("Add: " + ATH.isAdd() + ", Edit: " + ATH.isEdit() + ", View: " + ATH.isView());
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	/**
	 * เข้าระบบผ่านทางเมนู โดยหน้าแรกเป็นหน้าเพิ่ม
	 * @return
	 * @throws Exception
	 */
	public String init() throws Exception {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());

			// จัดการค่าเริ่มต้นต่างๆ 
		    manageInitial(conn, getModel(), getPF_CODE().getAddFunction(), PageType.ADD);
		    
		    getModel().getRoomBooking().setUserBookId(getUser().getId());
		    getModel().getRoomBooking().setUserBookName(getUser().getDisplayName());
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getAddFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return ActionReturnType.INIT.getResult();
	}

	/**
	 * กดบันทึกข้อมูล ถ้าบันทึกสำเร็จต้อง Redirect ไปหน้าปฏิทิน, ถ้าไม่สำเร็จให้อยู่หน้าเติม
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		
		getLogger().debug(StringDelimeter.EMPTY.getValue());
		String result = ActionReturnType.ADD_EDIT.getResult();
		
		CCTConnection conn = null;
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
		    manageAdd(conn, getModel(), ActionResultType.CHAIN);
			
			RoomBookingManager manager = new RoomBookingManager(getLogger(), getUser(), getLocale());
			String bookingId = manager.bookingNow(conn, getModel().getRoomBooking());

			RoomBookingData bookingData = manager.searchDataEventDrawByBookingId(conn, bookingId);
			// กำหนดค่า EventId และ CurrentDay สำหรับส่งไป focus ที่หน้าปฏิทิน
			SessionUtil.put(BKOnlineVariable.SessionParameter.BOOKING_EVEN_ID.getValue(), bookingData.getId());
			SessionUtil.put(BKOnlineVariable.SessionParameter.BOOKING_CURRENT_DAY.getValue(), bookingData.getRoomBooking().getStartDate());
			
			result = ActionReturnType.MAINPAGE.getResult();
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getAddFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return result;
	}
	
	public String gotoEventData() throws Exception {
		getLogger().debug(StringDelimeter.EMPTY.getValue());
		String result = ActionReturnType.MAINPAGE.getResult();
		try {
			
			SessionUtil.put(BKOnlineVariable.SessionParameter.BOOKING_EVEN_ID.getValue(), getModel().getTempEventId());
			SessionUtil.put(BKOnlineVariable.SessionParameter.BOOKING_CURRENT_DAY.getValue(), getModel().getTempCurrentDay());
			
		} catch (Exception e) {
			getLogger().error(e);
		}
		return result;
	}
	
	public String edit() throws Exception {
		
		String result = ActionReturnType.ADD_EDIT.getResult();
		
		CCTConnection conn = null;
		try {
			
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
		    manageAdd(conn, getModel(), ActionResultType.CHAIN);
			
			RoomBookingManager manager = new RoomBookingManager(getLogger(), getUser(), getLocale());
			manager.bookingUpdate(conn, getModel().getRoomBooking());

			result = ActionReturnType.MAINPAGE.getResult();
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getAddFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return result;
	}

	public void getComboForSearch(CCTConnection conn) {
		try {
			getModel().setListEquipment(SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.EQUIPMENT_ROOM));
			getModel().setListNotificationUse(SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.NOTIFICATION_USE));
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	@Override
	public RoomBookingModel getModel() {
		return model;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.BOOKING;
	}

	@Override
	public String dbLookupInititial() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}


}
