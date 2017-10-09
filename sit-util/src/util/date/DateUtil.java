/**
 * ทำงานเกี่ยวกับวันที่ เวลา
 * @author : SD
 * @version : 1.0
 *
 */

package util.date;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import util.type.DatabaseType.DbType;

/**
 * Class การทำงานเกี่ยวกับข้อมูลวันที่เวลา
 */
public class DateUtil {

	/**
	 * ดึงค่าวันที่ปัจจุบัน.
	 * <p>
	 * [Example : String curDate = DateUtil.getCurrentDate("dd/MM/yyyy","th")]
	 *
	 * @param format : formatDate
	 * @param locale : locale
	 * @return currentDate : วันที่ปัจจุบัน
	 * @throws Exception the exception
	 */
	@Deprecated public static String getCurrentDate(String format,String locale) throws Exception {
		try{
			Locale localeobj = null;
			if(locale!= null && !locale.trim().equals("")){
				localeobj = new Locale(locale.trim().toLowerCase(),locale.trim().toUpperCase());
			}
			return getCurrentDate(format, localeobj);
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * ดึงค่าวันที่ปัจจุบัน.
	 * <p>
	 * [Example : String curDate = getCurrentDate("dd/MM/yyyy",locale.ENGLISH)]
	 *
	 * @param format : formatDate
	 * @param locale : locale
	 * @return currentDate : วันที่ปัจจุบัน
	 * @throws Exception the exception
	 */
	public static String getCurrentDate(String format,Locale locale) throws Exception {
		String curdate = "";
		try{
			if(locale == null){
				locale = locale.ENGLISH;
			}

			DateFormat dateFormat = new SimpleDateFormat(format,locale);
			Date date = new Date();
			curdate = dateFormat.format(date);
		}catch(Exception e){
			throw e;
		}
		return curdate;
	}


	/**
	 * แปลงค่าจาก Date เป็น String.
	 * <p>
	 * [Example :  String curdate = DateUtil.dateToString(sysdate,"dd/MM/yyyy HH:mm:ss","en")]
	 *
	 * @param date : Date
	 * @param format : formatDate
	 * @param locale : Locale
	 * @return date : วันที่(String)
	 * @throws Exception the exception
	 */
	@Deprecated public static String dateToString(Date date,String format,String locale) throws Exception{
		try{
			Locale localeobj = null;
			if(locale!= null && !locale.trim().equals("")){
				localeobj = new Locale(locale.trim().toLowerCase(),locale.trim().toUpperCase());
			}
			return dateToString(date, format, localeobj);
		}catch(Exception e){
			throw e;
		}
	}


	/**
	 * แปลงค่าจาก Date เป็น String.
	 * <p>
	 * [Example :  String curdate = DateUtil.dateToString(sysdate,"dd/MM/yyyy HH:mm:ss",locale.ENGLISH)]
	 *
	 * @param date : Date
	 * @param format : formatDate
	 * @param locale : Locale
	 * @return date : วันที่(String)
	 * @throws Exception the exception
	 */
	public static String dateToString(Date date,String format,Locale locale) throws Exception{
		try{
			SimpleDateFormat parser = null;
			if(locale != null && ! locale.equals("")){
				parser = new SimpleDateFormat(format,locale);
			}else{
				parser = new SimpleDateFormat(format,Locale.ENGLISH);
			}
			return date == null ? null:parser.format(date);
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * แปลงค่าจาก String เป็น  Date.
	 * <p>
	 * [Example :  Date curdate = DateUtil.stringToDate("10/12/2012 12:13:54","dd/MM/yyyy HH:mm:ss","en")]
	 *
	 * @param dateString : Date
	 * @param format : formatDate
	 * @param locale : Locale
	 * @return date : วันที่(Date)
	 * @throws Exception the exception
	 */
	@Deprecated public static Date stringToDate(String dateString,String format,String locale) throws Exception{
		try{
			Locale localeobj = null;
			if(locale!= null && !locale.trim().equals("")){
				localeobj = new Locale(locale.trim().toLowerCase(),locale.trim().toUpperCase());
			}
			return stringToDate(dateString, format, localeobj);

		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * แปลงค่าจาก String เป็น  Date.
	 * <p>
	 * [Example :  Date curdate = DateUtil.stringToDate("10/12/2012 12:13:54","dd/MM/yyyy HH:mm:ss","Locale.ENGLISH")]
	 *
	 * @param dateString : Date
	 * @param format : formatDate
	 * @param locale : Locale
	 * @return date : วันที่(Date)
	 * @throws Exception the exception
	 */
	public static Date stringToDate(String dateString,String format,Locale locale) throws Exception{
		Date result = null;
		try{
			if(dateString != null && !dateString.trim().equals("")){
				if(locale!=null){
					result = new SimpleDateFormat(format,locale).parse(dateString);
				}else{
					result = new SimpleDateFormat(format,Locale.ENGLISH).parse(dateString);
				}
			}
			return result;
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * ดึงค่าวันที่ปัจจุบัน จาก database.
	 * <p>
	 * [Example : String curdate = DateUtil.getCurrentDateDB(conn,"dd/mm/yyyy",DateUtil.DbType.ORA)]
	 *
	 * @param conn : Connection
	 * @param format Format Date
	 * @param dbType : Database oracla,mysql,db2
	 * @return วันที่ปัจจุบัน จาก database
	 * @throws Exception the exception
	 */
	public static String getCurrentDateDB(Connection conn,String format,DbType dbType) throws Exception{
		String curDate = "";

		ResultSet rst = null;
		Statement stmt = null;
		try{
			if(dbType.equals(DbType.ORA)){
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rst = stmt.executeQuery("select to_char(sysdate,'"+format+"') as currentdate from dual");
				rst.next();
				curDate = rst.getString("currentdate");
			}else if(dbType.equals(DbType.MYSQL)){
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rst = stmt.executeQuery("select DATE_FORMAT(sysdate(),'"+format+"') as currentdate from dual");
				rst.next();
				curDate = rst.getString("currentdate");
			}else if(dbType.equals(DbType.DB2)){
				stmt = conn.createStatement();
				rst = stmt.executeQuery("select TO_CHAR(CURRENT TIMESTAMP,'"+format+"') as curdate from SYSIBM.SYSDUMMY1 ");
				rst.next();
				curDate = rst.getString("curdate");
			}
			return curDate;
		}catch (Exception e) {
			throw e;
		}finally{
			try{if(rst!=null)rst.close();}catch (Exception e) {}
			try{if(stmt!=null)stmt.close();}catch (Exception e) {}
		}
	}
	
	public static String getCurrentDateDB(Connection conn,DbType dbType) throws Exception{
		try{
			if(dbType.equals(DbType.ORA)){
				return getCurrentDateDB(conn,"DD/MM/YYYY HH24:MI:SS",dbType);
			}else if(dbType.equals(DbType.MYSQL)){
				return getCurrentDateDB(conn,"%d/%m/%Y %H:%i:%s",dbType);
			}else if(dbType.equals(DbType.DB2)){
				return getCurrentDateDB(conn,"DD/MM/YYYY HH24:MI:SS",dbType);
			}
			return "";
		}catch (Exception e) {
			throw e;
		}finally{
		}
	}

}
