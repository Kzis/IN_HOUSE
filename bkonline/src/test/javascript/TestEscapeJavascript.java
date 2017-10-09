package test.javascript;

public class TestEscapeJavascript {

	public static void main(String[] args) {
		 String input = "at line 66' 67' 68";
		 System.out.println(input.length() + " > " + input);
		 System.out.println(input.replace("'", "\\'").length() + " > " + input.replace("'", "\\'"));
		 
		 

		 
		 StringBuilder stringBuilder = new StringBuilder();
		 for (int startIndex = 0; startIndex < input.length();) {
			 int endIndex = input.indexOf("'", startIndex);
			 if (endIndex == -1) {
				 endIndex = input.length();
				 stringBuilder.append(input.substring(startIndex, endIndex));
			 } else {
				 stringBuilder.append(input.substring(startIndex, endIndex));
				 stringBuilder.append("\\'");
			 }

			 startIndex = endIndex + 1;
			 System.out.println(startIndex);
			 System.out.println(stringBuilder.toString());
		 }
		 
		 // 20 > at line 66\' 67\' 68
//		 18 > at line 66' 67' 68
		 System.out.println(stringBuilder.length() + " > " + stringBuilder.toString());
	}
}
