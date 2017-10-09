package com.cct.inhouse.core.config.parameter.domain;



public class DateFormat extends MergeParameter {

	private static final long serialVersionUID = -7829158200049419117L;

	private String forDatabaseInsert;
	private String forDatabaseInsertHHMM;
	private String forDatabaseInsertHHMMSS;
	
	private String forDatabaseSelect;
	private String forDatabaseSelectHHMM;
	private String forDatabaseSelectHHMMSS;
	
	private String forDisplay;
	private String forDisplayHHMM;
	private String forDisplayHHMMSS;

	public DateFormat clone() {
		DateFormat cloneObj = new DateFormat();
		
		// setter/getter
		cloneObj.merge(this);
		
		return cloneObj;
	}
	
	public String getForDisplay() {
		return forDisplay;
	}

	public void setForDisplay(String forDisplay) {
		this.forDisplay = forDisplay;
	}

	public String getForDisplayHHMM() {
		return forDisplayHHMM;
	}

	public void setForDisplayHHMM(String forDisplayHHMM) {
		this.forDisplayHHMM = forDisplayHHMM;
	}

	public String getForDisplayHHMMSS() {
		return forDisplayHHMMSS;
	}

	public void setForDisplayHHMMSS(String forDisplayHHMMSS) {
		this.forDisplayHHMMSS = forDisplayHHMMSS;
	}

	public String getForDatabaseSelect() {
		return forDatabaseSelect;
	}

	public void setForDatabaseSelect(String forDatabaseSelect) {
		this.forDatabaseSelect = forDatabaseSelect;
	}

	public String getForDatabaseSelectHHMM() {
		return forDatabaseSelectHHMM;
	}

	public void setForDatabaseSelectHHMM(String forDatabaseSelectHHMM) {
		this.forDatabaseSelectHHMM = forDatabaseSelectHHMM;
	}

	public String getForDatabaseSelectHHMMSS() {
		return forDatabaseSelectHHMMSS;
	}

	public void setForDatabaseSelectHHMMSS(String forDatabaseSelectHHMMSS) {
		this.forDatabaseSelectHHMMSS = forDatabaseSelectHHMMSS;
	}

	public String getForDatabaseInsert() {
		return forDatabaseInsert;
	}

	public void setForDatabaseInsert(String forDatabaseInsert) {
		this.forDatabaseInsert = forDatabaseInsert;
	}

	public String getForDatabaseInsertHHMM() {
		return forDatabaseInsertHHMM;
	}

	public void setForDatabaseInsertHHMM(String forDatabaseInsertHHMM) {
		this.forDatabaseInsertHHMM = forDatabaseInsertHHMM;
	}

	public String getForDatabaseInsertHHMMSS() {
		return forDatabaseInsertHHMMSS;
	}

	public void setForDatabaseInsertHHMMSS(String forDatabaseInsertHHMMSS) {
		this.forDatabaseInsertHHMMSS = forDatabaseInsertHHMMSS;
	}

}
