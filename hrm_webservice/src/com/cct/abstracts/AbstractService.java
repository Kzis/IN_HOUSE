package com.cct.abstracts;

import java.util.Locale;

import util.database.CCTConnection;

public abstract class AbstractService {
	protected CCTConnection conn = null;
	protected Locale locale = null;

	/**
	 * @param conn
	 */
	public AbstractService(CCTConnection conn, Locale locale) {
		this.conn = conn;
		this.locale = locale;
	}
}
