package com.cct.inhouse.bkonline.web.component.action;

import org.apache.log4j.Logger;

import com.cct.inhouse.bkonline.util.log.LogUtil;
import com.cct.inhouse.central.rmi.domain.SelectItemMethod;
import com.cct.inhouse.central.rmi.service.SelectItemRMIProviders;
import com.cct.inhouse.common.InhouseAction;
import com.cct.inhouse.core.standard.department.domain.Department;
import com.cct.inhouse.enums.DBLookup;
import com.opensymphony.xwork2.ModelDriven;

public class ComponentAction extends InhouseAction implements ModelDriven<ComponentModel> {

	private static final long serialVersionUID = 2515933382004334854L;
	
	private ComponentModel model = new ComponentModel();
	
	public ComponentAction() {
		getComboForSearch();
	}
	
	public String init() {
		return "init";
	}
	
	public String gotoo() {
		
		return "gotoo";
	}
	
	public void getComboForSearch() {
		try {
			getModel().setListAllQAUserSelectItem(
					SelectItemRMIProviders.requestSelectItem(
							getUser()
							, getLocale()
							, SelectItemMethod.SEARCH_USER_FULL_DISPLAY_SELECT_ITEM
							, null
					)
			);
			getLogger().debug("AllQAUserSelectItem: " + getModel().getListAllQAUserSelectItem().size());
			
			
			
			// สร้าง Object request
			Department departmentCriteria = new Department();
			getModel().setListDepartmentSelectItem(
					SelectItemRMIProviders.requestSelectItemObject(
							getUser()
							, getLocale()
							, SelectItemMethod.SEARCH_DEPARTMENT_SELECT_ITEM_OBJ
							, departmentCriteria
					)
			);
			getLogger().debug("DepartmentSelectItem: " + getModel().getListDepartmentSelectItem().size());
					
			
			
			// สร้าง Object request
			Department employeeFullnameCriteria = new Department();
			employeeFullnameCriteria.setDepartmentId("5"); // Filter with Programmer
			getModel().setListEmployeeFullnameByDepartmentIdSelectItem(
					SelectItemRMIProviders.requestSelectItem(
							getUser()
							, getLocale()
							, SelectItemMethod.SEARCH_EMPLOYEE_BY_DEPARTMENT_ID
							, employeeFullnameCriteria
					)
			);
			getLogger().debug("EmployeeFullnameByDepartmentIdSelectItem: " + getModel().getListEmployeeFullnameByDepartmentIdSelectItem().size());
			
			
			
			getModel().setListProjectsSelectItem(
					SelectItemRMIProviders.requestSelectItem(
							getUser()
							, getLocale()
							, SelectItemMethod.SEARCH_PROJECTS_SELECT_ITEM
					)
			);
			getLogger().debug("ProjectsSelectItem: " + getModel().getListProjectsSelectItem().size());
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	@Override
	public Logger loggerInititial() {
		return LogUtil.INITIAL;
	}

	
	@Override
	public ComponentModel getModel() {
		return model;
	}
	
	@Override
	public String dbLookupInititial() {
		return DBLookup.MYSQL_INHOUSE.getLookup();
	}
}
