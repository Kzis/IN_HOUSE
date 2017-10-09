package com.cct.inhouse.timeoffset.core.todo.service;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.cct.abstracts.AbstractService;
import com.cct.common.CommonUser;
import com.cct.inhouse.timeoffset.core.config.parameter.domain.SQLPath;
import com.cct.inhouse.timeoffset.core.todo.domain.TodoSearch;
import com.cct.inhouse.timeoffset.core.todo.domain.TodoSearchCriteria;

import util.database.CCTConnection;

public class TimeOffsetTodoService extends AbstractService {

	private TimeOffsetTodoDAO dao = null;

	public TimeOffsetTodoService(Logger logger, CommonUser user, Locale locale) {
		super(logger, user, locale);
		this.dao = new TimeOffsetTodoDAO(logger, SQLPath.TO_TODO.getSqlPath(), user, locale);
	}

	protected long countData(CCTConnection conn, TodoSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria);
	}

	protected List<TodoSearch> search(CCTConnection conn, TodoSearchCriteria criteria) throws Exception {
		return dao.search(conn, criteria);
	}

	protected int updateActive(CCTConnection conn, String ids, String activeFlag) throws Exception {
		return dao.updateActive(conn, ids, activeFlag);
	}

}
