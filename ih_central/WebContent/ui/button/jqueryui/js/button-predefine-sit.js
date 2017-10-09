+function($) {
	'use predefine button';
	var symbolSpllit = "|";
	var buttonType = {
		search : "SEARCH",
		clear :	"CLEAR",
		edit :	"EDIT",
		refresh : "REFRESH",
		save : "SAVE",
		cancel : "CANCEL",
		print : "PRINT",
		ok : "OK",
		close : "CLOSE", 
		gotoadd : "GOTOADD",
		editAndEditResult : "EDIT_AND_EDIT_TEST_RESULT", /*ใช้ในโครงการ qaqc* :channarong.i 28/09/60 */
		saveAndSaveResult : "SAVE_AND_ADD_TEST_RESULT",
		load : "LOAD"
	}
	
	var styleDefault = "btn-fixsize";
	

	var SitButtonPredefine = function(element, options) {
		this.options = options
		this.$body = $(document.body)
		this.$element = $(element)
		this.id = $(element).attr('id');
		this.buttonType = options.buttonType;
		this.auth = options.auth;
		this.func = options.func;
		this.container = options.container;
		this.style = options.style;
	}
	
	SitButtonPredefine.VERSION = ''
	SitButtonPredefine.DEFAULTS = {
	}
	
	SitButtonPredefine.prototype.create = function() {
		this.createSitButtonPredefineBox();
	}
	
	
	//Prototype Function สำหรับ change Disabled / Enabled Button
	SitButtonPredefine.prototype.changeStateButton = function(btntype, disabled) {	
		var btnid = "btn_"+ btntype.toUpperCase();
	    obj = jQuery("#"+this.id).find('#'+btnid);
	    obj.prop('disabled', disabled);
	    if(disabled == true){
	    	obj.addClass("ui-button-disabled ui-state-disabled");
	    }else{
	    	obj.removeClass("ui-button-disabled ui-state-disabled");
	    }
	}
	
	
	SitButtonPredefine.prototype.createSitButtonPredefineBox = function() {	
		var messageButton = {
			msgSearch : validateMessage.CODE_SEARCH,
			msgClear : validateMessage.CODE_CLEAR,
			msgSave : validateMessage.CODE_SAVE,
			msgCancel : validateMessage.CODE_CANCEL,
			msgEdit : validateMessage.CODE_EDIT,
			msgClose : validateMessage.CODE_CLOSE,
			msgPrint : validateMessage.CODE_PRINT,
			msgOk : validateMessage.CODE_OK	
		}	
		
		var tableHead = "<table class=\"BUTTON\"><tr class=\"\"><td class=\"LEFT RIGHT_70\"></td><td class=\"RIGHT LEFT_30\">";
		var tableFooter = "</td></tr></table>";

		var typeData = this.buttonType;
	    var type = typeData.split(symbolSpllit);
	    
	    var funcData = this.func;
	    var funcArray = funcData.split(symbolSpllit);
	    
	    var authData = this.auth;
	    if (typeof(authData) == "undefined" || !authData){
	    	authData = "";
	    }
	    var authArray = authData.split(symbolSpllit);
	    
	    var styleData = this.style;
	    if (typeof(styleData) == "undefined" || !styleData){
	    	styleData = "";
	    }
	    var styleArray = styleData.split(symbolSpllit);
	    
	    var button ="";
	    for(var j=0;j<type.length;j++){
	    	if (typeof(styleArray[j]) == "undefined" || !styleArray[j]){
	    		styleArray[j] = styleDefault;
	    	}	    	

	    	if(type[j].toUpperCase() == buttonType.search){
	    		if(authArray[j]=="true"){	
		    		button = button + "<button id=\"btn_"+buttonType.search+"\" class=\"btnSearch "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					messageButton.msgSearch +
					"</button> "
	    		}else{
		    		button = button + "<button id=\"btn_"+buttonType.search+"\" class=\"btnSearch "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgSearch +
					"</button> "
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.save){
	    		if(authArray[j]=="true"){	
		    		button = button + "<button id=\"btn_"+buttonType.save+"\" class=\"btnAdd "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					messageButton.msgSave +
					"</button> "
	    		}else{
		    		button = button + "<button id=\"btn_"+buttonType.save+"\" class=\"btnAdd "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgSave +
					"</button> "
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.clear){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.clear+"\" class=\"btnClear "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					messageButton.msgClear +
					"</button>"
	    		}else{
					button = button + "<button id=\"btn_"+buttonType.clear+"\" class=\"btnClear "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgClear +
					"</button>"
	    		}
	    	}	  
	    	if(type[j].toUpperCase() == buttonType.refresh){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.refresh+"\" class=\"btnRefresh "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					messageButton.msgClear +
					"</button>"
	    		}else{
					button = button + "<button id=\"btn_"+buttonType.refresh+"\" class=\"btnRefresh "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\">" +
					messageButton.msgClear +
					"</button>"
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.edit){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.edit+"\" class=\"btnEdit "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					messageButton.msgEdit +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.edit+"\" class=\"btnEdit "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgEdit +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.cancel){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.cancel+"\" class=\"btnCancel "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					messageButton.msgCancel +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.cancel+"\" class=\"btnCancel "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgCancel +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.print){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.print+"\" class=\"btnPrint "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					messageButton.msgPrint +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.print+"\" class=\"btnPrint "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgprint +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.ok){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.ok+"\" class=\"btnOK "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					messageButton.msgOk +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.ok+"\" class=\"btnOK "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgOk +
					"</button> "		    	
	    		}
	    	}	    	
	    	if(type[j].toUpperCase() == buttonType.close){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.close+"\" class=\"btnCancel "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					messageButton.msgClose +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.close+"\" class=\"btnCancel "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgClose +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.gotoadd){
	    		if(authArray[j]=="true"){	
		    		button = button + "<button id=\"btn_"+buttonType.gotoadd+"\" class=\"btnGotoAdd "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"</button> "
	    		}else{
		    		button = button + "<button id=\"btn_"+buttonType.gotoadd+"\" class=\"btnGotoAdd "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"</button> "
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.editAndEditResult){ /*ใช้ในโครงการ qaqc*/
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.editAndEditResult+"\" class=\"btnEdit "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"Edit and edit test result" +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.editAndEditResult+"\" class=\"btnEdit "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"Edit and edit test result" +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.saveAndSaveResult){ /*ใช้ในโครงการ qaqc*/
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.saveAndSaveResult+"\" class=\"btnAdd "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"Save and add test result" +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.saveAndSaveResult+"\" class=\"btnAdd "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"Save and add test result" +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.load){
	    		if(authArray[j]=="true"){	
		    		button = button + "<button id=\"btn_"+buttonType.load+"\" class=\"btnLoad "+styleArray[j]+"\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"Load" +
					"</button> "
	    		}else{
		    		button = button + "<button id=\"btn_"+buttonType.load+"\" class=\"btnLoad "+styleArray[j]+"\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"Load" +
					"</button> "
	    		}
	    	}
	    }
	    
	    //check contrainer ว่าเท่ากับ true หรือไม่
	    if(this.container == "true"){	
	    	$(this.$element).html(tableHead + button + tableFooter);
	    }else{
	    	$(this.$element).html(button);
	    }
	    this.initButton();
	}
	
	SitButtonPredefine.prototype.initButton = function() {	
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
		
		jQuery(".btnGotoAdd").button({
		    icons: {
		        primary: "ui-icon-plus"
		    }
		});
		
		jQuery(".btnLoad").button({
			icons: {
				primary: "ui-icon-search"
		    }
		});
	}
	
	
	// PLUGIN DEFINITION
	// =======================
	function Plugin(option, _relatedTarget, _data1) {
		return this.each(function() {
			var $this = $(this)
			var data = $this.data('sitButtonPredefine')
			var options = $.extend({}, SitButtonPredefine.DEFAULTS, $this.data(), typeof option == 'object' && option)
					
			if (!data) {
				$this.data('sitButtonPredefine', (data = new SitButtonPredefine(this, options)));
				data.create(_relatedTarget);
			}
			
			if (typeof option == 'string') {
				data[option](_relatedTarget,_data1);
			}
		})
	}
	
	var old = $.fn.sitButtonPredefine

	$.fn.sitButtonPredefine = Plugin
	$.fn.sitButtonPredefine.Constructor = SitButtonPredefine

}(jQuery);

/*
 * function สำหรับการทำงานเปลี่ยน disable/enable button
 * param divid : id ของ div ที่มีการวาด button
 * param buttonnType : ปุ่มที่ต้องการให้มีการ Disable/Enabled
 * param disabled : true/false 
 *  - กรณี true จะทำการ Disable
 *  - กรณี false จะทำการ Enabled 
 * 
 * */
function changeStateButton(divid,buttonType,disabled) {
    $("#"+divid).sitButtonPredefine("changeStateButton",buttonType,disabled);
}