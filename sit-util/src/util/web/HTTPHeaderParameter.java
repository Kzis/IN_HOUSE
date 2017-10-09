package util.web;

public enum HTTPHeaderParameter {
	
	X_FORWARDED_FOR("X-FORWARDED-FOR")
	, UNKNOWN("unknown")
	, PROXY_CLIENT_IPADDRESS("Proxy-Client-ipAddress")
	, WL_PROXY_CLIENT_IPADDRESS("WL-Proxy-Client-ipAddress")
	, HTTP_CLIENT_IPADDRESS("HTTP_CLIENT_ipAddress")
	, HTTP_X_FORWARDED_FOR("HTTP_X_FORWARDED_FOR")
	;
	
	private String value;

	private HTTPHeaderParameter(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
