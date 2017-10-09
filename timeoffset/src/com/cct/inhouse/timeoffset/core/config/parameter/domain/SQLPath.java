package com.cct.inhouse.timeoffset.core.config.parameter.domain;

import java.io.Serializable;

import com.cct.common.CommonSQLPath;

import resources.sql.inhouse.timeoffset.TimeOffsetSQL;

public enum SQLPath implements Serializable {
	
//	SEC_PERMISSION(SecuritySQL.class, "resources/sql/security/Permission.sql")
//	, 
	
	TO_CONDITION_PROJ(TimeOffsetSQL.class, "resources/sql/inhouse/timeoffset/ConditionProject.sql")
	, TO_MANAGE(TimeOffsetSQL.class, "resources/sql/inhouse/timeoffset/TimeOffsetManage.sql")
	, TO_TODO(TimeOffsetSQL.class, "resources/sql/inhouse/timeoffset/TimeOffsetTodo.sql")
	, TO_APPROVE(TimeOffsetSQL.class, "resources/sql/inhouse/timeoffset/TimeOffsetApprove.sql")
	, TO_HISTORY(TimeOffsetSQL.class, "resources/sql/inhouse/timeoffset/TimeOffsetHistory.sql")
	, TO_REPORT(TimeOffsetSQL.class, "resources/sql/inhouse/timeoffset/TimeOffsetReport.sql")
	, USER_SQL(TimeOffsetSQL.class, "resources/sql/inhouse/timeoffset/User.sql")
	;

	
	private TimeOffsetSQLPath sqlPath;

	private SQLPath(Class<?> className, String path) {
		this.sqlPath = new TimeOffsetSQLPath(className, path);
	}

	public CommonSQLPath getSqlPath() {
		return sqlPath;
	}
	
	public String getPath() {
		return sqlPath.getPath();
	}
}