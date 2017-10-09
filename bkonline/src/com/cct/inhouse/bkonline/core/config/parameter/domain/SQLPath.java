package com.cct.inhouse.bkonline.core.config.parameter.domain;

import java.io.Serializable;

import com.cct.common.CommonSQLPath;

import resources.sql.inhouse.bkonline.BKOnlineSQL;

public enum SQLPath implements Serializable {
	
	BOOKING_SQL(BKOnlineSQL.class, "resources/sql/inhouse/bkonline/Booking.sql")
	, SETTING_SQL(BKOnlineSQL.class, "resources/sql/inhouse/bkonline/Setting.sql")
	, NOTIFICATION_SQL(BKOnlineSQL.class, "resources/sql/inhouse/bkonline/Notification.sql")
	, USER_SQL(BKOnlineSQL.class, "resources/sql/inhouse/bkonline/User.sql")
	, AUTO_APPROVE_SQL(BKOnlineSQL.class, "resources/sql/inhouse/bkonline/AutoApprove.sql")
	, MOBILE_SQL(BKOnlineSQL.class, "resources/sql/inhouse/bkonline/Mobile.sql")
	;
	
	private ReferenceSQLPath sqlPath;

	private SQLPath(Class<?> className, String path) {
		this.sqlPath = new ReferenceSQLPath(className, path);
	}

	public CommonSQLPath getSqlPath() {
		return sqlPath;
	}
	
	public String getPath() {
		return sqlPath.getPath();
	}
}