package com.cct.inhouse.timeoffset.core.security.permission.service;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.domain.Operator;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.ParameterExtendedConfig;
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

		getLogger().debug("searchMenuAndFunction: " + ParameterExtendedConfig.getParameterExtended().getTimeOffsetConf().getSystemCode());
		
		Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();
		try {
			Operator operatorLevel = dao.searchOperatorLevel(conn, ParameterExtendedConfig.getParameterExtended().getTimeOffsetConf().getSystemCode());
			if (IHUtil.isDefaultUserId(userId)) {
				// เป็น default user โหลดสิทธิ์ทุกตัว
				mapOperator = dao.searchOperator(conn, ParameterExtendedConfig.getParameterExtended().getTimeOffsetConf().getSystemCode());
			} else {
				mapOperator = dao.searchOperatorByUserId(conn, userId, ParameterExtendedConfig.getParameterExtended().getTimeOffsetConf().getSystemCode());
			}
			
			for (String key : mapOperator.keySet()) {
				mapOperator.get(key).setMinLevel(operatorLevel.getMinLevel());
				mapOperator.get(key).setMaxLevel(operatorLevel.getMaxLevel());
				
				getLogger().debug(key + "(Min) : " + mapOperator.get(key).getMinLevel() );
				getLogger().debug(key + "(Max) : " + mapOperator.get(key).getMaxLevel() );
				getLogger().debug(key + "(Menu) : " + mapOperator.get(key).getMenuName() );
				
				break;
			}
			
		} catch (Exception e) {
			throw e;
		}

		return mapOperator;
	}


}