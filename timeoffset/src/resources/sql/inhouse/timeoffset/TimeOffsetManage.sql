

/*
================================================================================
    SQL File:  PS-TO-Manage Time Offset.sql
    
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

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : นับจำนวนข้อมูลการลงเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCount{
	SELECT COUNT(DISTINCT t.TIMEOFFSET_ID) AS CNT
	FROM to_timeoffset t
		left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID 
		left join std_project p on t.PROJECT_ID = p.PROJECT_ID
		left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID
		left join con_global_data gd on t.PROCESS_STATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 16
	where 1=1
		and t.USER_ID = %s
		and t.PROJECT_ID = %s
		and t.PROCESS_STATUS = %s
		and t.DELETED = 'N'
}



/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลการลงเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchTimeOffset{
	SELECT distinct t.TIMEOFFSET_ID, t.PROJECT_ID, t.PROJ_CON_ID, p.PROJECT_ABBR, pc.PROJ_CON_DETAIL,
	  SUM(td.DAY) AS DAY, SUM(td.HOUR) AS HOUR, SUM(td.MINUTE) AS MINUTE, t.PROCESS_STATUS, gd.GLOBAL_DATA_VALUE 
	FROM to_timeoffset t
		left join to_timeoffset_detail td on td.TIMEOFFSET_ID = t.TIMEOFFSET_ID 
		left join std_project p on t.PROJECT_ID = p.PROJECT_ID
		left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID
		left join con_global_data gd on t.PROCESS_STATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 16
	where 1=1
		and t.USER_ID = %s
		and t.PROJECT_ID = %s
		and t.PROCESS_STATUS = %s
		and t.DELETED = 'N'
	group by t.TIMEOFFSET_ID, t.PROJECT_ID, t.PROJ_CON_ID,
	  p.PROJECT_ABBR, pc.PROJ_CON_DETAIL, t.PROCESS_STATUS, gd.GLOBAL_DATA_VALUE 
	order by
	%s
	LIMIT %s /*ค่าเริ่มต้น*/
	, %s /*จำนวนข้อมูลต่อหน้า*/
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ดึง ID ข้อมูลเวลาชดเชย_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
checkDupMaster{
	SELECT TIMEOFFSET_ID 
	FROM to_timeoffset
	WHERE DELETED = 'N'
	  AND USER_ID = %s
	  AND PROJECT_ID = %s
	  AND PROJ_CON_ID = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกข้อมูลเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
insertMasterTO{
	INSERT INTO to_timeoffset(USER_ID,
	PROJECT_ID,
	PROJ_CON_ID,
	PROCESS_STATUS,
	DELETED,
	CREATE_USER,
	CREATE_DATE) 
	VALUES (
	%s,
	%s,
	%s,
	'W',
	'N',
	%s,
	CURRENT_TIMESTAMP)
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ตรวจสอบการเพิ่มข้อมูลเวลาชดเชย_SQL
Description :
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
checkDup{
	SELECT COUNT(*) as CNT
	FROM to_timeoffset t
		left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID 
	where 1=1
		and t.USER_ID = %s
		and t.PROJECT_ID = %s
		and t.PROJ_CON_ID = %s
		and t.DELETED = 'N'
		and ((td.START_DATETIME <= %s
		AND td.END_DATETIME >= %s) OR
	       (td.START_DATETIME >= %s
	       AND td.END_DATETIME <= %s) OR
	       (td.START_DATETIME <= %s
	       AND td.END_DATETIME >= %s))
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกข้อมูลรายละเอียดเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
insertDetailTO{
	INSERT INTO to_timeoffset_detail(
	TIMEOFFSET_ID
	, START_DATETIME
	, END_DATETIME
	, DAY
	, HOUR
	, MINUTE
	, DETAIL_WORK
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
	, CURRENT_TIMESTAMP)
}

insertDetailTOEdit{
	INSERT INTO to_timeoffset_detail(
	TIMEOFFSET_ID
	, START_DATETIME
	, END_DATETIME
	, DAY
	, HOUR
	, MINUTE
	, DETAIL_WORK
	, REMARK
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
	, CURRENT_TIMESTAMP)
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลเพื่อแสดงหน้าจอ_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchTimeOffsetById{
	SELECT t.TIMEOFFSET_ID, td.TIMEOFFSET_DET_ID
		, t.USER_ID, CONCAT(su1.NAME,' ',su1.SURNAME, ' (', su1.NICKNAME, ')') AS USER_FULLNAME
		, t.PROJECT_ID, p.PROJECT_ABBR
		, t.PROJ_CON_ID, pc.PROJ_CON_DETAIL, pc.PROJ_CON_FLAG, pc.HOUR_TOT
		, td.START_DATETIME, td.END_DATETIME, td.DAY, td.HOUR, td.MINUTE
		, td.DETAIL_WORK, td.REMARK
		, td.APPROVE_STATUS, gd.GLOBAL_DATA_VALUE 
		, td.APPROVE_REMARK, td.APPROVE_DATE
		, td.APPROVE_USER, CONCAT(su2.NAME,' ',su2.SURNAME) AS APPROVE_USER_FULLNAME
	FROM to_timeoffset t
		left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID 
		left join std_project p on t.PROJECT_ID = p.PROJECT_ID
		left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID
		left join con_global_data gd on td.APPROVE_STATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 2
		left join sec_user su1 on su1.USER_ID = t.USER_ID and su1.DELETED = 'N'
		left join sec_user su2 on su2.USER_ID = td.APPROVE_USER and su2.DELETED = 'N'
	where td.TIMEOFFSET_ID = %s
	order by td.TIMEOFFSET_DET_ID DESC
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกแก้ไขข้อมูลเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
editMasterTO{
	UPDATE to_timeoffset 
	SET UPDATE_USER = %s
	  , PROCESS_STATUS = %s
	  , UPDATE_DATE = CURRENT_TIMESTAMP
	WHERE TIMEOFFSET_ID = %s
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ตรวจสอบการแก้ไขข้อมูลเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
checkDupEdit{
	SELECT COUNT(*) as CNT
	FROM to_timeoffset t
		left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID 
	where 1=1
		and t.USER_ID = %s
		and td.PROJECT_ID = %s
		and td.PROJ_CON_ID = %s
		and ((td.START_DATETIME <= %s
		AND td.END_DATETIME >= %s) OR
	       (td.START_DATETIME >= %s
	       AND td.END_DATETIME <= %s) OR
	       (td.START_DATETIME <= %s
	       AND td.END_DATETIME >= %s))
		and td.TIMEOFFSET_DET_ID <> %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกแก้ไขข้อมูลรายละเอียดเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
editTimeOffset{
	UPDATE to_timeoffset_detail SET 
		START_DATETIME = %s
		, END_DATETIME = %s
		, DAY = %s
		, HOUR = %s
		, MINUTE = %s
		, DETAIL_WORK = %s
		, REMARK = %s
		, UPDATE_DATE = CURRENT_TIMESTAMP
		, UPDATE_USER = %s
	WHERE TIMEOFFSET_DET_ID = %s
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ลบรายการเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
deleteTODetail{
	DELETE FROM to_timeoffset_detail
	WHERE TIMEOFFSET_DET_ID IN (%s)
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหา Master_NO_Detail_SQL
Description : ใช่สำหรับค้นหา Master ที่ไม่มี Detail เหลืออยู่หลังจาก Delete ไปแล้ว
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchMasterIsNull{
	SELECT t.TIMEOFFSET_ID
	FROM to_timeoffset t 
	LEFT JOIN to_timeoffset_detail td ON t.TIMEOFFSET_ID = td.TIMEOFFSET_ID
	WHERE 1=1
	AND td.TIMEOFFSET_DET_ID IS NULL
	ORDER BY TIMEOFFSET_ID
}

searchMasterIsNull2{
	SELECT COUNT(*) as CNT
	from to_timeoffset t 
	left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID
	where 1=1
	and td.TIMEOFFSET_DET_ID is null
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ลบ Master รายการเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
deleteTOMaster{
	UPDATE to_timeoffset 
	SET DELETED = 'Y'
	  , UPDATE_USER = %s
	  , UPDATE_DATE = CURRENT_TIMESTAMP
	WHERE TIMEOFFSET_ID IN (%s)
}
/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ลบรายละเอียดการทำงาน_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
deleteTODetailInEdit{
	DELETE FROM to_timeoffset_detail 
	WHERE APPROVE_STATUS = 'W'
	AND TIMEOFFSET_ID = %s
}

deleteTODetailInEdit2{
	DELETE FROM to_timeoffset_detail 
	WHERE APPROVE_STATUS = 'W'
	AND TIMEOFFSET_DET_ID IN (%s)
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ตรวจสอบรายการรออนุมัติ_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCountTOT{
	SELECT count(*) AS TOT 
	FROM to_timeoffset_detail 
	WHERE APPROVE_STATUS = 'W' 
	AND TIMEOFFSET_ID = %s
}


/*-----------------------------
SQL : ค้นหาข้อมูลเงื่อนไขแต่ละโครงการ_SQL
Description : 
-------------------------------*/
searchProjectConditionSelectItem{
	SELECT pc.PROJ_CON_ID, pc.PROJECT_ID, p.PROJECT_ABBR,
		pc.PROJ_CON_DETAIL, pc.PROJ_CON_FLAG, pc.HOUR_TOT, 
		pc.ACTIVE, gd.GLOBAL_DATA_VALUE
	FROM to_project_condition pc
		left join std_project p on pc.PROJECT_ID = p.PROJECT_ID
		left join con_global_data gd on pc.ACTIVE = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 1
	where 1=1
		and pc.PROJECT_ID = %s
		and pc.ACTIVE = 'Y'
	order by pc.PROJECT_ID
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : หาเวลาชดเชยที่รออนุมัติจาก HRM_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchTO{
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
