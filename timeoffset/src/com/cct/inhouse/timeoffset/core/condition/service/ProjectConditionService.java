package com.cct.inhouse.timeoffset.core.condition.service;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectCondition;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectConditionSearch;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectConditionSearchCriteria;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.SQLPath;

import util.database.CCTConnection;

public class ProjectConditionService extends AbstractService {

	private ProjectConditionDAO dao = null;

	public ProjectConditionService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new ProjectConditionDAO(logger, SQLPath.TO_CONDITION_PROJ.getSqlPath(), user, locale);
	}

	protected long countData(CCTConnection conn, ProjectConditionSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria);
	}

	protected List<ProjectConditionSearch> search(CCTConnection conn, ProjectConditionSearchCriteria criteria) throws Exception {
		return dao.search(conn, criteria);
	}

	protected ProjectCondition searchById(CCTConnection conn, String id) throws Exception {
		return dao.searchById(conn, id);
	}

	protected long add(CCTConnection conn, ProjectCondition obj) throws Exception {
		
		//Check ว่าข้อมูลซ้ำหรือไม่
		dao.checkDup(conn, obj);
		
		return dao.add(conn, obj);
	}

	protected int edit(CCTConnection conn, ProjectCondition obj) throws Exception {
		
		//Check ว่าข้อมูลซ้ำหรือไม่
		dao.checkDup(conn, obj);
		
		return dao.edit(conn, obj);
	}

	protected int updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception {
		return dao.updateActive(conn, ids, activeFlag);
	}
	
}
