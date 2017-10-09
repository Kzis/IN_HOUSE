package com.cct.inhouse.bkonline.core.booking.roombooking.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.domain.Active;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.EmptyRoom;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBooking;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingData;
import com.cct.inhouse.bkonline.core.config.parameter.domain.ParameterExtendedConfig;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.util.IHUtil;

import util.calendar.CalendarUtil;
import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringDelimeter;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class RoomBookingDAO extends AbstractDAO<RoomBooking, RoomBooking, RoomBooking> {

	public RoomBookingDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath, user, locale);
	}

	/**
	 * สร้าง SQL ลบข้อมูล
	 */
	@Override
	protected String createSQLDelete(CCTConnection conn, String ids, CommonUser user, Locale locale) throws Exception {
		// TODO:
		return null;
	}
	
	/**
	 * สร้าง SQL สำหรับบันทึกจองห้อง
	 */
	@Override
	protected String createSQLAdd(CCTConnection conn, RoomBooking data, CommonUser user, Locale locale) throws Exception {
		
		String startData = IHUtil.convertDatePickerForDatabase(data.getStartDate());
		String endData = IHUtil.convertDatePickerForDatabase(data.getEndDate());
		
		int paramIndex = 0;
		Object[] params = new Object[29];
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getSubject(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = startData;
		params[paramIndex++] = data.getStartTime();
		params[paramIndex++] = endData;
		params[paramIndex++] = data.getEndTime();
		
		params[paramIndex++] = IHUtil.convertLongValue(user.getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(user.getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getDetail(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getEquipmentListId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getEquipmentListName(), conn.getDbType(), ResultType.NULL);
		
		params[paramIndex++] = IHUtil.convertLongValue(data.getBookingId());
		params[paramIndex++] = IHUtil.convertLongValue(data.getUserBookId());
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getUserBookName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getUserBookEmail(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getUserBookPhone(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getUserBookLineId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(data.getUserBookDepartmentId());
		params[paramIndex++] = IHUtil.convertLongValue(data.getUserBookPositionId());
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getNotificationEmail(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getNotificationLine(), conn.getDbType(), ResultType.NULL);

		params[paramIndex++] = IHUtil.convertLongValue(data.getAttendees());
		params[paramIndex++] = IHUtil.convertLongValue(data.getRoomId());
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getRoomName(), conn.getDbType(), ResultType.NULL);
				  
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getBookingStatusName(), conn.getDbType(), ResultType.NULL);

		params[paramIndex++] = data.getStartTime();
		params[paramIndex++] = data.getEndTime();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"insertEventDraw", params);

		getLogger().debug(sql);
		return sql;
	}
	
	/**
	 * สร้าง SQL ค้นหาห้องว่าง
	 * @throws Exception 
	 */
	protected List<EmptyRoom> searchListEmptyRoom(CCTConnection conn, String startDate, String startTime, String endDate, String endTime, CommonUser user, Locale locale) throws Exception {
		
		List<EmptyRoom> listResult = new ArrayList<EmptyRoom>();
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = IHUtil.convertDatePickerForDatabase(startDate);
		params[paramIndex++] = IHUtil.convertDatePickerForDatabase(endDate);
		params[paramIndex++] = startTime;
		params[paramIndex++] = endTime;

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchRoomFreeTime", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				EmptyRoom result = new EmptyRoom();
				result.setId(rst.getString("room_id"));
				result.setRoomName(rst.getString("room_name"));
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
	 * ค้นหาห้องที่ถูกปิด
	 * @param conn
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Active> searchMapClosedRoom(CCTConnection conn, String startDate, String startTime, String endDate, String endTime, CommonUser user, Locale locale) throws Exception {
		
		Map<String, Active> mapResult = new LinkedHashMap<String, Active>();
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = IHUtil.convertDatePickerForDatabase(startDate);
		params[paramIndex++] = IHUtil.convertDatePickerForDatabase(endDate);
		params[paramIndex++] = startTime;
		params[paramIndex++] = endTime;

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchRoomClosedTime", params);
		
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
	 * เปลื่ยนสถานะตามเงือนไข Approve
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusApprove(CCTConnection conn, String eventId, RoomBooking bookingStatus) throws Exception {

		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusApprove", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * เปลื่ยนสถานะตามเงือนไข CheckIn สำหรับผู้ใช้ทั่วไป
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusCheckInForGenaral(CCTConnection conn, String eventId, RoomBooking bookingStatus) throws Exception {

		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusCheckInForGenaral", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * เปลื่ยนสถานะตามเงือนไข CheckIn สำหรับ Admin
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusCheckInForAdmin(CCTConnection conn, String eventId, RoomBooking bookingStatus) throws Exception {

		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusCheckInForAdmin", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * เปลื่ยนสถานะตามเงือนไข CheckOut สำหรับผุ้ใช้ทั่วไป
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusCheckOutForGenaral(CCTConnection conn, String eventId, RoomBooking bookingStatus) throws Exception {

		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusCheckOutForGenaral", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * เปลื่ยนสถานะตามเงือนไข CheckOut สำหรับ Admin
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusCheckOutForAdmin(CCTConnection conn, String eventId, RoomBooking bookingStatus) throws Exception {

		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusCheckOutForAdmin", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * เปลื่ยนสถานะตามเงือนไข CheckOut กรณีไม่เคย CheckIn สำหรับผุ้ใช้ทั่วไป
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusCheckOutForGenaralIfNotCheckIn(CCTConnection conn, String eventId, RoomBooking bookingStatus) throws Exception {
		
		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusCheckOutForGenaralIfNotCheckIn", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * เปลื่ยนสถานะตามเงือนไข CheckOut กรณีไม่เคย CheckIn สำหรับ Admin
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusCheckOutForAdminIfNotCheckIn(CCTConnection conn, String eventId, RoomBooking bookingStatus) throws Exception {

		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusCheckOutForAdminIfNotCheckIn", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
		
	}
	
	/**
	 * เปลื่ยนสถานะตามเงือนไข CheckOut กรณีเกินเวลากำหนดต้องกำหนดเวลาด้วย
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusOverLimitCheckOut(CCTConnection conn, String eventId, String timeOverLimitCheckOut, RoomBooking bookingStatus) throws Exception {

		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(timeOverLimitCheckOut, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(timeOverLimitCheckOut, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusOverLimitCheckOut", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * เปลื่ยนสถานะตามเงือนไข CheckOut กรณีไม่เคย CheckIn กรณีเกินเวลากำหนดต้องกำหนดเวลาด้วย
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusOverLimitCheckOutIfNotCheckIn(CCTConnection conn, String eventId, String timeOverLimitCheckOut, RoomBooking bookingStatus) throws Exception {
		
		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(timeOverLimitCheckOut, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(timeOverLimitCheckOut, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusOverLimitCheckOutIfNotCheckIn", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * เปลื่ยนสถานะตามเงือนไข Cancel 
	 * @return 
	 * @throws Exception 
	 */
	protected boolean setBookingStatusCancel(CCTConnection conn, String eventId, RoomBooking bookingStatus) throws Exception {
		
		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = IHUtil.convertLongValue(getUser().getId());
		params[paramIndex++] = StringUtil.replaceSpecialString(getUser().getDisplayName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(bookingStatus.getBookingStatusName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"setBookingStatusCancel", params);
		
		getLogger().debug(sql);
		
		if (executeUpdate(conn, sql) > 0) {
			result = true;
		}
		
		return result;
		
	}
	
	/**
	 * ตรวจสอบสถานะก่อน Approve
	 * @return 
	 * @throws Exception 
	 */ 
	protected RoomBooking searchBookingStatusBeforeApprove(CCTConnection conn, String eventId, String userId) throws Exception {

		RoomBooking result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchBookingStatusBeforeApprove", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = new RoomBooking();
				result.setBookingStatusCode(StringUtil.nullToString(rst.getString("BOOKING_STATUS_CODE")));
				result.setBookingStatusName(StringUtil.nullToString(rst.getString("BOOKING_STATUS_NAME")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * ตรวจสอบสถานะก่อน CheckIn
	 * @return 
	 * @throws Exception 
	 */ 
	protected RoomBooking searchBookingStatusBeforeCheckIn(CCTConnection conn, String eventId, String userId) throws Exception {

		RoomBooking result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = ParameterExtendedConfig.getParameterExtended().getDataBeforeCheckInMinutes();
		params[paramIndex++] = ParameterExtendedConfig.getParameterExtended().getDataAfterCheckInMinutes();
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchBookingStatusBeforeCheckIn", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = new RoomBooking();
				result.setBookingStatusCode(StringUtil.nullToString(rst.getString("BOOKING_STATUS_CODE")));
				result.setBookingStatusName(StringUtil.nullToString(rst.getString("BOOKING_STATUS_NAME")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * ตรวจสอบสถานะก่อน CheckOut กรณีเคย Check In มาแล้ว
	 * @return 
	 * @throws Exception 
	 */ 
	protected RoomBooking searchBookingStatusBeforeCheckOut(CCTConnection conn, String eventId, String userId) throws Exception {

		RoomBooking result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchBookingStatusBeforeCheckOut", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = new RoomBooking();
				result.setBookingStatusCode(StringUtil.nullToString(rst.getString("BOOKING_STATUS_CODE")));
				result.setBookingStatusName(StringUtil.nullToString(rst.getString("BOOKING_STATUS_NAME")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * ตรวจสอบสถานะก่อน CheckOut กรณีไม่เคย Check In
	 * @return 
	 * @throws Exception 
	 */ 
	protected RoomBooking searchBookingStatusBeforeCheckOutIfNotCheckIn(CCTConnection conn, String eventId, String userId) throws Exception {

		RoomBooking result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchBookingStatusBeforeCheckOutIfNotCheckIn", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = new RoomBooking();
				result.setBookingStatusCode(StringUtil.nullToString(rst.getString("BOOKING_STATUS_CODE")));
				result.setBookingStatusName(StringUtil.nullToString(rst.getString("BOOKING_STATUS_NAME")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * ตรวจสอบการ Check Out ก่อนกำหนด
	 * @param conn
	 * @param eventId
	 * @return
	 * @throws Exception
	 */
	protected boolean searchCheckOutBeforeLimit(CCTConnection conn, String eventId) throws Exception {

		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = IHUtil.convertLongValue(eventId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchCheckOutBeforeLimit", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * ตรวจสอบสถานะก่อน Cancel
	 * @throws Exception 
	 */ 
	protected RoomBooking searchBookingStatusBeforeCancel(CCTConnection conn, String eventId, String userId) throws Exception {
		
		RoomBooking result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);
		params[paramIndex++] = IHUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchBookingStatusBeforeCancel", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = new RoomBooking();
				result.setBookingStatusCode(StringUtil.nullToString(rst.getString("BOOKING_STATUS_CODE")));
				result.setBookingStatusName(StringUtil.nullToString(rst.getString("BOOKING_STATUS_NAME")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	
	/**
	 * ข้อหาข้อมูลเพื่อ เพิ่มในการแจ้งเตือน
	 * @throws Exception 
	 */ 
	protected List<RoomBookingData> searchDataEventDrawByBookingId(CCTConnection conn, String bookingId) throws Exception {
		
		List<RoomBookingData> listResult = new ArrayList<RoomBookingData>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = IHUtil.convertLongValue(bookingId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchDataEventDrawByBookingId", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				RoomBookingData result = new RoomBookingData();
				result.setId(rst.getString("EVENT_ID"));
				result.getRoomBooking().setStartDate(rst.getString("EVENT_START_DATE"));
				result.getRoomBooking().setEndDate(rst.getString("EVENT_END_DATE"));
				result.getRoomBooking().setStartTime(rst.getString("EVENT_START_TIME"));
				result.getRoomBooking().setEndTime(rst.getString("EVENT_END_TIME"));
				result.getRoomBooking().setSubject(StringUtil.nullToString(rst.getString("EVENT_SUBJECT")));
				result.getRoomBooking().setDetail(StringUtil.nullToString(rst.getString("EVENT_DETAIL")));
				result.getRoomBooking().setAttendees(StringUtil.nullToString(rst.getString("EVENT_ATTENDEES")));
				result.getRoomBooking().setBookingStatusCode(StringUtil.nullToString(rst.getString("BOOKING_STATUS_CODE")));
				result.getRoomBooking().setBookingStatusName(StringUtil.nullToString(rst.getString("BOOKING_STATUS_NAME")));

				result.getRoomBooking().setUserBookId(StringUtil.nullToString(rst.getString("USER_BOOKING_ID")));
				result.getRoomBooking().setUserBookName(StringUtil.nullToString(rst.getString("USER_BOOKING_NAME")));
				
				result.getRoomSettingData().setId(StringUtil.nullToString(rst.getString("ROOM_ID")));
				result.getRoomSettingData().setName(StringUtil.nullToString(rst.getString("ROOM_NAME")));
				result.getRoomSettingData().setColor(StringUtil.nullToString(rst.getString("ROOM_COLOR")));

				result.getRoomBooking().setCreateUserId(StringUtil.nullToString(rst.getString("CREATE_USER_ID")));

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
	 * ข้อหาข้อมูลเพื่อ เพิ่มในการแจ้งเตือน
	 * @throws Exception 
	 */ 
	protected RoomBookingData searchDataEventDrawByEventId(CCTConnection conn, String eventId) throws Exception {
		
		RoomBookingData result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = IHUtil.convertLongValue(eventId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchDataEventDrawByEventId", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				result = new RoomBookingData();
				result.setId(rst.getString("EVENT_ID"));
				result.getRoomBooking().setStartDate(rst.getString("EVENT_START_DATE"));
				result.getRoomBooking().setEndDate(rst.getString("EVENT_END_DATE"));
				result.getRoomBooking().setStartTime(rst.getString("EVENT_START_TIME"));
				result.getRoomBooking().setEndTime(rst.getString("EVENT_END_TIME"));
				result.getRoomBooking().setSubject(StringUtil.nullToString(rst.getString("EVENT_SUBJECT")));
				result.getRoomBooking().setDetail(StringUtil.nullToString(rst.getString("EVENT_DETAIL")));
				result.getRoomBooking().setAttendees(StringUtil.nullToString(rst.getString("EVENT_ATTENDEES")));
				result.getRoomBooking().setBookingStatusCode(StringUtil.nullToString(rst.getString("BOOKING_STATUS_CODE")));
				result.getRoomBooking().setBookingStatusName(StringUtil.nullToString(rst.getString("BOOKING_STATUS_NAME")));

				result.getRoomBooking().setUserBookId(StringUtil.nullToString(rst.getString("USER_BOOKING_ID")));
				result.getRoomBooking().setUserBookName(StringUtil.nullToString(rst.getString("USER_BOOKING_NAME")));
				
				result.getRoomSettingData().setId(StringUtil.nullToString(rst.getString("ROOM_ID")));
				result.getRoomSettingData().setName(StringUtil.nullToString(rst.getString("ROOM_NAME")));
				result.getRoomSettingData().setColor(StringUtil.nullToString(rst.getString("ROOM_COLOR")));

				result.getRoomBooking().setCreateUserId(StringUtil.nullToString(rst.getString("CREATE_USER_ID")));

			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	
	
	/**
	 * ตรวจสอบว่ามีผู้จองในระบบจริงๆ หรือไม่
	 * @param conn
	 * @param userId
	 * @param userName
	 * @throws Exception 
	 */
	protected boolean searchUserIdForValidate(CCTConnection conn, String userId, String userName) throws Exception {
		
		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = IHUtil.convertLongValue(userId);
		params[paramIndex++] = StringUtil.replaceSpecialString(userName, conn.getDbType(), ResultType.EMPTY);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchUserIdForValidate", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next() && rst.getLong("TOT") > 0) {
				result = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * ตรวจสอบว่ามีห้องในระบบจริงๆ หรือไม่
	 * @param conn
	 * @param roomId
	 * @param roomName
	 * @return
	 * @throws Exception
	 */
	protected boolean searchRoomIdForValidate(CCTConnection conn, String roomId, String roomName) throws Exception {

		boolean result = false;
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = (roomId == null || roomId.isEmpty()) ? StringDelimeter.EMPTY.getValue() : IHUtil.convertLongValue(roomId);
		params[paramIndex++] = StringUtil.replaceSpecialString(roomName, conn.getDbType(), ResultType.EMPTY);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchRoomIdForValidate", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next() && rst.getLong("TOT") > 0) {
				result = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * ค้นหาเงือนไขปกติ โดยแสดงผลแบบรายสัปดาห์
	 */
	protected List<RoomBookingData> searchDataEventDraw(CCTConnection conn, RoomBookingData data,boolean flagAdmin, CommonUser user, Locale locale) throws Exception {
		
		List<RoomBookingData> listResult = new ArrayList<RoomBookingData>();
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = data.getStartEventDate();
		params[paramIndex++] = data.getEndEventDate();
		params[paramIndex++] = StringUtil.replaceSpecialString(data.getRoomIdSelected(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath()
				.getClassName(), getSqlPath().getPath(), "searchDataEventDraw",
				params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while(rst.next()) {
				RoomBookingData result = new RoomBookingData();
				result.setId(rst.getString("EVENT_ID"));
				result.getRoomBooking().setStartDate(rst.getString("EVENT_START_DATE"));
				result.getRoomBooking().setEndDate(rst.getString("EVENT_END_DATE"));
				result.getRoomBooking().setStartTime(rst.getString("EVENT_START_TIME"));
				result.getRoomBooking().setEndTime(rst.getString("EVENT_END_TIME"));
				result.getRoomBooking().setSubject(StringUtil.nullToString(rst.getString("EVENT_SUBJECT")));
				result.getRoomBooking().setDetail(StringUtil.nullToString(rst.getString("EVENT_DETAIL")));
				result.getRoomBooking().setAttendees(StringUtil.nullToString(rst.getString("EVENT_ATTENDEES")));
				result.getRoomBooking().setBookingStatusCode(StringUtil.nullToString(rst.getString("BOOKING_STATUS_CODE")));
				result.getRoomBooking().setBookingStatusName(StringUtil.nullToString(rst.getString("BOOKING_STATUS_NAME")));

				result.getRoomBooking().setUserBookId(StringUtil.nullToString(rst.getString("USER_BOOKING_ID")));
				result.getRoomBooking().setUserBookName(StringUtil.nullToString(rst.getString("USER_BOOKING_NAME")));
				
				result.getRoomSettingData().setId(StringUtil.nullToString(rst.getString("ROOM_ID")));
				result.getRoomSettingData().setName(StringUtil.nullToString(rst.getString("ROOM_NAME")));
				result.getRoomSettingData().setColor(StringUtil.nullToString(rst.getString("ROOM_COLOR")));

				result.getRoomBooking().setCreateUserId(StringUtil.nullToString(rst.getString("CREATE_USER_ID")));
				result.getRoomBooking().setCreateUserName(StringUtil.nullToString(rst.getString("CREATE_USER_NAME")));
				
				if (result.getRoomBooking().getCreateUserId().equals(user.getId())
						|| (result.getRoomBooking().getUserBookId().equals(user.getId()))) {
					result.setDataOwner(true);
				}
				
				result.setDataAdmin(flagAdmin);
				result.setCurrentTime(rst.getString("TO_TIME"));
				
				// ตรวจสอบวันที่เวลา Check Out เกิน Limit หรือไม่
				String todayDateTime = rst.getString("TODAY");
				String eventEndDateTime = rst.getString("EVENT_END_DATE_TIME");
				
				Calendar calendarTodayDateTime = CalendarUtil.getCalendarFromDateString(todayDateTime, ParameterConfig.getDateFormat().getForDatabaseSelectHHMM(), ParameterConfig.getApplication().getDatabaseLocale());
				Calendar calendarEventEndDateTime = CalendarUtil.getCalendarFromDateString(eventEndDateTime, ParameterConfig.getDateFormat().getForDatabaseSelectHHMM(), ParameterConfig.getApplication().getDatabaseLocale());
				calendarEventEndDateTime.add(Calendar.MINUTE, ParameterExtendedConfig.getParameterExtended().getDataOverLimitCheckOutMinutes());
				
				if (calendarEventEndDateTime.getTimeInMillis() < calendarTodayDateTime.getTimeInMillis()) {
					result.setDataOverLimitCheckOut(true);
				}
				
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
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	protected String createSQLEdit(CCTConnection arg0, RoomBooking arg1, CommonUser arg2, Locale arg3)
			throws Exception {
		return null;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	protected RoomBooking createResultSearchById(ResultSet arg0, CommonUser arg1, Locale arg2) throws Exception {
		return null;
	}
	
	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	protected String createSQLCheckDup(CCTConnection arg0, RoomBooking arg1, CommonUser arg2, Locale arg3)
			throws Exception {
		return null;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	protected String createSQLCountData(CCTConnection arg0, RoomBooking arg1, CommonUser arg2, Locale arg3)
			throws Exception {
		return null;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	protected String createSQLSearchById(CCTConnection arg0, String arg1, CommonUser arg2, Locale arg3)
			throws Exception {
		return null;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	protected String createSQLUpdateActive(CCTConnection arg0, String arg1, String arg2, CommonUser arg3, Locale arg4)
			throws Exception {
		return null;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	protected RoomBooking createResultSearch(ResultSet arg0, CommonUser arg1, Locale arg2) throws Exception {
		return null;
	}

	/**
	 * ไม่ได้ใช้งาน 
	 */
	@Deprecated
	@Override
	protected String createSQLSearch(CCTConnection arg0, RoomBooking arg1, CommonUser arg2, Locale arg3)
			throws Exception {
		return null;
	}

	@Override
	public InhouseUser getUser() {
		return (InhouseUser) super.getUser();
	}
	
	
}
