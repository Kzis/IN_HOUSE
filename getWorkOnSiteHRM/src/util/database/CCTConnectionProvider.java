package util.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.cct.getworkonsite.core.config.parameter.domain.Database;
import com.cct.getworkonsite.core.config.parameter.domain.ParameterConfig;

public class CCTConnectionProvider {

	public static boolean INIT = false;
	private static Map<String, DataSource> ds = new HashMap<String, DataSource>();

	public CCTConnectionProvider() throws Exception {
		init();
	}

	private void init() throws Exception {
		if (INIT == false) {
			try {
				ds.clear();
				for (int i = 0; i < ParameterConfig.getDbParameter().getDatabase().length; i++) {
					Database config = ParameterConfig.getDbParameter().getDatabase()[i];

					Properties dbcpProperties = new Properties();
					dbcpProperties.setProperty("driverClassName", config.getDriver());
					dbcpProperties.setProperty("url", config.getUrl());
					dbcpProperties.setProperty("username", config.getUser());
					dbcpProperties.setProperty("password", config.getPassword());
					dbcpProperties.setProperty("maxActive", config.getMaxActive());
					dbcpProperties.setProperty("maxIdle", config.getMaxIdle());
					dbcpProperties.setProperty("maxWait", config.getMaxWait());

					DataSource ddss = BasicDataSourceFactory.createDataSource(dbcpProperties);
					ds.put(config.getIndex(), ddss);
				}
				INIT = true;
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public CCTConnection getConnection(CCTConnection conn, String lookup) throws Exception {

		try {
			if (conn == null) {
				conn = new CCTConnection();
			}

			if ((conn.getConn() == null) || conn.getConn().isClosed()) {
				conn.setConn(ds.get(lookup).getConnection());
				conn.getSchemas().clear();
				conn.getSchemas().putAll(ParameterConfig.getDbParameter().getDatabase()[Integer.parseInt(lookup)].getSchemasMap());
				conn.setDbType(ParameterConfig.getDbParameter().getDatabase()[Integer.parseInt(lookup)].getDatabaseTypeEnum());
			}
		} catch (Exception e) {
			throw e;
		}

		return conn;
	}
}
