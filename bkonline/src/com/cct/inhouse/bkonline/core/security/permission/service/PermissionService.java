package com.cct.inhouse.bkonline.core.security.permission.service;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.domain.Operator;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.util.IHUtil;

import util.database.CCTConnection;

public class PermissionService extends AbstractService {

	private PermissionDAO dao = null;

	public PermissionService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new PermissionDAO(logger, null);
	}
	
	/**
	 * ค้นหารายละเอียดเมนูและสิทธิ์
	 * 
	 * @param operatorIds
	 * @param locale
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Operator> searchMenuAndFunction(CCTConnection conn, String userId) throws Exception {

		Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();
		try {
			Operator operatorLevel = dao.searchOperatorLevel(conn, ParameterConfig.getApplication().getSystemCode());
			if (IHUtil.isDefaultUserId(userId)) {
				// เป็น default user โหลดสิทธิ์ทุกตัว
				mapOperator = dao.searchOperator(conn, ParameterConfig.getApplication().getSystemCode());
			} else {
				mapOperator = dao.searchOperatorByUserId(conn, userId, ParameterConfig.getApplication().getSystemCode());
			}
			
			for (String key : mapOperator.keySet()) {
				mapOperator.get(key).setMinLevel(operatorLevel.getMinLevel());
				mapOperator.get(key).setMaxLevel(operatorLevel.getMaxLevel());
				break;
			}
		} catch (Exception e) {
			throw e;
		}

		return mapOperator;
	}

}