package com.cct.inhouse.bkonline.core.calendar.dashboard.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.inhouse.bkonline.core.booking.roombooking.domain.RoomBookingData;
import com.cct.inhouse.bkonline.core.setting.roomsetting.domain.RoomSettingData;
import com.cct.inhouse.common.InhouseModel;

public class DashboardModel extends InhouseModel {

	private static final long serialVersionUID = -1298126139309310825L;

	private RoomBookingData dashboardData = new RoomBookingData();
	private List<RoomBookingData> listDashboardData = new ArrayList<RoomBookingData>();
	private List<RoomSettingData> listSelectRoom = new ArrayList<RoomSettingData>();

	public List<RoomSettingData> getListSelectRoom() {
		return listSelectRoom;
	}

	public void setListSelectRoom(List<RoomSettingData> listSelectRoom) {
		this.listSelectRoom = listSelectRoom;
	}

	public RoomBookingData getDashboardData() {
		return dashboardData;
	}

	public void setDashboardData(RoomBookingData dashboardData) {
		this.dashboardData = dashboardData;
	}

	public List<RoomBookingData> getListDashboardData() {
		return listDashboardData;
	}

	public void setListDashboardData(List<RoomBookingData> listDashboardData) {
		this.listDashboardData = listDashboardData;
	}

}
