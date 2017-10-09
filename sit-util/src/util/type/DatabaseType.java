package util.type;

public class DatabaseType {
	public enum DbType{
		ORA("ORACLE"),	MYSQL("MYSQL"),	DB2("DB2") , POSTGRESQL("POSTGRESQL");
		
		private String value;

		private DbType(String value) {
			this.value = value;
		}
	
		public String getValue() {
			return value;
		}
	}

	
}
