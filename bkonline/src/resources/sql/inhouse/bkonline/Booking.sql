insertEventDraw {
	INSERT INTO [SCHEMAS].bk_event_draw (
	  EVENT_SUBJECT
	  ,EVENT_START_DATE
	  ,EVENT_START_TIME
	  ,EVENT_END_DATE
	  ,EVENT_END_TIME
	  
	  ,CREATE_USER_ID
	  ,CREATE_USER_NAME
	  ,CREATE_DATE_TIME
	  ,UPDATE_USER_ID
	  ,UPDATE_USER_NAME
	  ,UPDATE_DATE_TIME
	  
	  ,CHECK_IN_DATE_TIME
	  ,CHECK_OUT_DATE_TIME
	  
	  ,EVENT_DETAIL
	  ,EQUIPMENT_LIST_ID
	  ,EQUIPMENT_LIST_NAME
	  
	  ,BOOKING_ID
	  ,USER_BOOKING_ID
	  ,USER_BOOKING_NAME
	  ,USER_BOOKING_EMAIL
	  ,USER_BOOKING_PHONE
	  ,USER_BOOKING_LINE_ID
	  ,USER_BOOKING_DEPARTMENT_ID
	  ,USER_BOOKING_POSITION_ID
	  ,NOTIFICATION_EMAIL
	  ,NOTIFICATION_LINE
	  
	  ,EVENT_ATTENDEES
	  ,ROOM_ID
	  ,ROOM_NAME
	  ,EVENT_COMMENT
	  
	  ,BOOKING_STATUS_CODE
	  ,BOOKING_STATUS_NAME
	  
	  ,EVENT_START_TIME_ORIGINAL
	  ,EVENT_END_TIME_ORIGINAL
	) VALUES (
	  %s -- EVENT_SUBJECT - IN varchar(500)
	  ,%s -- EVENT_START_DATE - IN datetime
	  ,%s -- EVENT_START_TIME - IN varchar(5)
	  ,%s -- EVENT_END_DATE - IN datetime
	  ,%s -- EVENT_END_TIME - IN varchar(5)
	  
	  ,%s -- CREATE_USER_ID - IN int(11)
	  ,%s -- CREATE_USER_NAME - IN varchar(100)
	  ,SYSDATE() -- CREATE_DATE_TIME - IN timestamp
	  ,%s -- UPDATE_USER_ID - IN int(11)
	  ,%s -- UPDATE_USER_NAME - IN varchar(100)
	  ,SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
	  
	  ,NULL  -- CHECK_IN_DATE_TIME - IN datetime
	  ,NULL  -- CHECK_OUT_DATE_TIME - IN datetime
	  
	  ,%s  -- EVENT_DETAIL - IN varchar(1000)
	  ,%s  -- EQUIPMENT_LIST_ID - IN varchar(100)
	  ,%s  -- EQUIPMENT_LIST_NAME - IN varchar(500)
	  
	  ,%s -- BOOKING_ID - IN int(11)
	  ,%s -- USER_BOOKING_ID - IN int(11)
	  ,%s -- USER_BOOKING_NAME - IN varchar(255)
	  ,%s  -- USER_BOOKING_EMAIL - IN varchar(255)
	  ,%s  -- USER_BOOKING_PHONE - IN varchar(20)
	  ,%s  -- USER_BOOKING_LINE_ID - IN varchar(20)
	  ,%s  -- USER_BOOKING_DEPARTMENT_ID - IN int(11)
	  ,%s  -- USER_BOOKING_POSITION_ID - IN int(11)
	  ,%s -- NOTIFICATION_EMAIL - IN char(1)
	  ,%s -- NOTIFICATION_LINE - IN char(1)
	  
	  ,%s   -- EVENT_ATTENDEES - IN int(11)
	  ,%s -- ROOM_ID - IN int(11)
	  ,%s -- ROOM_NAME - IN varchar(100)
	  ,NULL  -- EVENT_COMMENT - IN varchar(1000)
	  
	  ,%s -- BOOKING_STATUS_CODE - IN varchar(2)
	  ,%s -- BOOKING_STATUS_NAME - IN varchar(100)
	  
	  ,%s
	  ,%s
	)
}

searchRoomFreeTime {
	SELECT ROOM_ID, ROOM_NAME, ROOM_COLOR, AUTOTIME_ID, ROOM_ATTENDEES_MAX, ROOM_PHONE, ROOM_DETAIL, DELETED 
	FROM [SCHEMAS].bk_room
	WHERE DELETED = 'N'
	AND ROOM_ID NOT IN (
		SELECT DISTINCT ROOM_ID
		FROM [SCHEMAS].bk_event_draw
	  	WHERE DELETED = 'N'
	  	AND BOOKING_STATUS_CODE IN ('W', 'A', 'CI', 'CO') /** ไม่นำ Unstable, Cancel, Delete */
	  	AND EVENT_START_DATE = %s
	  	AND EVENT_END_DATE = %s
	  	AND EVENT_END_TIME > %s
	  	AND EVENT_START_TIME < %s
	)
	ORDER BY PRIORITY ASC
}

/**
* ค้นหาสถานะอยู่ในช่วงปิดห้องประชุมหรือใหม่
**/
searchRoomClosedTime {
	SELECT ROOM_ID, 'Closed' AS STATUS
	FROM [SCHEMAS].bk_room_closed
	WHERE DELETED = 'N'
	AND CLOSED_START_DATE = %s
	AND CLOSED_END_DATE = %s
	AND CLOSED_END_TIME > %s
	AND CLOSED_START_TIME < %s
	ORDER BY CLOSED_ID DESC
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : เปลื่ยนสถานะตามเงือนไข 
Description : 
	CheckOut กรณี Admin ให้อัพเดด EVENT_START_DATE = CHECK_OUT_DATE_TIME 
	CheckOut กรณีผู้ใช้ทั่วไป ให้ให้อัพเดด SYSDATE() ที่ EVENT_START_DATE และ CHECK_OUT_DATE_TIME
	CheckOut กรณีไม่เคย CheckIn ให้อัพเดด CHECK_IN_DATE_TIME = EVENT_START_DATE
	CheckIn กรณีเลยเวลาที่กำหนดให้ข้ามไป CheckOut เลย
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
setBookingStatusApprove {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
	WHERE BOOKING_STATUS_CODE IN ('W', 'U')
	AND EVENT_ID = %s
}

setBookingStatusCheckInForGenaral {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
		, CHECK_IN_DATE_TIME = SYSDATE()
	WHERE BOOKING_STATUS_CODE = 'A'
	AND EVENT_ID = %s
}

setBookingStatusCheckInForAdmin {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
		, CHECK_IN_DATE_TIME = CONCAT(DATE_FORMAT(EVENT_START_DATE, '%Y-%m-%d'), ' ', EVENT_START_TIME, ':00')
	WHERE BOOKING_STATUS_CODE = 'A'
	AND EVENT_ID = %s
}

setBookingStatusCheckOutForGenaral {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
		, CHECK_OUT_DATE_TIME = SYSDATE()
		, EVENT_END_DATE = DATE_FORMAT(SYSDATE(), '%Y-%m-%d 00:00:00')
		, EVENT_END_TIME = DATE_FORMAT(SYSDATE(), '%H:%i')
	WHERE BOOKING_STATUS_CODE = 'CI'
	AND EVENT_ID = %s
}

setBookingStatusCheckOutForAdmin {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
		, CHECK_OUT_DATE_TIME = CONCAT(DATE_FORMAT(EVENT_END_DATE, '%Y-%m-%d'), ' ', EVENT_END_TIME, ':00')
	WHERE BOOKING_STATUS_CODE = 'CI'
	AND EVENT_ID = %s
}

setBookingStatusCheckOutForGenaralIfNotCheckIn {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
		, CHECK_IN_DATE_TIME = CONCAT(DATE_FORMAT(EVENT_START_DATE, '%Y-%m-%d'), ' ', EVENT_START_TIME, ':00')
		, CHECK_OUT_DATE_TIME = SYSDATE()
		, EVENT_END_DATE = DATE_FORMAT(SYSDATE(), '%Y-%m-%d 00:00:00')
		, EVENT_END_TIME = DATE_FORMAT(SYSDATE(), '%H:%i')
	WHERE BOOKING_STATUS_CODE = 'A'
	AND EVENT_ID = %s
}

setBookingStatusCheckOutForAdminIfNotCheckIn {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
		, CHECK_IN_DATE_TIME = CONCAT(DATE_FORMAT(EVENT_START_DATE, '%Y-%m-%d'), ' ', EVENT_START_TIME, ':00')
		, CHECK_OUT_DATE_TIME = CONCAT(DATE_FORMAT(EVENT_END_DATE, '%Y-%m-%d'), ' ', EVENT_END_TIME, ':00')
	WHERE BOOKING_STATUS_CODE = 'A'
	AND EVENT_ID = %s
}

setBookingStatusOverLimitCheckOut {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
		, CHECK_OUT_DATE_TIME = CONCAT(DATE_FORMAT(EVENT_START_DATE, '%Y-%m-%d'), ' ', %s, ':00')
		
		, EVENT_END_TIME = %s
	WHERE BOOKING_STATUS_CODE = 'CI'
	AND EVENT_ID = %s
}

setBookingStatusOverLimitCheckOutIfNotCheckIn {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
		, CHECK_IN_DATE_TIME = CONCAT(DATE_FORMAT(EVENT_START_DATE, '%Y-%m-%d'), ' ', EVENT_START_TIME, ':00')
		, CHECK_OUT_DATE_TIME = CONCAT(DATE_FORMAT(EVENT_START_DATE, '%Y-%m-%d'), ' ', %s, ':00')
		
		, EVENT_END_TIME = %s
	WHERE BOOKING_STATUS_CODE = 'A'
	AND EVENT_ID = %s
}


setBookingStatusCancel {
	UPDATE [SCHEMAS].bk_event_draw 
	SET 
		UPDATE_USER_ID = %s
		, UPDATE_USER_NAME = %s
		, UPDATE_DATE_TIME = SYSDATE() -- UPDATE_DATE_TIME - IN timestamp
		
		, BOOKING_STATUS_CODE = %s
		, BOOKING_STATUS_NAME = %s
		
		, CHECK_IN_DATE_TIME = null
		, CHECK_OUT_DATE_TIME = null
	WHERE BOOKING_STATUS_CODE IN ('W', 'U', 'A') 
	AND EVENT_ID = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL: ตรวจสอบสถานะก่อน Approve
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchBookingStatusBeforeApprove {
	SELECT EVENT_ID, BOOKING_STATUS_CODE, BOOKING_STATUS_NAME
	FROM [SCHEMAS].bk_event_draw
	WHERE BOOKING_STATUS_CODE IN ('W', 'U') 
	AND EVENT_ID = %s
	AND ((CREATE_USER_ID = %s) OR (USER_BOOKING_ID = %s))
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL: ตรวจสอบสถานะก่อน CheckIn
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchBookingStatusBeforeCheckIn {
	SELECT EVENT_ID, BOOKING_STATUS_CODE, BOOKING_STATUS_NAME, EVENT_START_DATE
	FROM [SCHEMAS].bk_event_draw
	WHERE BOOKING_STATUS_CODE = 'A'  -- approve แล้วถึง check in ได้
	AND STR_TO_DATE(CONCAT(DATE_FORMAT(EVENT_START_DATE, '%d/%m/%Y'), ' ', EVENT_START_TIME), '%d/%m/%Y %H:%i') - INTERVAL %s MINUTE <= SYSDATE()
	AND STR_TO_DATE(CONCAT(DATE_FORMAT(EVENT_START_DATE, '%d/%m/%Y'), ' ', EVENT_START_TIME), '%d/%m/%Y %H:%i') + INTERVAL %s MINUTE >= SYSDATE()
	AND EVENT_ID = %s
	AND ((CREATE_USER_ID = %s) OR (USER_BOOKING_ID = %s))
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL: ตรวจสอบสถานะก่อน CheckOut กรณีเคย Check In มาแล้ว
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchBookingStatusBeforeCheckOut {
	SELECT EVENT_ID, BOOKING_STATUS_CODE, BOOKING_STATUS_NAME, EVENT_START_DATE
	FROM [SCHEMAS].bk_event_draw
	WHERE BOOKING_STATUS_CODE = 'CI'  -- Check In แล้วถึง check out ได้
	AND EVENT_ID = %s
	AND ((CREATE_USER_ID = %s) OR (USER_BOOKING_ID = %s))
	AND CONCAT(DATE_FORMAT(EVENT_START_DATE, '%Y-%m-%d'), ' ', EVENT_START_TIME, ':00') < DATE_FORMAT(SYSDATE(), '%Y-%m-%d %H:%i:00')
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL: ตรวจสอบสถานะก่อน CheckOut กรณีไม่เคย Check In
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchBookingStatusBeforeCheckOutIfNotCheckIn {
	SELECT EVENT_ID, BOOKING_STATUS_CODE, BOOKING_STATUS_NAME, EVENT_START_DATE
	FROM [SCHEMAS].bk_event_draw
	WHERE BOOKING_STATUS_CODE = 'A'  -- approve แล้วถึง check out ได้
	AND EVENT_ID = %s
	AND ((CREATE_USER_ID = %s) OR (USER_BOOKING_ID = %s))
	AND CONCAT(DATE_FORMAT(EVENT_START_DATE, '%Y-%m-%d'), ' ', EVENT_START_TIME, ':00') < DATE_FORMAT(SYSDATE(), '%Y-%m-%d %H:%i:00')
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL: ตรวจสอบสถานะก่อน Cancel
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchBookingStatusBeforeCancel {
	SELECT EVENT_ID, BOOKING_STATUS_CODE, BOOKING_STATUS_NAME, EVENT_START_DATE
	FROM [SCHEMAS].bk_event_draw
	WHERE BOOKING_STATUS_CODE IN ('W', 'U', 'A')    -- CheckIn และ CheckOut ไม่สามารถยกเลิกได้
	AND EVENT_ID = %s
	AND ((CREATE_USER_ID = %s) OR (USER_BOOKING_ID = %s))
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL: ตรวจสอบการ Check Out ก่อนกำหนด
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCheckOutBeforeLimit {
	SELECT 
	  EVENT_ID, BOOKING_STATUS_CODE, BOOKING_STATUS_NAME
	  , DATE_FORMAT(SYSDATE(), '%Y-%m-%d %H:%i:00') AS CHECK_OUT_DATE_TIME
	  , CONCAT(DATE_FORMAT(EVENT_END_DATE, '%Y-%m-%d'), ' ', EVENT_END_TIME, ':00') AS EVENT_END_DATE_TIME
	FROM [SCHEMAS].bk_event_draw 
	WHERE BOOKING_STATUS_CODE IN ('A', 'CI') 
	AND DATE_FORMAT(SYSDATE(), '%Y-%m-%d %H:%i:00') < CONCAT(DATE_FORMAT(EVENT_END_DATE, '%Y-%m-%d'), ' ', EVENT_END_TIME, ':00')
	AND DATE_FORMAT(SYSDATE(), '%Y-%m-%d %H:%i:00') > CONCAT(DATE_FORMAT(EVENT_START_DATE, '%Y-%m-%d'), ' ', EVENT_START_TIME, ':00')
	AND EVENT_ID = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหา event การใช้งานห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchDataEventDraw {
SELECT 
    e.EVENT_ID, e.EVENT_SUBJECT
    , DATE_FORMAT(e.EVENT_START_DATE, '%d/%m/%Y') AS EVENT_START_DATE , DATE_FORMAT(e.EVENT_END_DATE, '%d/%m/%Y') AS EVENT_END_DATE 
    , e.EVENT_START_TIME, e.EVENT_END_TIME
    , e.CHECK_IN_DATE_TIME, e.CHECK_OUT_DATE_TIME
    , e.EVENT_DETAIL, e.EVENT_ATTENDEES, e.EQUIPMENT_LIST_ID, e.EQUIPMENT_LIST_NAME
    , e.BOOKING_ID, e.USER_BOOKING_ID, e.USER_BOOKING_NAME
    , r.ROOM_ID, r.ROOM_NAME, r.ROOM_COLOR, e.BOOKING_STATUS_CODE, e.BOOKING_STATUS_NAME
    , e.CREATE_USER_ID, e.CREATE_USER_NAME
    , DATE_FORMAT(SYSDATE(), '%d/%m/%Y %H:%i') AS TODAY
    , DATE_FORMAT(SYSDATE(), '%Y%m%d%h%i%S') AS TO_TIME
    , CONCAT(DATE_FORMAT(e.EVENT_END_DATE, '%d/%m/%Y'), ' ', e.EVENT_END_TIME) AS EVENT_END_DATE_TIME
FROM [SCHEMAS].bk_event_draw e, [SCHEMAS].bk_room r
WHERE ((e.DELETED = 'N') AND (r.DELETED = 'N'))
AND e.ROOM_ID = r.ROOM_ID
AND e.BOOKING_STATUS_CODE IN ('W', 'U', 'A', 'CI', 'CO')
AND e.EVENT_START_DATE >= DATE_FORMAT(%s, '%Y-%m-%d')
AND e.EVENT_END_DATE <= DATE_FORMAT(%s, '%Y-%m-%d')
AND e.ROOM_ID IN (%s)
  
ORDER BY PRIORITY ASC

}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหา event การใช้งานห้องประชุม
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchDataEventDrawByBookingId {
	SELECT 
	    e.EVENT_ID, e.EVENT_SUBJECT
	    , DATE_FORMAT(e.EVENT_START_DATE, '%d/%m/%Y') AS EVENT_START_DATE , DATE_FORMAT(e.EVENT_END_DATE, '%d/%m/%Y') AS EVENT_END_DATE 
	    , e.EVENT_START_TIME, e.EVENT_END_TIME
	    , e.CHECK_IN_DATE_TIME, e.CHECK_OUT_DATE_TIME
	    , e.EVENT_DETAIL, e.EVENT_ATTENDEES, e.EQUIPMENT_LIST_ID, e.EQUIPMENT_LIST_NAME
	    , e.BOOKING_ID, e.USER_BOOKING_ID, e.USER_BOOKING_NAME
	    , r.ROOM_ID, r.ROOM_NAME, r.ROOM_COLOR, e.BOOKING_STATUS_CODE, e.BOOKING_STATUS_NAME
	    , e.CREATE_USER_ID
	FROM [SCHEMAS].bk_event_draw e, [SCHEMAS].bk_room r
	WHERE ((e.DELETED = 'N') AND (r.DELETED = 'N'))
	AND e.ROOM_ID = r.ROOM_ID
	AND e.BOOKING_ID = %s
	  
	ORDER BY e.EVENT_ID ASC

}


searchDataEventDrawByEventId {
	SELECT 
	    e.EVENT_ID, e.EVENT_SUBJECT
	    , DATE_FORMAT(e.EVENT_START_DATE, '%d/%m/%Y') AS EVENT_START_DATE , DATE_FORMAT(e.EVENT_END_DATE, '%d/%m/%Y') AS EVENT_END_DATE 
	    , e.EVENT_START_TIME, e.EVENT_END_TIME
	    , e.CHECK_IN_DATE_TIME, e.CHECK_OUT_DATE_TIME
	    , e.EVENT_DETAIL, e.EVENT_ATTENDEES, e.EQUIPMENT_LIST_ID, e.EQUIPMENT_LIST_NAME
	    , e.BOOKING_ID, e.USER_BOOKING_ID, e.USER_BOOKING_NAME
	    , r.ROOM_ID, r.ROOM_NAME, r.ROOM_COLOR, e.BOOKING_STATUS_CODE, e.BOOKING_STATUS_NAME
	    , e.CREATE_USER_ID
	FROM [SCHEMAS].bk_event_draw e, [SCHEMAS].bk_room r
	WHERE ((e.DELETED = 'N') AND (r.DELETED = 'N'))
	AND e.ROOM_ID = r.ROOM_ID
	AND e.EVENT_ID = %s
	  
	ORDER BY e.EVENT_ID ASC

}










searchUserIdForValidate {
	SELECT COUNT(USER_ID) AS TOT 
	FROM [SCHEMAS].sec_user
	WHERE DELETED = 'N'
	AND USER_ID = %s
	AND CONCAT(NAME, ' (', NICKNAME, ')') = %s
}

searchRoomIdForValidate {
	SELECT COUNT(ROOM_ID) AS TOT 
	FROM [SCHEMAS].bk_room
	WHERE DELETED = 'N'
	AND ROOM_ID = %s
	AND ROOM_NAME = %s
}

