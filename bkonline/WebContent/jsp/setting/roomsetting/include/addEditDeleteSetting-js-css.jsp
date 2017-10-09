<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	
function sf() {
	initPage();
	initTableAllRoom();
	initTableTimeClose();
}

function initPage() {
	var roomId = jQuery("input[name='roomSetting.id']").val();
	if (roomId != '') {
		initButtonSet('EDIT');
	} else {
		initButtonSet('ADD');		
	}
	
	var equipmentListId = jQuery("input[name='roomSetting.equipmentListId']").val(); 
	if (equipmentListId != '') {
		initEquipmentListId(equipmentListId);	
	} else {
		
	}
}

function initTableAllRoom() {
	jQuery('#tableAllRoom').dataTable({
		jQueryUI: true
		, paging: false
		, ordering: false
		, filter: false
		, destroy: true
		, language: getTableAllRoomTitle()
		, dom: "<'top'ip><'H'>rt<'bottom'p><'F'>"
		, drawCallback: function( settings ) {
			// กำหนดความสูง Footer
			jQuery("#tableAllRoom_wrapper > .fg-toolbar:last").css( "height", "35" )
			setCssHover("tableAllRoom");
		}
	});	
}

function getTableAllRoomTitle() {
	var txtTitleHtml = '<div style="float: left; padding: 4px 10px 4px 10px;"><b><s:text name="numberOfSearch" />&nbsp;<s:property value="listAllRoom.size()"/>&nbsp;<s:text name="record(s)" /></b></div>';
	var oLanguage = {
			zeroRecords: validateMessage.CODE_30011,
	        info: txtTitleHtml,
	        infoEmpty: txtTitleHtml
	};
	return oLanguage;
}

function searchRoomDetail(roomId) {
	jQuery("input[name='roomSetting.id']").val(roomId);
	jQuery.ajax({
		type: "POST"
		, data: { 'roomSetting.id': roomId }
		, url: "<s:url value='/jsp/setting/ajaxSearchByRoomIdRoomSetting.action'/>"
		, async: true // ให้ทำงานพร้อมกันทำ ajax ให้เสร็จก่อน ค่อยทำ java script 
		, success: function(data){
			if (data.messageAjax.messageType == 'E') {
				showNotifyMessageError(data.messageAjax.message, data.messageAjax.messageDetail);
			} else {
				initButtonSet('EDIT');
				clearRoomDetailForm();
				bindingJsonToRoomDetail(data.roomSetting);
			}
		}
	});
}

function bindingJsonToRoomDetail(roomSetting) {
	jQuery('input[name="roomSetting.name"]').val(roomSetting.name);
	
	jQuery("#roomSetting_color").val(roomSetting.color);
	jQuery("#roomSetting_color").selectmenu("refresh");
	
	jQuery("#roomSetting_autotime").val(roomSetting.autotime);
	jQuery("#roomSetting_autotime").selectmenu("refresh");
	
	jQuery('input[name="roomSetting.phone"]').val(roomSetting.phone);
	jQuery('input[name="roomSetting.attendeesMax"]').val(roomSetting.attendeesMax);
	jQuery('input[name="roomSetting.priority"]').val(roomSetting.priority);
	
	jQuery("#roomSetting_priority").val(roomSetting.priority);
	jQuery("#roomSetting_priority").selectmenu("refresh");
	
	// initEquipmentListId(roomSetting.equipmentListId);
	
	jQuery('textarea[name="roomSetting.detail"]').val(roomSetting.detail);
	
	// console.info(roomSetting);
	// console.info(roomSetting.listTimeClosed.length);
	for (var index = 0; index < roomSetting.listTimeClosed.length; index++) {
		var timeClosed = roomSetting.listTimeClosed[index];
		var dateValue = new Object();
		dateValue.dateForDB = timeClosed.startDate;
		dateValue.dateForShow = timeClosed.startDateForShow;
		createRowTableTimeClose(dateValue, timeClosed.startTime, timeClosed.endTime, timeClosed.id, roomSetting.id);
	}
}

function initEquipmentListId(equipmentListId) {
	jQuery("input[name='roomSetting.equipmentListId']").val(equipmentListId); 
	if (equipmentListId != '') {
		var arrId = equipmentListId.split(',');
		for(var index = 0; index < arrId.length; index++) {
			jQuery('input[name="equipmentList"][value="' + arrId[index].trim() + '"]').prop('checked', true);
		}
	} else {
		jQuery('input[name="equipmentList"]').prop('checked', false);
	}
}

function clearRoomDetailForm() {
	clearRooTableTimeClose();
	
	jQuery('input[name="roomSetting.name"]').val("");
	
	jQuery("#roomSetting_color").val("");
	jQuery("#roomSetting_color").selectmenu("refresh");
	
	jQuery("#roomSetting_autotime").val("0");
	jQuery("#roomSetting_autotime").selectmenu("refresh");
	
	jQuery('input[name="roomSetting.phone"]').val("");
	jQuery('input[name="roomSetting.attendeesMax"]').val("");
	
	jQuery('input[name="equipmentList"]').prop('checked', false);
	
	jQuery('input[name="roomSetting.detail"]').val("");
}

function initButtonSet(type) {
	jQuery("#btnAddTime").button();
	jQuery("#tdButtonSet").empty();
	var btnA;
	var btnB;
	if (type == 'ADD') {
		btnA = jQuery('<button id="btnSaveAdd" class="btnSaveAdd" type="button" onclick="saveAdd();"><s:text name="create" /></button>');
		btnB = jQuery('<button id="btnCancelAdd" class="btnCancelAdd" type="button" onclick="cancelAdd();"><s:text name="cancel" /></button>');
		btnA.appendTo(jQuery("#tdButtonSet"));
		btnB.appendTo(jQuery("#tdButtonSet"));
		
		jQuery("#btnSaveAdd").button();
		jQuery("#btnCancelAdd").button();
	} else if (type == 'EDIT') {
		btnA = jQuery('<button id="btnSaveEdit" class="btnSaveEdit" type="button" onclick="saveEdit();"><s:text name="save" /></button>');
		btnB = jQuery('<button id="btnCancelEdit" class="btnCancelEdit" type="button" onclick="cancelEdit();"><s:text name="cancel" /></button>');
		btnA.appendTo(jQuery("#tdButtonSet"));
		btnB.appendTo(jQuery("#tdButtonSet"));

		jQuery("#btnSaveEdit").button();
		jQuery("#btnCancelEdit").button();
	}
}

function initTableTimeClose() {
	jQuery("#boxHeaderdivTableTimeClose > table > tbody > tr > th.edit").remove();
	jQuery("#boxDetaildivTableTimeClose > table > tbody > tr > td.edit").remove();
	jQuery("#divTableTimeClose > div.RESULT_BOX_TITAL > table.TOTAL_RECORD > tbody > tr > td").css('height', '2px')
}

function bindingHTMLToModel() {
	var equipmentListId = '';
	jQuery('input[name="equipmentList"]').each(function() {
		if (this.checked) {
			if (equipmentListId != '') {
				equipmentListId = equipmentListId + ','
			}
			equipmentListId = equipmentListId + jQuery(this).val();
		}
	});
	jQuery("input[name='roomSetting.equipmentListId']").val(equipmentListId); 
}

function receiveTableTimeCloseEditedInForm() {
	// console.info('receiveTableTimeCloseEditedInForm');
}

function addTableTimeClose() {
	// console.info('addTableTimeClose');
    //other column 1, 2, 3, ..., n dateForDB, dateForShow
    var roomId = jQuery('input[name="roomSetting.id"]').val();
    var dateStartValue = jQuery("#roomSetting_startDate").input_dateformat('dateValue');
    var dateEndValue = jQuery("#roomSetting_endDate").input_dateformat('dateValue');
    var timeStartValue = jQuery("#roomSetting_startTime").val();
    var timeEndValue = jQuery("#roomSetting_endTime").val();
    
    if (dateStartValue.dateForDB == '' || dateEndValue.dateForDB == '' || timeStartValue == '' || timeEndValue == '') {
		alert('Date is empty');
		return;
    } 
    
    var intTotalDay = conpareDateFormatById("roomSetting_endDate", "roomSetting_startDate");
    if (intTotalDay.day > 0) {
    	alert('ระบบจะตัดวันและลงเวลาเป็นรายวันให้');	
    }
    
    // console.info('totalDay: ' + intTotalDay.day);
    for (var round = 0; round <= intTotalDay.day; round++) {
    	var dateSelect = dateIncrese(dateStartValue, round);
    	createRowTableTimeClose(dateSelect, timeStartValue, timeEndValue, '', roomId);
    }
}

function dateIncrese(dateValue, increseValue) {
	
	// ตรวจสอบความยาวพอตัดไหม
	if (dateValue.dateForDB.length != 10) {
		return dateValue;
	}
		
	// ตัดค่า วัน เดือน ปี
	var dayValue = parseInt(dateValue.dateForDB.substring(0, 2));
	var monthValue = parseInt(dateValue.dateForDB.substring(3, 5)) - 1;
	var yearValue = parseInt(dateValue.dateForDB.substring(6, 10));
	
	// สร้าง Date object
	var dateValue = new Date(yearValue, monthValue, dayValue, 0, 0, 0, 0);
	
	// เพิ่มวันตามจำนวน
	var dateIncreseValue = new Date(dateValue.getTime() + (increseValue * 86400000));
	
	var txtDay = dateIncreseValue.getDate() + "";
	if (txtDay.length == 1) {
		txtDay = '0' + txtDay;
	}
	
	var txtMonth = (dateIncreseValue.getMonth() + 1) + "";
	if (txtMonth.length == 1) {
		txtMonth = '0' + txtMonth;
	}
	
	var txtYearDB = dateIncreseValue.getFullYear();
	var txtYearShow = txtYearDB;
	if (isInputDateLocaleThai()) {
		txtYearShow = txtYearShow + 543;
	}
	
	// ส่ง Date object กลับไป
	dateValue.dateForDB = txtDay + '/' + txtMonth + '/' + txtYearDB;
	dateValue.dateForShow = txtDay + '/' + txtMonth + '/' + txtYearShow;
	
	return dateValue;
}

function clearRooTableTimeClose() {
	var txtTableId = "tableId_divTableTimeClose";
    jQuery('table#' + txtTableId + ' tbody tr').remove();
}

function createRowTableTimeClose(dateStartValue, timeStartValue, timeEndValue, timeClosedId, roomId) {
	// console.info('createRowTableTimeClose');
    var txtTableId = "tableId_divTableTimeClose";
    var intTableIndex = jQuery('table#' + txtTableId + ' tbody tr').length;
    var arrColumn = ["checkbox", "col-width-150px date", "col-width-125px date", "col-width-125px date"];
    var arrElm = new Array();
	
	//elemnet 3 element ที่ต้องวาดคือ  input checkbox, hidden deleteFlag, hidden id
    arrElm[0] = "<input type='checkbox' id='cnkColumnId' name='cnkColumnId' value='" + timeClosedId + "'/>";
    arrElm[0] += "<input type='hidden' name='roomSetting.listTimeClosed["+intTableIndex+"].deleteFlag' value=''/>";
    arrElm[0] += "<input type='hidden' name='roomSetting.listTimeClosed["+intTableIndex+"].id' value='" + timeClosedId + "'/>";
    arrElm[0] += "<input type='hidden' name='roomSetting.listTimeClosed["+intTableIndex+"].roomId' value='" + roomId + "'/>";
    arrElm[0] += "<input type='hidden' name='roomSetting.listTimeClosed["+intTableIndex+"].startDate' value='"+ dateStartValue.dateForDB +"'/>";
    arrElm[0] += "<input type='hidden' name='roomSetting.listTimeClosed["+intTableIndex+"].startDateForShow' value='"+ dateStartValue.dateForShow +"'/>";
    arrElm[0] += "<input type='hidden' name='roomSetting.listTimeClosed["+intTableIndex+"].startTime' value='"+ timeStartValue +"'/>";
    arrElm[0] += "<input type='hidden' name='roomSetting.listTimeClosed["+intTableIndex+"].endTime' value='"+ timeEndValue +"'/>";
        
    arrElm[1] = dateStartValue.dateForShow;
    arrElm[2] = timeStartValue
    arrElm[3] = timeEndValue
	     
	//2. create row 
	tableCreateTableRow(txtTableId, arrColumn, arrElm);
}

function saveAdd(){
	
	bindingHTMLToModel();
	
    //1.ขั้นตอนการตรวจสอบ validate
    if (!validateFormInputAll()) {
			return;
	}
     
    //2.Confirm dialog
    if(confirm('<s:text name="50003" />') == false){ 
        return false;
    }
    submitPage("<s:url value='/jsp/setting/addRoomSetting.action' />");     
}

function saveEdit(){
	
	bindingHTMLToModel();
	
    //1.ขั้นตอนการตรวจสอบ validate
    if (!validateFormInputAll()) {
			return;
	}
    
    //2.Confirm dialog
    if(confirm('<s:text name="50004" />') == false){  
        return false;
    }
    submitPage("<s:url value='/jsp/setting/editRoomSetting.action' />");    
}

function cancelAdd() {
	if (confirm('<s:text name="50009" />') == false) {
        return false;
    }
	submitPage("<s:url value='/jsp/setting/initRoomSetting.action' />");
}

function cancelEdit() {
	if (confirm('<s:text name="50010" />') == false) {
        return false;
    }
	submitPage("<s:url value='/jsp/setting/initRoomSetting.action' />");
}






//***
//------ ลบห้องประชุม
function deleted() {
	if (confirm('<s:text name="bk.alertDelete" />') == false) {
    	return false;
    }
	submitPage("<s:url value='/jsp/setting/deleteRoomSetting.action' />");
}

</script>
<style type="text/css">
	DIV.DIV_ALL_ROOM {
		width: 35%;
		float: left;
		/** border: 1px solid red; **/
	}
	
	DIV.DIV_IN_FORM {
		width: 63%;
		float: right;
		/** border: 1px solid red; **/
	}
	
	TABLE.tableAllRoom {
		width: 100%;
	}
	
	TABLE.tableAllRoom TH.status, TABLE.tableAllRoom TD.status {
		width: 75px;
	}
	
	#tableAllRoom_wrapper.dataTables_wrapper {
		width: 94%;
		padding-left: 25px;
	}
	
	TABLE.FORM TD {
		/** border: 1px red solid; **/ 
	}
	
	TABLE.FORM TD.LABEL_SETTING_FIRST {
		width: 22%;
	}
	
	TABLE.FORM TD.LABEL_SETTING {
		width: 19%;
	}
	
	TABLE.FORM TD.VALUE_SETTING {
		width: 28%;
	}
	
	TABLE.FORM TD.BORDER_SETTING {
		width: auto;
	}

	.datepicker {
	    width: 100px;
	}
	
	.timepicker {
		width: 55px;
	} 
	
	@media screen and (min-width: 1600px){
		.datepicker {
	    	width: 110px;
		}
		
		.timepicker {
			width: 65px;
		} 
	}
	
	DIV.RESULT_TIME_CLOSE {
		background-color: transparent;
	}
	
	DIV.RESULT_BOX_DETAIL {
		background-color: white;
	}
	
	DIV.EQUIPMENT_BLOCK {
		width: 150px;
		float: left;
		padding-top: 3px;
	}
</style>