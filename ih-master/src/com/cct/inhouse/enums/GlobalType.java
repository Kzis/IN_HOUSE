package com.cct.inhouse.enums;

public enum GlobalType {

	ACTIVE_STATUS("ActiveStatus")
	, APPROVE_STATUS("ApproveStatus")
	, REPORT_TYPE("ReportType")
	, TASK_STATUS("TaskStatus")
	, ROOM_STATUS("RoomStatus")
	, EVENT_STATUS("EventStatus")
	, AUTO_TIME("AutoTime")
	, EQUIPMENT_ROOM("EquipmentRoom")
	, COLOR("Color")
	, HRM_APPROVE_STATUS("HrmApproveStatus")
	, NOTIFICATION_USE("NotificationUse")
	, PROCESS_STATUS("ProcessStatus")
	, ROOM_PRIORITY("RoomPriority")
	, TEST_RESULT_STATUS("TestResultStatus")
	, TEST_EXECUTE_STATUS("TestExecuteStatus")
	, TEST_EXECUTE_RESULT_STATUS("TestExecuteResultStatus")
	;

	private String value;

	private GlobalType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
