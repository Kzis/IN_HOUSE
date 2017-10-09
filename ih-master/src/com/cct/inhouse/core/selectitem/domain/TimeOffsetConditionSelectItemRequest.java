package com.cct.inhouse.core.selectitem.domain;

import com.cct.common.CommonSelectItemRequest;

public class TimeOffsetConditionSelectItemRequest extends CommonSelectItemRequest {

	private static final long serialVersionUID = -6285936377717361452L;

	private String projectId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
