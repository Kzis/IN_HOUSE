package com.cct.inhouse.central.rmi.domain;

public class RMISelectItemObjectRequest<T> extends RMISelectItemRequest {

	private static final long serialVersionUID = 2374454295221862029L;

	private T object;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
