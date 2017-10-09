package util.test;

import java.util.Locale;
import java.util.ResourceBundle;

import util.bundle.BundleUtil;
import util.string.StringUtil;
import util.type.DatabaseType.DbType;
import util.type.StringType.ResultType;

public class TestWork {

	public static void main(String[] args) {

		try{

			/**
			 * Random String.
			 *
			 * @param pattern : Format
			 * @param value : number
			 * @return String : number format
			 *
			 */
			//ResourceBundle bundle = BundleUtil.getBundle("util/number/Message", new Locale("th","TH"));
			//bundle.getString("one");


			String result = StringUtil.replaceSpecialString("บุรีรัมย์  พิญณุ'", DbType.ORA,ResultType.EMPTY);

			System.out.print(result);

			//String decodeStr =  DecryptionUtil.doDecrypt("dddd", DecType.AES128);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
