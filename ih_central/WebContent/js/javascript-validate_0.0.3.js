	var objFocus;
	var msgLabel = "";
	function validateFormInputAll() {
		/* clear msg */
		clearMessage();
		msgLabel = "";
		objFocus = null;
		
		/** validate require */
		jQuery("input, div.ui_picklist, select, textarea").each(function (index) {
			validateInputAll(jQuery(this), "requireInput");
		});
		/** end validate require */
		
		return focusFirstInput();
	}
	
	function validateDivInputAll(divId) {
		/* clear msg */
		clearMessage();
		msgLabel = "";
		objFocus = null;

		if (divId != undefined && divId != "") {
			/** validate require */
			jQuery("#" + divId).find("input, div.ui_picklist, select, textarea").each(function() {
				validateInputAll(jQuery(this), "requireFill");
			});
		}
		
		return focusFirstInput();
	}
	
	/**
	 * Validate require input
	 * @param obj
	 * @param requireClass
	 */
	function validateInputAll(obj, requireClass) {
		var className = jQuery(obj).attr("class");
		if (className != undefined) {
			if (className.indexOf('readonly') == -1) {
//				console.info(jQuery(obj).attr('type'));
				if (className.indexOf(requireClass) > -1) {
					var value;
					if ((jQuery(obj).attr('type') == 'radio') || (jQuery(obj).attr('type') == 'checkbox')) {
						var name = jQuery(obj).attr( "name" );
						if(jQuery("input[name='" + name + "']:checked").length > 0) {
							if (jQuery("input[name='" + name + "']").val() != undefined && jQuery("input[name='" + name + "']").val() != "") {
								value = jQuery("input[name='" + name + "']").val();	
							} else {
								value = "Y";
							}
						} else {
							value = "";
						}
					} else if (jQuery(obj).attr('type') == 'button') {
						// validate button uploadfile require data
						var validFile = jQuery(obj).attr("validFile");
						value = trim(jQuery("input[name='" + validFile + "']").val());
					} else if (jQuery(obj).attr('type') == 'ui_picklist') {
						var picklistVal = jQuery("input.picklist_value",obj).val();
						value = trim(picklistVal);
					} else {
						value = trim(jQuery(obj).val());
					}

					if (value == "") {
						var validName;
						
						if (jQuery(obj).attr("validName") != undefined) {
							validName = jQuery(obj).attr("validName");
							if (validName == undefined || validName == '') {
								// set default message if type button
								if (jQuery(obj).attr('type') == 'button') {
									validName = "Browse";
								}
							}
						} else {
							var type = jQuery(obj).attr("type");
							var className = jQuery(obj).attr("class");
							
							if ((type == 'radio') || (type == 'checkbox')) {
								validName = jQuery(obj).parent().attr('validName');
							} else {
								if (className.indexOf('input_dateformat_') > -1) {
									var eleId = jQuery(obj).attr("id").replace("_dd_sl_mm_sl_yyyy","");
									validName = jQuery("#"+eleId).attr("validName");
								} else if (className.indexOf('input_timeformat_') > -1) {
									var eleId = jQuery(obj).attr("id").replace("_hh_cl_mm","");
									validName = jQuery("#"+eleId).attr("validName");
								}
							}
						}
						
						if (validName != undefined) {
							if ((jQuery(obj).attr('type') == 'radio') || (jQuery(obj).attr('type') == 'checkbox')) {
								msg = validateMessage.CODE_10015.replace("xxx", validName); 	// xxx must select at least one item.
							} else {
								msg = validateMessage.CODE_10002.replace("xxx", validName); 	// xxx cannot be blank.
							}
							setMessageValid(type, msg, jQuery(obj));
						}
					} else {
						validateFormatInputAll(type, jQuery(obj));
					}
				} else {
					if (!jQuery.isArray(jQuery(obj).val()) && trim(jQuery(obj).val()) != "") {
						validateFormatInputAll(type, jQuery(obj));
					}
				}
			}
		}

		if (jQuery(obj).css("text-transform") == "uppercase") {
			jQuery(obj).val(jQuery(obj).val().toUpperCase());
		}
	}
	
	function focusFirstInput() {
		if (objFocus != undefined && msgLabel != "") {
			tabFocus();					// focus tab
			objFocus.focus();			// fofus object
			
			showNotifyMessageValidate(msgLabel);		// show message
			
			return false;
		} else {
			return true;
		}
	}

	function clearMessage() {
		clearNotifyMessage();
		jQuery(".msgLabel").each(function (index) {
			jQuery(this).html("");	
		});
		
		jQuery(".border_select").each(function (index) {
			jQuery(this).removeClass("border_select");
		});
	}

	function validateFormatInputAll(type, obj) {
		/** validate format */
		var className = obj.attr("class");
		
		if (className.indexOf('input_email') > -1) {
			if (validateEmailFormatFormIndex(obj) == false) {
				setMessageValid(type, validateMessage.CODE_10008, obj);			// รูปแบบอีเมลไม่ถูกต้อง
			}
		} else if (className.indexOf('input_dateformat_') > -1) {
			var status = inputValidateDateFormatValueName(jQuery(obj)[0]);
			if ((status == 'empty') || status == 'ok') {

			} else {
				msg = validateMessage.CODE_10016.replace("xxx", "Date");		// xxx is invalid.
				setMessageValid(type, msg, obj);
			}
		} else if (className.indexOf('input_timeformat_') > -1) {
			var status = inputValidateTimeFormatValueName(jQuery(obj)[0]);
			if ((status == 'empty') || status == 'ok') {

			} else {
				msg = validateMessage.CODE_10016.replace("xxx", "Time");		// xxx is invalid.
				setMessageValid(type, msg, obj);
			}
		}
		/** end validate format */
		
		var validation = obj.attr("cp_validation");
		if (validation != undefined) {
			validateFormatInput (type, obj, validation);
		}
		
	}
		
	function validateFormatInput (type, obj, validation) {
		// TODO
	}

	function setMessageValid(type, msg, obj) {
		if (type == 'part') {
			msgLabel = msg;
			obj.nextAll(".msgLabel").html("<font color='red'><b>" + msgLabel + "</b></font>");
		} else if (type == 'sum') {
			obj.addClass("border_select");
		} else {
			// console.log(msgLabel.indexOf(msg));
			if (msgLabel.indexOf(msg) == -1) {
				msgLabel += msg + "<br/>";
			}
			
			if (jQuery(obj).css("display") == "none" && jQuery(obj).prop("tagName") == "SELECT") {
				jQuery(obj).next().addClass("border_select");
			} else if ((jQuery(obj).attr('type') == 'radio') || (jQuery(obj).attr('type') == 'checkbox')) {
				jQuery(obj).addClass("border_select");
				// loop หา requireGroup กรณีวางโครงสร้างไว้หลายชั้น
				var elParent = jQuery(obj);
				for (var i = 0; i < 5; i++) {
					elParent = jQuery(elParent).parent();
					if (elParent.attr("class") == "requireGroup") {
						jQuery(elParent).addClass("border_select");
						break;
					}
				} 
			} else if (jQuery(obj).css("display") == "none" && jQuery(obj).prop("tagName") == "INPUT") {
				jQuery(obj).next().addClass("border_select");
			} else {
				obj.addClass("border_select");
			}
		}
		
		if (objFocus == undefined) {
			if (jQuery(obj).css("display") == "none" && jQuery(obj).prop("tagName") == "SELECT") {
				objFocus = jQuery(obj).next();
			} else {
				objFocus = obj;
			}
		}
	}
	
	function tabFocus() {
		var isLastTab = false;
		var objCurrent = jQuery(objFocus);
		
		// Last tab ?
		while (!isLastTab) {
			// find	selected tab id		
			var tabId = jQuery(objCurrent).parent().closest("div.ui-tabs").attr("id");
			if (tabId != undefined && tabId != "") {
				// find tab panel index
				var panelId = jQuery(objCurrent).closest("div.ui-tabs-panel").attr("id");
				if (panelId != undefined && panelId != "") {
					// find div id and index of selected tab
					var index = 0;
					jQuery("#" + tabId).children("div.ui-tabs-panel").each(function() {
						if (jQuery(this).attr("id") == panelId) {
							index = jQuery(this).index() - 1;
							return false;
						}
					});
					
					// focus selected tab
					jQuery("#" + tabId).tabs("option", "active", index);
					
					// find upper tab 
					objCurrent = jQuery("#" + tabId);
				}
				
			} else {
				// find not found upper tab. stop working focus tab
				isLastTab = true;
			}
		}
	}