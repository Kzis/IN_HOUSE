package com.cct.inhouse.bkonline.web.setting.roomsetting.action;

import org.apache.log4j.Logger;

import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionResultType;
import com.cct.enums.ActionReturnType;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.RoomSettingData;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.SettingModel;
import com.cct.inhouse.bkonline.core.setting.roomsetting.service.SettingManager;
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

public class SettingAction extends InhouseAction implements ModelDriven<SettingModel> {

	private static final long serialVersionUID = 2737334550572859540L;
	private SettingModel model = new SettingModel();

	public SettingAction() {
		try {
			getAuthorization(PFCode.BK_SETTING);
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
		    
		    getModel().setRoomSetting(new RoomSettingData());
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		
		return ActionReturnType.INIT.getResult();
	}
	
	
	public String ajaxSearchByRoomId() {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			// จัดการค่าเริ่มต้นต่างๆ 
		    manageInitial(conn, getModel(), getPF_CODE().getAddFunction(), PageType.ADD);
		    
		    SettingManager manager = new SettingManager(getLogger(), getUser(), getLocale());
		    RoomSettingData roomSetting = manager.searchById(conn, getModel().getRoomSetting().getId());
		    
		    getModel().setRoomSetting(roomSetting);
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
	
	public String add() throws Exception {
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			manageAdd(conn, getModel(), ActionResultType.BASIC);
			
			SettingManager manager = new SettingManager(getLogger(), getUser(),getLocale());
	        manager.add(conn, getModel().getRoomSetting());

			getModel().setRoomSetting(new RoomSettingData());
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getEditFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return ActionReturnType.INIT.getResult();
	}

	public String edit() throws Exception {

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			manageEdit(conn, getModel(), ActionResultType.BASIC);
			
			SettingManager manager = new SettingManager(getLogger(), getUser(),getLocale());
	        manager.edit(conn, getModel().getRoomSetting());

			getModel().setRoomSetting(new RoomSettingData());
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getEditFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return ActionReturnType.INIT.getResult();
	}
	
	public String delete() throws Exception {
		CCTConnection conn = null;
		try {
//			conn = new CCTConnectionProvider().getConnection(conn,getDbLookup());
//			SettingManager manager = new SettingManager(getLogger(), getUser(),getLocale());
//			manager.delete(conn, getModel().getData().getId());
//			getModel().setData(new SettingData());
		} catch (Exception e) {
			manageException(conn, getPF_CODE().getDeleteFunction(), this, e, getModel());
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return ActionReturnType.INIT.getResult();
	}
	
	public void getComboForSearch(CCTConnection conn) {
		try {
			// โหลดข้อมูล dropdownlist ต่างๆ
			getModel().setListAutotime(SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.AUTO_TIME));
			getModel().setListColor(SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.COLOR));
			getModel().setListEquipment(SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.EQUIPMENT_ROOM));
			getModel().setListRoomPriority(SelectItemRMIProviders.getSelectItems(getLocale(), GlobalType.ROOM_PRIORITY));
		} catch (Exception e) {
			getLogger().error(e);
		}
		
		try {
			// โหลดข้อมูล ห้องทั้งหมดพร้อมสถานะ
			SettingManager manager = new SettingManager(getLogger(), getUser(),getLocale());
			getModel().setListAllRoom(manager.search(conn, getModel().getRoomSetting()));
		} catch (Exception e) {
			try {
				manageException(conn, getPF_CODE().getAddFunction(), this, e, getModel());
			} catch (Exception e1) {
				
			}
		}
	}

	@Override
	public SettingModel getModel() {
		return model;
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.SETTING;
	}
	
	@Override
	public String dbLookupInititial() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}
}
