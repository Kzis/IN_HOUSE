package test.main;

import org.apache.log4j.PropertyConfigurator;

import com.cct.common.CommonMain;

public class TestBGMain extends CommonMain {

	@Override
	public String initProcessName() {
		return "TestBGMain";
	}

	@Override
	public void process(String[] arg0) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initLogConfig(String[] args) {
		// super.initLogConfig(args);
		PropertyConfigurator.configure("D:/Projects/neon3/inhouseprojectxx/bkonline/src/log4j.properties");
	}
	
	public static void main(String[] args) {
		new TestBGMain().start(args);
	}
}
