package com.cct.inhouse.timeoffset.core.condition.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonUser;
import com.cct.exception.MaxExceedException;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectCondition;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectConditionSearch;
import com.cct.inhouse.timeoffset.core.condition.domain.ProjectConditionSearchCriteria;

import util.database.CCTConnection;

public class ProjectConditionManager extends AbstractManager<ProjectConditionSearchCriteria, ProjectConditionSearch, ProjectCondition, CommonUser, Locale> {

	ProjectConditionService service = null;

	public ProjectConditionManager(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new ProjectConditionService(logger, user, locale);
	}

	@Override
	public long add(CCTConnection conn, ProjectCondition obj) throws Exception {
		return service.add(conn, obj);
	}
	
	@Override
	public int delete(CCTConnection conn, String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(CCTConnection conn, ProjectCondition obj) throws Exception {
		return service.edit(conn, obj);
	}

	@Override
	public List<ProjectConditionSearch> search(CCTConnection conn, ProjectConditionSearchCriteria criteria) throws Exception {

		List<ProjectConditionSearch> listResult = new ArrayList<ProjectConditionSearch>();

		try {

			criteria.setTotalResult(service.countData(conn, criteria));

			if (criteria.getTotalResult() == 0) {

			} else if ((criteria.isCheckMaxExceed()) && (criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceed())) {
				throw new MaxExceedException();
			} else {
				// ค้นหาข้อมูล
				listResult = service.search(conn, criteria);
			}

		} catch (Exception e) {
			throw e;
		}

		return listResult;
	}

	@Override
	public ProjectCondition searchById(CCTConnection conn, String id) throws Exception {
		return service.searchById(conn, id);
	}

	@Override
	public int updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception {
		return service.updateActive(conn, ids, activeFlag);
	}

}
