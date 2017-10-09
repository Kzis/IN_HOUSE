package com.cct.abstracts;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.common.CommonDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonUser;
import com.cct.exception.DuplicateException;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;

/**
 * @param <C> Criteria
 * @param <R> Search Result
 * @param <T> Main domain
 * @param <U> Common User
 * @param <L> Locale
 */
public abstract class AbstractDAO<C, R, T> extends CommonDAO {

	private CommonUser user = null;
	private Locale locale = null;
	
	public AbstractDAO(Logger logger, CommonSQLPath sqlPath, CommonUser user, Locale locale) {
		super(logger, sqlPath);
		this.user = user;
		this.locale = locale;
	}
	
	/**
	 * นำจำนวนข้อมูล โดยดึงจาก Column TOT
	 * @param conn
	 * @param criteria
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public long countData(CCTConnection conn, C criteria) throws Exception {
		
		long result = -1;
			
		String sql = createSQLCountData(conn, criteria, getUser(), getLocale());
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = rst.getLong(1);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * จัดการ SQL สำหรับนับจำนวนข้อมูล
	 * @param conn
	 * @param criteria
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected abstract String createSQLCountData(CCTConnection conn, C criteria, CommonUser user, Locale locale) throws Exception;
	
	/**
	 * ค้นหาข้อมูล
	 * @param conn
	 * @param criteria
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public List<R> search(CCTConnection conn, C criteria) throws Exception {
		
		List<R> listResult = new ArrayList<R>();
		
		String sql = createSQLSearch(conn, criteria, getUser(), getLocale());
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			while (rst.next()) {
				listResult.add(createResultSearch(rst, getUser(), getLocale()));
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listResult;
	}
	
	/**
	 * จัดการ SQL สำหรับค้นหาข้อมูล
	 * @param conn
	 * @param criteria
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected abstract String createSQLSearch(CCTConnection conn, C criteria, CommonUser user, Locale locale) throws Exception;
	
	
	/**
	 * จัดการ Result ค้นหาข้อมูล
	 * @param conn
	 * @param criteria
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected abstract R createResultSearch(ResultSet rst, CommonUser user, Locale locale) throws Exception;
	
	/**
	 * ค้นหาข้อมูลด้วย id สำหรับแก้ไข และแสดง
	 * @param conn
	 * @param id
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public T searchById(CCTConnection conn, String id) throws Exception {
		
		T result = null;
		
		String sql = createSQLSearchById(conn, id, getUser(), getLocale());
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = createResultSearchById(rst, getUser(), getLocale());
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * จัดการ SQL ค้นหาข้อมูลด้วย id สำหรับแก้ไข และแสดง
	 * @param conn
	 * @param id
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected abstract String createSQLSearchById(CCTConnection conn, String id, CommonUser user, Locale locale) throws Exception;
	
	/**
	 * จัดการ Result ค้นหาข้อมูล
	 * @param rst
	 * @return
	 * @throws Exception
	 */
	protected abstract T createResultSearchById(ResultSet rst, CommonUser user, Locale locale) throws Exception;
	
	/**
	 * เพิ่มข้อมูล
	 * @param conn
	 * @param obj
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public long add(CCTConnection conn, T obj) throws Exception {
	
		long pk = -1;
		
		String sql = createSQLAdd(conn, obj, getUser(), getLocale());
		try {
			pk = executeInsert(conn, sql);
		} catch (Exception ex) {
			throw ex;
		}
		return pk;
	}

	/**
	 *  จัดการ SQL สำหรับเพิ่มข้อมูล
	 * @param conn
	 * @param obj
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected abstract String createSQLAdd(CCTConnection conn, T obj, CommonUser user, Locale locale) throws Exception;
	
	/**
	 * แก้ไขข้อมูล
	 * @param conn
	 * @param obj
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public long edit(CCTConnection conn, T obj) throws Exception {
		
		long rowCount  = -1;
		
		String sql = createSQLEdit(conn, obj, getUser(), getLocale());
		try {
			rowCount = executeUpdate(conn, sql);
		} catch (Exception ex) {
			throw ex;
		}
		return rowCount;
	}
	
	/**
	 * จัดการ SQL สำหรับแก้ไขข้อมูล
	 * @param conn
	 * @param obj
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected abstract String createSQLEdit(CCTConnection conn, T obj, CommonUser user, Locale locale) throws Exception;

	/**
	 * For Delete Button
	 * @param conn
	 * @param ids = 1,2,3,...,N กรณีต้องการลบหลายรายการ ,ids = 1 กรณีต้องการลบ 1
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public long delete(CCTConnection conn, String ids) throws Exception {
		
		long rowCount  = -1;
		
		String sql = createSQLDelete(conn, ids, getUser(), getLocale());
		try {
			rowCount = executeUpdate(conn, sql);
		} catch (Exception ex) {
			throw ex;
		}
		return rowCount;
	}
	
	/**
	 * จัดการ SQL สำหรับลบข้อมูล
	 * @param conn
	 * @param ids
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected abstract String createSQLDelete(CCTConnection conn, String ids, CommonUser user, Locale locale) throws Exception;
	
	/**
	 * For Active and Inactive Button
	 * @param conn
	 * @param ids  = 1,2,3,...,N กรณีต้องการ update หลายรายการ ,ids = 1 รายการ
	 * @param activeFlag
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	public long updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception {
		
		long rowCount  = -1;
		
		String sql = createSQLUpdateActive(conn, ids, activeFlag, getUser(), getLocale());
		try {
			rowCount = executeUpdate(conn, sql);
		} catch (Exception ex) {
			throw ex;
		}
		return rowCount;
	}
	
	protected abstract String createSQLUpdateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user, Locale locale) throws Exception;

	/**
	 * Check duplicate before add or edit<br>
	 * return true is duplicate<br>
	 * return false not duplicate<br>
	 * @param conn
	 * @param obj
	 * @throws DuplicateDataException
	 */
	public void checkDup(CCTConnection conn, T obj) throws Exception {
		
		String sql = createSQLCheckDup(conn, obj, getUser(), getLocale());
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				boolean result = rst.getLong(1) > 0;
				if (result) {
					throw new DuplicateException();
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
	}
	
	/**
	 * จัดการ SQL สำหรับตรวจสอบข้อมูลซ้ำ
	 * @param conn
	 * @param obj
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected abstract String createSQLCheckDup(CCTConnection conn, T obj, CommonUser user, Locale locale) throws Exception;

	/**
	 * สำหรับ เปลื่ยน sql key ตาม locale
	 * 
	 * @param sqlKey
	 * @param locale
	 * @return
	 */
	public String getSqlKeyByLocale(String sqlKey, Locale locale) {
		return sqlKey + StringDelimeter.UNDERLINE.getValue() + locale.getLanguage();
	}
	

	public CommonUser getUser() {
		return user;
	}

	public Locale getLocale() {
		return locale;
	}
}
