package util.string;

public class TestRandom {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringUtil random = new StringUtil();
		try {
			String password = random.stringToNull("  ");
			System.out.println("password : " + password);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
