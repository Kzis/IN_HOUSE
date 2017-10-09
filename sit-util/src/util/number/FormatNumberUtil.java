/**
* Set Format of Number
* @author : SD
* @version : 1.0
*
*/
package util.number;

import java.text.DecimalFormat;

/**
 * Class การทำงานส่วน Format ของตัวเลข
 */
public class FormatNumberUtil {

	/**
	 * สำหรับการกำหนด format ตัวเลข.
	 * [Example : String number = FormatNumberUtil.customFormat("#,###.00",15100)]
	 *
	 * @param pattern : Format
	 * @param value : ค่าตัวเลข
	 * @return เลขตาม format ที่กำหนด
	 * @throws Exception
	 *
	 */
	public static String customFormat(String pattern, double value ) throws Exception{
		try{
			DecimalFormat format = new DecimalFormat(pattern);
			String output = format.format(value) ;
			return output;
		}catch(Exception e){
			throw e;
		}
	}
}

