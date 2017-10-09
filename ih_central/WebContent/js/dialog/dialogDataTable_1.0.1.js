var objDialogDataTable = {
		divPopupDatatable: "", //div id ของ popup
		chosenIds: [], //ค่า id ที่เลือก
		funChosenCallback: "", //function callback หลังจากเลือกข้อมูลบน Dialog
		searchByIdsAction: "", // action ในการ search ข้อมูลของ multi dialog หลังจากกดตกลงเลือกข้อมูล
		funClearCallback: "" //function ที่เอาไว้ clear ค่า criteria บนหน้า dialog
};

//CLEAR CRITERIA
function closeDialogDatatable() {
	
	clearDialogDatatable();
	
	//REMOVE OK BUTTON
	jQuery(objDialogDataTable.divPopupDatatable + " #buttonPopup").remove();
	
	//CLEAR SELECTED IDS
	jQuery(objDialogDataTable.divPopupDatatable + " input[name='criteria.selectedIds']" ).val("");
	
	//DESTROY DIALOG
	jQuery(objDialogDataTable.divPopupDatatable).dialog('destroy');
}

function clearDialogDatatable() {
	
	//HIDE DATATABLE RESULT 
	//SO WHEN THE DIALOG OPEN AGAIN IT WON'T SHOW THE RESULT BEFORE
	jQuery(objDialogDataTable.divPopupDatatable + " div.RESULT_SYSTEM div:first").hide();
	
	//HIDE OK BUTTON
	jQuery(objDialogDataTable.divPopupDatatable + " #buttonPopup").hide();
	
	//CLEAR CRITERIA KEY
	jQuery(objDialogDataTable.divPopupDatatable + " input[name='criteria.criteriaKey']" ).val("");
	
	//CLEAR CHOSEN IDS
	jQuery(objDialogDataTable.divPopupDatatable + " #criteria_chosenIds").val("");
	
	//CLEAR CHOSEN IDS
	objDialogDataTable.chosenIds = [];
	
	//CLEAR MESSAGE
	jQuery(".notify-close-btn").click();
	
 	if(objDialogDataTable.funClearCallback != '') {		
 		window[objDialogDataTable.funClearCallback]();
	}
	
}

/*
 * detail: function เมื่อ click ปุ่มตกลงที่หน้า popup ในกรณีที่เป็น popup เลือกมากกว่า 1 รายการ ในรูปแบบ dialog ที่มี navigate
 *           โดยจะทำการตรวจสอบการเลือกรายการ, custom รายการที่เลือกเข้า arrString และดำเนินการปิด popup
 * */
function dialogChooseMultiRecordForDatatable() {
	
	if (jQuery(objDialogDataTable.divPopupDatatable + ' #criteria_chosenIds').val() == "") {
		alert("you must select at least one record.");
		return false;
	}

	var params = {
		"criteria.selectedIds" : jQuery(objDialogDataTable.divPopupDatatable + ' #criteria_chosenIds').val()
	};
	
	jQuery.ajax({
		type : "POST",
		url : objDialogDataTable.searchByIdsAction ,
		data : jQuery.param(params),
		async : false,
		dataType: "json",
		success : function(data) {
			
			if(data.messageAjax.messageType == 'E') {
				alert(data.messageAjax.messageDetail);
				
			} else {
				
				closeDialogDatatable();
				
				if(objDialogDataTable.funChosenCallback != '') {		
			 		window[objDialogDataTable.funChosenCallback](data.lstResult);
				}
			}
		}
	});
	
	

}

/**
 * FOR MULTI CHOOSE DIALOG
 * ON CHECKBOX CHECKED ALL / UNCHECKED ALL
 * 
 * @param checkbox
 * @returns
 */
function dialogDatatableCheckAll(checkbox) {
	
	var freezeTableHeaderCheckboxSelector = jQuery(objDialogDataTable.divPopupDatatable + " .DTFC_LeftHeadWrapper").length == 0 ? "" : " .DTFC_LeftHeadWrapper";
	var freezeTableCheckboxSelector = jQuery(objDialogDataTable.divPopupDatatable + " .DTFC_LeftBodyLiner:last").length == 0 ? "" : " .DTFC_LeftBodyLiner:last";
	
	if(jQuery(checkbox).is(":checked")) {
		jQuery(objDialogDataTable.divPopupDatatable + freezeTableCheckboxSelector + " .d_datatable_chckbox:checkbox:not(:checked)").click();
	
	} else {
		jQuery(objDialogDataTable.divPopupDatatable + freezeTableCheckboxSelector + " .d_datatable_chckbox:checkbox:checked").click();
	}
}

/**
 * FOR MULTI CHOOSE DIALOG
 * ON CHECKBOX CHECKED / UNCHECKED
 * 
 * @param checkbox
 * @returns
 */
function dialogDatatableCheck(checkbox) {
	
	var val = jQuery(checkbox).val();
	var index = objDialogDataTable.chosenIds.indexOf(val);
	
	if(index == -1) {
		objDialogDataTable.chosenIds.push(val);
	} else {
		objDialogDataTable.chosenIds.splice(index, 1);
	}
	
	//set chosenIds to hidden input
	jQuery(objDialogDataTable.divPopupDatatable + ' #criteria_chosenIds').val(objDialogDataTable.chosenIds + "");
	
	var freezeTableHeaderCheckboxSelector = jQuery(objDialogDataTable.divPopupDatatable + " .DTFC_LeftHeadWrapper").length == 0 ? "" : " .DTFC_LeftHeadWrapper";
	var freezeTableCheckboxSelector = jQuery(objDialogDataTable.divPopupDatatable + " .DTFC_LeftBodyLiner:last").length == 0 ? "" : " .DTFC_LeftBodyLiner:last";
	
	//if all checkbox is checked
	if(jQuery(objDialogDataTable.divPopupDatatable + freezeTableCheckboxSelector + " .d_datatable_chckbox:checked").length == jQuery(objDialogDataTable.divPopupDatatable + freezeTableCheckboxSelector + " .d_datatable_chckbox").length) {
		//make header checkbox checked
		jQuery(objDialogDataTable.divPopupDatatable + freezeTableHeaderCheckboxSelector + " .d_datatable_chckbox_header:checkbox").prop('checked', true);
		
	} else {		
		//make header checkbox unchecked
		jQuery(objDialogDataTable.divPopupDatatable + freezeTableHeaderCheckboxSelector + " .d_datatable_chckbox_header:checkbox").prop('checked', false);
	}
	
}

/**
 * CLEAR CHOSEN IDS ตอนกดปุ่ม Search ใหม่
 * CLEAR ที่ datatable.js นะจ๊ะ
 */
function clearChosenIds() {
	jQuery(objDialogDataTable.divPopupDatatable + ' #criteria_chosenIds').val("");
	objDialogDataTable.chosenIds = [];
}

/**
 * FOR SINGLE CHOOSE DIALOG
 * SET ON DOUBLE CLICK EVENT (SINGLE CHOOSE)
 * 
 * @param tableId
 * @param lstResult
 * @returns
 */
function setOnDbClick(tableId, lstResult){
	
	jQuery(objDialogDataTable.divPopupDatatable + " " + tableId + ' tbody tr').each(function (i) {
		jQuery(this).addClass("cursor").attr("ondblclick", "closeDialogDatatable(); " + objDialogDataTable.funChosenCallback + "(" + JSON.stringify(lstResult[i]) + ");");
	});

	jQuery(objDialogDataTable.divPopupDatatable + ' .DTFC_LeftBodyLiner:last table.DTFC_Cloned tbody tr').each(function(nRow){
		jQuery(this).addClass("cursor").attr("ondblclick", "closeDialogDatatable(); " + objDialogDataTable.funChosenCallback + "(" + JSON.stringify(lstResult[nRow]) + ");");
    });
}

/**
 * option ไม่จำเป็นต้องเรียงลำดับตามตัวอย่างนะจ๊ะ
 * @param option 	1. divPopupId = div Id ของ Popup
					2. isModal = ถ้าเป็น true component บนหน้าจอจะ Disabled
					3. height = ความสูง (หน่วยเป็น px)
					4. width = ความกว้าง (หน่วยเป็น px)
					5. initAction = url สำหรับ initial หน้า dialog
					6. funInitCallback = function callback หลังจากยิง Ajax เข้าไปที่ initAction
					7. lpp = line per page [optional]
					8. selectedIds = string selector ของ hidden input ที่เก็บค่า Id ที่เลือกมาจาก Popup
					9. funChosenCallback = function callback หลังจากเลือกข้อมูลบน Dialog
					10. funClearCallback = function ที่เอาไว้ clear ค่า criteria บนหน้า dialog
					11. searchByIdsAction = url สำหรับการ search ข้อมูลที่เลือกจาก multi choose dialog [ถ้าเป็น single dialog ไม่ต้องส่งค่านี้มา]
 * @returns
 */
function openDataTableDialog(option) {
	
	//เก็บค่า ID ของ Div popup เพื่อให้ datatable.js ใช้
	objDialogDataTable.divPopupDatatable = '#' + option.divPopupId;
	objDialogDataTable.funClearCallback = option.funClearCallback;
	objDialogDataTable.funChosenCallback = option.funChosenCallback;
	objDialogDataTable.searchByIdsAction = option.searchByIdsAction;
	
	/*if (parseInt(jQuery(window).width(), 10) >= 1500) {
		if (option.height >= 750) {
			option.height = 700;
			option.width = option.width;
		}
	} */
	
	//เปิด Dialog
	jQuery(objDialogDataTable.divPopupDatatable).dialog({
		autoOpen : false,
		height : option.height,
		width : option.width,
		isModal : option.isModal,
		position : { my: "center middle", at: "top middle", of: window },
		resizable: false,
		zIndex: 100,
		
		open : function() {
			
			//set id ที่ได้รับมาจากหน้าหลักลงมัน hidden ที่อยู่บน dialog
			jQuery(objDialogDataTable.divPopupDatatable +' #criteria_selectedIds').val(jQuery(option.selectedIds).val());

		    jQuery.ajax({
				type : "POST",
			    url : option.initAction,
			    data : jQuery(objDialogDataTable.divPopupDatatable + " :input").serialize(),
			    async : false,
			    success : function(obj) {
			    	
		 	    	jQuery(objDialogDataTable.divPopupDatatable + ' [name="criteria.defaultHeaderSortsSelect"]').val(obj.criteria.headerSortsSelect);
		 	    	jQuery(objDialogDataTable.divPopupDatatable + ' [name="criteria.defaultOrderSortsSelect"]').val(obj.criteria.orderSortsSelect);
		 	    	
		 	    	if(option.lpp != undefined) {
		 	    		jQuery(objDialogDataTable.divPopupDatatable + ' #criteria_linePerPage').val(option.lpp);
		 	    	}
		 	    	
		 	    	if(option.searchByIdsAction != undefined) {
		 	   		 	    		
		 	    		jQuery(objDialogDataTable.divPopupDatatable).attr('dialogtype', 'multi');
		 	    		
		 	    		var dialogButton = "<table id=\"buttonPopup\" class=\"FORM\" >" 
				 	   		+ "<tr>"
				 	   		+ "<td style=\"width: 70%; height: 5px;\"></td>"
				 	   		+ "<td style=\"width: 30%; height: 5px;\">"
				 	   		+ "</tr>"
				 	   		+ "<tr> <td style=\"width: 70%;\"> </td>"
				 	   		+ "<td style=\"width: 30%;\">"
				 	   		+ "<button onclick=\"dialogChooseMultiRecordForDatatable();\">"
				 	   		+ "OK"
				 	   		+ "</button>"
				 	   		+ "</td>"
				 	   		+ "</tr>"
				 	   		+ "</table>";
				 	    	
				 	    	jQuery(objDialogDataTable.divPopupDatatable).append(dialogButton);
				 	    	jQuery(objDialogDataTable.divPopupDatatable + " #buttonPopup button").button({ });
				 	    	jQuery(objDialogDataTable.divPopupDatatable + " #buttonPopup").hide();
		 	    	} else {
		 	    		jQuery(objDialogDataTable.divPopupDatatable).attr('dialogtype', 'single');
		 	    	}
		 	    	
		 	    	if(option.funInitCallback != '') {		
		 	    		window[option.funInitCallback](obj);
					}
			    }
			});
			
		}, 
		close: function() {
			closeDialogDatatable();
		}
	});
	
	jQuery(objDialogDataTable.divPopupDatatable).dialog('open');
}