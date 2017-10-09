package com.cct.inhouse.timeoffset.core.condition.domain;

import java.util.ArrayList;
import java.util.List;

import com.cct.common.CommonSelectItem;
import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;

public class ProjectConditionModel extends InhouseModel {

	private static final long serialVersionUID = -1580874666974457357L;

	private ProjectConditionSearchCriteria criteria = new ProjectConditionSearchCriteria();

	private ProjectCondition projectCondition = new ProjectCondition();

	private List<CommonSelectItem> listProject = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listActiveStatus = new ArrayList<CommonSelectItem>();


	@Override
	public ProjectConditionSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (ProjectConditionSearchCriteria)criteria;
	}



	public List<CommonSelectItem> getListActiveStatus() {
		return listActiveStatus;
	}

	public void setListActiveStatus(List<CommonSelectItem> listActiveStatus) {
		this.listActiveStatus = listActiveStatus;
	}

	public List<CommonSelectItem> getListProject() {
		return listProject;
	}

	public void setListProject(List<CommonSelectItem> listProject) {
		this.listProject = listProject;
	}

	public ProjectCondition getProjectCondition() {
		return projectCondition;
	}

	public void setProjectCondition(ProjectCondition projectCondition) {
		this.projectCondition = projectCondition;
	}
	
	

}
