package com.cct.inhouse.central.rmi.domain;

public enum SelectItemMethod {
	// Search CommonSelectItem
	SEARCH_PROJECTS_SELECT_ITEM("searchProjectsSelectItem")
	, SEARCH_ALL_USER_DISPLAY_NAME_AND_NICKNAME("searchAllUserDisplayNameAndNicknameSelectItem")
	
	// Search CommonSelectItem with criteria
	, SEARCH_DEPARTMENT_SELECT_ITEM("searchDepartmentSelectItem")
	, SEARCH_USER_FULL_DISPLAY_SELECT_ITEM("searchUserFullDisplaySelectItem")
	, SEARCH_POSITIONS_SELECT_ITEM("searchPositionsSelectItem")
	, SEARCH_EMPLOYEE_BY_DEPARTMENT_ID("searchEmployeeFullnameByDepartmentId")
	, SEARCH_SYSTEMS_SELECT_ITEM("searchSystemsSelectItem")
	, SEARCH_SUBSYSTEMS_SELECT_ITEM("searchSubSystemsSelectItem")
	, SEARCH_SCENARIO_SELECT_ITEM("searchScenarioSelectItem")
	
	// Search CommonSelectItemObject
	, SEARCH_DEPARTMENT_SELECT_ITEM_OBJ("searchDepartmentSelectItemObj")
	, SEARCH_TIMEOFFSET_CON_BY_PROJECT_ID("searchTimeoffsetConditionByProjectId")
	;
	
	private String method;
	
	private SelectItemMethod(String method) {
		this.method = method;
	}
	
	public String getMethod() {
		return method;
	}
}
