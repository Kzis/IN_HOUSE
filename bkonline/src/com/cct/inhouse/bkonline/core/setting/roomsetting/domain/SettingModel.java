package com.cct.inhouse.bkonline.core.setting.roomsetting.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.inhouse.common.InhouseModel;

public class SettingModel extends InhouseModel {

	private static final long serialVersionUID = -1298126139309310825L;

	private RoomSettingData roomSetting = new RoomSettingData();
	
	// List all room in table
	private List<RoomSettingData> listAllRoom = new ArrayList<RoomSettingData>();

	// dropdownlist s:select
	private List<CommonSelectItem> listColor = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listAutotime = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listEquipment = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listRoomPriority = new ArrayList<CommonSelectItem>();

	public List<RoomSettingData> getListAllRoom() {
		return listAllRoom;
	}

	public void setListAllRoom(List<RoomSettingData> listAllRoom) {
		this.listAllRoom = listAllRoom;
	}

	public List<CommonSelectItem> getListColor() {
		return listColor;
	}

	public void setListColor(List<CommonSelectItem> listColor) {
		this.listColor = listColor;
	}

	public List<CommonSelectItem> getListAutotime() {
		return listAutotime;
	}

	public void setListAutotime(List<CommonSelectItem> listAutotime) {
		this.listAutotime = listAutotime;
	}

	public List<CommonSelectItem> getListEquipment() {
		return listEquipment;
	}

	public void setListEquipment(List<CommonSelectItem> listEquipment) {
		this.listEquipment = listEquipment;
	}

	public RoomSettingData getRoomSetting() {
		return roomSetting;
	}

	public void setRoomSetting(RoomSettingData roomSetting) {
		this.roomSetting = roomSetting;
	}

	public List<CommonSelectItem> getListRoomPriority() {
		return listRoomPriority;
	}

	public void setListRoomPriority(List<CommonSelectItem> listRoomPriority) {
		this.listRoomPriority = listRoomPriority;
	}

}
