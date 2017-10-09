package com.cct.core.validate.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cct.core.validate.domain.ValidatorCondition;
import com.cct.core.validate.domain.ValidatorConfig;
import com.cct.core.validate.domain.ValidatorField;
import com.cct.core.validate.domain.ValidatorWhat;

import util.log.DefaultLogUtil;
import util.xml.XMLUtil;

public class ValidatorService {
	
	// public static String filePath = System.getProperty("user.dir")  +"/src/bk-validators.xml";
	public static String filePath = "D:/Projects/neon3/inhouseprojectxx/bkonline/src/bk-validators.xml";
	
	public Map<String, Map<String, List<ValidatorWhat>>> loadXMLConfigToMap(String fileNameWithPath) {
		
		// Map.ClassName > Map.ActionName > List.InputName
		Map<String, Map<String, List<ValidatorWhat>>> mapValidatorClass = new HashMap<>();
		try {
			ValidatorConfig validatorConfig = load(fileNameWithPath);
			if (validatorConfig.getValidator() == null) {
				// No xml for validate
				return mapValidatorClass;
			}
			
			for (ValidatorField validatorField : validatorConfig.getValidator()) {
				Map<String, List<ValidatorWhat>> mapValidatorMethod = mapValidatorClass.get(validatorField.getClassName());
				if (mapValidatorMethod == null) {
					mapValidatorMethod = new HashMap<String, List<ValidatorWhat>>();
					
					List<ValidatorWhat> listInput = Arrays.asList(validatorField.getValidatorWhat());
					mapValidatorMethod.put(validatorField.getActionMethod(), listInput);
					
					mapValidatorClass.put(validatorField.getClassName(), mapValidatorMethod);
					
				} else {
					List<ValidatorWhat> listInput = mapValidatorMethod.get(validatorField.getActionMethod());
					if (listInput == null) {
						listInput = Arrays.asList(validatorField.getValidatorWhat());
						mapValidatorMethod.put(validatorField.getActionMethod(), listInput);
					} else {
						listInput.addAll(Arrays.asList(validatorField.getValidatorWhat()));
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapValidatorClass;
	}
	
	protected ValidatorConfig load(String filePath) throws Exception {
		ValidatorConfig validatorConfig = new ValidatorConfig();
		try {
			DefaultLogUtil.SERVER_VALIDATION.debug("path :- " + filePath);
			validatorConfig = (ValidatorConfig) XMLUtil.xmlToObject(filePath, validatorConfig);
		} catch (Exception e) {
			throw e;
		}
		return validatorConfig;
	}
	
	protected ValidatorConfig create(String filePath) throws Exception {
		ValidatorConfig validatorConfig = new ValidatorConfig();
		try {
			DefaultLogUtil.SERVER_VALIDATION.debug("path :- " + filePath);
			ValidatorField vf0 = new ValidatorField();
			vf0.setClassName("com.cct.inhouse.bkonline.web.booking.roombooking.action.RoomBookingAction");
			vf0.setActionMethod("addRoomBooking");
			
			ValidatorWhat vw0 = new ValidatorWhat();
			vw0.setInputName("roomBooking.subject");

			ValidatorCondition vc0 = new ValidatorCondition();
			vc0.setValidateType("requireInput");
			vc0.setMessage("You must enter a value for subject.");
			
			ValidatorCondition vc1 = new ValidatorCondition();
			vc1.setValidateType("stringLength");
			vc1.setMessage("Subject length (5-10).");
			vc1.setMinLength(5);
			vc1.setMaxLength(10);
			
			ValidatorCondition[] vcArray = new ValidatorCondition[2];
			vcArray[0] = vc0;
			vcArray[1] = vc1;
			
			vw0.setCondition(vcArray);
			
			ValidatorWhat[] vwArray = new ValidatorWhat[1];
			vwArray[0] = vw0;
			
			vf0.setValidatorWhat(vwArray);
			
			
			
			
			ValidatorField vf1 = new ValidatorField();
			vf1.setClassName("com.cct.inhouse.bkonline.web.booking.roombooking.action.RoomBookingAction");
			vf1.setActionMethod("editRoomBooking");
			
			vw0 = new ValidatorWhat();
			vw0.setInputName("roomBooking.detail");
			
			vc0 = new ValidatorCondition();
			vc0.setValidateType("stringLength");
			vc0.setMessage("Detail length (5-10).");
			vc0.setMinLength(5);
			vc0.setMaxLength(10);
			
			vcArray = new ValidatorCondition[1];
			vcArray[0] = vc0;
			
			vw0.setCondition(vcArray);
			
			ValidatorField[] vfArray = new ValidatorField[2];
			vfArray[0] = vf0;
			vfArray[1] = vf1;
			
			vwArray = new ValidatorWhat[1];
			vwArray[0] = vw0;
			
			vf1.setValidatorWhat(vwArray);
			
			validatorConfig.setValidator(vfArray);
			
			XMLUtil.objectToXML(filePath, validatorConfig);
		} catch (Exception e) {
			throw e;
		}
		return validatorConfig;
	}
	
	public static void main(String[] args) {
		try {
			//new ValidatorService().create(filePath);
			new ValidatorService().load(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

