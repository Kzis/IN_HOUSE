/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : หาวันที่ process ล่าสุด
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchLastProcessDate {
	SELECT MAX(PROCESS_REQ_DATETIME) AS PROCESS_DATE 
	FROM to_last_ws_process 
	WHERE PROCESS_STATUS = 'Y'
		AND PROCESS_NAME = 'GET_WORK_ONSITE_ALL'
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : insert process log
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
insertLog {
	INSERT INTO to_last_ws_process(
	  PROCESS_NAME
	  , PROCESS_REQ_DATETIME
	  , PROCESS_STATUS
	  , CREATE_DATE
	  , CREATE_USER) 
	VALUES (
	  'GET_WORK_ONSITE_ALL'
	  , CURRENT_TIMESTAMP
	  , 'W'
	  , CURRENT_TIMESTAMP
	  , %s
	)
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : update status process log
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
updateLog {
	UPDATE to_last_ws_process SET 
	  PROCESS_RES_DATETIME = CURRENT_TIMESTAMP
	  , PROCESS_STATUS = %s
	  , PROCESS_STATUS_DESC = %s
	  , TOTAL_RECORD = %s
	  , UPDATE_DATE = CURRENT_TIMESTAMP
	  , UPDATE_USER = %s 
	WHERE LAST_WS_PROCESS_ID = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : update ข้อมูลหยุดชอดเชย
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
updateWorkOnSite {
	UPDATE to_workonsite_hrm SET
	  USERID = %s
	  , EMPLOYEEID = %s
	  , FIRSTNAMETHA = %s
	  , LASTNAMETHA = %s
	  , NICKNAME = %s
	  , WORKONSITEID = %s
	  , ONSITEDATEFROM1 = %s
	  , ONSITETIMEFROM1 = %s
	  , ONSITEDATETO2 = %s
	  , ONSITETIMETO2 = %s
	  , TOTAL_ONSITEDAY = %s
	  , TOTAL_ONSITETIME = %s
	  , SITESERVICE = %s
	  , ONSITESTATUS = %s
	  , APPROVETS = %s
	  , SITESERVICE_REMARK = %s
	  , ONSITESTATUS_REMARK = %s
	  , APPROVERID = %s 
	  , UPDATE_USER = %s
	  , UPDATE_DATE = CURRENT_TIMESTAMP
	WHERE ONSITEID = %s
		AND USERID = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : insert ข้อมูลหยุดชอดเชย
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
insertWorkOnSite {
	INSERT INTO to_workonsite_hrm (
	  ONSITEID
	  , USERID
	  , EMPLOYEEID
	  , FIRSTNAMETHA
	  , LASTNAMETHA
	  , NICKNAME
	  , WORKONSITEID
	  , ONSITEDATEFROM1
	  , ONSITETIMEFROM1
	  , ONSITEDATETO2
	  , ONSITETIMETO2
	  , TOTAL_ONSITEDAY
	  , TOTAL_ONSITETIME
	  , SITESERVICE
	  , ONSITESTATUS
	  , APPROVETS
	  , SITESERVICE_REMARK
	  , ONSITESTATUS_REMARK
	  , APPROVERID
	  , CREATE_USER
	  , CREATE_DATE) 
	VALUES (
	  %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , %s
	  , CURRENT_TIMESTAMP
	)
}

  