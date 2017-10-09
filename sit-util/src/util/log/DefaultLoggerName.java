package util.log;

public enum DefaultLoggerName {

	INITIAL("INITIAL"), SELECTOR("SELECTOR"), COMMON("COMMON")
	, FILTER("FILTER"), UTIL("UTIL"), INTERCEPTOR("INTERCEPTOR"), SERVER_VALIDATION("SERVER_VALIDATION")
	;

	private String value;

	private DefaultLoggerName(String value) {
		this.value = value;
	}
		
	public String getValue() {
		return value;
	}
}
