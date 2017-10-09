package com.cct.inhouse.central.core.config.parameter.domain;

import java.io.Serializable;
import java.util.Map;

import com.cct.database.Database;
import com.cct.inhouse.core.config.parameter.domain.Application;
import com.cct.inhouse.core.config.parameter.domain.AttachFile;
import com.cct.inhouse.core.config.parameter.domain.Autocomplete;
import com.cct.inhouse.core.config.parameter.domain.ContextConfig;
import com.cct.inhouse.core.config.parameter.domain.DateFormat;
import com.cct.inhouse.core.config.parameter.domain.Parameter;
import com.cct.inhouse.core.config.parameter.domain.Report;

public class CParameterConfig implements Serializable {

	private static final long serialVersionUID = -2187894195556282622L;

	private static Parameter parameter;

	
	public static Parameter getParameter() {
		return parameter;
	}

	public static void setParameter(Parameter parameter) {
		CParameterConfig.parameter = parameter;
	}

	public Application getApplication() {
		return getParameter().getApplication();
	}

	public AttachFile getAttachFile() {
		return getParameter().getAttachFile();
	}

	public Autocomplete getAutocomplete() {
		return getParameter().getAutocomplete();
	}

	public ContextConfig getContextConfig() {
		return getParameter().getContextConfig();
	}

	public Database[] getDatabase() {
		return getParameter().getDatabase();
	}

	public Map<String, Database> getDatabaseMap() {
		return getParameter().getDatabaseMap();
	}

	public DateFormat getDateFormat() {
		return getParameter().getDateFormat();
	}

	public Report getReport() {
		return getParameter().getReport();
	}
}
