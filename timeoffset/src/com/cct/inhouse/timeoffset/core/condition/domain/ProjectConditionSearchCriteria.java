package com.cct.inhouse.timeoffset.core.condition.domain;

import com.cct.domain.HeaderSorts;
import com.cct.domain.SearchCriteria;

public class ProjectConditionSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = 5555455312476437409L;

	private String projectId;
	private String activeStatus;

	public ProjectConditionSearchCriteria() {
		
		HeaderSorts[] arrayHeaderSorts = { 
				new HeaderSorts("", HeaderSorts.ASC, "0"), 
				new HeaderSorts("", HeaderSorts.ASC, "0"), 
				new HeaderSorts("", HeaderSorts.ASC, "0"), 
				new HeaderSorts("PROJECT_ID", HeaderSorts.ASC, "0"),
				new HeaderSorts("", HeaderSorts.ASC, "0"),
				new HeaderSorts("", HeaderSorts.ASC, "0"), 
				new HeaderSorts("", HeaderSorts.ASC, "0"), 
		};
		
		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	// กำหนด default header sorts ให้สำหรับการกด search ครั้งแรก
	public void setDefaultHeaderSorts() {
		
		setHeaderSortsSelect("3");
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

}
