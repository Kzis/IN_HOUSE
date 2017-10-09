package com.cct.inhouse.util;

import com.cct.exception.ServerValidateException;

public class IHLogicValidate {
	public static boolean checkRequire(String object) throws ServerValidateException {
		return (object == null || object.isEmpty()) ? false : true;
	}
}
