/**
 * get Data จาก  Resource Bundle.
 * @author : SD
 * @version : 1.0
 *
 */
package util.bundle;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class การทำงานส่วน Resource Bundle
 */
public class BundleUtil {

	/**
	 * สำหรับการเรียกใช้ resource bundle ไฟล์.
	 * ในกรณีที่ locale ทีส่งเป็น null ค่า locale จะถูก set เป็น TH(THAI)
     * <p>
     * [Example : ResourceBundle bundle = BundleUtil.getBundle("util/number/Message", Locale.ENGLISG)]
	 *
	 * @param filePackage : path ที่อยู่ BundleFile
	 * @param locale : java.util.Locale
	 * @return ResourceBundle
	 * @throws Exception
	 */
	public static ResourceBundle getBundle(String filePackage, Locale locale) throws Exception {
		return  ResourceBundle.getBundle(filePackage, locale!=null ? locale : new Locale("th", "TH"));
	}
}
