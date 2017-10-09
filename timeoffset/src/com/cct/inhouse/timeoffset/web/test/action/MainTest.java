package com.cct.inhouse.timeoffset.web.test.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainTest {
	
	public static void main(String[]args){
		
		
		try {
			
			String completionDate1 = "22/09/2017 16:30:44";
			 System.out.println(completionDate1);
			 DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 Date date = new Date();
			 date = df.parse(completionDate1);
			 DateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
			 System.out.println(df1.format(date));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static String convertOnsiteTimeToHourMinute(String onsiteTime,String dataType) throws Exception{
			
			String decimalPattern = "0.";
			String[] partition;
			int hour = 0;
			int minute = 0;
			
			if(!(dataType.equals("HOUR") || dataType.equals("MINUTE"))){
				throw new Exception();
			}
			
			if(Double.parseDouble(onsiteTime) == 0){
				return "0";
			}
			
			if(onsiteTime.indexOf(".") >= 0){
				partition = onsiteTime.split(".");
				hour = Integer.parseInt(partition[0]);
				minute = Integer.parseInt(decimalPattern .concat(partition[1])) * 60;
			}
			
			if(dataType.equals("HOUR")){
				return String.valueOf(hour);
			}else{
				return String.valueOf(minute);
			}
		}

}


