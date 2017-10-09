package com.cct.abstracts;

import java.util.Locale;

import com.cct.hrmdata.core.config.domain.SQLPath;

/**
 * @param <C> Criteria
 * @param <R> Search Result
 * @param <T> Main domain
 * @param <U> Common User
 * @param <L> Locale
 */
public abstract class AbstractDAO {

	private SQLPath sqlPath;

	/**
	 * สำหรับดึง sql path
	 * 
	 * @return
	 */
	public SQLPath getSqlPath() {
		return sqlPath;
	}

	/**
	 * สำหรับตั้งค่า sql path
	 * 
	 * @param sqlPath
	 */
	public void setSqlPath(SQLPath sqlPath) {
		this.sqlPath = sqlPath;
	}

	/**
	 * สำหรับ เปลื่ยน sql key ตาม locale
	 * 
	 * @param sqlKey
	 * @param locale
	 * @return
	 */
	public String getSqlKeyByLocale(String sqlKey, Locale locale) {
		return sqlKey + "_" + locale.getLanguage();
	}
}
