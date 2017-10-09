


/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : นับจำนวนข้อมูลเงื่อนไขแต่ละโครงการ_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchCount{
	SELECT count(*) as cnt
	FROM to_project_condition pc
		left join std_project p on pc.PROJECT_ID = p.PROJECT_ID
		left join con_global_data gd on pc.ACTIVE = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 1
	where 1=1
		and pc.PROJECT_ID = %s
		and pc.ACTIVE = %s
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ค้นหาข้อมูลเงื่อนไขแต่ละโครงการ_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchProjectCondition{
	SELECT pc.PROJ_CON_ID
	, pc.PROJECT_ID
	, CONCAT(p.PROJECT_ABBR, '-', p.PROJECT_NAME ) AS PROJECT_ABBR
	, pc.PROJ_CON_DETAIL
	, pc.PROJ_CON_FLAG
	, pc.HOUR_TOT
	, pc.ACTIVE, gd.GLOBAL_DATA_VALUE
	FROM to_project_condition pc
		left join std_project p on pc.PROJECT_ID = p.PROJECT_ID
		left join con_global_data gd on pc.ACTIVE = gd.GLOBAL_DATA_CODE and gd.CON_GLOBAL_TYPE_ID = 1
	where 1=1
		and pc.PROJECT_ID = %s
		and pc.ACTIVE = %s
	order by 
	%s
	LIMIT %s /*ค่าเริ่มต้น*/
	, %s /*จำนวนข้อมูลต่อหน้า*/
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกข้อมูลเงือนไขโครงการ_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
insertProjectCondition{
	INSERT INTO to_project_condition(
		PROJECT_ID
		, PROJ_CON_DETAIL
		, PROJ_CON_FLAG
		, HOUR_TOT
		, ACTIVE
		, CREATE_USER
		, CREATE_DATE)
	VALUES(
		%s
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
searchProjectConditionById{
	SELECT pc.PROJ_CON_ID
			, pc.PROJECT_ID
			, p.PROJECT_ABBR
			, pc.PROJ_CON_DETAIL
			, pc.PROJ_CON_FLAG, pc.HOUR_TOT
			, pc.ACTIVE
	FROM to_project_condition pc
		left join std_project p on pc.PROJECT_ID = p.PROJECT_ID
	where 1=1
		and pc.PROJ_CON_ID = %s
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกแก้ไขข้อมูลเงือนไขโครงการ_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
editProjectCondition{
	UPDATE to_project_condition SET 
		PROJ_CON_DETAIL = %s
		, PROJ_CON_FLAG = %s
		, HOUR_TOT = %s
		, ACTIVE = %s
		, UPDATE_USER = %s
		, UPDATE_DATE = CURRENT_TIMESTAMP
	WHERE PROJ_CON_ID = %s
}
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : บันทึกรายการเปลี่ยนสถานการณ์ใช้งาน_SQL
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
setActiveStatus{
	UPDATE to_project_condition SET 
		ACTIVE=%s /*Y: เปิดใช้งาน, N: ปิดใช้งาน*/
		, UPDATE_USER = %s
		, UPDATE_DATE = CURRENT_TIMESTAMP
	WHERE PROJ_CON_ID IN (%s)
}

/*--------------------------------------------------------------------------------------------------------------------------------------------------
SQL : ตรวจสอบข้อมูลซ้ำ_SQL
Description : 
--------------------------------------------------------------------------------------------------------------------------------------------------*/
checkDup{
	SELECT count(*) as TOT
	FROM to_project_condition
	where 1=1
		and PROJECT_ID = %s
		and PROJ_CON_DETAIL = %s
		and PROJ_CON_ID <> %s -- กรณีหน้าแก้ไข
}