package com.cct.inhouse.timeoffset.core.security.permission.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractManager;
import com.cct.domain.Operator;
import com.cct.inhouse.common.InhouseUser;

import util.database.CCTConnection;

public class PermissionManager extends AbstractManager<Object, Object, Object, InhouseUser, Locale> {

	private PermissionService service = null;

	public PermissionManager(Logger logger, InhouseUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new PermissionService(logger, user, locale);
	}

	/**
	 * Process login
	 * @param config
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Map<String, Operator> searchMenuAndFunction(CCTConnection conn, String userId) throws Exception {
		//หาข้อมูลสิทธิ์
		return service.searchMenuAndFunction(conn, userId);
	}

	@Override
	public long add(CCTConnection arg0, Object arg1) throws Exception {
		return 0;
	}

	@Override
	public int delete(CCTConnection arg0, String arg1) throws Exception {
		return 0;
	}

	@Override
	public int edit(CCTConnection arg0, Object arg1) throws Exception {
		return 0;
	}

	@Override
	public List<Object> search(CCTConnection arg0, Object arg1) throws Exception {
		return null;
	}

	@Override
	public Object searchById(CCTConnection arg0, String arg1) throws Exception {
		return null;
	}

	@Override
	public int updateActive(CCTConnection arg0, String arg1, String arg2) throws Exception {
		return 0;
	}

}
