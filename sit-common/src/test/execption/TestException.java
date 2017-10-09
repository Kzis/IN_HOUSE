package test.execption;

import com.cct.exception.DefaultExceptionMessage;

public class TestException {

	public static void main(String[] args) {
		System.out.println(TestDefaultExceptionMessage.NOT_FOUND);
		System.out.println(TestDefaultExceptionMessage.AUTHENTICATE);
		TestDefaultExceptionMessage.AUTHENTICATE = "1234";
		System.out.println(DefaultExceptionMessage.AUTHENTICATE);
		System.out.println(TestDefaultExceptionMessage.AUTHENTICATE);
	}
}
