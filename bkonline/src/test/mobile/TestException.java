package test.mobile;

import com.cct.exception.AuthenticateException;

public class TestException {
	public static void main(String[] args) {
		try {
			new TestException().methodC();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception");
		}
	}
	public void methodA() throws AuthenticateException {
		throw new AuthenticateException();
	}
	public void methodB() throws Exception {
		throw new Exception();
	}
	public void methodC() throws Exception {
		try {
//			methodA();
			methodB();
		} catch (AuthenticateException ae) {
			System.out.println("AuthenticateException");	
		}
		
		
	}
}

