package com.cct.getworkonsite.core.gettimeoffset.service;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;

import com.cct.getworkonsite.core.config.parameter.domain.DBLookup;
import com.cct.hrmdata.web.gettimeoffset.ws.WorkOnsite;

public class GetWorkOnSiteManager {

	private GetWorkOnSiteService service = null;

	public void process() {

		LogUtil.GET_WORK_ONSITE.info("process...");

		CCTConnection conn = null;
		String userId = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.MYSQL_INHOUSE.getLookup());

			service = new GetWorkOnSiteService(conn);
//			String processDate = service.searchLastProcessDate();
			String processDate = "20100101000000";
			LogUtil.GET_WORK_ONSITE.info("processDate: " + processDate);

			// 2. call webservice get work onsite from HRM
			WorkOnsite[] arrWorkOnSite = service.callWebService(processDate, userId);
			LogUtil.GET_WORK_ONSITE.debug("arrWorkOnSite: " + arrWorkOnSite);

			// 3. check arrWorkOnSite for insert/update to database
			if (arrWorkOnSite != null && arrWorkOnSite.length > 0) {
				LogUtil.GET_WORK_ONSITE.info("arrWorkOnSite.length: " + arrWorkOnSite.length);
				service.updateData(arrWorkOnSite);
			}
		} catch (Exception e) {
			LogUtil.GET_WORK_ONSITE.error("", e);
		} finally {
			CCTConnectionUtil.close(conn);
		}

	}

}
