/**
 * Utilities for manage String
 * @author: prasit.s
 * @version: 1.0
 *
 *
 **/

package util.string;

import util.log.DefaultLogUtil;
import util.type.DatabaseType.DbType;
import util.type.StringType.ResultType;

/**
 * Class การทำงานจัดการข้อมูล String
 */
public class StringUtil {

	/**
	 * Convert null string to blank string and auto trim string.
	 * <p>
	 * [Example : String val = StringUtil.nullToString(null);]
	 *
	 * @param s
	 *            : object to convert
	 * @return String
	 * @throws Exception
	 *
	 */
	public static String nullToString(Object s) throws Exception {
		return s == null ? "" : s.toString().trim();
	}

	/**
	 * Convert blank string to null string and auto trim string.
	 * <p>
	 * [Example : String val = StringUtil.stringToNull("");]
	 *
	 * @param s
	 *            : object to convert
	 * @return String
	 *
	 */
	public static String stringToNull(Object s) throws Exception {
		return s == null || s.toString().trim().equals("") ? null : s.toString().trim();
	}

	/**
	 * Convert blank string to Zero and auto trim string.
	 * <p>
	 * [String val = StringUtil.nullToZero(null)]
	 *
	 * @param s
	 *            : object to convert
	 * @return String
	 *
	 */
	public static String nullToZero(Object s) throws Exception {
		return s == null || s.toString().trim().equals("") ? "0" : s.toString().trim();
	}

	/**
	 * Replace Special String Charector for database .
	 * <p>
	 * [Example : String val = StringUtil.replaceSpecialString("วรรณคดี '
	 * วิชาการ",DbType.ORA)]
	 *
	 * @param s
	 *            : String to convert
	 * @param dbType
	 *            : Database Type
	 * @return String
	 */
	public static String replaceSpecialString(String s, DbType dbType) throws Exception {
		if (dbType == null)
			return s;
		return replaceSpecialString(s, dbType, ResultType.EMPTY);
	}

	/**
	 * Replace Special String Charector for database .
	 * <p>
	 * [Example : String val = StringUtil.replaceSpecialString("วรรณคดี '
	 * วิชาการ",DbType.ORA,ResultType.EMPTY)]
	 *
	 * @param s
	 *            : String to convert
	 * @param dbType
	 *            : Database Type
	 * @return String
	 */
	public static String replaceSpecialString(String s, DbType dbType, ResultType resultType) throws Exception {
		if (dbType == null)
			return s;
		if (s == null || s.trim().equals("")) {
			if (resultType.equals(ResultType.EMPTY)) {
				return "";
			} else if (resultType.equals(ResultType.NULL)) {
				return null;
			} else {
				return s;
			}
		}
		if (dbType.equals(DbType.ORA)) {
			return s.replaceAll("'", "''").trim();
		} else if (dbType.equals(DbType.MYSQL)) {
			return s.replaceAll("'", "''").replace("\\", "\\\\").trim();
		} else if (dbType.equals(DbType.DB2)) {
			return s == null ? "" : s.replaceAll("'", "''").trim();
		} else if (dbType.equals(DbType.POSTGRESQL)) {
			return s == null ? "" : s.replaceAll("'", "''").trim();
		} else {
			return s;
		}
	}

	public static void main(String[] args) {
		try {
			System.out.print(replaceSpecialString(null, DbType.ORA, ResultType.NULL));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * แปลง String เป็น Long
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static Long stringToLong(Object s) throws Exception {
		if ((s == null) || s.toString().trim().isEmpty()) {
			return null;
		} else {
			return Long.valueOf(s.toString().trim());
		}
	}
	
	public static String escapeJavascript(String input) {
		String output = input;
		try {
			output = output.replace("'", "\\'").replace("\n", StringDelimeter.EMPTY.getValue()).replace("\r", StringDelimeter.EMPTY.getValue());
		} catch (Exception e) {
			DefaultLogUtil.UTIL.error(e);
		}
		return output;
	}
}
