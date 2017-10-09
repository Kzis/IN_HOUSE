package com.cct.inhouse.bkonline.core.setting.roomsetting.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.inhouse.common.InhouseDomain;

public class RoomSettingData extends InhouseDomain {

	private static final long serialVersionUID = 2435178586649432746L;

	private String name;		// ชื่อห้องประชุม
	private String color;		// สีห้องประชุม
	private String autotime;	// เวลาอนุมัติอัตโนมัติ
	private String attendeesMax; // จำนวนผู้เข้าร่วมประชุม
	private String phone;	// เบอร์โทรศัพท์ภายใน
	private String detail;	// รายละเอียด
	private String equipmentListId;	// ไอดีรายการอุปกรณ์
	private String priority;	// ระดับความสำคัญของห้อง ใช้ ORDER

	private List<RoomClosed> listTimeClosed = new ArrayList<RoomClosed>();
	private List<RoomPicture> listRoomPicture = new ArrayList<RoomPicture>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getAutotime() {
		return autotime;
	}

	public void setAutotime(String autotime) {
		this.autotime = autotime;
	}

	public String getAttendeesMax() {
		return attendeesMax;
	}

	public void setAttendeesMax(String attendeesMax) {
		this.attendeesMax = attendeesMax;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public List<RoomPicture> getListRoomPicture() {
		return listRoomPicture;
	}

	public void setListRoomPicture(List<RoomPicture> listRoomPicture) {
		this.listRoomPicture = listRoomPicture;
	}

	public List<RoomClosed> getListTimeClosed() {
		return listTimeClosed;
	}

	public void setListTimeClosed(List<RoomClosed> listTimeClosed) {
		this.listTimeClosed = listTimeClosed;
	}

	public String getEquipmentListId() {
		return equipmentListId;
	}

	public void setEquipmentListId(String equipmentListId) {
		this.equipmentListId = equipmentListId;
	}

}
