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
	SELECT COUNT(*) AS CNT
	FROM (
	  SELECT DISTINCT t.TIMEOFFSET_ID, t.PROJECT_ID, t.PROJ_CON_ID
	  FROM to_timeoffset t
	  	left join to_timeoffset_detail td on t.TIMEOFFSET_ID = td.TIMEOFFSET_ID 
	  	left join std_project p on t.PROJECT_ID = p.PROJECT_ID
	  	left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID
	  	left join con_global_data gd on td.APPROVE_STATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 2
	  WHERE td.APPROVE_STATUS = 'W'
	  and t.DELETED = 'N'
	  ) A
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลเวลาชดเชย
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/

searchTODO{
	SELECT distinct t.TIMEOFFSET_ID, t.PROJECT_ID, t.PROJ_CON_ID, 
	  p.PROJECT_ABBR, pc.PROJ_CON_DETAIL,
	  t.USER_ID, CONCAT('[', sd.DEPARTMENT_CODE, '] ', su.NAME,' ',su.SURNAME, ' (', su.NICKNAME, ')') AS USER_FULLNAME, 
	  SUM(td.DAY) AS DAY, SUM(td.HOUR) AS HOUR, SUM(td.MINUTE) AS MINUTE
	FROM to_timeoffset t
		left join to_timeoffset_detail td on td.TIMEOFFSET_ID = t.TIMEOFFSET_ID 
		left join sec_user su on su.USER_ID = t.USER_ID and su.DELETED = 'N'
		left join std_department sd on sd.DEPARTMENT_ID = su.DEPARTMENT_ID
		left join std_project p on t.PROJECT_ID = p.PROJECT_ID
		left join to_project_condition pc on t.PROJ_CON_ID = pc.PROJ_CON_ID
	WHERE td.APPROVE_STATUS = 'W'
	and t.DELETED = 'N'
	GROUP BY t.TIMEOFFSET_ID, t.PROJECT_ID, t.PROJ_CON_ID, t.USER_ID,p.PROJECT_ABBR, pc.PROJ_CON_DETAIL
	order by 
	%s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกรายการเปลี่ยนสถานะการอนุมัติ_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
setActiveStatus{
	UPDATE to_timeoffset_detail SET 
		APPROVE_STATUS = %s /*A: อนุมัติ, D: ไม่อนุมัติ*/
		, UPDATE_USER = %s
		, UPDATE_DATE = CURRENT_TIMESTAMP
	WHERE TIMEOFFSET_DET_ID IN (%s)
}