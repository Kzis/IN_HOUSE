package com.cct.hrmdata.web.gettimeoffset.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;

import com.cct.hrmdata.core.config.domain.DBLookup;
import com.cct.hrmdata.core.gettimeoffset.domain.WorkOnsite;
import com.cct.hrmdata.core.gettimeoffset.service.GetTimeOffsetManager;

@WebService
public class GetTimeOffsetWebService {

	@WebMethod
	public List<WorkOnsite> getTimeOffset(String processDate, String userId) {
		List<WorkOnsite> response = new ArrayList<WorkOnsite>();

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			GetTimeOffsetManager manager = new GetTimeOffsetManager(conn, null);
			response = manager.searchWorkOnsite(processDate, userId);

		} catch (Exception e) {
			LogUtil.GET_TIME_OFFSET.error("", e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return response;
	}
}
