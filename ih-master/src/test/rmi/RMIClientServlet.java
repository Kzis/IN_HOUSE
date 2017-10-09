package test.rmi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.log.DefaultLogUtil;

public class RMIClientServlet extends HttpServlet {

	private static final long serialVersionUID = -1326931967347560900L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DefaultLogUtil.INITIAL.info("");
		try {
			init();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DefaultLogUtil.INITIAL.info("");
		try {
			init();
		} catch (Exception e) {
			DefaultLogUtil.INITIAL.error(e);
		}			
	}

	public void init() throws ServletException {
		try {
			new RMIClient().requestParameter();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
