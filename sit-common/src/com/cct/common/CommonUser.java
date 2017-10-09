package com.cct.common;

import java.io.Serializable;
import java.util.Locale;

public class CommonUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 97409387321521105L;
	
	private String id;
	private Locale locale;
	private String givenName;
	private String familyName;

	public String getFullname() {
		String fullname = "";
		if ((getFamilyName() != null) && (!getFamilyName().isEmpty())) {
			fullname = getFamilyName();
		}
		
		if ((getGivenName() != null) && (!getGivenName().isEmpty())) {
			if (!fullname.isEmpty())  {
				fullname += ",";
			}
			
			fullname += getGivenName();
		}
		return fullname;
	}
	
	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
