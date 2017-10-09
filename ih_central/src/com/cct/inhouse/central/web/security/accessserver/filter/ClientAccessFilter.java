package com.cct.inhouse.central.web.security.accessserver.filter;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import util.log.DefaultLoggerName;
import util.string.StringDelimeter;
import util.web.HTTPHeaderParameter;
import util.web.SessionUtil;

public class ClientAccessFilter implements Filter {
	
	private Logger logger = Logger.getLogger(DefaultLoggerName.FILTER.getValue());
	private String ipAccess = null;
	private String hostnameAccess = null;
	
	public void init(FilterConfig config) throws ServletException {
		// Get init parameter
		ipAccess = config.getInitParameter("ip-access");
		hostnameAccess = config.getInitParameter("hostname-access");

		// Print the init parameter
		getLogger().info("ipAccess: " + ipAccess);
		getLogger().info("hostnameAccess: " + hostnameAccess);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {

		boolean accessIp = false;
		boolean accessHostname = false;
		
		// Get the IP address of client machine.
		String ipAddress = getIPAddress(request);
		String hostnameAddress = "";//getIPHostname(request);

		getLogger().debug("ipAddress: " + ipAddress + ", hostnameAddress: " + hostnameAddress);
		getLogger().debug("ipAddress: " + request.getRemoteAddr() + ", hostnameAddress: " + request.getRemoteHost());
		
		if (ipAccess.equals(StringDelimeter.STAR.getValue())) {
			// * is allow all request
			chain.doFilter(request, response);
			return;
		}
		
		// Log the IP address and current timestamp.
		if (ipAccess.equals(ipAddress)) {
			getLogger().debug("IP " + ipAddress + ", Time " + new Date().toString() + ", Can access.");
			accessIp = true;
		}

		if (hostnameAccess.equals(hostnameAddress)) {
			getLogger().debug("Hostname " + hostnameAccess + ", Time " + new Date().toString() + ", Can access.");
			accessHostname = true;
		}
		
		if (accessIp || accessHostname) {
			// Pass request back down the filter chain
			chain.doFilter(request, response);
		} else {
			// No permission
			getLogger().debug("IP " + ipAddress + ", Time " + new Date().toString() + ", No permission.");
			response.getWriter().print("No Permission to Access.");
			response.getWriter().flush();
		}
	}

	public void destroy() {
		/*
		 * Called before the Filter instance is removed from service by the web container
		 */
	}
	
	public String getIPAddress(ServletRequest requestx) {

		HttpServletRequest request = (HttpServletRequest) requestx;
		/**
		Enumeration<String> header = request.getHeaderNames();
		while (header.hasMoreElements()) {
			String key = header.nextElement();
			System.out.println(key + " > " + request.getHeader(key));
		}
		
		System.out.println(HTTPHeaderParameter.X_FORWARDED_FOR.getValue() + " > " + request.getHeader(HTTPHeaderParameter.X_FORWARDED_FOR.getValue()));
		System.out.println(HTTPHeaderParameter.PROXY_CLIENT_IPADDRESS.getValue() + " > " + request.getHeader(HTTPHeaderParameter.PROXY_CLIENT_IPADDRESS.getValue()));
		System.out.println(HTTPHeaderParameter.WL_PROXY_CLIENT_IPADDRESS.getValue() + " > " + request.getHeader(HTTPHeaderParameter.WL_PROXY_CLIENT_IPADDRESS.getValue()));
		System.out.println(HTTPHeaderParameter.HTTP_CLIENT_IPADDRESS.getValue() + " > " + request.getHeader(HTTPHeaderParameter.HTTP_CLIENT_IPADDRESS.getValue()));
		System.out.println(HTTPHeaderParameter.HTTP_X_FORWARDED_FOR.getValue() + " > " + request.getHeader(HTTPHeaderParameter.HTTP_X_FORWARDED_FOR.getValue()));
		System.out.println(HTTPHeaderParameter.UNKNOWN.getValue() + " > " + request.getHeader(HTTPHeaderParameter.UNKNOWN.getValue()));
		*/
		String ipAddress = SessionUtil.getRemoteIPAddress(request);
		/**
		String ipAddress = request.getHeader(HTTPHeaderParameter.X_FORWARDED_FOR.getValue());
		try {
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.PROXY_CLIENT_IPADDRESS.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.WL_PROXY_CLIENT_IPADDRESS.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.HTTP_CLIENT_IPADDRESS.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.HTTP_X_FORWARDED_FOR.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
			}
		} catch (Exception e) {
			getLogger().error(e);
		}
		**/
		return ipAddress;
	}

	public String getIPHostname(ServletRequest requestx) {

		HttpServletRequest request = (HttpServletRequest) requestx;

		Enumeration<String> header = request.getHeaderNames();
		while (header.hasMoreElements()) {
			String key = header.nextElement();
			System.out.println(key + " > " + request.getHeader(key));
		}
		
		String ipAddress = request.getHeader(HTTPHeaderParameter.X_FORWARDED_FOR.getValue());
		try {
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.PROXY_CLIENT_IPADDRESS.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.WL_PROXY_CLIENT_IPADDRESS.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.HTTP_CLIENT_IPADDRESS.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader(HTTPHeaderParameter.HTTP_X_FORWARDED_FOR.getValue());
			}
			if (ipAddress == null || ipAddress.isEmpty() || HTTPHeaderParameter.UNKNOWN.getValue().equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
			}
		} catch (Exception e) {
			getLogger().error(e);
		}
		return ipAddress;
	}
	
	public Logger getLogger() {
		return logger;
	}
}