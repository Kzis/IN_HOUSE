package com.cct.inhouse.bkonline.core.setting.roomsetting.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.domain.Active;
import com.cct.exception.DuplicateException;
import com.cct.inhouse.bkonline.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.RoomClosed;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.RoomSettingData;
import com.cct.inhouse.domain.GlobalVariable;

import util.database.CCTConnection;
import util.string.StringDelimeter;


public class SettingService extends AbstractService {
	
	private SettingDAO dao = null;
	
	
	public SettingService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new SettingDAO(logger, SQLPath.SETTING_SQL.getSqlPath(), user, locale);
	}
	
	/**
	 * ค้นหาข้อมูลห้อง และแสดงสถานะ ของห้องล่าสุด
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<RoomSettingData> search(CCTConnection conn, RoomSettingData data) throws Exception {
		
		// ค้นหาห้องล่าสุด
		List<RoomSettingData> listAllRoom = dao.search(conn, data);
		
		// ค้นหาสถานะล่าสุด
		Map<String, Active> mapRoomStatusActive = dao.searchLastedStatusInTime(conn, getUser(), getLocale());
		
		// ค้นหาช่วงเวลาห้องปิด
		Map<String, Active> mapRoomStatusClose = dao.searchRoomInTimeClosed(conn, getUser(), getLocale());
		for (RoomSettingData room : listAllRoom) {
			Active statusActive = mapRoomStatusActive.get(room.getId());
			if (statusActive != null) {
				// พบสถานะล่าสุด
				room.setActive(statusActive);
			} else {
				Active statusClose = mapRoomStatusClose.get(room.getId());
				if (statusClose != null) {
					// พบว่าห้องปิด
					room.setActive(statusClose);
				}
			}
		}
		return listAllRoom;
	}
	
	/**
	 * ค้นหาข้อมูลของห้อง พร้อมช่วงเวลาปิดห้อง
	 * @param conn
	 * @param roomId
	 * @return
	 * @throws Exception
	 */
	protected RoomSettingData searchById(CCTConnection conn, String roomId) throws Exception {
		
		// ค้นหาห้อง
		RoomSettingData roomData = dao.searchById(conn, roomId); 
		
		// ค้นหาตารางปิดห้อง
		List<RoomClosed> listTimeClose = dao.searchTimeClosedByRoomId(conn, getUser(), getLocale(), roomData.getId());
		
		roomData.setListTimeClosed(listTimeClose);
		
		return roomData;
	}
	
	protected void checkDup(CCTConnection conn, RoomSettingData data) throws Exception{
		try {
			dao.checkDup(conn, data);
		} catch (DuplicateException ex) {
			getLogger().debug(ex.getMessage(), ex);
			throw ex;
		} catch (Exception e) {
			throw e;
		}
	}
	

	/**
	 * บันทึกข้อมูลห้อง และตารางเวลาปิดห้อง
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	protected long edit(CCTConnection conn, RoomSettingData data) throws Exception{
		
		long result = 0;
		
		try {
			// บันทึกข้อมูลห้อง
			result = dao.edit(conn, data);
			
			// บันทึกข้อมูลตารางเวลาปิดห้อง
			for(RoomClosed row : data.getListTimeClosed()){
	            if(!row.getId().equals(StringDelimeter.EMPTY.getValue())  && !row.getDeleteFlag().equals(GlobalVariable.DeleteStatus.DELETED.getValue()) && row.isEdited()){
	                // แก้ไข ไม่ต้องทำอะไร หน้าจอไม่เปิดให้แก้ไข
	                getLogger().debug("EDIT> ID: " + row.getId());
	            }else if(!row.getId().equals(StringDelimeter.EMPTY.getValue())  && row.getDeleteFlag().equals(GlobalVariable.DeleteStatus.DELETED.getValue())){
	                // ลบ
	            	getLogger().debug("DELETE> ID: " + row.getId());
	                dao.deleteTimeClosed(conn, row.getId(), getUser(), getLocale());
	            }else if(row.getId().equals(StringDelimeter.EMPTY.getValue())  && !row.getDeleteFlag().equals(GlobalVariable.DeleteStatus.DELETED.getValue())){
	                // เพิ่มใหม่
	            	getLogger().debug("ADD> ID: " + row.getId());
	                dao.addTimeClosed(conn, row, getUser(), getLocale());
	            }
	        }
			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	/**
	 * เพิ่มข้อมูลห้อง และตารางเวลาปิดห้อง
	 * @param conn
	 * @param data
	 * @return
	 * @throws Exception
	 */
	protected long add(CCTConnection conn, RoomSettingData data) throws Exception{
		
		long roomId = 0;
		
		try {
			// บันทึกข้อมูลห้อง
			roomId = dao.add(conn, data);
			
			// บันทึกข้อมูลตารางเวลาปิดห้อง
			for(RoomClosed row : data.getListTimeClosed()){
				
				// กำหนดค่า RoomId เพื่อใช้เป็น fk สำหรับบันทึกข้อมูลตารางเวลาปิดห้องประชุม
				row.setRoomId(String.valueOf(roomId));
				
	            if(!row.getId().equals(StringDelimeter.EMPTY.getValue())  && !row.getDeleteFlag().equals(GlobalVariable.DeleteStatus.DELETED.getValue()) && row.isEdited()){
	                // แก้ไข ไม่ต้องทำอะไร หน้าจอไม่เปิดให้แก้ไข
	                getLogger().debug("EDIT> ID: " + row.getId());
	            }else if(!row.getId().equals(StringDelimeter.EMPTY.getValue())  && row.getDeleteFlag().equals(GlobalVariable.DeleteStatus.DELETED.getValue())){
	                // ลบ ไม่ต้องทำอะไร เพราะเป็นข้อมูลใหม่ ให้ข้ามไปได้เลย
	            	getLogger().debug("DELETE> ID: " + row.getId());
	            }else if(row.getId().equals(StringDelimeter.EMPTY.getValue())  && !row.getDeleteFlag().equals(GlobalVariable.DeleteStatus.DELETED.getValue())){
	                // เพิ่มใหม่
	            	getLogger().debug("ADD> ID: " + row.getId());
	                dao.addTimeClosed(conn, row, getUser(), getLocale());
	            }
	        }
			
		} catch (Exception e) {
			throw e;
		}
		return roomId;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected long delete(CCTConnection conn,String index) throws Exception{
		getLogger().debug("delete");
		try {
			return dao.delete(conn, index);
		} catch (Exception e) {
			getLogger().error("", e);
			throw e;
		}
	}
}
