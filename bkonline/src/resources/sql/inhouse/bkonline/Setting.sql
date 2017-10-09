/**
* ค้นหาห้องทั้งหมด
**/
searchAllRoom {
	SELECT ROOM_ID, ROOM_NAME, ROOM_COLOR, AUTOTIME_ID, ROOM_ATTENDEES_MAX, ROOM_PHONE, ROOM_DETAIL, EQUIPMENT_LIST_ID, PRIORITY
	FROM [SCHEMAS].bk_room
	WHERE DELETED = 'N'
	AND ROOM_ID = %s
	ORDER BY PRIORITY ASC
}

/**
* ค้นหาสถานะห้องล่าสุดจากการจอง ณเวลาปัจจุบัน
**/
searchLastedStatusInTime {
	SELECT ROOM_ID, BOOKING_STATUS_CODE, BOOKING_STATUS_NAME
	FROM [SCHEMAS].bk_event_draw
	WHERE DELETED = 'N'
	AND EVENT_START_DATE = DATE_FORMAT(sysdate(), '%Y-%m-%d')
	AND EVENT_END_DATE = DATE_FORMAT(sysdate(), '%Y-%m-%d')
	AND EVENT_START_TIME <= DATE_FORMAT(sysdate(), '%H:%i')
	AND EVENT_END_TIME >= DATE_FORMAT(sysdate(), '%H:%i')
	ORDER BY EVENT_ID DESC
}

/**
* ค้นหาสถานะอยู่ในช่วงปิดห้องประชุมหรือใหม่
**/
searchRoomInTimeClosed {
	SELECT ROOM_ID, 'Closed' AS STATUS
	FROM [SCHEMAS].bk_room_closed
	WHERE DELETED = 'N'
	AND CLOSED_START_DATE = DATE_FORMAT(sysdate(), '%Y-%m-%d')
	AND CLOSED_END_DATE = DATE_FORMAT(sysdate(), '%Y-%m-%d')
	AND CLOSED_START_TIME <= DATE_FORMAT(sysdate(), '%H:%i')
	AND CLOSED_END_TIME >= DATE_FORMAT(sysdate(), '%H:%i')
	ORDER BY CLOSED_ID DESC
}


/**
* ค้นหาข้อมูลเวลาการปิดห้อง
**/
searchTimeClosedByRoomId {
	SELECT CLOSED_ID, ROOM_ID
		, DATE_FORMAT(CLOSED_START_DATE, '%d/%m/%Y') AS CLOSED_START_DATE_STR, CLOSED_START_TIME
		, DATE_FORMAT(CLOSED_END_DATE, '%d/%m/%Y') AS CLOSED_END_DATE_STR, CLOSED_END_TIME
	FROM [SCHEMAS].bk_room_closed
	WHERE DELETED = 'N'
	AND ROOM_ID = %s
	ORDER BY CLOSED_START_DATE DESC, CLOSED_START_TIME DESC
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : checkDup ห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
checkDupRoomData {
    SELECT COUNT(ROOM_ID) AS TOT
	FROM [SCHEMAS].bk_room
	WHERE DELETED = 'N'
	AND ROOM_NAME = %s
	AND ROOM_ID <> %s
	
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : แก้ไข ห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
updateRoomSetting {
	UPDATE [SCHEMAS].bk_room
	SET
	  ROOM_NAME = %s
	  ,ROOM_COLOR = %s
	  ,AUTOTIME_ID = %s
	  ,ROOM_ATTENDEES_MAX = %s
	  ,ROOM_PHONE = %s
	  ,ROOM_DETAIL = %s
	  ,EQUIPMENT_LIST_ID = %s
	  ,PRIORITY = %s
	  ,UPDATE_USER_ID = %s
  	  ,UPDATE_DATE_TIME = SYSDATE()
	WHERE ROOM_ID = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : เพิ่มข้อมูลในตารางเวลาปิดห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
insertRoomClosedTime {
	INSERT INTO [SCHEMAS].bk_room_closed(
	  ROOM_ID
	  ,CLOSED_START_DATE
	  ,CLOSED_START_TIME
	  ,CLOSED_END_DATE
	  ,CLOSED_END_TIME
	  ,CREATE_USER_ID
	  ,CREATE_DATE_TIME
	  ,UPDATE_USER_ID
	  ,UPDATE_DATE_TIME
	) VALUES (
	  %s
	  ,%s
	  ,%s
	  ,%s
	  ,%s
	  ,%s
	  ,SYSDATE()
	  ,%s
	  ,SYSDATE()
	)
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ลบข้อมูลในตารางเวลาปิดห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
updateDeleteRoomClosedTime {
	UPDATE [SCHEMAS].bk_room_closed
	SET
	  UPDATE_USER_ID = %s
	  ,UPDATE_DATE_TIME = SYSDATE()
	  ,DELETED = 'Y'
	WHERE CLOSED_ID = %s
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : เพิ่ม ห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
insertRoomSetting {
	INSERT INTO [SCHEMAS].bk_room(
	  ROOM_NAME
	  ,ROOM_COLOR
	  ,AUTOTIME_ID
	  ,ROOM_ATTENDEES_MAX
	  ,ROOM_PHONE
	  ,ROOM_DETAIL
	  ,EQUIPMENT_LIST_ID
	  ,PRIORITY
	  ,CREATE_USER_ID
	  ,CREATE_DATE_TIME
	  ,UPDATE_USER_ID
	  ,UPDATE_DATE_TIME
	) VALUES (
	  %s
	  ,%s
	  ,%s
	  ,%s
	  ,%s
	  ,%s
	  ,%s
	  ,%s
	  ,%s
	  ,SYSDATE()
	  ,%s
	  ,SYSDATE()
	)
}
























/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ตรวจสอบการลงทะเบียนว่าเวลาที่ทำการจองทับกัการจองอื่นหรือเปล่า
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchRoomClosed{
	SELECT CLOSED_ID, ROOM_ID, CLOSED_START, CLOSED_END, DELETED 
	FROM [SCHEMAS].bk_room_closed
	WHERE [SCHEMAS].bk_room_closed.ROOM_ID = %s
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Combo ห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchRoomData {
  	SELECT *, NOW()
	FROM [SCHEMAS].bk_room,[SCHEMAS].con_global_data
	WHERE bk_room.ROOM_COLOR = con_global_data.GLOBAL_DATA_CODE
	AND bk_room.DELETED = 'N'
	order by bk_room.ROOM_ID 
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ลบห้องประชุมออกจากหน้าจอ แต่ในระบบสามารถค้นหาได้
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
deleteRoomById {
	UPDATE [SCHEMAS].bk_room
	SET DELETED = 'Y'
	WHERE ROOM_ID = %s
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ลบห้องประชุมออกจากหน้าจอ แต่ในระบบสามารถค้นหาได้
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
deleteRoomClosedById {
	DELETE FROM [SCHEMAS].bk_room_closed
	WHERE ROOM_ID = %s
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ลบห้องประชุมออกจากหน้าจอ แต่ในระบบสามารถค้นหาได้
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
addRoomClosedById {
	INSERT INTO [SCHEMAS].bk_room_closed
		(ROOM_ID,CLOSED_START,CLOSED_END) 
		VALUES(
			%s,
			%s,
			%s)
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : add ห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
addRoomData {
    INSERT INTO [SCHEMAS].bk_room
	(ROOM_NAME, ROOM_COLOR, AUTOTIME_ID, ROOM_ATTENDEES_MAX, ROOM_PHONE, ROOM_DETAIL, ACTIVE) 
	VALUES (%s,
	%s,
	%s,
	%s,
	%s,
	%s,
	%s)
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : add ห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
editRoomData {
	UPDATE [SCHEMAS].bk_room
	SET ROOM_NAME = %s,
	ROOM_COLOR = %s,
	AUTOTIME_ID = %s,
	ROOM_ATTENDEES_MAX = %s,
	ROOM_PHONE = %s,
	ROOM_DETAIL = %s,
	ACTIVE = %s
	WHERE ROOM_ID = %s
}
