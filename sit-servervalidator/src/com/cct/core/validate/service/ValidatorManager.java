package com.cct.core.validate.service;

import java.util.List;
import java.util.Map;

import com.cct.core.validate.domain.ValidatorConfig;
import com.cct.core.validate.domain.ValidatorWhat;

public class ValidatorManager {
	
	private ValidatorService service = null;
	
	public ValidatorManager() {
		this.service = new ValidatorService();
	}
	
	public Map<String, Map<String, List<ValidatorWhat>>> loadXMLConfigToMap(String fileNameWithPath) {
		return service.loadXMLConfigToMap(fileNameWithPath);
	}
	
	public ValidatorConfig loadXMLConfigToObject(String fileNameWithPath) throws Exception {
		return service.load(fileNameWithPath);
	}
	
	public static void main(String[] args) {
		try {
			// new ValidatorService().create(filePath);
			// new ValidatorService().load(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

