package test;

import javax.servlet.ServletContext;

public interface ParaCallBack {
	  public ServletContext methodToCallBackGetContext();
	  public void methodToCallBackSetContext(ServletContext servletContext);
}
