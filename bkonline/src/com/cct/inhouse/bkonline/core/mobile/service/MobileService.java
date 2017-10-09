package com.cct.inhouse.bkonline.core.mobile.service;

import org.apache.log4j.Logger;

import util.database.CCTConnection;

import com.cct.common.CommonService;
import com.cct.exception.AuthenticateException;
import com.cct.inhouse.bkonline.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.bkonline.core.mobile.domain.MDUser;

public class MobileService extends CommonService {

	private MobileDAO dao = null;
	
	public MobileService(Logger logger) {
		super(logger);
		this.dao = new MobileDAO(logger, SQLPath.MOBILE_SQL.getSqlPath());
	}

	protected void deleteDeviceInfo(CCTConnection conn, String deviceId) throws Exception {
		dao.deleteDeviceInfo(conn, deviceId);
	}
	
	protected void addDeviceInfo(CCTConnection conn, String userId, String deviceId, String plateForm, String tokenId)throws Exception {
		dao.addDeviceInfo(conn, userId, deviceId, plateForm, tokenId);
	}

	protected void checkTokenId(CCTConnection conn, MDUser mdUser)throws Exception {
		boolean chkTokenId = dao.checkTokenId(conn,mdUser);
		if(!chkTokenId){
			throw new AuthenticateException();
		}
	}
}
