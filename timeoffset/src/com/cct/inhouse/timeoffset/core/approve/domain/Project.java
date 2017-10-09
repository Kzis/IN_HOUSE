package com.cct.inhouse.timeoffset.core.approve.domain;

import com.cct.inhouse.common.InhouseDomain;

public class Project extends InhouseDomain {

	private static final long serialVersionUID = 3938709078038881013L;

	private String project;
	private String time;
	private String hr;
	private String min;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

}
