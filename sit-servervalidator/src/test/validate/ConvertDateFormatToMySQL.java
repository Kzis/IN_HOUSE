package test.validate;

import com.cct.core.validate.domain.ValidatorConstant;

public class ConvertDateFormatToMySQL {

	public static void main(String[] args) {
		
		try {
			String mysqlFormat = ValidatorConstant.DEFAULT_DATE_FORMAT.replace("DD", "%d").replace("MM", "%m").replace("YYYY", "%Y");
			System.out.println(mysqlFormat);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
