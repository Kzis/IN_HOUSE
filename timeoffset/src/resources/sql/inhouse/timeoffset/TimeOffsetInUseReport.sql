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
		and t.PROJ_CON_ID = %s
		and sd.DEPARTMENT_ID = %s
		and t.USER_ID = %s
		and td.START_DATETIME >= DATE_FORMAT(%s,'%Y-%m-%d %h:%i')
		and td.END_DATETIME <= DATE_FORMAT(%s,'%Y-%m-%d %h:%i')
		and td.APPROVE_STATUS = %s
	ORDER BY sd.DEPARTMENT_ID, t.USER_ID, td.START_DATETIME, td.END_DATETIME
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลเวลาชดเชย_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchTOInUse{
	SELECT distinct sd.DEPARTMENT_ID, CONCAT(sd.DEPARTMENT_NAME, ' (', sd.DEPARTMENT_CODE, ')') AS DEPARTMENT,
		t.USER_ID, CONCAT(su.NAME,' ',su.SURNAME, ' (', su.NICKNAME, ')') AS USER_FULLNAME,
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
	  and t.PROJ_CON_ID = %s
		and sd.DEPARTMENT_ID = %s
		and t.USER_ID = %s
		and td.START_DATETIME >= DATE_FORMAT(%s,'%Y-%m-%d %h:%i')
		and td.END_DATETIME <= DATE_FORMAT(%s,'%Y-%m-%d %h:%i')
		and td.APPROVE_STATUS = %s
	group by sd.DEPARTMENT_ID, t.USER_ID, td.START_DATETIME, td.END_DATETIME, td.APPROVE_STATUS, gd.GLOBAL_DATA_VALUE
	order by sd.DEPARTMENT_ID, t.USER_ID, td.START_DATETIME, td.END_DATETIME
}

