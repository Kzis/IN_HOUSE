/**
 * Description: Format date for Database
 * @Author : SD
 * @Version : 1.0
 * @Create date: 09/02/2012
 *
 */
package util.date;
/**
 * The Class Format Date สำหรับ Database ใช้คู่กับ .
 */
public class DateFormatDBUtil {

	private String dateformat;

	/** The Constant DD/MM/YYYY. */
	public static final DateFormatDBUtil DDMMYYYY = new DateFormatDBUtil("DD/MM/YYYY");

	/** The Constant DD/MM/YYYY HH24:MI:SS. */
	public static final DateFormatDBUtil DDMMYYYYHH24MISS = new DateFormatDBUtil("DD/MM/YYYY HH24:MI:SS");

	/** The Constant YYYYMMDDHH24MISS. */
	public static final DateFormatDBUtil YYYYMMDDHH24MISS = new DateFormatDBUtil("YYYYMMDDHH24MISS");

	/** The Constant YYYYMMDD. */
	public static final DateFormatDBUtil YYYYMMDD = new DateFormatDBUtil("YYYYMMDD");

	/** The Constant HH24:MI:SS. */
	public static final DateFormatDBUtil HH24MISS = new DateFormatDBUtil("HH24:MI:SS");

	/** The Constant HH24:MI. */
	public static final DateFormatDBUtil HH24MI = new DateFormatDBUtil("HH24:MI");

	/**
	 * Instantiates a new date format db util.
	 *
	 * @param dateformat : Format Date
	 */
	private DateFormatDBUtil(String dateformat) {
		this.dateformat = dateformat;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return dateformat;
	}

}
