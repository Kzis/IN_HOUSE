/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกการแจ้งเตือน
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
insertNotification {
	INSERT INTO [SCHEMAS].bk_notification (
		EVENT_ID
		, RECEIVER_USER_ID
		, SUBJECT
		, DETAIL
		
		, STATUS_FROM
		, STATUS_TO
		
		, CREATE_USER_ID
		, CREATE_USER_NAME
		, CREATE_DATE_TIME
		, UPDATE_USER_ID
		, UPDATE_USER_NAME
		, UPDATE_DATE_TIME
		
		, ROOM_NAME
		, EVENT_SUBJECT
		, EVENT_START_DATE
		, EVENT_START_TIME
		, EVENT_END_TIME
	) VALUES (
		%s
		, %s
		, %s
		, %s
		
		, %s
		, %s
		
		, %s
		, %s
		, STR_TO_DATE(%s, '%d/%m/%Y %H:%i:%S')
		, %s
		, %s
		, STR_TO_DATE(%s, '%d/%m/%Y %H:%i:%S')
		
		, %s
		, %s
		, %s
		, %s
		, %s
	)
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหา Template Message สำหรับแจ้งเตือน
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchNotificationTemplate {
	SELECT TEMPLATE_ID, SUBJECT, DETAIL, BOOKING_STATUS_CODE, UPDATE_DATE_TIME, ACTIVE 
	FROM [SCHEMAS].bk_notification_template
	WHERE ACTIVE = 'Y'
	AND BOOKING_STATUS_CODE = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาจำนวน Message ที่ยังไม่ได้อ่าน
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCountNotificationStatusTodo {
	SELECT COUNT(NOTIFICATION_ID)
	FROM [SCHEMAS].bk_notification
	WHERE STATUS_READ IN ('N', 'R')
	AND STATUS_TO IN ('W', 'NI', 'NO') 
	AND RECEIVER_USER_ID = %s
}

searchCountNotificationStatusAction {
	SELECT COUNT(NOTIFICATION_ID)
	FROM [SCHEMAS].bk_notification
	WHERE STATUS_READ IN ('N', 'R')
	AND STATUS_TO IN ('A', 'CI', 'CO', 'C') 
	AND RECEIVER_USER_ID = %s
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหา Message ทั้งหมดที่เป็นของเรา
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchNotificationTodo {
	SELECT 
		NOTIFICATION_ID, EVENT_ID, RECEIVER_USER_ID
		, SUBJECT, DETAIL
		, STATUS_FROM, STATUS_TO, STATUS_READ
		, CREATE_USER_ID, CREATE_USER_NAME
		, DATE_FORMAT(CREATE_DATE_TIME, '%d/%m/%Y %H:%i') AS CREATE_DATE_TIME
		, ROOM_NAME, EVENT_SUBJECT
		, DATE_FORMAT(EVENT_START_DATE, '%d/%m/%Y') AS EVENT_START_DATE, EVENT_START_TIME, EVENT_END_TIME
	FROM [SCHEMAS].bk_notification
	WHERE STATUS_READ IN ('N', 'R')
	AND STATUS_TO IN ('W', 'NI', 'NO') 
	AND RECEIVER_USER_ID = %s
	ORDER BY CREATE_DATE_TIME DESC
	LIMIT 100
}

searchNotificationAction {
	SELECT 
		NOTIFICATION_ID, EVENT_ID, RECEIVER_USER_ID
		, SUBJECT, DETAIL
		, STATUS_FROM, STATUS_TO, STATUS_READ
		, CREATE_USER_ID, CREATE_USER_NAME
		, DATE_FORMAT(CREATE_DATE_TIME, '%d/%m/%Y %H:%i') AS CREATE_DATE_TIME
		, ROOM_NAME, EVENT_SUBJECT
		, DATE_FORMAT(EVENT_START_DATE, '%d/%m/%Y') AS EVENT_START_DATE, EVENT_START_TIME, EVENT_END_TIME
	FROM [SCHEMAS].bk_notification
	WHERE STATUS_READ IN ('N', 'R')
	AND STATUS_TO IN ('A', 'CI', 'CO', 'C') 
	AND RECEIVER_USER_ID = %s
	ORDER BY CREATE_DATE_TIME DESC
	LIMIT 100
}


updateDeleteNotification {
	UPDATE [SCHEMAS].bk_notification SET 
	  STATUS_READ = 'D'
	  , UPDATE_USER_ID = %s
	  , UPDATE_USER_NAME = %s
	  , UPDATE_DATE_TIME = SYSDATE() 
	WHERE EVENT_ID = %s 
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Clear bagde และ message กรณีคลิกอ่านแล้ว ของ ยกเลิก และ คืนห้อง 
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
updateDeleteNotificationByNotificationId {
	UPDATE [SCHEMAS].bk_notification SET 
	  STATUS_READ = 'D'
	  , UPDATE_USER_ID = %s
	  , UPDATE_USER_NAME = %s
	  , UPDATE_DATE_TIME = SYSDATE() 
	WHERE NOTIFICATION_ID = %s
}


searchLineIdFromReciverId {
	SELECT USER_ID, LINE_USER_ID
	FROM [SCHEMAS].sec_user
	WHERE USER_ID = %s
	AND DELETED = 'N'

}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหา EventId ที่ไม่ได้ CheckIn 
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchEventIdIfNotCheckIn {
	SELECT EVENT_ID
	FROM [SCHEMAS].bk_event_draw
	WHERE BOOKING_STATUS_CODE = 'A'  -- approve แล้วถึง check in ได้
	AND CHECK_IN_DATE_TIME IS NULL
	AND STR_TO_DATE(CONCAT(DATE_FORMAT(EVENT_START_DATE, '%d/%m/%Y'), ' ', EVENT_START_TIME), '%d/%m/%Y %H:%i') + INTERVAL %s MINUTE <= SYSDATE()
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาการส่ง Notification ที่ไม่ได้ CheckIn 
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCountNotificationNotCheckIn {
	SELECT COUNT(NOTIFICATION_ID) AS TOT
	FROM [SCHEMAS].bk_notification
	WHERE 1 = 1 
	-- AND STATUS_READ IN ('N', 'R')
	AND STATUS_TO IN ('NI')
	AND EVENT_ID = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหา EventId ที่ไม่ได้ CheckOut
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchEventIdIfNotCheckOut {
	SELECT EVENT_ID 
	FROM [SCHEMAS].bk_event_draw 
	WHERE BOOKING_STATUS_CODE IN ('A', 'CI')  -- approve แล้วถึง check in ได้ 
	AND CHECK_OUT_DATE_TIME IS NULL 
	AND STR_TO_DATE(CONCAT(DATE_FORMAT(EVENT_END_DATE, '%d/%m/%Y'), ' ', EVENT_END_TIME), '%d/%m/%Y %H:%i') + INTERVAL %s MINUTE <= SYSDATE()
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาการส่ง Notification ที่ไม่ได้  CheckOut 
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCountNotificationNotCheckOut {
	SELECT COUNT(NOTIFICATION_ID) AS TOT
	FROM [SCHEMAS].bk_notification
	WHERE 1 = 1 
	-- AND STATUS_READ IN ('N', 'R')
	AND STATUS_TO IN ('NO')
	AND EVENT_ID = %s
}

