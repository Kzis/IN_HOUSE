package com.cct.inhouse.timeoffset.core.todo.domain;

import com.cct.domain.SearchCriteria;
import com.cct.inhouse.common.InhouseModel;
import com.cct.inhouse.timeoffset.core.approve.domain.Approve;

public class TodoModel extends InhouseModel {

	private static final long serialVersionUID = -1460622850784027457L;
	
	TodoSearchCriteria criteria = new TodoSearchCriteria();
	
	private Todo todo = new Todo();
	
	private Approve approve = new Approve();

	public TodoSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (TodoSearchCriteria)criteria;
	}

	public Todo getTodo() {
		return todo;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}

	public Approve getApprove() {
		return approve;
	}

	public void setApprove(Approve approve) {
		this.approve = approve;
	}
	

}
