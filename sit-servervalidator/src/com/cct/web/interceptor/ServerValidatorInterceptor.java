package com.cct.web.interceptor;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

import com.cct.common.CommonAction;
import com.cct.enums.ActionMessageType;
import com.cct.enums.ActionReturnType;
import com.cct.exception.ServerValidateException;
import com.cct.core.validate.domain.ValidateType;
import com.cct.core.validate.domain.ValidatorCondition;
import com.cct.core.validate.domain.ValidatorConfig;
import com.cct.core.validate.domain.ValidatorConstant;
import com.cct.core.validate.domain.ValidatorWhat;
import com.cct.core.validate.service.ValidatorManager;

import util.validate.ServerValidateUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;

import util.file.FileManagerUtil;
import util.log.DefaultLogUtil;
import util.string.StringDelimeter;
import util.web.SessionUtil;

public class ServerValidatorInterceptor implements Interceptor {

	private static final long serialVersionUID = -5218746150619807135L;
	
	private Logger logger = DefaultLogUtil.SERVER_VALIDATION;
	
	private static final String DEFAULT_CONFIG_FILE_NAME = "default-server-validatorx.xml";
	private static final String FILTER_CONFIG_FILE_NAME = "*-server-validatorx.xml";
	
	private String configFilePath;
	private boolean devMode = true;
	private Locale databaseLocale;
	
	private Map<String, Map<String, List<ValidatorWhat>>> mapValidatorConfig;
	
	@Override
	public void destroy() {
		getLogger().debug(StringDelimeter.EMPTY.getValue());
	}

	@Override
	public void init() {
		String defaultPath = StringDelimeter.EMPTY.getValue();
		String configPath = StringDelimeter.EMPTY.getValue();
		try {
			defaultPath = getDefaultConfigFilePathWithFileName();
			if (defaultPath != null) {
				ValidatorManager validatorManager = new ValidatorManager();
				ValidatorConfig defaultConfig = validatorManager.loadXMLConfigToObject(defaultPath);
				initDefaultConfig(defaultConfig);
				
				configPath = getConfigFilePathWithOutFileName(defaultPath);
				File[] arrayConfig = FileManagerUtil.listFiles(configPath, FILTER_CONFIG_FILE_NAME);
				getLogger().debug("Mode: " + isDevMode());
				getLogger().debug("Total config: " + arrayConfig.length);
				for (File config : arrayConfig) {
					getLogger().debug("config: " + config.getName());
					putMapValidatorConfig(validatorManager.loadXMLConfigToMap(config.getAbsolutePath()));	
				}
			}
		} catch (Exception e) {
			getLogger().error("Can't load file: " + defaultPath, e);
		}
	}
	
	public void initDefaultConfig(ValidatorConfig defaultConfig) {
		setDevMode(defaultConfig.isDevMode());
		Locale tempLocale = null;
		if (defaultConfig.getDatabaseLocale() == null) {
			tempLocale = Locale.ENGLISH;
		} else {
			tempLocale = new Locale(defaultConfig.getDatabaseLocale().toLowerCase(), defaultConfig.getDatabaseLocale().toUpperCase());
		}
		setDatabaseLocale(tempLocale);
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		ValueStack valueStack = actionInvocation.getStack();
		ActionSupport actionSupport = (ActionSupport) actionInvocation.getAction();
		Locale actionLocale = actionSupport.getLocale(); // actionInvocation.getInvocationContext().getLocale();
		getLogger().info("databaseLocale: " + getDatabaseLocale()); // Database Locale
		getLogger().info("actionLocale: " + actionLocale); // Action Locale
		String actionName = actionInvocation.getProxy().getActionName();
		String className = actionInvocation.getProxy().getAction().getClass().getCanonicalName();
		
		getLogger().info("class: " + className); // Class name
		getLogger().info("actionName: " + actionName); // Action name
		
		
		// getLogger().debug("peek: " + valueStack.peek()); // แอบดูข้อมูล
		// getLogger().debug("pop: " + valueStack.pop()); // ดึงข้อมูล ออกมาห้ามใช้
		// ถ้าไม่มี Input or Disable findString จะได้ null
		
		String result = ActionReturnType.INVALID_REQUEST.getResult();
		try {
			// ตรวจสอบข้อมูล
			String messageException = validate(className, actionName, actionSupport, valueStack, actionLocale);
			if (messageException.length() == 0) {
				// ถ้าผ่านให้เรียกไปที่ Action Method นั้นๆ ได้เลย
				result = actionInvocation.invoke();	
				
				getLogger().info("Return: " + result);
				return result;
			} else {
				setMessageSession(messageException, actionLocale, actionSupport);
			}
		} catch (Exception e) {
			getLogger().error(e);
		}
		
		if (isDevMode()) {
			getLogger().info("Return: " + result);
			return result;	
		} else {
			return ActionReturnType.BLOCKING_REQUEST.getResult();
		}
	}
	
	private String validate(String className, String actionName, ActionSupport actionSupport, ValueStack valueStack, Locale actionLocale) throws ServerValidateException {
		
		StringBuilder messageBuilder = new StringBuilder();
		
		if (getMapValidatorConfig() == null) {
			getLogger().debug("Config is null, skip validate");
			return StringDelimeter.EMPTY.getValue();
		}
		
		long timeStart = Calendar.getInstance(Locale.ENGLISH).getTimeInMillis();
		
		Map<String, List<ValidatorWhat>> mapValidatorAction = getMapValidatorConfig().get(className);
		getLogger().debug("Hit class: " + mapValidatorAction);
		if (mapValidatorAction != null && mapValidatorAction.size() > 0) {
			List<ValidatorWhat> listValidatorWhat = mapValidatorAction.get(actionName);
			getLogger().debug("Hit action: " + listValidatorWhat);
			if (listValidatorWhat != null && listValidatorWhat.size() > 0) {
				for (ValidatorWhat validatorWhat : listValidatorWhat) {
					getLogger().debug("Hit input [" +  validatorWhat.getInputName() + "]: " + valueStack.findValue(validatorWhat.getInputName()));
					Object objectValue = valueStack.findValue(validatorWhat.getInputName());
					
					// ตรวจสอบปัญหาให้ throw ได้เลย
					String messageTemp = matchDataType(objectValue, validatorWhat, actionSupport, valueStack, StringDelimeter.EMPTY.getValue(), actionLocale);
					messageBuilder.append(messageTemp);
				}
			}
			getLogger().debug(StringDelimeter.EMPTY.getValue());
		}		
		
		long timeEnd = Calendar.getInstance(Locale.ENGLISH).getTimeInMillis();
		getLogger().info(actionName + "-ValidateTime: " + convertTime(timeEnd - timeStart));
		
		return messageBuilder.toString();
	}
	
	private String matchDataType(Object objectValue, ValidatorWhat validatorWhat, ActionSupport actionSupport, ValueStack valueStack, String prefixName, Locale actionLocale) throws ServerValidateException {
		StringBuilder messageBuilder = new StringBuilder();
		if (objectValue == null) {
			getLogger().debug("Hit objectValue is null");
			// ต้องติด Case requireInput เมื่อมีการกำหนด validateType = requireInput
			
			String messageTemp = matchConditionAndValidate(objectValue, validatorWhat.getCondition(), actionSupport, valueStack, actionLocale);
			messageBuilder.append(messageTemp);
	
		} else if (objectValue instanceof Collection<?>){
			getLogger().debug("Hit collection");
			int index = 0;
			for (Iterator<?> iterator = ((Collection<?>)objectValue).iterator(); iterator.hasNext();) {
				// วนตรวจสอบทุกๆ Object ใน Collection
				if (validatorWhat.getValidatorCollection() != null) {
					for (ValidatorWhat condition : validatorWhat.getValidatorCollection()) {
						getLogger().debug("findInputFormat: " + prefixName + validatorWhat.getInputNameFormat());
						String findInput = String.format(prefixName + validatorWhat.getInputNameFormat(), index, condition.getInputName());
						Object objectInCollection = valueStack.findValue(findInput);
						getLogger().debug(findInput + ": " + objectInCollection);
						
						String messageTemp = matchDataType(objectInCollection, condition, actionSupport, valueStack, findInput, actionLocale);
						messageBuilder.append(messageTemp);
					}
				}
				iterator.next();
				index++;
			}
			
		} else if (objectValue instanceof Map<?,?>){
			getLogger().debug("Hit map");
			for (Object key : ((Map<?,?>) objectValue).keySet()) {
				if (validatorWhat.getValidatorCollection() != null) {
					for (ValidatorWhat condition : validatorWhat.getValidatorCollection()) {
						getLogger().debug("findInputFormat: " + prefixName + validatorWhat.getInputNameFormat());
						String findInput = String.format(prefixName + validatorWhat.getInputNameFormat(), key, condition.getInputName());
						Object objectInCollection = valueStack.findValue(findInput);
						getLogger().debug(findInput + ": " + objectInCollection);
					
						String messageTemp = matchDataType(objectInCollection, condition, actionSupport, valueStack, findInput, actionLocale);
						messageBuilder.append(messageTemp);
					}
				}
			}
			
		} else if (objectValue.getClass().isArray()){
			getLogger().debug("Hit array not validate");
			
		} else {
			getLogger().debug("Hit string");
			// ตรวจสอบตามเงือนไขปกติ
			
			String messageTemp = matchConditionAndValidate(objectValue, validatorWhat.getCondition(), actionSupport, valueStack, actionLocale);
			messageBuilder.append(messageTemp);
		}
		return messageBuilder.toString();
	}
	
	/**
	 * จับคู่เงือนที่ต้องการตรวจกับ Util
	 * @param objectValue
	 * @param arrayCondition
	 * @throws ServerValidateException 
	 */
	private String matchConditionAndValidate(Object objectValue, ValidatorCondition[] arrayCondition, ActionSupport actionSupport, ValueStack valueStack, Locale actionLocale) throws ServerValidateException {
		if (arrayCondition == null ) {
			// ไม่กำหนดเงือนไขที่ต้องตรวจมา
			return StringDelimeter.EMPTY.getValue();
		}

		StringBuilder messageBuilder = new StringBuilder();
		for (ValidatorCondition condition : arrayCondition) {
			// getLogger().debug("Condition: " + condition);
			
			try {
				switch (condition.getValidateType()) {
				case ValidateType.requireInput:	ServerValidateUtil.checkRequire(objectValue, actionLocale, actionSupport);
					break;
					
				case ValidateType.intValue:	ServerValidateUtil.checkIntValue(objectValue, actionLocale, actionSupport);
					break;
				
				case ValidateType.longValue: ServerValidateUtil.checkLongValue(objectValue, actionLocale, actionSupport);
					break;
				
				case ValidateType.dateValue: 
					caseDateValue(objectValue, condition, valueStack, actionLocale, actionSupport);
					break;
				
				case ValidateType.timeValue: 
					caseTimeValue(objectValue, condition, valueStack, actionLocale, actionSupport);
					break;
					
				case ValidateType.stringLength:
					getLogger().debug(condition.getMinLength() + " < " + condition.getMaxLength());
					ServerValidateUtil.checkStringLength(objectValue, condition.getMinLength(), condition.getMaxLength(), actionLocale, actionSupport);
					break;
					
				case ValidateType.dayRangeLimit:
					caseDayRangeLimit(objectValue, condition, valueStack, actionLocale, actionSupport);
					break;
					
				case ValidateType.emailValue:
					ServerValidateUtil.checkEmailValue(objectValue, actionLocale, actionSupport);
					break;
				
				case ValidateType.dateBeforeLimit:
					caseDateBeforeLimit(objectValue, condition, valueStack, actionLocale, actionSupport);
					break;
				
				case ValidateType.idCardValue:
					ServerValidateUtil.checkIdCardValue(objectValue, actionLocale, actionSupport);
					break;
					
				default: getLogger().debug("Invalid validateType: " + condition.getValidateType());
					break;
				}
			} catch (ServerValidateException e) {
				if (isDevMode()) {
					messageBuilder.append(generateMessage(condition, actionLocale, actionSupport));	
				} else {
					setMessageSession(e.getMessage(), actionLocale, actionSupport);
					throw e;
				}
			}
		}
		return messageBuilder.toString();
	}

	private void caseDateValue(Object objectValue, ValidatorCondition condition, ValueStack valueStack, Locale actionLocale, ActionSupport actionSupport) throws ServerValidateException {
		if (condition.isUserLocale()) {
			ServerValidateUtil.checkDateValue(objectValue, getDateFormat(condition.getFormat()), actionLocale, actionLocale, actionSupport);
		} else {
			ServerValidateUtil.checkDateValue(objectValue, getDateFormat(condition.getFormat()), getDatabaseLocale(), actionLocale, actionSupport);	
		}
	}
	
	private void caseTimeValue(Object objectValue, ValidatorCondition condition, ValueStack valueStack, Locale actionLocale, ActionSupport actionSupport) throws ServerValidateException {
		if (condition.isUserLocale()) {
			ServerValidateUtil.checkTimeValue(objectValue, getTimeFormat(condition.getFormat()), actionLocale, actionLocale, actionSupport);
		} else {
			ServerValidateUtil.checkTimeValue(objectValue, getTimeFormat(condition.getFormat()), getDatabaseLocale(), actionLocale, actionSupport);
		}
	}
	
	private void caseDayRangeLimit(Object objectValue, ValidatorCondition condition, ValueStack valueStack, Locale actionLocale, ActionSupport actionSupport) throws ServerValidateException {
		getLogger().debug("RangeLimit: " + condition.getRangeLimit());
		
		String startDate = String.valueOf(objectValue);
		String endDate = String.valueOf(objectValue);
		
		if (condition.getStartDate() != null) {
			startDate = valueStack.findString(condition.getStartDate());
		}
		
		if (condition.getEndDate() != null) {
			endDate = valueStack.findString(condition.getEndDate());
		}
		
		getLogger().debug("RangeLimit: " + condition.getRangeLimit() + ", " + startDate + " < " + endDate);
		if (condition.isUserLocale()) {
			ServerValidateUtil.checkDayRangeLimit(startDate, endDate, getDateFormat(condition.getFormat()), condition.getRangeLimit(), actionLocale, actionLocale, actionSupport);
		} else {
			ServerValidateUtil.checkDayRangeLimit(startDate, endDate, getDateFormat(condition.getFormat()), condition.getRangeLimit(), getDatabaseLocale(), actionLocale, actionSupport);
		}
	}
	
	private void caseDateBeforeLimit(Object objectValue, ValidatorCondition condition, ValueStack valueStack, Locale actionLocale, ActionSupport actionSupport) throws ServerValidateException {
		getLogger().debug("Limit by: " + condition.getValidateTypeCondition() + ", " + condition.getRangeLimit());
		if (condition.isUserLocale()) {
			ServerValidateUtil.checkDateBeforeLimit(objectValue, condition.getValidateTypeCondition(), condition.getRangeLimit(), actionLocale, actionLocale, getDateFormat(condition.getFormat()), condition.getDbLookup(), condition.getDbFormat(), actionSupport);
		} else {
			ServerValidateUtil.checkDateBeforeLimit(objectValue, condition.getValidateTypeCondition(), condition.getRangeLimit(), getDatabaseLocale(), actionLocale, getDateFormat(condition.getFormat()), condition.getDbLookup(), condition.getDbFormat(), actionSupport);
		}
		
	}
	
	private void setMessageSession(String detail, Locale actionLocale, ActionSupport actionSupport) {
		String[] messages = { ActionMessageType.ERROR.getType(), ServerValidateUtil.createSubject(actionLocale, actionSupport), detail };
		SessionUtil.put(CommonAction.MESSAGE, messages);
	}
	
	private String generateMessage(ValidatorCondition condition, Locale actionLocale, ActionSupport actionSupport) {
		String message = condition.getMessage();
		if ((message != null) && (message.startsWith(StringDelimeter.SHARP.getValue()))) {
			message = StringDelimeter.SHARP.getValue() + actionSupport.getText(message.substring(1));
		}
		return ServerValidateUtil.createMessage(message, actionLocale, actionSupport) + ValidatorConstant.DEFAULT_END_TAG;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public static String convertTime(long elapsed) {
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		return formatter.format(new Date(elapsed));
	}

	private Map<String, Map<String, List<ValidatorWhat>>> getMapValidatorConfig() {
		return mapValidatorConfig;
	}

	private void putMapValidatorConfig(Map<String, Map<String, List<ValidatorWhat>>> mapValidatorConfig) {
		if (this.mapValidatorConfig == null) {
			this.mapValidatorConfig = new HashMap<>();
		}
		this.mapValidatorConfig.putAll(mapValidatorConfig);
	}
	
	private String getDateFormat(String format) {
		String defaultFormat = ValidatorConstant.DEFAULT_DATE_FORMAT;
		if ((format != null) && format.trim().isEmpty()) {
			defaultFormat = format; 
		}
		return defaultFormat;
	}
	
	private String getTimeFormat(String format) {
		String defaultFormat = ValidatorConstant.DEFAULT_TIME_FORMAT;
		if ((format != null) && format.trim().isEmpty()) {
			defaultFormat = format; 
		}
		return defaultFormat;
	}

	/**
	 * ถ้าไม่กำหนดให้ใช้ค่า default
	 * @return
	 */
	public String getConfigFileName() {
		return DEFAULT_CONFIG_FILE_NAME;
	}

	public String getConfigFilePath() {
		if (configFilePath == null) {
			return StringDelimeter.EMPTY.getValue();
		} else {
			return configFilePath;
		}
	}
	
	/**
	 * Find file path and file name for load xml validation config
	 * @return
	 */
	public String getDefaultConfigFilePathWithFileName() {
		String filePathWithFileName = null;
		if (getConfigFilePath().trim().isEmpty()) {
			URL urlFile = FileResourceLoader.class.getClassLoader().getResource(getConfigFileName());
			if (urlFile != null) {
				try {
					filePathWithFileName = URLDecoder.decode(urlFile.getPath(),"UTF-8");
					getLogger().info("validatorFile: " + filePathWithFileName);

				} catch (Exception e) {
					getLogger().error(e);
				}
			}
			
		} else {
			filePathWithFileName = getConfigFilePath() + getConfigFileName();
		}
		
		return filePathWithFileName;
	}
	
	/**
	 * Find file path for load all xml validation config
	 * @return
	 */
	public String getConfigFilePathWithOutFileName(String defaultConfigFilePath) {
		return defaultConfigFilePath.replace(getConfigFileName(), StringDelimeter.EMPTY.getValue());
	}
	
	public static void main(String[] args) {
		
	}

	public boolean isDevMode() {
		return devMode;
	}

	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}

	public void setDatabaseLocale(Locale databaseLocale) {
		this.databaseLocale = databaseLocale;
	}

	public Locale getDatabaseLocale() {
		return databaseLocale;
	}
}
