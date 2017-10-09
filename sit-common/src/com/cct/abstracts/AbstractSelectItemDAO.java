package com.cct.abstracts;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.common.CommonDAO;
import com.cct.common.CommonSQLPath;
import com.cct.common.CommonSelectItem;
import com.cct.common.CommonSelectItemObject;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringUtil;

public abstract class AbstractSelectItemDAO extends CommonDAO {

	/**
	 * Constructor
	 * 
	 * @param logger
	 * @param conn
	 * @param sqlPath
	 */
	public AbstractSelectItemDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	/**
	 * Search data select item in type
	 * 
	 * @param sql
	 * @param typeColumn
	 * @param keyColumn
	 * @param valueColumn
	 * @return
	 * @throws Exception
	 */
	protected Map<String, List<CommonSelectItem>> searchGlobalDataSelectItem(CCTConnection conn, String sql, String typeColumn, String keyColumn, String valueColumn) throws Exception {

		Map<String, List<CommonSelectItem>> mapGlobalData = new HashMap<String, List<CommonSelectItem>>();

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				// Check had type and Create list
				String globalType = StringUtil.nullToString(rst.getString(typeColumn));
				if (mapGlobalData.get(globalType) == null) {
					mapGlobalData.put(globalType, new ArrayList<CommonSelectItem>());
				}

				// Create select item and Add to list
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString(keyColumn)));
				selectItem.setValue(StringUtil.nullToString(rst.getString(valueColumn)));
				mapGlobalData.get(globalType).add(selectItem);
			}
		} catch (Exception e) {
			getLogger().error(sql, e);
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return mapGlobalData;
	}

	/**
	 * สำหรับค้นหาข้อมูล select item และส่งกลับไปยัง client แบบ key กับ value
	 * 
	 * @param sql
	 * @param keyColumn
	 * @param valueColumn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchDataSelectItem(CCTConnection conn, String sql, String keyColumn, String valueColumn) throws Exception {

		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString(keyColumn)));
				selectItem.setValue(StringUtil.nullToString(rst.getString(valueColumn)));

				listSelectItem.add(selectItem);
			}

		} catch (Exception e) {
			getLogger().error(sql, e);
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return listSelectItem;
	}

	/**
	 * สำหรับค้นหาข้อมูล select item และส่งกลับไปยัง client แบบ key กับ object
	 * @param conn
	 * @param sql
	 * @param keyColumn
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<CommonSelectItemObject<?>> searchDataSelectItem(CCTConnection conn, String sql, String keyColumn, String valueColumn, Class<?> clazz) throws Exception {

		List<CommonSelectItemObject<?>> listSelectItem = new ArrayList<CommonSelectItemObject<?>>();

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				CommonSelectItemObject selectItem = new CommonSelectItemObject();
				selectItem.setKey(StringUtil.nullToString(rst.getString(keyColumn)));
				selectItem.setValue(StringUtil.nullToString(rst.getString(valueColumn)));
				
				// new object
				Constructor<?> ctor = clazz.getConstructor(ResultSet.class);
				Object object = ctor.newInstance(rst);
				// getLogger().debug(object.getClass());
				selectItem.setObject(object);

				// Set to list
				listSelectItem.add(selectItem);
			}

		} catch (Exception e) {
			getLogger().error(sql, e);
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return listSelectItem;
	}

}
