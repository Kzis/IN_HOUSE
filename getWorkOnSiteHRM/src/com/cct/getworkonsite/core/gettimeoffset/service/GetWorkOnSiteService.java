package com.cct.getworkonsite.core.gettimeoffset.service;

import java.rmi.RemoteException;

import util.database.CCTConnection;
import util.log.LogUtil;

import com.cct.domain.GlobalVariable;
import com.cct.getworkonsite.core.config.parameter.domain.SQLPath;
import com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServiceProxy;
import com.cct.hrmdata.web.gettimeoffset.ws.WorkOnsite;

public class GetWorkOnSiteService {

	private CCTConnection conn = null;
	private GetWorkOnSiteDAO dao = null;

	public GetWorkOnSiteService(CCTConnection conn) {
		this.conn = conn;
		dao = new GetWorkOnSiteDAO();
		dao.setSqlPath(SQLPath.GET_WORK_ONSITE);
	}

	protected String searchLastProcessDate() throws Exception {

		String processDate = null;
		try {
			// 1. get last process
			processDate = dao.searchLastProcessDate(conn);
		} catch (Exception e) {
			LogUtil.GET_WORK_ONSITE.error("", e);
		}

		return processDate;
	}

	protected WorkOnsite[] callWebService(String processDate, String userId) throws Exception {

		WorkOnsite[] arrWorkOnSite = null;
		int sizeArrWorkOnSite = 0;
		try {
			int lastWsProcessId = dao.insertLog(conn);
			LogUtil.GET_WORK_ONSITE.info("lastWsProcessId: " + lastWsProcessId);
			
			try {
				GetTimeOffsetWebServiceProxy service = new GetTimeOffsetWebServiceProxy();
				arrWorkOnSite = service.getTimeOffset(processDate, userId);
				
				if (arrWorkOnSite != null) {
					sizeArrWorkOnSite = arrWorkOnSite.length;
				}
				
				dao.updateLog(conn, GlobalVariable.PROCESS_STATUS_Y, null, sizeArrWorkOnSite, lastWsProcessId);
			} catch (RemoteException e1) {
				LogUtil.GET_WORK_ONSITE.error("", e1);
				dao.updateLog(conn, GlobalVariable.PROCESS_STATUS_N, e1.getMessage().toString(), sizeArrWorkOnSite, lastWsProcessId);
			}

		} catch (Exception e) {
			LogUtil.GET_WORK_ONSITE.error("", e);
		}

		return arrWorkOnSite;
	}

	protected void updateData(WorkOnsite[] workOnsite) throws Exception {

		int exec = 0;
		try {
			for (int i = 0; i < workOnsite.length; i++) {
				exec = dao.updateWorkOnSite(conn, workOnsite[i]);

				LogUtil.GET_WORK_ONSITE.debug("exec for update: " + exec);
				if (exec == 0) {
					exec = dao.insertWorkOnSite(conn, workOnsite[i]);
					LogUtil.GET_WORK_ONSITE.debug("exec for insert: " + exec);
				}
			}
		} catch (Exception e) {
			LogUtil.GET_WORK_ONSITE.error("", e);
		}

	}

}
