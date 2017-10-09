package util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestDate {
	public static void main(String[] args) {
		try {
			String trDate="25550106";
			Date tradeDate = new SimpleDateFormat("yyyymmdd", new Locale("th","TH")).parse(trDate);
			String krwtrDate = new SimpleDateFormat("yyyy-mm-dd", new Locale("th","TH")).format(tradeDate);
			Date krwTradeDate = new SimpleDateFormat("yyyy-mm-dd", new Locale("th","TH")).parse(krwtrDate);


			Date curdate = new Date();
			System.out.println(DateUtil.dateToString(curdate, DateFormatUtil.DDMMYYYY.toString(), "th"));


			System.out.println(DateUtil.stringToDate("13/02/2013", DateFormatUtil.DDMMYYYY.toString(), "en"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
