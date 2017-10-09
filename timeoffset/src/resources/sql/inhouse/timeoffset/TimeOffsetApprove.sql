/*
================================================================================
    SQL File:  PS-TO-Approve.sql
    
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
SQL : นับจำนวนข้อมูลเวลาชดเชย
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCount{
	SELECT COUNT(DISTINCT t.TIMEOFFSET_ID) AS CNT
	FROM to_timeoffset t
		left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID 
		left join sec_user su on su.USER_ID = t.USER_ID and su.DELETED = 'N'
		left join std_department sd on sd.DEPARTMENT_ID = su.DEPARTMENT_ID
		left join std_project p on t.PROJECT_ID = p.PROJECT_ID
		left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID
		left join con_global_data gd on t.PROCESS_STATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 16
	where 1=1
		and sd.DEPARTMENT_ID = %s
		and t.USER_ID = %s
		and t.DELETED = 'N'
		and t.PROJECT_ID = %s
		and pc.PROJ_CON_ID = %s
		and td.START_DATETIME >= STR_TO_DATE(%s,'%Y-%m-%d %H:%i')
		and td.END_DATETIME <= STR_TO_DATE(%s, '%Y-%m-%d %H:%i')
		and t.PROCESS_STATUS = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลเวลาชดเชย
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchApprove{
	SELECT distinct t.TIMEOFFSET_ID, t.PROJECT_ID, t.PROJ_CON_ID, 
	  p.PROJECT_ABBR, pc.PROJ_CON_DETAIL,
	  t.USER_ID, CONCAT('[', sd.DEPARTMENT_CODE, '] ', su.NAME,' ',su.SURNAME, ' (', su.NICKNAME, ')') AS USER_FULLNAME, 
	  SUM(td.DAY) AS DAY, SUM(td.HOUR) AS HOUR, SUM(td.MINUTE) AS MINUTE,
	  CONCAT(su2.NAME,' ',su2.SURNAME) AS APPROVE_USER, 
		t.PROCESS_STATUS, gd.GLOBAL_DATA_VALUE 
	FROM to_timeoffset t
		left join to_timeoffset_detail td on td.TIMEOFFSET_ID = t.TIMEOFFSET_ID 
		left join sec_user su on su.USER_ID = t.USER_ID and su.DELETED = 'N'
		left join std_department sd on sd.DEPARTMENT_ID = su.DEPARTMENT_ID
		left join std_project p on t.PROJECT_ID = p.PROJECT_ID
		left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID
	  left join con_global_data gd on t.PROCESS_STATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 16
	  left join sec_user su2 on su2.USER_ID = td.APPROVE_USER and su2.DELETED = 'N'
	where 1=1
		and sd.DEPARTMENT_ID = %s
		and t.USER_ID = %s
		and t.DELETED = 'N'
		and t.PROJECT_ID = %s
		and pc.PROJ_CON_ID = %s
		and td.START_DATETIME >= STR_TO_DATE(%s,'%Y-%m-%d %H:%i')
		and td.END_DATETIME <= STR_TO_DATE(%s, '%Y-%m-%d %H:%i')
		and t.PROCESS_STATUS = %s
	group by t.TIMEOFFSET_ID, t.PROJECT_ID, t.PROJ_CON_ID, t.USER_ID,
	  p.PROJECT_ABBR, pc.PROJ_CON_DETAIL, CONCAT(su2.NAME,' ',su2.SURNAME), 
	  t.PROCESS_STATUS, gd.GLOBAL_DATA_VALUE 
	order by 
	%s
	LIMIT %s /*ค่าเริ่มต้น*/
	, %s /*จำนวนข้อมูลต่อหน้า*/
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลรายละเอียดเวลาชดเชยเพื่อแสดงหน้าจอ_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchApproveById{
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
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลรายละเอียดเวลาชดเชยในแต่ละโครงการเพื่อแสดงหน้าจอ Dialog_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchApproveDialogById{
	SELECT t.TIMEOFFSET_ID, td.TIMEOFFSET_DET_ID 
		, t.PROJECT_ID, p.PROJECT_ABBR 
		, t.PROJ_CON_ID, pc.PROJ_CON_DETAIL
		, pc.PROJ_CON_FLAG, pc.HOUR_TOT
		, td.DETAIL_WORK
		, td.START_DATETIME, td.END_DATETIME 
		, td.DAY, td.HOUR, td.MINUTE 
		, td.REMARK 
	FROM to_timeoffset t 
		left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID 
		left join std_project p on t.PROJECT_ID = p.PROJECT_ID 
		left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID 
		left join sec_user su1 on su1.USER_ID = t.USER_ID and su1.DELETED = 'N' 
		left join std_department sd on sd.DEPARTMENT_ID = su1.DEPARTMENT_ID 
	WHERE td.TIMEOFFSET_DET_ID = %s
}
/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกแก้ไขข้อมูลเวลาชดเชย_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
updateTOMaster{
	UPDATE to_timeoffset 
	SET UPDATE_USER = %s
		, PROCESS_STATUS = %s
	  	, UPDATE_DATE = CURRENT_TIMESTAMP
	where TIMEOFFSET_ID = %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกแก้ไขข้อมูลรายละเอียดเวลาชดเชย_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
updateTODetail{
	UPDATE to_timeoffset_detail SET 
		START_DATETIME = %s
		, END_DATETIME = %s
		, DAY = %s
		, HOUR = %s
		, MINUTE = %s
		, REMARK = %s
		, APPROVE_STATUS = %s
		, APPROVE_REMARK = %s 
		, APPROVE_DATE = CURRENT_TIMESTAMP
		, APPROVE_USER = %s
		, UPDATE_DATE = CURRENT_TIMESTAMP
		, UPDATE_USER = %s
	WHERE TIMEOFFSET_DET_ID = %s
}
