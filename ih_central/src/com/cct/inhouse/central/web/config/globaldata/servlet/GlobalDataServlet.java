package com.cct.inhouse.central.web.config.globaldata.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cct.inhouse.central.core.selectitem.service.SelectItemManager;
import com.cct.inhouse.central.util.database.CConnectionProvider;
import com.cct.inhouse.central.util.log.LogUtil;
import com.cct.inhouse.enums.DBLookup;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.string.StringDelimeter;

public class GlobalDataServlet extends HttpServlet {

	private static final long serialVersionUID = -1326931967347560900L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.INITIAL.info(StringDelimeter.EMPTY.getValue());
		try {
			init();
		} catch (Exception e) {
			LogUtil.INITIAL.error(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogUtil.INITIAL.info(StringDelimeter.EMPTY.getValue());
		try {
			init();
		} catch (Exception e) {
			LogUtil.INITIAL.error(e);
		}
	}

	public void init() throws ServletException {

		CCTConnection conn = null;
		try {
			// Connect database
			conn = new CConnectionProvider().getConnection(conn
					, DBLookup.MYSQL_INHOUSE.getLookup());

			LogUtil.INITIAL.debug("Initial global data");
			// โหลด GlobalData ผ่าน Method SelectItemManager.init()
			SelectItemManager selectItemManager = new SelectItemManager(LogUtil.INITIAL);
			selectItemManager.init(conn);
			LogUtil.INITIAL.debug("Initial global data completed.");
			
		} catch (Exception e) {
			LogUtil.INITIAL.error("Can't load global data!!!", e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
}
