package com.cct.inhouse.bkonline.core.setting.roomsetting.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.domain.Active;
import com.cct.exception.ServerValidateException;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.RoomClosed;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.RoomSettingData;
import com.cct.inhouse.util.IHUtil;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class SettingDAO extends AbstractDAO<RoomSettingData, RoomSettingData, RoomSettingData> {
	
	public SettingDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	/**
	 * สร้าง list ของ room ทั้งหมด
	 */
	@Override
	protected RoomSettingData createResultSearch(ResultSet rst, CommonUser user, Locale local) throws Exception {
		RoomSettingData result = new RoomSettingData();
		result.setId(StringUtil.nullToString(rst.getString("ROOM_ID")));
		result.setName(StringUtil.nullToString(rst.getString("ROOM_NAME")));
		return result;
	}

	/**
	 * สร้าง SQL ค้นหาห้องทั้งหมด
	 */
	@Override
	protected String createSQLSearch(CCTConnection conn, RoomSettingData data, CommonUser user, Locale locale)
			throws Exception {

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = null;
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchAllRoom", params);
		
		getLogger().debug(sql);
		return sql;
	}
	
	/**
	 * ค้นหาสถานะห้องล่าสุดจากการจอง ณเวลาปัจจุบัน
	 * @param conn
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Active> searchLastedStatusInTime(CCTConnection conn, CommonUser user, Locale locale) throws Exception {
		
		Map<String, Active> mapResult = new HashMap<String, Active>();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchLastedStatusInTime");
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while(rst.next()) {
				Active result = new Active();
				result.setCode(StringUtil.replaceSpecialString(rst.getString("BOOKING_STATUS_CODE"), conn.getDbType()));
				result.setDesc(StringUtil.replaceSpecialString(rst.getString("BOOKING_STATUS_NAME"), conn.getDbType()));
				mapResult.put(rst.getString("ROOM_ID"), result);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return mapResult;
	}

	/**
	 * ค้นหาสถานะอยู่ในช่วงปิดห้องประชุมหรือใหม่
	 * @param conn
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception 
	 */
	protected Map<String, Active> searchRoomInTimeClosed(CCTConnection conn, CommonUser user, Locale locale) throws Exception {

		Map<String, Active> mapResult = new HashMap<String, Active>();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchRoomInTimeClosed");
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while(rst.next()) {
				Active result = new Active();
				result.setCode(StringUtil.replaceSpecialString(rst.getString("STATUS"), conn.getDbType()));
				result.setDesc(result.getCode());
				mapResult.put(rst.getString("ROOM_ID"), result);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return mapResult;
	}

	/**
	 * สร้าง Object ตามห้องที่ได้จาก SQL
	 */
	@Override
	protected RoomSettingData createResultSearchById(ResultSet rst, CommonUser user, Locale locale) throws Exception {
		RoomSettingData result = new RoomSettingData();
		result.setId(StringUtil.nullToString(rst.getString("ROOM_ID")));
		result.setName(StringUtil.nullToString(rst.getString("ROOM_NAME")));
		result.setColor(StringUtil.nullToString(rst.getString("ROOM_COLOR")));
		result.setAutotime(StringUtil.nullToString(rst.getString("AUTOTIME_ID")));
		result.setAttendeesMax(StringUtil.nullToString(rst.getString("ROOM_ATTENDEES_MAX")));
		result.setPhone(StringUtil.nullToString(rst.getString("ROOM_PHONE")));
		result.setDetail(StringUtil.nullToString(rst.getString("ROOM_DETAIL")));
		result.setEquipmentListId(StringUtil.nullToString(rst.getString("EQUIPMENT_LIST_ID")));
		result.setPriority(StringUtil.nullToString(rst.getString("PRIORITY")));
		return result;
	}
	
	/**
	 * ค้นหาข้อมูลห้องตาม roomId โดยใช้ SQL เดียว searchAllRoom แต่เพิ่ม AND ROOM_ID = ?
	 */
	@Override
	protected String createSQLSearchById(CCTConnection conn, String roomId, CommonUser user, Locale locale)
			throws Exception {
		
		if (StringUtil.replaceSpecialString(roomId, conn.getDbType(), ResultType.NULL) == null) {
			// ถ้า ID ไม่ส่งเข้ามา ห้าม execute query ป้องกันการตัดเงือนไข ID ทิ้ง จนทำให้ query ได้ข้อมูลผิดไป
			getLogger().debug("RoomId is null");
			throw new ServerValidateException();
		}
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.stringToLong(roomId);
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchAllRoom", params);
		
		getLogger().debug(sql);
		return sql;
	}
	
	/**
	 * ค้นหาสถานะอยู่ในช่วงปิดห้องประชุมหรือใหม่
	 * @param conn
	 * @param user
	 * @param locale
	 * @param roomId
	 * @return
	 * @throws Exception 
	 */
	protected List<RoomClosed> searchTimeClosedByRoomId(CCTConnection conn, CommonUser user, Locale locale, String roomId) throws Exception {

		List<RoomClosed> listResult = new ArrayList<RoomClosed>();
		
		if (StringUtil.replaceSpecialString(roomId, conn.getDbType(), ResultType.NULL) == null) {
			// ถ้า ID ไม่ส่งเข้ามา ห้าม execute query ป้องกันการตัดเงือนไข ID ทิ้ง จนทำให้ query ได้ข้อมูลผิดไป
			getLogger().debug("RoomId is null");
			throw new ServerValidateException();
		}
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.stringToLong(roomId);
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchTimeClosedByRoomId", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while(rst.next()) {
				RoomClosed result = new RoomClosed();
				result.setId(StringUtil.nullToString(rst.getString("CLOSED_ID")));
				result.setRoomId(StringUtil.nullToString(rst.getString("ROOM_ID")));
				
				result.setStartDate(StringUtil.nullToString(rst.getString("CLOSED_START_DATE_STR")));
				result.setStartDateForShow(IHUtil.convertDateTimeForDisplay(result.getStartDate()));
				result.setStartTime(StringUtil.nullToString(rst.getString("CLOSED_START_TIME")));
				
				result.setEndDate(StringUtil.nullToString(rst.getString("CLOSED_END_DATE_STR")));
				result.setEndDateForShow(IHUtil.convertDateTimeForDisplay(result.getEndDate()));
				result.setEndTime(StringUtil.nullToString(rst.getString("CLOSED_END_TIME")));
				
				listResult.add(result);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listResult;
	}
	
	/**
	 * สร้าง SQL ตรวจสอบข้อมูลซ้ำ<br>
	 * กรณีเพิ่มให้ส่ง id = null<br>
	 * กรณีแก้ไขให้ส่ง id = pk
	 */
	@Override
	protected String createSQLCheckDup(CCTConnection conn, RoomSettingData data, CommonUser user, Locale local)
			throws Exception {

		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getName(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.stringToLong(data.getId());

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"checkDupRoomData", params);

		getLogger().debug(sql);
		return sql;
	}
	
	/**
	 * อัพเดดข้อมูลห้องนั้นๆ
	 */
	@Override
	protected String createSQLEdit(CCTConnection conn, RoomSettingData data, CommonUser user, Locale local)
			throws Exception {
		
		if (StringUtil.replaceSpecialString(data.getId(), conn.getDbType(), ResultType.NULL) == null) {
			// ถ้า ID ไม่ส่งเข้ามา ห้าม execute query ป้องกันการตัดเงือนไข ID ทิ้ง จนทำให้ query ได้ข้อมูลผิดไป
			getLogger().debug("RoomId is null");
			throw new ServerValidateException();
		}
		
		int paramIndex = 0;
		Object[] params = new Object[10];				
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getColor(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(data.getAutotime());
		params[paramIndex++] = StringUtil.stringToLong(data.getAttendeesMax());
		
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getPhone(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getDetail(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getEquipmentListId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(data.getPriority());
		
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		params[paramIndex++] = StringUtil.stringToLong(data.getId());

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"updateRoomSetting", params);

		getLogger().debug(sql);
		return sql;
	}
	
	/**
	 * ลบข้อมูลตารางเวลาปิดห้องนั้นๆ 
	 * @param conn
	 * @param id
	 * @param user
	 * @param locale
	 * @throws Exception
	 */
	protected void deleteTimeClosed(CCTConnection conn, String id, CommonUser user, Locale locale) throws Exception {

		if (StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.NULL) == null) {
			// ถ้า ID ไม่ส่งเข้ามา ห้าม execute query ป้องกันการตัดเงือนไข ID ทิ้ง จนทำให้ query ได้ข้อมูลผิดไป
			getLogger().debug("ClosedID is null");
			throw new ServerValidateException();
		}
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		params[paramIndex++] = StringUtil.stringToLong(id);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"updateDeleteRoomClosedTime", params);
		
		getLogger().debug(sql);
		
		// Execute SQL
		executeUpdate(conn, sql);
	}

	protected void addTimeClosed(CCTConnection conn, RoomClosed data, CommonUser user, Locale locale) throws Exception {

		String closeDate = IHUtil.convertDatePickerForDatabase(data.getStartDate());
		
		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = StringUtil.stringToLong(data.getRoomId());

		params[paramIndex++] = closeDate;
		params[paramIndex++] = data.getStartTime();
		
		params[paramIndex++] = closeDate;
		params[paramIndex++] = data.getEndTime();
		
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"insertRoomClosedTime", params);
		
		getLogger().debug(sql);
		
		// Execute SQL
		executeInsert(conn, sql);
	}

	@Override
	protected String createSQLAdd(CCTConnection conn, RoomSettingData data, CommonUser user, Locale local)
			throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[10];				
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getColor(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(data.getAutotime());
		params[paramIndex++] = StringUtil.stringToLong(data.getAttendeesMax());
		
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getPhone(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getDetail(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getEquipmentListId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(data.getPriority());
		
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"insertRoomSetting", params);

		getLogger().debug(sql);
		return sql;
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	protected String createSQLDelete(CCTConnection conn, String index, CommonUser user, Locale locale)
			throws Exception {
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.replaceSpecialString(index, conn.getDbType());
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"deleteRoomById", params);

		getLogger().debug("sql createSQLCheckDup : " + sql);
		return sql;
	}


	@Override
	protected String createSQLUpdateActive(CCTConnection arg0, String arg1, String arg2, CommonUser arg3, Locale arg4)
			throws Exception {
		return null;
	}
	
	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	protected String createSQLCountData(CCTConnection arg0, RoomSettingData data, CommonUser arg2, Locale arg3)
			throws Exception {
		return null;
	}
}
