package com.cct.hrmdata.core.config.domain;

import java.io.Serializable;

import resources.sql.checkuserlogin.CheckUserLoginSQL;
import resources.sql.gettimeoffset.GetTimeOffsetSQL;
import resources.sql.getuser.GetUserSQL;
import resources.sql.timemoney.TimeMoneySQL;

public enum SQLPath implements Serializable {
	/**
	 * @Description: Class enum for data base lookup
	 */
	GET_USER_SQL(GetUserSQL.class, "resources/sql/getuser/GetUser.sql")
	,TIME_MONEY_SQL(TimeMoneySQL.class, "resources/sql/timemoney/TimeMoney.sql")
	,TIME_OFFSET_SQL(GetTimeOffsetSQL.class, "resources/sql/gettimeoffset/GetTimeOffset.sql")
	,CHECK_USER_LOGIN_SQL(CheckUserLoginSQL.class, "resources/sql/checkuserlogin/CheckUserLogin.sql")
	;

	private String path;
	private Class<?> className;

	private SQLPath(Class<?> className, String path) {
		this.path = path;
		this.className = className;
	}

	public String getPath() {
		return path;
	}

	public Class<?> getClassName() {
		return className;
	}
}