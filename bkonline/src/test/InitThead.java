package test;

import javax.servlet.ServletException;

import com.cct.inhouse.web.config.parameter.servlet.ParameterServlet;

public class InitThead extends Thread {
	
	private ParameterServlet p;
	public InitThead(ParameterServlet p) {
		this.p = p;
	}
	
	@Override
	public void run() {
		try {
			p.init();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}