var objDataTable = {
		
		table: {},
		order: new Array(),
		
		//HEADER SORT DATA TABLE
		hSelect: "",
		oSelect: "",
		
		//DATATABLE TEMPLATE
		datatableTemplate: {},
		
		//STORE DATATABLE INSTANCE
		tableInstance: {},
		
		//USE ON DRAW CHECKBOX
		chosenIds: [],
		
		isConfirm: false,
		
		height: "300px",
		
		isInitComplete: false
}

/*
 * เมื่อ initial datatable mุกครั้งต้อง กำหนด Default order ก่อน ดังนั้นจึงต้อง get order มาจาก headerSortsSelect มาเก็บไว้ก่อนทุกครั้ง
 * criteria.headerSortsSelect จะต้อง hidden ไว้ที่หน้า jsp
 */
jQuery(document).ready(function(){
	/* var hSelect = jQuery("input[name='criteria.headerSortsSelect']").val();
	var oSelect = jQuery("input[name='criteria.orderSortsSelect']").val();
	if((hSelect != null && hSelect != "")&&(oSelect != null && oSelect != "")){
    	var hs = hSelect.split(",");
    	var os = oSelect.split(",");
    	for ( var i = 0; i < hs.length; i++) {
			order[i] = [hs[i], os[i].toLowerCase()];
		}
    	//console.log(getLanguage());
	} */
	
	objDataTable.datatableTemplate = {};
	
	initHeaderSort(false, undefined);
});

function initHeaderSort(defaultFromCriteria, divToSerialize) {
	
	//panthapat.t
	divToSerialize = divToSerialize == undefined ? "" : "#" + divToSerialize + " ";
	
	//STORE HEADER VALUE FROM CRITERIA
	//TRUE WHEN PAGE IS DRAW
	if((objDataTable.hSelect == null && objDataTable.oSelect == null) || (objDataTable.hSelect == "" && objDataTable.oSelect == "")) {
		objDataTable.hSelect = jQuery(divToSerialize + "input[name='criteria.headerSortsSelect']").val();
		objDataTable.oSelect = jQuery(divToSerialize + "input[name='criteria.orderSortsSelect']").val();
	}
	
	//STORE DEFAULT HEADER VALUE WHICH IS THE SAME AS METHOD setDefaultHeaderSorts() IN *SearchCriteria.java
	//TRUE WHEN OPENED FROM MENU
	if(jQuery(divToSerialize + "input[name='criteria.defaultHeaderSortsSelect']").val() == "" && jQuery(divToSerialize + "input[name='criteria.defaultOrderSortsSelect']").val() == ""){
		jQuery(divToSerialize + "input[name='criteria.defaultHeaderSortsSelect']").val(jQuery(divToSerialize + "input[name='criteria.headerSortsSelect']").val());
		jQuery(divToSerialize + "input[name='criteria.defaultOrderSortsSelect']").val(jQuery(divToSerialize + "input[name='criteria.orderSortsSelect']").val());
	}
	
	//SET HEADER SORT TO DEFAULT WHICH IS THE SAME AS METHOD setDefaultHeaderSorts() IN *SearchCriteria.java
	if(defaultFromCriteria) {
		objDataTable.hSelect = jQuery(divToSerialize + "input[name='criteria.defaultHeaderSortsSelect']").val();
		objDataTable.oSelect = jQuery(divToSerialize + "input[name='criteria.defaultOrderSortsSelect']").val();
	}
	
	if((objDataTable.hSelect != null && objDataTable.hSelect != "")&&(objDataTable.oSelect != null && objDataTable.oSelect != "")){
    	var hs = objDataTable.hSelect.split(",");
    	var os = objDataTable.oSelect.split(",");
    	for ( var i = 0; i < hs.length; i++) {
			objDataTable.order[i] = [hs[i], os[i].toLowerCase()];
		}
	}
}


/**
 * Clear header and order sort datatable
 * @returns
 */
function clearHeaderSortDatatable() {
	objDataTable.hSelect = "";
	objDataTable.oSelect = "";
}

/** 
 * 
 * @param array of option
 * 
 * option divDatatable: "dialogMember" >>> div id ตัวใหญ่สุดของ datatable
 				 sessionCriteria: '<s:property value="#session[criteriaKey[0]].toJson()" escapeHtml="false" />' >>> criteria ที่อยู่บน session
				 initFunctionCallback: "initDialogSingle" >>> callback function หลักจาก function นี้ ทำงานเสร็จแล้ว ,
				 initAction: "<s:url value='/jsp/ea/initDialogDatatableSingle.action'/>" >>> url ที่ใช้ในการ init
				 tmpCriteriaName: name ของ tmp input ที่เก็บ criteriaKey เอาไว้
				 searchType: "F": fixedColumns, "D": dataTable, "R" : rowChild
				 contextPath: "<%=request.getContextPath()%>", //contextPath
				 colData: (Object) สำหรับกำหนด data ที่ต้องการแสดง
				 aOption: (Object) สำหรับกำหนดส่วนเสริมต่างๆให้ตาราง
				 				
 * @returns
 */
function initMultiDatatable() {
	
	for (var i = 0; i < arguments.length; i++) {
		
		var option = arguments[i];
		
		clearHeaderSortDatatable();
		
		var div = jQuery("#" + option.divToSerialize);
		
		if(jQuery(div).find("input[name='" + option.tmpCriteriaName + "']").val() == "") {
			
			 //SET CRITERIA HEADER SORTS TYPE
			jQuery(div).find('input[name="criteria.criteriaType"]').val(option.headerSortsType);
			
			 jQuery.ajax({
					type : "POST",
				    url : option.initAction,
				    data : jQuery(div).find(":input").serialize(),
				    async : false,
				    success : function(obj) {
				    	
			 	    	jQuery(div).find('input[name="criteria.defaultHeaderSortsSelect"]').val(obj.criteria.headerSortsSelect);
			 	    	jQuery(div).find('input[name="criteria.defaultOrderSortsSelect"]').val(obj.criteria.orderSortsSelect);
			 	    	
			 	    	jQuery(div).find('input[name="criteria.headerSortsSelect"]').val(obj.criteria.headerSortsSelect);
			 	    	jQuery(div).find('input[name="criteria.orderSortsSelect"]').val(obj.criteria.orderSortsSelect);
			 	    	
			 	    	jQuery(div).find('input[name="criteria.start"]').val(obj.criteria.start);
			 	    	jQuery(div).find('input[name="criteria.linePerPage"]').val(obj.criteria.linePerPage);
			 	    	
			 	    	if(jQuery(div).find('input[name="criteria.linePerPage"]').is("select")) {
			 	    		jQuery(div).find('input[name="criteria.linePerPage"]').selectmenu("refresh");
			 	    	}
			 	    
			 	    	jQuery(div).find('input[name="criteria.checkMaxExceed"]').val(obj.criteria.checkMaxExceed);
			 
				    }
			    });
			 
	    } else {
	    	
	    	//SET TEMP CRITERIA BACK TO REAL CRITERIA
			jQuery(div).find("input[name='criteria.criteriaKey']").val(jQuery(div).find("input[name='" + option.tmpCriteriaName + "']").val());
			
			//CLEAR TEMP CRITERIA
			jQuery(div).find("input[name='" + option.tmpCriteriaName + "']").val("");
			
			var criteria = JSON.parse(option.sessionCriteria);
			
			jQuery(div).find('input[name="criteria.defaultHeaderSortsSelect"]').val(criteria.defaultHeaderSortsSelect);
	    	jQuery(div).find('input[name="criteria.defaultOrderSortsSelect"]').val(criteria.defaultOrderSortsSelect);
	    	jQuery(div).find('input[name="criteria.headerSortsSelect"]').val(criteria.headerSortsSelect);
	    	jQuery(div).find('input[name="criteria.orderSortsSelect"]').val(criteria.orderSortsSelect);
	    	jQuery(div).find('input[name="criteria.start"]').val(criteria.start);
	    	jQuery(div).find('input[name="criteria.linePerPage"]').val(criteria.linePerPage);
	 
	    	if(jQuery(div).find('input[name="criteria.linePerPage"]').is("select")) {
	    		jQuery(div).find('input[name="criteria.linePerPage"]').selectmenu("refresh");
	    	}
	    
	    	jQuery(div).find('input[name="criteria.checkMaxExceed"]').val(criteria.checkMaxExceed);
	    	jQuery(div).find('input[name="criteria.criteriaType"]').val(criteria.criteriaType);
	    	
	    	//TRIGGER INIT CALL BACK FUNCTION
	    	if(option.initFunctionCallback != "") {
	    		window[option.initFunctionCallback](criteria);
	    	}
	    	
	    	dataTableSearch(option);
		}
	}

}

/**
 * GET SEARCH URL WITH PARAMS
 * @param aOption
 * @returns url + params
 */
function getSearchUrlWithParams(aOption) {
	
	var urlOption = aOption.urlSearch + "?";
	
	//SEARCH PAGE
	if(aOption.divToSerialize == undefined) {
		urlOption += jQuery("form :input").serialize();
		
	//DIALOG	
	} else {
		urlOption += jQuery('#' + aOption.divToSerialize + " :input").serialize();
	}
	
	return urlOption;
}

/**
 * INITIALIZE DATATABLE TEMPLATE
 * @param aOption
 * @returns
 */
function initDatatableTemplate(aOption) {
	
	if (aOption.divToSerialize in objDataTable.datatableTemplate) {
		jQuery("#" + aOption.divToSerialize + " #" + aOption.divResultId).html(objDataTable.datatableTemplate[aOption.divToSerialize]);
	} else {
		objDataTable.datatableTemplate[aOption.divToSerialize] = jQuery("#" + aOption.divToSerialize + " #" + aOption.divResultId).html();
	}
}

//panthapat.t
//init header sort for dialog
function initHeaderSortDialog(divToSerialize, aOption) {

	if(aOption.divToSerialize != undefined) {
		
		if(jQuery(divToSerialize + "input[name='criteria.criteriaKey']").val() == ""){
			initHeaderSort(true, aOption.divToSerialize);
			
		} else {
			initHeaderSort(false, aOption.divToSerialize);
		}
		
		initDatatableTemplate(aOption);
	}
}

/**
 * searchDatatable
 * @param aOption
 * @returns
 */
function dataTableSearch(option) {
	
	if(option.searchType.toUpperCase() == "F") {
		fixedColumns(CONTEXT_CENTRAL, option.colData, option);
		
	} else if(option.searchType.toUpperCase() == "D") {
		dataTable(CONTEXT_CENTRAL, option.colData, option);
		
	} else if(option.searchType.toUpperCase() == "R") {
		rowChild(CONTEXT_CENTRAL, option.colData, option);
	}
}


function manageDialog(divToSerialize, settings) {
	
	//panthapat.t
	if(divToSerialize != "") {
		
		if(jQuery(divToSerialize).attr('dialogtype') == 'multi') {
			
    		//HIDE/SHOW MULTI CHOOSE POPUP BUTTON
	    	/*if(settings.json.lstResult.length > 0) {
	    		jQuery(divToSerialize + "#buttonPopup").show();
	    	} else {
	    		jQuery(divToSerialize + "#buttonPopup").hide();
	    	}*/
			
			//clear chosenIds
			objDataTable.chosenIds = [];
			
			//if all checkbox is checked
			if(jQuery(divToSerialize + ".d_datatable_chckbox:checked").length == jQuery(divToSerialize + ".d_datatable_chckbox").length) {
				
				//make header checkbox checked
				jQuery(divToSerialize + ".d_datatable_chckbox_header:checkbox").prop('checked', true);
				
			} else {
				//make header checkbox unchecked
				jQuery(divToSerialize + ".d_datatable_chckbox_header:checkbox").prop('checked', false);
			}
			
		} else if(jQuery(divToSerialize).attr('dialogtype') == 'single') {
			//set event dbclick for each row if dialog in 'single' 
			setOnDbClick(settings.tableSelector, settings.json.lstResult);
		} 
	}
}


/**
 * dataTable
 * @param context :
 * @param colData : (Object) สำหรับกำหนด data ที่ต้องการแสดง
 * @param aOption : (Object) สำหรับกำหนดส่วนเสริมต่างๆให้ตาราง
 */
function dataTable(context, colData, aOption, isClear){
	
	//panthapat.t
	var divToSerialize = aOption.divToSerialize == undefined ? "" : "#" + aOption.divToSerialize + " ";
	
	if (jQuery(divToSerialize + "input[name='criteria.criteriaKey']").val() == "" && isClear == undefined) {
		clearMessageDataTable();
	}

	jQuery(divToSerialize + "#" + aOption.divResultId).show();
	
	initHeaderSortDialog(divToSerialize, aOption);
	
	if(divToSerialize != "" && jQuery(divToSerialize).attr('dialogtype') == "multi") {
		//clear choosen ids
		clearChosenIds();
	}

	var lpp = jQuery(divToSerialize + "[name='criteria.linePerPage']").val();
	var start = 1;
	
	if(jQuery(divToSerialize + "input[name='criteria.criteriaKey']").val() != ""){
		start = jQuery(divToSerialize + "input[name='criteria.start']").val();
	} else {
		//SET HEADER SORT TO DEFAULT
		initHeaderSort(true, aOption.divToSerialize);
	}

	var tableSelector = divToSerialize + "#" + aOption.tableId;
	jQuery(tableSelector).off('draw.dt');
	
	//panthapat.t
	var urlOption = getSearchUrlWithParams(aOption);
	
	objDataTable.table[aOption.divToSerialize] = jQuery(tableSelector).DataTable({
		autoWidth: false,
		scrollCollapse: false, // ไม่อนุญาติให้ table ลดความสูง
		processing: true,
		serverSide: true,
		scrollY: aOption.height == undefined ? objDataTable.height : aOption.height,
		jQueryUI: true,
		destroy: true,		//Must destroy early crate table.
		dom: "<'top'ip><'H'>rt<'bottom'p><'F'>",	//Set option
		order: objDataTable.order,
		pagingType: "full_numbers",	//to set pagination, default is next and previous only
		pageLength: lpp,
		displayStart: parseInt(start, 10)-1,
		ajax: {
			type: "POST",
			url : urlOption,
			dataSrc : "result",//,	//Default = data
			inputStartSelector : divToSerialize + "input[name='criteria.start']",
			divToSerialize : aOption.divToSerialize
			/*error : function(res){
				submitAction(aOption.urlSearch);
			}*/
		},
		columns: colData,
        createdRow: function( row, data, index ) {	//Row created callback
        	
        	//console.log("createdRow");
        	
        	createCommonIcon(context, row, data, index, aOption);
    		if(aOption.createdRowFunc != undefined){
    			window[aOption.createdRowFunc](row, data);
    		}
            return row;
        },
	    initComplete: function (settings, myCriteria) {
	    	
	    	//console.log("initComplete");
	    	
	    	manageMessageAjax(aOption, myCriteria);
	    	
	    	if(objDataTable.isConfirm) {

	    		objDataTable.isConfirm = false;
	    		
				if(divToSerialize == "") {
					searchAjax();
				} else {
					dataTableSearch(aOption);
				}
	    		
	    	} else {
		    	
		    	urlOption = getSearchUrlWithParams(aOption);
		    	
		    	//เตรียม serialize grikt5hkdf sort จะไม่ draw tab le ใหม่
		    	settings.ajax = {
		    		type: "POST",
	    			url : urlOption,
	    			dataSrc : "result",//,	//Default = data
	    			inputStartSelector : divToSerialize + "input[name='criteria.start']",
	    			divToSerialize : aOption.divToSerialize 
	
		    	};
				
				manageDialog(divToSerialize, settings, tableSelector); 
		    	
		    	//hide page navigate if there's only one page
				manageTogglePageNavigate(divToSerialize)

				//jQuery("div.CRITERIA input:first , div.CRITERIA select:first").not(".hasDatepicker").focus();

				if(aOption.footerHtml != undefined){
					//Create icon in footer
					jQuery(divToSerialize + "div.bottom").next().html(aOption.footerHtml);
				}
				
				drawnDt(context, aOption, null, settings);
				
				//initDisplayMaldives(divToSerialize);
				
				setCssHover(aOption);
				
				if(aOption.createdTableFunc != undefined){
					window[aOption.createdTableFunc](settings);
				}
				
				// custom object value
				settings.initComplete = true;
				settings.tableSelector = tableSelector;
	    	}
	    	
	    },
		language: getLanguage(),
		drawCallback: function( settings ) {
			
			//console.log("drawCallback: " + settings.initComplete);
			
			if(settings.initComplete) {
				
				//console.log("do drawCallback")
				
				manageDialog(divToSerialize, settings); 
				
				drawnDt(context, aOption, null, settings);
				
				setCssHover(aOption);
				
			} else {
				initDisplayMaldives(divToSerialize);
			}
		}
	});
}


function manageTogglePageNavigate(divToSerialize) {
	//hide page navigate if there's only one page
	if(jQuery(divToSerialize + "#tableResult_paginate span a").length == 1) {
		jQuery(divToSerialize + ".dataTables_paginate").css("visibility", "hidden");
	}
}

/**
 * fixedColumns
 * @param context :
 * @param colData : (Object) สำหรับกำหนด data ที่ต้องการแสดง
 * @param aOption : (Object) สำหรับกำหนดส่วนเสริมต่างๆให้ตาราง
 */
function fixedColumns(context, colData, aOption, isClear){
	
	//panthapat.t
	var divToSerialize = aOption.divToSerialize == undefined ? "" : "#" + aOption.divToSerialize + " ";
	
	if (jQuery(divToSerialize + "input[name='criteria.criteriaKey']").val() == "" && isClear == undefined) {
		clearMessageDataTable();
	}
	
	jQuery(divToSerialize + "#" + aOption.divResultId).show();
	
	initHeaderSortDialog(divToSerialize, aOption);
	
	if(divToSerialize != "" && jQuery(divToSerialize).attr('dialogtype') == "multi") {
		//clear choosen ids
		clearChosenIds();
	}

	var lpp = jQuery(divToSerialize + "[name='criteria.linePerPage']").val();
	var start = 1;
	
	if(jQuery(divToSerialize + "input[name='criteria.criteriaKey']").val() != ""){
		start = jQuery(divToSerialize + "input[name='criteria.start']").val();
	} else {
		//SET HEADER SORT TO DEFAULT
		initHeaderSort(true, aOption.divToSerialize);
	}

	var tableSelector = divToSerialize + "#" + aOption.tableId;
	jQuery(tableSelector).off('draw.dt');
	
	//panthapat.t
	var urlOption = getSearchUrlWithParams(aOption);
	
	var datatableOption = {
		
		autoWidth: true,
		scrollCollapse: false, // ไม่อนุญาติให้ table ลดความสูง
		processing: true,
		serverSide: true,
		scrollY: aOption.height == undefined ? objDataTable.height : aOption.height,
	    scrollX: true,
		jQueryUI: true,
		destroy: true,		//Must destroy early crate table.
		dom: "<'top'ip><'H'>rt<'bottom'p><'F'>",	//Set option
		order: objDataTable.order,
		pagingType: "full_numbers",	//to set pagination, default is next and previous only
		pageLength: lpp,
		displayStart: parseInt(start, 10)-1,
		pageginationAlertFunc : aOption.pageginationAlertFunc,
		ajax: {
			type: "POST",
			url : urlOption,
			dataSrc : "result",	//Default = data (Object result declare in CommonModel)
			inputStartSelector : divToSerialize + "input[name='criteria.start']", //ใช้ใน jquery.datatable.js
			divToSerialize : aOption.divToSerialize 
		},
		columns: colData,
		
	    createdRow: function( row, data, index ) {	//Row cretaed callback
	    	
	    	createCommonIcon(context, row, data, index, aOption);
			if(aOption.createdRowFunc != undefined){
				window[aOption.createdRowFunc](row, data);
			}
	        return row;
	    },
	    
	    initComplete: function (settings, myCriteria) {
	    	
	    	manageMessageAjax(aOption, myCriteria);
	    	
	    	if(objDataTable.isConfirm) {
	    		
	    	} else {
	    		
	    		//HIDE/SHOW MULTI CHOOSE POPUP BUTTON
		    	/* if(settings.json.lstResult.length > 0) {
		    		jQuery(divToSerialize + "#buttonPopup").show();
		    	} else {
		    		jQuery(divToSerialize + "#buttonPopup").hide();
		    	} */
		    	
		    	urlOption = getSearchUrlWithParams(aOption);
		    	
		    	//เตรียม serialize sort จะไม่ draw table ใหม่
		    	settings.ajax = {
		    		type: "POST",
					url : urlOption,
					dataSrc : "result",	//Default = data
					inputStartSelector : divToSerialize + "input[name='criteria.start']",
					divToSerialize : aOption.divToSerialize
		    	}; 
		    	
		    	objDataTable.tableInstance[aOption.divToSerialize == undefined ? "" : aOption.divToSerialize] = settings;
	    	}
	    },
	    
	    language: getLanguage(),

	    fixedColumns: {
			leftColumns: aOption.fixedCoumnsLeft, 
			rightColumns: aOption.fixedCoumnsRight,
			fnDrawCallback: function(o, i) {
				
				//console.log(o);
				//console.log(i);
				
				if(objDataTable.isConfirm) {
					
					objDataTable.isConfirm = false;
					
					if(divToSerialize == "") {
						searchAjax();
					} else {
						dataTableSearch(aOption);
					}
					
				} else {
					
					var settings = objDataTable.tableInstance[aOption.divToSerialize == undefined ? "" : aOption.divToSerialize];
					
					//remove checkbox in dataTables_scrollBody
					//ตอน submit ค่าใน criteria.selectedIds จะได้ไม่ซ้ำ
					jQuery(divToSerialize + ".dataTables_scrollBody .d_checkbox input[name='criteria.selectedIds']").remove();
					
					//ลบ header checkbox ออก
					jQuery(divToSerialize + ".dataTables_scrollHead input[name='chkall']").remove();
					
					manageDialog(divToSerialize, settings, tableSelector); 
			    	
			    	//hide page navigate if there's only one page
			    	if(jQuery(divToSerialize + "#tableResult_paginate span a").length == 1) {
			    		jQuery(divToSerialize + ".dataTables_paginate").css("visibility", "hidden");
			    	}
			    	
			    	//add footer
			    	if(aOption.footerHtml != undefined){
			    		//Create icon in footer
			    		jQuery(divToSerialize + "div.bottom").next().html(aOption.footerHtml);
			    	}
			
			    	drawnDt(context, aOption, null, settings);
			    	
					initDisplayMaldives(divToSerialize);
			    	
					setCssHover(aOption);
					
					//created table callback
			    	if(aOption.createdTableFunc != undefined){
			    		window[aOption.createdTableFunc](settings);
			    	}
			
			    	//jQuery("div.CRITERIA input:first , div.CRITERIA select:first").not(".hasDatepicker").focus();
					
				}
			}
		}
	}
	
	//CREATE DATATABLE INSTANCE
	objDataTable.table[aOption.divToSerialize] = jQuery(tableSelector).DataTable(datatableOption);
}

/**
 * dataTable Row Child
 * @param context :
 * @param colData : (Object) สำหรับกำหนด data ที่ต้องการแสดง
 * @param aOption : (Object) สำหรับกำหนดส่วนเสริมต่างๆให้ตาราง
 */
function rowChild(context, colData, aOption, isClear){
	
	//panthapat.t
	var divToSerialize = aOption.divToSerialize == undefined ? "" : "#" + aOption.divToSerialize + " ";
	
	if (jQuery(divToSerialize + "input[name='criteria.criteriaKey']").val() == "" && isClear == undefined) {
		clearMessageDataTable();
	}

	jQuery(divToSerialize + "#" + aOption.divResultId).show();
	
	initHeaderSortDialog(divToSerialize, aOption);
	
	if(divToSerialize != "" && jQuery(divToSerialize).attr('dialogtype') == "multi") {
		//clear choosen ids
		clearChosenIds();
	}
	
	var lpp = jQuery(divToSerialize + "[name='criteria.linePerPage']").val();
	var start = 1;

	if(jQuery(divToSerialize + "input[name='criteria.criteriaKey']").val() != ""){
		start = jQuery(divToSerialize + "input[name='criteria.start']").val();
	} else {
		//SET HEADER SORT TO DEFAULT
		initHeaderSort(true, aOption.divToSerialize);
	}

	var tableSelector = divToSerialize + "#" + aOption.tableId;
	jQuery(tableSelector).off('draw.dt');
	
	//panthapat.t
	var urlOption = getSearchUrlWithParams(aOption);

	table = jQuery(tableSelector).DataTable ({
		processing: true,
		serverSide: true,
		jQueryUI: true,
		scrollY: aOption.height == undefined ? objDataTable.height : aOption.height,
		destroy: true,		//Must destroy early crate table.
		dom: "<'top'ip><'H'>rt<'bottom'p><'F'>",	//Set option
		order: objDataTable.order,
		pagingType: "full_numbers",	//to set pagination, default is next and previous only
		pageLength: lpp,
		displayStart: parseInt(start, 10)-1,
		ajax: {
			type: "POST",
			url : urlOption,
			dataSrc : "result",	//Default = data
			inputStartSelector : divToSerialize + "input[name='criteria.start']"
			/*error : function(res){
				submitAction(aOption.urlSearch);
			}*/
		},
		columns: colData,
        createdRow: function( row, data, index ) {	//Row created callback
        	
        	createCommonIcon(context, row, data, index, aOption);
    		if(aOption.createdRowFunc != undefined){
    			window[aOption.createdRowFunc](row, data);
    		}
            return row;
        },
	    initComplete: function (settings, myCriteria) {
	    	
	    	//console.log("initComplete");

	    	manageMessageAjax(aOption, myCriteria);
	    	
	    	if(objDataTable.isConfirm) {

	    		objDataTable.isConfirm = false;
	    		
				if(divToSerialize == "") {
					searchAjax();
				} else {
					dataTableSearch(aOption);
				}
	    		
	    	} else {
		    	
		    	urlOption = getSearchUrlWithParams(aOption);
		    	
		    	//เตรียม serialize grikt5hkdf sort จะไม่ draw tab le ใหม่
		    	settings.ajax = {
		    		type: "POST",
	    			url : urlOption,
	    			dataSrc : "result",//,	//Default = data
	    			inputStartSelector : divToSerialize + "input[name='criteria.start']",
	    			divToSerialize : aOption.divToSerialize 
	
		    	};
				
				manageDialog(divToSerialize, settings, tableSelector); 
		    	
		    	//hide page navigate if there's only one page
				manageTogglePageNavigate(divToSerialize)

				//jQuery("div.CRITERIA input:first , div.CRITERIA select:first").not(".hasDatepicker").focus();

				if(aOption.footerHtml != undefined){
					//Create icon in footer
					jQuery(divToSerialize + "div.bottom").next().html(aOption.footerHtml);
				}
				
				drawnDt(context, aOption, null, settings);
				
				//initDisplayMaldives(divToSerialize);
				
				setCssHover(aOption);
				
				if(aOption.createdTableFunc != undefined){
					window[aOption.createdTableFunc](settings);
				}
				
				// custom object value
				settings.initComplete = true;
				settings.tableSelector = tableSelector;
	    	}
	    },
	    language: getLanguage(),
	    drawCallback: function( settings ) {
	    	if(settings.initComplete) {
				
				manageDialog(divToSerialize, settings); 
				
				drawnDt(context, aOption, null, settings);
				
				setCssHover(aOption);
				
			} else {
				initDisplayMaldives(divToSerialize);
			}
	    }
	});

}


function createDataTable() {
	
}

function clearMessageDataTable() {
	
}

function drawnDt(context, aOption, oTable, oData){
	
	//console.log("drawnDt")
	//console.log(oData)
	
	//panthapat.t
	var divToSerialize = aOption.divToSerialize == undefined ? "" : "#" + aOption.divToSerialize + " ";
	var tableSelector = divToSerialize + "#" + aOption.tableId;
	var freezeTableSelector = divToSerialize + ".DTFC_LeftBodyLiner:last table.DTFC_Cloned";
		
	//Keep criteria key
	jQuery(divToSerialize + "input[name='criteria.criteriaKey']").val(oData.json.criteria.criteriaKey);
	jQuery(divToSerialize + "input[name='criteria.headerSortsSelect']").val(oData.json.criteria.headerSortsSelect);
	jQuery(divToSerialize + "input[name='criteria.orderSortsSelect']").val(oData.json.criteria.orderSortsSelect);
	jQuery(divToSerialize + "input[name='criteria.start']").val(oData.json.criteria.start);
	
	jQuery(divToSerialize + "input[name='criteria.checkMaxExceed']").val(oData.json.criteria.checkMaxExceed);
	jQuery(divToSerialize + "input[name='criteria.alertMaxExceed']").val(oData.json.criteria.alertMaxExceed);

    var start = oData._iDisplayStart;
    
	for ( var i=0; i < oData.aiDisplay.length; i++ ){	//loop ตามจำนวนแถว
		//Counter order number
        jQuery(tableSelector+' tbody tr:eq('+i+') td:eq(0)').html(parseInt(start, 10) + (i+1)).addClass("order");
        
        //ใส่ row no. ให้ freeze table บนเวอร์ชั่นใหม่
        jQuery(freezeTableSelector + ' tbody tr:eq('+i+') td:eq(0)').html(parseInt(start, 10) + (i+1)).addClass("order");;
    }
}

//Manage message
function manageMessageAjax(aOption, myCriteria){
	
	//panthapat.t
	var divToSerialize = aOption.divToSerialize == undefined ? "" : "#" + aOption.divToSerialize + " ";

	var fact = "Y";
	
	if(myCriteria.messageAjax.messageType != null) {
		
		if(myCriteria.messageAjax.messageType == "C"){
			if(confirm(myCriteria.messageAjax.message)){
				jQuery(divToSerialize + "input[name='criteria.checkMaxExceed']").val(false);
				
				jQuery(divToSerialize + "input[name='criteria.criteriaKey']").val("");
				jQuery(divToSerialize + "input[name='criteria.alertMaxExceed']").val(true);
				
				objDataTable.isConfirm = true;
				
				fact = "Y";
			} else{
				fact = "N";
			}
		}
		
		//ถ้าเป็น dialog
		else if(divToSerialize != "" && jQuery(divToSerialize).attr('dialogtype') != undefined) {
			
			if(myCriteria.messageAjax.messageType == "W"){
				showNotifyMessageDataTableDialog(myCriteria.messageAjax.message, "warning");
				fact = "N";
				
			} else {
				showNotifyMessageDataTableDialog(myCriteria.messageAjax.message, "error", myCriteria.messageAjax.messageDetail);
				if(aOption.urlSearch.indexOf("/search") > -1){
					fact = "N";
				}	
			}
			
		//ถ้าไม่ใช่
		} else {

			if(myCriteria.messageAjax.messageType == "W"){
				showNotifyMessageInfo(myCriteria.messageAjax.message);
				fact = "N";
				
			} else if(myCriteria.messageAjax.messageType == "D"){
				showNotifyMessageInfo(myCriteria.messageAjax.message);
				fact = "Y";
				
			} else if(myCriteria.messageAjax.messageType == "S"){
				showNotifyMessageSuccess(myCriteria.messageAjax.message);		
				fact = "Y";
				
			} else if(myCriteria.messageAjax.messageType == "A"){
				alert(myCriteria.messageAjax.message);
				fact = "N";
				
			} else {
				showNotifyMessageError(myCriteria.messageAjax.message, myCriteria.messageAjax.messageDetail);
				if(aOption.urlSearch.indexOf("/search") > -1){
					fact = "N";
				}	
			}
		}
	}
		
	if(fact === "N"){
		jQuery(divToSerialize + "#" + aOption.divResultId).hide();
	}
	return fact;
}

function getLanguage(){
	var oLanguage = {
			zeroRecords: validateMessage.CODE_30020,	// ไม่มีข้อมูลแสดง
	        info: "&nbsp;<b>"+validateMessage.CODE_numberOfSearch+" _TOTAL_ "+validateMessage.CODE_order+"<b/>",	//รายการที่ค้นพบ xxx รายการ
	        infoEmpty: "&nbsp;<b>"+validateMessage.CODE_numberOfSearch+" _TOTAL_ "+validateMessage.CODE_order+"<b/>",	//รายการที่ค้นพบ xxx รายการ
	        paginate: {
		        first: "‹‹",
		        last: "››",
		        next: "›",
		        previous: "‹"
		    }
	};
	return oLanguage;
}

function toggleDataTable(headerCheckbox) {
	
	var topDiv = jQuery(headerCheckbox).closest('div.RESULT_SYSTEM');
	
	if(jQuery(headerCheckbox).is(":checked"))
		jQuery(topDiv).find("input[name='criteria.selectedIds']:not(:checked)").not(headerCheckbox).click()
	else
		jQuery(topDiv).find("input[name='criteria.selectedIds']:checked").not(headerCheckbox).click()
}

/**
 * FOR MULTI CHOOSE DIALOG
 * ON CHECKBOX CHECKED / UNCHECKED
 * 
 * @param checkbox
 * @returns
 */
function dataTableCheck(checkbox) {
	
	var topDiv = jQuery(checkbox).closest('div.RESULT_SYSTEM');
	
	if(jQuery(topDiv).find("input[name='criteria.selectedIds']:not(input[name='chkAll'])").length == jQuery(topDiv).find("input[name='criteria.selectedIds']:checked:not(input[name='chkAll'])").length)
		jQuery("input[type='checkbox'][name='chkAll']", topDiv).prop('checked', true)
	else
		jQuery("input[type='checkbox'][name='chkAll']", topDiv).prop('checked', false)
}

function createCommonIcon(context, row, data, index, aOption){
	
	//panthapat.t 
	//มี checkbox?
	if(aOption.divToSerialize != undefined) {
		
		if(jQuery("#" + aOption.divToSerialize).attr('dialogtype') == "multi") {
			
			jQuery('.d_checkbox', row).html('<input id="' + aOption.divToSerialize + data.id + '" type="checkbox" class="d_datatable_chckbox" onclick="dialogDatatableCheck(this);" value="' + data.id + '">');	//Checkbox
			
			//split chosenIds
			if(objDataTable.chosenIds.length == 0 && jQuery("#" + aOption.divToSerialize + " #criteria_chosenIds").val().length > 0) {
				objDataTable.chosenIds = jQuery("#" + aOption.divToSerialize + " #criteria_chosenIds").val().split(',');
			}
			
			//add checked to checkbox if chosen
			if(objDataTable.chosenIds.indexOf(data.id) > -1) {
				jQuery(".d_checkbox input[type='checkbox']", row).attr("checked", "checked");
			}
			
		} else {
			jQuery('.d_checkbox', row).html('<input type="checkbox" onclick="dataTableCheck(this);" name="criteria.selectedIds" value="' + data.id + '">');	//Checkbox
		}
		
	} else {
		jQuery('.d_checkbox', row).html('<input type="checkbox" name="criteria.selectedIds" value="' + data.id + '">');	//Checkbox
	}
	
	//add align class ให้ d_checbox
	jQuery('.d_checkbox', row).addClass('checkbox');
	
	//มี Radio?
	jQuery('.d_radio', row).html('<input type="radio" name="criteria.selectedIds" value="'+data.id+'">');	//Radio
	jQuery('.d_radio', row).addClass('checkbox');
	
    //มี view?
    if(aOption.urlView != undefined){
    	var viewHtml;
    	if(aOption.urlView.authen === 'true'){
    		viewHtml = "<img onclick='submitAction(\""+aOption.urlView.url+"\", \""+aOption.pk+"\", \""+data.id+"\");' class='cursor' title='"+ validateMessage.CODE_VIEW + "' src='"+context+"/images/icon/i_view.png'>";	//icon view
    	} else{
    		viewHtml = "<img class='cursor' title='"+ validateMessage.CODE_VIEW + "' src='"+context+"/images/icon/i_view_dis.png'>";//icon disable view

    	}
    	jQuery('.d_view', row).html(viewHtml);
    	jQuery('.d_view', row).addClass("checkbox");
    }

    //มี edit?
    if(aOption.urlEdit != undefined){
    	var editHtml;
    	if(aOption.urlEdit.authen === 'true'){
    		editHtml = "<img onclick='submitAction(\""+aOption.urlEdit.url+"\", \""+aOption.pk+"\", \""+data.id+"\");' class='cursor' title='"+ validateMessage.CODE_EDIT + "' src='"+context+"/images/icon/i_edit.png'>";	 //icon edit
    	} else{
    		editHtml = "<img class='cursor' title='"+ validateMessage.CODE_EDIT + "' src='"+context+"/images/icon/i_edit_dis.png'>";	 //icon disable edit
    	}
    	jQuery('.d_edit', row).html(editHtml);
    	jQuery('.d_edit', row).addClass("checkbox");
    }

    /* Set icon active or inactive */
	if(data.active.code == "Y"){
		jQuery('.d_status', row).html( '<img title="'+validateMessage.CODE_ACTIVE+'" src="'+context+'/images/icon/i_open.png">' );
	} else{
		jQuery('.d_status', row).html( '<img title="'+validateMessage.CODE_INACTIVE+'" src="'+context+'/images/icon/i_close.png">' );
	}
	jQuery('.d_status', row).addClass("status");

    // มี child row?
    if (aOption.childRow != undefined && aOption.childRow == "Y") {
    	
    	//jQuery(row).find('td.details-control').addClass('center').html( '<img src="'+context+'/ui/datatables/jqueryui/css/images/details_open.png">' );
    	
    	// Add event listener for opening and closing details
    	jQuery('.details-control', row).click(function () {
        	var tr = jQuery(this).closest('tr');
            var row = table.row( tr );
                     
            if ( row.child.isShown() ) {
            
            	//jQuery(tr).find('td.details-control').html( '<img src="'+context+'/ui/datatables/jqueryui/css/images/details_open.png">' );
            	
            	// This row is already open - close it
            	row.child.hide();
            	tr.removeClass('shown');
            	jQuery("div.slider", row.child()).hide( "slow" );
            } else {
            	
            	//jQuery(tr).find('td.details-control').html( '<img src="'+context+'/ui/datatables/jqueryui/css/images/details_close.png">' );
            	
                // Open this row
                row.child( createDataTableRowChild(row), 'no-padding' ).show();
                tr.addClass('shown');
                jQuery("div.slider", row.child()).show( "slow" );

                var ele = jQuery(row.child().children(".no-padding").children(".slider").children(".display"));
                tableId = jQuery(ele).attr("id");
                setCssHover(tableId)
            }
        });
    }
}

function setCssHover(aOption){
	
	//panthapat.t
	var divToSerialize = aOption.divToSerialize == undefined ? "" : "#" + aOption.divToSerialize + " ";
	var tableSelector = divToSerialize + "#" + aOption.tableId;
	var clonedTable = divToSerialize + "table.DTFC_Cloned";
		
	jQuery('tbody tr', jQuery(tableSelector)).each(function (i) {
		jQuery(this).mouseover(function() {
			jQuery(this).addClass("ui-state-highlight");
			jQuery(this).parent().parent().parent().parent().parent().children(".DTFC_LeftWrapper").children(".DTFC_LeftBodyWrapper").children(".DTFC_LeftBodyLiner").children(".DTFC_Cloned").find("tbody > tr:eq("+i+")").addClass("ui-state-highlight");
		});

		jQuery(this).mouseout(function() {
			jQuery(this).removeClass("ui-state-highlight");
			jQuery(this).parent().parent().parent().parent().parent().children(".DTFC_LeftWrapper").children(".DTFC_LeftBodyWrapper").children(".DTFC_LeftBodyLiner").children(".DTFC_Cloned").find("tbody > tr:eq("+i+")").removeClass("ui-state-highlight");
		});
	});
	
	jQuery('tbody tr', jQuery(clonedTable)).each(function(nRow){
		jQuery(this).mouseover(function() {
			jQuery(this).addClass("ui-state-highlight");
			jQuery(this).parent().parent().parent().parent().parent().parent().children(".dataTables_scroll").children(".dataTables_scrollBody").children(".display").find("tbody > tr:eq("+nRow+")").addClass("ui-state-highlight");
		});

		jQuery(this).mouseout(function() {
			jQuery(this).removeClass("ui-state-highlight");
			jQuery(this).parent().parent().parent().parent().parent().parent().children(".dataTables_scroll").children(".dataTables_scrollBody").children(".display").find("tbody > tr:eq("+nRow+")").removeClass("ui-state-highlight");
		});
    });
}