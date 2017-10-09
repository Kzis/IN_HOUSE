function LTrim(str) {
	/*
	 * PURPOSE: Remove leading blanks from our string. IN: str - the string we
	 * want to LTrim
	 */

	var whitespace = new String(" \t\n\r");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(0)) != -1) {
		// We have a string with leading blank(s)...
		var j = 0, i = s.length;

		// Iterate from the far left of string until we
		// don't have any more whitespace...
		while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
			j++;

		// Get the substring from the first non-whitespace
		// character to the end of the string...
		s = s.substring(j, i);
	}
	return s;
}

function RTrim(str) {
	/*
	 * PURPOSE: Remove trailing blanks from our string. IN: str - the string we
	 * want to RTrim
	 *
	 */

	// We don't want to trip JUST spaces, but also tabs,
	// line feeds, etc. Add anything else you want to
	// "trim" here in Whitespace
	var whitespace = new String(" \t\n\r");
	var s = new String(str);

	if (whitespace.indexOf(s.charAt(s.length - 1)) != -1) {
		// We have a string with trailing blank(s)...
		var i = s.length - 1; // Get length of string

		// Iterate from the far right of string until we
		// don't have any more whitespace...
		while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
			i--;

		// Get the substring from the front of the string to
		// where the last non-whitespace character is...
		s = s.substring(0, i + 1);
	}

	return s;
}

function Trim(str) {
	/*
	 * PURPOSE: Remove trailing and leading blanks from our string. IN: str -
	 * the string we want to Trim
	 *
	 * RETVAL: A Trimmed string!
	 */
	return RTrim(LTrim(str));
}

function ReplaceAll(inText, inFindStr, inReplStr, inCaseSensitive) {
	var searchFrom = 0;
	var offset = 0;
	var outText = "";
	var searchText = "";
	if (inCaseSensitive == null) {
		inCaseSensitive = false;
	}

	if (inCaseSensitive) {
		searchText = inText.toLowerCase();
		inFindStr = inFindStr.toLowerCase();
	} else {
		searchText = inText;
	}

	offset = searchText.indexOf(inFindStr, searchFrom);
	while (offset != -1) {
		outText += inText.substring(searchFrom, offset);
		outText += inReplStr;
		searchFrom = offset + inFindStr.length;
		offset = searchText.indexOf(inFindStr, searchFrom);
	}
	outText += inText.substring(searchFrom, inText.length);

	return (outText);
}

function replaceurl(inText) {
	inText = ReplaceAll(inText, '%', '%25');
	inText = ReplaceAll(inText, '&', '%26');
	inText = ReplaceAll(inText, '<', '%3C');
	inText = ReplaceAll(inText, '>', '%3E');
	inText = ReplaceAll(inText, '=', '%3D');
	inText = ReplaceAll(inText, '+', '%2B');
	inText = ReplaceAll(inText, '#', '%23');
	inText = ReplaceAll(inText, '?', '%40');
	outText = inText;
	return (outText);
}

jQuery(document).ready(function() {

    jQuery('textarea[maxlength]').keyup(function(){
        //get the limit from maxlength attribute
        var limit = parseInt($(this).attr('maxlength'));
        //get the current text inside the textarea
        var text = $(this).val();
        //count the number of characters in the text
        var chars = text.length;

        //check if there are more characters then allowed
        if(chars > limit){
            //and if there are use substr to get the text before the limit
            var new_text = text.substr(0, limit);

            //and change the current text with the new text
            $(this).val(new_text);
        }
    });

});

function validateRowRequireInput(tableId, msg){
	if (msg == undefined) {
		msg = validateMessage.CODE_10019;		// ข้อมูลไม่เพียงพอ
	}
	var eles = jQuery("#"+tableId+" tr .requireSelect");
	for(var i=0;i < eles.length;i++){
		if ((eles[i].tagName == 'SPAN') || (eles[i].tagName == 'span') || (eles[i].tagName == 'DIV') || (eles[i].tagName == 'div') || (eles[i].tagName == 'LABEL') || (eles[i].tagName == 'label')) {
			continue;
		}
		if ((eles[i].type == 'radio') || (eles[i].type == 'checkbox')) {
			if (findChecked(eles[i].name).length == 0) {
				alert(msg);
				document.getElementsByName(eles[i].name)[0].focus();
				return false;
			} else {
				document.getElementsByName(eles[i].name)[0].className = document.getElementsByName(eles[i].name)[0].className.replace("requireSelect", "requireInput");
				document.getElementById(eles[i].name + 'GroupId').className = document.getElementById(eles[i].name + 'GroupId').className.replace("requireSelect", "requireGroup");
			}
		} else {
			//alert(eles[i].name + ": " + !eles[i].disabled + " && " + !eles[i].readOnly );
			if(!eles[i].disabled && trim(eles[i].value)==''){
				alert(msg);
				eles[i].focus();
				return false;
			} else if(!eles[i].disabled && (trim(eles[i].value) == '__/__/____')){
				alert(msg);
				eles[i].focus();
				return false;
			} else {
				eles[i].className = eles[i].className.replace("requireSelect", "requireInput");
			}
		}
	}

	eles = jQuery("#"+tableId+" tr .requireInput");
	for(var i=0;i < eles.length;i++){
		if ((eles[i].type == 'radio') || (eles[i].type == 'checkbox')) {
			if (findChecked(eles[i].name).length == 0) {
				alert(msg);
				//alert(jQuery(eles).attr('name'));
				document.getElementsByName(eles[i].name)[0].focus();
				document.getElementsByName(eles[i].name)[0].className = document.getElementsByName(eles[i].name)[0].className.replace("requireInput", "requireSelect");

				document.getElementById(eles[i].name + 'GroupId').className = document.getElementById(eles[i].name + 'GroupId').className.replace("requireGroup","requireSelect");
				return false;
			}
		} else {
			if(!eles[i].disabled && trim(eles[i].value)==''){
				alert(msg);
				eles[i].focus();
				eles[i].className = eles[i].className.replace("requireInput", "requireSelect");
				return false;
			} else if(!eles[i].disabled && (trim(eles[i].value) == '__/__/____')){
				alert(msg);
				eles[i].focus();
				eles[i].className = eles[i].className.replace("requireInput", "requireSelect");
				return false;
			}
		}
	}
	return true;
}

function validateRow(tableId) {

	var pass = validateRowRequireInput(tableId);
	if (pass) {
		return validateRowInput(tableId);
	} else {
		return false;
	}
}

function validateRowInput(tableId){
	//เคลียร์กรอบแดง
	findAndReplaceClassByRowTable(tableId, 'requireEmailSelect', 'input_email');
	findAndReplaceClassByRowTable(tableId, 'requireIDCardSelect', 'input_idcard');
	findAndReplaceClassByRowTable(tableId, 'requirePasswordFormatSelect', 'input_password_format');

	findAndReplaceClassByRowTable(tableId, 'requireDateformat_dd_sl_mm_sl_yyyy_select', 'input_dateformat_dd_sl_mm_sl_yyyy');
	findAndReplaceClassByRowTable(tableId, 'requireDateformat_yyyy_sl_mm_sl_dd_select', 'input_dateformat_yyyy_sl_mm_sl_dd');
	findAndReplaceClassByRowTable(tableId, 'requireDateformat_mm_sl_dd_sl_yyyy_select', 'input_dateformat_mm_sl_dd_sl_yyyy');
	findAndReplaceClassByRowTable(tableId, 'requireDateformat_yyyy_sl_dd_sl_mm_select', 'input_dateformat_yyyy_sl_dd_sl_mm');
	findAndReplaceClassByRowTable(tableId, 'requireDateformat_mm_sl_yyyy_select', 'input_dateformat_mm_sl_yyyy');
	findAndReplaceClassByRowTable(tableId, 'requireDateformat_yyyy_sl_mm_select', 'input_dateformat_yyyy_sl_mm');
	findAndReplaceClassByRowTable(tableId, 'requireDateformat_yyyy_select', 'input_dateformat_yyyy');

	findAndReplaceClassByRowTable(tableId, 'requireTimeformat_hh_cl_mm_select', 'input_timeformat_hh_cl_mm');
	// Select class ที่ขึั้นต้น input_ สำหรับตรวจรูปแบบ
	var eles = jQuery("#"+tableId+" tr input[class*='input_']");
	for (var index = 0; index < eles.length; index++) {
		if (jQuery(eles[index]).is('[disabled]')) {
			continue;
		}
		var className = eles[index].className;
		if (className.indexOf('input_email') > -1) {
			if (validateEmailFormatFormIndex(eles[index]) == false) {
				return false;
			}
		} else if (className.indexOf('input_idcard') > -1) {
			if (validateIDCardFormatFormIndex(eles[index]) == false) {
				return false;
			}
		} else if (className.indexOf('input_password_format') > -1) {
			if (validatePasswordFormatFormIndex(eles[index]) == false) {
				return false;
			}
		} else if (className.indexOf('input_dateformat_') > -1) {
			var status = inputValidateDateFormatValueName(eles[index]);
			//console.info('status: ' + status);
			if ((status == 'empty') || status == 'ok') {

			} else {
				//console.info('className: ' + className);
				if (className.indexOf('input_dateformat_dd_sl_mm_sl_yyyy') > -1) {
					eles[index].className = eles[index].className.replace("input_dateformat_dd_sl_mm_sl_yyyy", "requireDateformat_dd_sl_mm_sl_yyyy_select");
				} else if (className.indexOf('input_dateformat_yyyy_sl_mm_sl_dd') > -1) {
					eles[index].className = eles[index].className.replace("input_dateformat_yyyy_sl_mm_sl_dd", "requireDateformat_yyyy_sl_mm_sl_dd_select");
				} else if (className.indexOf('input_dateformat_mm_sl_dd_sl_yyyy') > -1) {
					eles[index].className = eles[index].className.replace("input_dateformat_mm_sl_dd_sl_yyyy", "requireDateformat_mm_sl_dd_sl_yyyy_select");
				} else if (className.indexOf('input_dateformat_yyyy_sl_dd_sl_mm') > -1) {
					eles[index].className = eles[index].className.replace("input_dateformat_yyyy_sl_dd_sl_mm", "requireDateformat_yyyy_sl_dd_sl_mm_select");
				} else if (className.indexOf('input_dateformat_mm_sl_yyyy') > -1) {
					eles[index].className = eles[index].className.replace("input_dateformat_mm_sl_yyyy", "requireDateformat_mm_sl_yyyy_select");
				} else if (className.indexOf('input_dateformat_yyyy_sl_mm') > -1) {
					eles[index].className = eles[index].className.replace("input_dateformat_yyyy_sl_mm", "requireDateformat_yyyy_sl_mm_select");
				} else if (className.indexOf('input_dateformat_yyyy') > -1) {
					eles[index].className = eles[index].className.replace("input_dateformat_yyyy", "requireDateformat_yyyy_select");
				}

				if (status == "error all") {
					alert(validateMessage.CODE_10020);			// กรุณาตรวจสอบรูปแบบวันที่
				} else if (status == "not year") {
					alert(validateMessage.CODE_10021);			// กรุณาตรวจสอบรูปแบบปี
				} else if (status == "not month") {
					alert(validateMessage.CODE_10022);			// กรุณาตรวจสอบรูปแบบเดือน
				} else if (status == "not day") {
					alert(validateMessage.CODE_10023);			// กรุณาตรวจสอบรูปแบบวัน
				}
				eles[index].focus();
				return false;
			}
		} else if (className.indexOf('input_timeformat_') > -1) {
			var status = inputValidateTimeFormatValueName(eles[index]);
			//console.info('status: ' + status);
			if ((status == 'empty') || status == 'ok') {

			} else {
				//console.info('className: ' + className);
				if (className.indexOf('input_timeformat_hh_cl_mm') > -1) {
					eles[index].className = eles[index].className.replace("input_timeformat_hh_cl_mm", "requireTimeformat_hh_cl_mm_select");
				}

				if (status == "error all") {
					alert(validateMessage.CODE_10011);			// กรุณาตรวจสอบข้อมูลเวลา
				} else if (status == "not hour") {
					alert(validateMessage.CODE_10011);			// กรุณาตรวจสอบข้อมูลเวลา
				} else if (status == "not minute") {
					alert(validateMessage.CODE_10011);			// กรุณาตรวจสอบข้อมูลเวลา
				}
				eles[index].focus();
				return false;
			}
		}
	}
	return true;
}

function findAndReplaceClass(formIndex, classForFind, classForReplace) {
	var elesSelect = jQuery("#"+document.forms[formIndex].id + " ." + classForFind);
	for(var i = 0; i < elesSelect.length; i++){
		elesSelect[i].className = elesSelect[i].className.replace(classForFind, classForReplace);
	}
}

function findAndReplaceClassByRowTable(tableId, classForFind, classForReplace) {
	var elesSelect = jQuery("#"+tableId+" tr ." + classForFind);
	for(var i = 0; i < elesSelect.length; i++){
		elesSelect[i].className = elesSelect[i].className.replace(classForFind, classForReplace);
	}
}
/**
* ตรวจสอบการเลือกข้อมูล
* elName คือ ชื่อของ input ที่ต้องการตรวจสอบ
* return false เมื่อไม่มีการเลือกข้อมูล
* return tr ขอข้อมูลที่ถูกเลือก
*/
function validateSelectOne(elName) {

	//	Arunya.k  รองรับกรณีส่ง element มา
	if (typeof elName === 'object') {
		var arrayChecked = elName;
	}else{
		var arrayChecked = findChecked(elName);
	}

	if (arrayChecked.length == 0){
		alert(validateMessage.CODE_10001);		// คุณต้องทำการเลือกอย่างน้อย 1 รายการ
		return false;
	}

	var arrayCheckedTr = null;
	var notFoundTr = true;
	var limit = 10;
	var checked = arrayChecked[0];
	while (true) {
		if ((checked.tagName == 'TR') || (checked.tagName == 'tr')) {
			notFoundTr = false;
			break;
		} else {
			if (arrayCheckedTr == null) {
				arrayCheckedTr = arrayChecked.parent();
			} else {
				arrayCheckedTr = arrayCheckedTr.parent();
			}
			checked = arrayCheckedTr[0];
		}

		limit--;
		if (limit < 0) {
			break;
		};
	}

	if (notFoundTr){
		alert(validateMessage.CODE_10001);		// คุณต้องทำการเลือกอย่างน้อย 1 รายการ
		return false;
	}

	return arrayCheckedTr;
}

/**
* ตรวจสอบการเลือกข้อมูล
* elName คือ ชื่อของ input ที่ต้องการตรวจสอบ
* return 0 เมื่อไม่มีการเลือกข้อมูล
* return >= 1  เมื่อเลือกข้อมูล
*/
function findChecked(checkboxName) {
		return jQuery("input[name='" + checkboxName + "']:checked");
}

/**
* ตรวจสอบการเลือกข้อมูล
* elName คือ ชื่อของ input ที่ต้องการตรวจสอบ
* return false เมื่อไม่พบการเลือก
* return true เมื่อเลือกข้อมูล
*/
function validateSelect(elName){
	if (findChecked(elName).length == 0) {
		alert(validateMessage.CODE_10001);		// คุณต้องทำการเลือกอย่างน้อย 1 รายการ
		return false;
	} else {
		return true;
	}
}

// ตรวจสอบรูปแบบ ip address
function validateIpaddressFormatFormIndex(el, msg) {
	if (msg == undefined) {
		msg = validateMessage.CODE_10019;		// ข้อมูลไม่เพียงพอ
	}

	var elHtml = el;
	// คืนสถานะของ ip address เป็น require input
	jQuery("#"+jQuery(elHtml).attr('id').replace("_IPADDRESS_BLOCK_1", "").replace("_IPADDRESS_BLOCK_2", "").replace("_IPADDRESS_BLOCK_3", "").replace("_IPADDRESS_BLOCK_4", "")).input_ipaddress('toRequireFormat');
	var status = checkIpaddressFormat(elHtml);
	if(!elHtml.disabled && (trim(elHtml.value) != '') && (status != true)){
		alert(msg);
		status.focus();
		jQuery("#"+jQuery(elHtml).attr('id').replace("_IPADDRESS_BLOCK_1", "").replace("_IPADDRESS_BLOCK_2", "").replace("_IPADDRESS_BLOCK_3", "").replace("_IPADDRESS_BLOCK_4", "")).input_ipaddress('toRequireSelectFormat');
		return false;
	}
	return true;
}

function checkIpaddressFormat(elHtml){
	return jQuery("#"+jQuery(elHtml).attr('id').replace("_IPADDRESS_BLOCK_1", "").replace("_IPADDRESS_BLOCK_2", "").replace("_IPADDRESS_BLOCK_3", "").replace("_IPADDRESS_BLOCK_4", "")).input_ipaddress('validateIpaddressValue');
}

/*
 * ตรวจสอบรูปแบบ email
 */
function validateEmailFormat(elementIds, msg) {

	var elArray = elementIds.split(',');
	for (var index in elementIds.split(',')) {
		var el = document.getElementById(trim(elArray[index]));
		if (validateEmailFormatFormIndex(el, msg) == false) {
			return false;
		}
	}
	return true;
}

/*
 * ตรวจสอบรูปแบบ บัตรประชาชน
 */
function validateIDCardFormat(elementIds, msg) {

	var elArray = elementIds.split(',');
	for (var index in elementIds.split(',')) {
		var el = document.getElementById(trim(elArray[index]));
		if (validateIDCardFormatFormIndex(el, msg) == false) {
			return false;
		}
	}
	return true;
}

function validateIDCardFormatFormIndex(el, msg) {
	if (msg == undefined) {
		msg = validateMessage.CODE_10002.replace('xxx',el.value);		// xxx ไม่สามารถว่างได้
	}
	var elHtml = el;
	elHtml.className = elHtml.className.replace("requireIDCardSelect", "input_idcard");
	if(!elHtml.disabled && (trim(elHtml.value) != '') && !checkIDCardFormat(trim(elHtml.value))){
		alert(msg);
		elHtml.focus();
		elHtml.className = elHtml.className.replace("input_idcard", "requireIDCardSelect");
		return false;
	}
	return true;
}

//*** ตรวจสอบหมายเลขบัตรประชาชน  กรณีที่เลือกประเภทเอกสารอ้างอิง เป็นบัตรประชาชน ***
function checkIDCardFormat(id){
	if(id.length != 13) return false;
    for(i=0, sum=0; i < 12; i++)
        sum += parseFloat(id.charAt(i))*(13-i);
    if((11-sum%11)%10!=parseFloat(id.charAt(12))) return false;
    return true;
}

function msieversion() {
    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");
    if (msie > 0) {      // If Internet Explorer, return version number
        //alert(parseInt(ua.substring(msie + 5, ua.indexOf(".", msie))));
        return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
    } else {               // If another browser, return 0
        //alert('otherbrowser');
        return 999;
    }
}

/**
 * ตรวจสอบ รูปแบบ วัน เดือน เกิด แยกช่อง text
 * @param eleDD : id text วัน
 * @param eleMM : id text เดือน
 * @param eleYYYY : id text ปี
 */
function checkDateInput(eleDD ,eleMM ,eleYYYY){

	 jQuery("#"+eleDD).blur(function() {
		  	var dd = jQuery(this).val();

		  	if(dd != '') {

			  	if(dd >= 1 && dd <= 31 || parseInt(dd) == 0) {
			  		if(dd.length == 1) {
			  			jQuery(this).val("0" + dd);
			  		}
			  	} else {
			  		jQuery(this).val("");
			  	}
		  	} else {
		  		jQuery(this).val("");
		  	}

		  	validateDate(eleDD,eleMM);
	 });

	jQuery("#"+eleMM).blur(function() {
	  	var mm = jQuery(this).val();

	  	if(mm != '') {

		  	if(mm >= 1 && mm <= 12 || parseInt(mm) == 0) {
		  		if(mm.length == 1) {
		  			jQuery(this).val("0" + mm);
		  		}
		  	} else {
		  		jQuery(this).val("");
		  	}
	  	} else {
	  		jQuery(this).val("");
	  	}

	  	validateDate(eleDD,eleMM);
	});

	jQuery("#"+eleYYYY).blur(function() {
	  	var yyyy = jQuery(this).val();

	  	if(yyyy.length != 4) {
	  		jQuery(this).val("");
	  	}
	});
}

function validateDate(eleDD ,eleMM) {
	var dd = jQuery("#"+eleDD).val();
	var mm = jQuery("#"+eleMM).val();

	if(dd != '' && mm != '') {

		var month = parseInt(mm);
		var day = parseInt(dd);

		if(parseInt(dd) > 0 && parseInt(mm) > 0) {

			if(month == 4 || month == 6 || month == 9 || month == 11) {

				if(day > 30) {
					jQuery("#"+eleDD).val("30");
				}
			} else	if (month == 2) {
				if(day > 29) {
					jQuery("#"+eleDD).val("29");
				}

			} else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				if(day > 31) {
					jQuery("#"+eleDD).val("31");
				}
			}
		}
	}
}

function validateEmailFormatFormIndex(el) {
	if(!checkEmailFormat(trim(jQuery(el).val()))){
		return false;
	}
	return true;
}

function checkEmailFormat(elementValue){
	  var emailPattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  return emailPattern.test(elementValue);
}


function checkGetherCurrentDate(val){
	var date = val.substring(0, 2);
	var month = val.substring(3, 5);
	var year = val.substring(6, 10);

	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!

	var yyyy = today.getFullYear();
	if(dd<10){
	    dd='0'+dd
	}
	if(mm<10){
	    mm='0'+mm
	}
	var todayStr = yyyy+""+mm+""+dd;
	var dateToCompare = year+month+date;

	if (dateToCompare > todayStr) {
	    return true;
	}
	else {
	    return false;
	}
}

/**
 * check date time start and date time end
 * @param startDate : 12/01/2016
 * @param endDate :12/01/2016
 * @param startTime :00:00
 * @param endTime : 00:00
 * @returns {Boolean}
 */
function validateCompareDatetime(dateCheckId , dateCompareId, timeCheckId, timeCompareId, validateMessage) {
	var startDate = jQuery('#' + dateCheckId).val();
	var endDate = jQuery('#' + dateCompareId).val();
	var startTime = jQuery('#' + timeCheckId).val();
	var endTime = jQuery('#' + timeCompareId).val();
	
	
	if (startDate != '' && endDate != '') {
		var tempForm = startDate.split("/");
		var tempTo = endDate.split("/");
		var dateForm = new Date(tempForm[2]+"/"+tempForm[1]+"/"+tempForm[0]);
		var dateTo = new Date(tempTo[2]+"/"+tempTo[1]+"/"+tempTo[0]);
		
		var timeDiff = Math.abs(dateForm.getTime() - dateTo.getTime());
		var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
		
		if (diffDays == 0) {
			if(startTime != '' && endTime != ''){
		    	var sts = startTime.split(":");
		    	var ets = endTime.split(":");
		    	
		    	var stMin = (parseInt(sts[0]) * 60 + parseInt(sts[1]));
		    	var etMin = (parseInt(ets[0]) * 60 + parseInt(ets[1]));
		    	if ( stMin > etMin) {
		    		jQuery(".MESSAGE").append(validateMessage + "<br/>");
		    		jQuery('#' + timeCheckId +"_hh_cl_mm").addClass("border_select");
		    		jQuery('#' + timeCompareId +"_hh_cl_mm").addClass("border_select");
		    		return false;
		    	} else {
		    		jQuery(".MESSAGE").append("");
		    		return true;
		    	}
			}
		} else {
			return true;
		}
	}
	return true;
}