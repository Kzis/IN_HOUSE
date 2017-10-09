package com.cct.inhouse.bkonline.core.mobile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import util.cryptography.EncryptionUtil;
import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;
import util.type.CrytographyType.EncType;

import com.cct.common.CommonManager;
import com.cct.exception.AuthenticateException;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBooking;
import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingData;
import com.cct.inhouse.bkonline.core.booking.roombooking.service.RoomBookingManager;
import com.cct.inhouse.bkonline.core.mobile.domain.MDExceptionFlag;
import com.cct.inhouse.bkonline.core.mobile.domain.MDNotification;
import com.cct.inhouse.bkonline.core.mobile.domain.MDNotificationMessage;
import com.cct.inhouse.bkonline.core.mobile.domain.MDRoomBooking;
import com.cct.inhouse.bkonline.core.mobile.domain.MDRoomBookingData;
import com.cct.inhouse.bkonline.core.mobile.domain.MDRoomSettingData;
import com.cct.inhouse.bkonline.core.mobile.domain.MDStatus;
import com.cct.inhouse.bkonline.core.mobile.domain.MDUser;
import com.cct.inhouse.bkonline.core.mobile.domain.MobileData;
import com.cct.inhouse.bkonline.core.notification.domain.NotificationMessage;
import com.cct.inhouse.bkonline.core.notification.service.NotificationManager;
import com.cct.inhouse.bkonline.core.security.user.domain.TempUser;
import com.cct.inhouse.bkonline.core.security.user.service.UserManager;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.RoomSettingData;
import com.cct.inhouse.bkonline.core.setting.roomsetting.service.SettingManager;
import com.cct.inhouse.bkonline.domain.BKOnlineVariable;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.enums.DBLookup;
import com.cct.inhouse.util.database.CCTConnectionProvider;

public class MobileManager extends CommonManager {

	public static final int LIMIT_LINE_ERROR = 5;
	
	private MobileService service = null;
	public MobileManager(Logger logger) {
		super(logger);
		this.service = new MobileService(logger);
	}

	/**
	 * ขั้นตอนการ Login ใหม่<br>
	 * 1. หา User Id/ User Info จาก searchUserLogin (ดัก Exception)<br>
	 * 2. ลบข้อมูล Table Token ด้วยเงือนไข device id<br>
	 * 3. เพิ่มข้อมูล Table Token (user id + device id + platFrom = Token id)<br>
	 * @param mobileData
	 * @throws Exception 
	 */
	public void newLogin(MobileData mobileData) throws Exception {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			// 1.หา User Id/ User Info จาก searchUserLogin (ดัก Exception)
			UserManager userManager = new UserManager(getLogger());
			TempUser tempUser = userManager.searchUserLogin(conn, mobileData.getMdUser().getUserName(), mobileData.getMdUser().getPassword());
			
			// 2.ลบข้อมูล Table Token ด้วยเงือนไข device id
			service.deleteDeviceInfo(conn, mobileData.getMdUser().getDeviceId());
			
			// 3.Generate Token Id user id + device id + platFrom = Token id
			setMdUserToTempUser(mobileData.getMdUser(),tempUser);
			String tokenId = EncryptionUtil.doEncrypt(mobileData.getMdUser().getUserId()+mobileData.getMdUser().getDeviceId()+mobileData.getMdUser().getPlatform(), EncType.MD5);
			mobileData.getMdUser().setTokenId(tokenId);
			
			// 4.เพิ่มข้อมูล Table Token (user id + device id + platFrom = Token id)
			service.addDeviceInfo(conn, mobileData.getMdUser().getUserId(), mobileData.getMdUser().getDeviceId(), mobileData.getMdUser().getPlatform(), mobileData.getMdUser().getTokenId());
			
		} catch (AuthenticateException ae) {
			MDStatus status = new MDStatus();
			status.setExceptionFlag(MDExceptionFlag.LOGIN_FAIL.getFlag());
			status.setMessage(MDExceptionFlag.LOGIN_FAIL.getMessage());
			MobileManager.setException(status, ae);
			throw status;
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
	}
	
	

	/**
	 * ขั้นตอนการ Login อยู่แล้ว<br>
	 * 1. หา User Id จาก Table Token ด้วยเงือนไข Token id  (ดัก Exception)
	 * 2. หา User Info จาก searchUserInSystem  (ดัก Exception)
	 * @param mobileData
	 * @throws Exception 
	 */
	public void pastLogin(MobileData mobileData) throws Exception {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			//1. หา User Id จาก Table Token ด้วยเงือนไข Token id  (ดัก Exception)
			service.checkTokenId(conn, mobileData.getMdUser());
			
			//2. หา User Info จาก searchUserInSystem  (ดัก Exception)
			UserManager userManager = new UserManager(getLogger());
			InhouseUser userInfo = userManager.searchUserInSystem(conn, mobileData.getMdUser().getUserId());
			
			//3. set UserInfo
			setsInhouseUserToMdUser(mobileData.getMdUser(), userInfo);
			
		} catch (AuthenticateException ae) {
			MDStatus status = new MDStatus();
			status.setExceptionFlag(MDExceptionFlag.LOGIN_FAIL.getFlag());
			status.setMessage(MDExceptionFlag.LOGIN_FAIL.getMessage());
			MobileManager.setException(status, ae);
			throw status;
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
	}
	
	public void searchRoomMeeting(MobileData mobileData) throws Exception {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			
			SettingManager manager = new SettingManager(getLogger(), setMdUserToInhouseUser(mobileData.getMdUser()),setMdUserToInhouseUser(mobileData.getMdUser()).getLocale());
			mobileData.setMdRoom(setListRoomSettingToListMDRoom(manager.search(conn, null)));
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
	}

	public void searchRoomEvent(MobileData mobileData) throws Exception{
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			RoomBookingManager managerEvent = new RoomBookingManager(getLogger(), setMdUserToInhouseUser(mobileData.getMdUser()),setMdUserToInhouseUser(mobileData.getMdUser()).getLocale());
			SettingManager managerRoom = new SettingManager(getLogger(), setMdUserToInhouseUser(mobileData.getMdUser()),setMdUserToInhouseUser(mobileData.getMdUser()).getLocale());
			mobileData.setMdRoom(setListRoomSettingToListMDRoom(managerRoom.search(conn, null)));
			List<RoomSettingData> lstRoom = setListListMDRoomToListRoomSetting(mobileData.getMdRoom());
 			for (int i = 0; i < lstRoom.size(); i++) {
				mobileData.getMdEvent().addAll((
						setListRoomBookingDataToListMDRoomEvent(
								managerEvent.searchDataEventDraw(conn, setDefultRoomBookingData(lstRoom.get(i).getId(),mobileData.getMdRoomSelect()))
								)
				));
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void updateApprove(MobileData mobileData) throws Exception{
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), setMdUserToInhouseUser(mobileData.getMdUser()), setMdUserToInhouseUser(mobileData.getMdUser()).getLocale());
		    roomBookingManager.updateBookingStatusApprove(conn, mobileData.getMdEventSelect().getEventIdSelected());
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void updateCheckOut(MobileData mobileData) throws Exception{
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), setMdUserToInhouseUser(mobileData.getMdUser()), setMdUserToInhouseUser(mobileData.getMdUser()).getLocale());
			roomBookingManager.updateBookingStatusCheckOut(conn, mobileData.getMdEventSelect().getEventIdSelected());
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void updateCheckOutOverLimit(MobileData mobileData) throws Exception{
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), setMdUserToInhouseUser(mobileData.getMdUser()), setMdUserToInhouseUser(mobileData.getMdUser()).getLocale());
			roomBookingManager.updateBookingStatusOverLimitCheckOut(conn, mobileData.getMdEventSelect().getEventIdSelected(),mobileData.getMdEventSelect().getTimeOverLimitCheckOut());
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void updateCancel(MobileData mobileData) throws Exception{
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), setMdUserToInhouseUser(mobileData.getMdUser()), setMdUserToInhouseUser(mobileData.getMdUser()).getLocale());
			roomBookingManager.updateBookingStatusCancel(conn, mobileData.getMdEventSelect().getEventIdSelected());
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void searchNotification(MobileData mobileData)throws Exception {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			MDNotification mdNotification = new MDNotification();
			
			NotificationManager manager = new NotificationManager(getLogger());
			mdNotification.setTotalTodoMessage(manager.searchCountNotificationStatusTodo(conn, setMdUserToInhouseUser(mobileData.getMdUser())));
			mdNotification.setListTodoMessage(setNotiToMDNoti(manager.searchNotificationTodo(conn, setMdUserToInhouseUser(mobileData.getMdUser()))));
			
			mdNotification.setTotalActionMessage(manager.searchCountNotificationStatusAction(conn, setMdUserToInhouseUser(mobileData.getMdUser())));
			mdNotification.setListActionMessage(setNotiToMDNoti(manager.searchNotificationAction(conn, setMdUserToInhouseUser(mobileData.getMdUser()))));
			
			mobileData.setMdNotification(mdNotification);
			
		} catch (Exception e) {
			throw e;
		}finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void clearNotification(MobileData mobileData)throws Exception {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, getDbLookup());
			NotificationManager manager = new NotificationManager(getLogger());
			manager.clearNotificationMessage(conn, mobileData.getMdNotification().getSelectNotificationId(), setMdUserToInhouseUser(mobileData.getMdUser()));
		}catch (Exception e) {
			throw e;
		}finally {
			CCTConnectionUtil.close(conn);
			searchNotification(mobileData);
		}
	}
	public static void setMdUserToTempUser(MDUser mdUser,TempUser tempUser) {
		
		mdUser.setUserId(tempUser.getId());
		mdUser.setDepartmentId(tempUser.getDepartmentId());
		mdUser.setPositionId(tempUser.getPositionId());
		mdUser.setTitleName(tempUser.getTitleName());
		mdUser.setName(tempUser.getName());
		mdUser.setSurName(tempUser.getSurName());
		mdUser.setNickName(tempUser.getNickName());
		mdUser.setDisplayName(tempUser.getDisplayName());
		
		mdUser.setEmail(tempUser.getEmail());
		mdUser.setGmail(tempUser.getGmail());
		mdUser.setLineUserId(tempUser.getLineUserId());
		
		mdUser.setColorCode(tempUser.getColorCode());
		mdUser.setTableNo(tempUser.getTableNo());
		mdUser.setInsidePhoneNumber(tempUser.getInsidePhoneNumber());
		mdUser.setCardId(tempUser.getCardId());
		mdUser.setUpdateDate(tempUser.getUpdateDate());
	}
	public static RoomBookingData setDefultRoomBookingData(String id,MDRoomBookingData room) {
		RoomBookingData result = new RoomBookingData();
		result.setRoomIdSelected(id);
		if (room != null) {
			if (room.getCurrentMode() != null) {
				result.setCurrentMode(room.getCurrentMode());
			}else {
				result.setCurrentMode(BKOnlineVariable.Mode.DAY.getValue());
			}
			if (room.getCurrentStep() != null) {
				result.setCurrentStep(room.getCurrentStep());
				if (!room.getCurrentStep().isEmpty()) {
					if (room.getCurrentDay() != null) {
						result.setCurrentDay(room.getCurrentDay());
					}
				}
			}else{
				result.setCurrentStep(StringDelimeter.EMPTY.getValue());
			}
		}else {
			result.setCurrentMode(BKOnlineVariable.Mode.DAY.getValue());
			result.setCurrentStep(StringDelimeter.EMPTY.getValue());
		}
		return result;
	}
	
	public static void setException(MDStatus status, Exception e) {
		status.setExceptionDesc(getErrorMessage(e));
		status.setStackTrace(e.getStackTrace());
	}
	
	public static List<RoomSettingData> setListListMDRoomToListRoomSetting(List<MDRoomSettingData> roomData) {
		List<RoomSettingData> result = new ArrayList<RoomSettingData>();
		RoomSettingData mdRoom;
		for (int i = 0; i < roomData.size(); i++) {
			mdRoom = new RoomSettingData();
			mdRoom.setId(roomData.get(i).getId());	
			mdRoom.setName(roomData.get(i).getName());	
			mdRoom.setColor(roomData.get(i).getColor());	
			mdRoom.setAutotime(roomData.get(i).getAutotime());
			mdRoom.setAttendeesMax(roomData.get(i).getAttendeesMax());
			mdRoom.setPhone(roomData.get(i).getPhone());
			mdRoom.setDetail(roomData.get(i).getDetail());
			mdRoom.setEquipmentListId(roomData.get(i).getEquipmentListId());
			mdRoom.setPriority(roomData.get(i).getPriority());
			result.add(mdRoom);
		}
		return result;
	}
	public static List<MDRoomSettingData> setListRoomSettingToListMDRoom(List<RoomSettingData> roomData) {
		List<MDRoomSettingData> result = new ArrayList<MDRoomSettingData>();
		MDRoomSettingData mdRoom;
		for (int i = 0; i < roomData.size(); i++) {
			mdRoom = new MDRoomSettingData();
			mdRoom.setId(roomData.get(i).getId());	
			mdRoom.setActiveCode(roomData.get(i).getActive().getCode());	
			mdRoom.setActiveDesc(roomData.get(i).getActive().getDesc());	
			mdRoom.setName(roomData.get(i).getName());	
			mdRoom.setColor(roomData.get(i).getColor());	
			mdRoom.setAutotime(roomData.get(i).getAutotime());
			mdRoom.setAttendeesMax(roomData.get(i).getAttendeesMax());
			mdRoom.setPhone(roomData.get(i).getPhone());
			mdRoom.setDetail(roomData.get(i).getDetail());
			mdRoom.setEquipmentListId(roomData.get(i).getEquipmentListId());
			mdRoom.setPriority(roomData.get(i).getPriority());
			result.add(mdRoom);
		}
		return result;
	}
	public static RoomBookingData setMDRoomEventToRoomBookingData(MDRoomBookingData mdRoomEvent) {
		RoomBookingData result = new RoomBookingData();
		result.setEventIdFocus(mdRoomEvent.getEventIdFocus());
		result.setEventIdSelected(mdRoomEvent.getEventIdSelected());
		result.setRoomIdSelected(mdRoomEvent.getRoomIdSelected());
		
		result.setStartEventDate(mdRoomEvent.getStartEventDate());
		result.setEndEventDate(mdRoomEvent.getEndEventDate());
		
		result.setCurrentDay(mdRoomEvent.getCurrentDay());
		result.setCurrentMode(mdRoomEvent.getCurrentMode()); 
		result.setCurrentStep(mdRoomEvent.getCurrentStep()); 
		
		result.setDataOwner(mdRoomEvent.isDataOwner());
		result.setDataOverLimitCheckOut(mdRoomEvent.isDataOverLimitCheckOut());
		result.setTimeOverLimitCheckOut(mdRoomEvent.getTimeOverLimitCheckOut());
		
		result.setRoomSettingData(setMDRoomMeetingToRoomSettingData(mdRoomEvent.getRoomSettingData()));
		result.setRoomBooking(setMDRoomBookingToRoomBooking(mdRoomEvent.getRoomBooking()));
		return result;
	}
	
	public static List<MDRoomBookingData> setListRoomBookingDataToListMDRoomEvent(List<RoomBookingData> roomEvent) {
		List<MDRoomBookingData> lstResutl = new ArrayList<MDRoomBookingData>();
		MDRoomBookingData result;
		for (int i = 0; i < roomEvent.size(); i++) {
			result = new MDRoomBookingData();
			result.setEventIdFocus(roomEvent.get(i).getEventIdFocus());
			result.setEventIdSelected(roomEvent.get(i).getEventIdSelected());
			result.setRoomIdSelected(roomEvent.get(i).getRoomIdSelected());
			
			result.setStartEventDate(roomEvent.get(i).getStartEventDate());
			result.setEndEventDate(roomEvent.get(i).getEndEventDate());
			
			result.setCurrentTime(roomEvent.get(i).getCurrentTime());
			result.setCurrentDay(roomEvent.get(i).getCurrentDay());
			result.setCurrentMode(roomEvent.get(i).getCurrentMode()); 
			result.setCurrentStep(roomEvent.get(i).getCurrentStep()); 
			
			result.setDataAdmin(roomEvent.get(i).isDataAdmin());
			result.setDataOwner(roomEvent.get(i).isDataOwner());
			result.setDataOverLimitCheckOut(roomEvent.get(i).isDataOverLimitCheckOut());
			result.setTimeOverLimitCheckOut(roomEvent.get(i).getTimeOverLimitCheckOut());
			
			result.setRoomSettingData(setRoomMeetingToMDRoomSettingData(roomEvent.get(i).getRoomSettingData())); 
			result.setRoomBooking(setRoomBookingToMDRoomBooking(roomEvent.get(i).getRoomBooking()));
			lstResutl.add(result);
		}
		return lstResutl;
	}
	
	public static MDRoomSettingData setRoomMeetingToMDRoomSettingData(RoomSettingData mdRoomEvent) {
		MDRoomSettingData result = new MDRoomSettingData();
		result.setId(mdRoomEvent.getId());		// ชื่อห้องประชุม
		result.setName(mdRoomEvent.getName());		// ชื่อห้องประชุม
		result.setColor(mdRoomEvent.getColor());		// สีห้องประชุม
		result.setAutotime(mdRoomEvent.getAutotime());	// เวลาอนุมัติอัตโนมัติ
		result.setAttendeesMax(mdRoomEvent.getAttendeesMax()); // จำนวนผู้เข้าร่วมประชุม
		result.setPhone(mdRoomEvent.getPhone());	// เบอร์โทรศัพท์ภายใน
		result.setDetail(mdRoomEvent.getDetail());	// รายละเอียด
		result.setEquipmentListId(mdRoomEvent.getEquipmentListId());	// ไอดีรายการอุปกรณ์
		result.setPriority(mdRoomEvent.getPriority());	// ระดับความสำคัญของห้อง ใช้ ORDER
		
		return result;
	}
	public static RoomSettingData setMDRoomMeetingToRoomSettingData(MDRoomSettingData mdRoomEvent) {
		RoomSettingData result = new RoomSettingData();
		result.setId(mdRoomEvent.getId());		// ชื่อห้องประชุม
		result.setName(mdRoomEvent.getName());		// ชื่อห้องประชุม
		result.setColor(mdRoomEvent.getColor());		// สีห้องประชุม
		result.setAutotime(mdRoomEvent.getAutotime());	// เวลาอนุมัติอัตโนมัติ
		result.setAttendeesMax(mdRoomEvent.getAttendeesMax()); // จำนวนผู้เข้าร่วมประชุม
		result.setPhone(mdRoomEvent.getPhone());	// เบอร์โทรศัพท์ภายใน
		result.setDetail(mdRoomEvent.getDetail());	// รายละเอียด
		result.setEquipmentListId(mdRoomEvent.getEquipmentListId());	// ไอดีรายการอุปกรณ์
		result.setPriority(mdRoomEvent.getPriority());	// ระดับความสำคัญของห้อง ใช้ ORDER
		
		return result;
	}
	public static MDRoomBooking setRoomBookingToMDRoomBooking(RoomBooking md) {
		MDRoomBooking result = new MDRoomBooking();
		result.setSubject(md.getSubject()); // หัวข้อ
		result.setStartDate(md.getStartDate()); // เริ่มประชุมวันที่
		result.setStartTime(md.getStartTime()); // เริ่มประชุมเวลา
		result.setEndDate(md.getEndDate()); // จบประชุมวันที่
		result.setEndTime(md.getEndTime()); // จบประชุมเวลา
		
		result.setCreateUserId(md.getCreateUserId()); // ไอดีผู้สร้าง
		result.setCreateUserName(md.getCreateUserName()); // ชื่อผู้สร้าง
		result.setCreateDateTime(md.getCreateDateTime()); // วันที่เวลาที่สร้าง
		result.setUpdateUserId(md.getUpdateUserId()); // ไอดีผู้แก้ไข
		result.setUpdateUserName(md.getUpdateUserName()); // ชื่อผู้แก้ไข
		result.setUpdateDateTime(md.getUpdateDateTime()); // วันที่เวลาที่แก้ไข
		
		result.setCheckInDateTime(md.getCheckInDateTime()); // วันที่เวลาเข้าใช้งานห้อง
		result.setCheckOutDateTime(md.getCheckOutDateTime()); // วันที่เวลาออกจากห้อง
		
		result.setDetail(md.getDetail()); // รายละเอียดการประชุม
		result.setEquipmentListId(md.getEquipmentListId()); // ไอดีอุปกรณ์ที่ต้องการ
		result.setEquipmentListName(md.getEquipmentListName()); // ชื่ออุปกรณ์ที่ต้องการ
		
		result.setBookingId(md.getBookingId()); // ไอดีการจอง
		
		result.setUserBookId(md.getUserBookId()); // ไอดีผู้จอง
		result.setUserBookName(md.getUserBookName()); // ชื่อผู้จอง
		
		result.setUserBookEmail(md.getUserBookEmail()); // อีเมล์ผุ้จอง
		result.setUserBookPhone(md.getUserBookPhone()); // เบอร์โทรผู้จอง
		result.setUserBookLineId(md.getUserBookLineId()); // line id ผู้จอง
		result.setUserBookDepartmentId(md.getUserBookDepartmentId()); // แผนก สำหรับออกรายงานสถิติ
		result.setUserBookPositionId(md.getUserBookPositionId()); // ตำแหน่ง
		
		result.setNotificationEmail(md.getNotificationEmail()); // แจ้ง Notification โดยช่องทางไหน
		result.setNotificationLine(md.getNotificationLine()); // แจ้ง Notification โดยช่องทางไหน
		result.setAttendees(md.getAttendees()); // จำนวนผู้เข้าร่วมประชุม
		
		result.setRoomId(md.getRoomId()); // ไอดีห้อง
		result.setRoomName(md.getRoomName()); // ชื่อห้อง
		
		result.setBookingStatusCode(md.getBookingStatusCode()); // สถานะการใช้งานห้อง
		result.setBookingStatusName(md.getBookingStatusName()); // สถานะการใช้งานห้อง
		
		result.setComment(md.getComment());
		result.setDeleted(md.getDeleted());
		
		return result;
	}
	public static RoomBooking setMDRoomBookingToRoomBooking(MDRoomBooking md) {
		RoomBooking result = new RoomBooking();
		result.setSubject(md.getSubject()); // หัวข้อ
		result.setStartDate(md.getStartDate()); // เริ่มประชุมวันที่
		result.setStartTime(md.getStartTime()); // เริ่มประชุมเวลา
		result.setEndDate(md.getEndDate()); // จบประชุมวันที่
		result.setEndTime(md.getEndTime()); // จบประชุมเวลา

		result.setCreateUserId(md.getCreateUserId()); // ไอดีผู้สร้าง
		result.setCreateUserName(md.getCreateUserName()); // ชื่อผู้สร้าง
		result.setCreateDateTime(md.getCreateDateTime()); // วันที่เวลาที่สร้าง
		result.setUpdateUserId(md.getUpdateUserId()); // ไอดีผู้แก้ไข
		result.setUpdateUserName(md.getUpdateUserName()); // ชื่อผู้แก้ไข
		result.setUpdateDateTime(md.getUpdateDateTime()); // วันที่เวลาที่แก้ไข

		result.setCheckInDateTime(md.getCheckInDateTime()); // วันที่เวลาเข้าใช้งานห้อง
		result.setCheckOutDateTime(md.getCheckOutDateTime()); // วันที่เวลาออกจากห้อง

		result.setDetail(md.getDetail()); // รายละเอียดการประชุม
		result.setEquipmentListId(md.getEquipmentListId()); // ไอดีอุปกรณ์ที่ต้องการ
		result.setEquipmentListName(md.getEquipmentListName()); // ชื่ออุปกรณ์ที่ต้องการ

		result.setBookingId(md.getBookingId()); // ไอดีการจอง

		result.setUserBookId(md.getUserBookId()); // ไอดีผู้จอง
		result.setUserBookName(md.getUserBookName()); // ชื่อผู้จอง

		result.setUserBookEmail(md.getUserBookEmail()); // อีเมล์ผุ้จอง
		result.setUserBookPhone(md.getUserBookPhone()); // เบอร์โทรผู้จอง
		result.setUserBookLineId(md.getUserBookLineId()); // line id ผู้จอง
		result.setUserBookDepartmentId(md.getUserBookDepartmentId()); // แผนก สำหรับออกรายงานสถิติ
		result.setUserBookPositionId(md.getUserBookPositionId()); // ตำแหน่ง
		
		result.setNotificationEmail(md.getNotificationEmail()); // แจ้ง Notification โดยช่องทางไหน
		result.setNotificationLine(md.getNotificationLine()); // แจ้ง Notification โดยช่องทางไหน
		result.setAttendees(md.getAttendees()); // จำนวนผู้เข้าร่วมประชุม

		result.setRoomId(md.getRoomId()); // ไอดีห้อง
		result.setRoomName(md.getRoomName()); // ชื่อห้อง

		result.setBookingStatusCode(md.getBookingStatusCode()); // สถานะการใช้งานห้อง
		result.setBookingStatusName(md.getBookingStatusName()); // สถานะการใช้งานห้อง

		result.setComment(md.getComment());
		result.setDeleted(md.getDeleted());
		
		return result;
	}
	
	
	public static List<MDNotificationMessage> setNotiToMDNoti(List<NotificationMessage> notification) {
		List<MDNotificationMessage> result = new ArrayList<MDNotificationMessage>();
		MDNotificationMessage mdNoti;
		for (int i = 0; i < notification.size(); i++) {
			mdNoti = new MDNotificationMessage();
			mdNoti.setNotificationId(notification.get(i).getNotificationId());
			mdNoti.setEventId(notification.get(i).getEventId());
			mdNoti.setReceiverUserId(notification.get(i).getReceiverUserId());
			mdNoti.setSubject(notification.get(i).getSubject());
			mdNoti.setDetail(notification.get(i).getDetail());
			mdNoti.setStatusForm(notification.get(i).getStatusForm());
			mdNoti.setStatusTo(notification.get(i).getStatusTo());
			mdNoti.setStatusRead(notification.get(i).getStatusRead());
			mdNoti.setUserId(notification.get(i).getUserId());
			mdNoti.setUserName(notification.get(i).getUserName());
			mdNoti.setCreateDateTime(notification.get(i).getCreateDateTime());
			
			mdNoti.setRoomName(notification.get(i).getRoomName());
			mdNoti.setEventSubject(notification.get(i).getEventSubject());
			mdNoti.setEventStartDate(notification.get(i).getEventStartDate());
			mdNoti.setEventStartTime(notification.get(i).getEventStartTime());
			mdNoti.setEventEndTime(notification.get(i).getEventEndTime());
			result.add(mdNoti);
		}
		return result;
	}
	public static InhouseUser setMdUserToInhouseUser(MDUser mobileUser) {
		InhouseUser result = new InhouseUser();
		result.setId(mobileUser.getUserId());
		result.setCardId(mobileUser.getCardId());
		result.setColorCode(mobileUser.getColorCode());
		result.setDepartmentId(mobileUser.getDepartmentId());
		result.setDepartmentName(mobileUser.getDepartmentName());
		result.setDisplayName(mobileUser.getDisplayName());
		result.setEmail(mobileUser.getEmail());
		result.setFullName(mobileUser.getFullName());
		result.setGmail(mobileUser.getGmail());
		result.setInsidePhoneNumber(mobileUser.getInsidePhoneNumber());
		result.setLineUserId(mobileUser.getLineUserId());
		result.setName(mobileUser.getName());
		result.setNickName(mobileUser.getNickName());
		result.setPositionId(mobileUser.getPositionId());
		result.setPositionName(mobileUser.getPositionName());
		result.setSurName(mobileUser.getSurName());
		result.setTableNo(mobileUser.getTableNo());
		result.setTitleName(mobileUser.getTitleName());
		result.setUpdateDate(mobileUser.getUpdateDate());
		result.setLocale(new Locale("EN"));
		return result;
	}
	public static void setsInhouseUserToMdUser(MDUser mobileUser, InhouseUser userInfo) {
		mobileUser.setCardId(userInfo.getCardId());
		mobileUser.setColorCode(userInfo.getColorCode());
		mobileUser.setDepartmentId(userInfo.getDepartmentId());
		mobileUser.setDepartmentName(userInfo.getDepartmentName());
		mobileUser.setDisplayName(userInfo.getDisplayName());
		mobileUser.setEmail(userInfo.getEmail());
		mobileUser.setFullName(userInfo.getFullName());
		mobileUser.setGmail(userInfo.getGmail());
		mobileUser.setInsidePhoneNumber(userInfo.getInsidePhoneNumber());
		mobileUser.setLineUserId(userInfo.getLineUserId());
		mobileUser.setName(userInfo.getName());
		mobileUser.setNickName(userInfo.getNickName());
		mobileUser.setPositionId(userInfo.getPositionId());
		mobileUser.setPositionName(userInfo.getPositionName());
		mobileUser.setSurName(userInfo.getSurName());
		mobileUser.setTableNo(userInfo.getTableNo());
		mobileUser.setTitleName(userInfo.getTitleName());
		mobileUser.setUpdateDate(userInfo.getUpdateDate());
		
	}
	/**
	 * ตัดข้อมูล error
	 * @param e
	 * @return
	 */
	public static String getErrorMessage(Exception e) {
		String error = e.getMessage() + "<br/>";
		for (int i = 0; i < LIMIT_LINE_ERROR; i++) {
			if (e.getStackTrace().length <= i) {
				break;
			} else {
				error += e.getStackTrace()[i] + "<br/>";
			}
		}
		return error;
	}
	
	public String getDbLookup() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}

}
