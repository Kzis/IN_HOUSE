/*
================================================================================
    SQL File:  PS-TO-Report.sql
    
    Project :  	In House
    System :  Time Offset Management (TO)
================================================================================
    Version : 0.0-0.1            Record Date : 24/02/2017              Prepared / Modified By : นันทพร ภู่ศรีเศวตชาติ
    Change Detail : 
    - จัดทำเอกสาร
	
===============================================================================
Doc. Ref No : SA-Frm-SQL-2.0
===============================================================================
*/

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : นับจำนวนข้อมูลใช้เวลาชดเชย (ผู้จัดการ)_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCountManager{
	SELECT COUNT(*) AS CNT
	FROM sec_user u
		RIGHT JOIN std_department d on u.DEPARTMENT_ID = d.DEPARTMENT_ID
	WHERE u.DELETED = 'N'
		AND u.DEPARTMENT_ID = %s
		AND u.USER_ID = %s
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลใช้เวลาชดเชย (ผู้จัดการ)_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchManager{
	SELECT u.USER_ID, u.DEPARTMENT_ID, 
	  concat(d.DEPARTMENT_NAME, ' (', d.DEPARTMENT_CODE, ')') AS DEPARTMENT,
	  concat(u.NAME, ' ', u.SURNAME, ' (', u.NICKNAME, ')') AS FULLNAME, 
	  (SELECT DATE_FORMAT(MAX(PROCESS_RES_DATETIME),'%Y-%m-%d %T') AS PROCESS_RES_DATETIME 
		FROM to_last_ws_process 
		WHERE PROCESS_STATUS = 'Y' 
			AND PROCESS_NAME = 'GET_WORK_ONSITE_ALL' 
		GROUP BY USER_ID) AS ALL_PROCESS_RES_DATETIME
	FROM sec_user u
	  RIGHT JOIN std_department d on u.DEPARTMENT_ID = d.DEPARTMENT_ID
	WHERE u.DELETED = 'N'
	  AND u.DEPARTMENT_ID = %s
	  AND u.USER_ID = %s
	LIMIT %s /*ค่าเริ่มต้น*/, 
	%s /*จำนวนข้อมูลต่อหน้า*/
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : หาเวลาชดเชยที่รออนุมัติจาก HRM_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchManagerTime{
	select 
		case WHEN (sum(DAY) * 8 * 60) + (sum(HOUR) * 60) + sum(MINUTE) is null THEN 0 
      		ELSE (sum(DAY) * 8 * 60) + (sum(HOUR) * 60) + sum(MINUTE)
    	END - (
    		case WHEN ( 
					(select (sum(TOTAL_ONSITEDAY) * 8 * 60) from to_workonsite_hrm where ONSITESTATUS = 'A' and USERID = %s) +
			    	(select (sum(TOTAL_ONSITETIME) * 60) from to_workonsite_hrm where ONSITESTATUS = 'A' and USERID = %s)
			    ) is null then 0 else (
			    	(select (sum(TOTAL_ONSITEDAY) * 8 * 60) from to_workonsite_hrm where ONSITESTATUS = 'A' and USERID = %s) +
			    	(select (sum(TOTAL_ONSITETIME) * 60) from to_workonsite_hrm where ONSITESTATUS = 'A' and USERID = %s)
			    ) end
	  ) AS TOTAL_TIMEOFFSET_MIN, -- เวลาชดเชยคงเหลือ
	  case when(
		  	select (sum(TOTAL_ONSITEDAY) * 8 * 60) + (sum(TOTAL_ONSITETIME) * 60) AS TOTAL_WAIT_HRM
		  	from to_workonsite_hrm 
		  	where ONSITESTATUS = 'W' 
			 	and USERID = %s
		 	) is null then 0 else (
		 		select (sum(TOTAL_ONSITEDAY) * 8 * 60) + (sum(TOTAL_ONSITETIME) * 60) AS TOTAL_WAIT_HRM
			  	from to_workonsite_hrm 
			  	where ONSITESTATUS = 'W' 
				 	and USERID = %s
		 	) end AS TOTAL_WAIT_A_HRM, -- เวลาชดเชยที่รออนุมัติจาก HRM
	 	(
	  	select DATE_FORMAT(MAX(PROCESS_RES_DATETIME),'%Y-%m-%d %T') AS PROCESS_RES_DATETIME 
	    from to_last_ws_process 
		where PROCESS_STATUS = 'Y' 
			and PROCESS_NAME = 'GET_WORK_ONSITE_BY_USER'
	      	and USER_ID = %s
	      group by USER_ID
	     ) AS PROCESS_RES_DATETIME_BY_USER -- วัน - เวลา Syn ข้อมูล HRM ล่าสุด
	from to_timeoffset_detail 
	where APPROVE_STATUS = 'A' 
	  and TIMEOFFSET_ID IN (
	    select TIMEOFFSET_ID 
	    from to_timeoffset 
	    where DELETED = 'N' 
	      and USER_ID = %s
	  )
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : หารายละเอียดเวลาชดเชยที่รออนุมัติจาก HRM_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchManagerDetail{
	select CONCAT(DATE_FORMAT(hrm.ONSITEDATEFROM1, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMEFROM1, 1, 2), ':', SUBSTRING(hrm.ONSITETIMEFROM1, 3, 2)) AS START_DATETIME, 
	CONCAT(DATE_FORMAT(hrm.ONSITEDATETO2, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMETO2, 1, 2), ':', SUBSTRING(hrm.ONSITETIMETO2, 3, 2)) AS END_DATETIME,
	hrm.TOTAL_ONSITEDAY AS TOTAL_ONSITEDAY,
	hrm.TOTAL_ONSITETIME AS TOTAL_ONSITETIME,
	SITESERVICE_REMARK
	from to_workonsite_hrm hrm
	where ONSITESTATUS = 'W' 
		and USERID = %s
		and hrm.ONSITEDATEFROM1 >= STR_TO_DATE(%s, '%d/%m/%Y %H:%i')
		and hrm.ONSITEDATETO2 <= STR_TO_DATE(%s, '%d/%m/%Y %H:%i')
}

/*-------------------------------------------------------------------------------------------------------------------------------------------------
SQL : หาวันที่ process ล่าสุด
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchProcessReqDateTime{
	SELECT MAX(PROCESS_REQ_DATETIME) AS PROCESS_DATE 
	FROM to_last_ws_process 
	WHERE PROCESS_STATUS = 'Y' 
		AND PROCESS_NAME = 'GET_WORK_ONSITE_BY_USER'
		AND USER_ID = %s
}

searchProcessReqDateTimeAll{
	SELECT MAX(PROCESS_REQ_DATETIME) AS PROCESS_DATE 
	FROM to_last_ws_process 
	WHERE PROCESS_STATUS = 'Y' 
	AND PROCESS_NAME = 'GET_WORK_ONSITE_ALL'
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : นับจำนวนข้อมูลใช้เวลาชดเชย (พนักงาน)_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCountGeneral{
	SELECT COUNT(*) AS TOT
	FROM to_workonsite_hrm hrm
		left join con_global_data gd on hrm.ONSITESTATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 13
	WHERE 1=1
		and hrm.USERID = %s
		and hrm.ONSITEDATEFROM1 >= DATE_FORMAT(%s,'%Y-%m-%d %h:%i')
		and hrm.ONSITEDATETO2 <= DATE_FORMAT(%s,'%Y-%m-%d %h:%i')
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลใช้เวลาชดเชย(พนักงาน)_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchGeneral{
	SELECT CONCAT(DATE_FORMAT(hrm.ONSITEDATEFROM1, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMEFROM1, 1, 2), ':', SUBSTRING(hrm.ONSITETIMEFROM1, 3, 2)) AS START_DATETIME, 
		CONCAT(DATE_FORMAT(hrm.ONSITEDATETO2, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMETO2, 1, 2), ':', SUBSTRING(hrm.ONSITETIMETO2, 3, 2)) AS END_DATETIME,
		hrm.TOTAL_ONSITEDAY, hrm.TOTAL_ONSITETIME, hrm.ONSITESTATUS, gd.GLOBAL_DATA_VALUE, hrm.SITESERVICE_REMARK
	FROM to_workonsite_hrm hrm
		left join con_global_data gd on hrm.ONSITESTATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 13
	WHERE 1=1
		and hrm.USERID = %s
		and hrm.ONSITEDATEFROM1 >= DATE_FORMAT(%s,'%Y-%m-%d %h:%i')
		and hrm.ONSITEDATETO2 <= DATE_FORMAT(%s,'%Y-%m-%d %h:%i')
	ORDER BY hrm.ONSITEDATEFROM1, hrm.ONSITETIMEFROM1
}


/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : insert process log
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
insertLogCallWS{
	INSERT INTO to_last_ws_process(
	  PROCESS_NAME
	  , PROCESS_STATUS
	  , USER_ID
	  , CREATE_USER) 
	VALUES (
	  'GET_WORK_ONSITE_BY_USER'
	  , 'W'
	  , %s
	  , %s
	)
}


/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : update status process log
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
updateLogCallWS{
	UPDATE TO_LAST_WS_PROCESS SET 
		PROCESS_RES_DATETIME = CURRENT_TIMESTAMP
		, PROCESS_STATUS = %s
		, PROCESS_STATUS_DESC = %s
		, TOTAL_RECORD = %s
		, UPDATE_DATE = CURRENT_TIMESTAMP
		, UPDATE_USER = %s 
	WHERE LAST_WS_PROCESS_ID = %s
}


/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : insert ข้อมูลหยุดชอดเชย
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
insertWorkOnSite{
	INSERT INTO TO_WORKONSITE_HRM (
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
		, CURRENT_TIMESTAMP)
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : update ข้อมูลหยุดชอดเชย
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
updateWorkOnSite{
	UPDATE TO_WORKONSITE_HRM SET
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
}


/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : นับจำนวนข้อมูลเวลาชดเชย_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCount{
	SELECT COUNT(*) AS CNT
	FROM to_timeoffset t
		left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID 
		left join sec_user su on su.USER_ID = t.USER_ID and su.DELETED = 'N'
		left join std_department sd on sd.DEPARTMENT_ID = su.DEPARTMENT_ID
		left join std_project p on t.PROJECT_ID = p.PROJECT_ID
		left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID
		left join con_global_data gd on td.APPROVE_STATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 2
	WHERE 1=1
		and t.PROJECT_ID = %s
		and t.PROJ_CON_ID = %s
		and sd.DEPARTMENT_ID = %s
		and t.USER_ID = %s
		and DATE(td.START_DATETIME) >= DATE_FORMAT(%s,'%Y-%m-%d')
		and DATE(td.END_DATETIME) <= DATE_FORMAT(%s,'%Y-%m-%d')
		and td.APPROVE_STATUS = %s
	ORDER BY sd.DEPARTMENT_ID, t.USER_ID, td.START_DATETIME, td.END_DATETIME
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลเวลาชดเชย_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchTOInUse{
	SELECT distinct sd.DEPARTMENT_ID, CONCAT(sd.DEPARTMENT_NAME, ' (', sd.DEPARTMENT_CODE, ')') AS DEPARTMENT,
		p.PROJECT_ABBR,t.USER_ID, CONCAT(su.NAME,' ',su.SURNAME, ' (', su.NICKNAME, ')') AS USER_FULLNAME,
		td.START_DATETIME, td.END_DATETIME, td.APPROVE_STATUS, gd.GLOBAL_DATA_VALUE, 
		(sum(td.DAY) * 60 * 60) + (sum(td.HOUR) * 60) + sum(td.MINUTE) as mintue
	FROM to_timeoffset t
		left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID
		left join sec_user su on su.USER_ID = t.USER_ID and su.DELETED = 'N'
		left join std_department sd on sd.DEPARTMENT_ID = su.DEPARTMENT_ID
		left join std_project p on t.PROJECT_ID = p.PROJECT_ID
		left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID
		left join con_global_data gd on td.APPROVE_STATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 2
	where 1=1
		and t.PROJECT_ID = %s
	  	and t.PROJ_CON_ID = %s
		and sd.DEPARTMENT_ID = %s
		and t.USER_ID = %s
		and DATE(td.START_DATETIME) >= DATE_FORMAT(%s,'%Y-%m-%d')
		and DATE(td.END_DATETIME) <= DATE_FORMAT(%s,'%Y-%m-%d')
		and td.APPROVE_STATUS = %s
	group by sd.DEPARTMENT_ID, p.PROJECT_ABBR,t.USER_ID, td.START_DATETIME, td.END_DATETIME, td.APPROVE_STATUS, gd.GLOBAL_DATA_VALUE
	order by p.PROJECT_ABBR,sd.DEPARTMENT_ID, t.USER_ID, td.START_DATETIME, td.END_DATETIME
}
