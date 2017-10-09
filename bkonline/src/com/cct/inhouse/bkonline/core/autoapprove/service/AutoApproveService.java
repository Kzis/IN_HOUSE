package com.cct.inhouse.bkonline.core.autoapprove.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.cct.common.CommonService;
import com.cct.inhouse.bkonline.core.autoapprove.domain.TimeForAutoApprove;
import com.cct.inhouse.bkonline.core.booking.roombooking.service.RoomBookingManager;
import com.cct.inhouse.bkonline.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.common.InhouseUser;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;

import util.database.CCTConnection;

public class AutoApproveService extends CommonService {

	private AutoApproveDAO dao = null;
	
	public AutoApproveService(Logger logger) {
		super(logger);
		this.dao = new AutoApproveDAO(logger, SQLPath.AUTO_APPROVE_SQL.getSqlPath());
	}

	/**
	 * ใช้สำหรับอัพเดดสถานะ Approve อัตโนมัติ
	 * @param conn
	 * @throws Exception
	 */
	protected void updateBookingStatusApproveByAuto(CCTConnection conn) throws Exception {
		
		// 1. search room id and time
		List<TimeForAutoApprove> listTime = dao.searchRoomIdForAutoApprove(conn);
		if (listTime.size() == 0) {
			return;
		}
		
		InhouseUser tempUser = new InhouseUser();
		tempUser.setId("-1");
		tempUser.setDisplayName("ระบบอัตโนมัติ");
		
		RoomBookingManager roomBookingManager = new RoomBookingManager(getLogger(), tempUser, ParameterConfig.getApplication().getApplicationLocale());
		
		// 2. search event id
		for (TimeForAutoApprove tt : listTime) {
			List<String> listEventId = dao.searchEventIdForAutoApprove(conn, tt.getRoomId(), tt.getAutotimeId());
			if (listEventId.size() == 0) {
				continue;
			}
			
			for (String eventId : listEventId) {
				try {
					roomBookingManager.updateBookingStatusApproveByAuto(conn, eventId);
				} catch (Exception e) {
					getLogger().error(e);
				}
			}
		}
	}
}
