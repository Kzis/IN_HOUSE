package com.cct.inhouse.bkonline.util.notificationmessage;

import com.cct.inhouse.bkonline.core.notification.domain.NotificationMessage;

public final class NotificationMessageUtil {

	public static String createMessageWait(NotificationMessage message) {
		StringBuilder messageBuild = new StringBuilder();
		try {
			messageBuild.append("📅	แจ้งขอใช้งาน " + message.getRoomName() + "   ผู้แจ้ง: " + message.getUserName() + "\n");
			messageBuild.append("หัวข้อ: " + message.getSubject() + "\n");
			messageBuild.append("วันที่: " + message.getEventStartDate() + "   เวลา: " + message.getEventStartTime() + " - " + message.getEventEndTime() + "\n");
			messageBuild.append("วันที่แจ้ง: " + message.getCreateDateTime() + "\n");
		} catch (Exception e) {
			throw e;
		}
		return messageBuild.toString();
	}

	public static String createMessageApprove(NotificationMessage message) {
		StringBuilder messageBuild = new StringBuilder();
		try {
			messageBuild.append("✅	แจ้งอนุมัติใช้งาน " + message.getRoomName() + "   ผู้แจ้ง: " + message.getUserName() + "\n");
			messageBuild.append("หัวข้อ: " + message.getSubject() + "\n");
			messageBuild.append("วันที่: " + message.getEventStartDate() + "   เวลา: " + message.getEventStartTime() + " - " + message.getEventEndTime() + "\n");
			messageBuild.append("วันที่แจ้ง: " + message.getCreateDateTime() + "\n");
		} catch (Exception e) {
			throw e;
		}
		return messageBuild.toString();
	}
	
	public static String createMessageCheckIn(NotificationMessage message) {
		StringBuilder messageBuild = new StringBuilder();
		try {
			messageBuild.append("📥	แจ้งเข้าใช้งาน " + message.getRoomName() + "   ผู้แจ้ง: " + message.getUserName() + "\n");
			messageBuild.append("หัวข้อ: " + message.getSubject() + "\n");
			messageBuild.append("วันที่: " + message.getEventStartDate() + "   เวลา: " + message.getEventStartTime() + " - " + message.getEventEndTime() + "\n");
			messageBuild.append("วันที่แจ้ง: " + message.getCreateDateTime() + "\n");
		} catch (Exception e) {
			throw e;
		}
		return messageBuild.toString();
	}

	public static String createMessageCheckOut(NotificationMessage message) {
		StringBuilder messageBuild = new StringBuilder();
		try {
			messageBuild.append("📤	แจ้งคืนห้อง " + message.getRoomName() + "   ผู้แจ้ง: " + message.getUserName() + "\n");
			messageBuild.append("หัวข้อ: " + message.getSubject() + "\n");
			messageBuild.append("วันที่: " + message.getEventStartDate() + "   เวลา: " + message.getEventStartTime() + " - " + message.getEventEndTime() + "\n");
			messageBuild.append("วันที่แจ้ง: " + message.getCreateDateTime() + "\n");
		} catch (Exception e) {
			throw e;
		}
		return messageBuild.toString();
	}
	
	public static String createMessageCancel(NotificationMessage message) {
		StringBuilder messageBuild = new StringBuilder();
		try {
			messageBuild.append("❎	แจ้งยกเลิก " + message.getRoomName() + "   ผู้แจ้ง: " + message.getUserName() + "\n");
			messageBuild.append("หัวข้อ: " + message.getSubject() + "\n");
			messageBuild.append("วันที่: " + message.getEventStartDate() + "   เวลา: " + message.getEventStartTime() + " - " + message.getEventEndTime() + "\n");
			messageBuild.append("วันที่แจ้ง: " + message.getCreateDateTime() + "\n");
		} catch (Exception e) {
			throw e;
		}
		return messageBuild.toString();
	}
	
	public static String createMessageNotCheckIn(NotificationMessage message) {
		StringBuilder messageBuild = new StringBuilder();
		try {
			messageBuild.append("🕗	แจ้งไม่มีการใช้งาน " + message.getRoomName() + "   ผู้แจ้ง: " + message.getUserName() + "\n");
			messageBuild.append("หัวข้อ: " + message.getSubject() + "\n");
			messageBuild.append("วันที่: " + message.getEventStartDate() + "   เวลา: " + message.getEventStartTime() + " - " + message.getEventEndTime() + "\n");
			messageBuild.append("วันที่แจ้ง: " + message.getCreateDateTime() + "\n");
		} catch (Exception e) {
			throw e;
		}
		return messageBuild.toString();
	}
	
	public static String createMessageNotCheckOut(NotificationMessage message) {
		StringBuilder messageBuild = new StringBuilder();
		try {
			messageBuild.append("🕓	แจ้งไม่มีการคืนห้อง " + message.getRoomName() + "   ผู้แจ้ง: " + message.getUserName() + "\n");
			messageBuild.append("หัวข้อ: " + message.getSubject() + "\n");
			messageBuild.append("วันที่: " + message.getEventStartDate() + "   เวลา: " + message.getEventStartTime() + " - " + message.getEventEndTime() + "\n");
			messageBuild.append("วันที่แจ้ง: " + message.getCreateDateTime() + "\n");
		} catch (Exception e) {
			throw e;
		}
		return messageBuild.toString();
	}
}
