searchGlobalDataSelectItem {
	SELECT
	  CGD.CON_GLOBAL_DATA_ID, CGD.CON_GLOBAL_TYPE_ID, CGD.GLOBAL_DATA_CODE
	  , CGD.GLOBAL_DATA_VALUE, CGD.GLOBAL_DATA_REMARK
	  , CGD.LIST_NO, CGD.ACTIVE
	  , CGT.GLOBAL_TYPE_NAME, CGT.ACTIVE, CGT.GLOBAL_TYPE_CODE
	FROM [SCHEMAS].con_global_data CGD
	LEFT OUTER JOIN [SCHEMAS].con_global_type CGT ON (CGD.CON_GLOBAL_TYPE_ID = CGT.CON_GLOBAL_TYPE_ID)
	WHERE 1 = 1
	  AND CGT.ACTIVE = 'Y'
	  AND CGD.ACTIVE = 'Y'
	ORDER BY CGT.GLOBAL_TYPE_CODE, CGD.CON_GLOBAL_TYPE_ID, CGD.LIST_NO ASC
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Combo ผู้ใช้งานระบบ [SD-Suraphong Amonphornwitthaya (AE)]
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchUserFullDisplaySelectItem {
    SELECT DISTINCT SU.USER_ID
	    , CONCAT(SD.DEPARTMENT_CODE, '-', SU.NAME, ' ', SU.SURNAME, ' (', SU.NICKNAME , ')') AS FULL_NAME
  	FROM [SCHEMAS].sec_user SU
  	LEFT JOIN [SCHEMAS].std_department SD ON SD.DEPARTMENT_ID = SU.DEPARTMENT_ID
  	WHERE SU.DELETED = 'N'
  	  AND SD.DEPARTMENT_ID IN (%s)
  	  AND CONCAT(SD.DEPARTMENT_CODE, '-', SU.NAME, ' ', SU.SURNAME, ' (', SU.NICKNAME , ')') LIKE CONCAT(%s, '%')
   -- ยังไม่ทราบสาเหตุที่ทำให้ใช้งานไม่ได้  ORDER BY SD.DEPARTMENT_ID, FULL_NAME
    LIMIT %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Combo โครงการ (project)
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchProjectsSelectItem {
	SELECT PROJECT_ID
		, CONCAT(PROJECT_ABBR, '-', PROJECT_NAME ) AS PROJECT_NAME
	FROM [SCHEMAS].std_project
	WHERE 1 = 1
	AND CONCAT(PROJECT_ABBR, '-', PROJECT_NAME ) LIKE CONCAT(%s, '%')
	ORDER BY CREATE_DATE DESC, PROJECT_ABBR
	LIMIT %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Combo ชื่อ-สกุล พนักงาน (filter ตาม ฝ่าย/แผนก)
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchEmployeeFullnameByDepartmentId {
	select distinct su.USER_ID, CONCAT(su.NAME,' ',su.SURNAME, ' (', su.NICKNAME, ')') AS USER_FULLNAME
		, su.DEPARTMENT_ID, CONCAT(sd.DEPARTMENT_NAME, ' (', sd.DEPARTMENT_CODE, ')') AS USER_DEPARTMENT
	from sec_user su
		left join std_department sd on sd.DEPARTMENT_ID = su.DEPARTMENT_ID
	where su.DELETED = 'N'
		and su.DEPARTMENT_ID = %s
		AND CONCAT(su.NAME,' ',su.SURNAME, ' (', su.NICKNAME, ')') LIKE CONCAT(%s, '%')
	order by CONCAT(su.NAME,' ',su.SURNAME, ' (', su.NICKNAME, ')')
	LIMIT %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Combo แผนกตาม Department Id
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchDepartmentSelectItem {
	SELECT *
	FROM [SCHEMAS].std_department
	WHERE 1 = 1 
	AND DEPARTMENT_ID IN (%s)
	AND DEPARTMENT_NAME LIKE CONCAT(%s, '%')
	ORDER BY DEPARTMENT_NAME ASC
	LIMIT %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Combo เงื่อนไขการจัดการเวลาชดเชย
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchTimeoffsetConditionByProjectId {
	SELECT PROJ_CON_ID, PROJECT_ID, PROJ_CON_DETAIL, PROJ_CON_FLAG, HOUR_TOT
	FROM to_project_condition
	WHERE ACTIVE = 'Y'
	  AND PROJECT_ID = %s
}

/**
 * SQL : Combo : ค้นหาผู้ใช้ทั้งหมด โดยแสดงในรูปแบบ ชื่อจริง (ชื่อเล่น)
 */
searchAllUserDisplayNameAndNickname {
	SELECT 
	  USER_ID, DEPARTMENT_ID, POSITION_ID
	  , CONCAT(NAME, ' (', NICKNAME, ')') AS BOOKING_NAME
	  , INSIDE_PHONE_NUMBER, EMAIL, LINE_USER_ID
	FROM [SCHEMAS].sec_user
	WHERE DELETED = 'N'
	AND CONCAT(NAME, ' (', NICKNAME, ')') LIKE CONCAT('%', %s, '%')
	ORDER BY NICKNAME ASC
	LIMIT %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Autocomplete ระบบ (system)
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchSystemsSelectItem{
	SELECT SYS.SYSTEM_ID
		, SYS.SYSTEM_NAME
	FROM (
	    SELECT SYSTEM_ID
	  		, CONCAT(SYSTEM_CODE, '-', SYSTEM_NAME ) AS SYSTEM_NAME
	  	FROM [SCHEMAS].qa_system
	  	WHERE PROJECT_ID = %s
	) SYS
	WHERE SYS.SYSTEM_NAME LIKE UPPER(REPLACE(CONCAT('%', %s , '%'),' ',''))
	ORDER BY SYS.SYSTEM_NAME
	LIMIT %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Autocomplete ระบบย่อย (sub system)
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchSubSystemsSelectItem{
	SELECT SUB.SUB_SYSTEM_ID
	    , SUB.SUB_SYSTEM_NAME
	FROM (
	    SELECT SUB_SYSTEM_ID
	  		, CONCAT(SUB_SYSTEM_CODE, '-', SUB_SYSTEM_NAME ) AS SUB_SYSTEM_NAME
	  	FROM [SCHEMAS].qa_sub_system
	  	WHERE SYSTEM_ID = %s
	  ) SUB
	WHERE SUB.SUB_SYSTEM_NAME LIKE UPPER(REPLACE(CONCAT('%', %s , '%'),' ',''))
	ORDER BY SUB.SUB_SYSTEM_NAME
	LIMIT %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Autocomplete ตำแหน่ง 
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchPositionsSelectItem{
	SELECT DEP.DEPARTMENT_ID,DEP.DEPARTMENT_CODE
	FROM(
		SELECT DEPARTMENT_ID, DEPARTMENT_CODE
		FROM [SCHEMAS].std_department
		WHERE DEPARTMENT_ID IN (%s)
	  	)DEP
	WHERE DEP.DEPARTMENT_CODE LIKE UPPER(REPLACE(CONCAT('%', %s , '%'),' ',''))
	ORDER BY DEP.DEPARTMENT_CODE DESC
	LIMIT %s
}

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------
SQL : Autocomplete scenario (scenario)
Description : 
------------------------------------------------------------------------------------------------------------------------------------------------------------*/
searchScenarioSelectItem{
	SELECT SCENARIO_ID
	  , SCENARIO_NAME
	FROM [SCHEMAS].qa_scenario
	WHERE  1 = 1
	AND SUB_SYSTEM_ID = %s
	AND SCENARIO_NAME LIKE UPPER(REPLACE(CONCAT('%', %s , '%'),' ',''))
	ORDER BY SCENARIO_NAME
	LIMIT %s
}