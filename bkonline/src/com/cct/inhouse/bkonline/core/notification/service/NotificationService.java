package com.cct.inhouse.bkonline.core.notification.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.common.CommonService;
import com.cct.common.CommonUser;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBooking;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingData;
import com.cct.inhouse.bkonline.core.booking.roombooking.service.RoomBookingManager;
import com.cct.inhouse.bkonline.core.config.parameter.domain.ParameterExtendedConfig;
import com.cct.inhouse.bkonline.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.bkonline.core.notification.domain.NotificationMessage;
import com.cct.inhouse.bkonline.core.notification.domain.NotificationTemplate;
import com.cct.inhouse.bkonline.core.security.user.domain.AdminUser;
import com.cct.inhouse.bkonline.core.security.user.service.UserManager;
import com.cct.inhouse.bkonline.domain.BKOnlineVariable;
import com.cct.inhouse.bkonline.util.notificationmessage.NotificationMessageUtil;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.linebot.web.linebot.ws.LineBotWebServiceProxy;

import util.database.CCTConnection;
import util.date.DateUtil;
import util.string.StringDelimeter;

public class NotificationService extends CommonService {

	private NotificationDAO dao = null;
	
	public NotificationService(Logger logger) {
		super(logger);
		this.dao = new NotificationDAO(logger, SQLPath.NOTIFICATION_SQL.getSqlPath());
	}
	
	protected int searchCountNotificationStatusTodo(CCTConnection conn, CommonUser user) throws Exception {
		return dao.searchCountNotificationStatusTodo(conn, user);
	}
	
	protected int searchCountNotificationStatusAction(CCTConnection conn, CommonUser user) throws Exception {
		return dao.searchCountNotificationStatusAction(conn, user);
	}
	
	protected List<NotificationMessage> searchNotificationTodo(CCTConnection conn, CommonUser user) throws Exception {
		return dao.searchNotificationTodo(conn, user);
	}
	
	protected List<NotificationMessage> searchNotificationAction(CCTConnection conn, CommonUser user) throws Exception {
		return dao.searchNotificationAction(conn, user);
	}
	
	/**
	 * จองห้องต้องส่งให้ Admin, ผู้สร้างการจอง, ผู้จอง 
	 * @param conn
	 * @param bookingData
	 * @throws Exception
	 */
	protected void addBooking(CCTConnection conn, RoomBookingData bookingData, CommonUser user) throws Exception {
		NotificationTemplate notificationTemplate = dao.searchNotificationTemplate(conn, bookingData.getRoomBooking().getBookingStatusCode());
		
		NotificationMessage message = createMessage(conn, notificationTemplate, bookingData, null, BKOnlineVariable.BookingStatus.WAIT.getCode(), user);
		
		addNotificationMessage(conn, message, bookingData, user);		
	}
	
	/**
	 * อนุมัติต้องส่งให้ Admin, ผู้สร้างการจอง, ผู้จอง 
	 * @param conn
	 * @param bookingData
	 * @param user
	 * @throws Exception
	 */
	protected void addApprove(CCTConnection conn, RoomBookingData bookingData, RoomBooking lastStatus, CommonUser user) throws Exception {
		NotificationTemplate notificationTemplate = dao.searchNotificationTemplate(conn, bookingData.getRoomBooking().getBookingStatusCode());
		
		NotificationMessage message = createMessage(conn, notificationTemplate, bookingData, lastStatus, BKOnlineVariable.BookingStatus.APPROVE.getCode(), user);
		
		// ลบ event
		dao.updateDeleteNotification(conn, message);
		
		addNotificationMessage(conn, message, bookingData, user);		
	}

	/**
	 * เข้าใช้งานต้องส่งให้ Admin, ผู้สร้างการจอง, ผู้จอง 
	 * @param conn
	 * @param bookingData
	 * @param user
	 * @throws Exception
	 */
	protected void addCheckIn(CCTConnection conn, RoomBookingData bookingData, RoomBooking lastStatus, CommonUser user) throws Exception {
		NotificationTemplate notificationTemplate = dao.searchNotificationTemplate(conn, bookingData.getRoomBooking().getBookingStatusCode());
		
		NotificationMessage message = createMessage(conn, notificationTemplate, bookingData, lastStatus, BKOnlineVariable.BookingStatus.CHECK_IN.getCode(), user);
		
		// ลบ event
		dao.updateDeleteNotification(conn, message);
		
		addNotificationMessage(conn, message, bookingData, user);		
	}
	
	/**
	 * คืนห้องต้องส่งให้ Admin, ผู้สร้างการจอง, ผู้จอง 
	 * @param conn
	 * @param bookingData
	 * @param user
	 * @throws Exception
	 */
	protected void addCheckOut(CCTConnection conn, RoomBookingData bookingData, RoomBooking lastStatus, CommonUser user) throws Exception {
		NotificationTemplate notificationTemplate = dao.searchNotificationTemplate(conn, bookingData.getRoomBooking().getBookingStatusCode());
		
		NotificationMessage message = createMessage(conn, notificationTemplate, bookingData, lastStatus, BKOnlineVariable.BookingStatus.CHECK_OUT.getCode(), user);
		
		// ลบ event
		dao.updateDeleteNotification(conn, message);
		
		addNotificationMessage(conn, message, bookingData, user);		
	}
	
	/**
	 * ยกเลิกห้องต้องส่งให้ Admin, ผู้สร้างการจอง, ผู้จอง 
	 * @param conn
	 * @param bookingData
	 * @param user
	 * @throws Exception
	 */
	protected void addCancel(CCTConnection conn, RoomBookingData bookingData, RoomBooking lastStatus, CommonUser user) throws Exception {
		NotificationTemplate notificationTemplate = dao.searchNotificationTemplate(conn, bookingData.getRoomBooking().getBookingStatusCode());
		
		NotificationMessage message = createMessage(conn, notificationTemplate, bookingData, lastStatus, BKOnlineVariable.BookingStatus.CANCELED.getCode(), user);
		
		// ลบ event
		dao.updateDeleteNotification(conn, message);
		
		addNotificationMessage(conn, message, bookingData, user);		
	}
	
	/**
	 * ส่ง message แจ้งเตือนยังไม่ได้ใช้งานห้อง
	 * @param conn
	 * @throws Exception
	 */
	protected void addNotCheckIn(CCTConnection conn) throws Exception {

		InhouseUser tempUser = new InhouseUser();
		tempUser.setId("-1");
		tempUser.setDisplayName("ระบบอัตโนมัติ");
		
		RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), tempUser, ParameterConfig.getApplication().getApplicationLocale());
		
		try {
			Map<String, String> mapEventId = dao.searchEventIdIfNotCheckIn(conn);
			for (String eventId : mapEventId.keySet()) {
				getLogger().debug("eventId: " + eventId);
				if (dao.searchCountNotificationNotCheckIn(conn, eventId)) {
					// เคยแจ้งเตือนไปแล้ว
					continue;
				}
				
				RoomBookingData bookingData = roomBookingManager.searchDataEventDrawByEventId(conn, eventId);
				RoomBooking lastStatus = bookingData.getRoomBooking();
				
				NotificationTemplate notificationTemplate = dao.searchNotificationTemplate(conn, BKOnlineVariable.BookingStatus.NOT_CHECK_IN.getCode());
				
				NotificationMessage message = createMessage(conn, notificationTemplate, bookingData, lastStatus, BKOnlineVariable.BookingStatus.NOT_CHECK_IN.getCode(), tempUser);
				
				// ลบ event
				dao.updateDeleteNotification(conn, message);
				
				addNotificationMessage(conn, message, bookingData, tempUser);		
			}
			
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	/**
	 * ส่ง message แจ้งเตือนยังไม่ได้คืนห้อง
	 * @param conn
	 * @throws Exception
	 */
	protected void addNotCheckOut(CCTConnection conn) throws Exception {

		InhouseUser tempUser = new InhouseUser();
		tempUser.setId("-1");
		tempUser.setDisplayName("ระบบอัตโนมัติ");
		
		RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), tempUser, ParameterConfig.getApplication().getApplicationLocale());
		
		try {
			Map<String, String> mapEventId = dao.searchEventIdIfNotCheckOut(conn);
			for (String eventId : mapEventId.keySet()) {
				getLogger().debug("eventId: " + eventId);
				if (dao.searchCountNotificationNotCheckOut(conn, eventId)) {
					// เคยแจ้งเตือนไปแล้ว
					continue;
				}
				
				RoomBookingData bookingData = roomBookingManager.searchDataEventDrawByEventId(conn, eventId);
				RoomBooking lastStatus = bookingData.getRoomBooking();
				
				NotificationTemplate notificationTemplate = dao.searchNotificationTemplate(conn, BKOnlineVariable.BookingStatus.NOT_CHECK_OUT.getCode());
				
				NotificationMessage message = createMessage(conn, notificationTemplate, bookingData, lastStatus, BKOnlineVariable.BookingStatus.NOT_CHECK_OUT.getCode(), tempUser);
				
				// ลบ event
				dao.updateDeleteNotification(conn, message);
				
				addNotificationMessage(conn, message, bookingData, tempUser);		
			}
			
		} catch (Exception e) {
			getLogger().error(e);
			throw new Exception("ไม่สามารถส่งข้อความแจ้งเตือนได้");
		}
	}
	
	/**
	 * สร้าง Notificatoin Message
	 * @param notificationTemplate
	 * @param bookingData
	 * @param statusForm
	 * @param statusTo
	 * @param user
	 * @return
	 */
	private NotificationMessage createMessage(CCTConnection conn, NotificationTemplate notificationTemplate, RoomBookingData bookingData, RoomBooking lastStatus, String statusTo, CommonUser user) {
		NotificationMessage message = new NotificationMessage();
		message.setEventId(bookingData.getId());
		message.setSubject(notificationTemplate.getSubject());
		message.setDetail(notificationTemplate.getDetail());
		if (lastStatus == null) {
			message.setStatusForm(null);
		} else {
			message.setStatusForm(lastStatus.getBookingStatusCode());	
		}
		message.setStatusTo(statusTo);
		message.setUserId(user.getId());
		message.setUserName(((InhouseUser)user).getDisplayName());
		
		message.setRoomName(bookingData.getRoomSettingData().getName());
		message.setEventSubject(bookingData.getRoomBooking().getSubject());
		message.setEventStartDate(bookingData.getRoomBooking().getStartDate());
		message.setEventStartTime(bookingData.getRoomBooking().getStartTime());
		message.setEventEndTime(bookingData.getRoomBooking().getEndTime());
		
		String createDateTime = StringDelimeter.EMPTY.getValue();
		try {
			createDateTime = DateUtil.getCurrentDateDB(conn.getConn(), conn.getDbType());
		} catch (Exception e) {
			getLogger().error(e);
		}
		message.setCreateDateTime(createDateTime);
		return message;
	}
	
	/**
	 * เพิ่ม message ลงฐาน
	 * @param conn
	 * @param message
	 * @param bookingData
	 * @param user
	 * @throws Exception
	 */
	private void addNotificationMessage(CCTConnection conn, NotificationMessage message, RoomBookingData bookingData, CommonUser user) throws Exception {
		
		UserManager userManager = new UserManager(getLogger());
		
		// ส่งให้ Admin แต่และไม่ให้ตัวเราเอง
		Map<String, AdminUser> mapAdminUser = userManager.searchAdminInSystem(conn);
		
		if (mapAdminUser.get(user.getId()) != null) {
			// กรณี Admin เป็นผู้จองเองต้องแจ้งเตือน Admin คนอื่นๆ
			for (String key : mapAdminUser.keySet()) {
				getLogger().debug(key + " > " + user.getId());
				if (!key.equals(user.getId())) {
					// ส่งให้ Admin คนอื่นๆ
					message.setReceiverUserId(key);
					dao.add(conn, message);
					sentNotificationMessage(conn, message);
				}
			}

		} else {
			// กรณี ผู้ใช้ทั่วไปเป็นผู้จองต้องแจ้งเตือน Admin ทุกๆ คน
			for (String key : mapAdminUser.keySet()) {
				message.setReceiverUserId(key);
				dao.add(conn, message);
				sentNotificationMessage(conn, message);
			}
		}

		// ส่งข้อความให้คนจอง โดยไม่ต้องส่งให้ตนเอง
		if ((user.getId() == null) || !bookingData.getRoomBooking().getUserBookId().equals(user.getId())) {
			// ถ้าเป็น admin ไม่ต้องส่งแล้ว เนื่องส่งด้านบนแล้ว
			if (mapAdminUser.get(bookingData.getRoomBooking().getUserBookId()) == null) {
				message.setReceiverUserId(bookingData.getRoomBooking().getUserBookId());
				dao.add(conn, message);	
				sentNotificationMessage(conn, message);
			}
		}
		
		// ถ้าคนสร้างกับคนจองเป็นคนละคน ให้ส่ง  Message ให้คนสร้างด้วย
		if (!bookingData.getRoomBooking().getCreateUserId().equals(bookingData.getRoomBooking().getUserBookId())) {

			//  โดยไม่ต้องส่งให้ตนเอง
			if ((user.getId() == null) || !bookingData.getRoomBooking().getCreateUserId().equals(user.getId())) {
				// ถ้าเป็น admin ไม่ต้องส่งแล้ว เนื่องส่งด้านบนแล้ว
				if (mapAdminUser.get(bookingData.getRoomBooking().getCreateUserId()) == null) {
					message.setReceiverUserId(bookingData.getRoomBooking().getCreateUserId());
					dao.add(conn, message);	
					sentNotificationMessage(conn, message);
				}
			}
		} 
	}
	
	
	/**
	 * เคลียร์ Badge และ Message กรณีคลิกแล้ว
	 * @param conn
	 * @param notificationId
	 * @throws Exception 
	 */
	protected void clearNotificationMessage(CCTConnection conn, String notificationId,  CommonUser user) throws Exception {
		
		NotificationMessage message = new NotificationMessage();
		message.setNotificationId(notificationId);
		message.setUserId(user.getId());
		message.setUserName(((InhouseUser)user).getDisplayName());
		
		dao.updateDeleteNotificationByNotificationId(conn, message);
	}
	
	
	protected void sentNotificationMessage(CCTConnection conn, NotificationMessage message) throws Exception {
		// หาผู้รับ
		String lineId = dao.searchLineIdFromReciverId(conn, message.getReceiverUserId());
		
		// สร้าง Message ตามประเภท
		String messageForSent = "";
		if (message.getStatusTo().equals(BKOnlineVariable.BookingStatus.WAIT.getCode())) {
			messageForSent = NotificationMessageUtil.createMessageWait(message);
			
		} else if (message.getStatusTo().equals(BKOnlineVariable.BookingStatus.APPROVE.getCode())) {
			messageForSent = NotificationMessageUtil.createMessageApprove(message);
			
		} else if (message.getStatusTo().equals(BKOnlineVariable.BookingStatus.CHECK_IN.getCode())) {
			messageForSent = NotificationMessageUtil.createMessageCheckIn(message);
			
		} else if (message.getStatusTo().equals(BKOnlineVariable.BookingStatus.CHECK_OUT.getCode())) {
			messageForSent = NotificationMessageUtil.createMessageCheckOut(message);
			
		} else if (message.getStatusTo().equals(BKOnlineVariable.BookingStatus.CANCELED.getCode())) {
			messageForSent = NotificationMessageUtil.createMessageCancel(message);
			
		} else if (message.getStatusTo().equals(BKOnlineVariable.BookingStatus.NOT_CHECK_IN.getCode())) {
			messageForSent = NotificationMessageUtil.createMessageNotCheckIn(message);
			
		} else if (message.getStatusTo().equals(BKOnlineVariable.BookingStatus.NOT_CHECK_OUT.getCode())) {
			messageForSent = NotificationMessageUtil.createMessageNotCheckOut(message);
			
		}
		
		// ส่ง
		sentNotificationMessageToUser(lineId, messageForSent);
	}
	
	private void sentNotificationMessageToUser(String lineId, String message) throws Exception {
		
		if ((lineId == null) || lineId.trim().isEmpty()) {
			getLogger().debug("Line ID is null or empty.");
			return;
		}
		
		byte[] bytes = message.getBytes("UTF-8");
		LineBotWebServiceProxy service = new LineBotWebServiceProxy(ParameterExtendedConfig.getParameterExtended().getLineBotWebServiceProxy());
		boolean result = service.sendByteMessage(ParameterExtendedConfig.getParameterExtended().getLineBotChannelAccessToken(), lineId, bytes);
		getLogger().debug("Line: " + (result ? "SUCCESS" : "NOT SUCCESS"));
	}
	
}
