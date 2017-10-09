package com.cct.inhouse.core.config.parameter.domain;



public class Autocomplete extends MergeParameter {
	private static final long serialVersionUID = -1667053248276968328L;
	
	private int fillAtLeast;
	
	public Autocomplete clone() {
		Autocomplete cloneObj = new Autocomplete();
		
		// setter/getter
		cloneObj.merge(this);
		
		return cloneObj;
	}

	public int getFillAtLeast() {
		return fillAtLeast;
	}

	public void setFillAtLeast(int fillAtLeast) {
		this.fillAtLeast = fillAtLeast;
	}

}
