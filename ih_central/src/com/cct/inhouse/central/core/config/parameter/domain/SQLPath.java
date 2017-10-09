package com.cct.inhouse.central.core.config.parameter.domain;

import java.io.Serializable;

import com.cct.common.CommonSQLPath;

import resources.sql.inhouse.central.selectitem.SelectItemSQL;

public enum SQLPath implements Serializable {
	
	SELECT_ITEM_SQL(SelectItemSQL.class, "resources/sql/inhouse/central/selectitem/SelectItem.sql");

	private CentralSQLPath sqlPath;

	private SQLPath(Class<?> className, String path) {
		this.sqlPath = new CentralSQLPath(className, path);
	}

	public CommonSQLPath getSqlPath() {
		return sqlPath;
	}
	
	public String getPath() {
		return sqlPath.getPath();
	}
}