package com.cct.inhouse.bkonline.core.setting.roomsetting.service;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonUser;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.RoomSettingData;

import util.database.CCTConnection;

public class SettingManager extends AbstractManager<RoomSettingData, RoomSettingData, RoomSettingData, CommonUser, Locale> {
	private SettingService service = null;

	public SettingManager(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new SettingService(logger, user, locale);
	}

	/**
	 * ค้นหาข้อมูลห้อง และแสดงสถานะ ของห้องล่าสุด
	 */
	@Override
	public List<RoomSettingData> search(CCTConnection conn, RoomSettingData data) throws Exception {
		return service.search(conn, data);
	}
	
	/**
	 * ค้นหาข้อมูลของห้อง พร้อมช่วงเวลาปิดห้อง
	 */
	@Override
	public RoomSettingData searchById(CCTConnection conn, String roomId) throws Exception {
		return service.searchById(conn, roomId);
	}

	@Override
	public long add(CCTConnection conn, RoomSettingData data) throws Exception {
		
		long roomId = 0;
		
		// ตรวจสอว่าข้อมูลซ้ำไหม
		service.checkDup(conn, data);

		try {
			conn.setAutoCommit(false);

			roomId = service.add(conn, data);

			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		return roomId;

	}

	@Override
	public long edit(CCTConnection conn, RoomSettingData data) throws Exception {

		long result = 0;
		
		// ตรวจสอว่าข้อมูลซ้ำไหม
		service.checkDup(conn, data);

		try {
			conn.setAutoCommit(false);

			result = service.edit(conn, data);

			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		return Integer.parseInt(String.valueOf(result));
	}



	@Override
	public long delete(CCTConnection conn, String index) throws Exception {
		return 0; //service.delete(conn, index);
	}

	@Override
	public long updateActive(CCTConnection arg0, String arg1, String arg2) throws Exception {
		return 0;
	}
}
