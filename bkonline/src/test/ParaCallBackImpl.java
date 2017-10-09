package test;

import javax.servlet.ServletContext;

public class ParaCallBackImpl implements ParaCallBack {

	private ServletContext servletContext;
	
	@Override
	public ServletContext methodToCallBackGetContext() {
		return servletContext;
	}

	@Override
	public void methodToCallBackSetContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
