package com.cct.inhouse.bkonline.core.mobile.domain;



public class MDRoomSettingData {

	private String id;		// ชื่อห้องประชุม
	private String activeCode;		// ชื่อห้องประชุม
	private String activeDesc;		// ชื่อห้องประชุม
	private String name;		// ชื่อห้องประชุม
	private String color;		// สีห้องประชุม
	private String autotime;	// เวลาอนุมัติอัตโนมัติ
	private String attendeesMax; // จำนวนผู้เข้าร่วมประชุม
	private String phone;	// เบอร์โทรศัพท์ภายใน
	private String detail;	// รายละเอียด
	private String equipmentListId;	// ไอดีรายการอุปกรณ์
	private String priority;	// ระดับความสำคัญของห้อง ใช้ ORDER
	
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	public String getActiveDesc() {
		return activeDesc;
	}
	public void setActiveDesc(String activeDesc) {
		this.activeDesc = activeDesc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getEquipmentListId() {
		return equipmentListId;
	}
	public void setEquipmentListId(String equipmentListId) {
		this.equipmentListId = equipmentListId;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

}
