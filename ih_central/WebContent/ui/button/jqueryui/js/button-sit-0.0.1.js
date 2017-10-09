+function($) {
	'use button';
	
	var pageSearch = "search";
	var pageAdd = "add";
	var pageEdit = "edit";
	var pageView = "view";
	var pageReport = "report";
	var pagePrint = "print";
	var pageSearchDialog = "search_dialog";
	var pageChooseDialog = "choose_dialog";
	
	
	var SitButton = function(element, options) {

		this.options = options
		this.$body = $(document.body)
		this.$element = $(element)
		this.id = $(element).attr('id');
		this.auth = options.auth;
		this.funcSearch = options.funcSearch;
		this.funcClearFormCallBack = options.funcClearFormCallBack;
		this.buttonType = options.buttonType;
		this.funcValidSearch = options.funcValidSearch;
		this.divCriteriaId = options.divCriteriaId;
		this.divTableResultId = options.divTableResultId;
		
		
		this.messageConfirmAdd = options.messageConfirmAdd;
		this.funcSaveAdd = options.funcSaveAdd;
		this.funcValidAdd = options.funcValidAdd;
		this.messageCancelAdd = options.messageCancelAdd;
		this.funcCancelAdd = options.funcCancelAdd;
		
		this.messageConfirmEdit = options.messageConfirmEdit;
		this.funcSaveEdit = options.funcSaveEdit;
		this.funcValidEdit = options.funcValidEdit;
		this.messageCancelEdit = options.messageCancelEdit;
		this.funcCancelEdit = options.funcCancelEdit;
		
		this.messageCancelView = options.messageCancelView;
		this.funcCancelView = options.funcCancelView;
		
		this.funcPrint = options.funcPrint;
		this.funcCancelPrint = options.funcCancelPrint;
		this.funcValidPrint = options.funcValidPrint;
		
		this.funcSearchDialog = options.funcSearchDialog;
		this.funcValidSearchDialog = options.funcValidSearchDialog;
		this.funcClearDialog = options.funcClearDialog;
		this.funcCloseDialog = options.funcCloseDialog;
		this.divCriteriaIdDialog = options.divCriteriaIdDialog;
		this.funcClearFormCallBackDialog = options.funcClearFormCallBackDialog;
		this.divTableResultIdDialog = options.divTableResultIdDialog;
		
		this.funcChooseDialog = options.funcChooseDialog;
	}
	
	
	SitButton.VERSION = '0.0.1'
	SitButton.DEFAULTS = {
		funcSearch : "searchPage" ,
		funcClearFormCallBack : "clearFormCallBack",
		funcValidSearch : "checkValidSearch",
		funcSaveAdd : "saveAdd",
		funcValidAdd : "checkValidAdd",
		funcCancelAdd : "cancelAdd",
		funcSaveEdit : "saveEdit",  
		funcCancelEdit : "cancelEdit",
		funcValidEdit : "checkValidEdit",
		funcCancelView : "cancelView",
		funcPrint : "print",
		funcCancelPrint : "cancelPrint",
		funcValidPrint : "checkValidPrint",
		funcSearchDialog : "searchDialog",
		funcValidSearchDialog : "checkValidSearchDialog",
		funcClearDialog : "clearDialog",
		funcCloseDialog : "closeDialog",
		funcChooseDialog : "chooseDialog",
		funcClearFormCallBackDialog : "clearFormCallBackDialog"
	}
	
	SitButton.prototype.create = function() {
		this.createSitButtonBox();
	}
	
	SitButton.prototype.createSitButtonBox = function() {	
		var messageButton = {
			msgConfirmSaveAdd : validateMessage.CODE_50003,
			msgConfirmSaveEdit : validateMessage.CODE_50004,
			msgCancelAdd : validateMessage.CODE_50006,
			msgCancelEdit : validateMessage.CODE_50007,
			msgCancelView : validateMessage.CODE_50017,
			msgCancelDialog : validateMessage.CODE_50012,
				
			msgSearch : validateMessage.CODE_SEARCH,
			msgClear : validateMessage.CODE_CLEAR,
			msgSave : validateMessage.CODE_SAVE,
			msgCancel : validateMessage.CODE_CANCEL,
			msgEdit : validateMessage.CODE_EDIT,
			msgClose : validateMessage.CODE_CLOSE,
			msgPrint : validateMessage.CODE_PRINT,
			msgOk : validateMessage.CODE_OK	
		}		
		
		
		if (typeof(this.messageConfirmAdd) == "undefined" || !this.messageConfirmAdd){
			this.messageConfirmAdd = messageButton.msgConfirmSaveAdd;
		}
		if (typeof(this.messageCancelAdd) == "undefined" || !this.messageCancelAdd){
			this.messageCancelAdd = messageButton.msgCancelAdd;
		}
		if (typeof(this.messageConfirmEdit) == "undefined" || !this.messageConfirmEdit){
			this.messageConfirmEdit = messageButton.msgConfirmSaveEdit;
		}
		if (typeof(this.messageCancelEdit) == "undefined" || !this.messageCancelEdit){
			this.messageCancelEdit = messageButton.msgCancelEdit;
		}
		if (typeof(this.messageCancelView) == "undefined" || !this.messageCancelView){
			this.messageCancelView = messageButton.msgCancelView;
		}
		
		if (typeof(this.divCriteriaId) == "undefined" || !this.divCriteriaId){
			this.divCriteriaId = "";
		}
		
		if (typeof(this.divCriteriaIdDialog) == "undefined" || !this.divCriteriaIdDialog){
			this.divCriteriaIdDialog = "";
		}
		
		if (typeof(this.objClearId) == "undefined" || !this.objClearId){
			this.objClearId = "";
		}
		
		var tableHead = "<table class=\"BUTTON\"><tr class=\"\"><td class=\"LEFT RIGHT_70\"></td><td class=\"RIGHT LEFT_30\">";
		var tableFooter = "</td></tr></table>";

		
		
		
		if(this.buttonType == pageSearch){
			if(this.auth=="true"){
				$(this.$element).html(
					tableHead +
					"<button id=\"btnSearch\" class=\"btnSearch btn-fixsize\" type=\"button\" onclick=\"if(!onSearch('"+this.divCriteriaId+"','"+this.funcValidSearch+"')){return false;}; return "+this.funcSearch+"();\"> " +
					messageButton.msgSearch +
					"</button> "+
					"<button id=\"btnRefresh\" class=\"btnRefresh btn-clearsize\" type=\"button\" onclick=\"return clearForm('"+this.funcClearFormCallBack+"','"+this.divCriteriaId+"','"+this.divTableResultId+"' );\"> "+
					messageButton.msgClear +
					"</button> " +				
					tableFooter);
			}else{
				$(this.$element).html(
					tableHead +
					"<button id=\"btnSearch\"  class=\"btnSearch btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> "+
					messageButton.msgSearch+
					"</button> "+
					"<button id=\"btnRefresh\" class=\"btnRefresh btn-clearsize\" type=\"button\" onclick=\"return clearForm('"+this.funcClearFormCallBack+"','"+this.divCriteriaId+"');\"> "+
					messageButton.msgClear +
					"</button> " +				
					tableFooter);
			}
		}
		if(this.buttonType == pageAdd){
			if(this.auth=="true"){
				$(this.$element).html(
					tableHead +
					"<button id=\"btnAdd\" class=\"btnAdd btn-fixsize\" type=\"button\" onclick=\"if(!onAdd('"+this.messageConfirmAdd+"','"+this.funcValidAdd+"')){return false;}; return "+this.funcSaveAdd+"();\"> " +
					messageButton.msgSave +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\"if(!onCancelAdd('"+this.messageCancelAdd+"')){return false;}; return "+this.funcCancelAdd+"();\" > " +
					messageButton.msgCancel +
					"</button> " +	
					tableFooter);
			}else{
				$(this.$element).html(
					tableHead +		
					"<button id=\"btnAdd\" class=\"btnAdd btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgSave +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\"if(!onCancelAdd('"+this.messageCancelAdd+"')){return false;}; return "+this.funcCancelAdd+"();\" > " +
					messageButton.msgCancel +
					"</button> " +
					tableFooter);
			}			
		}
		if(this.buttonType == pageEdit){
			if(this.auth=="true"){
				$(this.$element).html(
					tableHead +				
					"<button id=\"btnEdit\" class=\"btnEdit btn-fixsize\" type=\"button\" onclick=\"if(!onEdit('"+this.messageConfirmEdit+"','"+this.funcValidEdit+"')){return false;}; return "+this.funcSaveEdit+"();\"> " +
					messageButton.msgEdit + 
					"</button> " +
					"<button id=\"btnCancel\"  class=\"btnCancel btn-fixsize\"  type=\"button\" onclick=\"if(!onCancelEdit('"+this.messageCancelEdit+"')){return false;}; return "+this.funcCancelEdit+"();\"> " +
					messageButton.msgCancel + 
					"</button> " +
					tableFooter);
			}else{
				$(this.$element).html(
					tableHead +	
					"<button id=\"btnEdit\"  class=\"btnEdit btn-width\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgEdit + 
					"</button> " +
					"<button id=\"btnCancel\" class=\"btnCancel btn-width\" type=\"button\" onclick=\"if(!onCancelEdit('"+this.messgeCancelEdit+"')){return false;}; return "+this.funcCancelEdit+"();\"> " +
					messageButton.msgCancel + 
					"</button> " +				
					tableFooter);
			}			
		}
		if(this.buttonType == pageView){
			$(this.$element).html(
				tableHead +
				"<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\"if(!onCancelView('"+this.messageCancelView+"')){return false;}; return "+this.funcCancelView+"();\" > " +
				messageButton.msgClose +
				"</button> " +
				tableFooter);			
		}	
		
		if(this.buttonType == pageReport){
			if(this.auth=="true"){	
				$(this.$element).html(
					tableHead +					
					"<button id=\"btnPrint\" class=\"btnPrint btn-fixsize\" type=\"button\" onclick=\"if(!onReport()){return false;}; return "+this.funcPrint+"();\"> " +
					messageButton.msgPrint +
					"</button> " +
					"<button id=\"btnClear\" class=\"btnClear btn-clearsize\" type=\"button\" onclick=\"return clearForm('"+this.funcClearFormCallBack+"','"+this.divCriteriaId+"');\"> " +
					messageButton.msgClear +
					"</button> " +
				tableFooter);				
			}else{				
				$(this.$element).html(
					tableHead +	
					"<button id=\"btnPrint\"  class=\"btnPrint btn-fixsize\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgPrint +
					"</button> " +
					"<button id=\"btnClear\"  class=\"btnCancel btn-clearsize\" type=\"button\" onclick=\"return clearForm('"+this.funcClearFormCallBack+"','"+this.divCriteriaId+"');\" > " +
					messageButton.msgClear +
					"</button> " +	
				tableFooter);
			}				
		}
		
		if(this.buttonType == pagePrint){
			if(this.auth=="true"){	
				$(this.$element).html(
					tableHead +
					"<button id=\"btnPrint\" class=\"btnPrint btn-fixsize\"  type=\"button\" onclick=\"if(!onPrint()){return false;}; return "+this.funcPrint+"();\"> " +
					messageButton.msgPrint +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\"return "+this.funcCancelPrint+"();\"> " +
					messageButton.msgCancel +
					"</button> " +				
					tableFooter);
			}else{				
				$(this.$element).html(
					tableHead +	
					"<button id=\"btnPrint\" class=\"btnPrint btn-fixsize\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgPrint +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\"return "+this.funcCancelPrint+"();\" > " +
					messageButton.msgCancel +
					"</button> " +
				tableFooter);	
			}				
		}
		
		if(this.buttonType == pageSearchDialog){
			if(this.auth=="true"){	
				$(this.$element).html(
					tableHead +					
					"<button id=\"btnSearch\" class=\"btnSearch btn-fixsize\"  type=\"button\" onclick=\"if(!onSearchDialog('"+this.divCriteriaIdDialog+"','"+this.funcValidSearchDialog+"')){return false;}; return "+this.funcSearchDialog+"();\"> " +
					messageButton.msgSearch +
					"</button> " +
					"<button id=\"btnRefresh\" class=\"btnRefresh btn-clearsize\" type=\"button\" onclick=\"return clearForm('"+this.funcClearFormCallBackDialog+"','"+this.divCriteriaIdDialog+"','"+this.divTableResultIdDialog+"');\"> " +
					//"<button id=\"btnRefresh\" class=\"btnRefresh btn-fixsize\" type=\"button\" > " +
					messageButton.msgClear +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\"  type=\"button\" onclick=\"return "+this.funcCloseDialog+"();\"> " +
					messageButton.msgClose +
					"</button> " +
					tableFooter);
				
			}else{					
				$(this.$element).html(
					tableHead +	
					"<button id=\"btnSearch\" class=\"btnSearch btn-fixsize\" type=\"button\" disabled=\"disabled\" > " +
					messageButton.msgSearch +
					"</button> " +
					"<button id=\"btnRefresh\" class=\"btnRefresh btn-clearsize\" type=\"button\" onclick=\"return clearForm('"+this.funcClearFormCallBackDialog+"','"+this.divCriteriaIdDialog+"',,'"+this.divTableResultIdDialog+"');\"> " +
					//"<button id=\"btnRefresh\" class=\"btnRefresh btn-fixsize\" type=\"button\" > " +
					messageButton.msgClear +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\"return "+this.funcCloseDialog+"();\"> " +
					messageButton.msgClose + 
					"</button> " +
					tableFooter);
			}				
		}
		if(this.buttonType == pageChooseDialog){
			if(this.auth=="true"){	
				$(this.$element).html(
					tableHead +					
					"<button id=\"btnOk\" class=\"btnOK btn-fixsize\" type=\"button\" onclick=\"return "+this.funcChooseDialog+"();\"> " +
					messageButton.msgOk +
					"</button> " +
					tableFooter);
			}else{					
				$(this.$element).html(
					tableHead +
					"<button id=\"btnOk\" class=\"btnOK btn-fixsize\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgOk +
					"</button> " +
					tableFooter);
			}				
		}		
		this.initButton();
	}
	
	SitButton.prototype.initButton = function() {	
		//Default button
		jQuery(":button").button();

		//ปุ่มและ icon
		jQuery(".btnSearch").button({
			icons: {
				primary: "ui-icon-search"
		    }
		});

		jQuery(".btnRefresh").button({
		    icons: {
		        primary: "ui-icon-refresh"
		    }
		});

		jQuery(".btnEdit").button({
		    icons: {
		        primary: "ui-icon-disk"
		    }
		});

		jQuery(".btnCancel").button({
		    icons: {
		        primary: "ui-icon-close"
		    }
		});

		jQuery(".btnAdd").button({
		    icons: {
		        primary: "ui-icon-disk"
		    }
		});

		jQuery(".btnPrint").button({
		    icons: {
		        primary: "ui-icon-print"
		    }
		});

		jQuery(".btnClear").button({
		    icons: {
		        primary: "ui-icon-close"
		    }
		});
		
		jQuery(".btnClose").button({
		    icons: {
		        primary: "ui-icon-close"
		    }
		});
		
		jQuery(".btnSave").button({
		    icons: {
		        primary: "ui-icon-disk"
		    }
		});
		
		jQuery(".btnRegister").button({
			icons: {
				primary: "ui-icon-disk"
			}
		});
		
		jQuery(".btnBack").button({
			icons: {
				primary: "ui-icon-triangle-1-w"
			}
		});
		
		jQuery(".btnOK").button({
			icons: {
				primary: "ui-icon-disk"
			}
		});
		
		jQuery(".btnDeleteCustom").button({
			icons: {
				primary: "ui-icon-trash"
			}
		});
		
		jQuery(".btnBilling").button({
			icons: {
				primary: "ui-icon-circle-triangle-e"
			}
		});
		
		jQuery(".btnSale").button({
			icons: {
				primary: "ui-icon-circle-triangle-w"
			}
		});
	}
	
	
	
	
	// PLUGIN DEFINITION
	// =======================
	

	function Plugin(option, _relatedTarget) {
		return this.each(function() {
			var $this = $(this)
			var data = $this.data('sitButton')
			var options = $.extend({}, SitButton.DEFAULTS, $this.data(),
					typeof option == 'object' && option)
					
			if (!data) {
				$this.data('sitButton', (data = new SitButton(this, options)));
				data.create(_relatedTarget);
			}
			
			
			if (typeof option == 'string') {
				data[option](_relatedTarget);
			}
		})
	}
	

	
	var old = $.fn.sitButton

	$.fn.sitButton = Plugin
	$.fn.sitButton.Constructor = SitButton

}(jQuery);


/**
* สำหรับ print หน้าจอในรูปแบบ browser
*/
function printBrowser() {
	/*
	if (typeof document.getElementsByName("printId")[0] != "undefined") {
		var operationId = document.getElementsByName("printId")[0].value;
	    $.ajax({
	        type: 'post',
	        url: contextPath +'/jsp/log/printLog.action',
	        data: {
	          'operationId': operationId,
	        },
	        success: function (response) {

	        },
	        error: function () {

	        }
	    });
	}
	*/
	window.print();
    return false;
}


/**
* สำหรับ clear ค่าของหน้าจอ [ค้นหา, เพิ่ม, ลงทะเบียน]
*/
function clearForm(clearFormCallBack, divCriteria, divTableResult) {
	clearMessage();
	
	var objClear;
	if (typeof(divCriteria) == "undefined" || !divCriteria){
		objClear = jQuery(".clearform");
	}else{
		objClear = jQuery("#"+divCriteria).find(".clearform");
	}
	
	jQuery("#"+divTableResult).hide();
	
	jQuery(objClear).each(function(index) {
		jQuery(this).val("");
		if (jQuery(this).prop("tagName") == "SELECT") {
			jQuery(this).selectmenu('refresh');
		} else if (jQuery(this).prop("tagName") == "DIV") {
			jQuery(this).children("input[type=checkbox]").each(function() {
				jQuery(this).prop( "checked", false );
			});
			setClassCheckbox();

			jQuery(this).children("input[type=radio]").each(function() {
				jQuery(this).prop( "checked", false );
			});
			setClassRadio();
		}
	});

	// กรณีที่มีการกำหนดค่า default หรือ กำหนดค่าอื่นๆในแต่ละหน้าจอเอง
	// จะทำการตรวจสอบว่าแต่ละหน้าจอมี function clearFormCallBack()?
	// กรณีมี function การทำงานจะทำการเรียกใช้งาน function clearFormCallBack() ของหน้าจอนั้นๆ
	//if(typeof "'+clearFormCallBack+'" == 'function'){
		//clearFormCallBack();
	//}
	
	var fn = window[clearFormCallBack]; 
	if(typeof fn === 'function') {
	    fn();
	}
}


function onSearch(divid,checkValidSearch) {
	if(validateFormInputAll()){
		var criteriaKey;
		if (typeof(divid) == "undefined" || !divid){
			criteriaKey = $("input[name='criteria.criteriaKey']");
		}else{
			criteriaKey = $("#"+divid+" input[name='criteria.criteriaKey']")
		}		
		var fn = window[checkValidSearch]; 
		if(typeof fn === 'function') {
			if(fn()){
				criteriaKey.val("");
				return true;
			}else{
				return false;
			}
		}
		criteriaKey.val("");
		return true;
	}else{
		return false;
	}
}



//msgConfirm : ข้อความที่แสดงสำหรับยืนยันการ add
//checkValidAdd : function สำหรับการตรวจสอบ validate Add
function onAdd(msgConfirm,checkValidAdd){
	if(validateFormInputAll()){
		var fn = window[checkValidAdd]; 
		if(typeof fn == 'function'){
			if(fn() == false){
				return false;
			}
		}
		if (confirm(msgConfirm) == false) {
			return false;
		} else{
			return true;
		}
	}
}

function onEdit(msgConfirm,checkValid) {
	if(validateFormInputAll()){
		var fn = window[checkValid];
		if(typeof fn == 'function'){
			if(fn() == false){
				return false;
			}
		}
		if (confirm(msgConfirm) == false){;
			return false;
		} else{
			return true;
		}
	}

}

function onSearchDialog(divid,chkValidSearchDialog) {
	if(validateFormInputAll()){
		var fn = window[chkValidSearchDialog]; 
		if(typeof fn === 'function') {
			if(fn()){
				$("#"+divid+" input[name='criteria.criteriaKey']").val("");
				return true;
			}else{
				return false;
			}
		}
		$("#"+divid+" input[name='criteria.criteriaKey']").val("");
		return true;
	}else{
		return false;
	}	
	
	
}

function onValidSearch(){
	return false;
}


function onCancelView(msgCancelView) {
	if(confirm(msgCancelView) == false) {	
		return false;
	}else{
		return true;
	}
}

function onCancelAdd(msgCancelAdd){
	if(confirm(msgCancelAdd) == false) {	
		return false;
	}else{
		return true;
	}
}


function onCancelEdit(msgCancelEdit){
	if(confirm(msgCancelEdit) == false) {	
		return false;
	}else{
		return true;
	}
}

function onCancelDialog(){
	if(confirm(messageButton.msgCancelDialog) == false) {	
		return false;
	}else{
		return true;
	}	
}

function onReport() {
	if(validateFormInputAll()){
		return true;
	}else{
		return false;
	}
}

function onPrint() {
	if(validateFormInputAll()){
		return true;
	}else{
		return false;
	}
}
