package util.database;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

import util.string.StringUtil;
import util.type.DatabaseType.DbType;

/**
 * SQLUtil Version II <br>
 * 2017-02-28 : Add method getSQLString(Map<String, String> schemas, DefaultSQLPath sqlPath, String key, Object... params)
 * @author SD-Sittipol
 *
 */
public class SQLUtil {
	public static String DELIMITER_UNDERLINE = "_";
	public static String DELIMITER_PERCENT = "%";
	public static int LIKE_CENTER = 0;
	public static int LIKE_RIGHT = 1;
	public static int LIKE_LEFT = 2;

	/**
	 * Description : Read SQL command from .sql and prepared sql statement with parameters
	 *
	 * @param schemas
	 * @param path
	 * @param key
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String getSQLString(Map<String, String> schemas, Class classz, String path, String key, Object... params) throws Exception {
		String sql = "";
		if ((key.toUpperCase().indexOf("INSERT") != -1) || (key.toUpperCase().indexOf("UPDATE") != -1)) {
			sql = getSQL(classz, path, key);
			Object[] obj = checkParams(params);
			sql = formatM(sql, obj);
		} else {
			sql = getSQL(classz, path, key, params);
		}

		if (schemas != null) {
			for (String schema : schemas.keySet()) {
				sql = sql.replace(schema, schemas.get(schema));
			}
		}

		return sql;
	}

	/**
	 * %s to value
	 * @param sql
	 * @param obj
	 * @return
	 */
	public static String formatM(String sql, Object... obj) {
		if(sql != null){
			String[] line = sql.split("\n");
			String sqlResult = "";
			int i = 0;
			for(String temp : line){
				if(temp.indexOf("%s") != -1){
					temp = temp.replace("%s", obj[i]+"");
					i++;
				}
				sqlResult += temp + "\n";
			}
			sql = sqlResult;
		}
		return sql;
	}
	
	
	
	/**
	 * Description : SQL with Null value.(such as ADD,EDIT)
	 *
	 * @param path
	 *            sql file
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected static String getSQL(Class classz, String path, String key) throws Exception {
		InputStream is = null;
		BufferedReader br = null;
		StringBuffer sql = new StringBuffer();
		try {

			if(new File(path).exists()){
				is = new FileInputStream(path);
				
			} else {
				ClassLoader classLoader = (classz).getClassLoader();
				is = classLoader.getResourceAsStream(path);
			}

			DataInputStream in = new DataInputStream(is);
			br = new BufferedReader(new InputStreamReader(in, "UTF8"));

			String strLine;
			boolean read = false;
			while ((strLine = br.readLine()) != null) {

				if (strLine.trim().replace(" ", "").equals(key + "{")) {
					read = true;
					continue;
				}

				if (read) {
					if (!strLine.equals("}")) {
						if (strLine.trim().length() > 0) {
							sql.append(strLine.trim() + " \n");
						}
					} else {
						break;
					}
				}

			}

			in.close();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (br != null)
					br.close();
				if (is != null)
					is.close();
			} catch (Exception e) {
				throw e;
			}
		}
		return sql.toString();
	}

	/**
	 * checkParams
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Object[] checkParams(Object... params) throws Exception {
		Object[] obj = new Object[params.length];
		try {
			if (params != null) {
				for (int j = 0; j < params.length; j++) {
					if (params[j] == null || params[j].equals("null")) {
						obj[j] = null;
					} else {
						obj[j] = formatParams(params[j]);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return obj;
	}

	/**
	 * Description : SQL without Null value.
	 *
	 * @param path
	 *            sql file
	 * @param key
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected static String getSQL(Class classz, String path, String key, Object... params) throws Exception {
		InputStream is = null;
		BufferedReader br = null;
		StringBuffer sql = new StringBuffer();
		try {

			if(new File(path).exists()){
				is = new FileInputStream(path);
				
			} else {
				ClassLoader classLoader = (classz).getClassLoader();
				is = classLoader.getResourceAsStream(path);
			}
			
			DataInputStream in = new DataInputStream(is);
			br = new BufferedReader(new InputStreamReader(in, "UTF8"));

			String strLine;
			boolean read = false;
			int i = 0;

			while ((strLine = br.readLine()) != null) {
				if (strLine.trim().replace(" ", "").equals(key + "{")) {
					read = true;
					continue;
				}

				if (read) {
					if (!strLine.equals("}")) {

						if (strLine.indexOf("%s") != -1) {
							if (params != null) {
								if (params[i] == null) {
									i++;
									continue;
								} else {
									if ((strLine.indexOf(" IN (") != -1)
											|| (strLine.indexOf(" AS ") != -1)
											|| strLine.trim().equals("%s")
											|| (strLine.indexOf(".%s") != -1)
											|| (params[i].toString().indexOf("TO_DATE") != -1)) {
										sql.append(strLine.trim().replaceAll("%s", params[i].toString()) + "\n");
									} else {
										sql.append(strLine.trim().replaceAll("%s", Matcher.quoteReplacement(formatParams(params[i]))) + "\n");
									}
								}
							}

							i++;
						} else {
							if (strLine.trim().length() > 0) {
								sql.append(strLine.trim() + " \n");
							}
						}

					} else {
						break;
					}
				}
			}

			in.close();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (br != null)
					br.close();
				if (is != null)
					is.close();
			} catch (Exception e) {
				throw e;
			}
		}
		return sql.toString();
	}

	private static String formatParams(Object obj) throws Exception {
		return obj instanceof String ? String.format("'%s'", obj) : obj.toString();
	}

	public static String getColumnByLocale(String column, Locale locale) throws Exception {
		String columnWithLocale;
		if (column.lastIndexOf(DELIMITER_UNDERLINE) == (column.length() - 1)) {
			columnWithLocale = column + locale.getLanguage().toUpperCase();
		} else {
			columnWithLocale = column + DELIMITER_UNDERLINE + locale.getLanguage().toUpperCase();
		}
		return columnWithLocale;
	}

	public static String valueToDate(String value, String format) throws Exception {
		String toDate = "";
		try {
			toDate = "TO_DATE('" + value + "','" + format + "')";
		} catch (Exception e) {
			throw e;
		}
		return toDate;
	}

	public static String valueToLike(String value, int position) throws Exception {

		String toLike = null;
		try {
			String tmp = StringUtil.replaceSpecialString(value, DbType.ORA);
			if (tmp.isEmpty() == false) {
				if (position == LIKE_CENTER) {
					toLike = "%" + tmp + "%";
				} else if (position == LIKE_RIGHT) {
					toLike = tmp + "%";
				} else if (position == LIKE_LEFT) {
					toLike = "%" + tmp;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return toLike;
	}

	public static Object valueToInteger(String value) throws Exception {

		Integer toInteger = null;
		try {
			if ((value != null) && (value.trim().isEmpty() == false)) {
				toInteger = Integer.parseInt(value);
			}
		} catch (Exception e) {
			throw e;
		}
		return toInteger;
	}
	
	/**
	 * Description : Read SQL command from .sql and prepared sql statement with parameters
	 * @param schemas
	 * @param sqlPath
	 * @param key
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getSQLString(Map<String, String> schemas, DefaultSQLPath sqlPath, String key, Object... params) throws Exception {
		return getSQLString(schemas, sqlPath.getClass(), sqlPath.getPath(), key, params);
	}
}
