package test;


public class CallbackTest {

	public static void main(String[] args) {
		Caller caller = new Caller();
		CallBack callBack = new CallBackImpl();
		caller.register(callBack);
	}
}