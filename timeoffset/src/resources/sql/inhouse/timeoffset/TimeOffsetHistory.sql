================================================================================
    SQL File:  PS-TO-History for Manager.sql
    
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
Description : Count Manager
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCountManager{
	SELECT COUNT(*) AS CNT
	FROM to_workonsite_hrm hrm
		left join sec_user user on hrm.USERID = user.USER_ID and user.DELETED = 'N'
		left join std_department dep on dep.DEPARTMENT_ID = user.DEPARTMENT_ID
		left join con_global_data gd on hrm.ONSITESTATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 13  
	WHERE 1=1
	  and user.USER_ID = %s
	  and user.DEPARTMENT_ID = %s
	  and hrm.ONSITEDATEFROM1 >= %s
	  and hrm.ONSITEDATETO2 <= %s
	  and hrm.ONSITESTATUS = %s
	  and hrm.ONSITESTATUS <> 'C'
}


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลเวลาชดเชย
Description : Search Manager
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchHistoryManager{
	SELECT hrm.ONSITEID, hrm.USERID, hrm.EMPLOYEEID, CONCAT('[', dep.DEPARTMENT_CODE, '] ', hrm.FIRSTNAMETHA, ' ', hrm.LASTNAMETHA, ' (', hrm.NICKNAME, ')') AS FULLNAME,
	  CONCAT(DATE_FORMAT(hrm.ONSITEDATEFROM1, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMEFROM1, 1, 2), ':', SUBSTRING(hrm.ONSITETIMEFROM1, 3, 2)) AS START_DATETIME, 
	  CONCAT(DATE_FORMAT(hrm.ONSITEDATETO2, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMETO2, 1, 2), ':', SUBSTRING(hrm.ONSITETIMETO2, 3, 2)) AS END_DATETIME, 
	  CONCAT(DATE_FORMAT(hrm.ONSITEDATEFROM1, '%Y%m%d'), ' ', SUBSTRING(hrm.ONSITETIMEFROM1, 1, 2), SUBSTRING(hrm.ONSITETIMEFROM1, 3, 2)) AS START_DATETIME_STR, 
	  CONCAT(DATE_FORMAT(hrm.ONSITEDATETO2, '%Y%m%d'), ' ', SUBSTRING(hrm.ONSITETIMETO2, 1, 2), SUBSTRING(hrm.ONSITETIMETO2, 3, 2)) AS END_DATETIME_STR, 
      hrm.TOTAL_ONSITEDAY, hrm.TOTAL_ONSITETIME, hrm.ONSITESTATUS, gd.GLOBAL_DATA_VALUE, 
	  hrm.APPROVETS, hrm.SITESERVICE_REMARK
	FROM to_workonsite_hrm hrm
	  left join sec_user user on hrm.USERID = user.USER_ID and user.DELETED = 'N'
	  left join std_department dep on dep.DEPARTMENT_ID = user.DEPARTMENT_ID
	  left join con_global_data gd on hrm.ONSITESTATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 13
	WHERE 1=1
	  and user.USER_ID = %s
	  and user.DEPARTMENT_ID = %s
	  and hrm.ONSITEDATEFROM1 >= %s
	  and hrm.ONSITEDATETO2 <= %s
	  and hrm.ONSITESTATUS = %s
	  and hrm.ONSITESTATUS <> 'C'
	ORDER BY START_DATETIME_STR DESC, END_DATETIME_STR DESC, CONCAT('[', dep.DEPARTMENT_CODE, '] ', hrm.FIRSTNAMETHA, ' ', hrm.LASTNAMETHA, ' (', hrm.NICKNAME, ')')
	LIMIT %s /*ค่าเริ่มต้น*/
	, %s /*จำนวนข้อมูลต่อหน้า*/
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : นับจำนวนข้อมูลเวลาชดเชย
Description : Count General
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCountGeneral{
	SELECT COUNT(*) AS CNT
	FROM to_workonsite_hrm hrm
		left join sec_user user on hrm.USERID = user.USER_ID and user.DELETED = 'N'
		left join std_department dep on dep.DEPARTMENT_ID = user.DEPARTMENT_ID
		left join con_global_data gd on hrm.ONSITESTATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 13  
	WHERE 1=1
	  and user.USER_ID = %s
	  and hrm.ONSITEDATEFROM1 >= %s
	  and hrm.ONSITEDATETO2 <= %s
	  and hrm.ONSITESTATUS = %s
	  and hrm.ONSITESTATUS <> 'C'
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลเวลาชดเชย
Description : Search General
--------------------------------------------------------------------------------------------------------------------------------------------------*/
searchHistoryGeneral{
	SELECT hrm.ONSITEID, hrm.USERID, hrm.EMPLOYEEID,
	  CONCAT('[', dep.DEPARTMENT_CODE, '] ', hrm.FIRSTNAMETHA, ' ', hrm.LASTNAMETHA, ' (', hrm.NICKNAME, ')') AS FULLNAME,
	  CONCAT(DATE_FORMAT(hrm.ONSITEDATEFROM1, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMEFROM1, 1, 2), ':', SUBSTRING(hrm.ONSITETIMEFROM1, 3, 2)) AS START_DATETIME, 
	  CONCAT(DATE_FORMAT(hrm.ONSITEDATETO2, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMETO2, 1, 2), ':', SUBSTRING(hrm.ONSITETIMETO2, 3, 2)) AS END_DATETIME, 
	  hrm.TOTAL_ONSITEDAY, hrm.TOTAL_ONSITETIME, hrm.ONSITESTATUS, gd.GLOBAL_DATA_VALUE, 
	  hrm.APPROVETS, hrm.SITESERVICE_REMARK
	FROM to_workonsite_hrm hrm
	  right join sec_user user on hrm.USERID = user.USER_ID and user.DELETED = 'N'
	  left join std_department dep on dep.DEPARTMENT_ID = user.DEPARTMENT_ID
	  left join con_global_data gd on hrm.ONSITESTATUS = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 13
	WHERE 1=1
	  and user.USER_ID = %s
	  and hrm.ONSITEDATEFROM1 >= %s
	  and hrm.ONSITEDATETO2 <= %s
	  and hrm.ONSITESTATUS = %s
	  and hrm.ONSITESTATUS <> 'C'
	ORDER BY START_DATETIME,END_DATETIME,CONCAT(DATE_FORMAT(hrm.ONSITEDATEFROM1, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMEFROM1, 1, 2), ':', SUBSTRING(hrm.ONSITETIMEFROM1, 3, 2)),
	  CONCAT(DATE_FORMAT(hrm.ONSITEDATETO2, '%d/%m/%Y'), ' ', SUBSTRING(hrm.ONSITETIMETO2, 1, 2), ':', SUBSTRING(hrm.ONSITETIMETO2, 3, 2))
	LIMIT %s /*ค่าเริ่มต้น*/
	, %s /*จำนวนข้อมูลต่อหน้า*/
}
