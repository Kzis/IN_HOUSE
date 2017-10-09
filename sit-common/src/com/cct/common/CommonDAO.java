package com.cct.common;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;

public abstract class CommonDAO {

	private Logger logger;
	private CommonSQLPath sqlPath;
	
	public CommonDAO(Logger logger, CommonSQLPath sqlPath) {
		this.logger = logger;
		this.sqlPath = sqlPath;
	}

	/**
	 * สำหรับดึง sql path
	 * 
	 * @return
	 */
	public CommonSQLPath getSqlPath() {
		return sqlPath;
	}

	/**
	 * สำหรับดึง logger มาใช้งาน
	 * @return
	 */
	public Logger getLogger() {
		return logger;
	}
	
	/**
	 * สร้าง Statement สำหรับ Insert, Update, Delete และ Select
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public Statement createStatement(CCTConnection conn) throws Exception {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (Exception ex) {
			throw ex;
		}
		return stmt;
	}
	
	/**
	 * ค้นหาทั่วไป
	 * @param stmt
	 * @param rst
	 * @param sql
	 * @throws Exception
	 */
	public ResultSet executeQuery(Statement stmt, String sql) throws Exception {
		ResultSet rst = null;
		try {
			rst = stmt.executeQuery(sql);
		} catch (Exception ex) {
			getLogger().error(sql, ex);
			throw ex;
		}
		return rst;
	}
	
	/**
	 * Insert ข้อมูลทั่วไป
	 * @param conn
	 * @param stmt
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public long executeInsert(CCTConnection conn, String sql) throws Exception {
		long results  = -1;
		Statement stmt = null;
		try {
			stmt = createStatement(conn);
			results = stmt.executeUpdate(sql);
		} catch (Exception ex) {
			getLogger().error(sql, ex);
			throw ex;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		return results;
	}
	
	/**
	 * Insert ข้อมูล และรับ PK กลับ
	 * @param conn
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public long executeInsertReturnPK(CCTConnection conn, String sql) throws Exception {
		long pk = -1;
		try {
			pk = conn.executeInsertStatement(sql);
		} catch (Exception ex) {
			getLogger().error(sql, ex);
			throw ex;
		}
		return pk;
	}
	
	/**
	 * Update ข้อมูลทั่วไป
	 * @param conn
	 * @param stmt
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public long executeUpdate(CCTConnection conn, String sql) throws Exception {
		long rowCount  = -1;
		Statement stmt = null;
		try {
			stmt = createStatement(conn);
			rowCount = stmt.executeUpdate(sql);
		} catch (Exception ex) {
			getLogger().error(sql, ex);
			throw ex;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		return rowCount;
	}
}
