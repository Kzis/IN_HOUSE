/**
 * Description: Format Date
 * @author : SD
 * @version : 1.0
 *
 */
package util.date;

/**
 * Class Format Date ใช้กับ Class DateUtil.
 */
public class DateFormatUtil {

	private String dateformat;

	/** The Constant dd/MM/yyyy. */
	public static final DateFormatUtil DDMMYYYY = new DateFormatUtil("dd/MM/yyyy");

	/** The Constant dd/MM/yyyy HH:mm:ss. */
	public static final DateFormatUtil DDMMYYYYHH24MISS = new DateFormatUtil("dd/MM/yyyy HH:mm:ss");

	/** The Constant yyyyMMddHHmmss. */
	public static final DateFormatUtil YYYYMMDDHH24MISS = new DateFormatUtil("yyyyMMddHHmmss");

	/** The Constant yyyyMMdd. */
	public static final DateFormatUtil YYYYMMDD = new DateFormatUtil("yyyyMMdd");


	/** The Constant HH:mm:ss. */
	public static final DateFormatUtil HH24MISS = new DateFormatUtil("HH:mm:ss");


	/** The Constant HH:mm. */
	public static final DateFormatUtil HH24MI = new DateFormatUtil("HH:mm");

	/**
	 * Instantiates a new date format util.
	 *
	 * @param dateformat : Format Date
	 */
	private DateFormatUtil(String dateformat) {
		this.dateformat = dateformat;
	}

	public String toString() {
		return dateformat;
	}

}
