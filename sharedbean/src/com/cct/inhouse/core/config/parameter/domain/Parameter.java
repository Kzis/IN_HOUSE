package com.cct.inhouse.core.config.parameter.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.cct.database.Database;

@XmlRootElement
public class Parameter implements Serializable {

	private static final long serialVersionUID = -578325849007499211L;

	// @XmlTransient
	// @XmlAttribute
	// @XmlElement
	private Application application;
	private Autocomplete autocomplete;
	private Database[] database;
	private Map<String, Database> databaseMap;
	private DateFormat dateFormat;
	private AttachFile attachFile;
	private Report report;
	private ContextConfig contextConfig;
	private Rmi rmi;
	
	public Parameter clone() {
		Parameter cloneObj = new Parameter();
		cloneObj.setApplication(this.application.clone());
		cloneObj.setAutocomplete(this.autocomplete.clone());
		cloneObj.setDateFormat(this.dateFormat.clone());
		cloneObj.setAttachFile(this.attachFile.clone());
		cloneObj.setReport(this.report.clone());
		cloneObj.setRmi(this.rmi.clone());
		cloneObj.setContextConfig(this.contextConfig.clone());
		
		if(this.database != null) {
			cloneObj.database = this.database.clone();
		}
		
		if(this.databaseMap != null && !this.databaseMap.isEmpty()) {
			cloneObj.databaseMap = new LinkedHashMap<String, Database>(this.databaseMap);
		}
		
		return cloneObj;
	}
	
	public Application getApplication() {
		return application;
	}

	@XmlElement
	public void setApplication(Application application) {
		this.application = application;
	}

	@XmlTransient
	public Map<String, Database> getDatabaseMap() {
		if (getDatabase() == null) {
			return databaseMap;
		}

		if (databaseMap == null) {
			databaseMap = new LinkedHashMap<String, Database>();
			for (Database db : getDatabase()) {
				databaseMap.put(db.getKey(), db);
			}
		}
		return databaseMap;
	}

	public Database[] getDatabase() {
		return database;
	}

	@XmlElement
	public void setDatabase(Database[] database) {
		if (this.databaseMap != null) {
			this.databaseMap.clear();
			this.databaseMap = null;
		}
		this.database = database;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	@XmlElement
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public AttachFile getAttachFile() {
		return attachFile;
	}

	@XmlElement
	public void setAttachFile(AttachFile attachFile) {
		this.attachFile = attachFile;
	}

	public Report getReport() {
		return report;
	}

	@XmlElement
	public void setReport(Report report) {
		this.report = report;
	}
	
	public Rmi getRmi() {
		return rmi;
	}
	@XmlElement
	public void setRmi(Rmi rmi) {
		this.rmi = rmi;
	}

	public ContextConfig getContextConfig() {
		return contextConfig;
	}

	@XmlElement
	public void setContextConfig(ContextConfig contextConfig) {
		this.contextConfig = contextConfig;
	}

	public Autocomplete getAutocomplete() {
		return autocomplete;
	}

	@XmlElement
	public void setAutocomplete(Autocomplete autocomplete) {
		this.autocomplete = autocomplete;
	}

	public void merge(Parameter update) {
		// System.out.println("Merge: " + current + " > " + update);
		if (this.application == null && update.application != null){
			this.application = update.application;
		} else {
			this.application.merge(update.application);
		}
		
		if (this.autocomplete == null && update.autocomplete != null) {
			this.autocomplete = update.autocomplete;
		} else {
			this.autocomplete.merge(update.autocomplete);
		}
		
		if (this.dateFormat == null && update.dateFormat != null) {
			this.dateFormat = update.dateFormat;
		} else {
			this.dateFormat.merge(update.dateFormat);
		}
		
		if (this.attachFile == null && update.attachFile != null) {
			this.attachFile = update.attachFile;
		} else {
			this.attachFile.merge(update.attachFile);
		}
		
		if (this.report == null && update.report != null) {
			this.report = update.report;
		} else {
			this.report.merge(update.report);
		}
		
		if (this.contextConfig == null && update.contextConfig != null) {
			this.contextConfig = update.contextConfig;
		} else {
			this.contextConfig.merge(update.contextConfig);
		}
		
		if (this.database == null && update.database != null) {
			this.database = update.database;
		} else if(this.database != null && update.database != null && update.database.length > 0) {
			System.arraycopy(update.database, 0, this.database, 0, update.database.length); 
		}
	
		if (this.databaseMap == null && update.databaseMap != null) {
			this.databaseMap = update.databaseMap;
		} else if(this.databaseMap != null && update.databaseMap != null && !update.databaseMap.isEmpty()) {
			this.databaseMap.putAll(update.databaseMap);
		}
		
	}

	
	
}
