package com.cct.inhouse.core.config.parameter.domain;

import java.io.Serializable;

import com.cct.common.CommonSQLPath;

import resources.sql.inhouse.security.SharedPermissionSQL;

public enum SQLPath implements Serializable {
	/**
	 * @Description: Class enum for data base lookup
	 */
	SHARED_PERMISSION_SQL(SharedPermissionSQL.class, "resources/sql/inhouse/security/SharedPermission.sql");

	private SharedPermissionSQL sqlPath;

	private SQLPath(Class<?> className, String path) {
		this.sqlPath = new SharedPermissionSQL(className, path);
	}

	public CommonSQLPath getSqlPath() {
		return sqlPath;
	}
	
	public String getPath() {
		return sqlPath.getPath();
	}
}
