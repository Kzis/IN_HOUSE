package com.cct.abstracts;

import java.util.Locale;

import util.database.CCTConnection;


/**
 * @param <C> Criteria
 * @param <R> Search Result
 * @param <T> Main domain
 * @param <U> Common User
 * @param <L> Locale
 */
public abstract class AbstractManager{

	protected CCTConnection conn = null;
	protected Locale locale = null;

	/**
	 * @param conn
	 */
	public AbstractManager(CCTConnection conn, Locale locale) {
		this.conn = conn;
		this.locale = locale;
	}


}
