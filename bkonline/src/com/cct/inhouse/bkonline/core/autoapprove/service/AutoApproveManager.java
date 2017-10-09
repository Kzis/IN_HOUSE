package com.cct.inhouse.bkonline.core.autoapprove.service;

import org.apache.log4j.Logger;

import com.cct.common.CommonManager;

import util.database.CCTConnection;

public class AutoApproveManager extends CommonManager {

	private AutoApproveService service = null;
	
	public AutoApproveManager(Logger logger) {
		super(logger);
		this.service = new AutoApproveService(logger);
	}

	/**
	 * ใช้สำหรับอัพเดดสถานะ Approve อัตโนมัติ
	 * @param conn
	 * @throws Exception
	 */
	public void updateBookingStatusApproveByAuto(CCTConnection conn) throws Exception {
		service.updateBookingStatusApproveByAuto(conn);
	}
}
