package com.cct.inhouse.central.util.database;

import com.cct.database.CommonConnectionProvider;
import com.cct.inhouse.central.core.config.parameter.domain.CParameterConfig;

import util.database.CCTConnection;

public class CConnectionProvider extends CommonConnectionProvider {

	public CConnectionProvider() throws Exception {
		super(CParameterConfig.getParameter().getDatabase());
	}
	
	@Override
	public CCTConnection getConnection(CCTConnection conn, String lookup) throws Exception {
		return super.getConnection(conn, lookup);
	}
}
