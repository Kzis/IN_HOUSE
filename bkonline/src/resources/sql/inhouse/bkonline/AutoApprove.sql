
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาห้องที่ให้ทำอนุมัติอัตโนมัติ
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchRoomIdForAutoApprove {
	SELECT ROOM_ID, ROOM_NAME, AUTOTIME_ID
	FROM [SCHEMAS].bk_room
	WHERE DELETED = 'N'
	AND AUTOTIME_ID > 0
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหา Event สำหรับห้องที่กำหนดให้อนุมัติอัตโนมัติ
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchEventIdForAutoApprove {
	SELECT 
	    e.EVENT_ID, e.EVENT_SUBJECT
	FROM [SCHEMAS].bk_event_draw e, [SCHEMAS].bk_room r
	WHERE ((e.DELETED = 'N') AND (r.DELETED = 'N'))
	AND e.ROOM_ID = r.ROOM_ID
	AND e.BOOKING_STATUS_CODE IN ('W')
	AND e.ROOM_ID = %s
	AND e.CREATE_DATE_TIME + INTERVAL %s MINUTE <= SYSDATE()
	ORDER BY EVENT_ID ASC
}

