package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.log.DefaultLogUtil;

public class ProcessServlet extends HttpServlet {

	private static final long serialVersionUID = -5962385044405066997L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		try {
			DefaultLogUtil.INITIAL.debug("Get Process >>>>>>>>>>>>>>>>>> Begin");
			for (int i = 0; i < 2000000000; i++) {
				System.out.println("I > " + i);
			}
			DefaultLogUtil.INITIAL.debug("Get Process >>>>>>>>>>>>>>>>>> End");
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		try {
			DefaultLogUtil.INITIAL.debug("Post Process >>>>>>>>>>>>>>>>>> Begin");
			for (int i = 0; i < 20; i++) {
				Thread.sleep(1000);
			}
			DefaultLogUtil.INITIAL.debug("Post Process >>>>>>>>>>>>>>>>>> End");
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
	
	public void init() throws ServletException {
		try {
			DefaultLogUtil.INITIAL.debug("Create >>>>>>>>>>>>>>>>>> ");
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}

	@Override
	public void destroy() {
		try {
			DefaultLogUtil.INITIAL.debug("Destroy >>>>>>>>>>>>>>>>>> ");
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}
}
