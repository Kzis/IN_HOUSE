package com.cct.inhouse.timeoffset.core.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractManager;
import com.cct.common.CommonUser;
import com.cct.exception.MaxExceedException;
import com.cct.inhouse.core.config.parameter.domain.ParameterConfig;
import com.cct.inhouse.timeoffset.core.todo.domain.Todo;
import com.cct.inhouse.timeoffset.core.todo.domain.TodoSearch;
import com.cct.inhouse.timeoffset.core.todo.domain.TodoSearchCriteria;

import util.database.CCTConnection;

public class TimeOffsetTodoManager extends AbstractManager<TodoSearchCriteria, TodoSearch, Todo, CommonUser, Locale> {

	TimeOffsetTodoService service = null;

	public TimeOffsetTodoManager(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.service = new TimeOffsetTodoService(logger, user, locale);
	}

	@Override
	public List<TodoSearch> search(CCTConnection conn, TodoSearchCriteria criteria) throws Exception {

		List<TodoSearch> listResult = new ArrayList<TodoSearch>();
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
	public int updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception {
		return service.updateActive(conn, ids, activeFlag);
	}
	
	@Override
	public Todo searchById(CCTConnection conn, String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}	

	@Override
	public long add(CCTConnection conn, Todo obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(CCTConnection conn, String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(CCTConnection conn, Todo obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
