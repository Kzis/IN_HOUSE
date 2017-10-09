package test.validate;

public class ValidateString {
	
	public static void main(String[] args) {
		
		try {
			Object x = null;
			String xx = String.valueOf(x);
			System.out.println(xx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
