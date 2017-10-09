package com.cct.inhouse.util.database;

import com.cct.database.CommonConnectionProvider;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;

import util.database.CCTConnection;

public class CCTConnectionProvider extends CommonConnectionProvider {

	public CCTConnectionProvider() throws Exception {
		super(ParameterConfig.getDatabase());
	}
	
	@Override
	public CCTConnection getConnection(CCTConnection conn, String lookup) throws Exception {
		return super.getConnection(conn, lookup);
	}
}
