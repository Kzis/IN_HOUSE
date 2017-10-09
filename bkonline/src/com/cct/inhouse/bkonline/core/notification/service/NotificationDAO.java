package com.cct.inhouse.bkonline.core.notification.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.common.CommonDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.inhouse.bkonline.core.config.parameter.domain.ParameterExtendedConfig;
import com.cct.inhouse.bkonline.core.notification.domain.NotificationMessage;
import com.cct.inhouse.bkonline.core.notification.domain.NotificationTemplate;
import com.cct.inhouse.util.IHUtil;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class NotificationDAO extends CommonDAO {

	public NotificationDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	/**
	 * ข้อความ Template สำหรับส่ง noti
	 * @throws Exception 
	 */
	protected NotificationTemplate searchNotificationTemplate(CCTConnection conn, String bookingStatusCode) throws Exception {
		
		NotificationTemplate result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = bookingStatusCode;

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchNotificationTemplate", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = new NotificationTemplate();
				result.setSubject(StringUtil.nullToString(rst.getString("SUBJECT")));
				result.setDetail(StringUtil.nullToString(rst.getString("DETAIL")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * เพิ่ม Notification
	 * @throws Exception 
	 */
	protected void add(CCTConnection conn, NotificationMessage message) throws Exception {
		
		String startData = IHUtil.convertDatePickerForDatabase(message.getEventStartDate());
		
		int paramIndex = 0;
		Object[] params = new Object[17];
		params[paramIndex++] = StringUtil.stringToLong(message.getEventId());
		params[paramIndex++] = StringUtil.stringToLong(message.getReceiverUserId());
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getSubject(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getDetail(), conn.getDbType(), ResultType.NULL);
		
		params[paramIndex++] = message.getStatusForm();
		params[paramIndex++] = message.getStatusTo();
		
		params[paramIndex++] = message.getUserId();
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getUserName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = message.getCreateDateTime();
		params[paramIndex++] = message.getUserId();
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getUserName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = message.getCreateDateTime();
		
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getRoomName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getEventSubject(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(startData, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getEventStartTime(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getEventEndTime(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"insertNotification", params);
		
		getLogger().debug(sql);
		
		executeInsert(conn, sql);
		
	}
	
	
	/**
	 * นับจำนวนข้อความใหม่ตามคนรับ
	 * @throws Exception 
	 */
	protected int searchCountNotificationStatusTodo(CCTConnection conn, CommonUser user) throws Exception {
		
		int result = -1;

		int paramIndex = 0;
		Object[] params = new Object[10];
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchCountNotificationStatusTodo", params);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = rst.getInt(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return result;
	}
	
	protected int searchCountNotificationStatusAction(CCTConnection conn, CommonUser user) throws Exception {
		
		int result = -1;

		int paramIndex = 0;
		Object[] params = new Object[10];
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchCountNotificationStatusAction", params);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = rst.getInt(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return result;
	}
	
	/**
	 * ข้อความล่าสุดตามคนรับโดยเรียงจากวันที่ปัจจุบัน
	 * @throws Exception 
	 */
	protected List<NotificationMessage> searchNotificationTodo(CCTConnection conn, CommonUser user) throws Exception {
		
		List<NotificationMessage> listResult = new ArrayList<NotificationMessage>();

		int paramIndex = 0;
		Object[] params = new Object[10];
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchNotificationTodo", params);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				NotificationMessage result = new NotificationMessage();
				result.setNotificationId(StringUtil.nullToString(rst.getString("NOTIFICATION_ID")));
				
				result.setEventId(StringUtil.nullToString(rst.getString("EVENT_ID")));
				result.setReceiverUserId(StringUtil.nullToString(rst.getString("RECEIVER_USER_ID")));
				
				result.setSubject(StringUtil.nullToString(rst.getString("SUBJECT")));
				result.setDetail(StringUtil.nullToString(rst.getString("DETAIL")));
				
				result.setStatusForm(StringUtil.nullToString(rst.getString("STATUS_FROM")));
				result.setStatusTo(StringUtil.nullToString(rst.getString("STATUS_TO")));
				result.setStatusRead(StringUtil.nullToString(rst.getString("STATUS_READ")));
				
				result.setUserId(StringUtil.nullToString(rst.getString("CREATE_USER_ID")));
				result.setUserName(StringUtil.nullToString(rst.getString("CREATE_USER_NAME")));
				
				result.setCreateDateTime(StringUtil.nullToString(rst.getString("CREATE_DATE_TIME")));
				
				result.setRoomName(StringUtil.nullToString(rst.getString("ROOM_NAME")));
				result.setEventSubject(StringUtil.nullToString(rst.getString("EVENT_SUBJECT")));
				result.setEventStartDate(StringUtil.nullToString(rst.getString("EVENT_START_DATE")));
				result.setEventStartTime(StringUtil.nullToString(rst.getString("EVENT_START_TIME")));
				result.setEventEndTime(StringUtil.nullToString(rst.getString("EVENT_END_TIME")));
				
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
	 * ข้อความล่าสุดตามคนรับโดยเรียงจากวันที่ปัจจุบัน
	 * @throws Exception 
	 */
	protected List<NotificationMessage> searchNotificationAction(CCTConnection conn, CommonUser user) throws Exception {
		
		List<NotificationMessage> listResult = new ArrayList<NotificationMessage>();

		int paramIndex = 0;
		Object[] params = new Object[10];
		params[paramIndex++] = StringUtil.stringToLong(user.getId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchNotificationAction", params);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				NotificationMessage result = new NotificationMessage();
				result.setNotificationId(StringUtil.nullToString(rst.getString("NOTIFICATION_ID")));
				
				result.setEventId(StringUtil.nullToString(rst.getString("EVENT_ID")));
				result.setReceiverUserId(StringUtil.nullToString(rst.getString("RECEIVER_USER_ID")));
				
				result.setSubject(StringUtil.nullToString(rst.getString("SUBJECT")));
				result.setDetail(StringUtil.nullToString(rst.getString("DETAIL")));
				
				result.setStatusForm(StringUtil.nullToString(rst.getString("STATUS_FROM")));
				result.setStatusTo(StringUtil.nullToString(rst.getString("STATUS_TO")));
				result.setStatusRead(StringUtil.nullToString(rst.getString("STATUS_READ")));
				
				result.setUserId(StringUtil.nullToString(rst.getString("CREATE_USER_ID")));
				result.setUserName(StringUtil.nullToString(rst.getString("CREATE_USER_NAME")));
				
				result.setCreateDateTime(StringUtil.nullToString(rst.getString("CREATE_DATE_TIME")));
				
				result.setRoomName(StringUtil.nullToString(rst.getString("ROOM_NAME")));
				result.setEventSubject(StringUtil.nullToString(rst.getString("EVENT_SUBJECT")));
				result.setEventStartDate(StringUtil.nullToString(rst.getString("EVENT_START_DATE")));
				result.setEventStartTime(StringUtil.nullToString(rst.getString("EVENT_START_TIME")));
				result.setEventEndTime(StringUtil.nullToString(rst.getString("EVENT_END_TIME")));
				
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
	 * เปลื่ยนสถานะเป็นลบ
	 * @throws Exception 
	 */
	protected void updateDeleteNotification(CCTConnection conn, NotificationMessage message) throws Exception {
		
		if ((message.getEventId() == null) || message.getEventId().trim().isEmpty()) {
			return;
		}
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = IHUtil.convertLongValue(message.getUserId());
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getUserName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(message.getEventId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"updateDeleteNotification", params);
		
		getLogger().debug(sql);
		
		executeUpdate(conn, sql);
	}
	
	/**
	 * เปลื่ยนสถานะเป็นลบ
	 * @throws Exception 
	 */
	protected void updateDeleteNotificationByNotificationId(CCTConnection conn, NotificationMessage message) throws Exception {
		
		if ((message.getNotificationId() == null) || message.getNotificationId().trim().isEmpty()) {
			return;
		}
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = IHUtil.convertLongValue(message.getUserId());
		params[paramIndex++] = StringUtil.replaceSpecialString(message.getUserName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.stringToLong(message.getNotificationId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"updateDeleteNotificationByNotificationId", params);
		
		getLogger().debug(sql);
		
		executeUpdate(conn, sql);
	}
	
	protected String searchLineIdFromReciverId(CCTConnection conn, String reciverId) throws Exception {
		String lineId = null;
		if ((reciverId == null) || reciverId.trim().isEmpty()) {
			return lineId;
		}
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = IHUtil.convertLongValue(reciverId);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchLineIdFromReciverId", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				lineId = rst.getString("LINE_USER_ID");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return lineId;
	}
	
	/**
	 * ค้น EventId ที่ไม่ได้ CheckIn
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected Map<String, String> searchEventIdIfNotCheckIn(CCTConnection conn) throws Exception {
		
		Map<String, String> mapEventId = new LinkedHashMap<String, String>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = ParameterExtendedConfig.getParameterExtended().getTimeCheckedCheckIn();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchEventIdIfNotCheckIn", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				mapEventId.put(rst.getString("EVENT_ID"), rst.getString("EVENT_ID"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return mapEventId;
	}
	
	/**
	 * ค้น Notification Message ที่ไม่ได้ CheckIn
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected boolean searchCountNotificationNotCheckIn(CCTConnection conn, String eventId) throws Exception {
		
		boolean foundNotificationMessage = false;
		
		if ((eventId == null) || eventId.trim().isEmpty()) {
			return true;
		}
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchCountNotificationNotCheckIn", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				foundNotificationMessage = (rst.getInt(1) > 0);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return foundNotificationMessage;
	}
	
	/**
	 * ค้น EventId ที่ไม่ได้ CheckOut
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected Map<String, String> searchEventIdIfNotCheckOut(CCTConnection conn) throws Exception {
		
		Map<String, String> mapEventId = new LinkedHashMap<String, String>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = ParameterExtendedConfig.getParameterExtended().getTimeCheckedCheckOut();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchEventIdIfNotCheckOut", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				mapEventId.put(rst.getString("EVENT_ID"), rst.getString("EVENT_ID"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return mapEventId;
	}
	
	/**
	 * ค้น Notification Message ที่ไม่ได้ CheckOut
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected boolean searchCountNotificationNotCheckOut(CCTConnection conn, String eventId) throws Exception {
		
		boolean foundNotificationMessage = false;
		
		if ((eventId == null) || eventId.trim().isEmpty()) {
			return true;
		}
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = IHUtil.convertLongValue(eventId);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(),
				"searchCountNotificationNotCheckOut", params);
		
		getLogger().debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				foundNotificationMessage = (rst.getInt(1) > 0);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return foundNotificationMessage;
	}
}
