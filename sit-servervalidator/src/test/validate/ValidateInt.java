package test.validate;

public class ValidateInt {

	public static void main(String[] args) {
		Object x = Integer.MAX_VALUE;
		Object y = Long.MAX_VALUE ;
		try {
			int xx = Integer.valueOf(x.toString());
			System.out.println(xx);
			
			int yy = Integer.valueOf(y.toString());
			System.out.println(yy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
