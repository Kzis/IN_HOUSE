package com.cct.abstracts;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.common.CommonManager;
import com.cct.common.CommonUser;

import util.database.CCTConnection;


/**
 * @param <C> Criteria
 * @param <R> Search Result
 * @param <T> Main domain
 * @param <U> Common User
 * @param <L> Locale
 */
public abstract class AbstractManager<C, R, T, U, L> extends CommonManager {

	private CommonUser user = null;
	private Locale locale = null;

	/**
	 * @param conn
	 */
	public AbstractManager(Logger logger, CommonUser user, Locale locale) {
		super(logger);
		this.user = user;
		this.locale = locale;
	}

	/**
	 * @Desc For Search Button
	 * @param conn
	 * @param criteria
	 * @param confirm
	 *            (confirm status for user confirm to show result over max
	 *            limit)
	 * @return
	 * @throws Exception
	 */
	public abstract List<R> search(CCTConnection conn, C criteria) throws Exception;

	/**
	 * @Desc For Load Edit or View Button
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract T searchById(CCTConnection conn, String id) throws Exception;

	/**
	 * @Desc For Add Button
	 * @param conn
	 * @param obj
	 * @param userId
	 * @throws Exception
	 */
	public abstract long add(CCTConnection conn, T obj) throws Exception;

	/**
	 * @Desc For Edit Button
	 * @param conn
	 * @param obj
	 * @throws Exception
	 */
	public abstract int edit(CCTConnection conn, T obj) throws Exception;

	/**
	 * @Desc For Delete Button
	 * @param conn
	 * @param ids
	 *            = 1,2,3,...,N กรณีต้องการลบหลายรายการ ,ids = 1 กรณีต้องการลบ 1
	 *            รายการ
	 * @param userId
	 *            for updateUser field
	 * @throws Exception
	 */
	public abstract int delete(CCTConnection conn, String ids) throws Exception;

	/**
	 * @Desc For Active and Inactive Button
	 * @param conn
	 * @param ids
	 *            = 1,2,3,...,N กรณีต้องการ update หลายรายการ ,ids = 1 รายการ
	 * @param activeFlag
	 *            Y=active,N=inactive
	 * @param userId
	 *            for updateUser field
	 * @throws Exception
	 */
	public abstract int updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception;

	public CommonUser getUser() {
		return user;
	}

	public Locale getLocale() {
		return locale;
	}

}
