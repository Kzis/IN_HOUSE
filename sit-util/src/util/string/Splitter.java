package util.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.type.SplitterType.Delimiter;
import util.type.SplitterType.TextQualifier;

public class Splitter {
	
	public static void main(String[] args) {
		
		String data = "j1:'j2:j3':j4:j5:j6:j7:j8";
		
		List<String> result = Splitter.splitDelimiter(Delimiter.COLON, TextQualifier.SINGLE_QOUTE, data);
		
		for (int i = 0; i < result.size(); i++) {
			System.out.println("index [" + i + "] >>> " + result.get(i));
		}
		
	}
	

	/**
	 * 
	 * @param delimiter
	 * @param textQualifier
	 * @return
	 */
	public static List<String> splitDelimiter(Delimiter delimiter, TextQualifier textQualifier, String data) {
		
		if(data == null || data.isEmpty() || delimiter == null || textQualifier == null) {
			return null;
		}
		
		String[] splitStr = null;
		List<String> result = new ArrayList<String>();
		
		//if textQualifier is empty just split with delimiter and convert into list
		if(textQualifier.getValue().equals(TextQualifier.NONE.getValue())) {
			
			splitStr = data.split(delimiter.getValue());
			result = Arrays.asList(splitStr);
			
		} else {
			
			splitStr = data.split(textQualifier.getValue());
			
			for (String str : splitStr) {
				
				str = str.trim();
				
				if(!str.isEmpty() && !str.equals(delimiter.getValue())) {
					
					if(str.startsWith(delimiter.getValue()) || str.endsWith(delimiter.getValue())) {
						
						//substring if the first char of this string is a delimiter
						str = str.charAt(0) == delimiter.getValue().charAt(0) ? str.substring(1, str.length()):str;
						
						//substring if the last char of this string is a delimiter
						str = str.charAt(str.length() - 1) == delimiter.getValue().charAt(0) ? str.substring(0, str.length() - 1):str;
						
						//convert into list and add to result
						result.addAll(Arrays.asList(str.split(delimiter.getValue())));
						
					} else {
						result.add(str);
					}
				}
			}
		}
		
		return result;
	}
}
